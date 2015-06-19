package com.cooper.nwemail.ui.news;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.cooper.nwemail.R;
import com.cooper.nwemail.adapter.NewsListAdapter;
import com.cooper.nwemail.application.ApplicationComponent;
import com.cooper.nwemail.bus.AnyThreadBus;
import com.cooper.nwemail.enums.NavigationEnum;
import com.cooper.nwemail.models.Article;
import com.cooper.nwemail.ui.common.BaseFragment;

import javax.inject.Inject;

import butterknife.InjectView;
import io.realm.RealmResults;

/**
 * TODO
 */
public class NewsListFragment extends BaseFragment implements NewsListView,
        SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "NewsListFragment";

    private static final String KEY_NEWS_TYPE = "KEY_NEWS_TYPE";

    @Inject
    NewsListPresenterImpl presenter;

    @Inject
    AnyThreadBus mBus;

    @InjectView(R.id.swipeRefresh_news)
    SwipeRefreshLayout mSwipeRefresh;

    @InjectView(R.id.recyclerview_news)
    RecyclerView mListView;

    private NewsListAdapter mAdapter;

    public static NewsListFragment newInstance(final NavigationEnum navigationEnum) {
        final Bundle bundle = new Bundle();
        bundle.putInt(KEY_NEWS_TYPE, navigationEnum.ordinal());
        final NewsListFragment fragment = new NewsListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mSwipeRefresh.setOnRefreshListener(this);
        mSwipeRefresh.setColorSchemeColors(R.color.app_primary);

        //Set the correct layout manager based on device + orientation
        RecyclerView.LayoutManager layoutManager;
        final Resources resources = getResources();
        if (resources.getBoolean(R.bool.isTablet)) {
            int cols = resources.getInteger(R.integer.article_column_span);
            layoutManager = new StaggeredGridLayoutManager(cols, LinearLayoutManager.VERTICAL);
        } else {
            layoutManager = new LinearLayoutManager(getActivity());
        }

        mListView.setLayoutManager(layoutManager);

        //Get the news type we are displaying and pass it to the presenter
        final Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey(KEY_NEWS_TYPE)) {

            final NavigationEnum newsType = NavigationEnum.values()[bundle.getInt(KEY_NEWS_TYPE)];
            getActivity().setTitle(NavigationEnum.getName(newsType));

            presenter.setNewsType(newsType);
        } else {
            onDestroy();
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_news_list;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_article_list, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean result = false;

        if (item.getItemId() == R.id.action_refresh) {
            onRefresh();
            result = true;
        }
        return result;
    }

    @Override
    public void onRefresh() {
        //Ask the presenter to refresh
        presenter.updateArticles();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.initialize();
    }

    @Override
    public void displayArticles(final RealmResults<Article> articles) {
        Log.d(TAG, "Displaying " + articles.size() + " articles");
        if (mAdapter == null) {
            mAdapter = new NewsListAdapter(getActivity(), mBus, null);
            mListView.setAdapter(mAdapter);
        }

        mAdapter.setArticles(articles);
    }

    @Override
    public void hideList() {
        mListView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showList() {
        mListView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideUpdating() {
        mSwipeRefresh.setRefreshing(false);
    }

    @Override
    public void showUpdating() {
        mSwipeRefresh.setRefreshing(true);
    }

    @Override
    public void showErrorMessage() {
        final View view = getView();
        if (view != null) {
            Snackbar.make(getView(), R.string.snackbar_update_fail, Snackbar.LENGTH_LONG)
                    .setAction(R.string.snackbar_action_retry, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onRefresh();
                        }
                    }).show();
        }
    }

    @Override
    public void showLoading() {
        mProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mProgress.setVisibility(View.GONE);
    }

    @Override
    protected void setupComponent(ApplicationComponent applicationComponent) {
        DaggerNewsListComponent.builder()
                .applicationComponent(applicationComponent)
                .newsListModule(new NewsListModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void onDestroy() {
        presenter.destroy();
        super.onDestroy();
    }
}

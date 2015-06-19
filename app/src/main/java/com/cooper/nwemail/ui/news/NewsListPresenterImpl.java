package com.cooper.nwemail.ui.news;

import android.util.Log;

import com.cooper.nwemail.bus.AnyThreadBus;
import com.cooper.nwemail.bus.events.RSSErrorEvent;
import com.cooper.nwemail.bus.events.RSSSuccessEvent;
import com.cooper.nwemail.data.DataManager;
import com.cooper.nwemail.enums.NavigationEnum;
import com.cooper.nwemail.models.Article;
import com.cooper.nwemail.rss.RSSClient;
import com.crashlytics.android.Crashlytics;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;

import io.realm.RealmResults;

/**
 * TODO
 */
public class NewsListPresenterImpl extends NewsListPresenter {

    private static final String TAG = "NewsListPresenterImpl";
    private NavigationEnum mNewsType;

    private RSSClient mRSSClient;
    private DataManager mData;
    private AnyThreadBus mBus;

    @Inject
    public NewsListPresenterImpl(final NewsListView view, final RSSClient rssClient,
                                 final DataManager data, final AnyThreadBus bus) {
        super(view);
        mRSSClient = rssClient;
        mData = data;
        mBus = bus;
        mBus.register(this);
    }

    void setNewsType(final NavigationEnum newsType) {
        mNewsType = newsType;
        initialize();
    }

    @Override
    public void initialize() {

        view.showLoading();
        view.hideList();

        if (mNewsType != null) {
            if (!loadAndShowArticles()) {
                Log.d(TAG, "Unable to load articles");
                updateArticles();
            }
        }
    }

    @Override
    public void resume() {

    }

    @Override
    RealmResults<Article> loadArticles() {
        Log.d(TAG, "Loading articles");
        return mData.findAllArticles(mNewsType);
    }

    /**
     * Loads the articles from the db and displays them
     *
     * @return whether or not any articles were displayed
     */
    @Override
    boolean loadAndShowArticles() {
        final RealmResults<Article> results = loadArticles();

        if (results == null || results.isEmpty()) {
            return false;
        }

        view.displayArticles(results);
        view.showList();
        view.hideLoading();
        view.hideUpdating();
        return true;
    }

    @Subscribe
    public void onRSSSuccess(final RSSSuccessEvent event) {
        Log.d(TAG, "onRSSSuccess");

        //We know that the DB has been updated so show the articles
        loadAndShowArticles();
    }

    @Subscribe
    public void onRSSFailure(final RSSErrorEvent event) {
        view.hideUpdating();
        view.showErrorMessage();
        Log.d(TAG, "onRSSFailure");
        Crashlytics.getInstance().core.logException(event.getError());
    }

    @Override
    void updateArticles() {
        view.showUpdating();
        Log.d(TAG, "Updating Articles");
        mRSSClient.getRSSFeed(NavigationEnum.getFeedId(mNewsType));
    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        if (mBus != null) {
            mBus.unregister(this);
        }
    }
}

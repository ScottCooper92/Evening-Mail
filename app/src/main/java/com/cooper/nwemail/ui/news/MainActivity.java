package com.cooper.nwemail.ui.news;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.cooper.nwemail.R;
import com.cooper.nwemail.application.ApplicationComponent;
import com.cooper.nwemail.bus.events.ArticleClickEvent;
import com.cooper.nwemail.enums.NavigationEnum;
import com.cooper.nwemail.ui.about.AboutActivity;
import com.cooper.nwemail.ui.common.NavigationActivity;
import com.cooper.nwemail.ui.contact.ContactActivity;
import com.cooper.nwemail.ui.news.detail.NewsDetailActivity;
import com.cooper.nwemail.ui.news.detail.NewsImageDetailActivity;
import com.cooper.nwemail.ui.settings.SettingsActivity;
import com.squareup.otto.Subscribe;

/**
 * TODO
 */
public class MainActivity extends NavigationActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onNavigationItemSelected(NavigationEnum navigationEnum) {
        switch (navigationEnum) {
            case CONTACT:
                startActivity(new Intent(this, ContactActivity.class));
                break;
            case ABOUT:
                startActivity(new Intent(this, AboutActivity.class));
                break;
            case SETTINGS:
                startActivity(new Intent(this, SettingsActivity.class));
                break;
            default:
                addFragment(navigationEnum);
                break;
        }
    }

    /**
     * Adds a new NewsListFragment to the FrameLayout in this view
     *
     * @param navigationEnum
     */
    private void addFragment(final NavigationEnum navigationEnum) {
        final NewsListFragment newsListFragment = NewsListFragment.newInstance(navigationEnum);
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout_news_list, newsListFragment).commit();
    }

    @Subscribe
    public void onArticleClicked(final ArticleClickEvent event) {

        if (event.isHasImage()) {
            NewsImageDetailActivity.launch(this, event.getTransitionView(), event.getGuid());
        } else {
            final Intent intent = new Intent(this, NewsDetailActivity.class);
            intent.putExtra(NewsDetailActivity.ARG_ARTICLE, event.getGuid());
            startActivity(intent);
        }
    }


    @Override
    protected void setupComponent(ApplicationComponent applicationComponent) {
        mBus = applicationComponent.bus();
    }
}

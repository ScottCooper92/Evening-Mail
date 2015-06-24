package com.cooper.nwemail.ui.news;

import com.cooper.nwemail.application.ActivityScope;
import com.cooper.nwemail.bus.AnyThreadBus;
import com.cooper.nwemail.data.DataManager;
import com.cooper.nwemail.rss.RSSClient;

import dagger.Module;
import dagger.Provides;

@Module
public class NewsListModule {

    private final NewsListView view;

    public NewsListModule(final NewsListView view) {
        this.view = view;
    }

    @Provides
    @ActivityScope
    public NewsListView provideView() {
        return view;
    }

    @Provides
    @ActivityScope
    public NewsListPresenter providePresenter(final NewsListView newsListView, final RSSClient rssClient,
                                              final DataManager data, final AnyThreadBus bus) {
        return new NewsListPresenterImpl(newsListView, rssClient, data, bus);
    }
}

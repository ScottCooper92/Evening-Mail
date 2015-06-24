package com.cooper.nwemail.ui.news.detail;

import com.cooper.nwemail.application.ActivityScope;
import com.cooper.nwemail.data.DataManager;

import dagger.Module;
import dagger.Provides;

@Module
public class NewsDetailModule {

    private final NewsDetailView view;

    public NewsDetailModule(final NewsDetailView view) {
        this.view = view;
    }

    @Provides
    @ActivityScope
    public NewsDetailView provideView() {
        return view;
    }

    @Provides
    @ActivityScope
    public NewsDetailPresenter providePresenter(final NewsDetailView newsDetailView, final DataManager data) {
        return new NewsDetailPresenterImpl(newsDetailView, data);
    }
}

package com.cooper.nwemail.ui.news.detail;

import com.cooper.nwemail.application.ActivityScope;
import com.cooper.nwemail.application.ApplicationComponent;

import dagger.Component;

@ActivityScope
@Component(
        dependencies = ApplicationComponent.class,
        modules = NewsDetailModule.class
)
public interface NewsDetailComponent {
    void inject(NewsDetailActivity newsDetailActivity);
}

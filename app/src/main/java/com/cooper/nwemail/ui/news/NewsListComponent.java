package com.cooper.nwemail.ui.news;

import com.cooper.nwemail.application.ActivityScope;
import com.cooper.nwemail.application.ApplicationComponent;

import dagger.Component;

@ActivityScope
@Component(
        dependencies = ApplicationComponent.class,
        modules = NewsListModule.class
)
public interface NewsListComponent {
    void inject(NewsListFragment newsListFragment);
}

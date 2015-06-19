package com.cooper.nwemail.ui.settings;

import com.cooper.nwemail.application.ActivityScope;
import com.cooper.nwemail.data.DataManager;

import dagger.Module;
import dagger.Provides;

@Module
public class SettingsModule {

    private final SettingsView view;

    public SettingsModule(final SettingsView view) {
        this.view = view;
    }

    @Provides
    @ActivityScope
    public SettingsView provideView() {
        return view;
    }

    @Provides
    @ActivityScope
    public SettingsPresenter providePresenter(final SettingsView settingsView, final DataManager data) {
        return new SettingsPresenterImpl(settingsView, data);
    }
}

package com.cooper.nwemail.ui.settings;

import com.cooper.nwemail.application.ActivityScope;
import com.cooper.nwemail.application.ApplicationComponent;

import dagger.Component;

@ActivityScope
@Component(
        dependencies = ApplicationComponent.class,
        modules = SettingsModule.class
)
public interface SettingsComponent {
    void inject(SettingsFragment settingsFragment);
}

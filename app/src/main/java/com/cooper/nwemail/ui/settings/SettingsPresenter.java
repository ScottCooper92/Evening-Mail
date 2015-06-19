package com.cooper.nwemail.ui.settings;

import com.cooper.nwemail.ui.common.BasePresenter;

public abstract class SettingsPresenter extends BasePresenter<SettingsView> {

    public SettingsPresenter(SettingsView view) {
        super(view);
    }

    abstract void onClearCache();
}

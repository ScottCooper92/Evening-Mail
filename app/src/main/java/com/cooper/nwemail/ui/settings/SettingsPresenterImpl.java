package com.cooper.nwemail.ui.settings;

import com.cooper.nwemail.data.DataManager;

import javax.inject.Inject;

/**
 * The SettingsPresenter takes care of any logic associated with any settings options that require an immediate reaction
 */
public class SettingsPresenterImpl extends SettingsPresenter {

    private final DataManager mData;

    @Inject
    public SettingsPresenterImpl(SettingsView settingsView, DataManager data) {
        super(settingsView);
        mData = data;
    }

    @Override
    public void initialize() {

    }

    @Override
    public void resume() {

    }

    @Override
    void onClearCache() {
        mData.removeAllRecords();
    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {

    }
}

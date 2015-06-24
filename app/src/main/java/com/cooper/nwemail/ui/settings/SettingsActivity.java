package com.cooper.nwemail.ui.settings;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.Menu;

import com.cooper.nwemail.R;
import com.cooper.nwemail.application.ApplicationComponent;
import com.cooper.nwemail.ui.common.BaseActivity;

/**
 * The SettingsActivity simply hosts the SettingsFragment
 */
public class SettingsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    protected void setupComponent(ApplicationComponent applicationComponent) {

    }
}

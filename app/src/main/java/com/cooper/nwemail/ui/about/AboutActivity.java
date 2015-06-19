package com.cooper.nwemail.ui.about;

import android.os.Bundle;
import android.support.v7.app.ActionBar;

import com.cooper.nwemail.R;
import com.cooper.nwemail.application.ApplicationComponent;
import com.cooper.nwemail.ui.common.BaseActivity;

public class AboutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected void setupComponent(ApplicationComponent applicationComponent) {

    }
}

package com.cooper.nwemail.ui.common;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.cooper.nwemail.R;
import com.cooper.nwemail.application.ApplicationComponent;
import com.cooper.nwemail.application.NWEApplication;
import com.cooper.nwemail.bus.AnyThreadBus;
import com.cooper.nwemail.ui.contact.ContactActivity;
import com.cooper.nwemail.ui.settings.SettingsActivity;
import com.google.android.gms.analytics.GoogleAnalytics;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.Optional;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * TODO
 */
public abstract class BaseActivity extends AppCompatActivity {

    private static final String TAG = "BaseActivity";

    protected abstract void setupComponent(ApplicationComponent applicationComponent);

    protected AnyThreadBus mBus;

    @Optional
    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupComponent(NWEApplication.get(this).component());

        //Lock to portrait if we're a phone
        if(!getResources().getBoolean(R.bool.isTablet)){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.inject(this);
        setupToolbar();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!NWEApplication.isPremium()) {
            getMenuInflater().inflate(R.menu.menu_main, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_upgrade:
                NWEApplication.upgrade(this, 1001);
                break;
            case R.id.action_contact:
                final Intent contactIntent = new Intent(this, ContactActivity.class);
                startActivity(contactIntent);
                return true;
            case R.id.action_settings:
                final Intent settingsIntent = new Intent(this, SettingsActivity.class);
                startActivity(settingsIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupToolbar() {
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mBus != null) {
            mBus.register(this);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        GoogleAnalytics.getInstance(this).reportActivityStart(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!NWEApplication.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (mBus != null) {
            mBus.unregister(this);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        GoogleAnalytics.getInstance(this).reportActivityStop(this);
    }

    @Override
    protected void onDestroy() {
        ButterKnife.reset(this);
        super.onDestroy();
    }
}

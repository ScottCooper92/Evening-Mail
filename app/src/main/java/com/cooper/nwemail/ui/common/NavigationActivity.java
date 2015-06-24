package com.cooper.nwemail.ui.common;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import com.cooper.nwemail.R;
import com.cooper.nwemail.enums.NavigationEnum;

import butterknife.InjectView;
import butterknife.Optional;

/**
 * The NavigationActivity is inherited from when an Activity needs to show the NavigationDrawer.
 * Takes care of saving the instance state when rotating.
 */
public abstract class NavigationActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String KEY_NAVIGATION_POSITION = "KEY_NAVIGATION_POSITION";

    public abstract void onNavigationItemSelected(final NavigationEnum navigationEnum);

    @Optional
    @InjectView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @Optional
    @InjectView(R.id.navigation_view)
    NavigationView mNavigationView;

    private NavigationEnum mCurrentItem;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        setupNavigationDrawer();
    }

    protected void setupNavigationDrawer() {
        if (mNavigationView != null) {
            mNavigationView.setNavigationItemSelectedListener(this);

            final ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setHomeButtonEnabled(true);
                actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
                actionBar.setDisplayHomeAsUpEnabled(true);
            }
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        //If we are restoring then set the list back to how it was
        if (savedInstanceState != null && savedInstanceState.containsKey(KEY_NAVIGATION_POSITION)) {
            mCurrentItem = NavigationEnum.values()[savedInstanceState.getInt(KEY_NAVIGATION_POSITION, 0)];
            final MenuItem item = mNavigationView.getMenu().getItem(mCurrentItem.ordinal());
            item.setChecked(true);
        } else {
            final MenuItem item = mNavigationView.getMenu().getItem(0);
            onNavigationItemSelected(item);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        mCurrentItem = NavigationEnum.NEWS;
        switch (menuItem.getItemId()) {
            case R.id.nav_news:
                mCurrentItem = NavigationEnum.NEWS;
                break;
            case R.id.nav_afc:
                mCurrentItem = NavigationEnum.BARROW_AFC;
                break;
            case R.id.nav_raiders:
                mCurrentItem = NavigationEnum.BARROW_RAIDERS;
                break;
            case R.id.nav_football:
                mCurrentItem = NavigationEnum.FOOTBALL;
                break;
            case R.id.nav_league:
                mCurrentItem = NavigationEnum.RUGBY_LEAGUE;
                break;
            case R.id.nav_cricket:
                mCurrentItem = NavigationEnum.CRICKET;
                break;
            case R.id.nav_junior:
                mCurrentItem = NavigationEnum.JUNIOR_SPORT;
                break;
            case R.id.nav_other:
                mCurrentItem = NavigationEnum.OTHER_SPORT;
                break;
            case R.id.nav_contact:
                mCurrentItem = NavigationEnum.CONTACT;
                break;
            /*case R.id.nav_about:
                mCurrentItem = NavigationEnum.ABOUT;
                break;*/
            case R.id.nav_settings:
                mCurrentItem = NavigationEnum.SETTINGS;
                break;
        }

        menuItem.setChecked(true);
        mDrawerLayout.closeDrawers();
        onNavigationItemSelected(mCurrentItem);
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_NAVIGATION_POSITION, mCurrentItem.ordinal());
    }
}

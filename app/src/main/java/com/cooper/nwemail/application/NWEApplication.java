package com.cooper.nwemail.application;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.cooper.nwemail.R;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * The NWEApplication takes care of much of the global setup.
 * Calligraphy - Application wide Roboto
 * Dagger
 * Google Analytics
 */
public class NWEApplication extends Application {

    private static final String TAG = "NWEApplication";

    private ApplicationComponent component;
    private static Tracker mTracker;

    @Override
    public void onCreate() {
        super.onCreate();

        //Calligraphy
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath(getString(R.string.font_roboto_regular))
                .setFontAttrId(R.attr.fontPath)
                .build());

        //Dagger
        buildComponentAndInject();

        //Analytics
        final GoogleAnalytics googleAnalytics = GoogleAnalytics.getInstance(this);
        mTracker = googleAnalytics.newTracker(R.xml.app_tracker);
        mTracker.enableExceptionReporting(true);
        mTracker.enableAdvertisingIdCollection(true);
        mTracker.enableAutoActivityTracking(true);

        //Are we opted in?
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean optOut = sharedPreferences.contains("shareStatistics");
        if (optOut) {
            optOut = !sharedPreferences.getBoolean("shareStatistics", false);
        }
        googleAnalytics.setAppOptOut(optOut);
    }

    /**
     * Sends an Event to Google Analytics
     */
    public static void sendEvent(final String category, final String action) {
        if (mTracker != null)
            mTracker.send(new HitBuilders.EventBuilder().setCategory(category).setAction(action).build());
    }

    /**
     * Get an instance of this application
     */
    public static NWEApplication get(final Context context) {
        return (NWEApplication) context.getApplicationContext();
    }

    /**
     * Dagger
     */
    private void buildComponentAndInject() {
        component = ApplicationComponent.Initialiser.init(this);
        component.inject(this);
    }

    public ApplicationComponent component() {
        return component;
    }

}

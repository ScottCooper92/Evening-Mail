package com.cooper.nwemail.application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.cooper.inappbilling.util.IabHelper;
import com.cooper.inappbilling.util.IabResult;
import com.cooper.inappbilling.util.Inventory;
import com.cooper.inappbilling.util.Purchase;
import com.cooper.nwemail.BuildConfig;
import com.cooper.nwemail.R;
import com.crashlytics.android.Crashlytics;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.mopub.common.MoPub;

import io.fabric.sdk.android.Fabric;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * The NWEApplication takes care of much of the global setup.
 * Fabric - Answers and Crashlytics
 * Calligraphy - Application wide Roboto
 * Dagger
 * IAB
 * Google Analytics
 * <p/>
 * Much of the billing logic is handled here to allow it to be used from any activity.
 */
public class NWEApplication extends Application {

    private static final String TAG = "NWEApplication";

    private static Tracker mTracker;

    public static IabHelper mHelper;
    private ApplicationComponent component;

    //Does the user have the premium upgrade?
    private static boolean isPremium = false;

    private static final String SKU_PREMIUM = BuildConfig.DEBUG ? "android.test.purchased" : "upgrade";


    @Override
    public void onCreate() {
        super.onCreate();

        //Fabric
        final Fabric fabric = new Fabric.Builder(this)
                .kits(new Crashlytics(), new MoPub())
                .build();
        Fabric.with(fabric);

        //Calligraphy
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath(getString(R.string.font_roboto_regular))
                .setFontAttrId(R.attr.fontPath)
                .build());

        //Dagger
        buildComponentAndInject();

        //Billing
        final String base64EncodedPublicKey = getString(R.string.IAPKEY1)
                + getString(R.string.IAPKEY2)
                + getString(R.string.IAPKEY3);

        mHelper = new IabHelper(this, base64EncodedPublicKey);
        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(IabResult result) {
                Log.d(TAG, "Setup finished");

                if (!result.isSuccess()) {
                    mHelper = null;
                    Log.d(TAG, "In-app Billing setup failed: " + result);
                    return;
                }

                //Have we been disposed of?
                if (mHelper == null) {
                    return;
                }

                Log.d(TAG, "In-app Billing is set up OK");
                mHelper.queryInventoryAsync(mGotInventoryListener);
            }
        });

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
     * Listener that's called when we finish querying the items we own
     */
    final IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
        public void onQueryInventoryFinished(IabResult result, Inventory inventory) {
            Log.d(TAG, "Query inventory finished.");

            // Have we been disposed of in the meantime? If so, quit.
            if (mHelper == null) return;

            // Is it a failure?
            if (result.isFailure()) {
                Log.d(TAG, "Failed to query inventory: " + result);
                return;
            }

            Log.d(TAG, "Query inventory was successful.");

            /*
             * Check for items we own. Notice that for each purchase, we check
             * the developer payload to see if it's correct! See
             * verifyDeveloperPayload().
             */

            // Do we have the premium upgrade?
            final Purchase premiumPurchase = inventory.getPurchase(SKU_PREMIUM);
            isPremium = (premiumPurchase != null && verifyDeveloperPayload(premiumPurchase));
            Log.d(TAG, "User is " + (isPremium ? "PREMIUM" : "NOT PREMIUM"));
        }
    };


    /**
     * Listener that's called when we have finished a purcahse
     */
    static final IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener
            = new IabHelper.OnIabPurchaseFinishedListener() {
        public void onIabPurchaseFinished(IabResult result, Purchase purchase) {

            Log.d(TAG, "Purchase finished: " + result + ", purchase: " + purchase);

            // if we were disposed of in the meantime, quit.
            if (mHelper == null) return;

            //Check if it failed
            if (result.isFailure()) {
                Log.d(TAG, "Error purchasing " + result);
                return;
            }

            //Validate the purchase
            if (!verifyDeveloperPayload(purchase)) {
                Log.d(TAG, "Error purchasing, Authenticity verification failed");
                return;
            }

            Log.d(TAG, "Purchase Successful");

            if (purchase.getSku().equals(SKU_PREMIUM)) {
                Log.d(TAG, "Purchase is premium upgrade. Congratulating user.");
                isPremium = true;
            }

        }
    };

    /**
     * Verifies the developer payload of a purchase.
     */
    static boolean verifyDeveloperPayload(Purchase p) {
        final String payload = p.getDeveloperPayload();

        /*
         * TODO: At the moment we're just going to ignore this
         * TODO: At some point we should implment
         * the same one that you sent when initiating the purchase.
         *
         * WARNING: Locally generating a random string when starting a purchase and
         * verifying it here might seem like a good approach, but this will fail in the
         * case where the user purchases an item on one device and then uses your app on
         * a different device, because on the other device you will not have access to the
         * random string you originally generated.
         *
         * So a good developer payload has these characteristics:
         *
         * 1. If two different users purchase an item, the payload is different between them,
         *    so that one user's purchase can't be replayed to another user.
         *
         * 2. The payload must be such that you can verify it even when the app wasn't the
         *    one who initiated the purchase flow (so that items purchased by the user on
         *    one device work on other devices owned by the user).
         *
         * Using your own server to store and verify developer payloads across app
         * installations is recommended.
         */

        return true;
    }

    /**
     * The upgrade method will display the PurchaseDialog to the user
     */
    public static void upgrade(final Activity activity, final int requestCode) {
        /* TODO: for security, generate your payload here for verification. See the comments on
         *        verifyDeveloperPayload() for more info. Since this is a SAMPLE, we just use
         *        an empty string, but on a production app you should carefully generate this. */
        String payload = "";

        if (mHelper != null) {
            mHelper.launchPurchaseFlow(activity, SKU_PREMIUM, requestCode, mPurchaseFinishedListener, payload);
        }
    }

    /**
     *
     */
    public static boolean handleActivityResult(int requestCode, int resultCode, Intent data) {
        return mHelper != null && mHelper.handleActivityResult(requestCode, resultCode, data);
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

    public static boolean isPremium() {
        return isPremium;
    }

    private void buildComponentAndInject() {
        component = ApplicationComponent.Initialiser.init(this);
        component.inject(this);
    }

    public ApplicationComponent component() {
        return component;
    }

}

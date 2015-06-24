package com.cooper.nwemail.ui.settings;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import com.cooper.nwemail.R;
import com.cooper.nwemail.application.ApplicationComponent;
import com.cooper.nwemail.application.NWEApplication;
import com.cooper.nwemail.dialog.ConfirmDialog;
import com.google.android.gms.analytics.GoogleAnalytics;

import javax.inject.Inject;

/**
 * The SettingsFragment displays a list of settings the user can interact with
 */
public class SettingsFragment extends PreferenceFragment implements SettingsView {

    @Inject
    SettingsPresenterImpl presenter;

    private SettingsActivity mActivity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = (SettingsActivity) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupComponent(NWEApplication.get(getActivity()).component());

        addPreferencesFromResource(R.xml.pref_general);

        //Data / Cache
        final Preference clearCache = findPreference("clearCache");
        clearCache.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(final Preference preference) {
                final ConfirmDialog confirmDialog =
                        ConfirmDialog.newInstance(R.string.clear_cache_title, R.string.clear_cache_text);
                confirmDialog.show(mActivity.getSupportFragmentManager(), ConfirmDialog.TAG);
                confirmDialog.setOnClickListener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == DialogInterface.BUTTON_POSITIVE) {
                            presenter.onClearCache();
                        }
                        dialog.dismiss();
                    }
                });
                return true;
            }
        });

        /** Privacy **/
        final CheckBoxPreference shareStatistics = (CheckBoxPreference) findPreference("shareStatistics");
        shareStatistics.setOnPreferenceChangeListener(new CheckBoxPreference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                GoogleAnalytics.getInstance(getActivity()).setAppOptOut((Boolean) newValue);
                return true;
            }
        });

        //About app
        try {
            final Context context = getActivity();
            final PackageInfo packageInfo =
                    context.getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);

            final Preference aboutPreference = findPreference("aboutApp");
            aboutPreference.setSummary(packageInfo.versionName);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    protected void setupComponent(ApplicationComponent applicationComponent) {
        DaggerSettingsComponent.builder()
                .applicationComponent(applicationComponent)
                .settingsModule(new SettingsModule(this))
                .build()
                .inject(this);
    }


    @Override
    public void showLoading() {
        //Ignore
    }

    @Override
    public void hideLoading() {
        //Ignore
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
    }
}
package com.cooper.nwemail.ui.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.cooper.nwemail.R;
import com.cooper.nwemail.application.ApplicationComponent;
import com.cooper.nwemail.application.NWEApplication;
import com.cooper.nwemail.bus.AnyThreadBus;
import com.pnikosis.materialishprogress.ProgressWheel;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.Optional;

/**
 * All fragments that follow MVP need to extend this fragment.
 * Takes care of dependency injection
 */
public abstract class BaseFragment extends Fragment {

    protected abstract void setupComponent(final ApplicationComponent applicationComponent);

    protected abstract int getLayout();

    @Optional
    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    @Inject
    AnyThreadBus mBus;

    @Optional
    @InjectView(R.id.progressBar_loading)
    public ProgressWheel mProgress;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupComponent(NWEApplication.get(getActivity()).component());
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mBus != null) {
            mBus.register(this);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(getLayout(), container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (toolbar != null) {
            ((BaseActivity) getActivity()).setSupportActionBar(toolbar);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mBus != null) {
            mBus.unregister(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
    }
}
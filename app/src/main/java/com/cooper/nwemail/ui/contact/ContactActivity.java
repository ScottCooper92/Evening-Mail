package com.cooper.nwemail.ui.contact;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;

import com.cooper.nwemail.R;
import com.cooper.nwemail.adapter.ContactListAdapter;
import com.cooper.nwemail.application.ApplicationComponent;
import com.cooper.nwemail.bus.events.ContactRowClickedEvent;
import com.cooper.nwemail.enums.ContactTypeEnum;
import com.cooper.nwemail.ui.common.BaseActivity;
import com.cooper.nwemail.utils.ContactUtils;
import com.squareup.otto.Subscribe;

import butterknife.InjectView;

public class ContactActivity extends BaseActivity {

    @InjectView(R.id.recyclerview_contact)
    RecyclerView mRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        //Set the correct layout manager based on device + orientation
        RecyclerView.LayoutManager layoutManager;
        final Resources resources = getResources();
        if (resources.getBoolean(R.bool.isTablet)) {
            int cols = resources.getInteger(R.integer.contact_column_span);
            layoutManager = new StaggeredGridLayoutManager(cols, LinearLayoutManager.VERTICAL);
        } else {
            layoutManager = new LinearLayoutManager(this);
        }

        final ContactListAdapter adapter =
                new ContactListAdapter(this, mBus, ContactUtils.createContactList(this));

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(adapter);
    }

    @Subscribe
    public void onContactRowClicked(final ContactRowClickedEvent event) {
        final Intent intent = ContactTypeEnum.createIntent(event.getMethods());
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    protected void setupComponent(ApplicationComponent applicationComponent) {
        mBus = applicationComponent.bus();
    }
}

package com.cooper.nwemail.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cooper.nwemail.R;
import com.cooper.nwemail.bus.AnyThreadBus;
import com.cooper.nwemail.bus.events.ContactRowClickedEvent;
import com.cooper.nwemail.enums.ContactTypeEnum;
import com.cooper.nwemail.models.ContactMethod;
import com.cooper.nwemail.models.ContactMethodType;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Adapter for the ContactList in the ContactActivity
 */
public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ContactView> {

    private Context context;
    private AnyThreadBus mBus;
    private List<ContactMethodType> methods;

    public ContactListAdapter(final Context context, final AnyThreadBus bus, final List<ContactMethodType> methods) {
        this.context = context;
        mBus = bus;
        this.methods = methods;
    }

    @Override
    public ContactView onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ContactView(LayoutInflater.from(context).inflate(R.layout.card_contact_list, parent, false));
    }

    @Override
    public void onBindViewHolder(ContactView holder, int position) {

        final ContactMethodType type = methods.get(position);
        holder.heading.setText(type.getHeader());
        holder.list.removeAllViews();

        for (final ContactMethod methods : type.getMethods()) {
            final ContactRow row = new ContactRow(context);
            row.header.setText(methods.heading);
            row.subheader.setText(methods.subheading);
            row.icon.setImageResource(ContactTypeEnum.getIcon(methods.mType));

            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mBus.post(new ContactRowClickedEvent(type.getContactType(), methods));
                }
            });

            holder.list.addView(row);
        }
    }

    @Override
    public int getItemCount() {
        return methods.size();
    }


    static class ContactView extends RecyclerView.ViewHolder {

        public LinearLayout cardRoot;
        public TextView heading;
        public LinearLayout list;

        public ContactView(View itemView) {
            super(itemView);
            cardRoot = (LinearLayout) itemView.findViewById(R.id.linearLayout_card_root);
            heading = (TextView) itemView.findViewById(R.id.textView_contact_list_title);
            list = (LinearLayout) itemView.findViewById(R.id.linearLayout_contact_list);
        }
    }

    static class ContactRow extends RelativeLayout {

        @InjectView(R.id.imageView_contact_icon)
        public ImageView icon;

        @InjectView(R.id.textView_contact_header)
        public TextView header;

        @InjectView(R.id.textView_contact_subheader)
        public TextView subheader;

        public ContactRow(Context context) {
            super(context);
            init(context);
        }

        public ContactRow(Context context, AttributeSet attrs) {
            super(context, attrs);
            init(context);
        }

        public ContactRow(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            init(context);
        }

        private void init(final Context context) {
            inflate(context, R.layout.row_contact, this);
            ButterKnife.inject(this);
        }
    }
}

package com.cooper.nwemail.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.cooper.nwemail.R;

/**
 * The ConfirmDialog takes presents the user with some information and asks
 * whether they want to confirm the action they are about to perform.
 * Use setOnClickListener to get a callback when one of the buttons is clicked.
 */
public class ConfirmDialog extends DialogFragment implements Dialog.OnClickListener {

    public static final String TAG = "ConfirmDialog";

    private static final String ARG_TITLE = "ARG_TITLE";
    private static final String ARG_TEXT = "ARG_TEXT";

    private Dialog.OnClickListener mListener;

    public ConfirmDialog() {
    }

    public static ConfirmDialog newInstance(final int title, final int text) {
        final ConfirmDialog confirmDialog = new ConfirmDialog();
        final Bundle bundle = new Bundle();

        bundle.putInt(ARG_TITLE, title);
        bundle.putInt(ARG_TEXT, text);

        confirmDialog.setArguments(bundle);
        return confirmDialog;
    }

    /**
     * Sets the listener for the buttons
     *
     * @param listener The listener for button clicks
     */
    public void setOnClickListener(final Dialog.OnClickListener listener) {
        mListener = listener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Bundle bundle = getArguments();
        final int title = bundle.getInt(ARG_TITLE);
        final int text = bundle.getInt(ARG_TEXT);

        return new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setMessage(text)
                .setPositiveButton(R.string.ok, this)
                .setNegativeButton(R.string.cancel, this)
                .create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (mListener != null) {
            mListener.onClick(dialog, which);
        }
    }
}

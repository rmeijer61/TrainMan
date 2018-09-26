package com.rmeijer.trainman;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;


public class AlertNotFoundFragment extends DialogFragment {

    public AlertNotFoundFragment() {
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
            .setTitle(R.string.not_found_msg_text)
            .setMessage(R.string.OK_to_continue_msg_text)
            .setPositiveButton(
                R.string.OK_msg_text,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        getActivity().finish();
                    }
                }
            )
            .create();
    }
}


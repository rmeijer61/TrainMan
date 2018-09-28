package com.rmeijer.trainman;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import java.util.Objects;


public class AlertSentFragment extends DialogFragment {

    private String mMsg = " ";
    private Dialog dialog;

    public AlertSentFragment() {
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        dialog = new AlertDialog.Builder(Objects.requireNonNull(getActivity()))
                .setTitle(R.string.alert_title)
                .setMessage(getMsg())
                .setPositiveButton(
                    R.string.OK_msg_text, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dismiss();
                            }
                    }
                )
                .create();
        return dialog;
    }

    public void setAlertMessage(String msg) {
        mMsg = msg;
    }

    public String getMsg() {
        return mMsg;
    }
}


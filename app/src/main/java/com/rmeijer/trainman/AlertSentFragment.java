package com.rmeijer.trainman;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;


public class AlertSentFragment extends DialogFragment {

    private String mMsg = " ";
    private Dialog dialog;

    public AlertSentFragment() {
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        dialog = new AlertDialog.Builder(getActivity())
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


/**
    public void showAlert (String alertMsg) {
 // TESTING

 String msg = mCustomer.getId().toString() + " " + mCustomer.getDate();
 AlertFragment alert = new AlertFragment();
 alert.setAlertMessage(msg);
 alert.show(fmAlert, "alert_tag");
 //

    }
*/
}


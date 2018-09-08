package com.rmeijer.trainman;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;


public class ConfirmFragment extends DialogFragment {

    public ConfirmFragment() {
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
            .setTitle(R.string.logoff_msg_text)
            .setMessage(R.string.are_you_sure_msg_text)
            .setNegativeButton(
                R.string.no,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dismiss();
                    }
                }
            )
            .setPositiveButton(
                R.string.yes,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // To exit and return, this works, but there is another option
                        getActivity().finish();
                        // But the following crashes. The intent cannot be properly configured
                        //Intent intent = new Intent(getActivity(), getClass());
                        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        //startActivity(intent);
                        //getActivity().finish();
                    }
                }
            )
            .create();
    }

}


package com.rmeijer.trainman;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import java.util.UUID;


public class SessionDeleteFragment extends DialogFragment {

    UUID mSessionId = null;

    public SessionDeleteFragment() {
    }

    public void setSessionId(UUID sessionId) {
        mSessionId = sessionId;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
            .setTitle(R.string.delete_customer_msg_text)
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
                        if (mSessionId != null) {
                            SessionStore.get(getActivity()).deleteSession(mSessionId);

                        }

                    }
                }
            )
            .create();
    }

}


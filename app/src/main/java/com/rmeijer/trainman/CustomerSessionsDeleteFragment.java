package com.rmeijer.trainman;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import java.util.Objects;
import java.util.UUID;


public class CustomerSessionsDeleteFragment extends DialogFragment {

    UUID mCustomerId = null;

    public CustomerSessionsDeleteFragment() {
    }

    public void setCustomerId(UUID customerId) {
        mCustomerId = customerId;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(Objects.requireNonNull(getActivity()))
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
                        if (mCustomerId != null) {
                            SessionStore.get(getActivity()).deleteCustomerSessions(mCustomerId);

                        }

                    }
                }
            )
            .create();
    }

}


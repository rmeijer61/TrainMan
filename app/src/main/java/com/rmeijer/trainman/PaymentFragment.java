package com.rmeijer.trainman;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class PaymentFragment extends Fragment {

    // 7.10 - Overriding Fragment.onCreate(Bundle)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // 7.11 - Overriding onCreateView(…)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_payment, container, false);

        // 5.6 - Wiring up the buttons
        // 7.12 - Wiring up the EditText widget
        // 7.13 - Setting Button text
        // 7.14 - Listening for CheckBox changes
        Button mPaymentCancelButton = v.findViewById(R.id.payment_cancel_button);
        mPaymentCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            int messageResId = R.string.cancel_button_text;
            Toast.makeText(getContext(), messageResId, Toast.LENGTH_SHORT).show();
            getActivity().finish();
            }
        });

        Button mPaymentSaveButton = v.findViewById(R.id.payment_save_button);
        mPaymentSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            int messageResId = R.string.save_button_text;
            Toast.makeText(getContext(), messageResId, Toast.LENGTH_SHORT).show();

            // Save data
            // here

            getActivity().finish();
            }
        });

        return v;

    }
}
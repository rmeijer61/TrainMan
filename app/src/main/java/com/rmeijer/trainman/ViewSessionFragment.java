package com.rmeijer.trainman;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class ViewSessionFragment extends Fragment {

    // 7.10 - Overriding Fragment.onCreate(Bundle)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Session mSession = new Session();
        int messageResId = R.string.OK_msg_text;
        Toast.makeText(getContext(), messageResId + ", View Session Info", Toast.LENGTH_SHORT).show();
    }

    // 7.11 - Overriding onCreateView(…)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_view_session, container, false);

        // 5.6 - Wiring up the buttons
        // 7.12 - Wiring up the EditText widget
        // 7.13 - Setting Button text
        // 7.14 - Listening for CheckBox changes

        Button mCompleteCustomerSessionButton = v.findViewById(R.id.complete_session_button);
        mCompleteCustomerSessionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Complete the session
                int messageResId = R.string.OK_msg_text;
                Toast.makeText(getContext(), messageResId + ", Session completed", Toast.LENGTH_SHORT).show();
                //

            }
        });

        Button mSignSessionButton = v.findViewById(R.id.sign_session_button);
        mSignSessionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Sign the session
                int messageResId = R.string.OK_msg_text;
                Toast.makeText(getContext(), messageResId + ", Sign Session", Toast.LENGTH_SHORT).show();

                // Start SignActivity
                Intent intent = new Intent(getActivity(), SignActivity.class);
                startActivity(intent);
            }
        });

        Button mProcessPaymentButton = v.findViewById(R.id.process_payment_button);
        mProcessPaymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Sign the session
                int messageResId = R.string.OK_msg_text;
                Toast.makeText(getContext(), messageResId + ", Process payment", Toast.LENGTH_SHORT).show();

                // Start SignActivity
                Intent intent = new Intent(getActivity(), PaymentActivity.class);
                startActivity(intent);
            }
        });


        Button mGenerateReceiptButton = v.findViewById(R.id.generate_receipt_button);
        mGenerateReceiptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Sign the session
                int messageResId = R.string.OK_msg_text;
                Toast.makeText(getContext(), messageResId + ", Generate receipt", Toast.LENGTH_SHORT).show();

                // Start SignActivity
                Intent intent = new Intent(getActivity(), ReceiptActivity.class);
                startActivity(intent);
            }
        });

        Button mCustomerCancelButton = v.findViewById(R.id.view_session_cancel_button);
        mCustomerCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int messageResId = R.string.cancel_button_text;
                Toast.makeText(getContext(), messageResId, Toast.LENGTH_SHORT).show();
                getActivity().finish();
            }
        });


        return v;

    }
}

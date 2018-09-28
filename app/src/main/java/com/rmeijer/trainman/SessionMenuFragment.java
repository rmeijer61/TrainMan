package com.rmeijer.trainman;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.Objects;
import java.util.UUID;

public class SessionMenuFragment extends Fragment {

    // Get the customer_id from the parent activity
    private static final String EXTRA_CUSTOMER_ID =
            "com.rmeijer.trainman.customer_id";

    // 10.6 - Writing a newInstance(UUID) method
    private static final String ARG_CUSTOMER_ID = "customer_id";

    // Get the session_id from the parent activity
    private static final String EXTRA_SESSION_ID =
            "com.rmeijer.trainman.session_id";

    // 10.6 - Writing a newInstance(UUID) method
    private static final String ARG_SESSION_ID = "session_id";

    // 7.10 - Overriding Fragment.onCreate(Bundle)
    private Customer mCustomer;

    private Session mSession;

    // 10.6 - Writing a newInstance(UUID) method
    public static SessionMenuFragment newInstance(UUID sessionId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_SESSION_ID, sessionId);

        SessionMenuFragment fragment = new SessionMenuFragment();
        fragment.setArguments(args);
        return fragment;
    }

    // 7.10 - Overriding Fragment.onCreate(Bundle)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    // 7.11 - Overriding onCreateView(…)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_session_menu, container, false);

        // 5.6 - Wiring up the buttons
        // 7.12 - Wiring up the EditText widget
        // 7.13 - Setting Button text
        // 7.14 - Listening for CheckBox changes

        Button mViewSessionButton = v.findViewById(R.id.session_menu_view_session_button);
        mViewSessionButton.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int messageResId = R.string.view_session_button_text;
                    Toast.makeText(getContext(), messageResId, Toast.LENGTH_SHORT).show();

                    // Get the extra from the pager activity intent
                    UUID customerId = (UUID) Objects.requireNonNull(getActivity()).getIntent().getSerializableExtra(EXTRA_CUSTOMER_ID);
                    UUID sessionId = (UUID) getActivity().getIntent().getSerializableExtra(EXTRA_SESSION_ID);
                    mSession = SessionStore.get(getActivity()).getSession(customerId, sessionId);

                    // Start CustomerViewActivity
                    Intent intent = new Intent(getActivity(), SessionViewActivity.class);

                    //intent.putExtra(ARG_AESSION_ID, sessionId);
                    intent.putExtra(EXTRA_CUSTOMER_ID, customerId);
                    intent.putExtra(EXTRA_SESSION_ID, sessionId);

                    startActivity(intent);
                }
            }
        );


        Button mSignSessionButton = v.findViewById(R.id.session_menu_sign_session_button);
        mSignSessionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Sign the session
                int messageResId = R.string.sign_session_button_text;
                Toast.makeText(getContext(), messageResId, Toast.LENGTH_SHORT).show();

                UUID customerId = (UUID) Objects.requireNonNull(getActivity()).getIntent().getSerializableExtra(EXTRA_CUSTOMER_ID);
                UUID sessionId = (UUID) getActivity().getIntent().getSerializableExtra(EXTRA_SESSION_ID);

                // Start SignSessionActivity
                Intent intent = new Intent(getActivity(), SignSessionActivity.class);
                intent.putExtra(EXTRA_CUSTOMER_ID, customerId);
                intent.putExtra(EXTRA_SESSION_ID, sessionId);
                startActivity(intent);
            }
        });

        Button mEnterPaymentButton = v.findViewById(R.id.session_menu_enter_payment_button);
        mEnterPaymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Sign the session
                int messageResId = R.string.enter_payment_button_text;
                Toast.makeText(getContext(), messageResId, Toast.LENGTH_SHORT).show();

                UUID customerId = (UUID) Objects.requireNonNull(getActivity()).getIntent().getSerializableExtra(EXTRA_CUSTOMER_ID);
                UUID sessionId = (UUID) getActivity().getIntent().getSerializableExtra(EXTRA_SESSION_ID);

                // Start SignSessionActivity
                Intent intent = new Intent(getActivity(), PaymentEnterActivity.class);
                intent.putExtra(EXTRA_CUSTOMER_ID, customerId);
                intent.putExtra(EXTRA_SESSION_ID, sessionId);
                startActivity(intent);
            }
        });

        Button mViewPaymentButton = v.findViewById(R.id.session_menu_view_payment_button);
        mViewPaymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Sign the session
                int messageResId = R.string.view_payment_button_text;
                Toast.makeText(getContext(), messageResId, Toast.LENGTH_SHORT).show();

                UUID customerId = (UUID) Objects.requireNonNull(getActivity()).getIntent().getSerializableExtra(EXTRA_CUSTOMER_ID);
                UUID sessionId = (UUID) getActivity().getIntent().getSerializableExtra(EXTRA_SESSION_ID);

                // Start SignSessionActivity
                Intent intent = new Intent(getActivity(), PaymentViewActivity.class);
                intent.putExtra(EXTRA_CUSTOMER_ID, customerId);
                intent.putExtra(EXTRA_SESSION_ID, sessionId);
                startActivity(intent);
            }
        });


        Button mGenerateReceiptButton = v.findViewById(R.id.session_menu_generate_receipt_button);
        mGenerateReceiptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Sign the session
                int messageResId = R.string.generate_receipt_button_text;
                Toast.makeText(getContext(), messageResId, Toast.LENGTH_SHORT).show();

                UUID customerId = (UUID) Objects.requireNonNull(getActivity()).getIntent().getSerializableExtra(EXTRA_CUSTOMER_ID);
                UUID sessionId = (UUID) getActivity().getIntent().getSerializableExtra(EXTRA_SESSION_ID);

                // Start SignSessionActivity
                Intent intent = new Intent(getActivity(), ReceiptActivity.class);
                intent.putExtra(EXTRA_CUSTOMER_ID, customerId);
                intent.putExtra(EXTRA_SESSION_ID, sessionId);
                startActivity(intent);
            }
        });

        Button mDoneButton = v.findViewById(R.id.session_menu_done_button);
        mDoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int messageResId = R.string.done_button_text;
                Toast.makeText(getContext(), messageResId, Toast.LENGTH_SHORT).show();

                Objects.requireNonNull(getActivity()).finish();
            }
        });



        // End of Menu *****************************************************************************
        return v;
    }

}

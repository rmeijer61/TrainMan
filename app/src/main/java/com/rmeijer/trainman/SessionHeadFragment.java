package com.rmeijer.trainman;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Objects;
import java.util.UUID;

public class SessionHeadFragment extends Fragment {

    // 11.3 - Creating newIntent
    private static final String EXTRA_CUSTOMER_ID =
            "com.rmeijer.trainman.customer_id";

    // 10.6 - Writing a newInstance(UUID) method
    private static final String ARG_CUSTOMER_ID = "customer_id";

    // 11.3 - Creating newIntent
    private static final String EXTRA_SESSION_ID =
            "com.rmeijer.trainman.session_id";

    // 10.6 - Writing a newInstance(UUID) method
    private static final String ARG_SESSION_ID = "session_id";

    // 7.10 - Overriding Fragment.onCreate(Bundle)
    private Customer mCustomer;
    private Session mSession;

    // 7.12 - Wiring up the EditText widget
    private TextView mNameField;
    private TextView mServiceField;

    // Customer Id field
    private TextView mIdField;


    // 10.6 - Writing a newInstance(UUID) method
    // Creates each instance of the fragment - one for each session
    public static SessionHeadFragment newInstance(UUID sessionId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_SESSION_ID, sessionId);

        SessionHeadFragment fragment = new SessionHeadFragment();
        fragment.setArguments(args);

        return fragment;
    }

    public void getSessionObject() {
        UUID sessionId;
        Activity test_activity = getActivity();
        if (test_activity != null) {
            Intent test_intent = getActivity().getIntent();
            if (test_intent != null) {
                sessionId = (UUID) Objects.requireNonNull(this.getArguments()).get(ARG_SESSION_ID);
                Log.v("SessionHead: ", "Found ARG_SESSION_ID: " + sessionId);
            }
        }
    }

    public void updateExtra(UUID sessionId) {

        // Get the session_id from the parent activity
        String EXTRA_SESSION_ID =
                "com.rmeijer.trainman.session_id";

        // 10.6 - Writing a newInstance(UUID) method
        String ARG_SESSION_ID = "session_id";

        Objects.requireNonNull(getActivity()).getIntent().putExtra(EXTRA_SESSION_ID, sessionId);
    }

    // 7.10 - Overriding Fragment.onCreate(Bundle)
    //@Override
    //public void onCreate(Bundle savedInstanceState) {
    //    super.onCreate(savedInstanceState);
    //    Customer mCustomerHead = new Customer();
    //}
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 10.4 - Retrieving the extra and fetching the Customer
        //Customer mCustomer = new Customer();

        // Get the extra from the activity intent
        //UUID customerId = (UUID) getActivity().getIntent().getSerializableExtra(EXTRA_CUSTOMER_ID);
        //mCustomer = CustomerStore.get(getActivity()).getCustomer(customerId);
    }

    // 7.11 - Overriding onCreateView(…)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_session_head, container, false);

        mCustomer = new Customer();
        mSession = new Session();

        UUID customerId = (UUID) Objects.requireNonNull(getActivity()).getIntent().getSerializableExtra(EXTRA_CUSTOMER_ID);
        Log.v("SessionHead: ", "Got Extra customer Id: " + customerId.toString());
        if (customerId != null) {
            mCustomer = CustomerStore.get(getActivity()).getCustomer(customerId);
            if (mCustomer != null) {
                Log.v("SessionHead: ", "Name " + mCustomer.getName());
                Log.v("SessionHead: ", "Customer Id " + mCustomer.getId().toString());
            } else {
                Log.v("SessionHead: ", "************Customer not found!****************");
            }
        } else {
            Log.v("SessionHead: ", "************Customer ID EXTRA not found!****************");
        }

        // 7.12 - Wiring up the EditText widget
        mNameField = (TextView) v.findViewById(R.id.session_head_customer_name);
        mNameField.setText(mCustomer.getName());

        // Will not have a service during EnterSession; Only with View Session
        //mServiceField = (TextView) v.findViewById(R.id.session_head_session_service);

        // 10.5 - Updating view objects
        //mServiceField.setText(mSession.getService());

        //*************************************
        return v;

    }

}

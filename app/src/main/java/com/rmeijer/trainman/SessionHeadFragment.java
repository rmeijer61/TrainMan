package com.rmeijer.trainman;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.UUID;

public class SessionHeadFragment extends Fragment {

    // 11.3 - Creating newIntent
    private static final String EXTRA_CUSTOMER_ID =
            "com.rmeijer.trainman.customer_id";

    // 10.6 - Writing a newInstance(UUID) method
    private static final String ARG_CUSTOMER_ID = "customer_id";

    // 7.10 - Overriding Fragment.onCreate(Bundle)
    private Customer mCustomer;

    // 7.12 - Wiring up the EditText widget
    private TextView mNameField;

    // Customer Id field
    private TextView mIdField;

    // 10.6 - Writing a newInstance(UUID) method
    // Creates each instance of the fragment - one for each session
    public static SessionHeadFragment newInstance(UUID customerId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_CUSTOMER_ID, customerId);

        SessionHeadFragment fragment = new SessionHeadFragment();
        fragment.setArguments(args);

        return fragment;
    }

    public void updateExtra(UUID customerId) {

        // Get the customer_id from the parent activity
        String EXTRA_CUSTOMER_ID =
                "com.rmeijer.trainman.customer_id";

        // 10.6 - Writing a newInstance(UUID) method
        String ARG_CUSTOMER_ID = "customer_id";

        getActivity().getIntent().putExtra(EXTRA_CUSTOMER_ID, customerId);
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
        UUID customerId = (UUID) getActivity().getIntent().getSerializableExtra(EXTRA_CUSTOMER_ID);
        mCustomer = CustomerStore.get(getActivity()).getCustomer(customerId);;
    }

    // 7.11 - Overriding onCreateView(…)
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_session_head, container, false);

        // 7.12 - Wiring up the EditText widget
        mNameField = (TextView) v.findViewById(R.id.session_head_customer_name);

        // 10.5 - Updating view objects
        mNameField.setText(mCustomer.getName());

        //*************************************
        return v;

    }

}

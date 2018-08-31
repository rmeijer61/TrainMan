package com.rmeijer.trainman;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.UUID;

public class CustomerHeadFragment extends Fragment {
    // 7.10 - Overriding Fragment.onCreate(Bundle)
    //@Override
    //public void onCreate(Bundle savedInstanceState) {
    //    super.onCreate(savedInstanceState);
    //    Customer mCustomerHead = new Customer();
    //}


    // 10.6 - Writing a newInstance(UUID) method
    private static final String ARG_CUSTOMER_ID = "customer_id";

    // 7.10 - Overriding Fragment.onCreate(Bundle)
    private Customer mCustomer;

    // 7.12 - Wiring up the EditText widget
    private EditText mNameField;

    // 10.6 - Writing a newInstance(UUID) method
    public static CustomerHeadFragment newInstance(UUID customerId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_CUSTOMER_ID, customerId);

        CustomerHeadFragment fragment = new CustomerHeadFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 10.4 - Retrieving the extra and fetching the Customer
        //Customer mCustomer = new Customer();

        // 10.8 - Getting customer ID from the arguments
        //UUID customerId = (UUID) getActivity().getIntent()
        //        .getSerializableExtra(Customer.EXTRA_CUSTOMER_ID);
        UUID customerId = (UUID) getArguments().getSerializable(ARG_CUSTOMER_ID);
        mCustomer = CustomerStore.get(getActivity()).getCustomer(customerId);

    }


    // 7.11 - Overriding onCreateView(…)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_customer_head, container, false);

        // 7.12 - Wiring up the EditText widget
        mNameField = (EditText) v.findViewById(R.id.customer_head_customer_name);

        // 10.5 - Updating view objects
        mNameField.setText(mCustomer.getName());

        return v;

    }
}

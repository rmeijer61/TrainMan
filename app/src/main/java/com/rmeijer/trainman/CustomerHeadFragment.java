package com.rmeijer.trainman;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.UUID;

import static android.widget.AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL;

public class CustomerHeadFragment extends Fragment {

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
    // Creates each instance of the fragment - one for each customer
    public static CustomerHeadFragment newInstance(UUID customerId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_CUSTOMER_ID, customerId);

        CustomerHeadFragment fragment = new CustomerHeadFragment();
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

        // TESTING
        //UUID check_customerid = (UUID) getActivity().getIntent().getSerializableExtra(EXTRA_CUSTOMER_ID);
        //FragmentManager fmAlert = getActivity().getSupportFragmentManager();
        //String msg = "Update CustomerHead Extra: " + check_customerid.toString();
        //AlertFragment alert = new AlertFragment();
        //alert.setAlertMessage(msg);
        //alert.show(fmAlert, "alert_tag");
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

        // 10.8 - Getting customer ID from the arguments
        //UUID customerId = (UUID) getActivity().getIntent()
        //        .getSerializableExtra(Customer.EXTRA_CUSTOMER_ID);
        UUID customerId = (UUID) getArguments().getSerializable(ARG_CUSTOMER_ID);
        mCustomer = CustomerStore.get(getActivity()).getCustomer(customerId);
    }

    // 7.11 - Overriding onCreateView(…)
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_customer_head, container, false);



        // 7.12 - Wiring up the EditText widget
        mNameField = (TextView) v.findViewById(R.id.customer_head_customer_name);

        // 10.5 - Updating view objects
        mNameField.setText(mCustomer.getName());

        // Customer id textview field
        mIdField = v.findViewById(R.id.customer_head_customer_id);
        mIdField.setText(mCustomer.getId().toString());
        mIdField.setTag(mCustomer.getId());

        //*************************************
        return v;

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.v("CustomerHead: ", "onResume*****************************************");
        Log.v("CustomerHead: ", "Customer: " + mCustomer.getId().toString());
        //Toast.makeText(getActivity(), "Focus changed: " + mCustomer.getId().toString(), Toast.LENGTH_SHORT).show();
        Log.v("CustomerHead: ", "*************************************************");
    }

}

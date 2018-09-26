package com.rmeijer.trainman;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

    // 17.6 - Adding callback interface
    private Callbacks mCallbacks;

    /**
     * Required interface for hosting activities
     */
    public interface Callbacks {
        void onCustomerSelected(Customer customer);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallbacks = (Callbacks) context;
        getCustomerObject();
        Log.v("CustomerHead: ", "onAttach - beg+++++++++++++++++++++++++++++++++++");
        if (mCustomer != null) {
            Log.v("CustomerHead: ", "Customer: " + mCustomer.getId().toString());
        }
        Log.v("CustomerHead: ", "onAttach - end+++++++++++++++++++++++++++++++++++");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    // end 17.6


    // 10.6 - Writing a newInstance(UUID) method
    // Creates each instance of the fragment - one for each customer
    public static CustomerHeadFragment newInstance(UUID customerId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_CUSTOMER_ID, customerId);

        CustomerHeadFragment fragment = new CustomerHeadFragment();
        fragment.setArguments(args);

        return fragment;
    }

    public void getCustomerObject() {
        UUID customerId = null;
        Activity test_activity = getActivity();
        if (test_activity != null) {
            Intent test_intent = getActivity().getIntent();
            if (test_intent != null) {
                //customerId = (UUID) getActivity().getIntent().getSerializableExtra(EXTRA_CUSTOMER_ID);
                //Log.v("CustomerHead: ", "Found EXTRA_CUSTOMER_ID: " + customerId);
                customerId = (UUID) this.getArguments().get(ARG_CUSTOMER_ID);
                Log.v("CustomerHead: ", "Found ARG_CUSTOMER_ID: " + customerId);
            }
        }

        if (customerId != null) {
            mCustomer = CustomerStore.get(getActivity()).getCustomer(customerId);
            if (mCustomer.getId() != null) {
                Log.v("CustomerHead: ", "Found customer: " + mCustomer.getId());
            } else {
                Log.v("CustomerHead: ", "Customer NOT FOUND! ");
            }
        } else {
            Log.v("CustomerHead: ", "getCustomerOnject****************************");
            Log.v("CustomerHead: ", "Customer extra not found!");
            Log.v("CustomerHead: ", "getCustomerOnject****************************");
            Toast.makeText(getActivity(), "Id extra not found or null!", Toast.LENGTH_SHORT).show();
        }
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
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            Log.v("CustomerHead: ", "Visible - beg************************************");
            // If not the first time (because can be null on first time)
            if (mCustomer != null) {
                getCustomerObject();
                updateExtra(mCustomer.getId());
            }

            Log.v("CustomerHead: ", "Visible - end************************************");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // Make sure the customer object is current
        getCustomerObject();
        Log.v("CustomerHead: ", "onResume*****************************************");
        if (mCustomer.getId() != null) {
            Log.v("CustomerHead: ", "Customer: " + mCustomer.getId().toString());
        }
        Log.v("CustomerHead: ", "*************************************************");
    }

}

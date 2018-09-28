package com.rmeijer.trainman;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.Objects;
import java.util.UUID;

public class CustomerMenuFragment extends Fragment {

    // Get the customer_id from the parent activity
    private static final String EXTRA_CUSTOMER_ID =
            "com.rmeijer.trainman.customer_id";

    // 10.6 - Writing a newInstance(UUID) method
    private static final String ARG_CUSTOMER_ID = "customer_id";

    // 7.10 - Overriding Fragment.onCreate(Bundle)
    private Customer mCustomer;


    // 10.6 - Writing a newInstance(UUID) method
    public static CustomerMenuFragment newInstance(UUID customerId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_CUSTOMER_ID, customerId);

        CustomerMenuFragment fragment = new CustomerMenuFragment();
        fragment.setArguments(args);
        return fragment;
    }

    // 7.10 - Overriding Fragment.onCreate(Bundle)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //mCustomerMenu = new CustomerMenu();

    }

    // 7.11 - Overriding onCreateView(…)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_customer_menu, container, false);

        // 5.6 - Wiring up the buttons
        // 7.12 - Wiring up the EditText widget
        // 7.13 - Setting Button text
        // 7.14 - Listening for CheckBox changes

        Button mViewCustomerButton = v.findViewById(R.id.view_customer_button);
        mViewCustomerButton.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int messageResId = R.string.view_customer_button_text;
                    Toast.makeText(getContext(), messageResId, Toast.LENGTH_SHORT).show();

                    // Get the extra from the pager activity intent
                    UUID customerId = (UUID) Objects.requireNonNull(getActivity()).getIntent().getSerializableExtra(EXTRA_CUSTOMER_ID);
                    Log.v("CustomerMenu: ", "Got Extra customer Id: " + customerId.toString());
                    mCustomer = CustomerStore.get(getActivity()).getCustomer(customerId);

                    // Start CustomerViewActivity
                    Intent intent = new Intent(getActivity(), CustomerViewActivity.class);
                    intent.putExtra(EXTRA_CUSTOMER_ID, customerId);
                    startActivity(intent);
                }
            }
        );

        Button mTakeCustomerPictureButton = v.findViewById(R.id.take_customer_picture_button);
        mTakeCustomerPictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            int messageResId = R.string.take_customer_picture_button_text;
            Toast.makeText(getContext(), messageResId, Toast.LENGTH_SHORT).show();

            UUID customerId = (UUID) Objects.requireNonNull(getActivity()).getIntent().getSerializableExtra(EXTRA_CUSTOMER_ID);
            Log.v("CustomerMenu: ", "Got Extra customer Id: " + customerId.toString());

            // Start PictureActivity
            Intent intent = new Intent(getActivity(), PictureActivity.class);
            intent.putExtra(EXTRA_CUSTOMER_ID, customerId);
            startActivity(intent);
            }
        });

        Button mViewCustomerSessionsButton = v.findViewById(R.id.customer_menu_view_sessions_button);
        mViewCustomerSessionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int messageResId = R.string.view_customer_sessions_button_text;
                Toast.makeText(getContext(), messageResId, Toast.LENGTH_SHORT).show();

                // Get the extra from the activity intent
                UUID customerId = (UUID) Objects.requireNonNull(getActivity()).getIntent().getSerializableExtra(EXTRA_CUSTOMER_ID);
                Log.v("CustomerMenu: ", "Got Extra customer Id: " + customerId.toString());

                // Start SessionActivity
                Intent intent = new Intent(getActivity(), SessionListActivity.class);
                intent.putExtra(EXTRA_CUSTOMER_ID, customerId);
                startActivity(intent);
            }
        });

        Button mEnterSessionButton = v.findViewById(R.id.enter_new_session_button);
        mEnterSessionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            int messageResId = R.string.enter_new_session_button_text;
            Toast.makeText(getContext(), messageResId, Toast.LENGTH_SHORT).show();

            // Get the extra from the activity intent
            UUID customerId = (UUID) Objects.requireNonNull(getActivity()).getIntent().getSerializableExtra(EXTRA_CUSTOMER_ID);
            Log.v("CustomerMenu: ", "Got Extra customer Id: " + customerId.toString());

            // Start SessionEnterActivity
            Intent intent = new Intent(getActivity(), SessionEnterActivity.class);
            intent.putExtra(EXTRA_CUSTOMER_ID, customerId);
            startActivity(intent);
            }
        });

        Button mViewCustomerPaymentsButton = v.findViewById(R.id.customer_menu_view_payments_button);
        mViewCustomerPaymentsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int messageResId = R.string.view_customer_sessions_button_text;
                Toast.makeText(getContext(), messageResId, Toast.LENGTH_SHORT).show();

                // Get the extra from the activity intent
                UUID customerId = (UUID) Objects.requireNonNull(getActivity()).getIntent().getSerializableExtra(EXTRA_CUSTOMER_ID);
                Log.v("CustomerMenu: ", "Got Extra customer Id: " + customerId.toString());

                // Start SessionActivity
                Intent intent = new Intent(getActivity(), PaymentListActivity.class);
                intent.putExtra(EXTRA_CUSTOMER_ID, customerId);
                startActivity(intent);
            }
        });

        Button mDeleteCustomerSessionsButton = v.findViewById(R.id.customer_menu_delete_sessions_button);
        mDeleteCustomerSessionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int messageResId = R.string.delete_customer_sessions_button_text;
                Toast.makeText(getContext(), messageResId, Toast.LENGTH_SHORT).show();

                FragmentManager fm = getFragmentManager();
                CustomerSessionsDeleteFragment dialog = new CustomerSessionsDeleteFragment();
                UUID customerId = (UUID) Objects.requireNonNull(getActivity()).getIntent().getSerializableExtra(EXTRA_CUSTOMER_ID);
                Log.v("CustomerMenu: ", "Got Extra customer Id: " + customerId.toString());
                dialog.setCustomerId(customerId);
                dialog.show(fm, null);
            }
        });

        Button mDoneButton = v.findViewById(R.id.customer_menu_done_button);
        mDoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            int messageResId = R.string.done_button_text;
            Toast.makeText(getContext(), messageResId, Toast.LENGTH_SHORT).show();

            Objects.requireNonNull(getActivity()).finish();
            }
        });

        //^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
        // Return View object
        //^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
        return v;
    }
}
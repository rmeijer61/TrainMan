package com.rmeijer.trainman;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

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
    public View onCreateView(LayoutInflater inflater,
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
                    UUID customerId = (UUID) getActivity().getIntent().getSerializableExtra(EXTRA_CUSTOMER_ID);
                    mCustomer = CustomerStore.get(getActivity()).getCustomer(customerId);

                    // Start ViewCustomerActivity
                    Intent intent = new Intent(getActivity(), ViewCustomerActivity.class);

                    //intent.putExtra(ARG_CUSTOMER_ID, customerId);
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

            // Start PictureActivity
            Intent intent = new Intent(getActivity(), PictureActivity.class);
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
            UUID customerId = (UUID) getActivity().getIntent().getSerializableExtra(EXTRA_CUSTOMER_ID);
            mCustomer = CustomerStore.get(getActivity()).getCustomer(customerId);;

            // Start EnterSessionActivity
            Intent intent = new Intent(getActivity(), EnterSessionActivity.class);

            //intent.putExtra(ARG_CUSTOMER_ID, customerId);
            intent.putExtra(EXTRA_CUSTOMER_ID, customerId);
            startActivity(intent);
            }
        });

        Button mViewSessionButton = v.findViewById(R.id.view_session_button);
        mViewSessionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            int messageResId = R.string.view_session_button_text;
            Toast.makeText(getContext(), messageResId, Toast.LENGTH_SHORT).show();

            // Start SessionActivity
            Intent intent = new Intent(getActivity(), ViewSessionActivity.class);
            startActivity(intent);
            }
        });

        Button mDoneButton = v.findViewById(R.id.customer_menu_done_button);
        mDoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            int messageResId = R.string.done_button_text;
            Toast.makeText(getContext(), messageResId, Toast.LENGTH_SHORT).show();

            getActivity().finish();
            }
        });

        // End of Menu *****************************************************************************
        return v;
    }

}

package com.rmeijer.trainman;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.UUID;

public class ViewCustomerFragment extends Fragment {

    // 11.3 - Creating newIntent
    private static final String EXTRA_CUSTOMER_ID =
            "com.rmeijer.trainman.customer_id";

    // 10.6 - Writing a newInstance(UUID) method
    private static final String ARG_CUSTOMER_ID = "customer_id";

    // 7.10 - Overriding Fragment.onCreate(Bundle)
    private Customer mCustomer;

    // 7.12 - Wiring up the EditText widget
    private EditText mNameField;

    // 10.6 - Writing a newInstance(UUID) method
    public static ViewCustomerFragment newInstance(UUID customerId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_CUSTOMER_ID, customerId);

        ViewCustomerFragment fragment = new ViewCustomerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 10.4 - Retrieving the extra and fetching the Customer
        //Customer mCustomer = new Customer();

        // 10.8 - Getting customer ID from the arguments
        UUID customerId = (UUID) getActivity().getIntent().getSerializableExtra(EXTRA_CUSTOMER_ID);

        mCustomer = CustomerStore.get(getActivity()).getCustomer(customerId);

        FragmentManager fmCustomerHead = getActivity().getSupportFragmentManager();

        // TESTING
        //UUID check_customerid = (UUID) getIntent().getSerializableExtra(EXTRA_CUSTOMER_ID);
        //UUID check_customerid = (UUID) getActivity().getIntent().getSerializableExtra(EXTRA_CUSTOMER_ID);

        // TESTING
        //UUID check_customerid = (UUID) getActivity().getIntent().getSerializableExtra(EXTRA_CUSTOMER_ID);
        //FragmentManager fmAlert = getActivity().getSupportFragmentManager();
        //String msg = "View Customer Extra: " + check_customerid.toString();
        //AlertFragment alert = new AlertFragment();
        //alert.setAlertMessage(msg);
        //alert.show(fmAlert, "alert_tag");
        //
    }

    // 7.11 - Overriding onCreateView(…)
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_view_customer, container, false);

        // 7.12 - Wiring up the EditText widget
        mNameField = (EditText) v.findViewById(R.id.view_customer_name);

        // 10.5 - Updating view objects
        mNameField.setText(mCustomer.getName());

        mNameField.addTextChangedListener(
            new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    // This space intentionally left blank
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    mCustomer.setName(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {
                    // This one too
                }
            }
        );

        // 5.6 - Wiring up the buttons
        // 7.12 - Wiring up the EditText widget
        // 7.13 - Setting Button text
        // 7.14 - Listening for CheckBox changes
        Button mCustomerTakePictureButton = v.findViewById(R.id.view_customer_take_picture_button);
        mCustomerTakePictureButton.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int messageResId = R.string.customer_take_picture_button_text;
                    Toast.makeText(getContext(), messageResId, Toast.LENGTH_SHORT).show();
                    getActivity().finish();
                }
            }
        );

        Button mCustomerCancelButton = v.findViewById(R.id.view_customer_cancel_button);
        mCustomerCancelButton.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int messageResId = R.string.cancel_button_text;
                    Toast.makeText(getContext(), messageResId, Toast.LENGTH_SHORT).show();
                    getActivity().finish();
                }
            }
        );

        Button mCustomerSaveButton = v.findViewById(R.id.view_customer_save_button);
        mCustomerSaveButton.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int messageResId = R.string.save_button_text;
                    Toast.makeText(getContext(), messageResId, Toast.LENGTH_SHORT).show();
                    // Save data
                    // here
                    getActivity().finish();
                }
            }
        );

        return v;

    }
}

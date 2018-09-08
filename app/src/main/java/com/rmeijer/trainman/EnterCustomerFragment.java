package com.rmeijer.trainman;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;
import java.util.UUID;

public class EnterCustomerFragment extends Fragment {

    // 10.6 - Writing a newInstance(UUID) method
    private static final String ARG_CUSTOMER_ID = "customer_id";

    // 12.3 - Showing your DialogFragment
    private static final String DIALOG_DATE_FM_TAG = "DialogDateFMTag";

    // 12.8 - Setting target fragment
    private static final int REQUEST_DATE = 0;

    // 7.10 - Overriding Fragment.onCreate(Bundle)
    private Customer mCustomer;

    // 7.12 - Wiring up the EditText widget
    private EditText mNameField;

    // 7.13 - Setting Button text
    private Button mBirthDateButton;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // New customer object
        Customer mCustomer = new Customer();
    }

    // 7.11 - Overriding onCreateView(…)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_enter_customer, container, false);

        // This fixed the null issue, but why?
        mCustomer = new Customer();

        // 7.12 - Wiring up the EditText widget
        mNameField = (EditText) v.findViewById(R.id.enter_customer_name);

        // TESTING
        //FragmentManager fmAlert = getFragmentManager();
        //String msg = "onCreateView: " + mCustomer.getDate().toString();
        //AlertFragment alert = new AlertFragment();
        //alert.setAlertMessage(msg);
        //alert.show(fmAlert, "alert_tag");
        //


        // 7.13 - Setting Button text
        // Date button
        mBirthDateButton = (Button) v.findViewById(R.id.enter_customer_birthdate_button);

        // 12.12 - Highlighting date button update
        // 12.13 - Cleaning up with updateDate()
        //updateDate();

        // 12.3 - Show DialogFragment
        //mDateButton.setEnabled(false);
        mBirthDateButton.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentManager manager = getFragmentManager();

                    // 12.6 - Adding call to newInstance()
                    //DatePickerFragment dialog = new DatePickerFragment();

                    //*************************************** Testing **************************
                    DatePickerFragment dialog = DatePickerFragment
                        .newInstance(new Date()); // TESTING
                        //.newInstance(mCustomer.getBirthDate());
                    // end 12.6

                    // 12.8 - Setting target fragment
                    dialog.setTargetFragment(EnterCustomerFragment.this, REQUEST_DATE);
                    // end 12.8

                    dialog.show(manager, DIALOG_DATE_FM_TAG);
                }
            }
        );
        // end 12.3


        // 5.6 - Wiring up the buttons
        // 7.12 - Wiring up the EditText widget
        // 7.13 - Setting Button text
        // 7.14 - Listening for CheckBox changes
        Button mCancelButton = v.findViewById(R.id.enter_customer_cancel_button);
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int messageResId = R.string.cancel_button_text;
                Toast.makeText(getContext(), messageResId, Toast.LENGTH_SHORT).show();
                getActivity().finish();
            }
        });

        Button mSaveButton = v.findViewById(R.id.enter_customer_save_button);
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int messageResId = R.string.save_button_text;
                Toast.makeText(getContext(), messageResId, Toast.LENGTH_SHORT).show();

                // Save data
                Customer mCustomer = new Customer();

                // Write to the log
                Log.v("EditText", mNameField.getText().toString());

                mCustomer.setName(mNameField.getText().toString());

                CustomerStore.get(getActivity()).addCustomer(mCustomer);

                //Intent intent = CustomerPagerActivity
                //        .newIntent(getActivity(), mCustomer.getId());
                //startActivity(intent);

                getActivity().finish();
            }
        });

        return v;

    }

    // 12.11 - Responding to the dialog
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        // Error message
        FragmentManager fmAlert = getFragmentManager();
        String msg = "onActivityResult: " + mCustomer.getId().toString() + " " + mCustomer.getDate();
        AlertFragment alert = new AlertFragment();
        // TESTING
        //alert.setAlertMessage(msg);
        //alert.show(fmAlert, "alert_tag");
        //

        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data
                    .getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            if (date == null) {
                alert.setAlertMessage("date is null");
                alert.show(fmAlert, "alert_tag");
                return;
            } else {
                mCustomer.setBirthDate(date);
            }

            // 12.12 - Highlighting date button update
            // 12.13 - Cleaning up with updateDate()
            updateBirthDate();
        }
    }
    // end 12.11

    // 12.12 - Extract date button update
    // 12.13 - Cleaning up with updateDate()
    private void updateBirthDate() {
        mBirthDateButton.setText(mCustomer.getBirthDate().toString());
    }
}
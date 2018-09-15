package com.rmeijer.trainman;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;

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
    private Spinner mGenderSpinner;
    private EditText mPhone1Field;
    private EditText mPhone2Field;
    private EditText mEmail1Field;
    private EditText mEmail2Field;
    private EditText mAddress1Field;
    private EditText mAddress2Field;
    private EditText mCityField;
    private EditText mStateField;
    private EditText mZipField;
    private EditText mNoteField;

    private DatePickerDialog.OnDateSetListener mDateSetListener;

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
        mNameField = (EditText) v.findViewById(R.id.enter_customer_name_edittext);


        // 7.13 - Setting Button text
        // Date button
        mBirthDateButton = (Button) v.findViewById(R.id.enter_customer_birthdate_button);
        mBirthDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calDate = Calendar.getInstance();
                int year = calDate.get(Calendar.YEAR);
                int month = calDate.get(Calendar.MONTH);
                int day = calDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        getContext(),
                        0,
                        mDateSetListener,
                        year, month, day);
                //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getDatePicker().setCalendarViewShown(false);
                dialog.show();

            }
        });
        //android.R.style.Theme_Material_Light_Dialog_NoActionBar_MinWidth

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month +1;
                Calendar calDate = Calendar.getInstance();
                calDate.set(year, month, day);
                String dateString = month + "/" + day + "/" + year;
                mBirthDateButton.setText(dateString);
                mCustomer.setBirthDate(calDate.getTime());
            }
        };

        mGenderSpinner = (Spinner) v.findViewById(R.id.enter_customer_gender_spinner);
        mPhone1Field = (EditText) v.findViewById(R.id.enter_customer_phone1_edittext);
        mPhone2Field = (EditText) v.findViewById(R.id.enter_customer_phone2_edittext);
        mEmail1Field = (EditText) v.findViewById(R.id.enter_customer_email1_edittext);
        mEmail2Field = (EditText) v.findViewById(R.id.enter_customer_email2_edittext);
        mAddress1Field = (EditText) v.findViewById(R.id.enter_customer_address1_edittext);
        mAddress2Field = (EditText) v.findViewById(R.id.enter_customer_address2_edittext);
        mCityField = (EditText) v.findViewById(R.id.enter_customer_city_edittext);
        mStateField = (EditText) v.findViewById(R.id.enter_customer_state_edittext);
        mZipField = (EditText) v.findViewById(R.id.enter_customer_zip_edittext);
        mNoteField = (EditText) v.findViewById(R.id.enter_customer_note_edittext);

        /* --------------------------------------------------------------------------- */
        // Cancel and Save Buttons
        /* --------------------------------------------------------------------------- */

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
                Customer customer = new Customer();

                customer.setName(mNameField.getText().toString());
                customer.setGender(mGenderSpinner.getSelectedItem().toString());
                customer.setPhone1(mPhone1Field.getText().toString());
                customer.setPhone2(mPhone2Field.getText().toString());
                customer.setEmail1(mEmail1Field.getText().toString());
                customer.setEmail2(mEmail2Field.getText().toString());
                customer.setAddress1(mAddress1Field.getText().toString());
                customer.setAddress2(mAddress2Field.getText().toString());
                customer.setCity(mCityField.getText().toString());
                customer.setState(mStateField.getText().toString());
                customer.setZip(mZipField.getText().toString());
                customer.setNote(mNoteField.getText().toString());

                // Write to the log
                Log.v("EnterCustomer: ", "Adding " + customer.getName() + "...");

                CustomerStore.get(getActivity()).addCustomer(customer);

                getActivity().finish();
            }
        });

        return v;

    }

    // 12.11 - Responding to the dialog
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intentData) {
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
            Calendar date = (Calendar) intentData
                    .getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            if (date == null) {
                Log.v("EnterCustomer: ", "onActivityResult: " + "Date is NULL!");
                return;
            } else {
                Log.v("EnterCustomer: ", "onActivityResult: " + "Got date " + date.toString());
                mCustomer.setBirthDate(date.getTime());
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
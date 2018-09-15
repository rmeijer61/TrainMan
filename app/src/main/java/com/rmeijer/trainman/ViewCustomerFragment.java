package com.rmeijer.trainman;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
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

    private Spinner mGenderSpinner;
    private Button mBirthDateButton;
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

        // Get the customer obhject from the customer store
        mCustomer = CustomerStore.get(getActivity()).getCustomer(customerId);

        //FragmentManager fmCustomerHead = getActivity().getSupportFragmentManager();
    }

    // 14.11 - Pushing updates
    // Customer instances get modified in ViewCustomerFragment
    // and will need to be written out when ViewCustomerFragment is done
    @Override
    public void onPause() {
        super.onPause();

        CustomerStore.get(getActivity())
                .updateCustomer(mCustomer);
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
        Log.v("ViewCustomer: ", "Customer: " + mCustomer.getName());
        if (mCustomer.getGender() != null) {
            Log.v("ViewCustomer: ", "Gender: " + mCustomer.getGender());
        }
        if (mCustomer.getBirthDate() != null) {
            Log.v("ViewCustomer: ", "Birthdate: " + mCustomer.getBirthDate().toString());
        }

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

        // https://stackoverflow.com/questions/10634180/how-to-set-spinner-default-by-its-value-instead-of-position
        // https://stackoverflow.com/questions/2390102/how-to-set-selected-item-of-spinner-by-value-not-by-position
        mGenderSpinner = (Spinner) v.findViewById(R.id.view_customer_gender_spinner);
        Log.v("ViewCustomer: ", "Gender: " + mCustomer.getGender());

        if (mCustomer.getGender() != null) {
            String mGender = mCustomer.getGender();
            if (mGender.substring(0,1).equals("M")) {
                mGenderSpinner.setSelection(1);
                Log.v("ViewCustomer: ", "Gender: " + mGenderSpinner.toString());
            } else if (mGender.substring(0,1).equals("F")) {
                mGenderSpinner.setSelection(2);
                Log.v("ViewCustomer: ", "Gender: " + mGenderSpinner.toString());
            } else if (mGender.substring(0,1).equals("O")) {
                mGenderSpinner.setSelection(3);
                Log.v("ViewCustomer: ", "Gender: " + mGenderSpinner.toString());
            } else mGenderSpinner.setSelection(0);
            Log.v("ViewCustomer: ", "Gender: " + mGenderSpinner.toString());
        } else {
            mGenderSpinner.setSelection(0);
            Log.v("ViewCustomer: ", "Gender: " + mGenderSpinner.toString());
        }

        mBirthDateButton = (Button) v.findViewById(R.id.view_customer_birthdate_button);
        if (mCustomer.getBirthDate() != null) {
            Log.v("ViewCustomer: ", "Birthdate: " + mCustomer.getBirthDate().toString());
            Calendar calDate = Calendar.getInstance();
            calDate.setTime(mCustomer.getBirthDate());
            int year = calDate.get(Calendar.YEAR);
            int month = calDate.get(Calendar.MONTH);
            int day = calDate.get(Calendar.DAY_OF_MONTH);
            String dateString = month + "/" + day + "/" + year;
            mBirthDateButton.setText(dateString);
        }
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

        mPhone1Field = (EditText) v.findViewById(R.id.view_customer_phone1_edittext);
        mPhone1Field.setText(mCustomer.getPhone1());
        mPhone1Field.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        // This space intentionally left blank
                    }
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        mCustomer.setPhone1(s.toString());
                    }
                    @Override
                    public void afterTextChanged(Editable s) {
                        // This one too
                    }
                }
        );
        mPhone2Field = (EditText) v.findViewById(R.id.view_customer_phone2_edittext);
        mPhone2Field.setText(mCustomer.getPhone2());
        mPhone2Field.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        // This space intentionally left blank
                    }
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        mCustomer.setPhone2(s.toString());
                    }
                    @Override
                    public void afterTextChanged(Editable s) {
                        // This one too
                    }
                }
        );
        mEmail1Field = (EditText) v.findViewById(R.id.view_customer_email1_edittext);
        mEmail1Field.setText(mCustomer.getEmail1());
        mEmail1Field.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        // This space intentionally left blank
                    }
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        mCustomer.setEmail1(s.toString());
                    }
                    @Override
                    public void afterTextChanged(Editable s) {
                        // This one too
                    }
                }
        );
        mEmail2Field = (EditText) v.findViewById(R.id.view_customer_email2_edittext);
        mEmail2Field.setText(mCustomer.getEmail2());
        mEmail2Field.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        // This space intentionally left blank
                    }
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        mCustomer.setEmail2(s.toString());
                    }
                    @Override
                    public void afterTextChanged(Editable s) {
                        // This one too
                    }
                }
        );
        mAddress1Field = (EditText) v.findViewById(R.id.view_customer_address1_edittext);
        mAddress1Field.setText(mCustomer.getAddress1());
        mAddress1Field.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        // This space intentionally left blank
                    }
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        mCustomer.setAddress1(s.toString());
                    }
                    @Override
                    public void afterTextChanged(Editable s) {
                        // This one too
                    }
                }
        );
        mAddress2Field = (EditText) v.findViewById(R.id.view_customer_address2_edittext);
        mAddress2Field.setText(mCustomer.getAddress2());
        mAddress2Field.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        // This space intentionally left blank
                    }
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        mCustomer.setAddress2(s.toString());
                    }
                    @Override
                    public void afterTextChanged(Editable s) {
                        // This one too
                    }
                }
        );
        mCityField = (EditText) v.findViewById(R.id.view_customer_city_edittext);
        mCityField.setText(mCustomer.getCity());
        mCityField.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        // This space intentionally left blank
                    }
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        mCustomer.setCity(s.toString());
                    }
                    @Override
                    public void afterTextChanged(Editable s) {
                        // This one too
                    }
                }
        );
        mStateField = (EditText) v.findViewById(R.id.view_customer_state_edittext);
        mStateField.setText(mCustomer.getState());
        mStateField.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        // This space intentionally left blank
                    }
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        mCustomer.setState(s.toString());
                    }
                    @Override
                    public void afterTextChanged(Editable s) {
                        // This one too
                    }
                }
        );
        mZipField = (EditText) v.findViewById(R.id.view_customer_zip_edittext);
        mZipField.setText(mCustomer.getZip());
        mZipField.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        // This space intentionally left blank
                    }
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        mCustomer.setZip(s.toString());
                    }
                    @Override
                    public void afterTextChanged(Editable s) {
                        // This one too
                    }
                }
        );
        mNoteField = (EditText) v.findViewById(R.id.view_customer_note_edittext);
        mNoteField.setText(mCustomer.getNote());
        mNoteField.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        // This space intentionally left blank
                    }
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        mCustomer.setNote(s.toString());
                    }
                    @Override
                    public void afterTextChanged(Editable s) {
                        // This one too
                    }
                }
        );
        /* -------------------------------------------------------------------------------- */
        // Picture, Delete, Save, and Cancel buttons
        /* -------------------------------------------------------------------------------- */

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

        Button mDeleteButton = v.findViewById(R.id.view_customer_delete_button);
        mDeleteButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int messageResId = R.string.delete_button_text;
                        Toast.makeText(getContext(), messageResId, Toast.LENGTH_SHORT).show();
                        FragmentManager fm = getFragmentManager();
                        DeleteCustomerFragment dialog = new DeleteCustomerFragment();
                        dialog.setCustomerId(mCustomer.getId());
                        dialog.show(fm, null);
                    }
                }
        );

        Button mSaveButton = v.findViewById(R.id.view_customer_save_button);
        mSaveButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int messageResId = R.string.save_button_text;
                        Toast.makeText(getContext(), messageResId, Toast.LENGTH_SHORT).show();

                        // Save data
                        //Customer customer = new Customer();

                        /*
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
                        */

                        // Write to the log
                        Log.v("ViewCustomer: ", "Updating " + mCustomer.getName() + "...");

                        CustomerStore.get(getActivity()).updateCustomer(mCustomer);
                        getActivity().finish();
                    }
                }
        );

        Button mCancelButton = v.findViewById(R.id.view_customer_cancel_button);
        mCancelButton.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int messageResId = R.string.cancel_button_text;
                    Toast.makeText(getContext(), messageResId, Toast.LENGTH_SHORT).show();
                    getActivity().finish();
                }
            }
        );

        return v;

    }

}

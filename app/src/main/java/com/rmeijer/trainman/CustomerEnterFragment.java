package com.rmeijer.trainman;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
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
import java.util.Objects;

public class CustomerEnterFragment extends Fragment {

    private String TAG = "EnterCustomer: ";

    //**********************************************************************************************
    // Objects and Variables
    //**********************************************************************************************
    private Customer mCustomer;
    private Button mBirthDateButton;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    //**********************************************************************************************
    // onCreate
    //**********************************************************************************************
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    //**********************************************************************************************
    // View objects
    //**********************************************************************************************
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_customer_enter, container, false);

        mCustomer = new Customer();

        //------------------------------------------------------------------------------------------
        // Customer Name
        //------------------------------------------------------------------------------------------
        EditText mNameField = v.findViewById(R.id.customer_name_edittext);
        if (mCustomer.getName() != null) {
            mNameField.setText(mCustomer.getName());
            Log.v(TAG, "Customer: " + mCustomer.getName());
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

        //------------------------------------------------------------------------------------------
        // Gender
        //------------------------------------------------------------------------------------------
        Spinner mGenderSpinner = v.findViewById(R.id.gender_spinner);
        if (mCustomer.getGender() != null) {
            String mGender = mCustomer.getGender();
            if (mGender.equals("Male")) {
                mGenderSpinner.setSelection(1);
                Log.v(TAG, "Gender: " + mGenderSpinner.toString());
            } else if (mGender.equals("Female")) {
                mGenderSpinner.setSelection(2);
                Log.v(TAG, "Gender: " + mGenderSpinner.toString());
            } else if (mGender.equals("Other")) {
                mGenderSpinner.setSelection(3);
                Log.v(TAG, "Gender: " + mGenderSpinner.toString());
            } else {
                mGenderSpinner.setSelection(0);
                Log.v(TAG, "Gender not selected");
            }
        } else {
            mGenderSpinner.setSelection(0);
            Log.v(TAG, "Gender is null.");
        }

        //------------------------------------------------------------------------------------------
        // Birth Date
        //------------------------------------------------------------------------------------------
        mBirthDateButton = v.findViewById(R.id.customer_birthdate_button);
        if (mCustomer.getBirthDate() != null) {
            Log.v(TAG, "Birthdate: " + mCustomer.getBirthDate().toString());
            Calendar calDate = Calendar.getInstance();
            calDate.setTime(mCustomer.getBirthDate());
            int year = calDate.get(Calendar.YEAR);
            int month = calDate.get(Calendar.MONTH);
            int day = calDate.get(Calendar.DAY_OF_MONTH);
            String dateString = (month+1) + "/" + day + "/" + year;
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
                        Objects.requireNonNull(getContext()),
                        0,
                        mDateSetListener,
                        year, month, day);
                dialog.getDatePicker().setCalendarViewShown(false);
                dialog.show();

            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // For display: month = month +1;
                Log.v(TAG, "onDateSet got birthdate " + (month+1) + "/" + day + "/" + year);
                Calendar calDate = Calendar.getInstance();
                calDate.set(year, month, day);
                String dateString = (month+1) + "/" + day + "/" + year;
                mBirthDateButton.setText(dateString);
                // Convert Calendar to jave.util.Date. Internally, the date is handled properly
                mCustomer.setBirthDate(calDate.getTime());
                Log.v(TAG, "onDateSet set birthdate " + mCustomer.getBirthDate().toString());
            }
        };

        //------------------------------------------------------------------------------------------
        // Phone1
        //------------------------------------------------------------------------------------------
        EditText mPhone1Field = v.findViewById(R.id.customer_phone1_edittext);
        if (mCustomer.getPhone1() != null) {
            mPhone1Field.setText(mCustomer.getPhone1());
        }
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

        //------------------------------------------------------------------------------------------
        // Phone2
        //------------------------------------------------------------------------------------------
        EditText mPhone2Field = v.findViewById(R.id.customer_phone2_edittext);
        if (mCustomer.getPhone2() != null) {
            mPhone2Field.setText(mCustomer.getPhone2());
        }
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

        //------------------------------------------------------------------------------------------
        // Email1
        //------------------------------------------------------------------------------------------
        EditText mEmail1Field = v.findViewById(R.id.customer_email1_edittext);
        if (mCustomer.getEmail1() != null) {
            mEmail1Field.setText(mCustomer.getEmail1());
        }
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

        //------------------------------------------------------------------------------------------
        // Email2
        //------------------------------------------------------------------------------------------
        EditText mEmail2Field = v.findViewById(R.id.customer_email2_edittext);
        if (mCustomer.getEmail2() != null) {
            mEmail2Field.setText(mCustomer.getEmail2());
        }
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

        //------------------------------------------------------------------------------------------
        // Address1
        //------------------------------------------------------------------------------------------
        EditText mAddress1Field = v.findViewById(R.id.customer_address1_edittext);
        if (mCustomer.getAddress1() != null) {
            mAddress1Field.setText(mCustomer.getAddress1());
        }
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

        //------------------------------------------------------------------------------------------
        // Address2
        //------------------------------------------------------------------------------------------
        EditText mAddress2Field = v.findViewById(R.id.customer_address2_edittext);
        if (mCustomer.getAddress2() != null) {
            mAddress2Field.setText(mCustomer.getAddress2());
        }
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

        //------------------------------------------------------------------------------------------
        // City
        //------------------------------------------------------------------------------------------
        EditText mCityField = v.findViewById(R.id.customer_city_edittext);
        if (mCustomer.getCity() != null) {
            mCityField.setText(mCustomer.getCity());
        }
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

        //------------------------------------------------------------------------------------------
        // State
        //------------------------------------------------------------------------------------------
        EditText mStateField = v.findViewById(R.id.customer_state_edittext);
        if (mCustomer.getState() != null) {
            mStateField.setText(mCustomer.getState());
        }
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

        //------------------------------------------------------------------------------------------
        // Zip
        //------------------------------------------------------------------------------------------
        EditText mZipField = v.findViewById(R.id.customer_zip_edittext);
        if (mCustomer.getZip() != null) {
            mZipField.setText(mCustomer.getZip());
        }
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

        //------------------------------------------------------------------------------------------
        // Note
        //------------------------------------------------------------------------------------------
        EditText mNoteField = v.findViewById(R.id.customer_note_edittext);
        if (mCustomer.getNote() != null) {
            mNoteField.setText(mCustomer.getNote());
        }
        mNoteField.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        // This space intentionally left blank
                    }
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        // This space intentionally left blank
                    }
                    @Override
                    public void afterTextChanged(Editable s) {
                        mCustomer.setNote(s.toString());
                    }
                }
        );

        //******************************************************************************************
        // Cancel and Save Buttons
        //******************************************************************************************

        Button mCancelButton = v.findViewById(R.id.enter_customer_cancel_button);
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int messageResId = R.string.cancel_button_text;
                Toast.makeText(getContext(), messageResId, Toast.LENGTH_SHORT).show();
                Objects.requireNonNull(getActivity()).finish();
            }
        });

        Button mSaveButton = v.findViewById(R.id.enter_customer_save_button);
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int messageResId = R.string.save_button_text;
                Toast.makeText(getContext(), messageResId, Toast.LENGTH_SHORT).show();

                // Write to the log
                Log.v(TAG, "Name " + mCustomer.getName());
                Log.v(TAG, "Gender " + mCustomer.getGender());
                Log.v(TAG, "Birthdate " + mCustomer.getBirthDate().toString());

                CustomerStore.get(getActivity()).addCustomer(mCustomer);

                Objects.requireNonNull(getActivity()).finish();
            }
        });

        return v;

    }
}
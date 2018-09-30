package com.rmeijer.trainman;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.util.Calendar;
import java.util.Objects;
import java.util.UUID;

public class CustomerViewFragment extends Fragment {

    private String TAG = "CustomerView: ";

    //**********************************************************************************************
    // Intent extras/arguments
    //**********************************************************************************************
    private static final String EXTRA_CUSTOMER_ID = "com.rmeijer.trainman.customer_id";
    private static final String ARG_CUSTOMER_ID = "customer_id";

    //**********************************************************************************************
    // Objects and Variables
    //**********************************************************************************************
    private Customer mCustomer;
    private Spinner mGenderSpinner;
    private Button mBirthDateButton;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private ImageView mPhotoImageView;
    private File mPhotoFile;

    //**********************************************************************************************
    // Called when creating a new instance of this fragment
    //**********************************************************************************************
    // 10.6 - Writing a newInstance(UUID) method
    public static CustomerViewFragment newInstance(UUID customerId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_CUSTOMER_ID, customerId);

        CustomerViewFragment fragment = new CustomerViewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    //**********************************************************************************************
    // onCreate
    //**********************************************************************************************
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    //**********************************************************************************************
    // onPause
    //**********************************************************************************************
    // 14.11 - Pushing updates
    // Customer instances get modified in CustomerViewFragment
    // and will need to be written out when CustomerViewFragment is done
    @Override
    public void onPause() {
        super.onPause();

        CustomerStore.get(getActivity())
                .updateCustomer(mCustomer);
    }

    //**********************************************************************************************
    // View objects
    //**********************************************************************************************
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_customer_view, container, false);

        //*****************************************************************************************
        // Get EXTRA value from the activity's intent
        // (Note: The fragment argument is also available)
        //*****************************************************************************************
        Context mContext = Objects.requireNonNull(getActivity()).getApplicationContext();
        // 11.3 - Creating newIntent - Get intent EXTRA
        final UUID customerId = (UUID) getActivity().getIntent().getSerializableExtra(EXTRA_CUSTOMER_ID);
        if (customerId != null) {
            CustomerStore sCustomerStore = CustomerStore.get(mContext);
            mCustomer = sCustomerStore.getCustomer(customerId);
            if (mCustomer != null) {
                Log.v( TAG, "Name " + mCustomer.getName());
                Log.v(TAG, "Customer Id " + mCustomer.getId().toString());
            } else {
                Log.v(TAG, "************Customer not found!****************");
                FragmentManager fmAlert = getFragmentManager();
                String msg = "Customer not found - application cannot find the customer in data store";
                AlertFragment alert = new AlertFragment();
            }
        } else {
            Log.v(TAG, "************Customer ID EXTRA not found!****************");
            FragmentManager fmAlert = getFragmentManager();
            String msg = "Customer Id not found - application cannot find the intent extra value";
            AlertFragment alert = new AlertFragment();
        }

        //------------------------------------------------------------------------------------------
        // Get Customer Picture
        //------------------------------------------------------------------------------------------
        mPhotoImageView = v.findViewById(R.id.customer_picture_imageview);

        mPhotoFile = CustomerStore.get(getActivity()).getPhotoFile(mCustomer);
        updatePhotoView();

        //------------------------------------------------------------------------------------------
        // Customer Name
        //------------------------------------------------------------------------------------------
        EditText mNameField = v.findViewById(R.id.view_customer_name);
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
        mGenderSpinner = v.findViewById(R.id.view_customer_gender_spinner);
        if (mCustomer.getGender() != null) {
            String mGender = mCustomer.getGender();
            switch (mGender) {
                case "Male":
                    mGenderSpinner.setSelection(1);
                    Log.v(TAG, "Gender: " + mGenderSpinner.getSelectedItem());
                    break;
                case "Female":
                    mGenderSpinner.setSelection(2);
                    Log.v(TAG, "Gender: " + mGenderSpinner.getSelectedItem());
                    break;
                case "Other":
                    mGenderSpinner.setSelection(3);
                    Log.v(TAG, "Gender: " + mGenderSpinner.getSelectedItem());
                    break;
                default:
                    mGenderSpinner.setSelection(0);
                    Log.v(TAG, "Gender not selected");
            }
        } else {
            mGenderSpinner.setSelection(0);
            Log.v(TAG, "Gender is null.");
        }

        mGenderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                mCustomer.setGender(mGenderSpinner.getSelectedItem().toString());
                Log.v(TAG, "Gender: " + mCustomer.getGender());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //------------------------------------------------------------------------------------------
        // Birth Date
        //------------------------------------------------------------------------------------------
        mBirthDateButton = v.findViewById(R.id.view_customer_birthdate_button);
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
                //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
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
        EditText mPhone1Field = v.findViewById(R.id.view_customer_phone1_edittext);
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
        EditText mPhone2Field = v.findViewById(R.id.view_customer_phone2_edittext);
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
        EditText mEmail1Field = v.findViewById(R.id.view_customer_email1_edittext);
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
        EditText mEmail2Field = v.findViewById(R.id.view_customer_email2_edittext);
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
        EditText mAddress1Field = v.findViewById(R.id.view_customer_address1_edittext);
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
        EditText mAddress2Field = v.findViewById(R.id.view_customer_address2_edittext);
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
        EditText mCityField = v.findViewById(R.id.view_customer_city_edittext);
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
        EditText mStateField = v.findViewById(R.id.view_customer_state_edittext);
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
        EditText mZipField = v.findViewById(R.id.view_customer_zip_edittext);
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
        EditText mNoteField = v.findViewById(R.id.view_customer_note_edittext);
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
        // Picture, Delete, Save, and Cancel buttons
        //******************************************************************************************

        Button mCustomerTakePictureButton = v.findViewById(R.id.view_customer_take_picture_button);
        mCustomerTakePictureButton.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int messageResId = R.string.take_customer_picture_button_text;
                    Toast.makeText(getContext(), messageResId, Toast.LENGTH_SHORT).show();

                    UUID customerId = (UUID) Objects.requireNonNull(getActivity()).getIntent().getSerializableExtra(EXTRA_CUSTOMER_ID);
                    Log.v(TAG, "Got Extra customer Id: " + customerId.toString());

                    // Start PictureActivity
                    Intent intent = new Intent(getActivity(), PictureActivity.class);
                    intent.putExtra(EXTRA_CUSTOMER_ID, customerId);
                    startActivity(intent);
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
                        CustomerDeleteFragment dialog = new CustomerDeleteFragment();
                        dialog.setCustomerId(mCustomer.getId());
                        dialog.show(Objects.requireNonNull(fm), null);
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

                        Log.v(TAG, "Updating " + mCustomer.getName() + "...");

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

        //******************************************************************************************
        // Return the View Object
        //******************************************************************************************
        return v;

    }

    // 16.11 - Updating mPhotoImageView
    private void updatePhotoView() {
        if (mPhotoFile == null || !mPhotoFile.exists()) {
            Log.v( TAG, "updatePhotoView: mPhotoFile is null!" );
            mPhotoImageView.setImageResource(R.drawable.nophoto);
        } else {
            Bitmap bitmap = PictureUtils.getScaledBitmap(
                    mPhotoFile.getPath(), Objects.requireNonNull(getActivity()));
            mPhotoImageView.setImageBitmap(bitmap);
            Log.v( TAG, "updatePhotoView Drawable: " + mPhotoImageView.getDrawable());
        }
    }

}

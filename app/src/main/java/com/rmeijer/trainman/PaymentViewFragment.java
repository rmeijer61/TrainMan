package com.rmeijer.trainman;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
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
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Objects;
import java.util.UUID;

import static java.lang.Double.valueOf;

public class PaymentViewFragment extends Fragment {

    // Logging Tag
    String TAG = "ViewPayment: ";

    //*********************************************************************************************
    // Intent Extras and/or Arguments
    //*********************************************************************************************
    // 11.3 - Creating newIntent
    private static final String EXTRA_CUSTOMER_ID =
            "com.rmeijer.trainman.customer_id";

    // 10.6 - Writing a newInstance(UUID) method
    private static final String ARG_CUSTOMER_ID = "customer_id";

    // 11.3 - Creating newIntent
    private static final String EXTRA_SESSION_ID =
            "com.rmeijer.trainman.session_id";

    // 10.6 - Writing a newInstance(UUID) method
    private static final String ARG_SESSION_ID = "session_id";

    // 12.3 - Showing your DialogFragment
    private static final String DIALOG_DATE_FM_TAG = "DialogDateFMTag";

    // 12.8 - Setting target fragment
    private static final int REQUEST_DATE = 0;

    //*********************************************************************************************
    // Intect extra/argument values
    //*********************************************************************************************
    private UUID customerId;
    private UUID sessionId;

    //*********************************************************************************************
    // Custom Objects
    //*********************************************************************************************
    private Customer mCustomer;
    private Session mSession;
    private Payment mPayment;

    //*********************************************************************************************
    // View Objects
    //*********************************************************************************************
    // 7.12 - Wiring up the EditText widget
    private Spinner mPayMethodSpinner;
    private EditText mCardNumberEditText;
    private Button mExpireDateButton;
    private EditText mSecurityCodeEditText;
    private EditText mAmountEditText;
    private Button mPayDateButton;
    private EditText mNameEditText;
    private EditText mAddress1EditText;
    private EditText mAddress2EditText;
    private EditText mCityEditText;
    private EditText mStateEditText;
    private EditText mZipEditText;
    
    private DatePickerDialog.OnDateSetListener mExpireDateSetListener;
    private DatePickerDialog.OnDateSetListener mPayDateSetListener;

    //*********************************************************************************************
    // Methods
    //*********************************************************************************************
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // 7.11 - Overriding onCreateView(…)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_payment, container, false);

        //getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        mPayment = new Payment();

        //*****************************************************************************************
        // Get EXTRA values
        //*****************************************************************************************
        Context mContext = Objects.requireNonNull(getActivity()).getApplicationContext();
        // 11.3 - Creating newIntent - Get intent EXTRA
        customerId = (UUID) getActivity().getIntent().getSerializableExtra(EXTRA_CUSTOMER_ID);
        if (customerId != null) {
            CustomerStore sCustomerStore = CustomerStore.get(mContext);
            mCustomer = sCustomerStore.getCustomer(customerId);
            if (mCustomer != null) {
                Log.v( TAG, "Name " + mCustomer.getName());
                Log.v(TAG, "Customer Id " + mCustomer.getId().toString());
            } else {
                Log.v(TAG, "************Customer not found!****************");
            }
        } else {
            Log.v(TAG, "************Customer ID EXTRA not found!****************");
        }

        sessionId = (UUID) getActivity().getIntent().getSerializableExtra(EXTRA_SESSION_ID);
        if (sessionId != null) {
            // ------------------------------------------------------------------------------------
            // Get the Session object from the Session store
            // ------------------------------------------------------------------------------------
            SessionStore sSessionStore = SessionStore.get(mContext);
            mSession = sSessionStore.getSession(customerId, sessionId);
            if (mSession != null) {
                Log.v(TAG, "Service " + mSession.getService());
                Log.v(TAG, "Session Id " + mSession.getId().toString());
            } else {
                Log.v(TAG, "************Session not found!****************");
            }
        } else {
            Log.v(TAG, "************Session ID EXTRA not found!****************");
        }

        //******************************************************************************************
        // Update new Payment object with Customer and Session Ids
        //******************************************************************************************

        if (sessionId != null) {
            Log.v(TAG, "Find the Payment object for session Id:  " + sessionId);
            PaymentStore sPaymentStore = PaymentStore.get(mContext);
            mPayment = sPaymentStore.getSessionPayment(sessionId);
            if (mPayment != null) {
                Log.v(TAG, "Found Payment object. Payment Id:  " + mPayment.getId());
                Log.v(TAG, "Customer Id:  " + mPayment.getCustomerId());
                Log.v(TAG, "Session Id:  " + mPayment.getSessionId());
            } else {
                Log.v(TAG, "Payment object is null for session Id: " + sessionId);
            }
        } else {
            Log.v(TAG, "*** Session Id is null! ***");
        }

        if (mPayment.getSessionId() == null) {
            FragmentManager fm = getFragmentManager();
            AlertNotFoundFragment dialog = new AlertNotFoundFragment();
            assert fm != null;
            dialog.show(fm, null);
        }

        //******************************************************************************************
        // Process view objects
        //******************************************************************************************

        //------------------------------------------------------------------------------------------
        // Pay Method
        //------------------------------------------------------------------------------------------
        mPayMethodSpinner = v.findViewById(R.id.payment_pay_method_spinner);
        if (mPayment.getPayMethod() != null) {
            String mPayMethod = mPayment.getPayMethod();
            if (mPayMethod.equals("Cash")) {
                mPayMethodSpinner.setSelection(1);
                Log.v(TAG, "PayMethod: " + mPayMethodSpinner.toString());
            } else if (mPayMethod.equals("Credit Card")) {
                mPayMethodSpinner.setSelection(2);
                Log.v(TAG, "PayMethod: " + mPayMethodSpinner.toString());
            } else if (mPayMethod.equals("PayPal")) {
                mPayMethodSpinner.setSelection(3);
                Log.v(TAG, "PayMethod: " + mPayMethodSpinner.toString());
            } else {
                mPayMethodSpinner.setSelection(0);
                Log.v(TAG, "PayMethod not selected");
            }
        } else {
            mPayMethodSpinner.setSelection(0);
            Log.v("TAG ", "PayMethod is null");
        }
        mPayMethodSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                mPayment.setPayMethod(mPayMethodSpinner.getSelectedItem().toString());
                Log.v(TAG, "PayMethod: " + mPayment.getPayMethod());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //------------------------------------------------------------------------------------------
        // Card Number
        //------------------------------------------------------------------------------------------
        mCardNumberEditText = v.findViewById(R.id.payment_card_number_edittext);
        // For view/edit; for enter function, object should be empty
        if (mPayment.getCardNumber() != null) {
            mCardNumberEditText.setText(mPayment.getCardNumber());
        }
        mCardNumberEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                Log.v(TAG, "onDateSet (EditText) cardNumber: " + mCardNumberEditText.getText());
                mPayment.setCardNumber(mCardNumberEditText.getText().toString());
                Log.v(TAG, "onDateSet (Session Object) cardNumber: " + mPayment.getCardNumber());
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        //------------------------------------------------------------------------------------------
        // Card Expire Date
        //------------------------------------------------------------------------------------------
        mExpireDateButton = v.findViewById(R.id.payment_card_expire_date_button);
        if (mPayment.getExpireDate() != null) {
            Log.v(TAG, "Expire date: " + mPayment.getExpireDate().toString());
            Calendar calDate = Calendar.getInstance();
            calDate.setTime(mPayment.getExpireDate());
            int year = calDate.get(Calendar.YEAR);
            int month = calDate.get(Calendar.MONTH);
            int day = calDate.get(Calendar.DAY_OF_MONTH);
            String dateString = (month+1) + "/" + day + "/" + year;
            mExpireDateButton.setText(dateString);
        }
        mExpireDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calDate = Calendar.getInstance();
                int year = calDate.get(Calendar.YEAR);
                int month = calDate.get(Calendar.MONTH);
                int day = calDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        Objects.requireNonNull(getContext()),
                        0,
                        mExpireDateSetListener,
                        year, month, day);
                dialog.getDatePicker().setCalendarViewShown(false);
                dialog.show();
            }
        });

        mExpireDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // For display: remember to add 1 to month: (month+1);
                Log.v(TAG, "onDateSet got expireDate " + (month+1) + "/" + day + "/" + year);
                Calendar calDate = Calendar.getInstance();
                calDate.set(year, month, day);
                String dateString = (month+1) + "/" + day + "/" + year;
                mExpireDateButton.setText(dateString);
                // Convert Calendat date to java.util.Date. getTime properly handles the month
                mPayment.setExpireDate(calDate.getTime());
                Log.v(TAG, "onDateSet set expireDate " + mPayment.getExpireDate().toString());
            }
        };

        //------------------------------------------------------------------------------------------
        // Card Security Code
        //------------------------------------------------------------------------------------------
        mSecurityCodeEditText = v.findViewById(R.id.payment_card_security_code_edittext);
        // For view/edit; for enter function, object should be empty
        if (mPayment.getSecurityCode() != null) {
            mSecurityCodeEditText.setText(mPayment.getSecurityCode());
        }
        mSecurityCodeEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                Log.v(TAG, "onDateSet (EditText) securityCode: " + mSecurityCodeEditText.getText());
                mPayment.setSecurityCode(mSecurityCodeEditText.getText().toString());
                Log.v(TAG, "onDateSet (Session Object) securityCode: " + mPayment.getSecurityCode());
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
        
        //------------------------------------------------------------------------------------------
        // Payment Amount (double)
        //------------------------------------------------------------------------------------------
        mAmountEditText = v.findViewById(R.id.payment_amount_edittext);
        // For view/edit; for enter function, object should be empty
        if (valueOf(mPayment.getAmount()) > 0.0) {
            mAmountEditText.setText((Double.valueOf(mPayment.getAmount()).toString()));
        }
        mAmountEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                Log.v(TAG, "onDateSet (EditText) amount: " + mAmountEditText.getText());
                mPayment.setAmount(Double.parseDouble(mAmountEditText.getText().toString()));
                Log.v(TAG, "onDateSet (Session Object) amount: " + valueOf(mPayment.getAmount()).toString());
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        //------------------------------------------------------------------------------------------
        // Payment Date
        //------------------------------------------------------------------------------------------
        mPayDateButton = v.findViewById(R.id.payment_pay_date_button);
        if (mPayment.getPayDate() != null) {
            Log.v(TAG, "Payment date: " + mPayment.getPayDate().toString());
            Calendar calDate = Calendar.getInstance();
            calDate.setTime(mPayment.getPayDate());
            int year = calDate.get(Calendar.YEAR);
            int month = calDate.get(Calendar.MONTH);
            int day = calDate.get(Calendar.DAY_OF_MONTH);
            String dateString = (month+1) + "/" + day + "/" + year;
            mPayDateButton.setText(dateString);
        }
        mPayDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calDate = Calendar.getInstance();
                int year = calDate.get(Calendar.YEAR);
                int month = calDate.get(Calendar.MONTH);
                int day = calDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        Objects.requireNonNull(getContext()),
                        0,
                        mPayDateSetListener,
                        year, month, day);
                dialog.getDatePicker().setCalendarViewShown(false);
                dialog.show();
            }
        });

        mPayDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // For display: remember to add 1 to month: (month+1);
                Log.v(TAG, "onDateSet got payDate " + (month+1) + "/" + day + "/" + year);
                Calendar calDate = Calendar.getInstance();
                calDate.set(year, month, day);
                String dateString = (month+1) + "/" + day + "/" + year;
                mPayDateButton.setText(dateString);
                // Convert Calendat date to java.util.Date. getTime properly handles the month
                mPayment.setPayDate(calDate.getTime());
                Log.v(TAG, "onDateSet set payDate " + mPayment.getPayDate().toString());
            }
        };
        
        //------------------------------------------------------------------------------------------
        // Bill To Name
        //------------------------------------------------------------------------------------------
        mNameEditText = v.findViewById(R.id.payment_bill_to_name_edittext);
        // For view/edit; for enter function, object should be empty
        if (mPayment.getName() != null) {
            mNameEditText.setText(mPayment.getName());
        }
        mNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                Log.v(TAG, "onDateSet (EditText) name: " + mNameEditText.getText());
                mPayment.setName(mNameEditText.getText().toString());
                Log.v(TAG, "onDateSet (Psyment Object) name: " + mPayment.getName());
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        //------------------------------------------------------------------------------------------
        // Bill To Address1
        //------------------------------------------------------------------------------------------
        mAddress1EditText = v.findViewById(R.id.payment_bill_to_address1_edittext);
        // For view/edit; for enter function, object should be empty
        if (mPayment.getAddress1() != null) {
            mAddress1EditText.setText(mPayment.getAddress1());
        }
        mAddress1EditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                Log.v(TAG, "onDateSet (EditText) address1: " + mAddress1EditText.getText());
                mPayment.setAddress1(mAddress1EditText.getText().toString());
                Log.v(TAG, "onDateSet (Psyment Object) address1: " + mPayment.getAddress1());
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        //------------------------------------------------------------------------------------------
        // Bill To Address2
        //------------------------------------------------------------------------------------------
        mAddress2EditText = v.findViewById(R.id.payment_bill_to_address2_edittext);
        // For view/edit; for enter function, object should be empty
        if (mPayment.getAddress2() != null) {
            mAddress2EditText.setText(mPayment.getAddress2());
        }
        mAddress2EditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                Log.v(TAG, "onDateSet (EditText) address2: " + mAddress2EditText.getText());
                mPayment.setAddress2(mAddress2EditText.getText().toString());
                Log.v(TAG, "onDateSet (Psyment Object) address2: " + mPayment.getAddress2());
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        //------------------------------------------------------------------------------------------
        // Bill To City
        //------------------------------------------------------------------------------------------
        mCityEditText = v.findViewById(R.id.payment_bill_to_city_edittext);
        // For view/edit; for enter function, object should be empty
        if (mPayment.getCity() != null) {
            mCityEditText.setText(mPayment.getCity());
        }
        mCityEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                Log.v(TAG, "onDateSet (EditText) city: " + mCityEditText.getText());
                mPayment.setCity(mCityEditText.getText().toString());
                Log.v(TAG, "onDateSet (Psyment Object) city: " + mPayment.getCity());
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        //------------------------------------------------------------------------------------------
        // Bill To State
        //------------------------------------------------------------------------------------------
        mStateEditText = v.findViewById(R.id.payment_bill_to_state_edittext);
        // For view/edit; for enter function, object should be empty
        if (mPayment.getState() != null) {
            mStateEditText.setText(mPayment.getState());
        }
        mStateEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                Log.v(TAG, "onDateSet (EditText) state: " + mStateEditText.getText());
                mPayment.setState(mStateEditText.getText().toString());
                Log.v(TAG, "onDateSet (Psyment Object) state: " + mPayment.getState());
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        //------------------------------------------------------------------------------------------
        // Bill To Zip
        //------------------------------------------------------------------------------------------
        mZipEditText = v.findViewById(R.id.payment_bill_to_zip_edittext);
        // For view/edit; for enter function, object should be empty
        if (mPayment.getZip() != null) {
            mZipEditText.setText(mPayment.getZip());
        }
        mZipEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                Log.v(TAG, "onDateSet (EditText) zip: " + mZipEditText.getText());
                mPayment.setZip(mZipEditText.getText().toString());
                Log.v(TAG, "onDateSet (Psyment Object) zip: " + mPayment.getZip());
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });       
       

        //******************************************************************************************
        // View Navigation: Cancel and Save Buttons
        //******************************************************************************************
        Button mBackButton = v.findViewById(R.id.back_button);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int messageResId = R.string.back_button_text;
                Toast.makeText(getContext(), messageResId, Toast.LENGTH_SHORT).show();
                getActivity().finish();
            }
        });

        Button mCancelButton = v.findViewById(R.id.cancel_button);
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int messageResId = R.string.cancel_button_text;
                Toast.makeText(getContext(), messageResId, Toast.LENGTH_SHORT).show();
                getActivity().finish();
            }
        });

        Button mSaveButton = v.findViewById(R.id.save_button);
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int messageResId = R.string.save_button_text;
                Toast.makeText(getContext(), messageResId, Toast.LENGTH_SHORT).show();

                // Write to the log
                Log.v(TAG, "Payment Id:    " + mPayment.getId().toString());
                Log.v(TAG, "Customer Id:   " + mPayment.getCustomerId().toString());
                Log.v(TAG, "Session Id:    " + mPayment.getSessionId().toString());
                Log.v(TAG, "PayMethod:     " + mPayment.getPayMethod());
                Log.v(TAG, "Card Number:   " + mPayment.getCardNumber());
                Log.v(TAG, "ExpireDate:    " + mPayment.getExpireDate().toString());
                Log.v(TAG, "Security Code: " + mPayment.getSecurityCode());
                Log.v(TAG, "Amount:        " + valueOf(mPayment.getAmount()));
                Log.v(TAG, "PayDate:       " + mPayment.getPayDate().toString());
                Log.v(TAG, "Name:          " + mPayment.getName());
                Log.v(TAG, "Address1:      " + mPayment.getAddress1());
                Log.v(TAG, "Address2:      " + mPayment.getAddress2());
                Log.v(TAG, "City:          " + mPayment.getCity());
                Log.v(TAG, "State:         " + mPayment.getState());
                Log.v(TAG, "Zip:           " + mPayment.getZip());

                PaymentStore.get(getActivity()).updatePayment(mPayment);

                getActivity().finish();
            }
        });

        //******************************************************************************************
        // Return the View object
        //******************************************************************************************
        return v;
    }

    //**********************************************************************************************
    // No longer used ???
    //**********************************************************************************************
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intentData) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        // Error message
        FragmentManager fmAlert = getFragmentManager();
        String msg = "onActivityResult: " + mPayment.getId().toString() + " " + mPayment.getDate();
        AlertFragment alert = new AlertFragment();
        // TESTING
        //alert.setAlertMessage(msg);
        //alert.show(fmAlert, "alert_tag");
        //

        if (requestCode == REQUEST_DATE) {
            Calendar date = (Calendar) intentData
                    .getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            if (date == null) {
                Log.v(TAG, "onActivityResult: " + "Date is NULL!");
                return;
            } else {
                Log.v(TAG, "onActivityResult: " + "Got date " + date.toString());
                mPayment.setPayDate(date.getTime());
            }

            // 12.12 - Highlighting date button update
            // 12.13 - Cleaning up with updateDate()
            updatePayDate();
        }
    }
    // end 12.11

    // 12.12 - Extract date button update
    // 12.13 - Cleaning up with updateDate()
    private void updatePayDate() {
        mPayDateButton.setText(mPayment.getPayDate().toString());
    }

}
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
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Objects;
import java.util.UUID;

public class SessionViewFragment extends Fragment {

    private String TAG = "SessionView: ";

    //**********************************************************************************************
    // Intent extras/arguments
    //**********************************************************************************************
    // 11.3 - Creating newIntent
    private static final String EXTRA_CUSTOMER_ID = "com.rmeijer.trainman.customer_id";

    // 10.6 - Writing a newInstance(UUID) method
    private static final String ARG_CUSTOMER_ID = "customer_id";

    // 11.3 - Creating newIntent
    private static final String EXTRA_SESSION_ID = "com.rmeijer.trainman.session_id";

    // 10.6 - Writing a newInstance(UUID) method
    private static final String ARG_SESSION_ID = "session_id";

    //**********************************************************************************************
    // Objects and Variables
    //**********************************************************************************************
    private Customer mCustomer;
    private Session mSession;
    private Spinner mServiceSpinner;
    private Button mSessionDateButton;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private EditText mDescrEditText;
    private CheckBox mCompletedCheckBox;
    private CheckBox mPaidCheckBox;
    private EditText mSignEditText;

    //**********************************************************************************************
    // 10.6 - Writing a newInstance(UUID) method
    // Creates each instance of the fragment - one for each session
    //**********************************************************************************************
    public static SessionViewFragment newInstance(UUID customerId, UUID sessionId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_CUSTOMER_ID, customerId);
        args.putSerializable(ARG_SESSION_ID, sessionId);

        SessionViewFragment fragment = new SessionViewFragment();
        fragment.setArguments(args);

        return fragment;
    }

    //**********************************************************************************************
    // Get Session object
    //**********************************************************************************************
    public void getSessionObject() {
        UUID sessionId;
        Activity test_activity = getActivity();
        if (test_activity != null) {
            Intent test_intent = getActivity().getIntent();
            if (test_intent != null) {
                sessionId = (UUID) this.getArguments().get(ARG_SESSION_ID);
                Log.v("SViewSession: ", "Found ARG_SESSION_ID: " + sessionId);
            }
        }
    }

    //**********************************************************************************************
    // 7.10 - Overriding Fragment.onCreate(Bundle)
    //**********************************************************************************************
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
    }

    //**********************************************************************************************
    // 7.11 - Overriding onCreateView(…)
    //**********************************************************************************************
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_session_view, container, false);

        mSession = new Session();

        //*****************************************************************************************
        // Get EXTRA values
        //*****************************************************************************************
        Context mContext = Objects.requireNonNull(getActivity()).getApplicationContext();
        // 11.3 - Creating newIntent - Get intent EXTRA
        final UUID customerId = (UUID) getActivity().getIntent().getSerializableExtra(EXTRA_CUSTOMER_ID);
        if (customerId != null) {
            CustomerStore sCustomerStore = CustomerStore.get(mContext);
            mCustomer = sCustomerStore.getCustomer(customerId);
            if (mCustomer != null) {
                Log.v(TAG, "Name " + mCustomer.getName());
                Log.v(TAG, "Customer Id " + mCustomer.getId().toString());
            } else {
                Log.v(TAG, "************Customer not found!****************");
            }
        } else {
            Log.v(TAG, "************Customer ID EXTRA not found!****************");
        }

        final UUID sessionId = (UUID) getActivity().getIntent().getSerializableExtra(EXTRA_SESSION_ID);
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

        Log.v(TAG, "(store) --> Session Id:   " + mSession.getId());
        Log.v(TAG, "(store) --> Customer Id:  " + mSession.getCustomerId());

        //*****************************************************************************************
        // Process view input
        //*****************************************************************************************
        mServiceSpinner = v.findViewById(R.id.view_session_service_spinner);
        if (mSession.getService() != null) {
            String mService = mSession.getService();
            switch (mService) {
                case "Standard":
                    mServiceSpinner.setSelection(1);
                    Log.v(TAG, "Service: " + mServiceSpinner.getSelectedItem());
                    break;
                case "Premium":
                    mServiceSpinner.setSelection(2);
                    Log.v(TAG, "Service: " + mServiceSpinner.getSelectedItem());
                    break;
                case "Other":
                    mServiceSpinner.setSelection(3);
                    Log.v(TAG, "Service: " + mServiceSpinner.getSelectedItem());
                    break;
                default:
                    mServiceSpinner.setSelection(0);
                    Log.v(TAG, "Service not selected");
            }
        } else {
            mServiceSpinner.setSelection(0);
            Log.v(TAG, "Service is null");
        }

        mServiceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                mSession.setService(mServiceSpinner.getSelectedItem().toString());
                Log.v(TAG, "Service: " + mSession.getService());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mSessionDateButton = v.findViewById(R.id.view_session_date_button);
        if (mSession.getSessionDate() != null) {
            Log.v(TAG, "Session date: " + mSession.getSessionDate().toString());
            Calendar calDate = Calendar.getInstance();
            calDate.setTime(mSession.getSessionDate());
            int year = calDate.get(Calendar.YEAR);
            int month = calDate.get(Calendar.MONTH);
            int day = calDate.get(Calendar.DAY_OF_MONTH);
            String dateString = (month+1) + "/" + day + "/" + year;
            mSessionDateButton.setText(dateString);
        }
        mSessionDateButton.setOnClickListener(new View.OnClickListener() {
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

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // For display: month = month +1;
                Log.v(TAG, "onDateSet got session date " + (month+1) + "/" + day + "/" + year);
                Calendar calDate = Calendar.getInstance();
                calDate.set(year, month, day);
                String dateString = (month+1) + "/" + day + "/" + year;
                mSessionDateButton.setText(dateString);
                // Convert Calendat date to java.util.Date. getTime properly handles the month
                mSession.setSessionDate(calDate.getTime());
                Log.v(TAG, "onDateSet set session date " + mSession.getSessionDate().toString());
            }
        };

        mDescrEditText = v.findViewById(R.id.view_session_descr_edittext);
        if (mSession.getDescr() != null) {
            mDescrEditText.setText(mSession.getDescr());
        }
        mDescrEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                Log.v(TAG, "onDateSet (EditText) descr: " + mDescrEditText.getText());
                mSession.setDescr(mDescrEditText.getText().toString());
                Log.v(TAG, "onDateSet (Session Object) descr: " + mSession.getDescr());
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        mCompletedCheckBox = v.findViewById(R.id.view_session_completed_checkbox);
        Log.v(TAG, "onDateSet (store) completed is " + mSession.isCompleted());
        mCompletedCheckBox.setChecked(mSession.isCompleted());
        Log.v(TAG, "onDateSet (CheckBox) completed is now " + mCompletedCheckBox.isChecked());
        mCompletedCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v(TAG, "onDateSet (CheckBox) completed is " + mCompletedCheckBox.isChecked());
                mSession.setCompleted(mCompletedCheckBox.isChecked());
                Log.v(TAG, "onDateSet (Session Object) completed is " + mSession.isCompleted());
            }
        });

        mPaidCheckBox = v.findViewById(R.id.view_session_paid_checkbox);
        Log.v(TAG, "onDateSet (store) paid is " + mSession.isPaid());
        mPaidCheckBox.setChecked(mSession.isPaid());
        Log.v(TAG, "onDateSet (CheckBox) paid is now " + mPaidCheckBox.isChecked());
        mPaidCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v(TAG, "onDateSet (CheckBox) paid is " + mPaidCheckBox.isChecked());
                mSession.setPaid(mPaidCheckBox.isChecked());
                Log.v(TAG, "onDateSet (Session Object) paid is " + mSession.isPaid());
            }
        });

        mSignEditText = v.findViewById(R.id.view_session_sign_edittext);
        if (mSession.getSign() != null) {
            mSignEditText.setText(mSession.getSign());
        }
        mSignEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                Log.v(TAG, "onDateSet (EditText) sign: " + mSignEditText.getText());
                mSession.setSign(mSignEditText.getText().toString());
                Log.v(TAG, "onDateSet (Session Object) sign: " + mSession.getSign());
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
        
        // 5.6 - Wiring up the buttons
        // 7.12 - Wiring up the EditText widget
        // 7.13 - Setting Button text
        // 7.14 - Listening for CheckBox changes

        Button mCancelButton = v.findViewById(R.id.view_session_cancel_button);
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int messageResId = R.string.cancel_button_text;
                Toast.makeText(getContext(), messageResId, Toast.LENGTH_SHORT).show();
                getActivity().finish();
            }
        });

        Button mDeleteSessionButton = v.findViewById(R.id.view_session_delete_button);
        mDeleteSessionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int messageResId = R.string.delete_button_text;
                Toast.makeText(getContext(), messageResId, Toast.LENGTH_SHORT).show();

                FragmentManager fm = getFragmentManager();
                SessionDeleteFragment dialog = new SessionDeleteFragment();
                dialog.setSessionId(mSession.getId());
                dialog.show(Objects.requireNonNull(fm), null);
            }
        });

        Button mSaveButton = v.findViewById(R.id.view_session_save_button);
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int messageResId = R.string.save_button_text;
                Toast.makeText(getContext(), messageResId, Toast.LENGTH_SHORT).show();

                Log.v(TAG, "Name:         " + mCustomer.getName());
                Log.v(TAG, "Customer Id:  " + mSession.getCustomerId());
                Log.v(TAG, "Session Id:   " + mSession.getId());
                Log.v(TAG, "Service:      " + mSession.getService());
                Log.v(TAG, "Session Date: " + mSession.getSessionDate().toString());
                Log.v(TAG, "Descr:        " + mSession.getDescr());
                Log.v(TAG, "Completed:    " + mSession.isCompleted());
                Log.v(TAG, "Paid:         " + mSession.isPaid());
                Log.v(TAG, "Sign:         " + mSession.getSign());

                SessionStore.get(getActivity()).updateSession(mSession);

                Intent intent = SessionPagerActivity.newIntent(getActivity().getApplicationContext(), customerId, sessionId);
                //Create an argument in the pager activity intent for the customer_id
                intent.putExtra(EXTRA_CUSTOMER_ID, customerId);
                intent.putExtra(ARG_CUSTOMER_ID, customerId);
                intent.putExtra(EXTRA_SESSION_ID, sessionId);
                intent.putExtra(ARG_SESSION_ID, sessionId);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        //******************************************************************************************
        // Return View object
        //******************************************************************************************
        return v;

    }
}

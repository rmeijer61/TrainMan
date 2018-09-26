package com.rmeijer.trainman;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
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
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;
import java.util.UUID;

public class SessionViewFragment extends Fragment {

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

    private Customer mCustomer;
    private Session mSession;

    private Spinner mServiceSpinner;
    private Button mSessionDateButton;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private EditText mDescrEditText;
    private CheckBox mCompletedCheckBox;
    private CheckBox mPaidCheckBox;
    private EditText mSignEditText;

    // 10.6 - Writing a newInstance(UUID) method
    // Creates each instance of the fragment - one for each session
    public static SessionViewFragment newInstance(UUID customerId, UUID sessionId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_CUSTOMER_ID, customerId);
        args.putSerializable(ARG_SESSION_ID, sessionId);

        SessionViewFragment fragment = new SessionViewFragment();
        fragment.setArguments(args);

        return fragment;
    }

    public void getSessionObject() {
        UUID sessionId = null;
        Activity test_activity = getActivity();
        if (test_activity != null) {
            Intent test_intent = getActivity().getIntent();
            if (test_intent != null) {
                sessionId = (UUID) this.getArguments().get(ARG_SESSION_ID);
                Log.v("SViewSession: ", "Found ARG_SESSION_ID: " + sessionId);
            }
        }
    }

    // 7.10 - Overriding Fragment.onCreate(Bundle)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Session mSession = new Session();
    }

    // 7.11 - Overriding onCreateView(…)
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_session_view, container, false);

        mSession = new Session();

        //*****************************************************************************************
        // Get EXTRA values
        //*****************************************************************************************
        Context mContext = getActivity().getApplicationContext();
        // 11.3 - Creating newIntent - Get intent EXTRA
        final UUID customerId = (UUID) getActivity().getIntent().getSerializableExtra(EXTRA_CUSTOMER_ID);
        if (customerId != null) {
            CustomerStore sCustomerStore = CustomerStore.get(mContext);
            mCustomer = sCustomerStore.getCustomer(customerId);
            if (mCustomer != null) {
                Log.v("ViewSession: ", "Name " + mCustomer.getName());
                Log.v("ViewSession: ", "Customer Id " + mCustomer.getId().toString());
            } else {
                Log.v("ViewSession: ", "************Customer not found!****************");
            }
        } else {
            Log.v("ViewSession: ", "************Customer ID EXTRA not found!****************");
        }

        final UUID sessionId = (UUID) getActivity().getIntent().getSerializableExtra(EXTRA_SESSION_ID);
        if (sessionId != null) {
            // ------------------------------------------------------------------------------------
            // Get the Session object from the Session store
            // ------------------------------------------------------------------------------------
            SessionStore sSessionStore = SessionStore.get(mContext);
            mSession = sSessionStore.getSession(customerId, sessionId);
            if (mSession != null) {
                Log.v("ViewSession: ", "Service " + mSession.getService());
                Log.v("ViewSession: ", "Session Id " + mSession.getId().toString());
            } else {
                Log.v("ViewSession: ", "************Session not found!****************");
            }
        } else {
            Log.v("ViewSession: ", "************Session ID EXTRA not found!****************");
        }

        Log.v("ViewSession: ", "(store) --> Session Id:   " + mSession.getId());
        Log.v("ViewSession: ", "(store) --> Customer Id:  " + mSession.getCustomerId());

        //*****************************************************************************************
        // Process view input
        //*****************************************************************************************
        mServiceSpinner = (Spinner) v.findViewById(R.id.view_session_service_spinner);
        if (mSession.getService() != null) {
            String mService = mSession.getService();
            if (mService.equals("Standard")) {
                mServiceSpinner.setSelection(1);
                Log.v("ViewSession: ", "Service: " + mServiceSpinner.toString());
            } else if (mService.equals("Premium")) {
                mServiceSpinner.setSelection(2);
                Log.v("ViewSession: ", "Service: " + mServiceSpinner.toString());
            } else if (mService.equals("Other")) {
                mServiceSpinner.setSelection(3);
                Log.v("ViewSession: ", "Service: " + mServiceSpinner.toString());
            } else {
                mServiceSpinner.setSelection(0);
                Log.v("ViewSession: ", "Service not selected");
            }
        } else {
            mServiceSpinner.setSelection(0);
            Log.v("ViewSession: ", "Service is null");
        }

        mServiceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                mSession.setService(mServiceSpinner.getSelectedItem().toString());
                Log.v("ViewSession: ", "Service: " + mSession.getService());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mSessionDateButton = (Button) v.findViewById(R.id.view_session_date_button);
        if (mSession.getSessionDate() != null) {
            Log.v("ViewSession: ", "Session date: " + mSession.getSessionDate().toString());
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
                Log.v("ViewSession: ", "onDateSet got session date " + (month+1) + "/" + day + "/" + year);
                Calendar calDate = Calendar.getInstance();
                calDate.set(year, month, day);
                String dateString = (month+1) + "/" + day + "/" + year;
                mSessionDateButton.setText(dateString);
                // Convert Calendat date to java.util.Date. getTime properly handles the month
                mSession.setSessionDate(calDate.getTime());
                Log.v("ViewSession: ", "onDateSet set session date " + mSession.getSessionDate().toString());
            }
        };

        mDescrEditText = (EditText) v.findViewById(R.id.view_session_descr_edittext);
        if (mSession.getDescr() != null) {
            mDescrEditText.setText(mSession.getDescr());
        }
        mDescrEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                Log.v("ViewSession: ", "onDateSet (EditText) descr: " + mDescrEditText.getText());
                mSession.setDescr(mDescrEditText.getText().toString());
                Log.v("ViewSession: ", "onDateSet (Session Object) descr: " + mSession.getDescr());
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        mCompletedCheckBox = (CheckBox) v.findViewById(R.id.view_session_completed_checkbox);
        Log.v("ViewSession: ", "onDateSet (store) completed is " + mSession.isCompleted());
        mCompletedCheckBox.setChecked(mSession.isCompleted());
        Log.v("ViewSession: ", "onDateSet (CheckBox) completed is now " + mCompletedCheckBox.isChecked());
        mCompletedCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("ViewSession: ", "onDateSet (CheckBox) completed is " + mCompletedCheckBox.isChecked());
                mSession.setCompleted(mCompletedCheckBox.isChecked());
                Log.v("ViewSession: ", "onDateSet (Session Object) completed is " + mSession.isCompleted());
            }
        });

        mPaidCheckBox = (CheckBox) v.findViewById(R.id.view_session_paid_checkbox);
        Log.v("ViewSession: ", "onDateSet (store) paid is " + mSession.isPaid());
        mPaidCheckBox.setChecked(mSession.isPaid());
        Log.v("ViewSession: ", "onDateSet (CheckBox) paid is now " + mPaidCheckBox.isChecked());
        mPaidCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("ViewSession: ", "onDateSet (CheckBox) paid is " + mPaidCheckBox.isChecked());
                mSession.setPaid(mPaidCheckBox.isChecked());
                Log.v("ViewSession: ", "onDateSet (Session Object) paid is " + mSession.isPaid());
            }
        });

        mSignEditText = (EditText) v.findViewById(R.id.view_session_sign_edittext);
        if (mSession.getSign() != null) {
            mSignEditText.setText(mSession.getSign());
        }
        mSignEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                Log.v("ViewSession: ", "onDateSet (EditText) sign: " + mSignEditText.getText());
                mSession.setSign(mSignEditText.getText().toString());
                Log.v("ViewSession: ", "onDateSet (Session Object) sign: " + mSession.getSign());
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
                dialog.show(fm, null);
            }
        });

        Button mSaveButton = v.findViewById(R.id.view_session_save_button);
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int messageResId = R.string.save_button_text;
                Toast.makeText(getContext(), messageResId, Toast.LENGTH_SHORT).show();

                Log.v("ViewSession: ", "Name:         " + mCustomer.getName());
                Log.v("ViewSession: ", "Customer Id:  " + mSession.getCustomerId());
                Log.v("ViewSession: ", "Session Id:   " + mSession.getId());
                Log.v("ViewSession: ", "Service:      " + mSession.getService());
                Log.v("ViewSession: ", "Session Date: " + mSession.getSessionDate().toString());
                Log.v("ViewSession: ", "Descr:        " + mSession.getDescr());
                Log.v("ViewSession: ", "Completed:    " + mSession.isCompleted());
                Log.v("ViewSession: ", "Paid:         " + mSession.isPaid());
                Log.v("ViewSession: ", "Sign:         " + mSession.getSign());

                SessionStore.get(getActivity()).updateSession(mSession);
                getActivity().finish();
            }
        });


        return v;

    }
}

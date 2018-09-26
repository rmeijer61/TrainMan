package com.rmeijer.trainman;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.UUID;

public class SignSessionFragment extends Fragment {

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

    private UUID customerId;
    private UUID sessionId;

    private Customer mCustomer;
    private Session mSession;

    private TextView mSessionServiceTextView;
    private TextView mSessionDateTextView;
    private EditText mSignEditText;

    // 10.6 - Writing a newInstance(UUID) method
    // Creates each instance of the fragment - one for each session
    public static SignSessionFragment newInstance(UUID customerId, UUID sessionId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_CUSTOMER_ID, customerId);
        args.putSerializable(ARG_SESSION_ID, sessionId);

        SignSessionFragment fragment = new SignSessionFragment();
        fragment.setArguments(args);

        return fragment;
    }

    // 7.10 - Overriding Fragment.onCreate(Bundle)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // 7.11 - Overriding onCreateView(…)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sign_session, container, false);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        mSession = new Session();

        //*****************************************************************************************
        // Get EXTRA values
        //*****************************************************************************************
        Context mContext = getActivity().getApplicationContext();
        // 11.3 - Creating newIntent - Get intent EXTRA
        customerId = (UUID) getActivity().getIntent().getSerializableExtra(EXTRA_CUSTOMER_ID);
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

        sessionId = (UUID) getActivity().getIntent().getSerializableExtra(EXTRA_SESSION_ID);
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


        mSessionServiceTextView = (TextView) v.findViewById(R.id.sign_session_service_textview);
        mSessionServiceTextView.setText(mSession.getService());

        Calendar calDate = Calendar.getInstance();
        calDate.setTime(mSession.getSessionDate());
        int year = calDate.get(Calendar.YEAR);
        int month = calDate.get(Calendar.MONTH);
        int day = calDate.get(Calendar.DAY_OF_MONTH);
        String dateString = (month+1) + "/" + day + "/" + year;
        mSessionDateTextView = (TextView) v.findViewById(R.id.sign_session_date_textview);
        mSessionDateTextView.setText(dateString);

        mSignEditText = (EditText) v.findViewById(R.id.sign_session_sign_edittext);
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

        //*****************************************************************************************
        // View Buttons
        //*****************************************************************************************
        // 5.6 - Wiring up the buttons
        // 7.12 - Wiring up the EditText widget
        // 7.13 - Setting Button text
        // 7.14 - Listening for CheckBox changes
        Button mCancelButton = v.findViewById(R.id.sign_session_cancel_button);
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int messageResId = R.string.cancel_button_text;
                Toast.makeText(getContext(), messageResId, Toast.LENGTH_SHORT).show();
                getActivity().finish();
            }
        });

        Button mConfirmButton = v.findViewById(R.id.sign_session_confirm_button);
        mConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int messageResId = R.string.save_button_text;
                Toast.makeText(getContext(), messageResId, Toast.LENGTH_SHORT).show();

                Log.v("SignSession: ", "Sign: " + mSession.getSign());

                SessionStore.get(getActivity()).updateSessionSign(mSession);

                //To confirm the result
                Context mContext = getActivity().getApplicationContext();
                SessionStore sSessionStore = SessionStore.get(mContext);
                Session newSession = sSessionStore.getSession(customerId, sessionId);
                Log.v("SignSession: ", "Confirm Sign: " + mSession.getSign());

                getActivity().finish();
            }
        });

        return v;

    }
}

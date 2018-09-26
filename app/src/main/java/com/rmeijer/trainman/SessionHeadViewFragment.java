package com.rmeijer.trainman;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.UUID;

public class SessionHeadViewFragment extends Fragment {

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

    private TextView mSessionServiceTextView;
    private TextView mSessionDateTextView;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    private Customer mCustomer;
    private Session mSession;

    private UUID customerId;
    private UUID sessionId;

    // 10.6 - Writing a newInstance(UUID) method
    // Creates each instance of the fragment - one for each session
    public static SessionHeadViewFragment newInstance(UUID customerId, UUID sessionId) {
        Log.v("ViewSessionHead: ", "newInstance: " + customerId.toString());
        Log.v("ViewSessionHead: ", "newInstance: " + sessionId.toString());
        Bundle args = new Bundle();
        args.putSerializable(ARG_CUSTOMER_ID, customerId);
        args.putSerializable(ARG_SESSION_ID, sessionId);

        SessionHeadViewFragment fragment = new SessionHeadViewFragment();
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
                Log.v("ViewSessionHead: ", "Found ARG_SESSION_ID: " + sessionId);
            }
        }
    }

    // 7.10 - Overriding Fragment.onCreate(Bundle)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Session mSession = new Session();
        int messageResId = R.string.OK_msg_text;
        Toast.makeText(getContext(), messageResId + ", View Session Info", Toast.LENGTH_SHORT).show();
    }

    // 7.11 - Overriding onCreateView(…)
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_session_head_view, container, false);

        mSession = new Session();

        //*****************************************************************************************
        // Get EXTRA values
        //*****************************************************************************************
        Context mContext = getActivity().getApplicationContext();
        // 11.3 - Creating newIntent - Get intent EXTRA
        customerId = (UUID) getActivity().getIntent().getSerializableExtra(EXTRA_CUSTOMER_ID);
        Log.v("ViewSessionHead: ", "Got Extra customer Id: " + customerId.toString());
        if (customerId != null) {
            CustomerStore sCustomerStore = CustomerStore.get(mContext);
            mCustomer = sCustomerStore.getCustomer(customerId);
            if (mCustomer != null) {
                Log.v("ViewSessionHead: ", "Name " + mCustomer.getName());
                Log.v("ViewSessionHead: ", "Customer Id " + mCustomer.getId().toString());
            } else {
                Log.v("ViewSessionHead: ", "************Customer not found!****************");
            }
        } else {
            Log.v("ViewSessionHead: ", "************Customer ID EXTRA not found!****************");
        }

        sessionId = (UUID) getActivity().getIntent().getSerializableExtra(EXTRA_SESSION_ID);
        Log.v("ViewSessionHead: ", "Got Extra session Id: " + sessionId.toString());
        if (sessionId != null) {
            // ------------------------------------------------------------------------------------
            // Get the Session object from the Session store
            // ------------------------------------------------------------------------------------
            SessionStore sSessionStore = SessionStore.get(mContext);
            mSession = sSessionStore.getSession(customerId, sessionId);
            if (mSession != null) {
                Log.v("ViewSessionHead: ", "Service " + mSession.getService());
                Log.v("ViewSessionHead: ", "Session Id " + mSession.getId().toString());
            } else {
                Log.v("ViewSessionHead: ", "************Session not found!****************");
                getActivity().finish();
            }
        } else {
            Log.v("ViewSessionHead: ", "************Session ID EXTRA not found!****************");
        }

        mSessionServiceTextView = (TextView) v.findViewById(R.id.view_session_head_service_textview);
        mSessionServiceTextView.setText(mSession.getService());

        Calendar calDate = Calendar.getInstance();
        calDate.setTime(mSession.getSessionDate());
        int year = calDate.get(Calendar.YEAR);
        int month = calDate.get(Calendar.MONTH);
        int day = calDate.get(Calendar.DAY_OF_MONTH);
        String dateString = (month+1) + "/" + day + "/" + year;
        mSessionDateTextView = (TextView) v.findViewById(R.id.view_session_head_date_textview);
        mSessionDateTextView.setText(dateString);
        return v;
    }
}

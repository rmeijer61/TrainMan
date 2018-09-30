package com.rmeijer.trainman;

import android.content.Context;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Objects;
import java.util.UUID;

public class ReceiptFragment extends Fragment {
    // Logging Tag
    String TAG = "EnterPayment: ";

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
    private TextView mNameTextView;
    private TextView mSessionServiceTextView;
    private TextView mSessionDateTextView;
    private TextView mAmountTextView;
    private TextView mPaymentDateTextView;
    private EditText mEmailEditText;
    private EditText mPrinterEditText;

    // 7.10 - Overriding Fragment.onCreate(Bundle)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //**********************************************************************************************
    // View object
    //**********************************************************************************************
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_receipt, container, false);

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
        // Update the Payment object
        //******************************************************************************************
        mPayment = new Payment();
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
            dialog.show(Objects.requireNonNull(fm), null);
        }

        //------------------------------------------------------------------------------------------
        // Customer Name
        //------------------------------------------------------------------------------------------
        mNameTextView = v.findViewById(R.id.receipt_customer_name_textview);
        mNameTextView.setText(mCustomer.getName());

        //------------------------------------------------------------------------------------------
        // Session Service
        //------------------------------------------------------------------------------------------
        mSessionServiceTextView = v.findViewById(R.id.receipt_session_service_textview);
        mSessionServiceTextView.setText(mSession.getService());

        //------------------------------------------------------------------------------------------
        // Session Date
        //------------------------------------------------------------------------------------------
        Calendar calDate = Calendar.getInstance();
        calDate.setTime(mSession.getSessionDate());
        int sessionYear = calDate.get(Calendar.YEAR);
        int sessionMonth = calDate.get(Calendar.MONTH);
        int sessionDay = calDate.get(Calendar.DAY_OF_MONTH);
        String sessionDateString = (sessionMonth+1) + "/" + sessionDay + "/" + sessionYear;
        mSessionDateTextView = v.findViewById(R.id.receipt_session_date_textview);
        mSessionDateTextView.setText(sessionDateString);

        //------------------------------------------------------------------------------------------
        // Payment Amount
        //------------------------------------------------------------------------------------------
        mAmountTextView = v.findViewById(R.id.receipt_payment_amount_textview);
        mAmountTextView.setText((Double.valueOf(mPayment.getAmount()).toString()));

        //------------------------------------------------------------------------------------------
        // Payment date
        //------------------------------------------------------------------------------------------
        Calendar calPayDate = Calendar.getInstance();
        calDate.setTime(mPayment.getPayDate());
        int paymentYear = calDate.get(Calendar.YEAR);
        int paymentMonth = calDate.get(Calendar.MONTH);
        int paymentDay = calDate.get(Calendar.DAY_OF_MONTH);
        String paymentDateString = (paymentMonth+1) + "/" + paymentDay + "/" + paymentYear;
        mPaymentDateTextView = (TextView) v.findViewById(R.id.receipt_payment_date_textview);
        mPaymentDateTextView.setText(paymentDateString);

        //------------------------------------------------------------------------------------------
        // Email
        //------------------------------------------------------------------------------------------
        mEmailEditText = v.findViewById(R.id.receipt_email_edittext);
        if (mCustomer != null) {
            if (mCustomer.getEmail1() != null) {
                mEmailEditText.setText(mCustomer.getEmail1());
            }
        }
        // For view/edit; for enter function, object should be empty
        mEmailEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                Log.v(TAG, "onDateSet (EditText) email: " + mEmailEditText.getText());
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        //------------------------------------------------------------------------------------------
        // Printer
        //------------------------------------------------------------------------------------------
        mPrinterEditText = (EditText) v.findViewById(R.id.receipt_printer_edittext);
        // For view/edit; for enter function, object should be empty
        mPrinterEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                Log.v(TAG, "onDateSet (EditText) printer: " + mPrinterEditText.getText());
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        //******************************************************************************************
        // Navigation Buttons
        //******************************************************************************************
        Button mEmailButton = v.findViewById(R.id.email_button);
        mEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int messageResId = R.string.receipt_email_msg_text;
                Toast.makeText(getContext(), messageResId, Toast.LENGTH_SHORT).show();

                FragmentManager fm = getFragmentManager();
                AlertSentFragment dialog = new AlertSentFragment();
                dialog.setAlertMessage("Email sent");
                dialog.show(Objects.requireNonNull(fm), null);
            }
        });

        Button mPrintButton = v.findViewById(R.id.print_button);
        mPrintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int messageResId = R.string.receipt_print_msg_text;
                Toast.makeText(getContext(), messageResId, Toast.LENGTH_SHORT).show();

                FragmentManager fm = getFragmentManager();
                AlertSentFragment dialog = new AlertSentFragment();
                dialog.setAlertMessage("Sending to printer");
                dialog.show(Objects.requireNonNull(fm), null);
            }
        });

        Button mDoneButton = v.findViewById(R.id.done_button);
        mDoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int messageResId = R.string.done_button_text;
                Toast.makeText(getContext(), messageResId, Toast.LENGTH_SHORT).show();
                getActivity().finish();
            }
        });

        return v;

    }
}

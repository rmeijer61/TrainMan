package com.rmeijer.trainman;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

public class SessionEnterFragment extends Fragment {

    // 11.3 - Creating newIntent
    private static final String EXTRA_CUSTOMER_ID =
            "com.rmeijer.trainman.customer_id";

    // 10.6 - Writing a newInstance(UUID) method
    private static final String ARG_CUSTOMER_ID = "customer_id";

    private Customer mCustomer;
    private Session mSession;
    private Spinner mServiceSpinner;
    private Button mSessionDateButton;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private EditText mDescr;
    private CheckBox mCompleted;
    private CheckBox mPaid;
    private EditText mSignEditText;

    // 7.10 - Overriding Fragment.onCreate(Bundle)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // 7.11 - Overriding onCreateView(…)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_session_enter, container, false);

        // Not working?
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        mSession = new Session();

        UUID customerId = (UUID) getActivity().getIntent().getSerializableExtra(EXTRA_CUSTOMER_ID);
        if (customerId != null) {
            mCustomer = CustomerStore.get(getActivity()).getCustomer(customerId);
            if (mCustomer != null) {
                Log.v("EnterSession: ", "Name " + mCustomer.getName());
                Log.v("EnterSession: ", "Customer Id " + mCustomer.getId().toString());
                mSession.setCustomerId(mCustomer.getId());
            } else {
                Log.v("EnterSession: ", "************Customer not found!****************");
            }
        } else {
            Log.v("EnterSession: ", "************Customer ID EXTRA not found!****************");
        }

        mServiceSpinner = (Spinner) v.findViewById(R.id.enter_session_service_spinner);
        mServiceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                mSession.setService(mServiceSpinner.getSelectedItem().toString());
                Log.v("EnterSession: ", "Service: " + mSession.getService());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mSessionDateButton = (Button) v.findViewById(R.id.enter_session_date_button);
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
        //android.R.style.Theme_Material_Light_Dialog_NoActionBar_MinWidth

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // For display: month = month +1;
                Log.v("EnterSession: ", "onDateSet got session date " + (month+1) + "/" + day + "/" + year);
                Calendar calDate = Calendar.getInstance();
                calDate.set(year, month, day);
                String dateString = (month+1) + "/" + day + "/" + year;
                mSessionDateButton.setText(dateString);
                // Convert Calendat date to java.util.Date. getTime properly handles the month
                mSession.setSessionDate(calDate.getTime());
                Log.v("EnterSession: ", "onDateSet set session date " + mSession.getSessionDate().toString());
            }
        };

        mDescr = (EditText) v.findViewById(R.id.enter_session_descr_edittext);
        mDescr.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                mSession.setDescr(mDescr.getText().toString());
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });


        mCompleted = (CheckBox) v.findViewById(R.id.enter_session_completed_checkbox);
        mCompleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSession.setCompleted(mCompleted.isChecked());
            }
        });


        mPaid = (CheckBox) v.findViewById(R.id.enter_session_paid_checkbox);
        mPaid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSession.setPaid(mPaid.isChecked());
            }
        });


        mSignEditText = (EditText) v.findViewById(R.id.enter_session_sign_edittext);
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
        Button mCancelButton = v.findViewById(R.id.enter_session_cancel_button);
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            int messageResId = R.string.cancel_button_text;
            Toast.makeText(getContext(), messageResId, Toast.LENGTH_SHORT).show();
            getActivity().finish();
            }
        });

        Button mSaveButton = v.findViewById(R.id.enter_session_save_button);
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            int messageResId = R.string.save_button_text;
            Toast.makeText(getContext(), messageResId, Toast.LENGTH_SHORT).show();

            Log.v("EnterSession: ", "Name:         " + mCustomer.getName());
            Log.v("EnterSession: ", "Service:      " + mSession.getService());
            Log.v("EnterSession: ", "Session Date: " + mSession.getSessionDate().toString());
            Log.v("EnterSession: ", "Descr:        " + mSession.getDescr());
            Log.v("EnterSession: ", "Completed:    " + mSession.isCompleted());
            Log.v("EnterSession: ", "Paid:         " + mSession.isPaid());
            Log.v("EnterSession: ", "Sign:         " + mSession.getSign());

            SessionStore.get(getActivity()).addSession(mSession);

            getActivity().finish();
            }
        });

        return v;

    }
}

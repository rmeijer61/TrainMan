package com.rmeijer.trainman;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import java.util.Calendar;

public class ViewSessionFragment extends Fragment {

    private Button mSessionDateButton;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private Session mSession;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_view_session, container, false);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        mSession = new Session();

        mSessionDateButton = (Button) v.findViewById(R.id.view_session_date_button);
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
                month = month +1;
                Calendar calDate = Calendar.getInstance();
                calDate.set(year, month, day);
                String dateString = month + "/" + day + "/" + year;
                mSessionDateButton.setText(dateString);
                mSession.setDate(calDate.getTime());
            }
        };

        // 5.6 - Wiring up the buttons
        // 7.12 - Wiring up the EditText widget
        // 7.13 - Setting Button text
        // 7.14 - Listening for CheckBox changes

        Button mCompleteCustomerSessionButton = v.findViewById(R.id.view_session_completed_checkbox);
        mCompleteCustomerSessionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Complete the session
                int messageResId = R.string.OK_msg_text;
                Toast.makeText(getContext(), messageResId + ", Session completed", Toast.LENGTH_SHORT).show();
                //

            }
        });

        Button mSignSessionButton = v.findViewById(R.id.sign_session_button);
        mSignSessionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Sign the session
                int messageResId = R.string.OK_msg_text;
                Toast.makeText(getContext(), messageResId + ", Sign Session", Toast.LENGTH_SHORT).show();

                // Start SignActivity
                Intent intent = new Intent(getActivity(), SignActivity.class);
                startActivity(intent);
            }
        });

        Button mProcessPaymentButton = v.findViewById(R.id.process_payment_button);
        mProcessPaymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Sign the session
                int messageResId = R.string.OK_msg_text;
                Toast.makeText(getContext(), messageResId + ", Process payment", Toast.LENGTH_SHORT).show();

                // Start SignActivity
                Intent intent = new Intent(getActivity(), PaymentActivity.class);
                startActivity(intent);
            }
        });


        Button mGenerateReceiptButton = v.findViewById(R.id.generate_receipt_button);
        mGenerateReceiptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Sign the session
                int messageResId = R.string.OK_msg_text;
                Toast.makeText(getContext(), messageResId + ", Generate receipt", Toast.LENGTH_SHORT).show();

                // Start SignActivity
                Intent intent = new Intent(getActivity(), ReceiptActivity.class);
                startActivity(intent);
            }
        });

        Button mCancelButton = v.findViewById(R.id.view_session_cancel_button);
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int messageResId = R.string.cancel_button_text;
                Toast.makeText(getContext(), messageResId, Toast.LENGTH_SHORT).show();
                getActivity().finish();
            }
        });

        Button mSaveButton = v.findViewById(R.id.view_session_save_button);
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int messageResId = R.string.save_button_text;
                Toast.makeText(getContext(), messageResId, Toast.LENGTH_SHORT).show();
                getActivity().finish();
            }
        });


        return v;

    }
}

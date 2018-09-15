package com.rmeijer.trainman;

import android.app.DatePickerDialog;
import android.media.tv.TvInputService;
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

public class EnterSessionFragment extends Fragment {

    private Button mSessionDateButton;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private Session mSession;

    // 7.10 - Overriding Fragment.onCreate(Bundle)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // 7.11 - Overriding onCreateView(…)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_enter_session, container, false);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        mSession = new Session();

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

            // Save data
            // here

            getActivity().finish();
            }
        });

        return v;

    }
}

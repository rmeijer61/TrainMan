package com.rmeijer.trainman;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class DatePickerFragment extends DialogFragment {
        //implements DatePickerDialog.OnDateSetListener {

    private DatePicker mDatePicker;

    // 12.9 - Calling back to your target
    public static final String EXTRA_DATE =
            "com.rmeijer.trainman.extra_date";

    // 12.5 - Adding a newInstance(Date) method
    private static final String ARG_DATE = "extra_date";

    public static DatePickerFragment newInstance(Date date) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATE, date);

        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // 12.4 - Adding DatePicker to AlertDialog
        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_date, null);
        // end 12.4

        // 12.7 - Extracting the date and initializing DatePicker

        Date date = (Date) Objects.requireNonNull(getArguments()).getSerializable(ARG_DATE);

        Calendar calDate = Calendar.getInstance();
        calDate.setTime(date);
        int year = calDate.get(Calendar.YEAR);
        int month = calDate.get(Calendar.MONTH);
        int day = calDate.get(Calendar.DAY_OF_MONTH);

        mDatePicker = v.findViewById(R.id.dialog_date_picker);
        mDatePicker.init(year, month, day, null);

        // Create a new instance of DatePickerDialog and return it
        return new AlertDialog.Builder(Objects.requireNonNull(getActivity()))
                .setTitle(R.string.date_picker_title)
                // 12.4 - Adding DatePicker to AlertDialog
                .setView(v)
                //

                // 12.10 - Are you OK?
                //.setPositiveButton(android.R.string.ok, null)
                .setPositiveButton(
                        android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                int year = mDatePicker.getYear();
                                int month = mDatePicker.getMonth();
                                int day = mDatePicker.getDayOfMonth();
                                Calendar calDate = Calendar.getInstance();
                                calDate.set(year, month, day);

                                // TESTING
                                //String alertMsg = "onCreateDialog: " + EXTRA_DATE + " " + date.toString();
                                //showAlert(alertMsg);

                                sendResult(Activity.RESULT_OK, calDate);
                            }
                        }
                )
                // end 12.10
                .create();


    }

    /*
    private DatePickerDialog mDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int day){
            final Calendar calDate = Calendar.getInstance();
            calDate.set(year, month, day);
            sendResult(Activity.RESULT_OK, calDate);
        }
    };
    */

    private void sendResult(int resultCode, Calendar calDate) {
        if (getTargetFragment() == null) {
            Log.v("DatePicker: ", "Target fragment is NULL!");
            return;
        }
        Log.v("DatePicker: ", "Date " + calDate.toString());
        //Intent intent = new Intent();
        Intent intent = new Intent(getActivity(), CustomerEnterActivity.class);
        intent.putExtra(EXTRA_DATE, calDate);

        getTargetFragment()
                .onActivityResult(getTargetRequestCode(), resultCode, intent);
    }

    public void showAlert (String alertMsg) {
        String alertTag = "ALERT_TAG";
        FragmentManager fmAlert = getFragmentManager();
        AlertFragment alert = new AlertFragment();
        alert.setAlertMessage("Msg: " + alertMsg);
        alert.show(Objects.requireNonNull(fmAlert), alertTag);
    }
}

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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Objects;

public class DatePickerDayFragment extends DialogFragment {

    // 12.9 - Calling back to your target
    public static final String EXTRA_DATE =
            "com.rmeijer.trainman.extra_date";

    // 12.5 - Adding a newInstance(Date) method
    private static final String ARG_DATE = "extra_date";

    private DatePicker mDatePicker;

    public static DatePickerDayFragment newInstance(Date date) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATE, date);

        DatePickerDayFragment fragment = new DatePickerDayFragment();
        fragment.setArguments(args);
        return fragment;
    }
    // end 12.5


    // 12.2 - Creating a DialogFragment
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // 12.7 - Extracting the date and initializing DatePicker
        Date date = (Date) Objects.requireNonNull(getArguments()).getSerializable(ARG_DATE);
        //Date date = new Date(); // TESTING

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        // end 12.7

        // 12.4 - Adding DatePicker to AlertDialog
        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_date, null);
        // end 12.4

        // 12.7 - Extracting the date and initializing DatePicker
        mDatePicker = v.findViewById(R.id.dialog_date_picker);
        mDatePicker.init(year, month, day, null);
        // end 12.7

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
                                Date date = new GregorianCalendar(year, month, day).getTime();

                                // TESTING
                                //String alertMsg = "onCreateDialog: " + EXTRA_DATE + " " + date.toString();
                                //showAlert(alertMsg);

                                sendResult(Activity.RESULT_OK, date);
                            }
                        }
                )
                // end 12.10
                .create();
    }

    // 12.9 - Calling back to your target
    private void sendResult(int resultCode, Date date) {
        if (getTargetFragment() == null) {
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(EXTRA_DATE, date);

        // TESTING
        //String alertMsg = "sendResult: " + EXTRA_DATE + " " + resultCode + " " + intent.getAction();
        //showAlert(alertMsg);

        getTargetFragment()
                .onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
    // end 12.9

    public void showAlert (String alertMsg) {
        String alertTag = "ALERT_TAG";
        FragmentManager fmAlert = getFragmentManager();
        AlertFragment alert = new AlertFragment();
        alert.setAlertMessage("Msg: " + alertMsg);
        alert.show(Objects.requireNonNull(fmAlert), alertTag);
    }
}

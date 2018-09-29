package com.rmeijer.trainman;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.Calendar;
import java.util.Objects;
import java.util.UUID;

public class SessionHeadFragment extends Fragment {

    private String TAG = "CustomerHead: ";

    //**********************************************************************************************
    // Intent extras/arguments
    //**********************************************************************************************
    private static final String EXTRA_CUSTOMER_ID = "com.rmeijer.trainman.customer_id";
    private static final String ARG_CUSTOMER_ID = "customer_id";
    private static final String EXTRA_SESSION_ID = "com.rmeijer.trainman.session_id";
    private static final String ARG_SESSION_ID = "session_id";

    //**********************************************************************************************
    // Objects and Variables
    //**********************************************************************************************
    private Customer mCustomer;
    private Session mSession;
    private ImageView mPhotoImageView;
    private File mPhotoFile;
    private TextView mNameField;
    private TextView mGenderTextView;
    private TextView mBirthDateTextView;
    private TextView mServiceField;
    private TextView mIdField;

    //**********************************************************************************************
    // 10.6 - Writing a newInstance(UUID) method
    // Creates each instance of the fragment - one for each session
    //**********************************************************************************************
    public static SessionHeadFragment newInstance(UUID sessionId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_SESSION_ID, sessionId);

        SessionHeadFragment fragment = new SessionHeadFragment();
        fragment.setArguments(args);

        return fragment;
    }

    //**********************************************************************************************
    // Get the session object
    //**********************************************************************************************
    public void getSessionObject() {
        UUID sessionId;
        Activity test_activity = getActivity();
        if (test_activity != null) {
            Intent test_intent = getActivity().getIntent();
            if (test_intent != null) {
                sessionId = (UUID) Objects.requireNonNull(this.getArguments()).get(ARG_SESSION_ID);
                Log.v("SessionHead: ", "Found ARG_SESSION_ID: " + sessionId);
            }
        }
    }

    //**********************************************************************************************
    // Update intent extra with new pager data
    //**********************************************************************************************
    public void updateExtra(UUID sessionId) {

        // Get the session_id from the parent activity
        String EXTRA_SESSION_ID = "com.rmeijer.trainman.session_id";

        // 10.6 - Writing a newInstance(UUID) method
        String ARG_SESSION_ID = "session_id";

        Objects.requireNonNull(getActivity()).getIntent().putExtra(EXTRA_SESSION_ID, sessionId);
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
        View v = inflater.inflate(R.layout.fragment_session_head, container, false);

        mCustomer = new Customer();
        mSession = new Session();

        UUID customerId = (UUID) Objects.requireNonNull(getActivity()).getIntent().getSerializableExtra(EXTRA_CUSTOMER_ID);
        Log.v("SessionHead: ", "Got Extra customer Id: " + customerId.toString());
        if (customerId != null) {
            mCustomer = CustomerStore.get(getActivity()).getCustomer(customerId);
            if (mCustomer != null) {
                Log.v("SessionHead: ", "Name " + mCustomer.getName());
                Log.v("SessionHead: ", "Customer Id " + mCustomer.getId().toString());
            } else {
                Log.v("SessionHead: ", "************Customer not found!****************");
            }
        } else {
            Log.v("SessionHead: ", "************Customer ID EXTRA not found!****************");
        }

        // Customer Picture
        mPhotoImageView = v.findViewById(R.id.session_head_imageview);
        mPhotoFile = CustomerStore.get(getActivity()).getPhotoFile(mCustomer);
        updatePhotoView();

        // Customer name
        mNameField = v.findViewById(R.id.session_head_customer_name_textview);
        mNameField.setText(mCustomer.getName());

        // Gender
        mGenderTextView = v.findViewById(R.id.session_head_gender_textview);
        mGenderTextView.setText(mCustomer.getGender());

        //Birthdate
        mBirthDateTextView = v.findViewById(R.id.session_head_birthdate_textview);
        Calendar calDate = Calendar.getInstance();
        calDate.setTime(mCustomer.getBirthDate());
        int year = calDate.get(Calendar.YEAR);
        int month = calDate.get(Calendar.MONTH);
        int day = calDate.get(Calendar.DAY_OF_MONTH);
        String dateString = (month+1) + "/" + day + "/" + year;
        mBirthDateTextView.setText(dateString);

        //******************************************************************************************
        return v;

    }

    //**********************************************************************************************
    // 16.11 - Updating mPhotoImageView
    //**********************************************************************************************
    private void updatePhotoView() {
        if (mPhotoFile == null || !mPhotoFile.exists()) {
            Log.v( TAG, "updatePhotoView: mPhotoFile is null!" );
            mPhotoImageView.setImageResource(R.drawable.nophoto);
        } else {
            Bitmap bitmap = PictureUtils.getScaledBitmap(
                    mPhotoFile.getPath(), Objects.requireNonNull(getActivity()));
            mPhotoImageView.setImageBitmap(bitmap);
            Log.v( TAG, "updatePhotoView Drawable: " + mPhotoImageView.getDrawable());
        }
    }

}

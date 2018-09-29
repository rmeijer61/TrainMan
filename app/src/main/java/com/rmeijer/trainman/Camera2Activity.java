package com.rmeijer.trainman;

import android.content.Context;
import android.graphics.Camera;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import java.util.UUID;

public class Camera2Activity extends AppCompatActivity {

    // Log tag
    private final String TAG = "Camera2Activity: ";

    //**********************************************************************************************
    // Extras/Arguments
    //**********************************************************************************************
    // Will get the customerId extra from the intent
    private static final String EXTRA_CUSTOMER_ID = "com.rmeijer.trainman.customer_id";
    // Not used - The fragment newInstance will update the fragment's argument
    private static final String ARG_CUSTOMER_ID = "customer_id";

    //**********************************************************************************************
    // Instance variables
    //**********************************************************************************************
    private Camera mCamera;

    //**********************************************************************************************
    // onCreate
    //**********************************************************************************************
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Layout
        setContentView(R.layout.activity_camera2);

        // Suppress soft keyboard on create
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        Customer mCustomer;

        //******************************************************************************************
        // Get EXTRA values
        //******************************************************************************************
        Context mContext = getApplicationContext();

        final UUID customerId = (UUID) getIntent().getSerializableExtra(EXTRA_CUSTOMER_ID);
        if (customerId != null) {
            CustomerStore sCustomerStore = CustomerStore.get(mContext);
            mCustomer = sCustomerStore.getCustomer(customerId);
            if (mCustomer != null) {
                Log.v(TAG, "Name " + mCustomer.getName());
                Log.v(TAG, "Customer Id " + mCustomer.getId().toString());
            } else {
                Log.v(TAG, "************Customer not found!****************");
                FragmentManager fmAlert = getSupportFragmentManager();
                String msg = "Customer not found - application cannot find the customer in data store";
                AlertFragment alert = new AlertFragment();
            }
        } else {
            Log.v(TAG, "************Customer ID EXTRA not found!****************");
            FragmentManager fmAlert = getSupportFragmentManager();
            String msg = "Customer not found - application cannot find the customer in data store";
            AlertFragment alert = new AlertFragment();
        }

        /* Used by the Android team. I will use the book version but keep for reference
        if (null == savedInstanceState) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container_fPic2, Camera2Fragment.newInstance())
                    .commit();
        }
        */

        // Create Fragment Manager
        FragmentManager fm = getSupportFragmentManager();

        Fragment fragment_fPic2 = fm.findFragmentById(R.id.fragment_container_fPic2);

        if (fragment_fPic2 == null) {
            // The PictureFragment newInstance method will create the fragment with the necessary argument
            fragment_fPic2 = Camera2Fragment.newInstance();
            //fragment_fPic2 = new Camera2Fragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container_fPic2, fragment_fPic2)
                    .commit();
        }
    }

}

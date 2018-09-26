package com.rmeijer.trainman;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.UUID;

public class PictureActivity extends AppCompatActivity {

    // Log tag
    private String TAG = "PictureActivity: ";

    //**********************************************************************************************
    // Extras/Arguments
    //**********************************************************************************************
    // Will get the customerId extra from the intent
    private static final String EXTRA_CUSTOMER_ID = "com.rmeijer.trainman.customer_id";
    // Not used - The fragment newInstance will update the fragment's argument
    private static final String ARG_CUSTOMER_ID = "customer_id";

    //**********************************************************************************************
    // onCreate
    //**********************************************************************************************
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Layout
        setContentView(R.layout.activity_picture);

        // Suppress soft keyboard on create
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        Customer mCustomer = new Customer();

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

        // Create Fragment Manager
        FragmentManager fm = getSupportFragmentManager();
        //------------------------------------------------------------------------------------------
        // UserFragment container
        //------------------------------------------------------------------------------------------
        Fragment fragment_fPic1 = fm.findFragmentById(R.id.fragment_container_fPic1);
        if (fragment_fPic1 == null) {
            fragment_fPic1 = new UserFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container_fPic1, fragment_fPic1)
                    .commit();
        }

        //------------------------------------------------------------------------------------------
        // PictureFragment container
        //------------------------------------------------------------------------------------------
        Fragment fragment_fPic2 = fm.findFragmentById(R.id.fragment_container_fPic2);

        if (fragment_fPic2 == null) {
            // The PictureFragment newInstance method will create the fragment with the necessary argument
            fragment_fPic2 = PictureFragment.newInstance(customerId);
            fm.beginTransaction()
                    .add(R.id.fragment_container_fPic2, fragment_fPic2)
                    .commit();
        }
    }
}

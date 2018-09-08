package com.rmeijer.trainman;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import java.util.UUID;

public class CustomerMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_main);

        // 7.15 - Getting the FragmentManager
        FragmentManager fm = getSupportFragmentManager();


        // 7.16 - Adding a UserFragment
        Fragment fragment_fc1 = fm.findFragmentById(R.id.fragment_container_fc1);

        if (fragment_fc1 == null) {
            fragment_fc1 = new UserFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container_fc1, fragment_fc1)
                    .commit();
        }

        // 7.16 - Adding a CustomerHeadFragment

        Fragment fragment_fc2 = fm.findFragmentById(R.id.fragment_container_fc2);

        if (fragment_fc2 == null) {
            fragment_fc2 = new CustomerHeadFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container_fc2, fragment_fc2)
                    .commit();
        }

        // 7.16 - Adding a CustomerMenuFragment
        Fragment fragment_fc3 = fm.findFragmentById(R.id.fragment_container_fc3);

        if (fragment_fc3 == null) {
            fragment_fc3 = new CustomerMenuFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container_fc3, fragment_fc3)
                    .commit();
        }

    }


    // 10.2 - Creating a newIntent method
    // 10.7 - Using newInstance(UUID)
    //public static final String EXTRA_CUSTOMER_ID =
    private static final String EXTRA_CUSTOMER_ID =
            "com.rmeijer.trainman.customer_id";

    public static Intent newIntent(Context packageContext, UUID customerId) {
        Intent intent = new Intent(packageContext, CustomerMainActivity.class);
        intent.putExtra(EXTRA_CUSTOMER_ID, customerId);
        return intent;
    }


    // 8.9 - Cleaning up CustomerMainActivity
    //@Override
    protected Fragment createFragment() {
        // 10.7 - Using newInstance(UUID)
        //return new CustomerFragment();
        UUID customerId = (UUID) getIntent()
                .getSerializableExtra(EXTRA_CUSTOMER_ID);
        return CustomerHeadFragment.newInstance(customerId);

    }
}
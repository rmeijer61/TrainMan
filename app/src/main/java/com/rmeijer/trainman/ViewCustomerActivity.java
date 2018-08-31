package com.rmeijer.trainman;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.UUID;

public class ViewCustomerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_customer);

        int messageResId = R.string.view_customer_title;
        Context context = getApplicationContext();
        Toast.makeText(context, messageResId, Toast.LENGTH_SHORT).show();

        // 7.15 - Getting the FragmentManager
        FragmentManager fm = getSupportFragmentManager();

        // 7.16 - Adding a UserFragment
        Fragment fragment_f21 = fm.findFragmentById(R.id.fragment_container_f21);

        if (fragment_f21 == null) {
            fragment_f21 = new UserFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container_f21, fragment_f21)
                    .commit();
        }


        // 7.16 - Adding a EnterCustomerFragment
        Fragment fragment_f22 = fm.findFragmentById(R.id.fragment_container_f22);

        if (fragment_f22 == null) {
            // Implemeting 10.7 means creating fragment cannot be done right away
            // should call ViewCustomerFragment.newInstance(UUID)
            // when it needs to create a ViewCustomerFragment
            //fragment_f22 = new ViewCustomerFragment();
            fragment_f22 = createFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container_f22, fragment_f22)
                    .commit();
        }

    }


    // 10.2 - Creating a newIntent method
    // 10.7 - Using newInstance(UUID)
    //public static final String EXTRA_CUSTOMER_ID =
    private static final String EXTRA_CUSTOMER_ID =
            "com.rmeijer.trainman.customer_id";
            //"com.rmeijer.trainman.EXTRA_CUSTUMER_ID";
            //"com.rmeijer.trainman.customer_id";

    public static Intent newIntent(Context packageContext, UUID customerId) {
        Intent intent = new Intent(packageContext, ViewCustomerActivity.class);
        intent.putExtra(EXTRA_CUSTOMER_ID, customerId);
        return intent;
    }


    // 8.9 - Cleaning up CustomerMainActivity
    //@Override
    protected Fragment createFragment() {
        // 10.7 - Using newInstance(UUID)
        //return new EnterCustomerFragment();
        UUID customerId = (UUID) getIntent()
                .getSerializableExtra(EXTRA_CUSTOMER_ID);
        return ViewCustomerFragment.newInstance(customerId);

    }
}
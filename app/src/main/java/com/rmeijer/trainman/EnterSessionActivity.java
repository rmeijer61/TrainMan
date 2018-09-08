package com.rmeijer.trainman;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.UUID;

public class EnterSessionActivity extends AppCompatActivity {

    // 10.6 - Writing a newInstance(UUID) method
    //??? Update the pager activity intent with new customer_id
    private static final String ARG_CUSTOMER_ID = "customer_id";

    // 11.3 - Creating newIntent
    private static final String EXTRA_CUSTOMER_ID =
            "com.rmeijer.trainman.customer_id";

    // 11.3 - Creating newIntent
    public static Intent newIntent(Context packageContext, UUID customerId) {
        Intent intent = new Intent(packageContext, EnterSessionActivity.class);
        intent.putExtra(EXTRA_CUSTOMER_ID, customerId);
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_session);

        int messageResId = R.string.enter_new_session_title;
        Context context = getApplicationContext();
        Toast.makeText(context, messageResId, Toast.LENGTH_SHORT).show();

        // 11.3 - Creating newIntent - get Extra
        final UUID customerId = (UUID) getIntent()
                .getSerializableExtra(EXTRA_CUSTOMER_ID);

        // 7.15 - Getting the FragmentManager
        FragmentManager fm = getSupportFragmentManager();

        // 7.16 - Adding a UserFragment
        Fragment fragment_fESess1 = fm.findFragmentById(R.id.fragment_container_fESess1);

        if (fragment_fESess1 == null) {
            fragment_fESess1 = new UserFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container_fESess1, fragment_fESess1)
                    .commit();
        }

        // 7.16 - Adding a EnterCustomerFragment
        Fragment fragment_fESess2 = fm.findFragmentById(R.id.fragment_container_fESess2);

        if (fragment_fESess2 == null) {
            fragment_fESess2 = new SessionHeadFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container_fESess2, fragment_fESess2)
                    .commit();
        }

        // 7.16 - Adding a EnterSessionFragment
        Fragment fragment_fESess3 = fm.findFragmentById(R.id.fragment_container_fESess3);

        if (fragment_fESess3 == null) {
            fragment_fESess3 = new EnterSessionFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container_fESess3, fragment_fESess3)
                    .commit();
        }
    }
}

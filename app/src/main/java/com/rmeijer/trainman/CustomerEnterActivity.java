package com.rmeijer.trainman;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

public class CustomerEnterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_enter);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        int messageResId = R.string.enter_customer_title;
        Context context = getApplicationContext();
        Toast.makeText(context, messageResId, Toast.LENGTH_SHORT).show();

        // 7.15 - Getting the FragmentManager
        FragmentManager fm = getSupportFragmentManager();

        // 7.16 - Adding a UserFragment
        Fragment fragment_fCust1 = fm.findFragmentById(R.id.fragment_container_fCust1);

        if (fragment_fCust1 == null) {
            fragment_fCust1 = new UserFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container_fCust1, fragment_fCust1)
                    .commit();
        }

        // 7.16 - Adding a CusttureFragment
        Fragment fragment_fCust2 = fm.findFragmentById(R.id.fragment_container_fCust2);

        if (fragment_fCust2 == null) {
            fragment_fCust2 = new CustomerEnterFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container_fCust2, fragment_fCust2)
                    .commit();
        }
    }

}

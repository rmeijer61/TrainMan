package com.rmeijer.trainman;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.UUID;

public class CustomerListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_list);

        int messageResId = R.string.display_customer_list_button_text;
        Context context = getApplicationContext();
        Toast.makeText(context, messageResId, Toast.LENGTH_SHORT).show();

        // 7.15 - Getting the FragmentManager
        FragmentManager fm = getSupportFragmentManager();

        // 7.16 - Adding a EnterCustomerFragment
        Fragment fragment_f11 = fm.findFragmentById(R.id.fragment_container_f11);

        if (fragment_f11 == null) {
            fragment_f11 = new UserFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container_f11, fragment_f11)
                    .commit();
        }

        // 7.16 - Adding a EnterCustomerFragment
        Fragment fragment_f12 = fm.findFragmentById(R.id.fragment_container_f12);

        if (fragment_f12 == null) {
            fragment_f12 = new CustomerListFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container_f12, fragment_f12)
                    .commit();
        }
    }


}

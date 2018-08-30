package com.rmeijer.trainman;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class CustomerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        int messageResId = R.string.enter_new_customer_title;
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

        // 7.16 - Adding a CustomerFragment
        Fragment fragment_f22 = fm.findFragmentById(R.id.fragment_container_f22);

        if (fragment_f22 == null) {
            fragment_f22 = new CustomerFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container_f22, fragment_f22)
                    .commit();
        }
    }
}
package com.rmeijer.trainman;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class EnterSessionActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_session);

        int messageResId = R.string.enter_new_session_title;
        Context context = getApplicationContext();
        Toast.makeText(context, messageResId, Toast.LENGTH_SHORT).show();

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
            fragment_fESess2 = new EnterSessionFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container_fESess2, fragment_fESess2)
                    .commit();
        }
    }
}

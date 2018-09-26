package com.rmeijer.trainman;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

public class SessionViewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_view);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        // 7.15 - Getting the FragmentManager
        FragmentManager fm = getSupportFragmentManager();

        // 7.16 - Adding a UserFragment
        Fragment fragment_fViewSession1 = fm.findFragmentById(R.id.fragment_container_fViewSession1);

        if (fragment_fViewSession1 == null) {
            fragment_fViewSession1 = new UserFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container_fViewSession1, fragment_fViewSession1)
                    .commit();
        }

        // 7.16 - Adding a SessionHeadFragment
        Fragment fragment_fViewSession2 = fm.findFragmentById(R.id.fragment_container_fViewSession2);

        if (fragment_fViewSession2 == null) {
            fragment_fViewSession2 = new SessionHeadFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container_fViewSession2, fragment_fViewSession2)
                    .commit();
        }

        // 7.16 - Adding a SessionViewFragment
        Fragment fragment_fViewSession3 = fm.findFragmentById(R.id.fragment_container_fViewSession3);

        if (fragment_fViewSession3 == null) {
            fragment_fViewSession3 = new SessionViewFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container_fViewSession3, fragment_fViewSession3)
                    .commit();
        }
    }
}

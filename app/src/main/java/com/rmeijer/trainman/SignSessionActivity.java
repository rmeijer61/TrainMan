package com.rmeijer.trainman;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.Toast;

public class SignSessionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_session);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        int messageResId = R.string.sign_session_title;
        Context context = getApplicationContext();
        Toast.makeText(context, messageResId, Toast.LENGTH_SHORT).show();

        // 7.15 - Getting the FragmentManager
        FragmentManager fm = getSupportFragmentManager();

        // 7.16 - Adding a UserFragment
        Fragment fragment_fSign1 = fm.findFragmentById(R.id.fragment_container_fSign1);

        if (fragment_fSign1 == null) {
            fragment_fSign1 = new UserFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container_fSign1, fragment_fSign1)
                    .commit();
        }

        // 7.16 - Adding a CustomerEnterFragment
        Fragment fragment_fSign2 = fm.findFragmentById(R.id.fragment_container_fSign2);

        if (fragment_fSign2 == null) {
            fragment_fSign2 = new SignSessionFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container_fSign2, fragment_fSign2)
                    .commit();
        }

    }
}
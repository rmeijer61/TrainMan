package com.rmeijer.trainman;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class ViewSessionActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_session);

        int messageResId = R.string.view_session_title;
        Context context = getApplicationContext();
        Toast.makeText(context, messageResId, Toast.LENGTH_SHORT).show();

        // 7.15 - Getting the FragmentManager
        FragmentManager fm = getSupportFragmentManager();

        // 7.16 - Adding a UserFragment
        Fragment fragment_fVSess1 = fm.findFragmentById(R.id.fragment_container_fVSess1);

        if (fragment_fVSess1 == null) {
            fragment_fVSess1 = new UserFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container_fVSess1, fragment_fVSess1)
                    .commit();
        }

        // 7.16 - Adding a ViewSessionFragment
        Fragment fragment_fVSess2 = fm.findFragmentById(R.id.fragment_container_fVSess2);

        if (fragment_fVSess2 == null) {
            fragment_fVSess2 = new ViewSessionFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container_fVSess2, fragment_fVSess2)
                    .commit();
        }
    }
}

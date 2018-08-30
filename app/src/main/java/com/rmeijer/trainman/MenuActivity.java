package com.rmeijer.trainman;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // 7.15 - Getting the FragmentManager
        FragmentManager fm = getSupportFragmentManager();

        // 7.16 - Adding a UserFragment
        Fragment fragment_f1 = fm.findFragmentById(R.id.fragment_container_f1);

        if (fragment_f1 == null) {
            fragment_f1 = new UserFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container_f1, fragment_f1)
                    .commit();
        }

        // 7.16 - Adding a MenuFragment
        Fragment fragment_f2 = fm.findFragmentById(R.id.fragment_container_f2);

        if (fragment_f2 == null) {
            fragment_f2 = new MenuFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container_f2, fragment_f2)
                    .commit();
        }


    }
}

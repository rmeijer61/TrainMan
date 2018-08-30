package com.rmeijer.trainman;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class ReceiptActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);

        int messageResId = R.string.generate_receipt_title;
        Context context = getApplicationContext();
        Toast.makeText(context, messageResId, Toast.LENGTH_SHORT).show();

        // 7.15 - Getting the FragmentManager
        FragmentManager fm = getSupportFragmentManager();

        // 7.16 - Adding a UserFragment
        Fragment fragment_fRec1 = fm.findFragmentById(R.id.fragment_container_fRec1);

        if (fragment_fRec1 == null) {
            fragment_fRec1 = new UserFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container_fRec1, fragment_fRec1)
                    .commit();
        }

        // 7.16 - Adding a ReceiptFragment
        Fragment fragment_fRec2 = fm.findFragmentById(R.id.fragment_container_fRec2);

        if (fragment_fRec2 == null) {
            fragment_fRec2 = new ReceiptFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container_fRec2, fragment_fRec2)
                    .commit();
        }
    }
}
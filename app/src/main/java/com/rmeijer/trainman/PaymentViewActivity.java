package com.rmeijer.trainman;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.Toast;

public class PaymentViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        int messageResId = R.string.payment_process_payment_title;
        Context context = getApplicationContext();
        Toast.makeText(context, messageResId, Toast.LENGTH_SHORT).show();

        // 7.15 - Getting the FragmentManager
        FragmentManager fm = getSupportFragmentManager();

        // 7.16 - Adding a UserFragment
        Fragment fragment_fPay1 = fm.findFragmentById(R.id.fragment_container_fPay1);

        if (fragment_fPay1 == null) {
            fragment_fPay1 = new UserFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container_fPay1, fragment_fPay1)
                    .commit();
        }

        // 7.16 - Adding a PaymentFragment
        Fragment fragment_fPay2 = fm.findFragmentById(R.id.fragment_container_fPay2);

        if (fragment_fPay2 == null) {
            fragment_fPay2 = new PaymentViewFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container_fPay2, fragment_fPay2)
                    .commit();
        }
    }
}
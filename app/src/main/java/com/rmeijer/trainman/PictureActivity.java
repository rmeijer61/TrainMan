package com.rmeijer.trainman;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class PictureActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);

        int messageResId = R.string.take_customer_picture_title;
        Context context = getApplicationContext();
        Toast.makeText(context, messageResId, Toast.LENGTH_SHORT).show();

        // 7.15 - Getting the FragmentManager
        FragmentManager fm = getSupportFragmentManager();

        // 7.16 - Adding a UserFragment
        Fragment fragment_fPic1 = fm.findFragmentById(R.id.fragment_container_fPic1);

        if (fragment_fPic1 == null) {
            fragment_fPic1 = new UserFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container_fPic1, fragment_fPic1)
                    .commit();
        }

        // 7.16 - Adding a PictureFragment
        Fragment fragment_fPic2 = fm.findFragmentById(R.id.fragment_container_fPic2);

        if (fragment_fPic2 == null) {
            fragment_fPic2 = new PictureFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container_fPic2, fragment_fPic2)
                    .commit();
        }
    }
}

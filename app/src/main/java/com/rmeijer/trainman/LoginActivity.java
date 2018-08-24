package com.rmeijer.trainman;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.EditText;


public class LoginActivity extends AppCompatActivity {

    // 3.1 - Adding a TAG constant
    private static final String TAG = "LoginActivity";
    // 3.5 - Adding a key for the value
    private static final String KEY_INDEX = "index";

    private EditText eUsernameText;
    private EditText ePasswordText;
    private Button mLoginButton;
    private int mCurrentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Adding a log statement to onCreate(Bundle)
        Log.d(TAG, "onCreate(Bundle) called");

        setContentView(R.layout.activity_login);



        // 2.8 - Wiring up the new button
        mLoginButton = findViewById(R.id.login_button);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Check authentication credentials
                eUsernameText = (EditText) findViewById(R.id.username);
                String username = eUsernameText.getText().toString();

                ePasswordText = (EditText) findViewById(R.id.password);
                String password =  ePasswordText.getText().toString();

                int messageResId = 0;

                if (checkLogin(username, password)) {
                    messageResId = R.string.success_toast;
                } else {
                    messageResId = R.string.failure_toast;
                }
                Context context = getApplicationContext();
                Toast.makeText(context, messageResId, Toast.LENGTH_SHORT).show();
            }
        });

    }

    // 3.3 - Overriding more lifecycle methods
    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    // 3.6 - Overriding onSaveInstanceState(…)
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }


    // 2.10 - Adding checkLogin(boolean)
    private boolean checkLogin(String username, String password) {
        String validUsername = getResources().getString(R.string.valid_username);
        String validPassword = getResources().getString(R.string.valid_password);

        if (username.equals(validUsername) && password.equals(validPassword))
            return true;
        else
            return false;
    }

}

package com.rmeijer.trainman;

// 7.9 - Supporting the Fragment import
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import java.util.Objects;

public class MenuFragment extends Fragment {
    private static final String TAG = "MenuFragment";
    private static final String EXTRA_RESULT = "true";

    // 7.10 - Overriding Fragment.onCreate(Bundle)
    //private Menu mMenu;

    // 7.10 - Overriding Fragment.onCreate(Bundle)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //mMenu = new Menu();
    }

    // 7.11 - Overriding onCreateView(…)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_menu, container, false);

        // ???
        Objects.requireNonNull(getActivity()).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        // 5.6 - Wiring up the buttons
        // 7.12 - Wiring up the EditText widget
        // 7.13 - Setting Button text
        // 7.14 - Listening for CheckBox changes
        Button mDisplayCustomerListButton = v.findViewById(R.id.display_customer_list_button);
        mDisplayCustomerListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            int messageResId = R.string.display_customer_list_button_text;
            Toast.makeText(getContext(), messageResId, Toast.LENGTH_SHORT).show();

            // Start CustomerListActivity
            Intent intent = new Intent(getActivity(), CustomerListActivity.class);
            startActivity(intent);
            }
        });

        Button mEnterNewCustomerButton = v.findViewById(R.id.enter_new_customer_button);
        mEnterNewCustomerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            int messageResId = R.string.enter_new_customer_button_text;
            Toast.makeText(getContext(), messageResId, Toast.LENGTH_SHORT).show();

            // Start CustomerEnterActivity
            Intent intent = new Intent(getActivity(), CustomerEnterActivity.class);
            startActivity(intent);
            }
        });

        Button mLogOffButton = v.findViewById(R.id.menu_logoff_button);
        mLogOffButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            FragmentManager fm = getFragmentManager();
            AlertConfirmFragment dialog = new AlertConfirmFragment();
            dialog.show(Objects.requireNonNull(fm), null);
            }
        });

        Button mGenerateCustomersButton = v.findViewById(R.id.menu_generate_customers_button);
        mGenerateCustomersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int messageResId = R.string.generate_customers_button_text;
                Toast.makeText(getContext(), messageResId, Toast.LENGTH_SHORT).show();

                // Generate some text customer data
                Log.v(TAG,"Menu: GenerateCustomers (get)...");
                CustomerGenerator sCustomerGenerator = CustomerGenerator.get(getActivity().getApplicationContext());
                Log.v(TAG,"Menu: GenerateCustomers...");
                sCustomerGenerator.GenerateCustomers(getActivity().getApplicationContext());

                // Start CustomerListActivity
                Intent intent = new Intent(getActivity(), CustomerListActivity.class);
                startActivity(intent);
            }
        });


        Button mCamera2DemoButton = v.findViewById(R.id.menu_camera2_demo_button);
        mCamera2DemoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int messageResId = R.string.camera2_demo_button_text;
                Toast.makeText(getContext(), messageResId, Toast.LENGTH_SHORT).show();

                // Start CustomerEnterActivity
                Intent intent = new Intent(getActivity(), Camera2Activity.class);
                startActivity(intent);
            }
        });

        //******************************************************************************************
        // Return View object
        //******************************************************************************************
        return v;
    }

}


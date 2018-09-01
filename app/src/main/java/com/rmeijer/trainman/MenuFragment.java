package com.rmeijer.trainman;

// 7.9 - Supporting the Fragment import
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class MenuFragment extends Fragment {

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_menu, container, false);

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

            // Start EnterCustomerActivity
            Intent intent = new Intent(getActivity(), EnterCustomerActivity.class);
            startActivity(intent);
            }
        });

        Button mLogOffButton = v.findViewById(R.id.menu_logoff_button);
        mLogOffButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int messageResId = R.string.logoff_msg_text;
                Toast.makeText(getContext(), messageResId, Toast.LENGTH_SHORT).show();

                getActivity().finish();
            }
        });

        return v;
        }
    }

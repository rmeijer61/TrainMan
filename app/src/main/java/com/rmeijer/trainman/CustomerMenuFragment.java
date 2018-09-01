package com.rmeijer.trainman;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class CustomerMenuFragment extends Fragment {

    // 7.10 - Overriding Fragment.onCreate(Bundle)
    //private CustomerMenu mCustomerMenu;

    // 7.10 - Overriding Fragment.onCreate(Bundle)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //mCustomerMenu = new CustomerMenu();
    }

    // 7.11 - Overriding onCreateView(…)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_customer_menu, container, false);

        // 5.6 - Wiring up the buttons
        // 7.12 - Wiring up the EditText widget
        // 7.13 - Setting Button text
        // 7.14 - Listening for CheckBox changes

        Button mTakeCustomerPictureButton = v.findViewById(R.id.take_customer_picture_button);
        mTakeCustomerPictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            int messageResId = R.string.take_customer_picture_button_text;
            Toast.makeText(getContext(), messageResId, Toast.LENGTH_SHORT).show();

            // Start PictureActivity
            Intent intent = new Intent(getActivity(), PictureActivity.class);
            startActivity(intent);
            }
        });

        Button mEnterSessionButton = v.findViewById(R.id.enter_new_session_button);
        mEnterSessionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            int messageResId = R.string.enter_new_session_button_text;
            Toast.makeText(getContext(), messageResId, Toast.LENGTH_SHORT).show();

            // Start SessionActivity
            Intent intent = new Intent(getActivity(), EnterSessionActivity.class);
            startActivity(intent);
            }
        });

        Button mViewSessionButton = v.findViewById(R.id.view_session_button);
        mViewSessionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            int messageResId = R.string.view_session_button_text;
            Toast.makeText(getContext(), messageResId, Toast.LENGTH_SHORT).show();

            // Start SessionActivity
            Intent intent = new Intent(getActivity(), ViewSessionActivity.class);
            startActivity(intent);
            }
        });

        Button mDoneButton = v.findViewById(R.id.customer_menu_done_button);
        mDoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            int messageResId = R.string.done_button_text;
            Toast.makeText(getContext(), messageResId, Toast.LENGTH_SHORT).show();

            getActivity().finish();
            }
        });

        Button mLogOffButton = v.findViewById(R.id.customer_menu_logoff_button);
        mDoneButton.setOnClickListener(new View.OnClickListener() {
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

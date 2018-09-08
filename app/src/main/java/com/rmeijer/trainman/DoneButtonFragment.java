package com.rmeijer.trainman;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class DoneButtonFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_done_button, container, false);

        Button mDoneButton = v.findViewById(R.id.customer_list_done_button);
        mDoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int messageResId = R.string.done_button_text;
                Toast.makeText(getContext(), messageResId, Toast.LENGTH_SHORT).show();

                getActivity().finish();
            }
        });

        return v;

    }
}

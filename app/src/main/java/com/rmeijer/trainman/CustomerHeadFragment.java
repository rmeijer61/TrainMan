package com.rmeijer.trainman;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.Calendar;
import java.util.Objects;
import java.util.UUID;

import static android.widget.AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL;

public class CustomerHeadFragment extends Fragment {

    private String TAG = "CustomerHead: ";

    //**********************************************************************************************
    // Intent extras/arguments
    //**********************************************************************************************
    private static final String EXTRA_CUSTOMER_ID = "com.rmeijer.trainman.customer_id";
    private static final String ARG_CUSTOMER_ID = "customer_id";

    //**********************************************************************************************
    // Objects and Variables
    //**********************************************************************************************
    private Customer mCustomer;
    private TextView mNameTextView;
    private TextView mGenderTextView;
    private TextView mBirthDateTextView;
    private ImageView mPhotoImageView;
    private File mPhotoFile;

    //**********************************************************************************************
    // 17.6 - Adding callback interface
    //**********************************************************************************************
    private Callbacks mCallbacks;

    /**
     * Required interface for hosting activities
     */
    public interface Callbacks {
        void onCustomerSelected(Customer customer);
    }

    //**********************************************************************************************
    // onAttach
    //**********************************************************************************************
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallbacks = (Callbacks) context;
        getCustomerObject();
        Log.v("CustomerHead: ", "onAttach - beg+++++++++++++++++++++++++++++++++++");
        if (mCustomer != null) {
            Log.v("CustomerHead: ", "Customer: " + mCustomer.getId().toString());
        }
        Log.v("CustomerHead: ", "onAttach - end+++++++++++++++++++++++++++++++++++");
    }

    //**********************************************************************************************
    //
    //**********************************************************************************************
    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    //**********************************************************************************************
    // 10.6 - Writing a newInstance(UUID) method
    // Creates each instance of the fragment - one for each customer
    //**********************************************************************************************
    public static CustomerHeadFragment newInstance(UUID customerId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_CUSTOMER_ID, customerId);

        CustomerHeadFragment fragment = new CustomerHeadFragment();
        fragment.setArguments(args);

        return fragment;
    }

    //**********************************************************************************************
    // Get Customer object
    //**********************************************************************************************
    public void getCustomerObject() {
        UUID customerId = null;
        Activity test_activity = getActivity();
        if (test_activity != null) {
            Intent test_intent = getActivity().getIntent();
            if (test_intent != null) {
                //customerId = (UUID) getActivity().getIntent().getSerializableExtra(EXTRA_CUSTOMER_ID);
                //Log.v("CustomerHead: ", "Found EXTRA_CUSTOMER_ID: " + customerId);
                customerId = (UUID) Objects.requireNonNull(this.getArguments()).get(ARG_CUSTOMER_ID);
                Log.v("CustomerHead: ", "Found ARG_CUSTOMER_ID: " + customerId);
            }
        }

        if (customerId != null) {
            mCustomer = CustomerStore.get(getActivity()).getCustomer(customerId);
            if (mCustomer.getId() != null) {
                Log.v("CustomerHead: ", "Found customer: " + mCustomer.getId());
            } else {
                Log.v("CustomerHead: ", "Customer NOT FOUND! ");
            }
        } else {
            Log.v("CustomerHead: ", "getCustomerOnject****************************");
            Log.v("CustomerHead: ", "Customer extra not found!");
            Log.v("CustomerHead: ", "getCustomerOnject****************************");
            Toast.makeText(getActivity(), "Id extra not found or null!", Toast.LENGTH_SHORT).show();
        }
    }

    //**********************************************************************************************
    // Update intent extra customer data with new pager data
    //**********************************************************************************************
    public void updateExtra(UUID customerId) {

        // Get the customer_id from the parent activity
        String EXTRA_CUSTOMER_ID =
                "com.rmeijer.trainman.customer_id";

        // 10.6 - Writing a newInstance(UUID) method
        String ARG_CUSTOMER_ID = "customer_id";

        Objects.requireNonNull(getActivity()).getIntent().putExtra(EXTRA_CUSTOMER_ID, customerId);
    }

    //**********************************************************************************************
    // 7.10 - Overriding Fragment.onCreate(Bundle)
    //**********************************************************************************************
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 10.4 - Retrieving the extra and fetching the Customer
        //Customer mCustomer = new Customer();

        // 10.8 - Getting customer ID from the arguments
        //UUID customerId = (UUID) getActivity().getIntent()
        //        .getSerializableExtra(Customer.EXTRA_CUSTOMER_ID);
        UUID customerId = (UUID) Objects.requireNonNull(getArguments()).getSerializable(ARG_CUSTOMER_ID);
        mCustomer = CustomerStore.get(getActivity()).getCustomer(customerId);
    }

    //**********************************************************************************************
    // 7.11 - Overriding onCreateView(…)
    //**********************************************************************************************
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_customer_head, container, false);

        // Customer Picture
        mPhotoImageView = v.findViewById(R.id.customer_head_picture_imageview);
        mPhotoFile = CustomerStore.get(getActivity()).getPhotoFile(mCustomer);
        updatePhotoView();

        // Customer name
        mNameTextView = v.findViewById(R.id.customer_head_customer_name_textview);
        mNameTextView.setText(mCustomer.getName());

        // Gender
        mGenderTextView = v.findViewById(R.id.customer_head_gender_textview);
        mGenderTextView.setText(mCustomer.getGender());

        //Birthdate
        mBirthDateTextView = v.findViewById(R.id.customer_head_birthdate_textview);
        Calendar calDate = Calendar.getInstance();
        calDate.setTime(mCustomer.getBirthDate());
        int year = calDate.get(Calendar.YEAR);
        int month = calDate.get(Calendar.MONTH);
        int day = calDate.get(Calendar.DAY_OF_MONTH);
        String dateString = (month+1) + "/" + day + "/" + year;
        mBirthDateTextView.setText(dateString);

        //******************************************************************************************
        return v;

    }

    //**********************************************************************************************
    // 16.11 - Updating mPhotoImageView
    //**********************************************************************************************
    private void updatePhotoView() {
        if (mPhotoFile == null || !mPhotoFile.exists()) {
            Log.v( TAG, "updatePhotoView: mPhotoFile is null!" );
            mPhotoImageView.setImageResource(R.drawable.nophoto);
        } else {
            Bitmap bitmap = PictureUtils.getScaledBitmap(
                    mPhotoFile.getPath(), Objects.requireNonNull(getActivity()));
            mPhotoImageView.setImageBitmap(bitmap);
            Log.v( TAG, "updatePhotoView Drawable: " + mPhotoImageView.getDrawable());
        }
    }

    //**********************************************************************************************
    // Determine what is being displayed by the pager
    //**********************************************************************************************
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            Log.v("CustomerHead: ", "Visible - beg************************************");
            // If not the first time (because can be null on first time)
            if (mCustomer != null) {
                getCustomerObject();
                updateExtra(mCustomer.getId());
            }

            Log.v("CustomerHead: ", "Visible - end************************************");
        }
    }

    //**********************************************************************************************
    // onResume
    //**********************************************************************************************
    @Override
    public void onResume() {
        super.onResume();
        // Make sure the customer object is current
        getCustomerObject();
        Log.v("CustomerHead: ", "onResume*****************************************");
        if (mCustomer.getId() != null) {
            Log.v("CustomerHead: ", "Customer: " + mCustomer.getId().toString());
        }
        Log.v("CustomerHead: ", "*************************************************");
    }

}

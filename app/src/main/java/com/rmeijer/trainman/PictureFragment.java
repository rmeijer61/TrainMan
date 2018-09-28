package com.rmeijer.trainman;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Camera;
//import android.hardware.Camera;
//import android.hardware.camera2.CameraManager;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class PictureFragment extends Fragment {

    // Logging Tag
    final String TAG = "Picture: ";

    //**********************************************************************************************
    // Instance variables
    //**********************************************************************************************
    private ImageButton mPhotoButton;
    private ImageView mPhotoImageView;
    private File mPhotoFile;
    private static final int REQUEST_PHOTO = 2;
    private Camera mCamera;
    Preview mPreview;

    //**********************************************************************************************
    // Extras/Arguments
    //**********************************************************************************************
    private static final String EXTRA_CUSTOMER_ID = "com.rmeijer.trainman.customer_id";
    private static final String ARG_CUSTOMER_ID = "customer_id";

    //**********************************************************************************************
    // New instance with extras/arguments
    //**********************************************************************************************
    public static PictureFragment newInstance(UUID customerId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_CUSTOMER_ID, customerId);

        PictureFragment fragment = new PictureFragment();
        fragment.setArguments(args);

        return fragment;
    }

    //**********************************************************************************************
    // onCreate
    //**********************************************************************************************
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    //**********************************************************************************************
    // onCreateView
    //**********************************************************************************************
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_picture, container, false);

        Customer mCustomer = new Customer();

        //*****************************************************************************************
        // Get EXTRA value from the activity's intent
        // (Note: The fragment argument is also available) 
        //*****************************************************************************************
        Context mContext = Objects.requireNonNull(getActivity()).getApplicationContext();
        // 11.3 - Creating newIntent - Get intent EXTRA
        final UUID customerId = (UUID) getActivity().getIntent().getSerializableExtra(EXTRA_CUSTOMER_ID);
        if (customerId != null) {
            CustomerStore sCustomerStore = CustomerStore.get(mContext);
            mCustomer = sCustomerStore.getCustomer(customerId);
            if (mCustomer != null) {
                Log.v( TAG, "Name " + mCustomer.getName());
                Log.v(TAG, "Customer Id " + mCustomer.getId().toString());
            } else {
                Log.v(TAG, "************Customer not found!****************");
                FragmentManager fmAlert = getFragmentManager();
                String msg = "Customer not found - application cannot find the customer in data store";
                AlertFragment alert = new AlertFragment();
            }
        } else {
            Log.v(TAG, "************Customer ID EXTRA not found!****************");
            FragmentManager fmAlert = getFragmentManager();
            String msg = "Customer Id not found - application cannot find the intent extra value";
            AlertFragment alert = new AlertFragment();
        }

        // 16.7 - Grabbing photo file location
        if (customerId != null) {
            mPhotoFile = CustomerStore.get(getActivity()).getPhotoFile(mCustomer);
        }

        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        // View objects
        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

        // 16.8 - Firing a camera intent
        PackageManager packageManager = getActivity().getPackageManager();

        //------------------------------------------------------------------------------------------
        // Customer Name
        //------------------------------------------------------------------------------------------
        TextView mNameField = v.findViewById(R.id.picture_customer_name_textview);
        mNameField.setText(mCustomer.getName());

        //------------------------------------------------------------------------------------------
        // Camera Button
        //------------------------------------------------------------------------------------------
        mPhotoButton = v.findViewById(R.id.picture_imagebutton);
        // 16.8 - Firing a camera intent

        // ACTION_IMAGE_CAPTURE
        // Standard Intent action that can be sent to have the camera application capture an image and return it.
        final Intent captureImageIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);


        /*
        if (isCameraOpen()) {
            Log.v( TAG, "Camera is open");
            releaseCameraAndPreview();
        }

        Log.v( TAG, "Check for a camera... ");
        checkCameraHardware(getActivity().getApplicationContext());

        Log.v( TAG, "Open camera... ");
        safeCameraOpen();

        */

        /*
        Log.v( TAG, "Checking canTakePhoto... ");

        boolean canTakePhoto = mPhotoFile != null &&
                captureImageIntent.resolveActivity(packageManager) != null;
        mPhotoButton.setEnabled(canTakePhoto);

        if (!canTakePhoto) {
            Log.v( TAG, "canTakePhoto: " + canTakePhoto);
            FragmentManager fmAlert = getFragmentManager();
            String msg = "resolveActivity: " + canTakePhoto;
            AlertFragment alert = new AlertFragment();
        } else {
            Log.v( TAG, "canTakePhoto! " + canTakePhoto);
        }
        */

        mPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = FileProvider.getUriForFile(getActivity(),
                        "com.rmeijer.trainman.fileprovider",
                        mPhotoFile);
                captureImageIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                Log.v( TAG, "OnClickListener: URI: " + uri.toString());

                List<ResolveInfo> cameraActivities = getActivity()
                        .getPackageManager().queryIntentActivities(captureImageIntent,
                                PackageManager.MATCH_DEFAULT_ONLY);

                for (ResolveInfo activity : cameraActivities) {
                    getActivity().grantUriPermission(activity.activityInfo.packageName,
                            uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                }
                Log.v( TAG, "OnClickListener: cameraActivities:   " + cameraActivities.toString());
                Log.v( TAG, "OnClickListener: captureImageIntent: " + captureImageIntent.toString());
                Log.v( TAG, "OnClickListener: REQUEST_PHOTO:      " + REQUEST_PHOTO);

                // Start Activity *******************************************
                try {
                    Log.v( TAG, "Start the camera activity... ");
                    startActivityForResult(captureImageIntent, REQUEST_PHOTO);
                } catch (Exception e) {
                    Log.e(getString(R.string.app_name), "Failed to start camera activity with intent: " + captureImageIntent.toString());
                    e.printStackTrace();
                }


                Log.v( TAG, "OnClickListener: back: " + REQUEST_PHOTO);

                getActivity().finish();
            }
        });

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            Log.v( TAG, "OnClickListener: NO Permission: " + REQUEST_PHOTO);
        }

        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.READ_CONTACTS)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                Log.v( TAG, "OnClickListener: Need user permission");
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(
                        getActivity(),
                        new String[]{Manifest.permission.CAMERA, "CAMERA_PERMISSION"},
                        REQUEST_PHOTO
                        );

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            Log.v( TAG, "OnClickListener: Already have permission");
        }
        //------------------------------------------------------------------------------------------
        // Picture ImageView
        //------------------------------------------------------------------------------------------
        // ImageView for picture
        mPhotoImageView = v.findViewById(R.id.picture_image_imageview);
        //mPreview = new Preview(getActivity().getApplicationContext());
        //mPreview.setCamera(mCamera);
        //mPhotoImageView.setImageBitmap();

        // 16.12 - Calling updatePhotoView()
        updatePhotoView();

        //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        // Cancel and Save Buttons
        //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        Button mCancelButton = v.findViewById(R.id.picture_cancel_button);
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int messageResId = R.string.cancel_button_text;
                Toast.makeText(getContext(), messageResId, Toast.LENGTH_SHORT).show();
                getActivity().finish();
            }
        });

        Button mSaveButton = v.findViewById(R.id.picture_save_button);
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int messageResId = R.string.save_button_text;
                Toast.makeText(getContext(), messageResId, Toast.LENGTH_SHORT).show();

                // Save data
                // here

                getActivity().finish();
            }
        });

        //^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
        // Return View object
        //^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
        return v;
    }

    //**********************************************************************************************
    // onActivityResult
    //**********************************************************************************************
    // 16.12 - Calling updatePhotoView()
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.v( TAG, "onActivityResult " + resultCode);

        if (resultCode != Activity.RESULT_OK) {
            Log.v( TAG, "onActivityResult resultCode: " + resultCode + " returning...");
            return;
        }
        if (requestCode == REQUEST_PHOTO) {
            Uri uri = FileProvider.getUriForFile(getActivity(),
                    "com.rmeijer.trainman.fileprovider",
                    mPhotoFile);

            getActivity().revokeUriPermission(uri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            Log.v( TAG, "onActivityResult PERMISSION: " + Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            updatePhotoView();
        }
    }

    // 16.11 - Updating mPhotoImageView
    private void updatePhotoView() {
        if (mPhotoFile == null || !mPhotoFile.exists()) {
            Log.v( TAG, "updatePhotoView: mPhotoFile is null!" );
            mPhotoImageView.setImageDrawable(null);
        } else {
            Bitmap bitmap = PictureUtils.getScaledBitmap(
                    mPhotoFile.getPath(), getActivity());
            mPhotoImageView.setImageBitmap(bitmap);
            Log.v( TAG, "updatePhotoView Drawable: " + mPhotoImageView.getDrawable());
        }
    }

    // Check if this device has a camera
    // FEATURE_CAMERA
    // Feature for getSystemAvailableFeatures() and hasSystemFeature(String): The device has a camera facing away from the screen.
    // added in API level 7
    // Constant Value: "android.hardware.camera"
    // FEATURE_CAMERA_FRONT
    // added in API level 9
    // public static final String FEATURE_CAMERA_FRONT
    // Feature for getSystemAvailableFeatures() and hasSystemFeature(String): The device has a front facing camera.
    // Constant Value: "android.hardware.camera.front"
    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            // this device has a camera
            Log.v( TAG, "checkCameraHardware: Found a camera :)" );
            return true;
        } else {
            // no camera on this device
            Log.v( TAG, "checkCameraHardware: No camera found!!!" );
            return false;
        }
    }

    // Later - Determine th id and then implement
    // Creates a new Camera object to access the first back-facing camera on the device.
    // static Camera 	open(int cameraId)
    // Creates a new Camera object to access a particular hardware camera.
    private boolean safeCameraOpen() {
        boolean qOpened = false;
        //Preview mPreview = new Preview(getActivity().getApplicationContext());

        try {
            releaseCameraAndPreview();
            //mCamera = Camera.open();
            Log.v( TAG, "Opened camera: " + mCamera.toString());
            qOpened = (mCamera != null);
        } catch (Exception e) {
            Log.e(getString(R.string.app_name), "failed to open Camera. Camera is not available (in use or does not exist)");
            e.printStackTrace();
        }

        return qOpened;
    }

    private void releaseCameraAndPreview() {
        Log.v( TAG, "releaseCamera:");
        //mPreview.setCamera(null);
        if (mCamera != null) {
            Log.v( TAG, "releaseCamera: Releasing " + mCamera.toString());
            //mCamera.release();
            mCamera = null;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        releaseCameraAndPreview();
    }
}

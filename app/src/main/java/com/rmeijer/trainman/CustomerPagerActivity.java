package com.rmeijer.trainman;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.UUID;

// 11.1 - Created class
// 11.1 - Setting up ViewPager
// 17.7 - Implementing callbacks
public class CustomerPagerActivity extends AppCompatActivity
    implements CustomerHeadFragment.Callbacks {

    // 10.6 - Writing a newInstance(UUID) method
    //??? Update the pager activity intent with new customer_id
    private static final String ARG_CUSTOMER_ID = "customer_id";

    // 11.3 - Creating newIntent
    private static final String EXTRA_CUSTOMER_ID =
            "com.rmeijer.trainman.customer_id";

    // 11.2 - Setting up pager adapter
    private ViewPager mViewPager;
    private List<Customer> mCustomers;

    // Current CustomerHead
    private FragmentStatePagerAdapter pager_adapter;
    private View current_view;
    private UUID current_id;
    private View current_focus_view;
    private View current_focus_view_id;
    private View current_focus_child;
    private ViewGroup current_viewgroup;
    private Fragment current_fragment;
    private int viewpager_position;
    private UUID current_customerId;
    private Customer customer;
    private Customer current_customer;
    private int current_item;

    private UUID mUUID;


    // 17.7 - Implementing callbacks
    @Override
    public void onCustomerSelected(Customer customer) {
    }

    // 11.3 - Creating newIntent
    public static Intent newIntent(Context packageContext, UUID customerId) {
        Intent intent = new Intent(packageContext, CustomerPagerActivity.class);
        intent.putExtra(EXTRA_CUSTOMER_ID, customerId);
        return intent;
    }

    public void updateExtra(UUID customerId) {
        // Get the customer_id from the parent activity
        String EXTRA_CUSTOMER_ID =
                "com.rmeijer.trainman.customer_id";

        // 10.6 - Writing a newInstance(UUID) method
        String ARG_CUSTOMER_ID = "customer_id";

        getIntent().putExtra(EXTRA_CUSTOMER_ID, customerId);
        getIntent().putExtra(ARG_CUSTOMER_ID, customerId);

        // TESTING
        UUID check_customerId = (UUID) getIntent().getSerializableExtra(EXTRA_CUSTOMER_ID);
        Log.v("X" , "****************************************************");
        Log.v("updateExtra", "check_customerId: " + check_customerId.toString());
        Log.v("X" , "****************************************************");
    }

    // 11.1 - Setting up ViewPager
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_pager);

        // 11.3 - Creating newIntent
        final UUID customerId = (UUID) getIntent()
                .getSerializableExtra(EXTRA_CUSTOMER_ID);

        // 11.2 - Setting up pager adapter
        mViewPager = (ViewPager) findViewById(R.id.customer_view_pager);

        mCustomers = CustomerStore.get(this).getCustomers();

        // Change fragmentmanager to fm for consistency
        //FragmentManager fragmentManager = getSupportFragmentManager();
        // 7.15 - Getting the FragmentManager
        FragmentManager fm = getSupportFragmentManager();

        // 7.16 - Adding a UserFragment
        Fragment fragment_fVCust1 = fm.findFragmentById(R.id.fragment_container_fVCust1);

        if (fragment_fVCust1 == null) {
            fragment_fVCust1 = new UserFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container_fVCust1, fragment_fVCust1)
                    .commit();
        }

        // 7.16 - Adding a CustomerMenuFragment
        Fragment fragment_fVCust3 = fm.findFragmentById(R.id.fragment_container_fVCust3);

        if (fragment_fVCust3 == null) {
            fragment_fVCust3 = new CustomerMenuFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container_fVCust3, fragment_fVCust3)
                    .commit();
        }

        // An Adapter object acts as a bridge between an AdapterView and the underlying data for that view.
        // The Adapter provides access to the data items.
        // The Adapter is also responsible for making a View for each item in the data set.
        // An AdapterView is a view whose children are determined by an Adapter.
        // Pager creates multiple fragments which includes tha fragment before and fragment after
        pager_adapter = new FragmentStatePagerAdapter(fm) {

            @Override
            public Fragment getItem(int position) {
                Log.v("pager_adapter: ", "******************************");
                Log.v("pager_adapter: ", "getItem- Position: " + Integer.toString(position));

                // Need to share
                //Customer customer = mCustomers.get(position);
                customer = mCustomers.get(position);

                // Current customer
                current_customer = mCustomers.get(mViewPager.getCurrentItem());
                current_customerId = current_customer.getId();

                // Change to CustomerHeadFragment
                //return ViewCustomerFragment.newInstance(customer.getId());

                Log.v("pager_adapter: ", "Current - beg***********");
                Log.v("pager_adapter: ", "getItem- (Cuurrnt) customerId: " + current_customer.getId().toString());
                Log.v("pager_adapter: ", "getItem- (Current) Name: " + current_customer.getName());
                updateExtra(current_customer.getId());

                if (mViewPager != null) {
                    current_item = mViewPager.getCurrentItem();
                    Log.v("pager_adapter", "getItem- (Current) Current Item: " + Integer.toString(current_item));

                    if (getCurrentFocus() != null) {
                        current_focus_view = getCurrentFocus();
                        Log.v("pager_adapter: ", "getItem- (Current) Current View: " + current_focus_view);
                        current_focus_view_id = current_focus_view.findViewById(R.id.customer_head_customer_id);
                        Log.v("pager_adapter: ", "getItem- (Current) Current View Id: " + current_focus_view_id);
                    }
                    if (mViewPager.getFocusedChild() != null) {
                        current_focus_child = mViewPager.getFocusedChild();
                        current_id = (UUID) mViewPager.getFocusedChild().findViewById(R.id.customer_head_customer_id).getTag();
                        Log.v("pager_adapter: ", "getItem- (Current) Current focus: " + current_focus_child.toString());
                        Log.v("pager_adapter: ", "getItem- (Current) Current customerId: " + current_id.toString());
                    }
                }
                Log.v("X" , "Current - end***********");

                return CustomerHeadFragment.newInstance(customer.getId());
            }

            @Override
            public int getCount() {
                return mCustomers.size();
            }


        };
        mViewPager.setAdapter(pager_adapter);

        // 11.6 - Setting the initial pager item
        // ONE TIME!!!
        for (int i = 0; i < mCustomers.size(); i++) {
            if (mCustomers.get(i).getId().equals(customerId)) {
                mViewPager.setCurrentItem(i);
                viewpager_position = mViewPager.getCurrentItem();
                break;
            }
        }

    }


    @Override
    public void onStop() {
        super.onStop();
        Log.v("Pager Act: ", "onStop *******************************************");
        Log.v("Pager Act: ", "onStop- Current customerId: " + current_customerId);
        if (mViewPager != null) {
            current_item = mViewPager.getCurrentItem();
            Log.v("Pager Act: ", "onStop- Current Item: " + Integer.toString(current_item));
        }
        Log.v("X" , "****************************************************");
    }

}

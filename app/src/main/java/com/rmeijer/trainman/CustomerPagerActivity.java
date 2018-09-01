package com.rmeijer.trainman;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;
import java.util.UUID;

// 11.1 - Created class
// 11.1 - Setting up ViewPager
public class CustomerPagerActivity extends AppCompatActivity {

    // 11.3 - Creating newIntent
    private static final String EXTRA_CUSTOMER_ID =
            "com.rmeijer.trainman.customer_id";

    // 11.2 - Setting up pager adapter
    private ViewPager mViewPager;
    private List<Customer> mCustomers;

    // 11.3 - Creating newIntent
    public static Intent newIntent(Context packageContext, UUID customerId) {
        Intent intent = new Intent(packageContext, CustomerPagerActivity.class);
        intent.putExtra(EXTRA_CUSTOMER_ID, customerId);
        return intent;
    }

    // 11.1 - Setting up ViewPager
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_pager);

        // 11.3 - Creating newIntent
        UUID customerId = (UUID) getIntent()
                .getSerializableExtra(EXTRA_CUSTOMER_ID);

        // 11.2 - Setting up pager adapter
        //Fix for two fragments
        //mViewPager = (ViewPager) findViewById(R.id.customer_view_pager);
        mViewPager = (ViewPager) findViewById(R.id.customer_view_pager);

        mCustomers = CustomerStore.get(this).getCustomers();

        // Author naming for fm not consistent !!!
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

        mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {

            @Override
            public Fragment getItem(int position) {
                Customer customer = mCustomers.get(position);

                // Note: it will be ViewCustomerFragment !!!!
                return ViewCustomerFragment.newInstance(customer.getId());
            }

            @Override
            public int getCount() {
                return mCustomers.size();
            }
        });

        // 11.6 - Setting the initial pager item
        for (int i = 0; i < mCustomers.size(); i++) {
            if (mCustomers.get(i).getId().equals(customerId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }


    }

}

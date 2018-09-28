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
public class SessionPagerActivity extends AppCompatActivity {

    // 11.3 - Creating newIntent
    private static final String EXTRA_CUSTOMER_ID =
            "com.rmeijer.trainman.customer_id";

    // 10.6 - Writing a newInstance(UUID) method
    //??? Update the pager activity intent with new session_id
    private static final String ARG_SESSION_ID = "sessio_id";

    // 11.3 - Creating newIntent
    private static final String EXTRA_SESSION_ID =
            "com.rmeijer.trainman.session_id";

    private UUID customerId;
    //private UUID mUUID;

    // 11.2 - Setting up pager adapter
    private ViewPager mViewPager;
    private List<Customer> mCustomerArray;
    private List<Session> mSessions;

    // Current SessionHead
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
    private UUID current_sessionId;
    private Customer mCustomer;
    private Session mSession;
    private Customer current_customer;
    private Session current_session;
    private int current_item;

    // 11.3 - Creating newIntent
    public static Intent newIntent(Context packageContext, UUID customerId, UUID sessionId) {
        Intent intent = new Intent(packageContext, SessionPagerActivity.class);
        intent.putExtra(EXTRA_CUSTOMER_ID, customerId);
        intent.putExtra(EXTRA_SESSION_ID, sessionId);
        return intent;
    }

    public void updateExtra(UUID sessionId) {
        // Get the customer_id from the parent activity
        getIntent().putExtra(EXTRA_SESSION_ID, sessionId);
        getIntent().putExtra(ARG_SESSION_ID, sessionId);

        // TESTING
        UUID check_sessionId = (UUID) getIntent().getSerializableExtra(EXTRA_SESSION_ID);
        Log.v("Session updateExtra", "****************************************************");
        Log.v("Session updateExtra", "check_sessionId: " + check_sessionId.toString());
        Log.v("Session updateExtra", "****************************************************");
    }

    // 11.1 - Setting up ViewPager
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_pager);

        mSession = new Session();

        //*****************************************************************************************
        // Get EXTRA values
        //*****************************************************************************************
        Context mContext = getApplicationContext();
        // 11.3 - Creating newIntent - Get intent EXTRA
        customerId = (UUID) getIntent().getSerializableExtra(EXTRA_CUSTOMER_ID);
        Log.v("SessionPager: ", "Got Extra customer Id: " + customerId.toString());
        if (customerId != null) {
            CustomerStore sCustomerStore = CustomerStore.get(mContext);
            mCustomer = sCustomerStore.getCustomer(customerId);
            if (mCustomer != null) {
                Log.v("SessionPager: ", "Name " + mCustomer.getName());
                Log.v("SessionPager: ", "Customer Id " + mCustomer.getId().toString());
            } else {
                Log.v("SessionPager: ", "************Customer not found!****************");
            }
        } else {
            Log.v("SessionPager: ", "************Customer ID EXTRA not found!****************");
        }

        final UUID sessionId = (UUID) getIntent().getSerializableExtra(EXTRA_SESSION_ID);
        if (sessionId != null) {
            // ------------------------------------------------------------------------------------
            // Get the Session object from the Session store
            // ------------------------------------------------------------------------------------
            SessionStore sSessionStore = SessionStore.get(mContext);
            mSession = sSessionStore.getSession(customerId, sessionId);
            Log.v("SessionPager: ", "Got Extra session Id: " + sessionId.toString());
            if (mSession != null) {
                Log.v("SessionPager: ", "Service " + mSession.getService());
                Log.v("SessionPager: ", "Session Id " + mSession.getId().toString());
            } else {
                Log.v("SessionPager: ", "************Session not found!****************");
            }
        } else {
            Log.v("SessionPager: ", "************Session ID EXTRA not found!****************");
        }

        //*****************************************************************************************
        // Set up the layout
        //*****************************************************************************************

        // Change fragmentmanager to fm for consistency
        //FragmentManager fragmentManager = getSupportFragmentManager();
        // 7.15 - Getting the FragmentManager
        FragmentManager fm = getSupportFragmentManager();

        // 7.16 - Adding a UserFragment
        Fragment fragment_fViewSession1 = fm.findFragmentById(R.id.fragment_container_fViewSession1);
        if (fragment_fViewSession1 == null) {
            fragment_fViewSession1 = new UserFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container_fViewSession1, fragment_fViewSession1)
                    .commit();
        }

        // 7.16 - Adding a CustomerHeadFragment
        Fragment fragment_fViewSession2 = fm.findFragmentById(R.id.fragment_container_fViewSession2);
        if (fragment_fViewSession2 == null) {
            fragment_fViewSession2 = new SessionHeadFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container_fViewSession2, fragment_fViewSession2)
                    .commit();
        }

        // This Fragment - Fragment fragment_fView Session3 = fm.findFragmentById(R.id.fragment_container_fViewSession3);
        // is handled by the pager: android.support.v4.view.ViewPager: fragment_container_fViewSession2_session_view_pager
        // 11.2 - Setting up pager adapter
        mViewPager = findViewById(R.id.fragment_container_fViewSession3_session_view_pager);

        // 7.16 - Adding a CustomerMenuFragment
        Fragment fragment_fViewSession4 = fm.findFragmentById(R.id.fragment_container_fViewSession4);
        if (fragment_fViewSession4 == null) {
            fragment_fViewSession4 = new SessionMenuFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container_fViewSession4, fragment_fViewSession4)
                    .commit();
        }

        //*****************************************************************************************
        // Set up the pager with mSessionS - PLURAL!!!!!!!!!!!!!!!!!!
        //*****************************************************************************************
        mSessions = SessionStore.get(this).getSessions(customerId);

        // An Adapter object acts as a bridge between an AdapterView and the underlying data for that view.
        // The Adapter provides access to the data items.
        // The Adapter is also responsible for making a View for each item in the data set.
        // An AdapterView is a view whose children are determined by an Adapter.
        // Pager creates multiple fragments which includes tha fragment before and fragment after
        pager_adapter = new FragmentStatePagerAdapter(fm) {

            @Override
            public Fragment getItem(int position) {
                Log.v("Session pager_adapter: ", "getItem ******************************");
                Log.v("Session pager_adapter: ", "getItem- Position: " + Integer.toString(position));

                // Need to share
                //Customer customer = mCustomers.get(position);
                mSession = mSessions.get(position);

                // Current session
                current_session = mSessions.get(mViewPager.getCurrentItem());
                current_sessionId = current_session.getId();

                // Change to SessionHeadFragment
                //return SessionViewFragment.newInstance(session.getId());

                Log.v("Session pager_adapter: ", "Current - beg***********");
                Log.v("Session pager_adapter: ", "getItem- (Cuurrnt) sessionId: " + current_session.getId().toString());
                Log.v("Session pager_adapter: ", "getItem- (Current) Service: " + current_session.getService());
                updateExtra(current_session.getId());

                if (mViewPager != null) {
                    current_item = mViewPager.getCurrentItem();
                    Log.v("Session pager_adapter", "getItem- (Current) Current Item: " + Integer.toString(current_item));
                }
                Log.v("Session pager_adapter:" , "Current - end***********");

                return SessionHeadViewFragment.newInstance(mCustomer.getId(), mSession.getId());
            }

            @Override
            public int getCount() {
                return mSessions.size();
            }


        };
        mViewPager.setAdapter(pager_adapter);

        // 11.6 - Setting the initial pager item
        // ONE TIME!!!
        for (int i = 0; i < mSessions.size(); i++) {
            if (mSessions.get(i).getId().equals(sessionId)) {
                mViewPager.setCurrentItem(i);
                viewpager_position = mViewPager.getCurrentItem();
                break;
            }
        }

    }


    @Override
    public void onStop() {
        super.onStop();
        Log.v("Session Pager Act: ", "onStop *******************************************");
        Log.v("Session Pager Act: ", "onStop- Current sessionId: " + current_sessionId);
        if (mViewPager != null) {
            current_item = mViewPager.getCurrentItem();
            Log.v("Session Pager Act: ", "onStop- Current Item: " + Integer.toString(current_item));
        }
        Log.v("Session Pager Act: ", "onStop ********************************************");
    }

}

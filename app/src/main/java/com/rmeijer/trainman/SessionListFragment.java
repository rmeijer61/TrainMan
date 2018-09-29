package com.rmeijer.trainman;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

// 8.11 - Implementing CustomerListFragment
public class SessionListFragment extends Fragment {

    // 11.3 - Creating newIntent
    private static final String EXTRA_CUSTOMER_ID =
            "com.rmeijer.trainman.customer_id";

    // 10.6 - Writing a newInstance(UUID) method
    private static final String ARG_CUSTOMER_ID = "customer_id";

    // 10.6 - Writing a newInstance(UUID) method
    private static final String ARG_SESSION_ID = "session_id";

    // 13.19 - Saving subtitle visibility
    private static final String SAVED_SUBTITLE_VISIBLE = "subtitle";

    // 8.16 - Setting up the view for CustomerListFragment
    private RecyclerView mSessionRecyclerView;

    // 8.20 - Setting an Adapter
    // Changed to local
    private SessionAdapter mAdapter;

    // 13.15 - Keeping subtitle visibility state
    private boolean mSubtitleVisible;

    UUID customerId;
    private Customer mCustomer;
    private CheckBox mCompletedCheckBox;
    private CheckBox mPaidCheckBox;

    // 13.7 - Receiving menu callbacks
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_session_list, container, false);

        //*****************************************************************************************
        // Get intent EXTRA(s)
        //*****************************************************************************************
        customerId = (UUID) Objects.requireNonNull(getActivity()).getIntent().getSerializableExtra(EXTRA_CUSTOMER_ID);
        Log.v("SessionList: ", "Got Extra customer Id: " + customerId.toString());
        if (customerId != null) {
            mCustomer = CustomerStore.get(getActivity()).getCustomer(customerId);
            if (mCustomer != null) {
                Log.v("SessionList: ", "Name " + mCustomer.getName());
                Log.v("SessionList: ", "Customer Id " + mCustomer.getId().toString());
            } else {
                Log.v("SessionList: ", "************Customer not found!****************");
            }
        } else {
            Log.v("SessionList: ", "************Customer ID EXTRA not found!****************");
        }

        // Removed cast
        mSessionRecyclerView = view.findViewById(R.id.session_recycler_view);
        mSessionRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // 13.19 - Saving subtitle visibility
        if (savedInstanceState != null) {
            mSubtitleVisible = savedInstanceState.getBoolean(SAVED_SUBTITLE_VISIBLE);
        }
        // end 13.19

        // 8.20 - Setting an Adapter
        updateUI(customerId);

        mCustomer = new Customer();

        return view;
    }

    // Mysteriously appears in 13.6 - Inflating a menu resource
    @Override
    public void onResume() {
        super.onResume();
        updateUI(customerId);
    }

    // 13.19 - Saving subtitle visibility
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVED_SUBTITLE_VISIBLE, mSubtitleVisible);
    }
    // end 13.19

    // 13.6 - Inflating a menu resource
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_session_list, menu);
    }

    // 13.10 - Responding to menu selection
    // returns a boolean value
    // Defined in res/menu/fragment_session_list
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_session:
                // Start CustomerEnterActivity
                UUID customerId = (UUID) Objects.requireNonNull(getActivity()).getIntent().getSerializableExtra(EXTRA_CUSTOMER_ID);
                Log.v("SessionList: ", "Got Extra customer Id: " + customerId.toString());
                Intent intent = new Intent(getActivity(), SessionEnterActivity.class);
                intent.putExtra(EXTRA_CUSTOMER_ID, customerId);
                startActivity(intent);
                return true;
            // 13.14 - Responding to SHOW SUBTITLE action item
            case R.id.show_subtitle:
                // 13.16 - Updating a MenuItem
                mSubtitleVisible = !mSubtitleVisible;
                Objects.requireNonNull(getActivity()).invalidateOptionsMenu();
                // end 13.16
                updateSubtitle();
                return true;
            // end 13.14
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    // end 13.10

    // 13.13 - Setting the toolbar’s subtitle
    private void updateSubtitle() {
        //--------------------------------------------------------------
        // Query sessions
        //--------------------------------------------------------------
        SessionStore sessionStore = SessionStore.get(getActivity());
        int sessionCount = sessionStore.getSessions(customerId).size();

        String subtitle = getString(R.string.session_list_subtitle_format, sessionCount);

        // 13.17 - Showing or hiding the subtitle
        if (!mSubtitleVisible) {
            subtitle = null;
        }
        // end 13.17

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        Objects.requireNonNull(Objects.requireNonNull(activity).getSupportActionBar()).setSubtitle(subtitle);
    }
    // end 13.13

    // 8.20 - Setting an Adapter
    // Later: you will add more to updateUI() as configuring your UI gets more involved
    private void updateUI(UUID customerId) {
        //--------------------------------------------------------------
        // Query sessions
        //--------------------------------------------------------------
        SessionStore sessionStore = SessionStore.get(getActivity());
        List<Session> sessions = sessionStore.getSessions(customerId);

        SessionAdapter mAdapter = new SessionAdapter(sessions);
        mSessionRecyclerView.setAdapter(mAdapter);

        // 10.9 - Reloading the list in onResume()
        //mAdapter = new SessionAdapter(sessions);
        //mSessionRecyclerView.setAdapter(mAdapter);
        if (mAdapter == null) {
            mAdapter = new SessionAdapter(sessions);
            mSessionRecyclerView.setAdapter(mAdapter);
        } else {
            // 14.21 - Calling setSessions(List<>)
            mAdapter.setSessions(sessions);
            // end 14.21
            mAdapter.notifyDataSetChanged();
        }
    }

    // 8.17 - The beginnings of a ViewHolder
    //private class SessionHolder extends RecyclerView.ViewHolder {
    // 8.24 - Detecting presses in SessionHolder
    private class SessionHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // 8.21 - Pulling out views in the constructor
        private TextView mServiceTextView;
        private TextView mDateTextView;

        // 8.22 - Writing a bind(Session) method
        private Session mSession;


        private SessionHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_session, parent, false));

            // 8.24 - Detecting presses in SessionHolder
            itemView.setOnClickListener(this);

            // 8.21 - Pulling out views in the constructor
            mServiceTextView = itemView.findViewById(R.id.session_list_session_type_textview);
            mDateTextView = itemView.findViewById(R.id.session_list_session_date);
            mCompletedCheckBox = itemView.findViewById(R.id.session_list_completed_checkbox);
            mPaidCheckBox = itemView.findViewById(R.id.session_list_paid_checkbox);
        }

        // 8.22 - Writing a bind(Customer) method
        public void bind(Session session) {
            mSession = session;
            // Service
            mServiceTextView.setText(mSession.getService());
            // Service date
            Calendar calDate = Calendar.getInstance();
            calDate.setTime(mSession.getSessionDate());
            int year = calDate.get(Calendar.YEAR);
            int month = calDate.get(Calendar.MONTH);
            int day = calDate.get(Calendar.DAY_OF_MONTH);
            String dateString = (month+1) + "/" + day + "/" + year;
            mDateTextView.setText(dateString);
            // Completed
            mCompletedCheckBox.setChecked(mSession.isCompleted());
            // Paid
            mPaidCheckBox.setChecked(mSession.isPaid());
        }

        // 8.24 - Detecting presses in SessionHolder
        @Override
        public void onClick(View view) {
            // Go to Session main - (Update: Go to pager instead)
            // 10.3 - Stashing and passing a Session
            //Intent intent = new Intent(getActivity(), SessionViewActivity.class);
            // 11.4 - Firing it up
            //Intent intent = SessionViewActivity.newIntent(getActivity(), mSession.getId());

            Log.v("SessionList: ", "Create intent with customer Id: " + customerId);
            Log.v("SessionList: ", "Create intent with customer Id: " + mSession.getId().toString());

            Intent intent = SessionPagerActivity.newIntent(getActivity(), customerId, mSession.getId());

            //**************************************************************************************
            //Create arguments in the pager activity intent for the customerId and session_id
            intent.putExtra(ARG_CUSTOMER_ID, mCustomer.getId());
            intent.putExtra(ARG_SESSION_ID, mSession.getId());
            //**************************************************************************************

            startActivity(intent);
        }
    }

    // 8.18 - The beginnings of an adapter
    // Note: The code in Listing 8.18 will not compile. You will fix this in a moment
    // > implement three method overrides in SessionAdapter
    private class SessionAdapter extends RecyclerView.Adapter<SessionHolder> {

        private List<Session> mSessions;

        private SessionAdapter(List<Session> sessions) {
            mSessions = sessions;
        }

        @NonNull
        @Override
        public SessionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            // 8.19 - Filling out CustomerAdapter
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            //return null;
            return new SessionHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull SessionHolder holder, int position) {
            // 8.19 - Filling out SessionAdapter
            // Adapter must have an override for onBindViewHolder(…),
            //	but for now, you can leave it empty. In a moment, you will return to it

            // 8.23 - Calling the bind(Customer) method
            Session session = mSessions.get(position);
            holder.bind(session);

        }

        @Override
        public int getItemCount() {
            // 8.19 - Filling out SessionAdapter
            //return 0;
            return mSessions.size();
        }

        // 14.20 - Adding setSessions(List<Session>)
        private void setSessions(List<Session> sessions) {
            mSessions = sessions;
        }
        // 14.20

    }

    @Override
    public void onStop() {
        super.onStop();
        Log.v("SessionList: ", "onStop *******************************************");
        Log.v("SessionList: ", "onStop- Current customer Id: " + customerId);
        Log.v("SessionList: ", "onStop *******************************************");
    }

}

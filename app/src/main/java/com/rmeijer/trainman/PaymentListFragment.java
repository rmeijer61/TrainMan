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
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

// 8.11 - Implementing CustomerListFragment
public class PaymentListFragment extends Fragment {

    // Logging Tag
    String TAG = "PaymentList: ";

    // 11.3 - Creating newIntent
    private static final String EXTRA_CUSTOMER_ID = "com.rmeijer.trainman.customer_id";
    private static final String ARG_CUSTOMER_ID = "customer_id";

    // 10.6 - Writing a newInstance(UUID) method
    private static final String EXTRA_SESSION_ID = "com.rmeijer.trainman.session_id";
    private static final String ARG_SESSION_ID = "session_id";

    // 11.3 - Creating newIntent
    private static final String EXTRA_PAYMENT_ID = "com.rmeijer.trainman.payment_id";
    private static final String ARG_PAYMENT_ID = "payment_id";

    // 13.19 - Saving subtitle visibility
    private static final String SAVED_SUBTITLE_VISIBLE = "subtitle";

    // 8.16 - Setting up the view for CustomerListFragment
    private RecyclerView mPaymentRecyclerView;

    // 8.20 - Setting an Adapter
    // Changed to local
    private PaymentAdapter mAdapter;

    // 13.15 - Keeping subtitle visibility state
    private boolean mSubtitleVisible;

    UUID customerId;
    private Customer mCustomer;

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
        View view = inflater.inflate(R.layout.fragment_payment_list, container, false);

        //*****************************************************************************************
        // Get intent EXTRA(s)
        //*****************************************************************************************
        customerId = (UUID) Objects.requireNonNull(getActivity()).getIntent().getSerializableExtra(EXTRA_CUSTOMER_ID);
        Log.v(TAG, "Got Extra customer Id: " + customerId.toString());
        if (customerId != null) {
            mCustomer = CustomerStore.get(getActivity()).getCustomer(customerId);
            if (mCustomer != null) {
                Log.v(TAG, "Name " + mCustomer.getName());
                Log.v(TAG, "Customer Id " + mCustomer.getId().toString());
            } else {
                Log.v(TAG, "************Customer not found!****************");
            }
        } else {
            Log.v(TAG, "************Customer ID EXTRA not found!****************");
        }

        // Removed cast
        mPaymentRecyclerView = view.findViewById(R.id.payment_recycler_view);
        mPaymentRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

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
        inflater.inflate(R.menu.fragment_payment_list, menu);
    }

    // 13.10 - Responding to menu selection
    // returns a boolean value
    // Defined in res/menu/fragment_payment_list
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_session:
                // Start CustomerEnterActivity
                UUID customerId = (UUID) Objects.requireNonNull(getActivity()).getIntent().getSerializableExtra(EXTRA_CUSTOMER_ID);
                Log.v(TAG, "Got Extra customer Id: " + customerId.toString());
                Intent intent = new Intent(getActivity(), PaymentEnterActivity.class);
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
        PaymentStore paymentStore = PaymentStore.get(getActivity());
        int paymentCount = paymentStore.getPayments(customerId).size();

        String subtitle = getString(R.string.payment_list_subtitle_format, paymentCount);

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
        PaymentStore paymentStore = PaymentStore.get(getActivity());
        List<Payment> payments = paymentStore.getPayments(customerId);

        PaymentAdapter mAdapter = new PaymentAdapter(payments);
        mPaymentRecyclerView.setAdapter(mAdapter);

        // 10.9 - Reloading the list in onResume()
        //mAdapter = new PaymentAdapter(sessions);
        //mPaymentRecyclerView.setAdapter(mAdapter);
        // 14.21 - Calling setSessions(List<>)
        mAdapter.setPayments(payments);
        // end 14.21
        mAdapter.notifyDataSetChanged();
    }

    // 8.17 - The beginnings of a ViewHolder
    //private class PaymentHolder extends RecyclerView.ViewHolder {
    // 8.24 - Detecting presses in PAymentHolder
    private class PaymentHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // 8.21 - Pulling out views in the constructor
        private TextView mPaymentAmountTextView;
        private TextView mPaymentDateTextView;

        // 8.22 - Writing a bind(Payment) method
        private Payment mPayment;


        private PaymentHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_payment, parent, false));

            // 8.24 - Detecting presses in SessionHolder
            itemView.setOnClickListener(this);

            // 8.21 - Pulling out views in the constructor
            mPaymentAmountTextView = itemView.findViewById(R.id.payment_list_payment_amount);
            mPaymentDateTextView = itemView.findViewById(R.id.payment_list_payment_date);
        }

        // 8.22 - Writing a bind(Customer) method
        public void bind(Payment payment) {
            mPayment = payment;
            mPaymentAmountTextView.setText("Payment Amount: " + Double.valueOf(mPayment.getAmount()).toString());
            // Format the date
            Calendar calDate = Calendar.getInstance();
            calDate.setTime(mPayment.getPayDate());
            int year = calDate.get(Calendar.YEAR);
            int month = calDate.get(Calendar.MONTH);
            int day = calDate.get(Calendar.DAY_OF_MONTH);
            String dateString = "Payment Date:   " + (month+1) + "/" + day + "/" + year;
            mPaymentDateTextView.setText(dateString);
        }

        // 8.24 - Detecting presses in SessionHolder
        @Override
        public void onClick(View view) {
            Log.v(TAG, "Create intent with customer Id: " + customerId);
            Log.v(TAG, "Create intent with payment Id: " + mPayment.getId().toString());

            // Get the extra from the activity intent
            UUID customerId = (UUID) Objects.requireNonNull(getActivity()).getIntent().getSerializableExtra(EXTRA_CUSTOMER_ID);
            Log.v(TAG, "Got Extra customer Id: " + customerId.toString());

            // Start SessionEnterActivity
            Intent intent = new Intent(getActivity(), PaymentViewActivity.class);
            intent.putExtra(EXTRA_CUSTOMER_ID, customerId);
            intent.putExtra(EXTRA_PAYMENT_ID, mPayment.getId());
            startActivity(intent);
        }
    }

    // 8.18 - The beginnings of an adapter
    // Note: The code in Listing 8.18 will not compile. You will fix this in a moment
    // > implement three method overrides in PaymentAdapter
    private class PaymentAdapter extends RecyclerView.Adapter<PaymentHolder> {

        private List<Payment> mPayments;

        private PaymentAdapter(List<Payment> payments) {
            mPayments = payments;
        }

        @NonNull
        @Override
        public PaymentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            // 8.19 - Filling out PaymentAdapter
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            //return null;
            return new PaymentHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull PaymentHolder holder, int position) {
            // 8.19 - Filling out PaymentAdapter
            // Adapter must have an override for onBindViewHolder(…),
            //	but for now, you can leave it empty. In a moment, you will return to it

            // 8.23 - Calling the bind(Customer) method
            Payment payment = mPayments.get(position);
            holder.bind(payment);

        }

        @Override
        public int getItemCount() {
            // 8.19 - Filling out PaymentAdapter
            //return 0;
            return mPayments.size();
        }

        // 14.20 - Adding setPayments(List<Payment>)
        private void setPayments(List<Payment> payments) {
            mPayments = payments;
        }
        // 14.20

    }

    @Override
    public void onStop() {
        super.onStop();
        Log.v(TAG, "onStop *******************************************");
        Log.v(TAG, "onStop- Current customer Id: " + customerId);
        Log.v(TAG, "onStop *******************************************");
    }

}

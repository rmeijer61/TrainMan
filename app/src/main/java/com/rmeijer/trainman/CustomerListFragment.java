package com.rmeijer.trainman;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

// 8.11 - Implementing CustomerListFragment
public class CustomerListFragment extends Fragment {

    // 10.6 - Writing a newInstance(UUID) method
    private static final String ARG_CUSTOMER_ID = "customer_id";

    // 13.19 - Saving subtitle visibility
    private static final String SAVED_SUBTITLE_VISIBLE = "subtitle";

    // 8.16 - Setting up the view for CustomerListFragment
    private RecyclerView mCustomerRecyclerView;

    // 8.20 - Setting an Adapter
    // Changed to local
    private CustomerAdapter mAdapter;

    // 13.15 - Keeping subtitle visibility state
    private boolean mSubtitleVisible;

    // 13.7 - Receiving menu callbacks
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer_list, container, false);

        // Removed cast
        mCustomerRecyclerView = (RecyclerView) view.findViewById(R.id.customer_recycler_view);
        mCustomerRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // 13.19 - Saving subtitle visibility
        if (savedInstanceState != null) {
            mSubtitleVisible = savedInstanceState.getBoolean(SAVED_SUBTITLE_VISIBLE);
        }
        // end 13.19

        // 8.20 - Setting an Adapter
        updateUI();

        return view;
    }

    // Mysteriously appears in 13.6 - Inflating a menu resource
    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    // 13.19 - Saving subtitle visibility
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVED_SUBTITLE_VISIBLE, mSubtitleVisible);
    }
    // end 13.19

    // 13.6 - Inflating a menu resource
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_customer_list, menu);
    }

    // 13.10 - Responding to menu selection
    // returns a boolean value
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_customer:
                // Start EnterCustomerActivity
                Intent intent = new Intent(getActivity(), EnterCustomerActivity.class);
                startActivity(intent);
                return true;
            // 13.14 - Responding to SHOW SUBTITLE action item
            case R.id.show_subtitle:
                // 13.16 - Updating a MenuItem
                mSubtitleVisible = !mSubtitleVisible;
                getActivity().invalidateOptionsMenu();
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
        CustomerStore customerstore = CustomerStore.get(getActivity());
        int customerCount = customerstore.getCustomers().size();
        String subtitle = getString(R.string.subtitle_format, customerCount);

        // 13.17 - Showing or hiding the subtitle
        if (!mSubtitleVisible) {
            subtitle = null;
        }
        // end 13.17

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(subtitle);
    }
    // end 13.13

    // 8.20 - Setting an Adapter
    // Later: you will add more to updateUI() as configuring your UI gets more involved
    private void updateUI() {
        CustomerStore customerStore = CustomerStore.get(getActivity());
        List<Customer> customers = customerStore.getCustomers();

        CustomerAdapter mAdapter = new CustomerAdapter(customers);
        mCustomerRecyclerView.setAdapter(mAdapter);

        // 10.9 - Reloading the list in onResume()
        //mAdapter = new CustomerAdapter(customers);
        //mCustomerRecyclerView.setAdapter(mAdapter);
        if (mAdapter == null) {
            mAdapter = new CustomerAdapter(customers);
            mCustomerRecyclerView.setAdapter(mAdapter);
        } else {
            // 14.21 - Calling setCustomers(List<>)
            mAdapter.setCustomers(customers);
            // end 14.21
            mAdapter.notifyDataSetChanged();
        }
    }

    // 8.17 - The beginnings of a ViewHolder
    //private class CustomerHolder extends RecyclerView.ViewHolder {
    // 8.24 - Detecting presses in CustomerHolder
    private class CustomerHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // 8.21 - Pulling out views in the constructor
        private TextView mNameTextView;
        private TextView mDateTextView;

        // 8.22 - Writing a bind(Customer) method
        private Customer mCustomer;


        public CustomerHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_customer, parent, false));

            // 8.24 - Detecting presses in CustomerHolder
            itemView.setOnClickListener(this);

            // 8.21 - Pulling out views in the constructor
            mNameTextView = itemView.findViewById(R.id.customer_list_name);
            mDateTextView = itemView.findViewById(R.id.customer_list_date);
        }

        // 8.22 - Writing a bind(Customer) method
        public void bind(Customer customer) {
            mCustomer = customer;
            mNameTextView.setText(mCustomer.getName());
            mDateTextView.setText(mCustomer.getDate().toString());
        }

        // 8.24 - Detecting presses in CustomerHolder
        @Override
        public void onClick(View view) {
            // Go to Customer main - (Update: Go to pager instead)
            // 10.3 - Stashing and passing a Customer
            //Intent intent = new Intent(getActivity(), ViewCustomerActivity.class);
            // 11.4 - Firing it up
            //Intent intent = ViewCustomerActivity.newIntent(getActivity(), mCustomer.getId());

            Intent intent = CustomerPagerActivity.newIntent(getActivity(), mCustomer.getId());
            //Create an argument in the pager activity intent for the customer_id
            intent.putExtra(ARG_CUSTOMER_ID, mCustomer.getId());

            startActivity(intent);
        }
    }

    // 8.18 - The beginnings of an adapter
    // Note: The code in Listing 8.18 will not compile. You will fix this in a moment
    // > implement three method overrides in CustomerAdapter
    private class CustomerAdapter extends RecyclerView.Adapter<CustomerHolder> {

        private List<Customer> mCustomers;

        public CustomerAdapter(List<Customer> customers) {
            mCustomers = customers;
        }

        @NonNull
        @Override
        public CustomerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            // 8.19 - Filling out CustomerAdapter
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            //return null;
            return new CustomerHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull CustomerHolder holder, int position) {
            // 8.19 - Filling out CustomerAdapter
            // Adapter must have an override for onBindViewHolder(…),
            //	but for now, you can leave it empty. In a moment, you will return to it

            // 8.23 - Calling the bind(Customer) method
            Customer customer = mCustomers.get(position);
            holder.bind(customer);

        }

        @Override
        public int getItemCount() {
            // 8.19 - Filling out CustomerAdapter
            //return 0;
            return mCustomers.size();
        }

        // 14.20 - Adding setCustomers(List<Customer>)
        public void setCustomers(List<Customer> customers) {
            mCustomers = customers;
        }
        // 14.20

    }
}

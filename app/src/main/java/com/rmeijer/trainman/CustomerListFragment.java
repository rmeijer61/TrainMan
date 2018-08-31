package com.rmeijer.trainman;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

// 8.11 - Implementing CustomerListFragment
public class CustomerListFragment extends Fragment {
    // 8.16 - Setting up the view for CustomerListFragment
    private RecyclerView mCustomerRecyclerView;

    // 8.20 - Setting an Adapter
    // Changed to local
    private CustomerAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer_list, container, false);

        // Removed cast
        mCustomerRecyclerView = (RecyclerView) view.findViewById(R.id.customer_recycler_view);
        mCustomerRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // 8.20 - Setting an Adapter
        updateUI();

        return view;
    }

    // 8.20 - Setting an Adapter
    // Later: you will add more to updateUI() as configuring your UI gets more involved
    private void updateUI() {
        CustomerStore customerStore = CustomerStore.get(getActivity());
        List<Customer> customers = customerStore.getCustomers();

        CustomerAdapter mAdapter = new CustomerAdapter(customers);
        mCustomerRecyclerView.setAdapter(mAdapter);

        // 10.9 - Reloading the list in onResume()
        //mAdapter = new CrimeAdapter(crimes);
        //mCrimeRecyclerView.setAdapter(mAdapter);
        if (mAdapter == null) {
            mAdapter = new CustomerAdapter(customers);
            mCustomerRecyclerView.setAdapter(mAdapter);
        } else {
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
            mNameTextView = itemView.findViewById(R.id.customer_name);
            //mDateTextView = itemView.findViewById(R.id.customer_date);
        }

        // 8.22 - Writing a bind(Customer) method
        public void bind(Customer customer) {
            mCustomer = customer;
            mNameTextView.setText(mCustomer.getName());
            //mDateTextView.setText(mCustomer.getDate().toString());
        }

        // 8.24 - Detecting presses in CustomerHolder
        @Override
        public void onClick(View view) {
            //Toast.makeText(getActivity(),
            //         "Selected " + mCustomer.getName(), Toast.LENGTH_SHORT)
            //        .show();

            // Go to Customer main
            // 10.3 - Stashing and passing a Crime
            //Intent intent = new Intent(getActivity(), ViewCustomerActivity.class);
            Intent intent = ViewCustomerActivity.newIntent(getActivity(), mCustomer.getId());

            startActivity(intent);
            // ???
            getActivity().finish();
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



    }
}

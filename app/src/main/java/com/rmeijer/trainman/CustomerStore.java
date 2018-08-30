package com.rmeijer.trainman;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CustomerStore {
    // 8.1 - Setting up the singleton
    private static CustomerStore sCustomerStore;

    // 8.2 - Setting up the List of Crime objects
    private List<Customer> mCustomers;

    // 8.1 - Setting up the singleton
    public static CustomerStore get(Context context) {
        if (sCustomerStore == null) {
            sCustomerStore = new CustomerStore(context);
        }
        return sCustomerStore;
    }

    // 8.1 - Setting up the singleton
    private CustomerStore(Context context) {
        // 8.2 - Setting up the List of Crime objects
        mCustomers = new ArrayList<>();

        // 8.3 - Generating customers
        for (int i = 100; i < 120; i++) {
            Customer customer = new Customer();
            customer.setName("Customer #" + i);
            mCustomers.add(customer);
        }

    }

    // 8.2 - Setting up the List of Customer objects
    public List<Customer> getCustomers() {
        return mCustomers;
    }

    public Customer getCustomer(UUID id) {
        for (Customer customer : mCustomers) {
            if (customer.getId().equals(id)) {
                return customer;
            }
        }

        return null;
    }
}

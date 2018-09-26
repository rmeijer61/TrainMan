package com.rmeijer.trainman;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.rmeijer.trainman.database.CustomerBaseHelper;
import com.rmeijer.trainman.database.CustomerCursorWrapper;
import com.rmeijer.trainman.database.CustomerDbSchema;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CustomerStore {

    final String TAG = "CustomerStore: ";

    // 8.1 - Setting up the singleton
    private static CustomerStore sCustomerStore;

    // 14.4 - Opening a SQLiteDatabase
    private Context mContext;
    private SQLiteDatabase mDatabase;

    //**********************************************************************************************
    // Methods
    //**********************************************************************************************

    // 13.8 - Adding a new customer
    public void addCustomer(Customer c) {
        // 14.7 - Tearing down some walls
        //mCustomers.add(c);

        // 14.9 - Inserting a row
        ContentValues values = getContentValues(c);
        mDatabase.insert(CustomerDbSchema.CustomerTable.TABLE_NAME, null, values);
    }

    // Delete customer row
    // ???? Need to check return code ????
    public void deleteCustomer(UUID customerId)
    {
        String uuidString = customerId.toString();
        mDatabase.delete(CustomerDbSchema.CustomerTable.TABLE_NAME,
           CustomerDbSchema.CustomerTable.Cols.UUID + " = ?",
            new String[] {uuidString}
            );
    }

    // 14.7 - Tearing down some walls
    // 8.2 - Setting up the List of Customer objects
    //private List<Customer> mCustomers;
    // end 14.7

    // 8.1 - Setting up the singleton
    public static CustomerStore get(Context context) {
        if (sCustomerStore == null) {
            sCustomerStore = new CustomerStore(context);
        }
        return sCustomerStore;
    }

    // 8.1 - Setting up the singleton
    private CustomerStore(Context context) {
        // 14.4 - Opening a SQLiteDatabase
        mContext = context.getApplicationContext();
        mDatabase = new CustomerBaseHelper(mContext)
                .getWritableDatabase();
        // end 14.4

        // 14.7 - Tearing down some walls
        // 8.2 - Setting up the List of Customer objects
        //mCustomers = new ArrayList<>();
        // end 14.7

        /** 13.9 - Remove random customers
        // 8.3 - Generating customers
        for (int i = 100; i < 120; i++) {
            Customer customer = new Customer();
            customer.setName("Customer #" + i);
            mCustomers.add(customer);
        }
        end 13.9 */
    }

    // 8.2 - Setting up the List of Customer objects
    public List<Customer> getCustomers() {
        // 14.7 - Tearing down some walls
        //return mCustomers;

        // 14.18 - Returning customer list
        //return new ArrayList<>();
        List<Customer> customerArray = new ArrayList<>();

        CustomerCursorWrapper cursor = queryCustomers(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                customerArray.add(cursor.getCustomerRow());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return customerArray;
        //end 14.18
    }

    // Why not static?
    public Customer getCustomer(UUID id) {

        // 14.7 - Tearing down some walls
        /**
        for (Customer customer : mCustomers) {
           if (customer.getId().equals(id)) {
               return customer;
           }
        }
        */
        // 14.19 - Rewriting getCustomer(UUID)
        //return null;
        CustomerCursorWrapper cursor = queryCustomers(
                CustomerDbSchema.CustomerTable.Cols.UUID + " = ?",
                new String[] { id.toString() }
        );

        try {
            if (cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToFirst();
            return cursor.getCustomerRow();
        } finally {
            cursor.close();
        }
        // end 14.19

    }

    // 14.10 - Updating a Customer
    public void updateCustomer(Customer customer) {
        String uuidString = customer.getId().toString();
        ContentValues values = getContentValues(customer);

        mDatabase.update(CustomerDbSchema.CustomerTable.TABLE_NAME, values,
                CustomerDbSchema.CustomerTable.Cols.UUID + " = ?",
                new String[] { uuidString });
    }
    // end 14.10

    //**********************************************************************************************
    // 16.6 - Finding photo file location
    //**********************************************************************************************
    public File getPhotoFile(Customer customer) {
        Log.v( TAG, "getPhotoFile: " + customer.toString());
        File filesDir = mContext.getFilesDir();
        Log.v( TAG, "getPhotoFile: FilesDir is: " + filesDir.toURI().toString());
        return new File(filesDir, customer.getPhotoFilename());
    }

    //**********************************************************************************************
    // SQLite wrapper and content values
    //**********************************************************************************************
    // 14.17 - 14.17 - Vending cursor wrapper
    // 14.12 - Querying for Customers
    //private Cursor queryCustomers(String whereClause, String[] whereArgs) {
    private CustomerCursorWrapper queryCustomers(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                CustomerDbSchema.CustomerTable.TABLE_NAME,
                null, // columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null  // orderBy
        );

        //return cursor;
        return new CustomerCursorWrapper(cursor);
    }
    // end 14.12
    // end 14.17

    // 14.8 - Creating a ContentValues
    // Writes and updates to databases are done with the assistance of a class called ContentValues
    // ContentValues is a key-value store class, specifically designed to store the kinds of data SQLite can hold
    private static ContentValues getContentValues(Customer customer) {
        ContentValues values = new ContentValues();
        values.put(CustomerDbSchema.CustomerTable.Cols.UUID, customer.getId().toString());
        values.put(CustomerDbSchema.CustomerTable.Cols.DATE, customer.getDate().getTime());
        values.put(CustomerDbSchema.CustomerTable.Cols.NAME, customer.getName());
        values.put(CustomerDbSchema.CustomerTable.Cols.GENDER, customer.getGender());
        values.put(CustomerDbSchema.CustomerTable.Cols.BIRTHDATE, customer.getBirthDate().getTime());
        values.put(CustomerDbSchema.CustomerTable.Cols.PHONE1, customer.getPhone1());
        values.put(CustomerDbSchema.CustomerTable.Cols.PHONE2, customer.getPhone2());
        values.put(CustomerDbSchema.CustomerTable.Cols.EMAIL1, customer.getEmail1());
        values.put(CustomerDbSchema.CustomerTable.Cols.EMAIL2, customer.getEmail2());
        values.put(CustomerDbSchema.CustomerTable.Cols.ADDRESS1, customer.getAddress1());
        values.put(CustomerDbSchema.CustomerTable.Cols.ADDRESS2, customer.getAddress2());
        values.put(CustomerDbSchema.CustomerTable.Cols.CITY, customer.getCity());
        values.put(CustomerDbSchema.CustomerTable.Cols.STATE, customer.getState());
        values.put(CustomerDbSchema.CustomerTable.Cols.ZIP, customer.getZip());
        values.put(CustomerDbSchema.CustomerTable.Cols.NOTE, customer.getNote());
        return values;
    }
    // end 14.8
}

package com.rmeijer.trainman.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.rmeijer.trainman.Customer;

import java.util.Date;
import java.util.UUID;


// 14.13 - Creating CustomerCursorWrapper
public class CustomerCursorWrapper extends CursorWrapper {
    public CustomerCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    // 14.14 - Adding getCustomer() method
    public Customer getCustomer() {
        String uuidString = getString(getColumnIndex(CustomerDbSchema.CustomerTable.Cols.UUID));
        String name = getString(getColumnIndex(CustomerDbSchema.CustomerTable.Cols.NAME));
        long date = getLong(getColumnIndex(CustomerDbSchema.CustomerTable.Cols.DATE));
        //long birthDate = getLong(getColumnIndex(CustomerDbSchema.CustomerTable.Cols.BIRTHDATE));

        // 14.16 - Finishing up getCustomer()
        Customer customer = new Customer(UUID.fromString(uuidString));
        customer.setDate(new Date(date));
        customer.setName(name);


        return customer;

        //return null;
        // end 14.16
    }

}
// end 14.13
package com.rmeijer.trainman.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.rmeijer.trainman.Customer;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;


// 14.13 - Creating CustomerCursorWrapper
public class CustomerCursorWrapper extends CursorWrapper {
    public CustomerCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    // 14.14 - Adding getCustomer() method
    // See also https://developer.android.com/reference/android/database/Cursor
    public Customer getCustomerRow() {
        String uuidString = getString(getColumnIndex(CustomerDbSchema.CustomerTable.Cols.UUID));
        String name = getString(getColumnIndex(CustomerDbSchema.CustomerTable.Cols.NAME));
        Date date = new Date(getLong(getColumnIndex(CustomerDbSchema.CustomerTable.Cols.DATE)));
        String gender =  getString(getColumnIndex(CustomerDbSchema.CustomerTable.Cols.GENDER));
        Date birthDate = new Date(getLong(getColumnIndex(CustomerDbSchema.CustomerTable.Cols.BIRTHDATE)));
        String phone1 = getString(getColumnIndex(CustomerDbSchema.CustomerTable.Cols.PHONE1));
        String phone2 = getString(getColumnIndex(CustomerDbSchema.CustomerTable.Cols.PHONE2));
        String email1 = getString(getColumnIndex(CustomerDbSchema.CustomerTable.Cols.EMAIL1));
        String email2 = getString(getColumnIndex(CustomerDbSchema.CustomerTable.Cols.EMAIL2));
        String address1 = getString(getColumnIndex(CustomerDbSchema.CustomerTable.Cols.ADDRESS1));
        String address2 = getString(getColumnIndex(CustomerDbSchema.CustomerTable.Cols.ADDRESS2));
        String city = getString(getColumnIndex(CustomerDbSchema.CustomerTable.Cols.CITY));
        String state = getString(getColumnIndex(CustomerDbSchema.CustomerTable.Cols.STATE));
        String zip = getString(getColumnIndex(CustomerDbSchema.CustomerTable.Cols.ZIP));
        String note = getString(getColumnIndex(CustomerDbSchema.CustomerTable.Cols.NOTE));

        // 14.16 - Finishing up getCustomerRow()
        Customer customer = new Customer(UUID.fromString(uuidString));
        customer.setDate(date);
        customer.setName(name);
        customer.setGender(gender);
        customer.setBirthDate(birthDate);
        customer.setPhone1(phone1);
        customer.setPhone2(phone2);
        customer.setEmail1(email1);
        customer.setEmail2(email2);
        customer.setAddress1(address1);
        customer.setAddress2(address2);
        customer.setCity(city);
        customer.setState(state);
        customer.setZip(zip);
        customer.setNote(note);

        return customer;

        //return null;
        // end 14.16
    }

}
// end 14.13
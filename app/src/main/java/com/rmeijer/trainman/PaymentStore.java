package com.rmeijer.trainman;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.rmeijer.trainman.database.PaymentBaseHelper;
import com.rmeijer.trainman.database.PaymentCursorWrapper;
import com.rmeijer.trainman.database.PaymentDbSchema;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PaymentStore {
    private static PaymentStore sPaymentStore;
    private Context mContext;
    private SQLiteDatabase mDatabase;
    private String TAG = "PaymentStore: ";

    //**********************************************************************************************
    // Open the SQLite Database
    //**********************************************************************************************
    private PaymentStore(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new PaymentBaseHelper(mContext)
                .getWritableDatabase();
    }

    //**********************************************************************************************
    //  SQLite Database Methods
    //**********************************************************************************************
    // 8.1 - Setting up the singleton
    public static PaymentStore get(Context context) {
        if (sPaymentStore == null) {
            sPaymentStore = new PaymentStore(context);
        }
        return sPaymentStore;
    }

    //**********************************************************************************************
    // Add Payment
    //**********************************************************************************************
    public void addPayment(Payment c) {
        ContentValues values = getContentValues(c);
        Log.v("PaymentStore: ", "addPayment Payment date: " + c.getPayDate().toString());
        mDatabase.insert(PaymentDbSchema.PaymentTable.TABLE_NAME, null, values);
    }

    //**********************************************************************************************
    // Delete Payment
    //**********************************************************************************************
    public void deletePayment(UUID paymentId)
    {
        String uuidString = paymentId.toString();
        Log.v(TAG, "****************************************************");
        Log.v(TAG, "Deleting payment: " + paymentId.toString());
        Log.v(TAG, "****************************************************");
        mDatabase.delete(PaymentDbSchema.PaymentTable.TABLE_NAME,
           PaymentDbSchema.PaymentTable.Cols.UUID + " = ?",
            new String[] {uuidString}
            );
    }

    //**********************************************************************************************
    // Delete all customer payments
    //**********************************************************************************************
    public void deleteCustomerPayments(UUID customerId)
    {
        String uuidString = customerId.toString();
        mDatabase.delete(PaymentDbSchema.PaymentTable.TABLE_NAME,
                PaymentDbSchema.PaymentTable.Cols.CUSTOMERID + " = ?",
                new String[] {uuidString}
        );
        Log.v(TAG, "****************************************************");
        Log.v(TAG, "Deleting payments for customer Id: " + customerId.toString());
        Log.v(TAG, "****************************************************");
    }

    //**********************************************************************************************
    // Update Payment
    //**********************************************************************************************
    public void updatePayment(Payment payment) {
        String uuidString = payment.getId().toString();
        ContentValues values = getContentValues(payment);

        mDatabase.update(PaymentDbSchema.PaymentTable.TABLE_NAME, values,
                PaymentDbSchema.PaymentTable.Cols.UUID + " = ?",
                new String[] { uuidString });
    }

    //**********************************************************************************************
    // Get a Payment
    //**********************************************************************************************
    public Payment getPayment(UUID paymentId) {

        PaymentCursorWrapper cursor = queryPayments(
                PaymentDbSchema.PaymentTable.Cols.UUID + " = ?",
                new String[] { paymentId.toString() }
        );

        try {
            if (cursor.getCount() == 0) {
                Log.v(TAG, "Customer's payment not found Id: (" + paymentId.toString() + ");");
                return null;
            }

            cursor.moveToFirst();
            return cursor.getPaymentRow();
        } finally {
            cursor.close();
        }
    }

    //**********************************************************************************************
    // Get a Session Payment
    //**********************************************************************************************
    public Payment getSessionPayment(UUID sessionId) {
        // Note: The session Id is unique
        PaymentCursorWrapper cursor = queryPayments(
                PaymentDbSchema.PaymentTable.Cols.SESSIONID + " = ?",
                new String[] { sessionId.toString() }
        );

        try {
            if (cursor.getCount() == 0) {
                Log.v(TAG, "Customer's session payment not found Id: (" + sessionId.toString() + ");");
                return new Payment();
            }
            cursor.moveToFirst();
            Log.v("PaymentStore: ", "getSessionPayment: " + cursor.getPaymentRow().getPayDate().toString());
            return cursor.getPaymentRow();
        } finally {
            cursor.close();
        }
    }

    //**********************************************************************************************
    // Get List of Payment objects
    //**********************************************************************************************
    public List<Payment> getPayments(UUID customerId) {
        List<Payment> paymentArray = new ArrayList<>();

        PaymentCursorWrapper cursor = queryPayments(
                PaymentDbSchema.PaymentTable.Cols.CUSTOMERID + " = ?",
                new String[] { customerId.toString() }
        );

        Log.v(TAG, "Found " + cursor.getCount() + " payments for customer Id: " + customerId.toString());

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                paymentArray.add(cursor.getPaymentRow());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return paymentArray;
    }

    //**********************************************************************************************
    // Generate queryPayments
    //**********************************************************************************************
    // 14.17 - 14.17 - Vending cursor wrapper
    // 14.12 - Querying for Payments
    private PaymentCursorWrapper queryPayments(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                PaymentDbSchema.PaymentTable.TABLE_NAME,
                null, // columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null  // orderBy
        );

        //return cursor;
        return new PaymentCursorWrapper(cursor);
    }

    //**********************************************************************************************
    // Writes and updates to databases are done with the assistance of a class called ContentValues
    // ContentValues is a key-value store class, specifically designed to store the kinds of data SQLite can hold
    //**********************************************************************************************
    private static ContentValues getContentValues(Payment payment) {
        ContentValues values = new ContentValues();
        values.put(PaymentDbSchema.PaymentTable.Cols.UUID, payment.getId().toString());
        values.put(PaymentDbSchema.PaymentTable.Cols.DATE, payment.getDate().getTime());
        values.put(PaymentDbSchema.PaymentTable.Cols.CUSTOMERID, payment.getCustomerId().toString());
        values.put(PaymentDbSchema.PaymentTable.Cols.SESSIONID, payment.getSessionId().toString());
        values.put(PaymentDbSchema.PaymentTable.Cols.PAYMETHOD, payment.getPayMethod());
        values.put(PaymentDbSchema.PaymentTable.Cols.CARDNUMBER, payment.getCardNumber());
        values.put(PaymentDbSchema.PaymentTable.Cols.EXPIREDATE, payment.getExpireDate().getTime());
        values.put(PaymentDbSchema.PaymentTable.Cols.SECURITYCODE, payment.getSecurityCode());
        values.put(PaymentDbSchema.PaymentTable.Cols.AMOUNT, payment.getAmount());
        values.put(PaymentDbSchema.PaymentTable.Cols.PAYDATE, payment.getPayDate().getTime());
        Log.v("PaymentStore: ", "ContentValues Payment date: " + payment.getPayDate().toString());
        values.put(PaymentDbSchema.PaymentTable.Cols.NAME, payment.getName());
        values.put(PaymentDbSchema.PaymentTable.Cols.ADDRESS1, payment.getAddress1());
        values.put(PaymentDbSchema.PaymentTable.Cols.ADDRESS2, payment.getAddress2());
        values.put(PaymentDbSchema.PaymentTable.Cols.CITY, payment.getCity());
        values.put(PaymentDbSchema.PaymentTable.Cols.STATE, payment.getState());
        values.put(PaymentDbSchema.PaymentTable.Cols.ZIP, payment.getZip());
        return values;
    }
}

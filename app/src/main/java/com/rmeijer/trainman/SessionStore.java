package com.rmeijer.trainman;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.rmeijer.trainman.database.SessionBaseHelper;
import com.rmeijer.trainman.database.SessionCursorWrapper;
import com.rmeijer.trainman.database.SessionDbSchema;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SessionStore {
    // 8.1 - Setting up the singleton
    private static SessionStore sSessionStore;

    // 13.8 - Adding a new customer
    public void addSession(Session s) {
        // 14.7 - Tearing down some walls
        //mSessions.add(c);

        // 14.9 - Inserting a row
        ContentValues values = getContentValues(s);
        mDatabase.insert(SessionDbSchema.SessionTable.TABLE_NAME, null, values);
    }

    // Delete session row
    // ???? Need to check return code ????
    public void deleteSession(UUID sessionId)
    {
        String uuidString = sessionId.toString();
        mDatabase.delete(SessionDbSchema.SessionTable.TABLE_NAME,
           SessionDbSchema.SessionTable.Cols.UUID + " = ?",
            new String[] {uuidString}
            );
    }

    // Delete all customer sessions
    public void deleteCustomerSessions(UUID customerId)
    {
        String uuidString = customerId.toString();
        mDatabase.delete(SessionDbSchema.SessionTable.TABLE_NAME,
                SessionDbSchema.SessionTable.Cols.CUSTOMERID + " = ?",
                new String[] {uuidString}
        );
        Log.v("X" , "****************************************************");
        Log.v("SessionStore", "Deleting sessions for customer Id: " + customerId.toString());
        Log.v("X" , "****************************************************");
    }

    // 14.7 - Tearing down some walls
    // 8.2 - Setting up the List of Session objects
    //private List<Session> mSessions;
    // end 14.7

    // 14.4 - Opening a SQLiteDatabase
    private Context mContext;
    private SQLiteDatabase mDatabase;

    // 8.1 - Setting up the singleton
    public static SessionStore get(Context context) {
        if (sSessionStore == null) {
            sSessionStore = new SessionStore(context);
        }
        return sSessionStore;
    }

    // 8.1 - Setting up the singleton
    private SessionStore(Context context) {
        // 14.4 - Opening a SQLiteDatabase
        mContext = context.getApplicationContext();
        mDatabase = new SessionBaseHelper(mContext)
                .getWritableDatabase();
        // end 14.4
    }

    // 8.2 - Setting up the List of Customer objects
    public List<Session> getSessions(UUID customerId) {
        // 14.18 - Returning customer list
        //return new ArrayList<>();
        List<Session> sessionArray = new ArrayList<>();

        SessionCursorWrapper cursor = querySessions(
                SessionDbSchema.SessionTable.Cols.CUSTOMERID + " = ?",
                new String[] { customerId.toString() }
        );

        Log.v("SessionStore", "Found " + cursor.getCount() + " sessions for customer Id: " + customerId.toString());

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                sessionArray.add(cursor.getSessionRow());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return sessionArray;
        //end 14.18
    }

    public Session getSession(UUID customerId, UUID sessionId) {

        SessionCursorWrapper cursor = querySessions(
                SessionDbSchema.SessionTable.Cols.CUSTOMERID + " = ?"
                + " AND " + SessionDbSchema.SessionTable.Cols.UUID + " = ?",
                new String[] { customerId.toString(), sessionId.toString() }
        );

        try {
            if (cursor.getCount() == 0) {
                Log.v("SessionStore: ", "Customer's session not found Id:");
                Log.v("SessionStore: ", "Customer Id " + customerId.toString());
                Log.v("SessionStore: ", "Session Id " + sessionId.toString());
                return null;
            }

            cursor.moveToFirst();
            return cursor.getSessionRow();
        } finally {
            cursor.close();
        }
        // end 14.19

    }

    // 14.10 - Updating a Session
    public void updateSession(Session session) {
        String uuidString = session.getId().toString();
        ContentValues values = getContentValues(session);

        mDatabase.update(SessionDbSchema.SessionTable.TABLE_NAME, values,
                SessionDbSchema.SessionTable.Cols.UUID + " = ?",
                new String[] { uuidString });
    }
    // end 14.10

    public void updateSessionSign(Session session) {
        String uuidString = session.getId().toString();
        ContentValues values = getContentValues(session);

        mDatabase.update(SessionDbSchema.SessionTable.TABLE_NAME, values,
                SessionDbSchema.SessionTable.Cols.UUID + " = ?",
                new String[] { uuidString });
    }

    // 14.17 - 14.17 - Vending cursor wrapper
    // 14.12 - Querying for Customers
    //private Cursor queryCustomers(String whereClause, String[] whereArgs) {
    private SessionCursorWrapper querySessions(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                SessionDbSchema.SessionTable.TABLE_NAME,
                null, // columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null  // orderBy
        );

        //return cursor;
        return new SessionCursorWrapper(cursor);
    }
    // end 14.12
    // end 14.17

    // 14.8 - Creating a ContentValues
    // Writes and updates to databases are done with the assistance of a class called ContentValues
    // ContentValues is a key-value store class, specifically designed to store the kinds of data SQLite can hold
    private static ContentValues getContentValues(Session session) {
        ContentValues values = new ContentValues();
        values.put(SessionDbSchema.SessionTable.Cols.UUID, session.getId().toString());
        values.put(SessionDbSchema.SessionTable.Cols.DATE, session.getDate().getTime());
        values.put(SessionDbSchema.SessionTable.Cols.CUSTOMERID, session.getCustomerId().toString());
        values.put(SessionDbSchema.SessionTable.Cols.SERVICE, session.getService());
        values.put(SessionDbSchema.SessionTable.Cols.SESSIONDATE, session.getSessionDate().getTime());
        values.put(SessionDbSchema.SessionTable.Cols.DESCR, session.getDescr());
        values.put(SessionDbSchema.SessionTable.Cols.COMPLETED, session.isCompleted());
        values.put(SessionDbSchema.SessionTable.Cols.PAID, session.isPaid());
        values.put(SessionDbSchema.SessionTable.Cols.SIGN, session.getSign());

        return values;
    }
    // end 14.8

    private static ContentValues getContentSignValue(Session session) {
        ContentValues value = new ContentValues();
        value.put(SessionDbSchema.SessionTable.Cols.SIGN, session.getSign());

        return value;
    }
}

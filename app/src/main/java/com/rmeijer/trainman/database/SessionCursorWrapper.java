package com.rmeijer.trainman.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.rmeijer.trainman.Session;

import java.util.Date;
import java.util.UUID;

//**************************************************************************************************
// 14.13 - Creating SessionCursorWrapper
//**************************************************************************************************
public class SessionCursorWrapper extends CursorWrapper {
    public SessionCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    //**********************************************************************************************
    // 14.14 - Adding getSession() method
    // See also https://developer.android.com/reference/android/database/Cursor
    //**********************************************************************************************
    public Session getSessionRow() {
        String uuidString = getString(getColumnIndex(SessionDbSchema.SessionTable.Cols.UUID));
        Date date = new Date(getLong(getColumnIndex(SessionDbSchema.SessionTable.Cols.DATE)));
        String customerId = getString(getColumnIndex(SessionDbSchema.SessionTable.Cols.CUSTOMERID));
        String service = getString(getColumnIndex(SessionDbSchema.SessionTable.Cols.SERVICE));
        Date sessionDate = new Date(getLong(getColumnIndex(SessionDbSchema.SessionTable.Cols.SESSIONDATE)));
        String descr =  getString(getColumnIndex(SessionDbSchema.SessionTable.Cols.DESCR));
        int completed = getInt(getColumnIndex(SessionDbSchema.SessionTable.Cols.COMPLETED));
        int paid = getInt(getColumnIndex(SessionDbSchema.SessionTable.Cols.PAID));
        String sign = getString(getColumnIndex(SessionDbSchema.SessionTable.Cols.SIGN));

        // 14.16 - Finishing up getSessionRow()
        Session session = new Session(UUID.fromString(uuidString));
        session.setDate(date);
        session.setCustomerId(UUID.fromString(customerId));
        session.setService(service);
        session.setSessionDate(sessionDate);
        session.setDescr(descr);
        session.setCompleted(completed == 1);
        session.setPaid(paid == 1);
        session.setSign(sign);

        return session;
    }

}
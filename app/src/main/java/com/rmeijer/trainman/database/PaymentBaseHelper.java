package com.rmeijer.trainman.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static com.rmeijer.trainman.database.PaymentDbSchema.PaymentTable.TABLE_NAME;

public class PaymentBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 2018091903;
    private static final String DATABASE_NAME = "paymentBase.db";

    public PaymentBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + TABLE_NAME
                + "(" + " _id integer primary key autoincrement"
                + ", " + PaymentDbSchema.PaymentTable.Cols.UUID
                + ", " + PaymentDbSchema.PaymentTable.Cols.DATE
                + ", " + PaymentDbSchema.PaymentTable.Cols.CUSTOMERID
                + ", " + PaymentDbSchema.PaymentTable.Cols.SESSIONID
                + ", " + PaymentDbSchema.PaymentTable.Cols.PAYMETHOD
                + ", " + PaymentDbSchema.PaymentTable.Cols.CARDNUMBER
                + ", " + PaymentDbSchema.PaymentTable.Cols.EXPIREDATE
                + ", " + PaymentDbSchema.PaymentTable.Cols.SECURITYCODE
                + ", " + PaymentDbSchema.PaymentTable.Cols.AMOUNT
                + ", " + PaymentDbSchema.PaymentTable.Cols.PAYDATE
                + ", " + PaymentDbSchema.PaymentTable.Cols.NAME
                + ", " + PaymentDbSchema.PaymentTable.Cols.ADDRESS1
                + ", " + PaymentDbSchema.PaymentTable.Cols.ADDRESS2
                + ", " + PaymentDbSchema.PaymentTable.Cols.CITY
                + ", " + PaymentDbSchema.PaymentTable.Cols.STATE
                + ", " + PaymentDbSchema.PaymentTable.Cols.ZIP
                + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(PaymentBaseHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
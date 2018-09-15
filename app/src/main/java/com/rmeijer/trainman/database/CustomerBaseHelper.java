package com.rmeijer.trainman.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static com.rmeijer.trainman.database.CustomerDbSchema.CustomerTable.TABLE_NAME;

// 14.3 - Creating CustomerBaseHelper
public class CustomerBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 2018091402;
    private static final String DATABASE_NAME = "customerBase.db";

    public CustomerBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Listing 14.5 - Writing first part of onCreate(SQLiteDatabase)
        //db.execSQL("create table " + CustomerTable.NAME);
        // 14.6 - Creating customer table
        db.execSQL("create table " + TABLE_NAME
                + "(" + " _id integer primary key autoincrement"
                + ", " + CustomerDbSchema.CustomerTable.Cols.UUID
                + ", " + CustomerDbSchema.CustomerTable.Cols.DATE
                + ", " + CustomerDbSchema.CustomerTable.Cols.NAME
                + ", " + CustomerDbSchema.CustomerTable.Cols.GENDER
                + ", " + CustomerDbSchema.CustomerTable.Cols.BIRTHDATE
                + ", " + CustomerDbSchema.CustomerTable.Cols.PHONE1
                + ", " + CustomerDbSchema.CustomerTable.Cols.PHONE2
                + ", " + CustomerDbSchema.CustomerTable.Cols.EMAIL1
                + ", " + CustomerDbSchema.CustomerTable.Cols.EMAIL2
                + ", " + CustomerDbSchema.CustomerTable.Cols.ADDRESS1
                + ", " + CustomerDbSchema.CustomerTable.Cols.ADDRESS2
                + ", " + CustomerDbSchema.CustomerTable.Cols.CITY
                + ", " + CustomerDbSchema.CustomerTable.Cols.STATE
                + ", " + CustomerDbSchema.CustomerTable.Cols.ZIP
                + ", " + CustomerDbSchema.CustomerTable.Cols.NOTE
                + ")"
        );
        // 14.6
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(CustomerBaseHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
// end 14.3
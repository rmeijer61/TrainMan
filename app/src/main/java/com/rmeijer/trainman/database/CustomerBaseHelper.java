package com.rmeijer.trainman.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

// 14.3 - Creating CustomerBaseHelper
public class CustomerBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "customerBase.db";

    public CustomerBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        // Listing 14.5 - Writing first part of onCreate(SQLiteDatabase)
        //db.execSQL("create table " + CustomerTable.NAME);
        // 14.6 - Creating customer table
        db.execSQL("create table "
                + CustomerDbSchema.CustomerTable.NAME
                + "(" + " _id integer primary key autoincrement"
                + ", " + CustomerDbSchema.CustomerTable.Cols.UUID
                + ", " + CustomerDbSchema.CustomerTable.Cols.DATE
                + ", " + CustomerDbSchema.CustomerTable.Cols.NAME
                //+ ", " + CustomerDbSchema.CustomerTable.Cols.BIRTHDATE
                + ")"
        );
        // 14.6
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
// end 14.3
package com.rmeijer.trainman.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static com.rmeijer.trainman.database.SessionDbSchema.SessionTable.TABLE_NAME;

// 14.3 - Creating SessionBaseHelper
public class SessionBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 2018091102;
    private static final String DATABASE_NAME = "sessionBase.db";

    public SessionBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Listing 14.5 - Writing first part of onCreate(SQLiteDatabase)
        //db.execSQL("create table " + SessionTable.NAME);
        // 14.6 - Creating session table
        db.execSQL("create table " + TABLE_NAME
                + "(" + " _id integer primary key autoincrement"
                + ", " + SessionDbSchema.SessionTable.Cols.UUID
                + ", " + SessionDbSchema.SessionTable.Cols.DATE
                + ", " + SessionDbSchema.SessionTable.Cols.CUSTOMERID
                + ", " + SessionDbSchema.SessionTable.Cols.SERVICE
                + ", " + SessionDbSchema.SessionTable.Cols.SESSIONDATE
                + ", " + SessionDbSchema.SessionTable.Cols.DESCR
                + ", " + SessionDbSchema.SessionTable.Cols.COMPLETED
                + ", " + SessionDbSchema.SessionTable.Cols.PAID
                + ", " + SessionDbSchema.SessionTable.Cols.SIGN
                + ")"
        );
        // 14.6
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(SessionBaseHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
// end 14.3
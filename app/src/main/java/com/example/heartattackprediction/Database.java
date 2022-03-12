package com.example.heartattackprediction;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database {

    private String databaseName = "heart";
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;
    private Context context;

    public Database(Context ct) {
        this.context = ct;
        DBHelper = new DatabaseHelper(context);
    }

    class DatabaseHelper extends SQLiteOpenHelper {
        public DatabaseHelper(Context context) {
            super(context, databaseName, null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table patientInfo(patientName TEXT,patientMobile TEXT,patientEmail TEXT,patientAddress TEXT);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase arg0, int oldVersion,
                              int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS patientInfo");
            onCreate(db);
        }

    }

    public Database open() throws SQLException {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        DBHelper.close();
    }


    public long insertPatientList(String name, String mobile,
                                   String email, String address) {
        ContentValues cv = new ContentValues();
        cv.put("patientName", name);
        cv.put("patientMobile", mobile);
        cv.put("patientEmail", email);
        cv.put("patientAddress", address);
        return db.insert("patientInfo", null, cv);
    }




    public Cursor getPatientInfo() {
        Cursor cur = db.query("patientInfo", null, null, null, null, null,
                null, null);
        return cur;
    }



}
package com.example.user.uprice.DBHelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Bob on 2016/11/14.
 */

public class DBHelper extends SQLiteOpenHelper {
    private Cursor cursor;
    private static DBHelper instance = null;

    public static DBHelper getInstance(Context context, String name, int version){
        if(instance == null) {
            instance = new DBHelper(context, name, null, version);
        }
        return instance;
    }

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table contacts " +
            "(_id INTEGER PRIMARY KEY NOT NULL, name VARCHAR, product VARCHAR, number VARCHAR, date VARCHAR,created_time TIMESTAMP default CURRENT_TIMESTAMP)"
        );

        sqLiteDatabase.execSQL("create table oil" +
                    "(_id INTEGER PRIMARY KEY NOT NULL, now_KM VARCHAR, km_l VARCHAR, nt_km VARCHAR, full_oil_nt VARCHAR, date VARCHAR,created_time TIMESTAMP default CURRENT_TIMESTAMP)"
            );
        sqLiteDatabase.execSQL("create table IF NOT EXISTS oilA " +
                "( _id INTEGER PRIMARY KEY NOT NULL, now_KM VARCHAR, km_l VARCHAR, nt_km VARCHAR, full_oil_nt VARCHAR, date VARCHAR,created_time TIMESTAMP default CURRENT_TIMESTAMP)"
        );
        sqLiteDatabase.execSQL("create table IF NOT EXISTS oilB " +
                "( _id INTEGER PRIMARY KEY NOT NULL, now_KM VARCHAR, km_l VARCHAR, nt_km VARCHAR, full_oil_nt VARCHAR, date VARCHAR,created_time TIMESTAMP default CURRENT_TIMESTAMP)"
        );
        sqLiteDatabase.execSQL("create table IF NOT EXISTS oilC " +
                "( _id INTEGER PRIMARY KEY NOT NULL, now_KM VARCHAR, km_l VARCHAR, nt_km VARCHAR, full_oil_nt VARCHAR, date VARCHAR,created_time TIMESTAMP default CURRENT_TIMESTAMP)"
        );
        sqLiteDatabase.execSQL("create table IF NOT EXISTS oilD " +
                "( _id INTEGER PRIMARY KEY NOT NULL, now_KM VARCHAR, km_l VARCHAR, nt_km VARCHAR, full_oil_nt VARCHAR, date VARCHAR,created_time TIMESTAMP default CURRENT_TIMESTAMP)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        onCreate(sqLiteDatabase);
    }

    public void onDropTable(SQLiteDatabase db){
        db.execSQL("drop table if exists apps");
    }
}

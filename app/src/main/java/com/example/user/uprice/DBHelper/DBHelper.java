package com.example.user.uprice.DBHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by fang on 2016/12/24.
 */

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context, String dbName, int version){
        super (context, dbName, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table apps(_id integer primary key autoincrement, name text , product text , number text ,"
                 +
                " date text, created_date datetime default current_timestamp);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }
    public void onDropTable(SQLiteDatabase db){
        db.execSQL("drop table if exists apps");
    }
}

package com.example.user.uprice.DBHelper;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user.uprice.R;

/**
 * Created by fang on 2016/12/26.
 */

public class Info extends AppCompatActivity {
    private final static String _ID = "_id";
    private DatePicker datePicker;
    private EditText etName, etProduct, etnumber,etdate;
    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private Button ok ,can;
    private Cursor cursor;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addmotorcycle_layout);
        drawColorToStatusBar();
        etName = (EditText) findViewById(R.id.ed_Name);
        etProduct = (EditText) findViewById(R.id.ed_Product);
        etnumber = (EditText) findViewById(R.id.ed_Number);
        etdate = (EditText) findViewById(R.id.ed_Date);
        datePicker = (DatePicker) findViewById(R.id.datePicker);
        ok = (Button) findViewById(R.id.OK);
        can = (Button) findViewById(R.id.cancel);



        dbHelper =new DBHelper(this, "contactsDB", null, 1);
        db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM contacts", null);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cursor.getCount()<5) {
                    newItem(v);
                    startActivity(new Intent(Info.this, Information.class));
                    Info.this.finish();
                }
                else{
                    //利用Toast的靜態函式makeText來建立Toast物件
                    Toast toast = Toast.makeText(Info.this,
                            "最多只能產生五個個人車庫!", Toast.LENGTH_SHORT);
                    //顯示Toast
                    toast.show();

                }
            }
        });
        can.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Info.this.finish();
               // startActivity(new Intent(Info.this, Information.class));
            }
        });

    }

    public void newItem(View view){
       // db.execSQL("create table oil" +pos+
                     //   "(_id INTEGER PRIMARY KEY NOT NULL, now_KM VARCHAR, km_l VARCHAR, nt_km VARCHAR, full_oil_nt VARCHAR, date VARCHAR,created_time TIMESTAMP default CURRENT_TIMESTAMP)");
        dbHelper =new DBHelper(this, "contactsDB", null, 1);
       // dbHelper = DBHelper.getInstance(this, "contactsDB", 1);
        db = dbHelper.getWritableDatabase();
        String name = etName.getText().toString();
        String product = etProduct.getText().toString();
        String number = etnumber.getText().toString();
        //String date = etdate.getText().toString();
        int year = datePicker.getYear();
        int month = datePicker.getMonth()+1;
        int day = datePicker.getDayOfMonth();
        String date = year + "-" + month + "-" + day;
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("product",product );
        values.put("number",number);
        values.put("date",date);
        Long id = db.insert("contacts", null, values);
        Log.i("ADD: ", String.valueOf(id));
        //String sql = "INSERT INTO contacts (_id, name, phone, address, created_time) VALUES (null, '" +
        // name + "', '" + phone + "', '" + address + "'," + null + ")";
        //Cursor cursor = db.rawQuery(sql, null);
        //Log.i("ADD: ", String.valueOf(cursor.getInt(0)));
    }
    @TargetApi(Build.VERSION_CODES.M)
    private void drawColorToStatusBar() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getColor(R.color.colorPrimaryDark));
    }
    public boolean delete(long rowid) {

        Log.d("mylog","del："+ rowid);
        return db.delete("contacts", _ID + "=" + rowid, null) > 0;
    }
}

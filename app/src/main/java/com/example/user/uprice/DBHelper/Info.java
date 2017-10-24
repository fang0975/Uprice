package com.example.user.uprice.DBHelper;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.user.uprice.R;

/**
 * Created by fang on 2016/12/26.
 */

public class Info extends AppCompatActivity {
    private EditText etName, etProduct, etnumber,etdate;
    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private Button ok ,can;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addmotorcycle_layout);
        etName = (EditText) findViewById(R.id.ed_Name);
        etProduct = (EditText) findViewById(R.id.ed_Product);
        etnumber = (EditText) findViewById(R.id.ed_Number);
        etdate = (EditText) findViewById(R.id.ed_Date);
        ok = (Button) findViewById(R.id.OK);
        can = (Button) findViewById(R.id.cancel);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newItem(v);
                startActivity(new Intent(Info.this, Information.class));
            }
        });
        can.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Info.this, Information.class));
            }
        });

    }
    public void newItem(View view){

        dbHelper = DBHelper.getInstance(this, "contactsDB", 1);
        db = dbHelper.getWritableDatabase();
        String name = etName.getText().toString();
        String product = etProduct.getText().toString();
        String number = etnumber.getText().toString();
        String date = etdate.getText().toString();
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
}

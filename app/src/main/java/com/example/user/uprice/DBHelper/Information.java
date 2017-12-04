package com.example.user.uprice.DBHelper;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.example.user.uprice.MainActivity;
import com.example.user.uprice.R;

public class Information extends AppCompatActivity {

    private ListView lv;
    private SQLiteDatabase db;
    private SimpleCursorAdapter adapter;
    private Cursor cursor;
    private Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iformation);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawColorToStatusBar();
        back = (Button) findViewById(R.id.Back);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                // .setAction("Action", null).show();
                startActivity(new Intent(Information.this, Info.class));
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Information.this, MainActivity.class));
            }
        });

        lv = (ListView) findViewById(R.id.Info_contact);

        //DBHelper dbHelper = DBHelper.getInstance(this, "contactsDB", 1);
        DBHelper dbHelper =new DBHelper(this, "contactsDB", null, 1);
        db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM contacts", null);
        adapter = new SimpleCursorAdapter(this, R.layout.info_item,
                cursor, new String[]{"name", "number", "date"}, new int[]{R.id.info_item_name, R.id.info_item_number,R.id.info_item_date}, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        lv.setAdapter(adapter);

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final int pos = position;
                cursor.moveToPosition(1);
                new AlertDialog.Builder(Information.this)
                        .setTitle("刪除")
                        .setMessage("確定要刪除此資料嗎")
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                delete(cursor.getInt(0));
                                cursor.requery();
                                adapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("否", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();

                return false;
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final int pos = position;
                cursor.moveToPosition(pos);
                //startActivity(new Intent(Information.this, PersonalOilCost.class));
                Intent intent = new Intent();
                intent.setClass(Information.this, PersonalOilCost.class);
                Bundle bundle = new Bundle();
                bundle.putString("name",cursor.getString(1));
                bundle.putString("product",cursor.getString(2));
               /* db.execSQL("create table oil" +pos+
                        "(_id INTEGER PRIMARY KEY NOT NULL, km_l VARCHAR, nt_km VARCHAR, full_oil_nt VARCHAR, date VARCHAR,created_time TIMESTAMP default CURRENT_TIMESTAMP)"
                );*/
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });




    }
    public void delete(int id){
        db.delete("contacts", "_id" + "=" + Integer.toString(id), null);
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void drawColorToStatusBar() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getColor(R.color.colorPrimaryDark));
    }
    @Override
    public void onResume(){
        super.onResume();
        cursor = db.rawQuery("SELECT * FROM contacts", null);
        adapter.changeCursor(cursor);
        Log.i("onResume", "Bingo");
        //adapter.notifyDataSetChanged();
    }
    public void close(){
        db.close();
    }

}

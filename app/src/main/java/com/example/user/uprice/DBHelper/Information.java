package com.example.user.uprice.DBHelper;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.app.AlertDialog;
import android.widget.AdapterView;
import android.view.View;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
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

        DBHelper dbHelper = DBHelper.getInstance(this, "contactsDB", 1);
        db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM contacts", null);
        adapter = new SimpleCursorAdapter(this, R.layout.info_item,
                cursor, new String[]{"name", "number", "date"}, new int[]{R.id.info_item_name, R.id.info_item_number,R.id.info_item_date}, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);



    }


    @Override
    public void onResume(){
        super.onResume();
        cursor = db.rawQuery("SELECT * FROM contacts", null);
        adapter.changeCursor(cursor);
        Log.i("onResume", "Bingo");
        //adapter.notifyDataSetChanged();
    }


}

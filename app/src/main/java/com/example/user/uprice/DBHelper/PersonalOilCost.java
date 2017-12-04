package com.example.user.uprice.DBHelper;

import android.annotation.TargetApi;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.user.uprice.R;

/**
 * Created by jkl87 on 2017/12/2.
 */

public class PersonalOilCost extends AppCompatActivity {
    private SQLiteDatabase db;
    private SimpleCursorAdapter adapter;
    private Cursor cursor;
    private DBHelper dbHelper;
    private EditText last_oilkm, now_oilkm, now_oil_L,now_km;
    private TextView OName,OProduct;
    private Button oilcost;
    private ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_oilcost);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawColorToStatusBar();
        //findview:

        OName =(TextView)findViewById(R.id.name) ;
        OProduct =(TextView)findViewById(R.id.product) ;
        oilcost =(Button) findViewById(R.id.oilcost_button) ;
        lv = (ListView) findViewById(R.id.lv_oilcost);
        //new oilcost data
        oilcost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PersonalOilCost.this, AddOilcost.class));
            }
        });
        //DB oilcost data
         //dbHelper = DBHelper.getInstance(this, "contactsDB", 1);

       /* dbHelper =new DBHelper(this, "contactsDB", null, 1);
        cursor = db.rawQuery("SELECT * FROM contacts", null);
        int i=cursor.getPosition();
        db.close();*/
       dbHelper =new DBHelper(this, "oilDB", null, 1);
        db = dbHelper.getReadableDatabase();

        cursor = db.rawQuery("SELECT * FROM oil", null);
        adapter = new SimpleCursorAdapter(this, R.layout.oilcost_info_item,
                cursor, new String[]{"_id", "km_l", "nt_km","full_oil_nt"}, new int[]{R.id.ID, R.id.oneL_runKM,R.id.oneKM_money,R.id.nowcost_NT}, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

        lv.setAdapter(adapter);
        //Name and Product SQL

        Bundle bundle0311 =this.getIntent().getExtras();
        String PGname = bundle0311.getString("name");
        String PGproduct = bundle0311.getString("product");
        OName.setText(PGname);
        OProduct.setText(PGproduct);


    }
    @TargetApi(Build.VERSION_CODES.M)
    private void drawColorToStatusBar() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getColor(R.color.colorPrimaryDark));
    }

    public void oilprice_select(){
        Spinner spinner = (Spinner)findViewById(R.id.oilprice);

        final String[] oilprice = {"98無鉛汽油", "95無鉛汽油", "92無鉛汽油", "酒精汽油", "超級柴油"};
        ArrayAdapter<String> lunchList = new ArrayAdapter<>(PersonalOilCost.this,
                android.R.layout.simple_spinner_dropdown_item,
                oilprice);
        spinner.setAdapter(lunchList);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {




            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }

}

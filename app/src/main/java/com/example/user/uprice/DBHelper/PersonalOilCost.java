package com.example.user.uprice.DBHelper;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
    private TextView OName,OProduct,Avoilcost_textview,av_oilcost;
    private Button oilcost,av_cal;
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
        Avoilcost_textview =(TextView)findViewById(R.id.textView7) ;
        av_oilcost =(TextView)findViewById(R.id.av_oilcost) ;
        oilcost =(Button) findViewById(R.id.oilcost_button) ;
        av_cal =(Button) findViewById(R.id.av_calculate) ;
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
       dbHelper =new DBHelper(this, "oilsDB", null, 1);
        db = dbHelper.getReadableDatabase();

        cursor = db.rawQuery("SELECT * FROM oil", null);
        adapter = new SimpleCursorAdapter(this, R.layout.oilcost_info_item,
                cursor, new String[]{"_id","km_l", "nt_km","full_oil_nt","now_KM"}, new int[]{R.id.ID,R.id.oneL_runKM,R.id.oneKM_money,R.id.nowcost_NT,R.id.nowKM}, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

        lv.setAdapter(adapter);
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final int pos = position;
                cursor.moveToPosition(1);
                new AlertDialog.Builder(PersonalOilCost.this)
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
        //Name and Product SQL

        Bundle bundle0311 =this.getIntent().getExtras();
        String PGname = bundle0311.getString("name");
        String PGproduct = bundle0311.getString("product");
        OName.setText(PGname);
        OProduct.setText(PGproduct);

        av_cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int FFavoilcost=0;
                int Favcost[]=new int[cursor.getCount()] ;
                cursor.moveToPosition(0);
                for(int i=0;i<(cursor.getCount());i++){

                    String Savcost = cursor.getString(2);
                    Favcost[i] =Integer.valueOf(Savcost);
                    FFavoilcost =FFavoilcost+Favcost[i];
                    cursor.moveToNext();
                }
                av_oilcost.setText(String.valueOf(FFavoilcost/cursor.getCount())+"  KM/L");
            }
        });

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
        cursor = db.rawQuery("SELECT * FROM oil", null);
        adapter.changeCursor(cursor);
        Log.i("onResume", "Bingo");
        //adapter.notifyDataSetChanged();
    }
    public void delete(int id){
        db.delete("oil", "_id" + "=" + Integer.toString(id), null);
    }

}

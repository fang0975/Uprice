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
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.example.user.uprice.R;

import java.text.NumberFormat;

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

        //Name and Product SQL

        Bundle bundle0311 =this.getIntent().getExtras();
        final String ID = bundle0311.getString("id");
        String PGname = bundle0311.getString("name");
        String PGproduct = bundle0311.getString("product");
        OName.setText(PGname);
        OProduct.setText(PGproduct);
        //new oilcost data
        oilcost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(PersonalOilCost.this, AddOilcost.class);
                Bundle bundleA = new Bundle();
                bundleA.putString("pos",ID);
                intent.putExtras(bundleA);
                startActivity(intent);
            }
        });
        //DB oilcost data

       dbHelper =new DBHelper(this, "oilsDB.db", null, 1);
        db = dbHelper.getReadableDatabase();


        switch (ID) {
            case"1":
            cursor = db.rawQuery("SELECT * FROM oil", null);
            adapter = new SimpleCursorAdapter(this, R.layout.oilcost_info_item,
                    cursor, new String[]{"_id", "km_l", "nt_km", "full_oil_nt", "now_KM"}, new int[]{R.id.ID, R.id.oneL_runKM, R.id.oneKM_money, R.id.nowcost_NT, R.id.nowKM}, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

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
            break;
            case"2":
                cursor = db.rawQuery("SELECT * FROM oilA", null);
                adapter = new SimpleCursorAdapter(this, R.layout.oilcost_info_item,
                        cursor, new String[]{"_id", "km_l", "nt_km", "full_oil_nt", "now_KM"}, new int[]{R.id.ID, R.id.oneL_runKM, R.id.oneKM_money, R.id.nowcost_NT, R.id.nowKM}, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

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
                                        deleteA(cursor.getInt(0));
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
                break;
            case"3":
                cursor = db.rawQuery("SELECT * FROM oilB", null);
                adapter = new SimpleCursorAdapter(this, R.layout.oilcost_info_item,
                        cursor, new String[]{"_id", "km_l", "nt_km", "full_oil_nt", "now_KM"}, new int[]{R.id.ID, R.id.oneL_runKM, R.id.oneKM_money, R.id.nowcost_NT, R.id.nowKM}, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

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
                                        deleteB(cursor.getInt(0));
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
                break;
            case"4":
                cursor = db.rawQuery("SELECT * FROM oilC", null);
                adapter = new SimpleCursorAdapter(this, R.layout.oilcost_info_item,
                        cursor, new String[]{"_id", "km_l", "nt_km", "full_oil_nt", "now_KM"}, new int[]{R.id.ID, R.id.oneL_runKM, R.id.oneKM_money, R.id.nowcost_NT, R.id.nowKM}, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

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
                                        deleteC(cursor.getInt(0));
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
                break;
            case"5":
                cursor = db.rawQuery("SELECT * FROM oilD", null);
                adapter = new SimpleCursorAdapter(this, R.layout.oilcost_info_item,
                        cursor, new String[]{"_id", "km_l", "nt_km", "full_oil_nt", "now_KM"}, new int[]{R.id.ID, R.id.oneL_runKM, R.id.oneKM_money, R.id.nowcost_NT, R.id.nowKM}, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

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
                                        deleteD(cursor.getInt(0));
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
                break;
        }
        final NumberFormat nf = NumberFormat.getInstance();   // 數字格式
        nf.setMaximumFractionDigits(1);                 // 限制小數第二位
        av_cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (ID) {
                    case"1":
                        cursor = db.rawQuery("SELECT * FROM oil", null);
                    float FFavoilcost = 0;
                    float Favcost[] = new float[cursor.getCount()];
                    cursor.moveToPosition(0);
                    for (int i = 0; i < (cursor.getCount()); i++) {

                        String Savcost = cursor.getString(2);
                        Favcost[i] = Float.valueOf(Savcost);
                        FFavoilcost = FFavoilcost + Favcost[i];
                        cursor.moveToNext();
                    }


                    av_oilcost.setText(String.valueOf(nf.format(FFavoilcost / cursor.getCount())) + "  KM/L");
                    break;
                    case"2":
                        cursor = db.rawQuery("SELECT * FROM oilA", null);
                        float FFavoilcostA = 0;
                        float FavcostA[] = new float[cursor.getCount()];
                        cursor.moveToPosition(0);
                        for (int i = 0; i < (cursor.getCount()); i++) {

                            String Savcost = cursor.getString(2);
                            FavcostA[i] = Float.valueOf(Savcost);
                            FFavoilcostA = FFavoilcostA + FavcostA[i];
                            cursor.moveToNext();
                        }

                        av_oilcost.setText(String.valueOf(nf.format(FFavoilcostA / cursor.getCount())) + "  KM/L");
                        break;
                    case"3":
                        cursor = db.rawQuery("SELECT * FROM oilB", null);
                        float FFavoilcostB = 0;
                        float FavcostB[] = new float[cursor.getCount()];
                        cursor.moveToPosition(0);
                        for (int i = 0; i < (cursor.getCount()); i++) {

                            String Savcost = cursor.getString(2);
                            FavcostB[i] = Float.valueOf(Savcost);
                            FFavoilcostB = FFavoilcostB + FavcostB[i];
                            cursor.moveToNext();
                        }

                        av_oilcost.setText(String.valueOf(nf.format(FFavoilcostB / cursor.getCount())) + "  KM/L");
                        break;
                    case"4":
                        cursor = db.rawQuery("SELECT * FROM oilC", null);
                        float FFavoilcostC = 0;
                        float FavcostC[] = new float[cursor.getCount()];
                        cursor.moveToPosition(0);
                        for (int i = 0; i < (cursor.getCount()); i++) {

                            String Savcost = cursor.getString(2);
                            FavcostC[i] = Float.valueOf(Savcost);
                            FFavoilcostC = FFavoilcostC + FavcostC[i];
                            cursor.moveToNext();
                        }

                        av_oilcost.setText(String.valueOf(nf.format(FFavoilcostC / cursor.getCount())) + "  KM/L");
                        break;
                    case"5":
                        cursor = db.rawQuery("SELECT * FROM oilD", null);
                        float FFavoilcostD = 0;
                        float FavcostD[] = new float[cursor.getCount()];
                        cursor.moveToPosition(0);
                        for (int i = 0; i < (cursor.getCount()); i++) {

                            String Savcost = cursor.getString(2);
                            FavcostD[i] = Float.valueOf(Savcost);
                            FFavoilcostD = FFavoilcostD + FavcostD[i];
                            cursor.moveToNext();
                        }

                        av_oilcost.setText(String.valueOf(nf.format(FFavoilcostD / cursor.getCount())) + "  KM/L");
                        break;
                }
            }
        });

    }
    @TargetApi(Build.VERSION_CODES.M)
    private void drawColorToStatusBar() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getColor(R.color.colorPrimaryDark));
    }
/*
    @Override
    public void onResume(){
        super.onResume();
        cursor = db.rawQuery("SELECT * FROM oil", null);
        adapter.changeCursor(cursor);
        Log.i("onResume", "Bingo");
        //adapter.notifyDataSetChanged();
    }*/
    public void delete(int id){
        db.delete("oil", "_id" + "=" + Integer.toString(id), null);
    }
    public void deleteA(int id){
        db.delete("oilA", "_id" + "=" + Integer.toString(id), null);
    }
    public void deleteB(int id){
        db.delete("oilB", "_id" + "=" + Integer.toString(id), null);
    }
    public void deleteC(int id){
        db.delete("oilC", "_id" + "=" + Integer.toString(id), null);
    }
    public void deleteD(int id){
        db.delete("oilD", "_id" + "=" + Integer.toString(id), null);
    }

}

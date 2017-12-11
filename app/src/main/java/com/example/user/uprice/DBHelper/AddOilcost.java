package com.example.user.uprice.DBHelper;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Intent;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import java.text.NumberFormat;
import com.example.user.uprice.MainActivity;
import com.example.user.uprice.R;

public class AddOilcost extends AppCompatActivity {

    private EditText last_oilkm, now_oilkm, now_oil_L,now_km,selected_price_textview;
    private TextView last_oilkm_textview, now_oilkm_textview, now_oil_L_textview,oilprice_textview,now_km_textview;
    private Button oilcost;
    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_oilcost);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar5);
        setSupportActionBar(toolbar);
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawColorToStatusBar();

        //findview
        last_oilkm = (EditText)findViewById(R.id.last_fulloil_km);
        now_oilkm = (EditText)findViewById(R.id.now_fulloil_km);
        now_oil_L = (EditText)findViewById(R.id.now_fulloil_L);
        now_km = (EditText)findViewById(R.id.now_km);
        oilcost = (Button) findViewById(R.id.oilcost_button);
        last_oilkm_textview=(TextView)findViewById(R.id.textView2) ;
        now_oilkm_textview=(TextView)findViewById(R.id.textView3) ;
        now_oil_L_textview=(TextView)findViewById(R.id.textView4) ;
        oilprice_textview=(TextView)findViewById(R.id.textView5) ;
        now_km_textview=(TextView)findViewById(R.id.textView6) ;
        selected_price_textview=(EditText)findViewById(R.id.selected_priceView) ;
        oilprice_select();

        //get ID of contacts
       Bundle bundle =this.getIntent().getExtras();
       final String ID = bundle.getString("pos");


        dbHelper =new DBHelper(this, "contactsDB", null, 1);
        db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM contacts", null);

        //calculate
        oilcost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    float Flast_oilkm = Float.parseFloat(last_oilkm.getEditableText().toString());      // 取得 上次加滿油里程數輸入值
                    float Fnow_oilkm = Float.parseFloat(now_oilkm.getEditableText().toString());     // 取得 這次加滿油里程數輸入值
                    float Fnow_oil_L = Float.parseFloat(now_oil_L.getEditableText().toString());     // 取得 這次加滿油加了幾公升// 輸入值
                    float Foil_price = Float.parseFloat(selected_price_textview.getEditableText().toString());     // 取得 加油的油價 輸入值
                    float Fnow_km = Float.parseFloat(now_km.getEditableText().toString());     // 取得 現在里程數 輸入值
                    float KML,NTL,nowcost;                                     //  一公升跑幾公里，每公里油錢，加滿要多少錢 計算結果

                    KML=((Fnow_oilkm-Flast_oilkm)/Fnow_oil_L); // 計算一公升跑幾公里
                    NTL= (Foil_price*Fnow_oil_L)/(Fnow_oilkm-Flast_oilkm); // 計算每公里油錢
                    nowcost=(Fnow_km-Fnow_oilkm)*NTL;


                    NumberFormat nf = NumberFormat.getInstance();   // 數字格式
                    nf.setMaximumFractionDigits(1);                 // 限制小數第二位
                    String SNowKm =String.valueOf(Fnow_km);
                    String SKML =String.valueOf(nf.format(KML));
                    String SNTL =String.valueOf(nf.format(NTL));
                    String Snowcost =String.valueOf(nf.format(nowcost));

                //nf.format()

                    switch (ID) {
                        case "1":
                            DBopen(v);
                            ContentValues values = new ContentValues();
                        values.put("now_KM", SNowKm);
                        values.put("km_l", SKML);
                        values.put("nt_km", SNTL);
                        values.put("full_oil_nt", Snowcost);
                        //values.put("date",date);
                        Long id = db.insert("oil", null, values);
                        Log.i("ADD: ", String.valueOf(id));
                        AddOilcost.this.finish();
                        break;
                        case "2":
                            DBopen(v);
                            ContentValues valuesA = new ContentValues();
                            valuesA.put("now_KM", SNowKm);
                            valuesA.put("km_l", SKML);
                            valuesA.put("nt_km", SNTL);
                            valuesA.put("full_oil_nt", Snowcost);
                            //values.put("date",date);
                            Long idA = db.insert("oilA", null, valuesA);
                            Log.i("ADD: ", String.valueOf(idA));
                            AddOilcost.this.finish();
                            break;
                        case "3":
                            DBopen(v);
                            ContentValues valuesB = new ContentValues();
                            valuesB.put("now_KM", SNowKm);
                            valuesB.put("km_l", SKML);
                            valuesB.put("nt_km", SNTL);
                            valuesB.put("full_oil_nt", Snowcost);
                            //values.put("date",date);
                            Long idB = db.insert("oilB", null, valuesB);
                            Log.i("ADD: ", String.valueOf(idB));
                            AddOilcost.this.finish();
                            break;
                        case "4":
                            DBopen(v);
                            ContentValues valuesC = new ContentValues();
                            valuesC.put("now_KM", SNowKm);
                            valuesC.put("km_l", SKML);
                            valuesC.put("nt_km", SNTL);
                            valuesC.put("full_oil_nt", Snowcost);
                            //values.put("date",date);
                            Long idC = db.insert("oilC", null, valuesC);
                            Log.i("ADD: ", String.valueOf(idC));
                            AddOilcost.this.finish();
                            break;
                        case "5":
                            DBopen(v);
                            ContentValues valuesD = new ContentValues();
                            valuesD.put("now_KM", SNowKm);
                            valuesD.put("km_l", SKML);
                            valuesD.put("nt_km", SNTL);
                            valuesD.put("full_oil_nt", Snowcost);
                            //values.put("date",date);
                            Long idD = db.insert("oilD", null, valuesD);
                            Log.i("ADD: ", String.valueOf(idD));
                            AddOilcost.this.finish();
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
    public void oilprice_select(){
        Spinner spinner = (Spinner)findViewById(R.id.oilprice);

        final String[] oilprice = {"98無鉛汽油", "95無鉛汽油", "92無鉛汽油", "酒精汽油", "超級柴油"};
        ArrayAdapter<String> lunchList = new ArrayAdapter<>(AddOilcost.this,
                android.R.layout.simple_spinner_dropdown_item,
                oilprice);
        spinner.setAdapter(lunchList);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int pos =position;
                switch (pos){
                    case 0://98
                        selected_price_textview.setText("29.6");
                    break;
                    case 1://95
                        selected_price_textview.setText("27.6");

                    break;
                    case 2://92
                        selected_price_textview.setText("26.1");

                    break;
                    case 3://酒精
                        selected_price_textview.setText("27.6");

                    break;
                    case 4://超柴
                        selected_price_textview.setText("23.9");
                    break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
    public void DBopen(View view) {
            dbHelper =new DBHelper(this, "oilsDB.db", null, 1);
            db = dbHelper.getWritableDatabase();

    }
}

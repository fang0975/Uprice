package com.example.user.uprice.DBHelper;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.user.uprice.R;

public class AddOilcost extends AppCompatActivity {

    private EditText last_oilkm, now_oilkm, now_oil_L,now_km,selected_price_textview;
    private TextView last_oilkm_textview, now_oilkm_textview, now_oil_L_textview,oilprice_textview,now_km_textview;
    private Button oilcost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_oilcost);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
                        selected_price_textview.setText("95.6");
                    break;
                    case 1://95


                    break;
                    case 2://92
                        

                    break;
                    case 3://酒精


                    break;
                    case 4://超柴

                    break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
}

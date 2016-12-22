package com.example.user.uprice;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.user.uprice.xmltojsonlib.XmlToJson;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static final MediaType MEDIA_TYPE_MARKDOWN
            = MediaType.parse("application/soap+xml; charset=utf-8");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        String postBody = "<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">" +
                "  <soap12:Body>" +
                "    <getCPCMainProdListPrice_English xmlns=\"http://tmtd.cpc.com.tw/\" />\n" +
                "  </soap12:Body>" +
                "</soap12:Envelope>";
        final RequestBody body = RequestBody.create(MEDIA_TYPE_MARKDOWN, postBody);
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://vipmember.tmtd.cpc.com.tw/OpenData/ListPriceWebService.asmx")
                .post(body)
                .addHeader("Content-Type", "application/soap+xml; charset=utf-8") //Notice this request has header if you don't need to send a header just erase this part
                .addHeader("Content-Length", "length")
                .build();

        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("HttpService", "onFailure() Request was: " );

                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();
                Log.e("Response",str.length()+"");
                XmlToJson xmlToJson = new XmlToJson.Builder(str).build();
                JSONObject jsonObject = xmlToJson.toJson();
                // convert to a Json String
                String jsonString = xmlToJson.toString();
                // convert to a formatted Json String
                String formatted = xmlToJson.toFormattedString();

                Log.d("JSON", "jsonObject:" + jsonObject.toString());
                Log.d("JSON", "jsonString:" + jsonString);
                Log.d("JSON", "formatted:" + formatted);



                /*        Log.e("123",str.length()+"");
                for(int i=0;i<str.length()-100;i++){
                    char[] chars1 = new char[] {};
                    String product="<產品名稱>";
                    String get="";
                    for(int j=i;j<=i+5;j++){
                        get+=str.charAt(j);
                    }
                    Log.e("123",get);
                    Log.e("456",product);
                    Log.e("  ","\n");
                    if(get == product){
                        String out="";
                        for(int k=i+6;k<=i+13;k++){
                            out+=str.charAt(k);
                        }
                        Log.e("response ", "onResponse(): " + out );
                    }
                }*/
            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fragmentManager = getFragmentManager();
        if (id == R.id.nav_PersonalGarage) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_main,
                            new PersonalGarageFragment()).commit();
        } else if (id == R.id.nav_OilPrice) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_main,
                            new OilPriceFragment()).commit();
        } else if (id == R.id.nav_GasStation) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_main,
                            new GasStationFragment()).commit();
        } else if (id == R.id.nav_Setting) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_main,
                            new SettingFragment()).commit();
        } /*else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

package com.example.user.uprice;

import android.app.FragmentManager;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.example.user.uprice.DBHelper.Information;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
                             PersonalGarageFragment.OnFragmentInteractionListener{

    private ListView Info_contact;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Uprice");

        setSupportActionBar(toolbar);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_main, new OilPriceFragment()).commit();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        // 呼叫自己額外宣告的方法，執行所有取得畫面元件物件的工作
        processViews();
        // 呼叫自己額外宣告的方法，執行所有註冊的工作
        processControllers();

    }
    private void processViews() {
        // 在這個方法中，取得畫面元件物件後指定給欄位變數
        Info_contact = (ListView)findViewById(R.id.Info_contact);
    }

    private void processControllers() {

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
           // iFrag.writeToDB();
            getFragmentManager().beginTransaction()
                    .replace(R.id.content_main,
                            new PersonalGarageFragment()).commit();

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
            startActivity(new Intent(MainActivity.this, Information.class));

            //fragmentManager.beginTransaction().replace(R.id.content_main, new PersonalGarageFragment()).commit();
        } else if (id == R.id.nav_OilPrice) {
            fragmentManager.beginTransaction().replace(R.id.content_main, new OilPriceFragment()).commit();
        } else if (id == R.id.nav_GasStation) {
            fragmentManager.beginTransaction().replace(R.id.content_main, new GasStationFragment()).commit();
        } else if (id == R.id.nav_Setting) {
            fragmentManager.beginTransaction().replace(R.id.content_main, new SettingFragment()).commit();
        } /*else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    @Override
    public void onFragmentPInteraction(String str) {

    }
}

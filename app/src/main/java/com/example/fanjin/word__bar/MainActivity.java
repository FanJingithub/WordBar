package com.example.fanjin.word__bar;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;


public class MainActivity extends BaseActivity {
    private MyDatabaseHelper dbHelper;
    private SQLiteDatabase db;
    ContentValues values;
    private DrawerLayout mDrawerLayout;
    Intent intent,intent2;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    Button show_click,showlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);
        show_click=(Button) findViewById(R.id.show_click);
        showlist = (Button) findViewById(R.id.show_list);;
        pref= getSharedPreferences("data",MODE_PRIVATE);
        boolean oldUser=pref.getBoolean("oldUser",false);
        int count=pref.getInt("count",0);
        if (!oldUser){
            show_click.setVisibility(View.VISIBLE);
        }
/*
        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        ActionBar actionBar=getSupportActionBar();
        if (actionBar !=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.icon_menu);
        }
*/
        //navView.setCheckedItem(R.id.user_center_wallet);

        Button aMenu=(Button) findViewById(R.id.a_menu);
        aMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(GravityCompat.START);
                editor=pref.edit();
                editor.putBoolean("oldUser",true);
                editor.commit();
                show_click.setVisibility(View.INVISIBLE);
            }
        });
        Button aHelp=(Button) findViewById(R.id.a_help);
        aHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent4=new Intent(MainActivity.this,TellAct.class);
                startActivity(intent4);
            }
        });

        mDrawerLayout=(DrawerLayout) findViewById(R.id.drawer_layout_main);
        NavigationView navView=(NavigationView) findViewById(R.id.nav_view_main);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.to_listv:
                        Log.e("click","------------credit");
                        intent2=new Intent(MainActivity.this,books.class);
                        startActivity(intent2);
                        break;
                    case R.id.to_listdel:
                        Log.e("click","------------help");
                        intent2=new Intent(MainActivity.this,wordInList.class);
                        intent2.putExtra("flag",3);
                        startActivity(intent2);
                        break;
                    case R.id.to_about:
                        Log.e("click","------------about");
                        intent2=new Intent(MainActivity.this,showabout.class);
                        startActivity(intent2);
                        break;
                    case R.id.to_trash:
                        Log.e("click","------------about");
                        intent2=new Intent(MainActivity.this,trash.class);
                        startActivity(intent2);
                        break;
                    case R.id.to_query:
                        Log.e("click","------------logout");
                        intent2=new Intent(MainActivity.this,QueryAct.class);
                        startActivity(intent2);
                        break;
                    case R.id.to_tell:
                        Log.e("click","------------logout");
                        intent2=new Intent(MainActivity.this,TellAct.class);
                        startActivity(intent2);
                        break;
                    default:
                }
                //mDrawerLayout.closeDrawers();
                return true;
            }
        });

        dbHelper = new MyDatabaseHelper(this, "myword.db", null, 1);
        db = dbHelper.getWritableDatabase();


        showlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, showlx.class);
                startActivity(intent);
            }
        });

    }
}

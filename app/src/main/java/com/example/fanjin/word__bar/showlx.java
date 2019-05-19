package com.example.fanjin.word__bar;


import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by FanJin on 2016/3/16.
 */
public class showlx extends BaseActivity implements View.OnClickListener {
    private TextView tv,showch,showexp,show_yinbiao;
    private WebView webView;
    private TextView tw;
    private TextView ty;
    private Button winit;
    private Button failit;
    private Button cutit,show_detail,choice2;
    private Button cutthem;
    private MyDatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private String sid;
    private String slx,sch,sexp;
    private String sword;
    private int lxi,lxid;
    private int snow,stot,tot;
    private int maxn,iid;
    private int[] getid=new int[20000];
    private int[] getls=new int[20000];
    ContentValues values;
    private DrawerLayout mDrawerLayout;
    Intent intent,intent2;
    InputStream in = null;
    BufferedReader reader = null;
    String mp3,yinbiao,explain,syinbiao,smp3;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    Button vvv;
    private MediaPlayer mediaPlayer;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.have_menu);
   /*     Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar=getSupportActionBar();
        if (actionBar !=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
*/

        Button aMenu=(Button) findViewById(R.id.a_menu2);
        aMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });
        Button aHelp=(Button) findViewById(R.id.a_help2);
        aHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent4=new Intent(showlx.this,TellAct.class);
                startActivity(intent4);
            }
        });

        //navView.setCheckedItem(R.id.user_center_wallet);
        mDrawerLayout=(DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navView=(NavigationView) findViewById(R.id.nav_view);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.to_listv:
                        Log.e("click","------------credit");
                        intent2=new Intent(showlx.this,books.class);
                        startActivity(intent2);
                        break;
                    case R.id.to_listdel:
                        Log.e("click","------------help");
                        intent2=new Intent(showlx.this,wordInList.class);
                        intent2.putExtra("flag",3);
                        startActivity(intent2);
                        break;
                    case R.id.to_about:
                        Log.e("click","------------about");
                        intent2=new Intent(showlx.this,showabout.class);
                        startActivity(intent2);
                        break;
                    case R.id.to_trash:
                        Log.e("click","------------about");
                        intent2=new Intent(showlx.this,trash.class);
                        startActivity(intent2);
                        break;
                    case R.id.to_query:
                        Log.e("click","------------logout");
                        intent2=new Intent(showlx.this,QueryAct.class);
                        startActivity(intent2);
                        break;
                    case R.id.to_tell:
                        Log.e("click","------------logout");
                        intent2=new Intent(showlx.this,TellAct.class);
                        startActivity(intent2);
                        break;
                    default:
                }
                //mDrawerLayout.closeDrawers();
                return true;
            }
        });

        tv=(TextView) findViewById(R.id.show_it);
        tw=(TextView) findViewById(R.id.show_word);
        //ty=(TextView) findViewById(R.id.tell_you);
        //showch=(TextView) findViewById(R.id.show_ch);
        showexp=(TextView) findViewById(R.id.show_exp);
        show_yinbiao=(TextView) findViewById(R.id.show_yinbiao);
        //webView=(WebView)findViewById(R.id.show_mp3);
        //webView.getSettings().setJavaScriptEnabled(true);

        winit = (Button) findViewById(R.id.win_it);
        cutthem = (Button) findViewById(R.id.cut_them);
        show_detail = (Button) findViewById(R.id.show_detail);
        dbHelper = new MyDatabaseHelper(this, "myword.db", null, 1);
        db = dbHelper.getWritableDatabase();
        shownext();

        winit.setOnClickListener(this);
        cutthem.setOnClickListener(this);
        show_detail.setOnClickListener(this);
    }

    public void shownext(){
        slx="当前复习计划中没有单词，赶紧到词库中去添加吧！";
        sch="";
        sword="";
        sexp="";
        yinbiao="";
        showexp.setText("");
        show_detail.setVisibility(View.VISIBLE);

        maxn=0;
        Cursor cursor = db.query("getword", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                iid = cursor.getInt(cursor.getColumnIndex("id"));
                maxn++;
                getls[iid]=maxn;
                getid[maxn]=iid;
            } while (cursor.moveToNext());
        }
        cursor.close();
        lxi=(int)(Math.random() * maxn+1);
        lxid=getid[lxi];
        sid = String.valueOf(lxid) ;
        Log.e("showmaxn","_____________"+maxn+"____________");
        Log.e("showlxi","_____________"+lxi+"____________");
        Log.e("showlxid","_____________"+lxid+"____________");
        cursor = db.query("getword", null, "id=?", new String[]{sid + ""}, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                sword = cursor.getString(cursor.getColumnIndex("word"));
                Log.e("showword", "____________"+sword+"__________________");
                snow= cursor.getInt(cursor.getColumnIndex("now"));
                Log.e("shownowx", "____________"+snow+"__________________");
                stot= cursor.getInt(cursor.getColumnIndex("total"));
                Log.e("showtot", "____________"+stot+"__________________");

                if (snow==0){
                    db.execSQL("create table " + sword + "_ (id integer primary key autoincrement, lx text,ch text)");
                    tot=0;
                    InputStream in = null;
                    BufferedReader reader = null;
                    try {
                        in=showlx.this.getClass().getClassLoader().getResourceAsStream("assets/"+sword+".txt");  //fix
                        Log.e("for_in", "_______________1____________");
                        reader = new BufferedReader(new InputStreamReader(in));
                        Log.e("for_in", "_______________2____________");
                        String line = "";
                        while ((line = reader.readLine()) != null) {
                            ContentValues values = new ContentValues();
                            values.put("lx", line);
                            line = reader.readLine();
                            values.put("ch", line);
                            db.insert(sword+"_", null, values); // 插入
                            values.clear();
                            tot++;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if (reader != null) {
                            try {
                                reader.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    ContentValues values = new ContentValues();
                    values.put("total", tot);
                    db.update("wordlist", values, "word=?", new String[]{sword + ""});
                    db.update("getword", values, "word=?", new String[]{sword + ""});
                    stot=tot;
                }

                if (snow>=stot) snow=0;
                snow++;
                ContentValues values = new ContentValues();
                values.put("now", snow);
                db.update("getword", values, "word=?", new String[]{sword + ""});
                Log.e("show", "____________hhh__________________");
                Cursor cursor2 = db.query(sword+"_", null, "id=?", new String[]{snow + ""}, null, null, null);
                Log.e("show", "____________kkk__________________");
                if (cursor2.moveToFirst()) {
                    do {
                        slx= cursor2.getString(cursor2.getColumnIndex("lx"));
                        sch= cursor2.getString(cursor2.getColumnIndex("ch"));
                    }while (cursor2.moveToNext());
                }
                Log.e("showlx", "____________"+slx+"__________________");
                cursor2.close();


                in = null;
                reader = null;
                try {

                    in=showlx.this.getClass().getClassLoader().getResourceAsStream("assets/_"+sword+".txt");
                    reader = new BufferedReader(new InputStreamReader(in));
                    mp3 = reader.readLine();

                    in=showlx.this.getClass().getClassLoader().getResourceAsStream("assets/&"+sword+".txt");
                    reader = new BufferedReader(new InputStreamReader(in));
                    yinbiao = reader.readLine();

                    in=showlx.this.getClass().getClassLoader().getResourceAsStream("assets/+"+sword+".txt");
                    reader = new BufferedReader(new InputStreamReader(in));
                    explain = reader.readLine();

                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("err","__________");
                } finally {
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }while (cursor.moveToNext());
        }
        cursor.close();
        tv.setText(slx);
        //showch.setText(sch);
        tw.setText(sword);
        show_yinbiao.setText(yinbiao);
        //webView.setWebViewClient(new WebViewClient());
        //webView.loadUrl(mp3);
        //Log.e("mp3","_______"+mp3);
        //ty.setText("");
        //showexp.setText(sword+"\n"+sexp);
        //showch.setVisibility(View.INVISIBLE);
        //showexp.setVisibility(View.INVISIBLE);

        vvv=(Button)findViewById(R.id.vvv);
        if (sch.length()==0){
            vvv.setVisibility(View.INVISIBLE);
        } else {
            vvv.setVisibility(View.VISIBLE);
        }
        vvv.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                boolean createState=false;
                //if(mediaPlayer==null){
                    mediaPlayer=createNetMp3();
                    createState=true;
                //}
                //当播放完音频资源时，会触发onCompletion事件，可以在该事件中释放音频资源，
                //以便其他应用程序可以使用该资源:
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        //mp.release();//释放音频资源
                        //setTitle("资源已经被释放了");
                    }
                });
                try {
                    //在播放音频资源之前，必须调用Prepare方法完成些准备工作
                    if(createState) mediaPlayer.prepare();
                    //开始播放音频
                    mediaPlayer.start();
                    //vv.setText("暂停");
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void DelLx(){
        db.delete("lxlist", "lx=?", new String[] { slx+"" });
    }
    private void DelWord(){
        values = new ContentValues();
        values.put("flag", 4 + "");
        db.update("wordlist", values, "word=?", new String[]{sword + ""});
        db.delete("getword", "word=?", new String[]{sword + ""});
        values.clear();
        values.put("word", sword);
        values.put("now", 0);
        values.put("total", stot);
        db.insert("delword", null, values);
        values.clear();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.win_it:
                Log.e("win","___________1_______________");
                showexp.setText("");
                shownext();
                break;
            case R.id.cut_them:
                Log.e("cut them", "___________1_______________");
                showexp.setText("");
                DelWord();
                shownext();
                break;
            case R.id.show_detail:
                //showch.setVisibility(View.VISIBLE);
                show_detail.setVisibility(View.INVISIBLE);
                if (sch.length()>0){
                    showexp.setText("翻译："+sch+"\n\n单词释义："+sword+'\n'+explain);
                } else {
                    showexp.setText("");
                }
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
        }
        return true;
    }

    public MediaPlayer createNetMp3(){
        String url=mp3;
        MediaPlayer mp=new MediaPlayer();
        try {
            mp.setDataSource(url);
        } catch (IllegalArgumentException e) {
            return null;
        } catch (IllegalStateException e) {
            return null;
        } catch (IOException e) {
            return null;
        }
        return mp;
    }
}

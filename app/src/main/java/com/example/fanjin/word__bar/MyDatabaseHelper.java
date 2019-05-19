package com.example.fanjin.word__bar;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by FanJin on 2016/3/16.
 */
public class MyDatabaseHelper extends SQLiteOpenHelper {
    InputStream in = null;
    BufferedReader reader = null;
    String mp3,yinbiao,explain,syinbiao,smp3,sword,line;

    public static final String CREATE_WL = "create table wordlist ("
            + "id integer primary key autoincrement, "
            + "word text, "
            + "flag integer, "
            + "total integer, "
            + "now integer)";
    public static final String CREATE_CW = "create table cetword ("
            + "id integer primary key autoincrement, "
            + "word text, "
            + "total integer, "
            + "now integer)";
    public static final String CREATE_FW = "create table fetword ("
            + "id integer primary key autoincrement, "
            + "word text, "
            + "total integer, "
            + "now integer)";
    public static final String CREATE_TW = "create table toeflword ("
            + "id integer primary key autoincrement, "
            + "word text, "
            + "total integer, "
            + "now integer)";
    public static final String CREATE_DW = "create table delword ("
            + "id integer primary key autoincrement, "
            + "word text, "
            + "total integer, "
            + "now integer)";
    public static final String CREATE_GW = "create table getword ("
            + "id integer primary key autoincrement, "
            + "word text, "
            + "total integer, "
            + "now integer)";
    public static final String CREATE_EX = "create table _explain ("
            + "id integer primary key autoincrement, "
            + "word text, "
            + "exp text) ";

    private String[] list_cet=new String[6000];
    private String[] list_toefl=new String[6000];
    private String[] list_toefl2=new String[2000];
    private String[] list_all=new String[10000];

    private int tot_cet,tot_toefl,tot_all,tot_toefl2;

    private int i,tot,num;
    private char nc;
    private String nword,nexp;
    private Context mContext;
    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory
            factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_WL);
        db.execSQL(CREATE_CW);
        db.execSQL(CREATE_FW);
        db.execSQL(CREATE_DW);
        db.execSQL(CREATE_GW);
        db.execSQL(CREATE_EX);
        db.execSQL(CREATE_TW);
        db.execSQL("create table detail (word text, mp3 text, yinbiao text ,explain text)");
        //Toast.makeText(mContext, "正在初始化数据，可能需要3分钟左右，请耐心等待！", Toast.LENGTH_SHORT).show();

        initialList();

        for(i=2;i<=5943;i++){

            ContentValues values = new ContentValues();
            values.put("word", list_cet[i]);
            //values.put("total", tot);
            values.put("flag", 1+"");
            db.insert("wordlist", null, values); // 插入
            values.clear();

            ContentValues values2 = new ContentValues();
            values2.put("word", list_cet[i]);
            db.insert("cetword", null, values2); // 插入
            values2.clear();

        }
        //Toast.makeText(mContext, "正在初始化数据，可能需要3分钟左右，请耐心等待！", Toast.LENGTH_SHORT).show();
        for(i=1;i<=4283;i++){

            ContentValues values = new ContentValues();
            values.put("word", list_toefl[i]);

            values.put("flag", 1+"");
            db.insert("wordlist", null, values); // 插入
            values.clear();

            ContentValues values2 = new ContentValues();
            values2.put("word", list_toefl[i]);
            db.insert("fetword", null, values2); // 插入
            values2.clear();

        }

        for(i=1;i<=1993;i++){
            ContentValues values = new ContentValues();
            values.put("word", list_toefl2[i]);

            values.put("flag", 1+"");
            db.insert("wordlist", null, values); // 插入
            values.clear();

            ContentValues values2 = new ContentValues();
            values2.put("word", list_toefl2[i]);
            db.insert("toeflword", null, values2); // 插入
            values2.clear();

        }

        //Toast.makeText(mContext, "数据载入完毕！", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch(oldVersion){
            case 1:

        }
        //Toast.makeText(mContext, "数据载入完毕！", Toast.LENGTH_SHORT).show();
    }

    private void initialList(){
        in = null;
        reader = null;
        try {

            tot_cet=0;
            in=MyDatabaseHelper.this.getClass().getClassLoader().getResourceAsStream("assets/cet_all.txt");
            reader = new BufferedReader(new InputStreamReader(in));
            line = "";
            while ((line = reader.readLine()) != null) {
                list_cet[tot_cet++]=line;
            }

            tot_toefl=0;
            in=MyDatabaseHelper.this.getClass().getClassLoader().getResourceAsStream("assets/toefl_all.txt");
            reader = new BufferedReader(new InputStreamReader(in));
            line = "";
            while ((line = reader.readLine()) != null) {
                list_toefl[tot_toefl++]=line;
            }

            tot_toefl2=0;
            in=MyDatabaseHelper.this.getClass().getClassLoader().getResourceAsStream("assets/toefl_only.txt");
            reader = new BufferedReader(new InputStreamReader(in));
            line = "";
            while ((line = reader.readLine()) != null) {
                list_toefl2[tot_toefl2++]=line;
            }

            tot_all=0;
            in=MyDatabaseHelper.this.getClass().getClassLoader().getResourceAsStream("assets/2all.txt");
            reader = new BufferedReader(new InputStreamReader(in));
            line = "";
            while ((line = reader.readLine()) != null) {
                list_all[tot_all++]=line;
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

    }
}

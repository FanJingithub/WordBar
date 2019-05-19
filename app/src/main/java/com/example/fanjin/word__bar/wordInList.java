package com.example.fanjin.word__bar;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by FanJin on 2016/9/12.
 */
public class wordInList extends BaseActivity{
    private MyDatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private String sid,str,lib;
    private String sword;
    private int tot,ff,i,maxn,iid,totn,num,flag;
    public static int[] fff=new int[20000];
    private int[] getid=new int[20000];
    private int[] getls=new int[20000];
    private String[] the_word=new String[1000];
    private TextView tott;
    ContentValues values,values2;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    private ListView lv;
    private MyListAdapter mAdapter;
    private ArrayList<String> list;
    private Button bt_selectall;
    private Button bt_cancel;
    private Button bt_deselectall,back;
    public static int checkNum; // 记录选中的条目数量
    public static TextView tv_show;// 用于显示选中的条目数量

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_cet);
        Intent intent=getIntent();
        num=intent.getIntExtra("click", 0);
        flag=intent.getIntExtra("flag",0);
        if (flag==0){
            str="六级词汇      ";
            lib="cetword";
        } else if (flag==1){
            str="托福词汇      ";
            lib="fetword";
        }else if (flag==2){
            str="托福高级词汇   ";
            lib="toeflword";
        } else if (flag==3){
            str="已添加的单词   ";
        }

        TextView head_cet=(TextView)  findViewById(R.id.head_cet);
        head_cet.setText(str);

        back=(Button) findViewById(R.id.back_listcet);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        lv = (ListView) findViewById(R.id.lv);
        tv_show = (TextView) findViewById(R.id.tv);

        dbHelper = new MyDatabaseHelper(this, "myword.db", null, 1);
        db = dbHelper.getWritableDatabase();
        list = new ArrayList<String>();
        maxn=-1;
        totn=0;
        for (i=0;i<20000;i++){
            fff[i]=-1;
            getid[i]=-1;
            getls[i]=-1;
        }
        checkNum=0;

        if (flag<3){
            Cursor cursor = db.query(lib, null, null, null, null, null, null);
            if (cursor.moveToFirst()) {
                do {
                    sword = cursor.getString(cursor.getColumnIndex("word"));
                    iid = cursor.getInt(cursor.getColumnIndex("id"));
                    if ((sword!=null)&&(sword.substring(0,1).equals ((char)(num+97)+""))){
                        list.add(sword);
                        maxn++;
                        getid[maxn]=iid;
                        getls[iid]=maxn;
                        fff[maxn]=-1;
                        the_word[maxn]=sword;
                    }
                } while (cursor.moveToNext());
            }
            cursor.close();

            Button confcet = (Button) findViewById(R.id.conf_cet);
            confcet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Cursor cursor = db.query(lib, null, null,null, null, null, null);
                    if (cursor.moveToFirst()) {
                        do {
                            iid=cursor.getInt(cursor.getColumnIndex("id"));
                            if ((getls[iid]>=0) && (fff[getls[iid]]==1)){
                                sword = cursor.getString(cursor.getColumnIndex("word"));
                                tot=cursor.getInt(cursor.getColumnIndex("total"));
                                db.delete(lib, "word=?", new String[]{sword + ""});

                                values = new ContentValues();
                                values.put("flag", 3+"");
                                db.update("wordlist", values, "word=?", new String[]{sword + ""});
                                values.clear();

                                Cursor cursor3 = db.query("getword", null,"word=?", new String[]{sword + ""},null,null,null);
                                boolean existed=false;
                                if (cursor3.moveToFirst()) {
                                    do {
                                        existed=true;
                                    } while (cursor3.moveToNext());
                                }
                                cursor3.close();

                                if (!existed){
                                    values2 = new ContentValues();
                                    values2.put("word", sword);
                                    values2.put("total", tot);
                                    values2.put("now", 0);
                                    db.insert("getword", null, values2);
                                    values2.clear();

                                }
                            }

                        } while (cursor.moveToNext());
                    }
                    cursor.close();
                    finish();
                }
            });

        } else if (flag==3){
            Cursor cursor = db.query("getword", null, null, null, null, null, null);
            if (cursor.moveToFirst()) {
                do {
                    sword = cursor.getString(cursor.getColumnIndex("word"));
                    iid = cursor.getInt(cursor.getColumnIndex("id"));
                    list.add(sword);
                    maxn++;
                    getid[maxn]=iid;
                    getls[iid]=maxn;
                    fff[maxn]=-1;
                    the_word[maxn]=sword;
                } while (cursor.moveToNext());
            }
            cursor.close();

            Button listDelData = (Button) findViewById(R.id.conf_cet);
            listDelData.setText("确认删除");
            listDelData.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Cursor cursor = db.query("getword", null, null,null, null, null, null);
                    if (cursor.moveToFirst()) {
                        do {
                            iid=cursor.getInt(cursor.getColumnIndex("id"));
                            if (fff[getls[iid]]==1) {
                                sword = cursor.getString(cursor.getColumnIndex("word"));
                                tot=cursor.getInt(cursor.getColumnIndex("total"));
                                //ff=cursor.getInt(cursor.getColumnIndex("flag"));

                                values = new ContentValues();
                                values.put("flag", 4 + "");
                                db.update("wordlist", values, "word=?", new String[]{sword + ""});
                                db.delete("getword", "word=?", new String[]{sword + ""});
                                values.clear();
                                values = new ContentValues();
                                //values.put("id", iid);
                                values.put("word", sword);
                                values.put("total", tot);
                                values.put("now", 0);
                                //values.put("wc", 0);
                                //values.put("rate", 0);
                                //values.put("rank", 0);
                                db.insert("delword", null, values);
                                values.clear();

                            }
                        } while (cursor.moveToNext());
                    }
                    cursor.close();
                    finish();
                }
            });
        }

        // 实例化自定义的MyAdapter
        mAdapter = new MyListAdapter(list, this);
        // 绑定Adapter
        lv.setAdapter(mAdapter);
        // 绑定listView的监听器
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Intent intent2=new Intent(wordInList.this,showWord.class);
                intent2.putExtra("word",the_word[arg2]);
                startActivity(intent2);
            }
        });
    }
}

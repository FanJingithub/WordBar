package com.example.fanjin.word__bar;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by FanJin on 2016/9/19.
 */
public class trash extends BaseActivity {
    private MyDatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private String sid;
    private String sword;
    ContentValues values;
    private int tot,i,maxn,iid;
    private int[] fff=new int[20000];
    private int[] getid=new int[20000];
    private int[] getls=new int[20000];
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_trash);

        back=(Button) findViewById(R.id.back_trash);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        dbHelper = new MyDatabaseHelper(this, "myword.db", null, 1);
        db = dbHelper.getWritableDatabase();
        ArrayList<String> nn = new ArrayList<String> ();
        maxn=0;
        Cursor cursor = db.query("delword", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                sword = cursor.getString(cursor.getColumnIndex("word"));
                iid = cursor.getInt(cursor.getColumnIndex("id"));
                nn.add(sword);
                maxn++;
                getid[maxn]=iid;
                getls[iid]=maxn;
                fff[maxn]=-1;
            } while (cursor.moveToNext());
        }
        cursor.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(trash.this, android.R.layout.simple_list_item_multiple_choice, nn);
        ListView listView = (ListView) findViewById(R.id.list_viewtrash);
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        for (i=1;i<=maxn;i++) fff[i]=-1;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                fff[arg2+1]=-fff[arg2+1];
            }
        });

        Button listtrashData = (Button) findViewById(R.id.list_trashData);
        listtrashData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = db.query("delword", null, null,null, null, null, null);
                if (cursor.moveToFirst()) {
                    do {
                        iid=cursor.getInt(cursor.getColumnIndex("id"));
                        if (fff[getls[iid]]==1) {
                            sword = cursor.getString(cursor.getColumnIndex("word"));
                            tot=cursor.getInt(cursor.getColumnIndex("total"));
                            //ff=cursor.getInt(cursor.getColumnIndex("flag"));

                            values = new ContentValues();
                            values.put("flag", 3 + "");
                            values.put("now", 1 + "");
                            db.update("wordlist", values, "word=?", new String[]{sword + ""});
                            db.delete("delword", "word=?", new String[]{sword + ""});
                            values.clear();
                            values = new ContentValues();
                            values.put("word", sword);
                            values.put("total", tot);
                            values.put("now", 1);
                            //values.put("wc", 0);
                            //values.put("rate", 0);
                            //values.put("rank", 0);
                            db.insert("getword", null, values);
                            values.clear();

                        }
                    } while (cursor.moveToNext());
                }
                cursor.close();
                finish();
            }
        });
    }
}
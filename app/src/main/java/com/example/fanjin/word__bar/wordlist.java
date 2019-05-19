package com.example.fanjin.word__bar;


import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


import java.util.ArrayList;

/**
 * Created by FanJin on 2016/3/18.
 */
public class wordlist extends BaseActivity {
    private MyDatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private String sid;
    private String sword;
    private int tot,ff,i,maxn,iid;
    private int[] fff=new int[20000];
    private int[] getid=new int[20000];
    private int[] getls=new int[20000];
    ContentValues values,values2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_listv);

        dbHelper = new MyDatabaseHelper(this, "myword.db", null, 1);
        db = dbHelper.getWritableDatabase();
        ArrayList<String> nn = new ArrayList<String> ();
        maxn=0;
        Cursor cursor = db.query("nolist", null, null, null, null, null, null);
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

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(wordlist.this, android.R.layout.simple_list_item_multiple_choice, nn);
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                fff[arg2+1]=-fff[arg2+1];
            }
        });

        Button listAddData = (Button) findViewById(R.id.list_AddData);
        listAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = db.query("nolist", null, null,null, null, null, null);
                if (cursor.moveToFirst()) {
                    do {
                        iid=cursor.getInt(cursor.getColumnIndex("id"));
                        if (fff[getls[iid]]==1){
                            sword = cursor.getString(cursor.getColumnIndex("word"));
                            tot=cursor.getInt(cursor.getColumnIndex("total"));
                            //ff=cursor.getInt(cursor.getColumnIndex("flag"));
                            values = new ContentValues();
                            values.put("flag", 1 + "");
                            db.update("allword", values, "word=?", new String[]{sword + ""});
                            db.delete("nolist", "word=?", new String[]{sword + ""});
                            values.clear();
                            values2 = new ContentValues();
                            values2.put("id", iid);
                            values2.put("word", sword);
                            values2.put("total", tot);
                            values2.put("now", 0);
                            values2.put("wc", 0);
                            values2.put("rate", 0);
                            values2.put("rank", 0);
                            db.insert("wordlist", null, values2);
                            values2.clear();
                        }

                    } while (cursor.moveToNext());
                }
                cursor.close();
                finish();
            }
        });
    }
}

package com.example.fanjin.word__bar;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by FanJin on 2016/3/19.
 */
public class QueryAct extends BaseActivity {
    private MyDatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private int ff,tot;
    private String sword;
    Cursor cursor;
    ContentValues values;
    TextView qqresult;
    Button queryit,clearit,back;
    Button conData,nconData;
    EditText wordEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.query_act);

        queryit = (Button) findViewById(R.id.query_it);
        clearit=(Button) findViewById(R.id.clear_it);
        wordEdit = (EditText) findViewById(R.id.Word_Edit);
        qqresult=(TextView)findViewById(R.id.qqresult);
        back=(Button) findViewById(R.id.back_query);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        clearit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                wordEdit.setText("");
                qqresult.setVisibility(View.INVISIBLE);
            }
        });


        queryit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sword = wordEdit.getText().toString().toLowerCase();
                wordEdit.setError(null);
                View focusView = null;
                if (sword.equals("")){
                    wordEdit.setError("请输入单词！");
                    focusView = wordEdit;
                }
                else {
                    dbHelper = new MyDatabaseHelper(QueryAct.this, "myword.db", null, 1);
                    db = dbHelper.getWritableDatabase();

                    cursor = db.query("wordlist", null, "word=?", new String[]{sword + ""}, null, null, null);
                    if (!cursor.moveToFirst()){

                        qqresult.setVisibility(View.VISIBLE);
                        qqresult.setText("抱歉，词库里没有这个单词");
                    } else {
                        qqresult.setVisibility(View.INVISIBLE);
                        Intent intent =new Intent(QueryAct.this,result.class);
                        intent.putExtra("word",sword);
                        startActivity(intent);
                    }
                    cursor.close();
                }

                /*
                dbHelper = new MyDatabaseHelper(QueryAct.this, "myword.db", null, 1);
                db = dbHelper.getWritableDatabase();

                cursor = db.query("wordlist", null, "word=?", new String[]{sword + ""}, null, null, null);
                if (cursor.moveToFirst()) {
                    do {
                        ff=0;
                        ff = cursor.getInt(cursor.getColumnIndex("flag"));
                        if (ff == 1) {
                            qresult.setText("从词库里找到了这个单词，是否添加？");
                            queryit.setVisibility(View.INVISIBLE);
                            conData.setVisibility(View.VISIBLE);
                            nconData.setVisibility(View.VISIBLE);
                            nconData.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    wordEdit.setText("");
                                    conData.setVisibility(View.INVISIBLE);
                                    nconData.setVisibility(View.INVISIBLE);
                                    queryit.setVisibility(View.VISIBLE);
                                    qresult.setText("");
                                }

                            });
                            conData.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    values = new ContentValues();
                                    values.put("flag", 3 + "");
                                    db.update("wordlist", values, "word=?", new String[]{sword + ""});
                                    db.delete("cetword", "word=?", new String[]{sword + ""});
                                    values.clear();
                                    values.put("word", sword);
                                    values.put("total", tot);
                                    values.put("now", 0);
                                    db.insert("getword", null, values);
                                    values.clear();
                                    wordEdit.setText("");
                                    conData.setVisibility(View.INVISIBLE);
                                    nconData.setVisibility(View.INVISIBLE);
                                    queryit.setVisibility(View.VISIBLE);
                                    qresult.setText("");
                                }
                            });
                        }
                        else if (ff == 2) {
                            qresult.setText("从FET词库里找到了这个单词，是否添加？");
                            queryit.setVisibility(View.INVISIBLE);
                            conData.setVisibility(View.VISIBLE);
                            nconData.setVisibility(View.VISIBLE);
                            nconData.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    wordEdit.setText("");
                                    conData.setVisibility(View.INVISIBLE);
                                    nconData.setVisibility(View.INVISIBLE);
                                    queryit.setVisibility(View.VISIBLE);
                                    qresult.setText("");
                                }

                            });
                            conData.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    values = new ContentValues();
                                    values.put("flag", 3 + "");
                                    db.update("wordlist", values, "word=?", new String[]{sword + ""});
                                    db.delete("fetword", "word=?", new String[]{sword + ""});
                                    values.clear();
                                    values.put("word", sword);
                                    values.put("total", tot);
                                    values.put("now", 0);
                                    db.insert("getword", null, values);
                                    values.clear();
                                    wordEdit.setText("");
                                    conData.setVisibility(View.INVISIBLE);
                                    nconData.setVisibility(View.INVISIBLE);
                                    queryit.setVisibility(View.VISIBLE);
                                    qresult.setText("");
                                }
                            });
                        }
                        else if (ff==3){
                            qresult.setText("你的学习日志里已有这个单词，是否删除？");
                            queryit.setVisibility(View.INVISIBLE);
                            conData.setVisibility(View.VISIBLE);
                            nconData.setVisibility(View.VISIBLE);
                            nconData.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    wordEdit.setText("");
                                    conData.setVisibility(View.INVISIBLE);
                                    nconData.setVisibility(View.INVISIBLE);
                                    queryit.setVisibility(View.VISIBLE);
                                    qresult.setText("");
                                }
                            });
                            conData.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    values = new ContentValues();
                                    values.put("flag", 4 + "");
                                    db.update("wordlist", values, "word=?", new String[]{sword + ""});
                                    db.delete("getword", "word=?", new String[]{sword + ""});
                                    values.clear();
                                    values.put("word", sword);
                                    values.put("now", 0);
                                    db.insert("delword", null, values);
                                    values.clear();
                                    wordEdit.setText("");
                                    conData.setVisibility(View.INVISIBLE);
                                    nconData.setVisibility(View.INVISIBLE);
                                    queryit.setVisibility(View.VISIBLE);
                                    qresult.setText("");
                                }
                            });
                        }

                        else if (ff==4){
                            qresult.setText("之前已删除该单词！");
                        }
                        else {
                            if (sword.equals("")){
                                qresult.setText("请输入单词！");
                            }else{
                                qresult.setText("抱歉，词库里没有这个单词");
                                wordEdit.setText("");
                            }

                        }

                    } while (cursor.moveToNext());
                }
                cursor.close();*/
            }
        });
    }

}

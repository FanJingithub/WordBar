package com.example.fanjin.word__bar;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by FanJin on 2017/3/13.
 */

public class result extends BaseActivity {
    private MyDatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private int ff,tot;
    Cursor cursor;
    ContentValues values;
    TextView qresult;
    Button conData,nconData,back;
    TextView word_name,word_ann, word_exp;
    WebView word_mp3;
    String str,sexp;
    InputStream in = null;
    BufferedReader reader = null;
    String mp3,yinbiao,explain,syinbiao,smp3,sword,line;
    Button vv;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=getIntent();
        sword=intent.getStringExtra("word");
        setContentView(R.layout.show_result);
        qresult=(TextView) findViewById(R.id.query_result);
        conData=(Button) findViewById(R.id.con_data);

        word_name=(TextView) findViewById(R.id.word_name2);
        word_ann=(TextView) findViewById(R.id.word_ann2);
        word_exp=(TextView) findViewById(R.id.word_exp2);

        back=(Button) findViewById(R.id.back_result);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        dbHelper = new MyDatabaseHelper(result.this, "myword.db", null, 1);
        db = dbHelper.getWritableDatabase();

        cursor = db.query("wordlist", null, "word=?", new String[]{sword + ""}, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                ff=0;
                ff = cursor.getInt(cursor.getColumnIndex("flag"));
                Log.e("ee","---------------"+ff);
                if (ff == 1) {
                    qresult.setText("此单词尚未加入复习计划，是否添加？");

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

                            finish();

                        }
                    });
                }
                else if (ff == 2) {

                }
                else if (ff==3){
                    qresult.setText("你的复习计划里已有这个单词，是否删除？");

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

                            finish();
                        }
                    });
                }

                else if (ff==4){
                    qresult.setText("之前已删除该单词,是否重新加入复习计划？");

                    conData.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            values = new ContentValues();
                            values.put("flag", 3 + "");
                            db.update("wordlist", values, "word=?", new String[]{sword + ""});
                            db.delete("delword", "word=?", new String[]{sword + ""});
                            values.clear();
                            values = new ContentValues();
                            values.put("word", sword);
                            values.put("now", 1);
                            db.insert("getword", null, values);
                            values.clear();

                            finish();
                        }
                    });
                }
            } while (cursor.moveToNext());
        }
        cursor.close();

        in = null;
        reader = null;
        try {

            in=result.this.getClass().getClassLoader().getResourceAsStream("assets/_"+sword+".txt");
            reader = new BufferedReader(new InputStreamReader(in));
            mp3 = reader.readLine();

            in=result.this.getClass().getClassLoader().getResourceAsStream("assets/&"+sword+".txt");
            reader = new BufferedReader(new InputStreamReader(in));
            yinbiao = reader.readLine();

            in=result.this.getClass().getClassLoader().getResourceAsStream("assets/+"+sword+".txt");
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

        word_name.setText(sword);
        word_ann.setText("音标："+yinbiao);
        word_exp.setText(explain);
        //word_mp3.getSettings().setJavaScriptEnabled(true);
        //word_mp3.setWebViewClient(new WebViewClient());
        //word_mp3.loadUrl(mp3);
        vv=(Button)findViewById(R.id.vv2);
        vv.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                boolean createState=false;
                if(mediaPlayer==null){
                    mediaPlayer=createNetMp3();
                    createState=true;
                }
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

    /**
     * 创建网络mp3
     * @return
     */
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

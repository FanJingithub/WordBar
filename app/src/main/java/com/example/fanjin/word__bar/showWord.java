package com.example.fanjin.word__bar;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by FanJin on 2017/2/16.
 */

public class showWord extends BaseActivity {
    TextView word_name,word_ann, word_exp;
    WebView word_mp3;
    String str,sexp;
    private MyDatabaseHelper dbHelper;
    private SQLiteDatabase db;
    InputStream in = null;
    BufferedReader reader = null;
    String mp3,yinbiao,explain,syinbiao,smp3,sword,line;
    Button vv;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_word);
        Intent intent=getIntent();
        str=intent.getStringExtra("word");


        word_name=(TextView) findViewById(R.id.word_name);
        word_ann=(TextView) findViewById(R.id.word_ann);
        word_exp=(TextView) findViewById(R.id.word_exp);
        word_mp3=(WebView) findViewById(R.id.word_mp3);
/*
        dbHelper = new MyDatabaseHelper(this, "myword.db", null, 1);
        db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("detail", null, "word=?", new String[]{str + ""}, null, null, null);
        Log.e("show", "____________kkk__________________");
        Log.e("show", "____________kkk_________________#"+str+"#");
        if (cursor.moveToFirst()) {
            do {
                sexp= cursor.getString(cursor.getColumnIndex("explain"));
                syinbiao=cursor.getString(cursor.getColumnIndex("yinbiao"));
                smp3=cursor.getString(cursor.getColumnIndex("mp3"));
                Log.e("show", "_____________________"+smp3);
            }while (cursor.moveToNext());
        }
        cursor.close();
*/

        in = null;
        reader = null;
        try {

            in=showWord.this.getClass().getClassLoader().getResourceAsStream("assets/_"+str+".txt");
            reader = new BufferedReader(new InputStreamReader(in));
            mp3 = reader.readLine();

            in=showWord.this.getClass().getClassLoader().getResourceAsStream("assets/&"+str+".txt");
            reader = new BufferedReader(new InputStreamReader(in));
            yinbiao = reader.readLine();

            in=showWord.this.getClass().getClassLoader().getResourceAsStream("assets/+"+str+".txt");
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

        word_name.setText(str);
        word_ann.setText("音标："+yinbiao);
        word_exp.setText(explain);
        //word_mp3.getSettings().setJavaScriptEnabled(true);
        //word_mp3.setWebViewClient(new WebViewClient());
        //word_mp3.loadUrl(mp3);
        vv=(Button)findViewById(R.id.vv);
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
    /**
     * 创建本地MP3
     * @return
     */

    //public MediaPlayer createLocalMp3(){
        /**
         * 创建音频文件的方法：
         * 1、播放资源目录的文件：MediaPlayer.create(MainActivity.this,R.raw.beatit);//播放res/raw 资源目录下的MP3文件
         * 2:播放sdcard卡的文件：mediaPlayer=new MediaPlayer();
         *   mediaPlayer.setDataSource("/sdcard/beatit.mp3");//前提是sdcard卡要先导入音频文件
         */
     //   MediaPlayer mp=MediaPlayer.create(this,R.raw.beatit);
     //   mp.stop();
     //   return mp;
    //}

}

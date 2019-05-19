package com.example.fanjin.word__bar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by FanJin on 2016/9/14.
 */
public class wordMenu extends BaseActivity {
    //private String[] nm;
    private int i,flag;
    private String str;
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_menu);
        Intent intent=getIntent();
        flag=intent.getIntExtra("flag",0);
        if (flag==0){
            str="六级词汇      ";
        } else if (flag==1){
            str="托福词汇      ";
        } else if (flag==2){
            str="托福高级词汇   ";
        }

        TextView head_menu=(TextView)  findViewById(R.id.head_menu);
        head_menu.setText(str);

        back=(Button) findViewById(R.id.back_wordmenu);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ArrayList<String> nm = new ArrayList<String> ();
        //nm.add("       Part   0  总列表");
        for (i=0;i<26;i++){
            nm.add("       Part   "+(char)(i+65));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(wordMenu.this, android.R.layout.simple_list_item_1, nm);
        ListView listView = (ListView) findViewById(R.id.list_menu);
        listView.setAdapter(adapter);
        //listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Intent intent=new Intent(wordMenu.this,wordInList.class);
                intent.putExtra("click",arg2);
                intent.putExtra("flag",flag);
                startActivity(intent);
            }
        });

    }

}

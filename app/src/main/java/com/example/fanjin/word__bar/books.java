package com.example.fanjin.word__bar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by FanJin on 2016/9/12.
 */
public class books extends BaseActivity {
    Intent intent;
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_books);
        intent=new Intent(books.this,wordMenu.class);

        back=(Button) findViewById(R.id.back_showbook);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Button tocet = (Button) findViewById(R.id.to_cet);
        tocet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("flag",0);
                startActivity(intent);
            }
        });

        Button tofet = (Button) findViewById(R.id.to_fet);
        tofet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("flag",1);
                startActivity(intent);
            }
        });

        Button totoefl = (Button) findViewById(R.id.to_toefl);
        totoefl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("flag",2);
                startActivity(intent);
            }
        });

    }

}

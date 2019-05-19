package com.example.fanjin.word__bar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by FanJin on 2016/3/23.
 */
public class showabout extends BaseActivity {
        Button back;

@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_about);

        back=(Button) findViewById(R.id.back_about);
        back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        finish();
                }
        });
        }

}
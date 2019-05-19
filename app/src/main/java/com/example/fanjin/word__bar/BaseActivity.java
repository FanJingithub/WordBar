package com.example.fanjin.word__bar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by FanJin on 2016/3/16.
 */
public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}

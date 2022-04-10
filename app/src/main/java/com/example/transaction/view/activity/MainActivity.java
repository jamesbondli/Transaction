package com.example.transaction.view.activity;

import android.content.Intent;
import android.os.Handler;

import com.example.transaction.R;

public class MainActivity extends BaseActivity {

    @Override
    protected void init() {
        setContentView(R.layout.activity_main);
        toTableActivity();
    }

    Handler handler = new Handler();

    private void toTableActivity() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, TableActivity.class);
                startActivity(intent);
            }
        }, 2000);
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}

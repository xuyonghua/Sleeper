package com.xyh.sleeper;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;


public class SplashActivity extends AppCompatActivity {

    private TextView mCountDownTextView;
    private MyCountDownTimer mCountDownTimer;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mCountDownTextView = (TextView) findViewById(R.id.start_skip_count_down);
        mCountDownTextView.setText(R.string.click_to_skip);
        //创建倒计时类
        mCountDownTimer = new MyCountDownTimer(3000, 1000);
        mCountDownTimer.start();
        //这是一个 Handler 里面的逻辑是从 Splash 界面跳转到 Main 界面
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startPage();
            }
        }, 3000);

        findViewById(R.id.start_skip).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPage();
            }
        });
    }

    public void startPage() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }


    @Override
    protected void onDestroy() {
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
        super.onDestroy();
    }

    class MyCountDownTimer extends CountDownTimer {
        /**
         * @param millisInFuture    表示以「 毫秒 」为单位倒计时的总数
         *                          例如 millisInFuture = 1000 表示1秒
         * @param countDownInterval 表示 间隔 多少微秒 调用一次 onTick()
         *                          例如: countDownInterval = 1000 ; 表示每 1000 毫秒调用一次 onTick()
         */

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }


        public void onFinish() {
            mCountDownTextView.setText(R.string.end_to_skip);
        }

        public void onTick(long millisUntilFinished) {
            mCountDownTextView.setText(millisUntilFinished / 1000 + getResources().getString(R.string.to_skip));
        }

    }

}

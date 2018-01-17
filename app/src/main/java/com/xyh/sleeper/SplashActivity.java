package com.xyh.sleeper;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.signature.StringSignature;
import com.xyh.sleeper.util.DateUtil;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;


public class SplashActivity extends AppCompatActivity {

    private static final String TAG = "SplashActivity";
    private TextView mCountDownTextView;
    private ImageView adImage;
    private Disposable mDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mCountDownTextView = (TextView) findViewById(R.id.start_skip_count_down);
        adImage = (ImageView) findViewById(R.id.image_ad);
        mCountDownTextView.setText(R.string.click_to_skip);
        findViewById(R.id.start_skip).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPage();
            }
        });
        mDisposable = Observable.interval(1, TimeUnit.SECONDS)
                .take(3)
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(Long aLong) throws Exception {
                        return 3 - (aLong + 1);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        if (aLong == 0) {
                            startPage();
                        }
                        mCountDownTextView.setText(aLong + "s 跳过");
                    }
                });

        Glide.with(this)
                .load("https://api.dujin.org/bing/1366.php")
                .centerCrop()
                .crossFade(800)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .signature(new StringSignature(DateUtil.getCurrentDate()))
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        Log.d(TAG, "onException: "+e.getMessage());
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        Log.d(TAG, "onResourceReady: "+model);
                        return false;
                    }
                })
                .into(adImage);
    }

    public void startPage() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }


    @Override
    protected void onDestroy() {
        if (mDisposable != null) {
            mDisposable.dispose();
        }
        super.onDestroy();
    }

}

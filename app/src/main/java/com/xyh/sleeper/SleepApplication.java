package com.xyh.sleeper;

import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.RefWatcher;

/**
 * Created by xyh on 2017/7/6.
 */

public class SleepApplication extends Application{

    public static RefWatcher getRefWatcher(Context context) {
        SleepApplication application = (SleepApplication) context.getApplicationContext();
        return application.refWatcher;
    }

    private RefWatcher refWatcher;
    @Override
    public void onCreate() {
        super.onCreate();
//        refWatcher = LeakCanary.install(this);
    }
}

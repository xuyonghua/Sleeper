package com.xyh.sleeper;

import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.RefWatcher;
import com.tencent.tinker.loader.app.TinkerApplication;
import com.tencent.tinker.loader.shareutil.ShareConstants;

/**
 * Created by xyh on 2017/7/6.
 */

public class SleepApplication extends TinkerApplication {

    public SleepApplication(){
        super(ShareConstants.TINKER_ENABLE_ALL, "com.xyh.sleeper.SleepApplicationLike",
                "com.tencent.tinker.loader.TinkerLoader", false);
    }

}

package com.xyh.sleeper.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.xyh.sleeper.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 加载的dialog
 */

public class LoadingUtil {
    private static ProgressDialog mWaittingDialog;

    public static void showLoading(Context context) {
        try {
            if (mWaittingDialog != null) {
                mWaittingDialog.dismiss();
                mWaittingDialog = null;
            }

            mWaittingDialog = new ProgressDialog(context, R.style.CustomProgressDialog);
            mWaittingDialog.setCancelable(false);
            LayoutInflater mInflater = mWaittingDialog.getLayoutInflater();
            View mView = mInflater.inflate(R.layout.dialog_loading, null);
            mWaittingDialog.show();
            mWaittingDialog.setContentView(mView);
            // 3秒后还未完成任务，则设置为可取消
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    if (mWaittingDialog != null)
                        mWaittingDialog.setCancelable(true);
                }
            };
            Timer timer = new Timer(true);
            timer.schedule(task, 3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void dismissLoading() {
        try {
            if (mWaittingDialog != null)
                mWaittingDialog.dismiss();
            mWaittingDialog = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

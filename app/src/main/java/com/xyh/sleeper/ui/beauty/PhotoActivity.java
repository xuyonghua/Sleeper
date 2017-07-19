package com.xyh.sleeper.ui.beauty;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.target.Target;
import com.xyh.sleeper.R;
import com.xyh.sleeper.adapter.PhotoAdapter;
import com.xyh.sleeper.util.FileUtil;
import com.xyh.sleeper.util.ToastUtil;

import java.io.File;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PhotoActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    private static final String TAG = "PhotoActivity";
    List<String> imgUrls;
    int pos;
    PhotoAdapter photoAdapter;
    @Bind(R.id.count)
    TextView countText;
    @Bind(R.id.save)
    TextView save;
    @Bind(R.id.photoViewPager)
    ViewPager photoViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initData() {
        Intent intent = getIntent();
        pos = intent.getIntExtra("position", 0);
        imgUrls = intent.getStringArrayListExtra("imageUrls");
    }

    private void initView() {
        photoAdapter = new PhotoAdapter(this, imgUrls);
        photoViewPager.setAdapter(photoAdapter);
        photoViewPager.addOnPageChangeListener(this);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                download(imgUrls.get(pos));
            }
        });
        countText.setText((pos + 1) + "/" + imgUrls.size());
        photoViewPager.setCurrentItem(pos);
    }

    public void download(String url) {
        new AsyncTask<String, Integer, File>() {
            @Override
            protected File doInBackground(String... params) {
                File file = null;
                try {
                    FutureTarget future = Glide
                            .with(PhotoActivity.this)
                            .load(params[0])
                            .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);
                    file = (File) future.get();
                    // 首先保存图片
                    File pictureFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsoluteFile();
                    File appDir = new File(pictureFolder, "Beauty");
                    if (!appDir.exists()) {
                        appDir.mkdirs();
                    }
                    String fileName = System.currentTimeMillis() + ".jpg";
                    File destFile = new File(appDir, fileName);
                    FileUtil.copyFile(file, destFile);
                    // 最后通知图库更新
                    sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                            Uri.fromFile(new File(destFile.getPath()))));
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage());
                }
                return file;
            }

            @Override
            protected void onPostExecute(File file) {
                super.onPostExecute(file);
                ToastUtil.showCenter(PhotoActivity.this, "图片成功保存SD卡");
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);
            }
        }.execute(url);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        countText.setText((position + 1) + "/" + imgUrls.size());
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (photoViewPager != null) {
            photoViewPager.removeOnPageChangeListener(this);
        }
    }
}

package com.xyh.sleeper;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.xyh.sleeper.adapter.MainAdapter;
import com.xyh.sleeper.http.RetrofitHelper;
import com.xyh.sleeper.ui.beauty.BeautyFragment;
import com.xyh.sleeper.ui.joke.JokeFragment;
import com.xyh.sleeper.ui.video.VideoFragment;
import com.xyh.sleeper.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

public class MainActivity extends AppCompatActivity {
    @Bind(R.id.content)
    ViewPager viewPager;
    @Bind(R.id.navigation)
    BottomNavigationView navigation;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.title)
    TextView title;

    private String[] mTitles;

    private List<Fragment> fragmentList;

    private MainAdapter adapter;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_joke:
                    title.setText(mTitles[0]);
                    viewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_video:
                    title.setText(mTitles[1]);
                    viewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_beauty:
                    title.setText(mTitles[2]);
                    viewPager.setCurrentItem(2);
                    return true;
            }
            return false;
        }

    };

    private ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            navigation.getMenu().getItem(position).setChecked(true);
            title.setText(mTitles[position]);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
        initViewPager();
    }

    private void initViewPager() {
        fragmentList = new ArrayList<>();
        fragmentList.add(new JokeFragment());
        fragmentList.add(new VideoFragment());
        fragmentList.add(new BeautyFragment());
        adapter = new MainAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(adapter);
    }

    private void initView() {
        mTitles = getResources().getStringArray(R.array.main_titles);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        title.setText(mTitles[0]);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        viewPager.addOnPageChangeListener(mOnPageChangeListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
        RetrofitHelper.getInstance().cancelAll();
    }

    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;

    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }

        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
            super.onBackPressed();
            return;
        } else {
            ToastUtil.showCenter(getBaseContext(), R.string.exitApp);
        }

        mBackPressed = System.currentTimeMillis();
    }

}

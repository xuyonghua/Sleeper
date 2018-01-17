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
import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.SupportFragment;

public class MainActivity extends SupportActivity{
//    @Bind(R.id.content)
//    ViewPager viewPager;
    @Bind(R.id.navigation)
    BottomNavigationView navigation;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.title)
    TextView title;

    private String[] mTitles;

    private List<Fragment> fragmentList;

    private MainAdapter adapter;

    private SupportFragment[] mFragments = new SupportFragment[3];

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_joke:
                    title.setText(mTitles[0]);
                    showHideFragment(mFragments[0]);
//                    viewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_video:
                    title.setText(mTitles[1]);
                    showHideFragment(mFragments[1]);
//                    viewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_beauty:
                    title.setText(mTitles[2]);
                    showHideFragment(mFragments[2]);
//                    viewPager.setCurrentItem(2);
                    return true;
                    default:
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
//        initViewPager();

        if(savedInstanceState == null){
            mFragments[0] = new JokeFragment();
            mFragments[1] = new VideoFragment();
            mFragments[2] = new BeautyFragment();
            loadMultipleRootFragment(R.id.content,0,mFragments[0],mFragments[1],mFragments[2]);
        }else {
            mFragments[0] = findFragment(JokeFragment.class);
            mFragments[1] = findFragment(VideoFragment.class);
            mFragments[2] = findFragment(BeautyFragment.class);
        }
    }

    private void initViewPager() {
        fragmentList = new ArrayList<>();
        fragmentList.add(new JokeFragment());
        fragmentList.add(new VideoFragment());
        fragmentList.add(new BeautyFragment());
        adapter = new MainAdapter(getSupportFragmentManager(), fragmentList);
//        viewPager.setAdapter(adapter);
    }

    private void initView() {
        mTitles = getResources().getStringArray(R.array.main_titles);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        title.setText(mTitles[0]);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
//        viewPager.addOnPageChangeListener(mOnPageChangeListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
        RetrofitHelper.getInstance().cancelAll();
    }

    private static final int TIME_INTERVAL = 2000;
    private long mBackPressed;

    @Override
    public void onBackPressedSupport() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        if(getFragmentManager().getBackStackEntryCount()>1){
            //如果当前存在fragment>1，当前fragment出栈
            pop();
        }else {
            if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
                finish();
            } else {
                ToastUtil.showCenter(getBaseContext(), R.string.exitApp);
            }
            mBackPressed = System.currentTimeMillis();
        }

    }

}

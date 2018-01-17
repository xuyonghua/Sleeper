package com.xyh.sleeper.ui.video;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.xyh.sleeper.BaseContract;
import com.xyh.sleeper.R;
import com.xyh.sleeper.adapter.VideoAdapter;
import com.xyh.sleeper.entity.VideoBean;
import com.xyh.sleeper.http.Apis;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by xyh on 2017/7/6.
 */

public class VideoFragment extends SupportFragment implements BaseContract.View<VideoBean> {
    @Bind(R.id.videoRecyclerView)
    RecyclerView videoRecyclerView;
    @Bind(R.id.videoRefresh)
    SwipeRefreshLayout videoRefresh;

    private List<VideoBean> dataList = new ArrayList<>();
    private VideoAdapter adapter;
    private LinearLayoutManager manager;
    private int pageIndex;
    private View footView;
    private VideoPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video, container, false);
        ButterKnife.bind(this, view);
        presenter = new VideoPresenter(this);
        initView();
        return view;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initData();
    }

    private void initView() {
        videoRefresh.setColorSchemeResources(R.color.colorPrimary, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        videoRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageIndex = 0;
                initData();
            }
        });
        manager = new LinearLayoutManager(getContext());
        videoRecyclerView.setLayoutManager(manager);
        footView = LayoutInflater.from(getContext()).inflate(R.layout.layout_footer, videoRecyclerView, false);
        adapter = new VideoAdapter(getContext(), dataList);
        adapter.setFootView(footView);
        videoRecyclerView.setAdapter(adapter);

        videoRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (dataList.size() > 0 && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int lastVisiblePosition = manager.findLastVisibleItemPosition();
                    if (lastVisiblePosition >= manager.getItemCount() - 1) {
                        onLoadMore();
                    }
                }
            }
        });
    }

    private void onLoadMore() {
        footView.setVisibility(View.VISIBLE);
        pageIndex++;
        initData();
    }

    private void initData() {
        presenter.httpRequest(Apis.getVideoUrl(pageIndex), pageIndex);
    }

    @Override
    public void onSuccess(List<VideoBean> response) {
        if (response == null) {
            return;
        }
        if (pageIndex == 0) {
            videoRefresh.setRefreshing(false);
            dataList.clear();
        } else {
            footView.setVisibility(View.GONE);
        }
        dataList.addAll(response);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onError(String errorMsg) {
        if (pageIndex == 0) {
            videoRefresh.setRefreshing(false);
        } else {
            footView.setVisibility(View.GONE);
        }
        if (TextUtils.isEmpty(errorMsg)) {
            return;
        }
        Toast.makeText(getContext(), errorMsg, Toast.LENGTH_SHORT).show();
    }
}

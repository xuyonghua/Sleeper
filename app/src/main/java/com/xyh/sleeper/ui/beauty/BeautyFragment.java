package com.xyh.sleeper.ui.beauty;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.xyh.sleeper.BaseContract;
import com.xyh.sleeper.R;
import com.xyh.sleeper.adapter.BeautyRecyclerViewAdapter;
import com.xyh.sleeper.entity.BeautyBean;
import com.xyh.sleeper.http.Apis;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by xyh on 2017/7/6.
 */

public class BeautyFragment extends Fragment implements BaseContract.View<BeautyBean>{
    @Bind(R.id.beautyRecyclerView)
    RecyclerView beautyRecyclerView;
    @Bind(R.id.beautyRefresh)
    SwipeRefreshLayout beautyRefresh;
    private List<BeautyBean> dataList;
    private BeautyRecyclerViewAdapter adapter;
    private BaseContract.Presenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_beauty, container, false);
        ButterKnife.bind(this, view);
        presenter = new BeautyPresenter(this);
        refreshData();
        initView();
        return view;
    }

    private void initView() {
        dataList = new ArrayList<>();
        adapter = new BeautyRecyclerViewAdapter(this.getContext(),dataList);
        beautyRecyclerView.setAdapter(adapter);
        final StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        //防止item 交换位置
        manager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);

        beautyRecyclerView.setLayoutManager(manager);

        //防止第一行到顶部有空白区域
        beautyRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                manager.invalidateSpanAssignments();
            }
        });
        beautyRefresh.setColorSchemeResources(R.color.colorPrimary, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        beautyRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });
    }

    private void refreshData(){
       presenter.httpRequest(Apis.getBeautyApi(),0);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public void onSuccess(List<BeautyBean> response) {
        dataList.clear();
        dataList.addAll(response);
        beautyRefresh.setRefreshing(false);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onError(String errorMsg) {
        beautyRefresh.setRefreshing(false);
        Toast.makeText(getContext(),errorMsg,Toast.LENGTH_SHORT).show();
    }

}

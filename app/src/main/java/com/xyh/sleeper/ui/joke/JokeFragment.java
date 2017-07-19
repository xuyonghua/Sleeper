package com.xyh.sleeper.ui.joke;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.xyh.sleeper.BaseContract;
import com.xyh.sleeper.R;
import com.xyh.sleeper.WebViewActivity;
import com.xyh.sleeper.adapter.JokeAdapter;
import com.xyh.sleeper.entity.JokeBean;
import com.xyh.sleeper.http.Apis;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by xyh on 2017/7/6.
 */

public class JokeFragment extends Fragment implements BaseContract.View<JokeBean> {
    private static final String TAG = "JokeFragment";
    @Bind(R.id.jokeRecyclerView)
    RecyclerView jokeRecyclerView;
    @Bind(R.id.jokeRefresh)
    SwipeRefreshLayout jokeRefresh;
    private JokeAdapter jokeAdapter;
    private List<JokeBean> dataList;
    private int pageIndex;
    private View footView;
    private LinearLayoutManager manager;
    private BaseContract.Presenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_joke, container, false);
        ButterKnife.bind(this, view);
        presenter = new JokePresenter(this);
        refreshData();
        initView();
        return view;
    }

    private void refreshData() {
        presenter.httpRequest(Apis.getJokeUrl(pageIndex), pageIndex);
    }

    private void initView() {
        dataList = new ArrayList<>();
        manager = new LinearLayoutManager(getContext());
        jokeRecyclerView.setLayoutManager(manager);
        jokeRecyclerView.setItemAnimator(new DefaultItemAnimator());
        jokeRecyclerView.addItemDecoration(new DefaultItemDecoration(getActivity()));
        footView = LayoutInflater.from(getContext()).inflate(R.layout.layout_footer, jokeRecyclerView, false);
        jokeAdapter = new JokeAdapter(getContext(), dataList);
        jokeAdapter.setFootView(footView);
        jokeRecyclerView.setAdapter(jokeAdapter);
        jokeAdapter.setOnItemClickListener(new JokeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getContext(), WebViewActivity.class);
                intent.putExtra("webUrl", dataList.get(position).getUrl());
                startActivity(intent);
            }
        });
        jokeRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int lastVisiblePosition = manager.findLastVisibleItemPosition();
                    if (dataList.size() > 0 && lastVisiblePosition == manager.getItemCount() - 1) {
                        onLoadMore();
                    }
                }
            }

        });


        jokeRefresh.setColorSchemeResources(R.color.colorPrimary, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        jokeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageIndex = 0;
                refreshData();
            }
        });
    }

    private void onLoadMore() {
        footView.setVisibility(View.VISIBLE);
        pageIndex++;
        Log.d(TAG, "onLoadMore: page:" + pageIndex);
        refreshData();
    }

    @Override
    public void onSuccess(List<JokeBean> response) {
        Log.d(TAG, "onSuccess: " + response.size());
        if (pageIndex == 0) {
            dataList.clear();
            jokeRefresh.setRefreshing(false);
        } else {
            footView.setVisibility(View.GONE);
        }
        dataList.addAll(response);
        jokeAdapter.notifyDataSetChanged();
    }

    @Override
    public void onError(String errorMsg) {
        if (pageIndex == 0) {
            dataList.clear();
            jokeRefresh.setRefreshing(false);
        } else {
            footView.setVisibility(View.GONE);
        }
        if (TextUtils.isEmpty(errorMsg)) {
            return;
        }
        Toast.makeText(getActivity().getBaseContext(), errorMsg, Toast.LENGTH_SHORT).show();
    }
}

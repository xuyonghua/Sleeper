package com.xyh.sleeper.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.xyh.sleeper.R;
import com.xyh.sleeper.entity.BeautyBean;
import com.xyh.sleeper.ui.beauty.PhotoActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xyh on 2017/7/7.
 */

public class BeautyRecyclerViewAdapter extends RecyclerView.Adapter<BeautyRecyclerViewAdapter.BeautyViewHolder> {
    private Context context;
    private List<BeautyBean> dataList;

    public BeautyRecyclerViewAdapter(Context context, List<BeautyBean> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public BeautyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //找到item的布局
        View view = LayoutInflater.from(context).inflate(R.layout.item_beauty, parent, false);
        return new BeautyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BeautyViewHolder holder, final int position) {
        Glide.with(context)
                .load(dataList.get(position).getImageUrl())
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(holder.beautyImage);
        holder.beautyImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> urls = new ArrayList<>();
                for (BeautyBean beauty:dataList) {
                    urls.add(beauty.getImageUrl());
                }
                Intent intent = new Intent(context, PhotoActivity.class);
                intent.putExtra("position",position);
                intent.putStringArrayListExtra("imageUrls",urls);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class BeautyViewHolder extends RecyclerView.ViewHolder {

        public ImageView beautyImage;

        public BeautyViewHolder(View itemView) {
            super(itemView);
            beautyImage = (ImageView) itemView.findViewById(R.id.beautyImage);
        }
    }
}



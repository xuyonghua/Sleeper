package com.xyh.sleeper.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xyh.sleeper.R;
import com.xyh.sleeper.entity.VideoBean;

import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by xyh on 2017/7/13.
 */

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoHolder> {
    private Context context;
    private List<VideoBean> dataList;

    private static final int TYPE_NORMAL = 0;
    private static final int TYPE_FOOTER = 1;
    private View footView;


    public VideoAdapter(Context context, List<VideoBean> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public VideoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_NORMAL) {
            View itemView = LayoutInflater.from(context).inflate(R.layout.item_video, parent, false);
            return new VideoHolder(itemView);
        } else {
            return new VideoHolder(footView);
        }
    }

    @Override
    public void onBindViewHolder(VideoHolder holder, int position) {
        if(getItemViewType(position) == TYPE_FOOTER){
            return;
        }
        VideoBean videoBean = dataList.get(position);
        holder.playCount.setText(videoBean.getPlayCount() + "次播放");
        holder.topicName.setText(videoBean.getTopicName());
        holder.voteCount.setText(String.valueOf(videoBean.getVoteCount()));
        Glide.with(context).load(videoBean.getTopicImg()).into(holder.topicImg);
        holder.player.setUp(videoBean.getMp4Hd_url(), JCVideoPlayer.SCREEN_LAYOUT_NORMAL, videoBean.getTitle());
        Glide.with(context).load(videoBean.getCover()).into(holder.player.thumbImageView);
    }

    @Override
    public int getItemCount() {
        int count = dataList == null ? 0 : dataList.size();
        if(footView != null){
            return count + 1;
        }
        return count;
    }

    public void setFootView(View footView) {
        this.footView = footView;
        notifyItemInserted(getItemCount() - 1);
    }

    @Override
    public int getItemViewType(int position) {
        if (footView != null && position == getItemCount() - 1) {
            return TYPE_FOOTER;
        } else {
            return TYPE_NORMAL;
        }
    }

    class VideoHolder extends RecyclerView.ViewHolder {
        TextView topicName;
        TextView playCount;
        TextView voteCount;
        ImageView topicImg;
        JCVideoPlayerStandard player;

        public VideoHolder(View itemView) {
            super(itemView);
            topicName = (TextView) itemView.findViewById(R.id.topicName);
            playCount = (TextView) itemView.findViewById(R.id.playCount);
            voteCount = (TextView) itemView.findViewById(R.id.voteCount);
            topicImg = (ImageView) itemView.findViewById(R.id.topicImg);
            player = (JCVideoPlayerStandard) itemView.findViewById(R.id.player);
        }
    }

}



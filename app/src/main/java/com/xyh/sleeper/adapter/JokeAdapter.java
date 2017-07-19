package com.xyh.sleeper.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.xyh.sleeper.R;
import com.xyh.sleeper.entity.JokeBean;
import com.xyh.sleeper.util.SettingUtil;

import java.util.List;

/**
 * Created by xyh on 2017/7/12.
 */

public class JokeAdapter extends RecyclerView.Adapter<JokeAdapter.JokeViewHolder> {
    private static final int ITEM_IMAGE = 1;
    private static final int ITEM_IMAGES = 2;
    private static final int ITEM_FOOTER = 3;
    private Context context;
    private List<JokeBean> dataList;
    private OnItemClickListener listener;
    private int itemWidth;
    private View footView;


    public JokeAdapter(Context context, List<JokeBean> dataList) {
        this.context = context;
        this.dataList = dataList;
        itemWidth = (SettingUtil.getScreenWidth(context) - SettingUtil.dip2px(context, 32)) / 3;
    }


    @Override
    public JokeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = null;
        if (viewType == ITEM_IMAGE) {
            itemView = LayoutInflater.from(context).inflate(R.layout.item_joke, parent, false);
        } else if (viewType == ITEM_IMAGES) {
            itemView = LayoutInflater.from(context).inflate(R.layout.item_joke_muti, parent, false);
        }else {
            itemView = footView;
        }
        return new JokeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(JokeViewHolder holder, final int position) {
        int type = getItemViewType(position);
        if(type == ITEM_FOOTER){
            return;
        }
        if (type == ITEM_IMAGE) {
            holder.content.setText(dataList.get(position).getDigest());
            Glide.with(context).load(dataList.get(position).getImg()).fitCenter().into(holder.image);
        } else if (type == ITEM_IMAGES) {
            List<JokeBean.Imgextra> images = dataList.get(position).getImages();
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(itemWidth, itemWidth);
            layoutParams.leftMargin = SettingUtil.dip2px(context, 5);
            holder.images.removeAllViews();
            for (JokeBean.Imgextra image : images) {
                ImageView img = new ImageView(context);
                img.setLayoutParams(layoutParams);
                Glide.with(context).load(image.imgsrc).fitCenter().diskCacheStrategy(DiskCacheStrategy.NONE).into(img);
                holder.images.addView(img);
            }
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(position);
            }
        });
        holder.title.setText(dataList.get(position).getTitle());

    }

    public void setFootView(View footView) {
        this.footView = footView;
        notifyItemInserted(getItemCount() - 1);
    }

    @Override
    public int getItemCount() {
        int count = dataList == null ? 0 : dataList.size();
        if (footView != null) {
            return count + 1;
        }
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        if (footView != null && position == getItemCount() - 1) {
            return ITEM_FOOTER;
        }
        if (dataList.get(position).getImages() == null) {
            return ITEM_IMAGE;
        } else {
            return ITEM_IMAGES;
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    class JokeViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView content;
        ImageView image;
        LinearLayout images;
        View itemView;

        public JokeViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            title = (TextView) itemView.findViewById(R.id.jokeTitle);
            content = (TextView) itemView.findViewById(R.id.jokeContent);
            image = (ImageView) itemView.findViewById(R.id.jokeImage);
            images = (LinearLayout) itemView.findViewById(R.id.jokeImages);
        }
    }
}






package com.xyh.sleeper.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.xyh.sleeper.ui.beauty.PhotoActivity;

import java.util.List;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by xyh on 2017/7/10.
 */

public class PhotoAdapter extends PagerAdapter {
    Context context;
    List<String> imgUrls;


    public PhotoAdapter(Context context, List<String> imgUrls) {
        this.context = context;
        this.imgUrls = imgUrls;
    }

    @Override
    public int getCount() {
        return imgUrls.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        String url = imgUrls.get(position);
        PhotoView photoView = new PhotoView(context);
        Glide.with(context).load(url).into(photoView);
        container.addView(photoView);
        photoView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float v, float v1) {
                ((PhotoActivity)context).finish();
            }

            @Override
            public void onOutsidePhotoTap() {

            }
        });
        return photoView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }
}

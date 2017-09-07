package com.yjh.glide;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class GlideAdapter extends RecyclerView.Adapter<GlideAdapter.GlideViewHolder> {

    private Context mContext;
    private List<String> mUrls;

    GlideAdapter(Context context) {
        this.mContext = context;
        this.mUrls = new ArrayList<>();
        mUrls.add("http://img.daimg.com/uploads/allimg/170904/3-1FZ4213403.jpg");
        mUrls.add("http://img.daimg.com/uploads/allimg/170904/3-1FZ4205P6.jpg");
        mUrls.add("http://img.daimg.com/uploads/allimg/170904/3-1FZ41P415.jpg");
        mUrls.add("http://img.daimg.com/uploads/allimg/170904/3-1FZ41K955.jpg");
        mUrls.add("http://img.daimg.com/uploads/allimg/170904/3-1FZ41K333.jpg");
        mUrls.add("http://img.daimg.com/uploads/allimg/170904/3-1FZ41K034.jpg");
        mUrls.add("http://img.daimg.com/uploads/allimg/170904/3-1FZ41J153.jpg");
        mUrls.add("http://img.daimg.com/uploads/allimg/170904/3-1FZ41I401.jpg");
        mUrls.add("http://img.daimg.com/uploads/allimg/170904/3-1FZ41I204.jpg");
        mUrls.add("http://img.daimg.com/uploads/allimg/170904/3-1FZ41H620.jpg");
        mUrls.add("http://img.daimg.com/uploads/allimg/170904/3-1FZ41H316.jpg");
        mUrls.add("http://img.daimg.com/uploads/allimg/170904/3-1FZ41G105.jpg");
        mUrls.add("http://img.daimg.com/uploads/allimg/170904/3-1FZ41FF8.jpg");
        mUrls.add("http://img.daimg.com/uploads/allimg/170904/3-1FZ4163009.jpg");
        mUrls.add("http://img.daimg.com/uploads/allimg/170904/3-1FZ4162337.jpg");
        mUrls.add("http://img.daimg.com/uploads/allimg/170904/3-1FZ4162046.jpg");
        mUrls.add("http://img.daimg.com/uploads/allimg/170904/3-1FZ4161613.jpg");
        mUrls.add("http://img.daimg.com/uploads/allimg/170904/3-1FZ4161148.jpg");
        mUrls.add("http://img.daimg.com/uploads/allimg/170904/3-1FZ4160935.jpg");
        mUrls.add("http://img.daimg.com/uploads/allimg/170904/3-1FZ4160118.jpg");
    }


    @Override
    public GlideViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new GlideViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_image, parent, false));
    }


    @Override
    public void onBindViewHolder(GlideViewHolder holder, int position) {
        String url = mUrls.get(position);
        new WidgetHolder(mContext, holder.imageView).showInWidget(url);
    }

    @Override
    public int getItemCount() {
        return mUrls.size();
    }

    class GlideViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        GlideViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
        }
    }
}

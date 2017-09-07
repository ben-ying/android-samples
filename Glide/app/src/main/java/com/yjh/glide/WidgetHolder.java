package com.yjh.glide;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.signature.ObjectKey;

import static com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions.withCrossFade;


public class WidgetHolder {
    private final Context mContext;
    private final Target<Bitmap> mTarget;
    private final ImageView imageView;

    public WidgetHolder(Context context, ImageView imageView) {
        this.mContext = context;
        this.imageView = imageView;
        this.mTarget = new CustomTarget(imageView);
    }

    public void showInWidget(String url) {
        GlideApp.with(mContext)
                .asBitmap()
//                .asCustomGif() // load gif image with MyGlideExtension
//                .asGif() // load gif image
                .load(url) // load image with url
                .placeholder(R.mipmap.ic_launcher) // add place holder
                .error(new ColorDrawable(Color.RED)) // load image failed
                .fallback(R.drawable.ic_launcher_background) // url is null
//                .miniThumb() // custom Extension in MyGlideExtension
                .transition(withCrossFade()) // add transition when show image
                .thumbnail(0.1f) // thumbnail
                .diskCacheStrategy(DiskCacheStrategy.ALL) // cache all
//                .skipMemoryCache(true) // skip memory cache
//                .diskCacheStrategy(DiskCacheStrategy.NONE) // skip disk cache
                // replace cache in new glide version
                .signature(new ObjectKey(mContext.getResources().getInteger(R.integer.glide_version)))
                .into(mTarget); // use custom target
    }
}

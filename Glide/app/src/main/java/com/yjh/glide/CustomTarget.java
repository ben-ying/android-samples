package com.yjh.glide;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;


public class CustomTarget implements Target<Bitmap> {
    private static final String TAG = CustomTarget.class.getSimpleName();
    private ImageView mImageView;

    public CustomTarget(ImageView imageView) {
        this.mImageView = imageView;
    }

    @Nullable
    @Override
    public Request getRequest() {
        return (Request) mImageView.getTag();
    }

    @Override
    public void onLoadStarted(@Nullable Drawable placeholder) {
        mImageView.setImageDrawable(placeholder);
    }

    @Override
    public void onLoadFailed(@Nullable Drawable errorDrawable) {
        mImageView.setImageDrawable(errorDrawable);
    }

    @Override
    public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
        mImageView.setImageBitmap(bitmap);
    }

    @Override
    public void onLoadCleared(@Nullable Drawable placeholder) {
        Log.d(TAG, "onLoadCleared");
    }

    @Override
    public void getSize(SizeReadyCallback cb) {
        // set image size not imageView
//        cb.onSizeReady(10, 10);
        cb.onSizeReady(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);
    }

    @Override
    public void removeCallback(SizeReadyCallback cb) {
        Log.d(TAG, "removeCallback");
    }

    @Override
    public void setRequest(@Nullable Request request) {
        mImageView.setTag(request);
    }

    @Override
    public void onStart() {
        Log.d(TAG, "onStart");
    }

    @Override
    public void onStop() {
        Log.d(TAG, "onStop");
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
    }
}

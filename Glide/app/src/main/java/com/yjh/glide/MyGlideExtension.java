package com.yjh.glide;

import android.graphics.Bitmap;

import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.annotation.GlideExtension;
import com.bumptech.glide.annotation.GlideOption;
import com.bumptech.glide.annotation.GlideType;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestOptions;

import static com.yjh.glide.GlideOptions.decodeTypeOf;

@GlideExtension
public class MyGlideExtension {
    // Size of mini thumb in pixels.
    private static final int MINI_THUMB_SIZE = 100;
    private static final RequestOptions DECODE_TYPE_GIF = decodeTypeOf(GifDrawable.class).lock();

    private MyGlideExtension() {
    }

    @GlideOption
    public static void miniThumb(RequestOptions options) {
        options.fitCenter().override(MINI_THUMB_SIZE);
    }

    // support for GIFs
    @GlideType(GifDrawable.class)
    public static void asCustomGif(RequestBuilder<GifDrawable> requestBuilder) {
        requestBuilder
                .transition(new DrawableTransitionOptions())
                .apply(DECODE_TYPE_GIF);
    }
}
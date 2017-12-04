# Glide Tutorial


## Set up your environment
1. Open your app’s build.gradle file. This is usually not the top-level build.gradle file but app/build.gradle.
2. Add the following lines inside dependencies:
```java
dependencies {
  compile 'com.github.bumptech.glide:glide:4.3.1'
}
```
If you are using ProGuard in your project add the following lines to your configuration:
```java
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.AppGlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
    **[] $VALUES;
    public *;
}
```
## Basic Usage
#### Load image with url
```java
Glide.with(fragment) // activity, fragment or view
    .load(myUrl)
    .into(imageView);
```
#### Cancelling loads you no longer need is simple too:
```java
Glide.with(fragment).clear(imageView);
```
## Generated API
1. Add a dependency on Glide’s annotation processor:
```java
dependencies {
  annotationProcessor 'com.github.bumptech.glide:compiler:4.3.1'
}
```
2. Include a AppGlideModule implementation in your application:
```java
package com.example.myapp;
   
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
   
@GlideModule
public final class MyAppGlideModule extends AppGlideModule {}
```
3. Make project in Android Studio
4. Custom Target(Optional)
```java
package com.example.myapp;

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
```
5. MyGlideExtension(Optional)
```java
package com.example.myapp;

import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.annotation.GlideExtension;
import com.bumptech.glide.annotation.GlideOption;
import com.bumptech.glide.annotation.GlideType;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestOptions;

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
```
6. Using generated API:
```java
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
```



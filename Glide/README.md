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
4. Using generated API:
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



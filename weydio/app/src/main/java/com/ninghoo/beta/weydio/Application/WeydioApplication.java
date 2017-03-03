package com.ninghoo.beta.weydio.Application;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;

import com.ninghoo.beta.weydio.R;
import com.ninghoo.beta.weydio.Model.Audio;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.util.ArrayList;

/**
 * Created by ningfu on 17-2-10.
 */

public class WeydioApplication extends Application
{
    // 由于是private属性，所以只要是自己的子类就可以直接访问和保存变量了。
    private  static Context mContext;

    private static ArrayList<Audio> mla;

    public static DisplayImageOptions mOptions;

    public static boolean isPlay;

    private static int mMaxPosition;

    @Override
    public void onCreate()
    {
        super.onCreate();

        // 在onCreate中获取全局的context对象，并在getContext方法中返回。
        mContext = getApplicationContext();

        // 初始化ImageLoader：
        initImageLoader(this);
    }

    // static的变量，通过static的方法来获取。
    public static Context getContext()
    {
        return mContext;
    }

    // 全局变量la的get-set方法。
    public static ArrayList<Audio> getMla()
    {
        return mla;
    }

    public static boolean getIsPlay() {
        return isPlay;
    }

    public static int getmMaxPosition() {
        return mMaxPosition;
    }

    public static void setMla(ArrayList<Audio> mla) {
        WeydioApplication.mla = mla;
    }

    public static void setIsPlay(boolean isPlay) {
        WeydioApplication.isPlay = isPlay;
    }

    public static void setmMaxPosition(int mMaxPosition) {
        WeydioApplication.mMaxPosition = mMaxPosition;
    }
    // 在Application中去初始化对ImageLoader的配置。
    private void initImageLoader(Context context)
    {

        // 第一步是初始化ImageLoaderConfiguration。
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCacheSize(10 * 1024 * 1024) // 10 Mb
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                //.writeDebugLogs() // Remove for release app
                .build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);


        mOptions = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.oafront)
                .showImageForEmptyUri(R.drawable.oafront)
                .showImageOnFail(R.drawable.oafront)
                .cacheOnDisk(true)
                .cacheInMemory(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
    }
}

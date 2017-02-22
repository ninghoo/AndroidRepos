package com.example.ningfu.musicsharedemo.Application;

import android.app.Application;
import android.content.Context;

/**
 * Created by ningfu on 17-2-20.
 */

public class DrawApplication extends Application
{
    // 由于是private属性，所以只要是自己的子类就可以直接访问和保存变量了。
    private  static Context mContext;

    @Override
    public void onCreate()
    {
        super.onCreate();

        // 在onCreate中获取全局的context对象，并在getContext方法中返回。
        mContext = getApplicationContext();
    }

    public static Context getContext()
    {
        return mContext;
    }

}

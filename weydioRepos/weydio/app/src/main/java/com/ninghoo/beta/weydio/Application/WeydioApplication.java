package com.ninghoo.beta.weydio.Application;

import android.app.Application;
import android.content.Context;
import android.widget.ArrayAdapter;

import com.ninghoo.beta.weydio.model.Audio;

import java.util.ArrayList;

/**
 * Created by ningfu on 17-2-10.
 */

public class WeydioApplication extends Application
{
    // 由于是private属性，所以只要是自己的子类就可以直接访问和保存变量了。
    private  static Context mContext;

    private static ArrayList<Audio> mla;

    @Override
    public void onCreate()
    {
        super.onCreate();

        // 在onCreate中获取全局的context对象，并在getContext方法中返回。
        mContext = getApplicationContext();
    }

    // static的变量，通过static的方法来获取。
    public static Context getContext()
    {
        return mContext;
    }

    public static ArrayList<Audio> getMla()
    {
        return mla;
    }

    public static void setMla(ArrayList<Audio> mla) {
        WeydioApplication.mla = mla;
    }
}

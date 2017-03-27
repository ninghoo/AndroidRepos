package com.ninghoo.beta.weydio2.Activity;

import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.ninghoo.beta.weydio2.R;

import java.lang.reflect.Field;

/**
 * Created by ningfu on 17-3-23.
 */

public class CommonActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        hideActionBar();
    }

    private void hideActionBar()
    {
        ActionBar actionBar = getSupportActionBar();

        if(actionBar != null)
        {
            actionBar.hide();
        }
    }

    /**
     * 通过反射的方式获取状态栏高度
     *
     * @return
     */
    public int getStatusBarHeight() {
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            return getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 通过反射的方式获取状态栏宽度
     *
     * @return
     */
    public int getStatusBarWidth()
    {
        Resources resources = this.getResources();

        DisplayMetrics dm = resources.getDisplayMetrics();

//        float density1 = dm.density;

//        int mWidth = dm.widthPixels - dip2px(density1, 10);

        return dm.widthPixels;
    }

    // dp转换成px；
    public static int dip2px(float scale, int dpValue)
    {
        return (int) (dpValue * scale + 0.5f);
    }
}

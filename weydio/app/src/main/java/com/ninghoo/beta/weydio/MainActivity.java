package com.ninghoo.beta.weydio;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.ninghoo.beta.weydio.Adapter.MusicListAdapter;
import com.ninghoo.beta.weydio.Application.WeydioApplication;
import com.ninghoo.beta.weydio.Service.MusicPlayService;
import com.ninghoo.beta.weydio.model.AppConstant;
import com.ninghoo.beta.weydio.model.Audio;
import com.ninghoo.beta.weydio.model.MediaDetails;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    private DrawerLayout mDrawLayout;

    private RecyclerView mRecyMusiclist;

    private MusicListAdapter adapter;

    private ArrayList<Audio> la;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InitDrawerLayout();

        hideActionBar();

        initState();

        initRecyMusicList();

    }

    private void InitDrawerLayout()
    {
        mDrawLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mDrawLayout.setScrimColor(Color.TRANSPARENT);

        mDrawLayout.setDrawerListener(new DrawerLayout.DrawerListener()
        {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset)
            {

            }

            @Override
            public void onDrawerOpened(View drawerView)
            {
//                Intent intent = new Intent();
//
//                intent.putExtra("MSG", AppConstant.PlayerMsg.STOP_MSG);
//                intent.setClass(mContext, MusicPlayService.class);
//
//                mContext.startService(intent);

                MusicPlayService.mediaPlayer.pause();
            }

            @Override
            public void onDrawerClosed(View drawerView)
            {
                MusicPlayService.mediaPlayer.start();
            }

            @Override
            public void onDrawerStateChanged(int newState)
            {

            }
        });
    }


    private void initRecyMusicList()
    {
        mRecyMusiclist = (RecyclerView) findViewById(R.id.recycl_musiclist);
        mRecyMusiclist.setLayoutManager(new LinearLayoutManager(this));

        // 由于getAudioList是static方法，所以可以直接通过类名调用。
        la =  MediaDetails.getAudioList(WeydioApplication.getContext());

        WeydioApplication.setMla(la);

        adapter = new MusicListAdapter(this, WeydioApplication.getMla());

        mRecyMusiclist.setAdapter(adapter);
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
     * 沉浸式状态栏
     */
    private void initState() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

            // 动态设置状态栏。
            LinearLayout linear_bar = (LinearLayout) findViewById(R.id.visable_bar);
            linear_bar.setVisibility(View.VISIBLE);
            //获取到状态栏的高度
            int statusHeight = getStatusBarHeight();
            //动态的设置隐藏布局的高度
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) linear_bar.getLayoutParams();
            params.height = statusHeight;
            linear_bar.setLayoutParams(params);
        }
    }

    /**
     * 通过反射的方式获取状态栏高度
     *
     * @return
     */
    private int getStatusBarHeight() {
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
}
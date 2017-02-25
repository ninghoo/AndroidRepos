package com.ninghoo.beta.weydio.Activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.ninghoo.beta.weydio.Application.WeydioApplication;
import com.ninghoo.beta.weydio.R;
import com.ninghoo.beta.weydio.Service.MusicPlayService;

import java.lang.reflect.Field;

/**
 * Created by ningfu on 17-2-24.
 */

public class WeydioActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        hideActionBar();

    }


    protected void hideActionBar()
    {
        ActionBar actionBar = getSupportActionBar();

        if(actionBar != null)
        {
            actionBar.hide();
        }
    }

    private void InitDrawerLayout(DrawerLayout mDrawLayout)
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
                if(MusicPlayService.isPause)
                {
                    MusicPlayService.mediaPlayer.start();
                    MusicPlayService.isPause = false;
                }
                else
                {
                    MusicPlayService.mediaPlayer.pause();
                    MusicPlayService.isPause = true;
                }
            }

            @Override
            public void onDrawerClosed(View drawerView)
            {

            }

            @Override
            public void onDrawerStateChanged(int newState)
            {

            }
        });
    }


    private void initNotification()
    {
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        Notification notification = new NotificationCompat.Builder(WeydioApplication.getContext())
                .setContentTitle("weydioTitle")
                .setContentText("weydioText")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.oafront))
                .build();

        manager.notify(1, notification);
    }
}

package com.ninghoo.beta.weydio.Activity;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ninghoo.beta.weydio.Adapter.MusicListAdapter;
import com.ninghoo.beta.weydio.Application.WeydioApplication;
import com.ninghoo.beta.weydio.FastScrollView.FastScrollRecyclerView;
import com.ninghoo.beta.weydio.R;
import com.ninghoo.beta.weydio.Service.MusicPlayService;
import com.ninghoo.beta.weydio.Model.AppConstant;
import com.ninghoo.beta.weydio.Model.Audio;
import com.ninghoo.beta.weydio.Model.MediaDetails;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class MusicRecyclerActivity extends CommonActivity {
    private DrawerLayout mDrawLayout;

    private FastScrollRecyclerView mRecyMusiclist;

    private MusicListAdapter adapter;

    private Context mContext;

    private TextView mShadow;

//    private Button mBtn2Now;

    public static boolean isShow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listrecycler);

        WeydioApplication.setIsPlay(false);

        InitDrawerLayout();

        initState();

        initNotify();

        initRecyMusicList();
    }

    private void InitDrawerLayout() {
        mDrawLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mDrawLayout.setScrimColor(Color.argb(106, 0, 0, 0));

        mDrawLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                if (MusicPlayService.isPause) {
                    MusicPlayService.mediaPlayer.start();
                    MusicPlayService.isPause = false;
                } else {
                    MusicPlayService.mediaPlayer.pause();
                    MusicPlayService.isPause = true;
                }

//                mDrawLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
//                activityNextSong();
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

//        findViewById(R.id.btn_Drawer2Act).setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
////                mDrawLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
////                mDrawLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
//
//            }
//        });
    }

    private void activityNextSong() {
        Intent intent = new Intent();

        mContext = WeydioApplication.getContext();

        intent.putExtra("MSG", AppConstant.PlayerMsg.NEXT_MSG);
        intent.setClass(mContext, MusicPlayService.class);
        // 在这里设置Intent去跳转制定的Sevice。

        mContext.startService(intent);
    }


    private void initRecyMusicList() {
        mRecyMusiclist = (FastScrollRecyclerView) findViewById(R.id.recycl_musiclist);

//        LinearLayoutManager layout = new LinearLayoutManager(this);
//        layout.setReverseLayout(true);    //显示列表和播放列表均翻转。

        mRecyMusiclist.setLayoutManager(new LinearLayoutManager(this));

        mShadow = (TextView) findViewById(R.id.tv_shadow);
        isShow = false;

        adapter = new MusicListAdapter(this, WeydioApplication.getMla());

        adapter.setOnItemLongClickListener(new MusicListAdapter.OnItemLongClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onItemLongClick(View view, int position) {
//                if(isShow)
//                {
//                    mShadow.setVisibility(View.INVISIBLE);
//                    isShow = false;
//                }
//                else
//                {
//                    mShadow.setVisibility(View.VISIBLE);
//                    isShow = true;
//                }

//                Toast.makeText(NowPlayActivity.this,"long click "+la.get(position),Toast.LENGTH_SHORT).show();

                turnToNow();
            }
        });

        mRecyMusiclist.setAdapter(adapter);
    }

    private void initNotify() {
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(WeydioApplication.getContext());

        Notification notification = builder.setContentTitle("adasd")
                .setContentText("text")
                .setWhen(System.currentTimeMillis())
//                .setSmallIcon(R.mipmap.leffilo)
//                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.leffilo))
                .build();

        manager.notify(0, notification);
    }

    /**
     * 沉浸式状态栏
     */
    private void initState() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            //透明导航栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

            // 状态栏变成白色。
            // 动态设置状态栏。
            LinearLayout linear_bar = (LinearLayout) findViewById(R.id.visable_bar);
            linear_bar.setVisibility(View.VISIBLE);
            //获取到状态栏的高度
            int statusHeight = getStatusBarHeight();
            int statusWidth = getStatusBarWidth();
            //动态的设置隐藏布局的高度
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) linear_bar.getLayoutParams();
            params.height = statusHeight;
            params.width = statusWidth;
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

    /**
     * 通过反射的方式获取状态栏宽度
     *
     * @return
     */
    public int getStatusBarWidth()
    {
        Resources resources = this.getResources();

        DisplayMetrics dm = resources.getDisplayMetrics();

        float density1 = dm.density;

        int mWidth = dm.widthPixels - dip2px(density1, 10);

        return mWidth;
    }

    // dp转换成px；
    public static int dip2px(float scale, int dpValue)
    {
        return (int) (dpValue * scale + 0.5f);
    }

    private void turnToNow()
    {
        Intent intent = new Intent();

        intent.setClass(WeydioApplication.getContext(), NowPlayActivity.class);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(MusicRecyclerActivity.this).toBundle());
            startActivity(intent);
        }
        else
        {
            startActivity(intent);
        }
    }
}

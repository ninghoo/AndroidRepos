package com.ninghoo.beta.weydio.Activity;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NotificationCompat;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.ninghoo.beta.weydio.Adapter.MusicListAdapter;
import com.ninghoo.beta.weydio.Application.WeydioApplication;
import com.ninghoo.beta.weydio.FastScrollView.FastScrollRecyclerView;
import com.ninghoo.beta.weydio.Model.Audio;
import com.ninghoo.beta.weydio.Model.MediaDetails;
import com.ninghoo.beta.weydio.R;
import com.ninghoo.beta.weydio.Service.MusicPlayService;
import com.ninghoo.beta.weydio.Model.AppConstant;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class MusicRecyclerActivity extends CommonActivity implements View.OnClickListener
{
    private ArrayList<Audio> la;

    private DrawerLayout mDrawLayout;

    private ImageView mBtnA_Z;
    private ImageView mBtnTimeOrder;
    private ImageView mBtnArtistOrder;
    // 默认1，显示全文本而非首字母。
    public static int intOrder = 1;

    private FastScrollRecyclerView mRecyMusiclist;

    private MusicListAdapter adapter;

    private Context mContext;

    public static boolean isShow;

    private FloatingActionButton mFloatBtn;

    // 虽然和nowplayActivity里面的receiver同名，但实际上是两个独立的对象，只是同样监听同一个action。
    private WeydioReceiver mWeydioReceiver;

    private IntentFilter intentFilter;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        initLa();

        setContentView(R.layout.activity_listrecycler);

        WeydioApplication.setIsPlay(false);

        InitDrawerLayout();

        initState();

        initNotify();

        initRecyMusicList();

        initFloatingBtn();

        initBroadCast();
    }

    @Override
    protected void onRestart()
    {
        super.onRestart();

        initFloatingBtn();
    }

    private void initFloatingBtn()
    {
        mFloatBtn = (FloatingActionButton) findViewById(R.id.fb_playPause);

        mFloatBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(MusicPlayService.mediaPlayer.isPlaying())
                {
                    MusicPlayService.mediaPlayer.pause();
                    mFloatBtn.setImageResource(R.drawable.ic_play_arrow_white_48dp);
                }
                else
                {
                    if(MusicPlayService.firstPlay == 0)
                    {
                        Intent intent = new Intent();
                        intent.putExtra("MSG", AppConstant.PlayerMsg.PLAY_MSG);
                        intent.setClass(WeydioApplication.getContext(), MusicPlayService.class);

                        startService(intent);
                    }
                    else
                    {
                        MusicPlayService.mediaPlayer.start();
                        mFloatBtn.setImageResource(R.drawable.ic_pause_white_48dp);
                    }
                }
            }
        });

        if(MusicPlayService.mediaPlayer.isPlaying())
        {
            mFloatBtn.setImageResource(R.drawable.ic_pause_white_48dp);
        }
        else
        {
            mFloatBtn.setImageResource(R.drawable.ic_play_arrow_white_48dp);
        }

        mFloatBtn.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View v)
            {
                return false;
            }
        });
    }

    private void InitDrawerLayout()
    {
        mDrawLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mDrawLayout.setScrimColor(Color.argb(100, 0, 0, 0));

//        mDrawLayout.setDrawerListener(new DrawerLayout.DrawerListener() {});

        mBtnA_Z = (ImageView) findViewById(R.id.btn_listA_Z);
        mBtnTimeOrder = (ImageView) findViewById(R.id.btn_listAddTime);
        mBtnArtistOrder = (ImageView) findViewById(R.id.btn_listArtist);

        mBtnA_Z.setOnClickListener(this);
        mBtnTimeOrder.setOnClickListener(this);
        mBtnArtistOrder.setOnClickListener(this);
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

        isShow = false;

        adapter = new MusicListAdapter(this, la);

        // 在此处重写Adapter的接口方法。
        adapter.setOnItemLongClickListener(new MusicListAdapter.OnItemLongClickListener()
        {
            @Override
            public void onItemLongClick(View view, int position) {

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
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(MusicRecyclerActivity.this, mFloatBtn, "shareNames").toBundle());
//            startActivity(intent);
        }
        else
        {
            startActivity(intent);
        }
    }

    private void initBroadCast()
    {
        intentFilter = new IntentFilter();
        intentFilter.addAction("serviceChangAlbumArt");

        mWeydioReceiver = new WeydioReceiver();
        registerReceiver(mWeydioReceiver, intentFilter);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_listA_Z:
                intOrder = 0;

                // 由于getAudioList是static方法，所以可以直接通过类名调用。
                la =  MediaDetails.getAudioList(WeydioApplication.getContext(), 0);

                WeydioApplication.setMla(la);

                changeInfo();
//                Toast.makeText(WeydioApplication.getContext(), "Orz", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_listAddTime:
                intOrder = 1;

                // 由于getAudioList是static方法，所以可以直接通过类名调用。
                la =  MediaDetails.getAudioList(WeydioApplication.getContext(), 1);

                changeInfo();
//                Toast.makeText(WeydioApplication.getContext(), "E=mc^2", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_listArtist:
                intOrder = 2;

                // 由于getAudioList是static方法，所以可以直接通过类名调用。
                la =  MediaDetails.getAudioList(WeydioApplication.getContext(), 2);

                changeInfo();
//                Toast.makeText(WeydioApplication.getContext(), "一o一", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    private void changeInfo()
    {
        WeydioApplication.setMla(la);

        adapter.notifyDataSetChanged();
        initRecyMusicList();
        MusicPlayService.mediaPlayer.stop();
        MusicPlayService.currentIndex = 0;
        MusicPlayService.firstPlay = 0;
        mFloatBtn.setImageResource(R.drawable.ic_play_arrow_white_48dp);
        mDrawLayout.closeDrawers();
    }


    private void initLa()
    {
        // 由于getAudioList是static方法，所以可以直接通过类名调用。
        la =  MediaDetails.getAudioList(WeydioApplication.getContext(), 1);

        WeydioApplication.setMla(la);
    }

    private class WeydioReceiver extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            initFloatingBtn();
        }
    }
}

package com.weydio.weydio16de31;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.p2p.nsd.WifiP2pServiceInfo;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.weydio.weydio16de31.Adapter.MusicListAdapter;
import com.weydio.weydio16de31.Model.AppConstant;
import com.weydio.weydio16de31.Model.Audio;
import com.weydio.weydio16de31.Model.MediaDetails;
import com.weydio.weydio16de31.Service.MusicPlayService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    final String TAG = "TAG";

    RecyclerView musicRecyclerView;

    private Button mNextBtn;

    private MusicListAdapter adapter;

    private ArrayList<Audio> la;

    static int currentPosition;

    private Intent intent;

    private Audio audio;

    private PowerManager.WakeLock mWakeLock = null;

    private WifiP2pServiceInfo mWifiP2pServiceInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        acquireWakelock();

        initiateMusicRecyclerView();

        initiateNextBtn();

    }

    private void initiateNextBtn()
    {
        mNextBtn = (Button)findViewById(R.id.btn_next);
        mNextBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                nextSong();
            }
        });
    }

    private void initiateMusicRecyclerView()
    {
        musicRecyclerView = (RecyclerView) findViewById(R.id.music_recyclerView);
        musicRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 由于getAudioList是static方法，所以可以直接通过类名调用。
        la =  MediaDetails.getAudioList(MainActivity.this);

        adapter = new MusicListAdapter(this, la);
        musicRecyclerView.setAdapter(adapter);
    }

    // wakeLock几乎没有效果，除非列入系统软件白名单。
    private void acquireWakelock()
    {
        Log.i(TAG, "正在申请电源锁");

        // 如果为空，初始化。
        if(null == mWakeLock)
        {
            PowerManager pm = (PowerManager) this.getSystemService(Context.POWER_SERVICE);

            // ?
            mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK | PowerManager.ON_AFTER_RELEASE, "");
        }
        if(null != mWakeLock)
        {
            mWakeLock.acquire();
        }

        Log.i(TAG, "电源锁申请完毕。");

    }

    private void releaseWakeLock()
    {
        Log.i(TAG, "正在释放电源锁");

        if (null != mWakeLock) {
            mWakeLock.release();
            mWakeLock = null;

            Log.i(TAG, "电源锁释放成功");
        }
    }

    public void nextSong()
    {
        Intent intent = new Intent();

        // 不知为啥，下面两句要这样写才能下一首。
        currentPosition = adapter.position++;
        audio = la.get(currentPosition+1);

        intent.putExtra("MSG", AppConstant.PlayerMsg.PLAY_MSG);
        intent.putExtra("url", audio.getmPath());
        intent.setClass(MainActivity.this, MusicPlayService.class);
        // 在这里设置Intent去跳转制定的Sevice。

        startService(intent);
    }

}
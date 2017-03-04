package com.ninghoo.beta.weydio.Service;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import com.ninghoo.beta.weydio.Activity.NowPlayActivity;
import com.ninghoo.beta.weydio.Application.WeydioApplication;
import com.ninghoo.beta.weydio.Model.AppConstant;
import com.ninghoo.beta.weydio.Model.Audio;

import java.util.ArrayList;

/**
 * Created by ningfu on 17-2-8.
 */

public class MusicPlayService extends Service
{
    public  static MediaPlayer mediaPlayer = new MediaPlayer();

    private String path;

    public static boolean isPause = true;

    // audio是单个的item数据。
    private Audio audio;

    int msg;

    public static int currentIndex = 0;

    private int mMaxPosition;

    private ArrayList<Audio> la;

    public static int firstPlay = 0;


    // onBind方法，用于与Activity沟通。
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
    }

    // 这是属于Service类里的内部方法。
    @Override
    public int onStartCommand(final Intent intent, int flags, int startId)
    {
        la = WeydioApplication.getMla();
        mMaxPosition = WeydioApplication.getmMaxPosition();

        msg = intent.getIntExtra("MSG", 0);

        if(msg == AppConstant.PlayerMsg.ITEMCLICK_MSG)
        {
            currentIndex = intent.getIntExtra("position", 0);
            path = la.get(currentIndex).getmPath();
            play(0);
        }
        else if(msg == AppConstant.PlayerMsg.PLAY_MSG)
        {
            path = la.get(currentIndex).getmPath();
            play(0);
        }
        else if(msg == AppConstant.PlayerMsg.PAUSE_MSG)
        {

        }
        else if(msg == AppConstant.PlayerMsg.STOP_MSG)
        {

        }
        else if(msg == AppConstant.PlayerMsg.NEXT_MSG)
        {
            nextSong();
        }
        else if(msg == AppConstant.PlayerMsg.PRIVIOUS_MSG)
        {
            previousSong();
        }
        else if(msg == AppConstant.PlayerMsg.START_MSG)
        {
            play(intent.getIntExtra("randomPlay", 0));
        }

        // 自动下一首。
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
        {

            @Override
            public void onCompletion(MediaPlayer mp)
            {
                nextSong();
            }
        });


        return super.onStartCommand(intent, flags, startId);
    }


    private void play(int position)
    {
        try
        {
            mediaPlayer.reset();

            mediaPlayer.setDataSource(path);
            mediaPlayer.prepare();
            mediaPlayer.setOnPreparedListener(new PreparedListener(position));

            WeydioApplication.setIsPlay(true);
            sendBroadCastToNowPlay();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    private void nextSong()
    {
        currentIndex++;

        if(currentIndex == mMaxPosition)
        {
            currentIndex = 0;
        }

        audio = la.get(currentIndex);

        path = audio.getmPath();

        play(0);
    }

    private void previousSong()
    {
        currentIndex--;

        if(currentIndex < 0)
        {
            currentIndex = mMaxPosition -1;
        }

        audio = la.get(currentIndex);

        path = audio.getmPath();

        play(0);
    }


    @Override
    public void onDestroy()
    {
        super.onDestroy();

        if(mediaPlayer != null)
        {
            mediaPlayer.stop();
            mediaPlayer.release();
            WeydioApplication.setIsPlay(false);
        }
    }

    private class PreparedListener implements MediaPlayer.OnPreparedListener
    {
        private int position;

        public PreparedListener(int position)
        {
            this.position = position;
        }

        @Override
        public void onPrepared(MediaPlayer mp)
        {
            mp.start();
            isPause = false;
            firstPlay++;
            WeydioApplication.setIsPlay(true);

            if(position > 0)
            {
                mp.seekTo(position);

            }
        }
    }

    private void sendBroadCastToNowPlay()
    {
        Intent intent = new Intent();//创建Intent对象
        intent.setAction("serviceChangAlbumArt");
        intent.putExtra("changAlbum", currentIndex);
        sendBroadcast(intent);//发送广播
    }
}

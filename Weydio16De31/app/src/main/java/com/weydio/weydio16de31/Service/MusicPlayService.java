package com.weydio.weydio16de31.Service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import com.weydio.weydio16de31.MainActivity;
import com.weydio.weydio16de31.Model.AppConstant;
import com.weydio.weydio16de31.Model.Audio;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/1/1.
 */

public class MusicPlayService extends Service
{
    private MediaPlayer mediaPlayer = new MediaPlayer();

    private String path;

    private boolean isPause;

    private Audio audio;

    int msg;

    private int currentIndex;

    private int maxIndex;

    private ArrayList<Audio> la;

    private Intent mInent;


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
        this.mInent = intent;

        currentIndex = intent.getIntExtra("position", 0);

        if(mediaPlayer.isPlaying())
        {
            stop();
        }

        path = intent.getStringExtra("url");

        Log.i("PATH", path);

        msg = intent.getIntExtra("MSG", 0);

        if(msg == AppConstant.PlayerMsg.PLAY_MSG)
        {
            play(0);
        }
        else if(msg == AppConstant.PlayerMsg.PAUSE_MSG)
        {
            pause();
        }
        else if(msg == AppConstant.PlayerMsg.STOP_MSG)
        {
            stop();
        }
        else if(msg == AppConstant.PlayerMsg.NEXT_MSG)
        {
            // none.
        }

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
        {

            @Override
            public void onCompletion(MediaPlayer mp)
            {
                la = mInent.getParcelableArrayListExtra("Audio");

                Log.i("Intent", mInent+"");
                Log.i("LA", la+"");

                currentIndex++;
                audio = la.get(currentIndex+1);

                path = audio.getmPath();

                play(0);
            }
        });


        return super.onStartCommand(intent, flags, startId);
    }


    private void stop()
    {
        if(mediaPlayer != null)
        {
            mediaPlayer.stop();

            try
            {
                mediaPlayer.prepare();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    private void pause()
    {
        if(mediaPlayer != null && mediaPlayer.isPlaying())
        {
            mediaPlayer.pause();
            isPause = true;
        }
    }

    private void play(int position)
    {
        try
        {
            mediaPlayer.reset();

            mediaPlayer.setDataSource(path);
            mediaPlayer.prepare();
            mediaPlayer.setOnPreparedListener(new PreparedListener(position));

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }


    @Override
    public void onDestroy()
    {
        if(mediaPlayer != null)
        {
            mediaPlayer.stop();
            mediaPlayer.release();
        }

        super.onDestroy();
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

            if(position > 0)
            {
                mp.seekTo(position);

            }
        }
    }

}

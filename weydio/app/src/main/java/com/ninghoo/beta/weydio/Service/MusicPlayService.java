package com.ninghoo.beta.weydio.Service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import com.ninghoo.beta.weydio.Application.WeydioApplication;
import com.ninghoo.beta.weydio.model.AppConstant;
import com.ninghoo.beta.weydio.model.Audio;

import java.util.ArrayList;

/**
 * Created by ningfu on 17-2-8.
 */

public class MusicPlayService extends Service
{
    private MediaPlayer mediaPlayer = new MediaPlayer();

    private String path;

    private boolean isPause;

    // audio是单个的item数据。
    private Audio audio;

    int msg;

    private static int currentIndex;

    private ArrayList<Audio> la;


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

        currentIndex = intent.getIntExtra("position", 0);

        if(mediaPlayer.isPlaying())
        {
            stop();
        }

        path = la.get(currentIndex).getmPath();

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

        // 自动下一首。
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
        {

            @Override
            public void onCompletion(MediaPlayer mp)
            {
                currentIndex++;

                audio = la.get(currentIndex);

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

    public void pause()
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

package com.ninghoo.beta.weydio.Activity;

import android.content.BroadcastReceiver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.john.waveview.WaveView;
import com.ninghoo.beta.weydio.Application.WeydioApplication;
import com.ninghoo.beta.weydio.Model.AppConstant;
import com.ninghoo.beta.weydio.Model.Audio;
import com.ninghoo.beta.weydio.R;
import com.ninghoo.beta.weydio.Service.MusicPlayService;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xw.repo.BubbleSeekBar;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.ninghoo.beta.weydio.Service.MusicPlayService.mediaPlayer;

/**
 * Created by ningfu on 17-2-25.
 */

public class NowPlayActivity extends CommonActivity implements View.OnTouchListener
{
    WaveView mWaveView;
    BubbleSeekBar mBubbleSeekBar;

    private IntentFilter intentFilter;
    private WeydioReceiver mWeydioReceiver;

    ImageView mBtnPlayPause;
    ImageView mBtnPrevious;
    ImageView mBtnNext;
    ImageView mBtnRoundTyp;
    ImageView mBtnMusicStack;

    TextView mTvTimeStill;
    Timer mTimer;
    TimerTask mTimerTask;

    TextView mArtistName;
    TextView mSongName;

    private ArrayList<Audio> mData;
    Audio audio;

    CircleImageView mAlbumArt;

    boolean isPause = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        mData = WeydioApplication.getMla();

        //判断横屏竖屏来填充不同的布局。
        if(getResources().getConfiguration().orientation == 1)
        {
            setContentView(R.layout.activity_nowplay);

            // 因为子啊横屏情况下，没有timestill的textView存在。
            initTextDuration();
        }
        else
        {
            setContentView(R.layout.activity_nowplay_horizonal);
        }

        initTranStatus();

        initBubbleSeek();

        initAlbumImage();

        initMusicCtrl();

        initNamenArtist();

        initBroadCast();

        initRoundType();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();

        unregisterReceiver(mWeydioReceiver);
    }

    private void initNamenArtist()
    {
        mArtistName = (TextView) findViewById(R.id.tv_nowArtistName);
        mSongName = (TextView) findViewById(R.id.tv_nowSongName);

        mArtistName.setText("." + audio.getmArtist());
        mSongName.setText(audio.getmTitle());
    }

    private void initBroadCast()
    {
        intentFilter = new IntentFilter();
        intentFilter.addAction("serviceChangAlbumArt");

        mWeydioReceiver = new WeydioReceiver();
        registerReceiver(mWeydioReceiver, intentFilter);
    }

    private void initAlbumImage()
    {
        audio = mData.get(MusicPlayService.currentIndex);

        final Uri albumArtUri = Uri.parse("content://media/external/audio/albumart");

        Uri uri = ContentUris.withAppendedId(albumArtUri, audio.getmAlbumId());

        String url = uri.toString();

//        Log.i("URL" , ":"+url);

        mAlbumArt = (CircleImageView) findViewById(R.id.ib_albumArt);

        // 这里的ImageLoader，并没有用MediaUtils去获取专辑图片，而是直接获取歌曲专辑的地址。
        ImageLoader.getInstance().displayImage(url, mAlbumArt, WeydioApplication.mOptions);
        setAnimation(mAlbumArt);
    }

    private void initMusicCtrl()
    {
        mBtnPlayPause = (ImageView) findViewById(R.id.im_playPause);
        mBtnPrevious = (ImageView) findViewById(R.id.ib_previous);
        mBtnNext = (ImageView) findViewById(R.id.ib_next);
        mBtnRoundTyp = (ImageView) findViewById(R.id.ib_replay);
        mBtnMusicStack = (ImageView) findViewById(R.id.ib_mustack);
        mTvTimeStill = (TextView) findViewById(R.id.tv_timestill);


        mBtnPlayPause.setOnTouchListener(this);
        mBtnPrevious.setOnTouchListener(this);
        mBtnNext.setOnTouchListener(this);
        mBtnRoundTyp.setOnTouchListener(this);
        mBtnMusicStack.setOnTouchListener(this);

        if (MusicPlayService.mediaPlayer.isPlaying())
        {
            mBtnPlayPause.setImageResource(R.drawable.ic_pause_circle_filled_white_48dp);
        }
        else
        {
            mBtnPlayPause.setImageResource(R.drawable.ic_play_arrow_white_48dp);
        }
    }


    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        switch (v.getId())
        {
            case R.id.im_playPause:
                if(!MusicPlayService.mediaPlayer.isPlaying())
                {
                    int i = MusicPlayService.firstPlay;
                    if(i == 0)
                    {
                        Intent intent = new Intent();
//                    intent.putExtra("randomPlay", new Random().nextInt(WeydioApplication.getMla().size()));
                        intent.putExtra("MSG", AppConstant.PlayerMsg.PLAY_MSG);
                        intent.setClass(WeydioApplication.getContext(), MusicPlayService.class);

                        i++;
                        startService(intent);
                        mBtnPlayPause.setImageResource(R.drawable.ic_pause_circle_filled_white_48dp);
                    }
                    else
                    {
                        MusicPlayService.mediaPlayer.start();
                        mBtnPlayPause.setImageResource(R.drawable.ic_pause_circle_filled_white_48dp);
                        isPause = false;
                    }
                }
                else if(MusicPlayService.mediaPlayer.isPlaying())
                {
                    mediaPlayer.pause();
                    mBtnPlayPause.setImageResource(R.drawable.ic_play_arrow_white_48dp);
                    isPause = true;
                }
                break;

            case R.id.ib_previous:
                activityPreviousSong();
                mBtnPlayPause.setImageResource(R.drawable.ic_pause_circle_filled_white_48dp);
                isPause = false;
                break;

            case R.id.ib_next:
                activityNextSong();
                mBtnPlayPause.setImageResource(R.drawable.ic_pause_circle_filled_white_48dp);
                isPause = false;
                break;

            case R.id.ib_replay:

                break;

            case R.id.ib_mustack:
                finish();
                break;

            default:
                break;
        }
        return false;
    }

    private void initTranStatus() 
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    private void initWaveDuration()
    {
        mWaveView = (WaveView) findViewById(R.id.wave_view);

    }

    private void initBubbleSeek()
    {
        mBubbleSeekBar = (BubbleSeekBar) findViewById(R.id.bubble_seek_bar_0);
//        mWaveView = (WaveView) findViewById(R.id.wave_view);

        AudioManager mAudioManager = (AudioManager) WeydioApplication.getContext().getSystemService(Context.AUDIO_SERVICE);
        int currVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC) ;// 当前的媒体音量
        mBubbleSeekBar.setProgress(currVolume) ;

        mBubbleSeekBar.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListenerAdapter() {
            @Override
            public void onProgressChanged(int i)
            {
//                mWaveView.setProgress(i);

                AudioManager audioManager = (AudioManager) WeydioApplication.getContext().getSystemService(Context.AUDIO_SERVICE);
//                Log.v("lyj_ring", "mVoiceSeekBar max progress = "+arg1);
                //系统音量和媒体音量同时更新
                audioManager.setStreamVolume(AudioManager.STREAM_SYSTEM, i, 0);
                audioManager.setStreamVolume(3, i, 0);//  3 代表  AudioManager.STREAM_MUSIC

                if(!WeydioApplication.getIsPlay())
                {
                    if(i >= 50)
                    {
                        Toast.makeText(WeydioApplication.getContext(), "<(￣—￣)> 这是音量键，很危险的。。。", Toast.LENGTH_SHORT).show();

//                        Toast toast=Toast.makeText(WeydioApplication.getContext(), "<(￣—￣)>  这样玩音量键，很危险",  Toast.LENGTH_SHORT);
//                        toast.setGravity(gravity, 300, 300);    //设置toast的位置
//                        toast.show();
                    }
                }
            }

            @Override
            public void onProgressChanged(float v)
            {

            }

            @Override
            public void getProgressOnActionUp(int i) {

            }

            @Override
            public void getProgressOnActionUp(float v) {

            }

            @Override
            public void getProgressOnFinally(int i) {

            }

            @Override
            public void getProgressOnFinally(float v) {

            }
        });

    }


    private void activityPreviousSong()
    {
        Intent intent = new Intent();

        intent.putExtra("MSG", AppConstant.PlayerMsg.PRIVIOUS_MSG);
        intent.putExtra("BASIC", 1);
        intent.setClass(WeydioApplication.getContext(), MusicPlayService.class);
        // 在这里设置Intent去跳转制定的Sevice。

        startService(intent);
    }

    private void activityNextSong()
    {
        Intent intent = new Intent();

         Context mContext = WeydioApplication.getContext();

        intent.putExtra("MSG", AppConstant.PlayerMsg.NEXT_MSG);
        intent.putExtra("BASIC", 1);
        intent.setClass(mContext, MusicPlayService.class);
        // 在这里设置Intent去跳转制定的Sevice。

        mContext.startService(intent);
    }

    private void setAnimation(View viewToAnimate)
    {
        Animation animation = AnimationUtils.loadAnimation(viewToAnimate.getContext(),
                R.anim.item_bottom_in);
        viewToAnimate.startAnimation(animation);

    }

    private class WeydioReceiver extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            initAlbumImage();

            initNamenArtist();
        }
    }

    private void initTextDuration()
    {
        mTimer = new Timer();
        mTimerTask = new TimerTask()
        {
            @Override
            public void run()
            {
                Message message = new Message();
                message.what = 1;
                mHandler.sendMessage(message);
            }
        };
        mTimer.schedule(mTimerTask, 0, 1000);
    }

    private Handler mHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);

            if(MusicPlayService.firstPlay == 0)
            {
                int millis = audio.getmDuration();

                int secondnd = (millis / 1000) / 60;
                int million = (millis / 1000) % 60;
                String f = String.valueOf(secondnd);
                String m = million >= 10 ? String.valueOf(million) : "0"
                        + String.valueOf(million);

                mTvTimeStill.setText("- " + f + "min . " + m + " -");
            }
            else
            {
                switch (msg.what)
                {
                    case 1:
                        int millis = MusicPlayService.mediaPlayer.getDuration() - MusicPlayService.mediaPlayer.getCurrentPosition();
                        int secondnd = (millis / 1000) / 60;
                        int million = (millis / 1000) % 60;
                        String f = String.valueOf(secondnd);
                        String m = million >= 10 ? String.valueOf(million) : "0"
                                + String.valueOf(million);

                        mTvTimeStill.setText("- " + f + "min . " + m + " -");
                }
            }
        }
    };


    private void initRoundType()
    {

    }
}

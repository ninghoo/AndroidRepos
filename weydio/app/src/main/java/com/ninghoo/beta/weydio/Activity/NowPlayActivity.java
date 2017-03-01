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
import android.support.annotation.Nullable;
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

import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.R.attr.gravity;
import static com.ninghoo.beta.weydio.Service.MusicPlayService.firstPlay;
import static com.ninghoo.beta.weydio.Service.MusicPlayService.mediaPlayer;

/**
 * Created by ningfu on 17-2-25.
 */

public class NowPlayActivity extends CommonActivity implements View.OnClickListener
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

    TextView mArtistName;
    TextView mSongName;

    Audio audio;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_nowplay);

        initTranStatus();

        initBubbleSeek();

        initAlbumImage();

        initMusicCtrl();

        initWaveDuration();

        initNamenArtist();

        initBroadCast();
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

        mArtistName.setText(audio.getmArtist());
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
        audio = WeydioApplication.getMla().get(MusicPlayService.currentIndex);

        final Uri albumArtUri = Uri.parse("content://media/external/audio/albumart");

        Uri uri = ContentUris.withAppendedId(albumArtUri, audio.getmAlbumId());

        String url = uri.toString();

//        Log.i("URL" , ":"+url);

        CircleImageView mAlbumArt = (CircleImageView) findViewById(R.id.ib_albumArt);

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

        mBtnPlayPause.setOnClickListener(this);
        mBtnPrevious.setOnClickListener(this);
        mBtnNext.setOnClickListener(this);
        mBtnRoundTyp.setOnClickListener(this);
        mBtnMusicStack.setOnClickListener(this);

        mBtnPlayPause.setImageResource(R.drawable.ic_play_circle_filled_white_48dp);
    }

    @Override
    public void onClick(View v)
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
                    }
                }
                else if(MusicPlayService.mediaPlayer.isPlaying())
                {
                    mediaPlayer.pause();
                    mBtnPlayPause.setImageResource(R.drawable.ic_play_circle_filled_white_48dp);
                }
                break;

            case R.id.ib_previous:
                activityPreviousSong();
                break;

            case R.id.ib_next:
                activityNextSong();
                break;

            case R.id.ib_replay:

                break;

            case R.id.ib_mustack:
                Intent intent = new Intent();
                intent.setClass(WeydioApplication.getContext(), MusicRecyclerActivity.class);

                startActivity(intent);

                break;

            default:
                break;
        }
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
//
//        Handler handler = new Handler();
//
//        Runnable thread = new Runnable()
//        {
//            public void run() {
//                // 获得歌曲现在播放位置并设置成播放进度条的值
//                if (mediaPlayer != null)
//                {
//                    int maxDuration = mediaPlayer.getDuration();
//                    int currentDuration = mediaPlayer.getCurrentPosition();
//                    int duration = currentDuration/maxDuration * 100;
//                    Log.i("PRS", ":" + duration);
//
//                    mWaveView.setProgress(duration);
//                    // 每次延迟100毫秒再启动线程
//                }
//            }
//        };
//        handler.postDelayed(thread, 100);
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
//                        Toast.makeText(WeydioApplication.getContext(), "<(￣—￣)> 这样玩音量键，很危险的。。。", Toast.LENGTH_SHORT).show();

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

    public class WeydioReceiver extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            initAlbumImage();

            initNamenArtist();
        }
    }
}

//package com.ninghoo.beta.weydio2.Activity;
//
//import android.app.KeyguardManager;
//import android.content.BroadcastReceiver;
//import android.content.ContentUris;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.media.AudioManager;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.os.PowerManager;
//import android.support.annotation.Nullable;
//import android.view.GestureDetector;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.WindowManager;
//import android.view.animation.Animation;
//import android.view.animation.AnimationUtils;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.ninghoo.beta.weydio2.Model.Audio;
//import com.nostra13.universalimageloader.core.ImageLoader;
//
//import java.util.ArrayList;
//import java.util.Timer;
//import java.util.TimerTask;
//
//import me.imid.swipebacklayout.lib.SwipeBackLayout;
//import me.imid.swipebacklayout.lib.app.SwipeBackActivity;
//
///**
// * Created by ningfu on 17-3-24.
// */
//
//public class LockScreenActivity extends SwipeBackActivity implements View.OnTouchListener
//{
//    private SwipeBackLayout mSwipeBackLayout;
//
//    WaveView mWaveView;
//    BubbleSeekBar mBubbleSeekBar;
//
//    private IntentFilter intentFilter;
//    private WeydioReceiver mWeydioReceiver;
//
//    FloatingActionButton mBtnPlayPause;
//    ImageView mBtnPrevious;
//    ImageView mBtnNext;
//    ImageView mBtnRoundTyp;
////    ImageView mBtnMusicStack;
//
//    TextView mTvTimeStill;
//    Timer mTimer;
//    TimerTask mTimerTask;
//
//    TextView mArtistName;
//    TextView mSongName;
//
//    private ArrayList<Audio> mData;
//    Audio audio;
//
//    //    CircleImageView mAlbumArt;
//    CircularMusicProgressBar mAlbumArt;
//
//    boolean isPause = true;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState)
//    {
//        super.onCreate(savedInstanceState);
//
//        // 仅使用WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD的时候，该flag会在解锁后显示activity。
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
//                |WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
//
//        setContentView(R.layout.activity_lockscreen);
//
//        mData = WeydioApplication.getMla();
//
//        initSwipeEdge();
//
//        initTextDuration();
//
//        initTranStatus();
//
//        initBubbleSeek();
//
//        initAlbumImage();
//
//        initMusicCtrl();
//
//        initNamenArtist();
//
//        initBroadCast();
//
//        initRoundType();
//
//    }
//    @Override
//    protected void onRestart()
//    {
//        super.onRestart();
//        initRoundType();
//    }
//
//    @Override
//    protected void onDestroy()
//    {
//        super.onDestroy();
//
//        unregisterReceiver(mWeydioReceiver);
//    }
//
//    private void initSwipeEdge()
//    {
//        mSwipeBackLayout = getSwipeBackLayout();
//
//        mSwipeBackLayout.setOnTouchListener(new View.OnTouchListener()
//        {
//            @Override
//            public boolean onTouch(View v, MotionEvent event)
//            {
//                GestureDetector detector = new GestureDetector(WeydioApplication.getContext(), new GestureDetector.SimpleOnGestureListener() {
//
//                    @Override
//                    public boolean onSingleTapUp(MotionEvent e) {
//                        return super.onSingleTapUp(e);
//                    }
//
//                    @Override
//                    public void onLongPress(MotionEvent e) {
//                        super.onLongPress(e);
//                    }
//
//                    @Override
//                    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
//                        return super.onScroll(e1, e2, distanceX, distanceY);
//                    }
//
//                    @Override
//                    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
//                        return super.onFling(e1, e2, velocityX, velocityY);
//                    }
//
//                    @Override
//                    public void onShowPress(MotionEvent e) {
//                        super.onShowPress(e);
//                    }
//
//                    @Override
//                    public boolean onDown(MotionEvent e) {
//                        return super.onDown(e);
//                    }
//
//                    @Override
//                    public boolean onDoubleTap(MotionEvent e)
//                    {
//                        //获取电源管理器对象
//                        PowerManager pm=(PowerManager) getSystemService(Context.POWER_SERVICE);
//
//                        //得到键盘锁管理器对象
//                        KeyguardManager km= (KeyguardManager)getSystemService(Context.KEYGUARD_SERVICE);
//                        KeyguardManager.KeyguardLock kl = km.newKeyguardLock("unLock");
//
//                        kl.reenableKeyguard();
//
//                        return super.onDoubleTap(e);
//                    }
//
//                    @Override
//                    public boolean onDoubleTapEvent(MotionEvent e) {
//                        return super.onDoubleTapEvent(e);
//                    }
//
//                    @Override
//                    public boolean onSingleTapConfirmed(MotionEvent e) {
//                        return super.onSingleTapConfirmed(e);
//                    }
//
//                    @Override
//                    public boolean onContextClick(MotionEvent e) {
//                        return super.onContextClick(e);
//                    }
//                });
//                detector.onTouchEvent(event);
//
//                return false;
//            }
//        });
//
//        mSwipeBackLayout.setEdgeSize(1600);
//
//        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_BOTTOM);
//    }
//
//    @Override
//    public void onBackPressed()
//    {
////        super.onBackPressed();
//    }
//
//    private void initNamenArtist()
//    {
//        mArtistName = (TextView) findViewById(R.id.tv_nowArtistName);
//        mSongName = (TextView) findViewById(R.id.tv_nowSongName);
//
//        mArtistName.setText("." + audio.getmArtist());
//        mSongName.setText(audio.getmTitle());
//    }
//
//    private void initBroadCast()
//    {
//        intentFilter = new IntentFilter();
//        intentFilter.addAction("serviceChangAlbumArt");
//
//        mWeydioReceiver = new WeydioReceiver();
//        registerReceiver(mWeydioReceiver, intentFilter);
//    }
//
//    private void initAlbumImage()
//    {
//        audio = mData.get(MusicPlayService.currentIndex);
//
//        final Uri albumArtUri = Uri.parse("content://media/external/audio/albumart");
//
//        Uri uri = ContentUris.withAppendedId(albumArtUri, audio.getmAlbumId());
//
//        String url = uri.toString();
//
////        Log.i("URL" , ":"+url);
//
//        mAlbumArt = (CircularMusicProgressBar) findViewById(R.id.ib_albumArt);
//
//        // 这里的ImageLoader，并没有用MediaUtils去获取专辑图片，而是直接获取歌曲专辑的地址。
//        ImageLoader.getInstance().displayImage(url, mAlbumArt, WeydioApplication.mOptionsBig);
//        setAnimation(mAlbumArt);
//
//        mAlbumArt.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                Intent intent = new Intent();
//
//                intent.putExtra("MSG", AppConstant.PlayerMsg.MUSICSTACK_MSG);
//                intent.setClass(WeydioApplication.getContext(), MusicPlayService.class);
//                // 在这里设置Intent去跳转制定的Sevice。
//
//                startService(intent);
//            }
//        });
//    }
//
//    private void initMusicCtrl()
//    {
//        mBtnPlayPause = (FloatingActionButton) findViewById(R.id.im_playPause);
//        mBtnPrevious = (ImageView) findViewById(R.id.ib_previous);
//        mBtnNext = (ImageView) findViewById(R.id.ib_next);
//        mBtnRoundTyp = (ImageView) findViewById(R.id.ib_replay);
////        mBtnMusicStack = (ImageView) findViewById(R.id.ib_mustack);
//        mTvTimeStill = (TextView) findViewById(R.id.tv_timestill);
//
//
//        mBtnPlayPause.setOnTouchListener(this);
//        mBtnPrevious.setOnTouchListener(this);
//        mBtnNext.setOnTouchListener(this);
//        mBtnRoundTyp.setOnTouchListener(this);
////        mBtnMusicStack.setOnTouchListener(this);
//
//        if (MusicPlayService.mediaPlayer.isPlaying())
//        {
//            mBtnPlayPause.setImageResource(R.drawable.ic_fiber_manual_record_white_18dp);
//        }
//        else
//        {
//            mBtnPlayPause.setImageResource(R.drawable.ic_clear_white_18dp);
//        }
//    }
//
//
//    @Override
//    public boolean onTouch(View v, MotionEvent event)
//    {
//        Intent intent = new Intent();
//
//        switch (v.getId())
//        {
//            case R.id.im_playPause:
//                if(!MusicPlayService.mediaPlayer.isPlaying())
//                {
//                    int i = MusicPlayService.firstPlay;
//                    if(i == 0)
//                    {
////                    intent.putExtra("randomPlay", new Random().nextInt(WeydioApplication.getMla().size()));
//                        intent.putExtra("MSG", AppConstant.PlayerMsg.PLAY_MSG);
//                        intent.setClass(WeydioApplication.getContext(), MusicPlayService.class);
//
//                        i++;
//                        startService(intent);
//                        mBtnPlayPause.setImageResource(R.drawable.ic_fiber_manual_record_white_18dp);
////                        mBtnPlayPause.setImageResource(R.drawable.ic_fiber_manual_record_white_18dp);
//                    }
//                    else
//                    {
//                        intent.putExtra("MSG", AppConstant.PlayerMsg.CONTINUE_MSG);
//                        intent.setClass(WeydioApplication.getContext(), MusicPlayService.class);
//                        startService(intent);
//
//                        mBtnPlayPause.setImageResource(R.drawable.ic_fiber_manual_record_white_18dp);
//                        isPause = false;
//                    }
//                }
//                else if(MusicPlayService.mediaPlayer.isPlaying())
//                {
//                    intent.putExtra("MSG", AppConstant.PlayerMsg.PAUSE_MSG);
//                    intent.setClass(WeydioApplication.getContext(), MusicPlayService.class);
//                    startService(intent);
//
//                    mBtnPlayPause.setImageResource(R.drawable.ic_clear_white_18dp);
//                    isPause = true;
//                }
//                break;
//
//            case R.id.ib_previous:
//                activityPreviousSong();
//                mBtnPlayPause.setImageResource(R.drawable.ic_fiber_manual_record_white_18dp);
//                isPause = false;
//                break;
//
//            case R.id.ib_next:
//                activityNextSong();
//                mBtnPlayPause.setImageResource(R.drawable.ic_fiber_manual_record_white_18dp);
//                isPause = false;
//                break;
//
//            case R.id.ib_replay:
//                if(MusicPlayService.replay == AppConstant.PlayerMsg.ROUND_MSG)
//                {
//                    mBtnRoundTyp.setImageResource(R.drawable.ic_repeat_one_wht);
//
//                    MusicPlayService.setReplay(AppConstant.PlayerMsg.REPEAT_MSG);
//                }
//                else if (MusicPlayService.replay == AppConstant.PlayerMsg.REPEAT_MSG)
//                {
//                    mBtnRoundTyp.setImageResource(R.drawable.ic_shuffle_white_48dp);
//
//                    MusicPlayService.setReplay(AppConstant.PlayerMsg.RANDOM_MSG);
//                }
//                else if (MusicPlayService.replay == AppConstant.PlayerMsg.RANDOM_MSG)
//                {
//                    mBtnRoundTyp.setImageResource(R.drawable.ic_replay_white_48dp);
//
//                    MusicPlayService.setReplay(AppConstant.PlayerMsg.ROUND_MSG);
//                }
//                break;
//
////            case R.id.ib_mustack:
////                finish();
////                break;
//
//            default:
//                break;
//        }
//        return false;
//    }
//
//    private void initTranStatus()
//    {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            //透明状态栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        }
//    }
//
//    private void initBubbleSeek()
//    {
//        mBubbleSeekBar = (BubbleSeekBar) findViewById(R.id.bubble_seek_bar_0);
////        mWaveView = (WaveView) findViewById(R.id.wave_view);
//
//        AudioManager mAudioManager = (AudioManager) WeydioApplication.getContext().getSystemService(Context.AUDIO_SERVICE);
//        int currVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC) ;// 当前的媒体音量
//        mBubbleSeekBar.setProgress(currVolume) ;
//
//        mBubbleSeekBar.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListenerAdapter() {
//            @Override
//            public void onProgressChanged(int i)
//            {
////                mWaveView.setProgress(i);
//
//                AudioManager audioManager = (AudioManager) WeydioApplication.getContext().getSystemService(Context.AUDIO_SERVICE);
////                Log.v("lyj_ring", "mVoiceSeekBar max progress = "+arg1);
//                //系统音量和媒体音量同时更新
//                audioManager.setStreamVolume(AudioManager.STREAM_SYSTEM, i, 0);
//                audioManager.setStreamVolume(3, i, 0);//  3 代表  AudioManager.STREAM_MUSIC
//
//                WeydioApplication.setCurrentVolume(i);
//
//                if(!WeydioApplication.getIsPlay())
//                {
//                    if(i >= 50)
//                    {
//                        Toast.makeText(WeydioApplication.getContext(), "<(￣—￣)> 这是音量键，很危险的。。。", Toast.LENGTH_SHORT).show();
//
////                        Toast toast=Toast.makeText(WeydioApplication.getContext(), "<(￣—￣)>  这样玩音量键，很危险",  Toast.LENGTH_SHORT);
////                        toast.setGravity(gravity, 300, 300);    //设置toast的位置
////                        toast.show();
//                    }
//                }
//            }
//
//            @Override
//            public void onProgressChanged(float v)
//            {
//
//            }
//
//            @Override
//            public void getProgressOnActionUp(int i) {
//
//            }
//
//            @Override
//            public void getProgressOnActionUp(float v) {
//
//            }
//
//            @Override
//            public void getProgressOnFinally(int i) {
//
//            }
//
//            @Override
//            public void getProgressOnFinally(float v) {
//
//            }
//        });
//
//    }
//
//
//    private void activityPreviousSong()
//    {
//        Intent intent = new Intent();
//
//        intent.putExtra("MSG", AppConstant.PlayerMsg.PRIVIOUS_MSG);
//        intent.putExtra("BASIC", 1);
//        intent.setClass(WeydioApplication.getContext(), MusicPlayService.class);
//        // 在这里设置Intent去跳转制定的Sevice。
//
//        startService(intent);
//    }
//
//    private void activityNextSong()
//    {
//        Intent intent = new Intent();
//
//        Context mContext = WeydioApplication.getContext();
//
//        intent.putExtra("MSG", AppConstant.PlayerMsg.NEXT_MSG);
//        intent.putExtra("BASIC", 1);
//        intent.setClass(mContext, MusicPlayService.class);
//        // 在这里设置Intent去跳转制定的Sevice。
//
//        mContext.startService(intent);
//    }
//
//    private void setAnimation(View viewToAnimate)
//    {
//        Animation animation = AnimationUtils.loadAnimation(viewToAnimate.getContext(),
//                R.anim.item_bottom_in);
//        viewToAnimate.startAnimation(animation);
//    }
//
//    private class WeydioReceiver extends BroadcastReceiver
//    {
//        @Override
//        public void onReceive(Context context, Intent intent)
//        {
//            initAlbumImage();
//
//            initNamenArtist();
//
//            initBtnPlaynPause();
//        }
//    }
//
//    private void initTextDuration()
//    {
//        mTimer = new Timer();
//        mTimerTask = new TimerTask()
//        {
//            @Override
//            public void run()
//            {
//                Message message = new Message();
//                message.what = 1;
//                mHandler.sendMessage(message);
//            }
//        };
//        mTimer.schedule(mTimerTask, 0, 1000);
//    }
//
//    private Handler mHandler = new Handler()
//    {
//        @Override
//        public void handleMessage(Message msg)
//        {
//            super.handleMessage(msg);
//
//            if(MusicPlayService.firstPlay == 0)
//            {
//                int millis = audio.getmDuration();
//
//                int secondnd = (millis / 1000) / 60;
//                int million = (millis / 1000) % 60;
//                String f = String.valueOf(secondnd);
//                String m = million >= 10 ? String.valueOf(million) : "0"
//                        + String.valueOf(million);
//
//                mTvTimeStill.setText("- " + f + "min . " + m + " -");
////                mTvTimeStill.setText("- " + f + "min . " + m + " -");
//
//            }
//            else
//            {
//                switch (msg.what)
//                {
//                    case 1:
////                        int millis = MusicPlayService.mediaPlayer.getDuration() - MusicPlayService.mediaPlayer.getCurrentPosition();
////                        int secondnd = (millis / 1000) / 60;
////                        int million = (millis / 1000) % 60;
////                        String f = String.valueOf(secondnd);
////                        String m = million >= 10 ? String.valueOf(million) : "0"
////                                + String.valueOf(million);
////
////                        mTvTimeStill.setText("- " + f + "min . " + m + " -");
//                        int totalDuration = MusicPlayService.mediaPlayer.getDuration();
//                        int currentMillis = MusicPlayService.mediaPlayer.getCurrentPosition();
//                        int millis = totalDuration - currentMillis;
//                        int secondnd = (millis / 1000) / 60;
//                        int million = (millis / 1000) % 60;
//                        String f = String.valueOf(secondnd);
//                        String m = million >= 10 ? String.valueOf(million) : "0"
//                                + String.valueOf(million);
//
//                        mTvTimeStill.setText("- " + f + "min . " + m + " -");
//
//                        float durationProgress = (float) currentMillis / totalDuration;
//                        mAlbumArt.setValue(durationProgress * 100);
//                }
//            }
//        }
//    };
//
//
//    private void initRoundType()
//    {
//        if (MusicPlayService.replay == AppConstant.PlayerMsg.REPEAT_MSG)
//        {
//            mBtnRoundTyp.setImageResource(R.drawable.ic_repeat_one_wht);
//        }
//        else if (MusicPlayService.replay == AppConstant.PlayerMsg.RANDOM_MSG)
//        {
//            mBtnRoundTyp.setImageResource(R.drawable.ic_shuffle_white_48dp);
//        }
//        else
//        {
//            mBtnRoundTyp.setImageResource(R.drawable.ic_replay_white_48dp);
//        }
//    }
//
//    private void initBtnPlaynPause()
//    {
//        if (MusicPlayService.mediaPlayer.isPlaying())
//        {
//            mBtnPlayPause.setImageResource(R.drawable.ic_fiber_manual_record_white_18dp);
//        }
//        else
//        {
//            mBtnPlayPause.setImageResource(R.drawable.ic_clear_white_18dp);
//        }
//    }
//}

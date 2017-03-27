package com.ninghoo.beta17ma27.weydio2.Service;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.view.KeyEvent;
import android.widget.Toast;

import com.ninghoo.beta17ma27.weydio2.Application.WeydioApplication;
import com.ninghoo.beta17ma27.weydio2.Model.AppConstant;
import com.ninghoo.beta17ma27.weydio2.Model.Audio;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by ningfu on 17-3-24.
 */

public class MusicPlayService extends NotifyService
{
    public  static MediaPlayer mediaPlayer = new MediaPlayer();

    private String path;

    public static boolean isPause = true;

    // audio是单个的item数据。
    private Audio audio;

    int msg;

    public static int currentIndex = 0;
    private int randomLastIndex = 0;

    private int mMaxPosition;

    private ArrayList<Audio> la;

    public static int firstPlay = 0;

    public static int replay = AppConstant.PlayerMsg.ROUND_MSG;

    BroadcastReceiver lockScreenReceiver;

    public ButtonBroadcastReceiver notifyBtnReceiver;

    AudioManager audioManager;
    ComponentName mComponent;

    private int doubleClick = 0;
    EarPhoneTimer myTimer;
    Timer timer;


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

//        initLockScreenPlay();

//        initButtonReceiver();

//        initMeidaButtonReceiver();

        Toast.makeText(WeydioApplication.getContext(), "asdad", Toast.LENGTH_LONG).show();
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
            isPause = false;
        }
        else if(msg == AppConstant.PlayerMsg.PLAY_MSG)
        {
            path = la.get(currentIndex).getmPath();
            play(0);
            isPause = false;
        }
        else if(msg == AppConstant.PlayerMsg.PAUSE_MSG)
        {
            mediaPlayer.pause();
            isPause = true;
        }
        else if(msg == AppConstant.PlayerMsg.CONTINUE_MSG)
        {
            mediaPlayer.start();
            isPause = false;
        }
        else if(msg == AppConstant.PlayerMsg.NEXT_MSG)
        {
            if (replay == AppConstant.PlayerMsg.RANDOM_MSG)
            {
                randomPlay();
                isPause = false;
            }
            else
            {
                nextSong();
                isPause = false;
            }
        }
        else if(msg == AppConstant.PlayerMsg.PRIVIOUS_MSG)
        {
            previousSong();
            isPause = false;
        }
        else if(msg == AppConstant.PlayerMsg.START_MSG)
        {
            play(intent.getIntExtra("randomPlay", 0));
            isPause = false;
        }
        else if(msg == AppConstant.PlayerMsg.MUSICSTACK_MSG)
        {
            if(!noticAgain)
            {
                noticAgain = true;
                initMusicNotify();
            }
            else
            {
                mNotifyManager.cancelAll();
                NotifyService.noticAgain = false;
            }
        }

        Toast.makeText(WeydioApplication.getContext(), ":"+ currentIndex, Toast.LENGTH_LONG).show();

//        initNotifyAlbumUrlnTitle();

//        initMusicNotify();

        // 自动下一首。
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
        {

            @Override
            public void onCompletion(MediaPlayer mp)
            {
                if(replay == AppConstant.PlayerMsg.REPEAT_MSG)
                {
                    play(currentIndex);
                }
                else if(replay == AppConstant.PlayerMsg.RANDOM_MSG)
                {
                    randomPlay();
                }
                else
                {
                    nextSong();
                }

                initNotifyAlbumUrlnTitle();

                initMusicNotify();
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
//            sendBroadCastToNowPlay();

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        isPause = false;
//        initMusicNotify();
    }

    private void randomPlay()
    {
        randomLastIndex = currentIndex;

        Random rand =new Random();
        currentIndex =rand.nextInt(mMaxPosition);
        path = la.get(currentIndex).getmPath();
        play(0);
    }

    private void nextSong()
    {
        currentIndex++;

        if(currentIndex == mMaxPosition)
        {
            currentIndex = 0;
        }

//        la = WeydioApplication.getMla();

        audio = la.get(currentIndex);

        path = audio.getmPath();

        play(0);
    }

    private void previousSong()
    {
        if(replay == AppConstant.PlayerMsg.RANDOM_MSG)
        {
            currentIndex = randomLastIndex;
        }
        else
        {
            currentIndex--;

            if(currentIndex < 0)
            {
                currentIndex = mMaxPosition -1;
            }
        }

//        la = WeydioApplication.getMla();

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

        audioManager.unregisterMediaButtonEventReceiver(mComponent);
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

    // 在service中监听锁屏和拔出耳机的BroadCastReceiver。
    private void initLockScreenPlay()
    {
        lockScreenReceiver = new BroadcastReceiver()
        {
            @Override
            public void onReceive(Context context, Intent intent)
            {
                String action = intent.getAction();

                if (action == Intent.ACTION_SCREEN_OFF)
                {
//                    Intent lockscreen = new Intent(MusicPlayService.this, LockScreenActivity.class);
//                    lockscreen.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(lockscreen);
                }
                else if(action == AudioManager.ACTION_AUDIO_BECOMING_NOISY)
                {
                    mediaPlayer.pause();
                    isPause = true;
                    initMusicNotify();
                    sendBroadCastToNowPlay();
                }

            }
        };

        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_OFF);
//        filter.addAction(Intent.ACTION_MEDIA_BUTTON);
        filter.addAction(AudioManager.ACTION_AUDIO_BECOMING_NOISY);
        registerReceiver(lockScreenReceiver, filter);
    }

    public static void setReplay(int replay) {
        MusicPlayService.replay = replay;
    }

    private void initNotifyAlbumUrlnTitle()
    {
        final Uri albumArtUri = Uri.parse("content://media/external/audio/albumart");

        audio = la.get(currentIndex);

        Uri uri = ContentUris.withAppendedId(albumArtUri, audio.getmAlbumId());

        String url = uri.toString();

        WeydioApplication.setAlbumUri(url);
        WeydioApplication.setCurrentMusicTitle(audio.getmTitle());
    }

    protected void initButtonReceiver()
    {
        notifyBtnReceiver = new ButtonBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_BUTTON);

        registerReceiver(notifyBtnReceiver, intentFilter);

    }

    public class ButtonBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent)
        {
            // TODO Auto-generated method stub
            String action = intent.getAction();
            if(action.equals(ACTION_BUTTON)){
                //通过传递过来的ID判断按钮点击属性或者通过getResultCode()获得相应点击事件
                int buttonId = intent.getIntExtra(INTENT_BUTTONID_TAG, 0);

                Intent mIntent = new Intent();

                switch (buttonId) {
                    case BUTTON_PREV_ID:
                        mIntent.putExtra("MSG", AppConstant.PlayerMsg.PRIVIOUS_MSG);
                        mIntent.setClass(WeydioApplication.getContext(), MusicPlayService.class);
                        startService(mIntent);
                        initMusicNotify();
                        break;
                    case BUTTON_PALY_ID:
                        if(mediaPlayer.isPlaying())
                        {
                            mediaPlayer.pause();
                            isPause = true;
                            initMusicNotify();
                            sendBroadCastToNowPlay();
                        }
                        else
                        {
                            mediaPlayer.start();
                            isPause = false;
                            initMusicNotify();
                            sendBroadCastToNowPlay();
                        }
                        break;
                    case BUTTON_NEXT_ID:
                        mIntent.putExtra("MSG", AppConstant.PlayerMsg.NEXT_MSG);
                        mIntent.setClass(WeydioApplication.getContext(), MusicPlayService.class);
                        startService(mIntent);
                        initMusicNotify();
                        break;
                    case BUTTON_CLOSE_ID:
                        mNotifyManager.cancelAll();
                        NotifyService.noticAgain = false;
                        break;
                    default:
                        break;
                }
            }
        }
    }

    public class MediaButtonReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent)
        {
            // TODO Auto-generated method stub
            String action = intent.getAction();

            if(action == Intent.ACTION_MEDIA_BUTTON)
            {
                Toast.makeText(WeydioApplication.getContext(), "ear", Toast.LENGTH_LONG).show();
                KeyEvent event = intent.getParcelableExtra(Intent.EXTRA_KEY_EVENT);

                int keycode = event.getKeyCode();

                switch (keycode) {
                    case KeyEvent.KEYCODE_MEDIA_NEXT:
                        myTimer = new EarPhoneTimer();
                        timer = new Timer(true);
                        timer.schedule(myTimer,600);

                        if(doubleClick == 2)
                        {
                            Intent intent1 = new Intent();
                            intent1.putExtra("MSG", AppConstant.PlayerMsg.NEXT_MSG);
                            intent1.setClass(WeydioApplication.getContext(), MusicPlayService.class);
                            startService(intent1);
                            initMusicNotify();
                            doubleClick = 0;
                        }
                        else
                        {
                            audioManager.setStreamVolume(AudioManager.STREAM_SYSTEM, WeydioApplication.getCurrentVolume() - 1, 0);
                            audioManager.setStreamVolume(3, WeydioApplication.getCurrentVolume() - 1, 0);
                            doubleClick = 0;
                        }
                        break;
                    case KeyEvent.KEYCODE_MEDIA_PREVIOUS:
                        myTimer = new EarPhoneTimer();
                        timer = new Timer(true);
                        timer.schedule(myTimer,600);

                        if(doubleClick == 2)
                        {
                            Intent intent1 = new Intent();
                            intent1.putExtra("MSG", AppConstant.PlayerMsg.PRIVIOUS_MSG);
                            intent1.setClass(WeydioApplication.getContext(), MusicPlayService.class);
                            startService(intent1);
                            initMusicNotify();
                            doubleClick = 0;
                        }
                        else
                        {
                            audioManager.setStreamVolume(AudioManager.STREAM_SYSTEM, WeydioApplication.getCurrentVolume() + 1, 0);
                            audioManager.setStreamVolume(3, WeydioApplication.getCurrentVolume() + 1, 0);
                            doubleClick = 0;
                        }
                        break;
                    case KeyEvent.KEYCODE_HEADSETHOOK:
                        if(mediaPlayer.isPlaying())
                        {
                            mediaPlayer.pause();
                            isPause = true;
                        }
                        else
                        {
                            mediaPlayer.start();
                            isPause = false;
                        }
                        initMusicNotify();
                        sendBroadCastToNowPlay();
                        break;
                    default:
                        break;
                }
            }
            abortBroadcast();
        }
    }

    private void initMeidaButtonReceiver()
    {
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        mComponent = new ComponentName(getPackageName(), MediaButtonReceiver.class.getName());

        audioManager.registerMediaButtonEventReceiver(mComponent);
    }

    public class EarPhoneTimer extends TimerTask
    {
        @Override
        public void run()
        {
            try
            {
                doubleClick ++;
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}

package com.ninghoo.beta17ma27.weydio2.Service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.widget.RemoteViews;

/**
 * Created by ningfu on 17-3-24.
 */

public class NotifyService extends Service
{
    PowerManager.WakeLock mWakeLock;

    protected RemoteViews remoteViews;
    protected Notification notification;
    protected NotificationManager mNotifyManager;

    public final static String ACTION_BUTTON = "WeydioNotify";

    public final static String INTENT_BUTTONID_TAG = "ButtonId";
    /** 上一首 按钮点击 ID */
    public final static int BUTTON_PREV_ID = 1;
    /** 播放/暂停 按钮点击 ID */
    public final static int BUTTON_PALY_ID = 2;
    /** 下一首 按钮点击 ID */
    public final static int BUTTON_NEXT_ID = 3;
    /** 关闭 按钮点击 ID */
    public final static int BUTTON_CLOSE_ID = 4;

    public static boolean noticAgain = true;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();

        mNotifyManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        initWakeLock();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();

        releaseWakeLock();
    }

    private void initWakeLock()
    {
        if (null == mWakeLock) {
            PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
            mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK
                    | PowerManager.ON_AFTER_RELEASE, "myService");
            if (null != mWakeLock) {
                mWakeLock.acquire();
            }
        }
    }

    private void releaseWakeLock()
    {
        if (null != mWakeLock) {
            mWakeLock.release();
            mWakeLock = null;
        }
    }

    protected void initMusicNotify()
    {
//        if(noticAgain)
//        {
//            NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
//
//            remoteViews = new RemoteViews(WeydioApplication.getContext().getPackageName(), R.layout.notification_control);
//
//            String url = WeydioApplication.getAlbumUri();
//
//            if (url != null)
//            {
//                remoteViews.setImageViewUri(R.id.bt_notic_last, Uri.parse(url));
//            }
//            else
//            {
//                remoteViews.setImageViewResource(R.id.bt_notic_last, R.drawable.oafront);
//            }
//
//            if(MusicPlayService.isPause)
//            {
//                remoteViews.setImageViewResource(R.id.bt_notic_play, R.drawable.ic_remove_black_48dp);
//            }
//            else
//            {
//                remoteViews.setImageViewResource(R.id.bt_notic_play, R.drawable.ic_brightness_1_black_18dp);
//            }
//
//            remoteViews.setTextViewText(R.id.tv_notic_musicname, WeydioApplication.getCurrentMusicTitle());
//
//            Log.i("NotifyAlbumUri", ":" + WeydioApplication.getAlbumUri());
//
//            Intent intent = new Intent(this, MusicRecyclerActivity.class);
//            // 点击跳转到主界面
//            PendingIntent intent_go = PendingIntent.getActivity(this, 5, intent,
//                    PendingIntent.FLAG_UPDATE_CURRENT);
//            remoteViews.setOnClickPendingIntent(R.id.notice, intent_go);
//
//////        // 4个参数context, requestCode, intent, flags
//////        PendingIntent intent_close = PendingIntent.getActivity(this, 0, intent,
//////                PendingIntent.FLAG_UPDATE_CURRENT);
//////        remoteViews.setOnClickPendingIntent(R.id.widget_close, intent_close);
////
////        // 设置上一曲
////        Intent prv = new Intent();
////        prv.setAction(String.valueOf(AppConstant.PlayerMsg.PRIVIOUS_MSG));
////        PendingIntent intent_prev = PendingIntent.getBroadcast(this, 1, prv,
////                PendingIntent.FLAG_UPDATE_CURRENT);
////        remoteViews.setOnClickPendingIntent(R.id.bt_notic_last, intent_prev);
////        remoteViews.setImageViewUri(R.id.bt_notic_last, WeydioApplication.getAlbumUri());
////
////        // 设置播放
////        if (MusicPlayService.mediaPlayer.isPlaying()) {
////            Intent playorpause = new Intent();
////            playorpause.setAction(String.valueOf(AppConstant.PlayerMsg.PAUSE_MSG));
////            PendingIntent intent_play = PendingIntent.getBroadcast(this, 2,
////                    playorpause, PendingIntent.FLAG_UPDATE_CURRENT);
////            remoteViews.setOnClickPendingIntent(R.id.bt_notic_play, intent_play);
////        }
////        else
////        {
////            Intent playorpause = new Intent();
////            playorpause.setAction(String.valueOf(AppConstant.PlayerMsg.PLAY_MSG));
////            PendingIntent intent_play = PendingIntent.getBroadcast(this, 6,
////                    playorpause, PendingIntent.FLAG_UPDATE_CURRENT);
////            remoteViews.setOnClickPendingIntent(R.id.bt_notic_play, intent_play);
////        }
////
////        // 下一曲
////        Intent next = new Intent();
////        next.setAction(String.valueOf(AppConstant.PlayerMsg.NEXT_MSG));
////        PendingIntent intent_next = PendingIntent.getBroadcast(this, 3, next,
////                PendingIntent.FLAG_UPDATE_CURRENT);
////        remoteViews.setOnClickPendingIntent(R.id.bt_notic_next, intent_next);
////
//////        // 设置收藏
//////        PendingIntent intent_fav = PendingIntent.getBroadcast(this, 4, intent,
//////                PendingIntent.FLAG_UPDATE_CURRENT);
//////        remoteViews.setOnClickPendingIntent(R.id.widget_fav, intent_fav);
//
//            Intent buttonIntent = new Intent(ACTION_BUTTON);
//        /* 上一首按钮 */
//            buttonIntent.putExtra(INTENT_BUTTONID_TAG, BUTTON_PREV_ID);
//            //这里加了广播，所及INTENT的必须用getBroadcast方法
//            PendingIntent intent_prev = PendingIntent.getBroadcast(this, 1, buttonIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//            remoteViews.setOnClickPendingIntent(R.id.bt_notic_last, intent_prev);
//        /* 播放/暂停  按钮 */
//            buttonIntent.putExtra(INTENT_BUTTONID_TAG, BUTTON_PALY_ID);
//            PendingIntent intent_paly = PendingIntent.getBroadcast(this, 2, buttonIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//            remoteViews.setOnClickPendingIntent(R.id.bt_notic_play, intent_paly);
//        /* 下一首 按钮  */
//            buttonIntent.putExtra(INTENT_BUTTONID_TAG, BUTTON_NEXT_ID);
//            PendingIntent intent_next = PendingIntent.getBroadcast(this, 3, buttonIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//            remoteViews.setOnClickPendingIntent(R.id.bt_notic_next, intent_next);
//        /* 关闭 按钮  */
//            buttonIntent.putExtra(INTENT_BUTTONID_TAG, BUTTON_CLOSE_ID);
//            PendingIntent intent_close = PendingIntent.getBroadcast(this, 4, buttonIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//            remoteViews.setOnClickPendingIntent(R.id.bt_notic_close, intent_close);
//
//            Notification notify = builder.build();
//            notify.contentView = remoteViews; // 设置下拉图标
//            notify.bigContentView = remoteViews; // 防止显示不完全,需要添加apisupport
//            notify.flags = Notification.FLAG_ONGOING_EVENT;
//            notify.icon = R.mipmap.ic_launcher_blk192;
//
//            builder.setSmallIcon(R.mipmap.ic_launcher_blk192);
//
//            mNotifyManager.notify(1, notify);
//        }
//        else
//        {
//            // none
//        }
    }
}
package com.ninghoo.beta.weydio.Service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.ninghoo.beta.weydio.Activity.MusicRecyclerActivity;
import com.ninghoo.beta.weydio.Application.WeydioApplication;
import com.ninghoo.beta.weydio.Model.AppConstant;
import com.ninghoo.beta.weydio.R;

/**
 * Created by ningfu on 17-3-8.
 */

public class NotifyService extends Service
{
    private RemoteViews remoteViews;
    private Notification notification;

    public ButtonBroadcastReceiver bReceiver;
    public final static String ACTION_BUTTON = "WeydioNotify";

    public final static String INTENT_BUTTONID_TAG = "ButtonId";
    /** 上一首 按钮点击 ID */
    public final static int BUTTON_PREV_ID = 1;
    /** 播放/暂停 按钮点击 ID */
    public final static int BUTTON_PALY_ID = 2;
    /** 下一首 按钮点击 ID */
    public final static int BUTTON_NEXT_ID = 3;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();

        initMusicNotify();

        initButtonReceiver();
    }

    private void initMusicNotify()
    {
        notification = new NotificationCompat.Builder(WeydioApplication.getContext()).build();

        NotificationManager mNotifyManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        remoteViews = new RemoteViews(WeydioApplication.getContext().getPackageName(), R.layout.notification_control);

        Intent intent = new Intent(this, MusicRecyclerActivity.class);
        // 点击跳转到主界面
        PendingIntent intent_go = PendingIntent.getActivity(this, 5, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.notice, intent_go);

//        // 4个参数context, requestCode, intent, flags
//        PendingIntent intent_close = PendingIntent.getActivity(this, 0, intent,
//                PendingIntent.FLAG_UPDATE_CURRENT);
//        remoteViews.setOnClickPendingIntent(R.id.widget_close, intent_close);

        // 设置上一曲
        Intent prv = new Intent();
        prv.setAction(String.valueOf(AppConstant.PlayerMsg.PRIVIOUS_MSG));
        PendingIntent intent_prev = PendingIntent.getBroadcast(this, 1, prv,
                PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.bt_notic_last, intent_prev);
        remoteViews.setImageViewUri(R.id.bt_notic_last, WeydioApplication.getAlbumUri());

        // 设置播放
        if (MusicPlayService.mediaPlayer.isPlaying()) {
            Intent playorpause = new Intent();
            playorpause.setAction(String.valueOf(AppConstant.PlayerMsg.PAUSE_MSG));
            PendingIntent intent_play = PendingIntent.getBroadcast(this, 2,
                    playorpause, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.bt_notic_play, intent_play);
        }
        else
        {
            Intent playorpause = new Intent();
            playorpause.setAction(String.valueOf(AppConstant.PlayerMsg.PLAY_MSG));
            PendingIntent intent_play = PendingIntent.getBroadcast(this, 6,
                    playorpause, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.bt_notic_play, intent_play);
        }

        // 下一曲
        Intent next = new Intent();
        next.setAction(String.valueOf(AppConstant.PlayerMsg.NEXT_MSG));
        PendingIntent intent_next = PendingIntent.getBroadcast(this, 3, next,
                PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.bt_notic_next, intent_next);

//        // 设置收藏
//        PendingIntent intent_fav = PendingIntent.getBroadcast(this, 4, intent,
//                PendingIntent.FLAG_UPDATE_CURRENT);
//        remoteViews.setOnClickPendingIntent(R.id.widget_fav, intent_fav);

        builder.setSmallIcon(R.drawable.oafront); // 设置顶部图标

        Notification notify = builder.build();
        notify.contentView = remoteViews; // 设置下拉图标
        notify.bigContentView = remoteViews; // 防止显示不完全,需要添加apisupport
        notify.flags = Notification.FLAG_ONGOING_EVENT;
        notify.icon = R.drawable.oafront;

        mNotifyManager.notify(1, notify);
    }

    public void initButtonReceiver(){
        bReceiver = new ButtonBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_BUTTON);
        registerReceiver(bReceiver, intentFilter);

    }

    public class ButtonBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            String action = intent.getAction();
            if(action.equals(ACTION_BUTTON)){
                //通过传递过来的ID判断按钮点击属性或者通过getResultCode()获得相应点击事件
                int buttonId = intent.getIntExtra(INTENT_BUTTONID_TAG, 0);
                switch (buttonId) {
                    case BUTTON_PREV_ID:
                        Toast.makeText(getApplicationContext(), "上一首", Toast.LENGTH_SHORT).show();
                        break;
                    case BUTTON_PALY_ID:
//                        Toast.makeText(getApplicationContext(), play_status, Toast.LENGTH_SHORT).show();
                        break;
                    case BUTTON_NEXT_ID:
                                Toast.makeText(getApplicationContext(), "下一首", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }
        }
    }
}

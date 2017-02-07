package com.weydio.weydio16oc23.BroadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pManager;

import com.weydio.weydio16oc23.Adapter.WeydioServiceDiscoverAdapter;
import com.weydio.weydio16oc23.MainActivity;
import com.weydio.weydio16oc23.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/10/28.
 */

public class WeydioBroadcastReceiver extends BroadcastReceiver
{
    private WifiP2pManager mManager;

    private WifiP2pManager.Channel mChannel;

    private MainActivity mMainActivity;

    public WeydioBroadcastReceiver(WifiP2pManager mManager, WifiP2pManager.Channel mChannel,
                                   MainActivity mMainActivity)
    {
        // 不需要返回类型。
        super();

        this.mManager = mManager;
        this.mChannel = mChannel;
        this.mMainActivity = mMainActivity;
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);

        if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
            if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
                mMainActivity.setIsWifiP2pEnabled(true);
            } else {
                mMainActivity.setIsWifiP2pEnabled(false);
            }

        } else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {
            if (mManager != null) {

            }
        } else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {

        } else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {

        }
    }
}

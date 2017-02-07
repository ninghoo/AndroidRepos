package com.weydio.weydio16oc16;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pManager;

import com.weydio.weydio16oc16.MainActivity;

public class WeydioBeamReceiver extends BroadcastReceiver
{
    private WifiP2pManager mManager;

    private WifiP2pManager.Channel mChannel;

    private MainActivity mMainActivity;

    /*
     默认的无参方法，如果要重写，就得调用超类super()方法。

     这里重写是为了让WeydioBeamReceiver和Activity进行通信？
      */
    public WeydioBeamReceiver(WifiP2pManager mManager, WifiP2pManager.Channel mChannel,
                              MainActivity mMainActivity)
    {
        super();

        this.mManager = mManager;
        this.mChannel = mChannel;
        this.mMainActivity = mMainActivity;
    }

    // 整个BroadcastReceiver只有onReceive()方法，只用于相应action事件。
    @Override
    public void onReceive(Context context, Intent intent)
    {
//        // TODO: This method is called when the BroadcastReceiver is receiving
//        // an Intent broadcast.
//        throw new UnsupportedOperationException("Not yet implemented");

        String action = intent.getAction();

        // if...else if， 所有的情况都会进行判断。

        // !!!!!!!!!!!!!!这里也写错了。
        if(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action))
        {
            int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);

            if(state == WifiP2pManager.WIFI_P2P_STATE_ENABLED)
            {
//                mMainActivity.setIsWifiP2pEnabled(true);
            }
            else
            {
//                mMainActivity.setIsWifiP2pEnabled(false);
            }
        }
        else if(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action))
        {
            if(mManager != null)
            {
                // 出错！！！！
                mManager.requestPeers(mChannel, (WifiP2pManager.PeerListListener)
                mMainActivity.getFragmentManager().findFragmentById(R.id.frag_list));
            }
        }
        else if(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action))
        {

        }
        else if(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action))
        {

        }
    }
}

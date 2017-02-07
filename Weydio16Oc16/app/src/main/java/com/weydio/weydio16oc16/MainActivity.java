package com.weydio.weydio16oc16;

import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    private WifiP2pManager mManager;
    private WifiP2pManager.Channel mChannel;
    private WeydioBeamReceiver mBroadcastReceiver = null;
    private IntentFilter mIntentFilter;

    private boolean isWifiP2pEnable = true;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 获取WifiP2pManager服务管理对象。
        mManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);

        /*
         初始化通信通道。

         channel通道通过mManager的initialize()方法来实现.
          */
        mChannel = mManager.initialize(this, getMainLooper(), null);

        // 实例化IntentFilter，添加要监听的Action.
        mIntentFilter = new IntentFilter();

        // 实例化BroadcastReceiver.
        mBroadcastReceiver = new WeydioBeamReceiver(mManager, mChannel, null);

        // 创建广播接收器对象。
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);


    }

    @Override
    protected void onResume()
    {
        super.onResume();

        // 注册广播接收器。
        registerReceiver(mBroadcastReceiver, mIntentFilter);
    }

    @Override
    protected void onPause()
    {
        super.onPause();

        // 注销广播接收器。
        unregisterReceiver(mBroadcastReceiver);
    }

    // 在xml文件实现了指定的onClick方法.
    public void onClick_Discover(View view)
    {
        if(!isWifiP2pEnable)
        {
            Toast.makeText(MainActivity.this, "Pleas Enable WiFi setting", Toast.LENGTH_LONG).show();
        }

        DeviceListFragment fragment = (DeviceListFragment) getFragmentManager()
                .findFragmentById(R.id.frag_list);

        fragment.onInitiateDiscover();

        mManager.discoverPeers(mChannel, new WifiP2pManager.ActionListener()
        {

            @Override
            public void onSuccess()
            {
                Toast.makeText(MainActivity.this, "Discover Initiated", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int reason)
            {
                Toast.makeText(MainActivity.this, "Discover Failed", Toast.LENGTH_LONG).show();
            }
        });

    }

    public void setIsWifiP2pEnabled(boolean isWifiP2pEnabled)
    {
        this.isWifiP2pEnable = isWifiP2pEnabled;
    }
}

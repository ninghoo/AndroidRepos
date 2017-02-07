package com.weydio.weydio16oc18;

import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.weydio.weydio16oc18.Adapter.WeydioListAdapter;
import com.weydio.weydio16oc18.BroadcastReceiver.WeydioBroadcastReceiver;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements WifiP2pManager.PeerListListener
{
    private WifiP2pManager mManager;
    private WifiP2pManager.Channel mChannel;
    private WeydioBroadcastReceiver mBroadcastReceiver = null;
    private final IntentFilter mIntentFilter = new IntentFilter();

    private boolean isWifiP2pEnable = false;

    private ListView mDeviceList;
    private List<WifiP2pDevice> peers = new ArrayList<WifiP2pDevice>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);

        mChannel = mManager.initialize(this, getMainLooper(), null);

        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);

        mDeviceList = (ListView) findViewById(R.id.lv_deviceList);

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

    @Override
    protected void onResume()
    {
        super.onResume();

        mBroadcastReceiver = new WeydioBroadcastReceiver(mManager, mChannel, this);

        registerReceiver(mBroadcastReceiver, mIntentFilter);
    }

    @Override
    protected void onPause()
    {
        super.onPause();

        unregisterReceiver(mBroadcastReceiver);
    }

    public void onClick_Discover(View view)
    {
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

    public void setIsWifiP2pEnabled(boolean isWifiP2pEnable)
    {
        this.isWifiP2pEnable = isWifiP2pEnable;

        if(isWifiP2pEnable)
        {
            Toast.makeText(this, "Wifi true", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "Wifi false", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPeersAvailable(WifiP2pDeviceList peersList)
    {
        peers.clear();
        peers.addAll(peersList.getDeviceList());

        WeydioListAdapter mdata = new WeydioListAdapter(this, R.layout.row_devices, peers);

        mDeviceList.setAdapter(mdata);
    }
}

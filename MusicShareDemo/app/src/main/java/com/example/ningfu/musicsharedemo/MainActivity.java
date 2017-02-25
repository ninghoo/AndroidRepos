package com.example.ningfu.musicsharedemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.ningfu.musicsharedemo.Adapter.MusicShrAdapter;
import com.example.ningfu.musicsharedemo.Broadcastreceiver.MusicShrBroadcastReceiver;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements WifiP2pManager.PeerListListener
{
    private WifiP2pManager mManager;
    private WifiP2pManager.Channel mChannel;
    private BroadcastReceiver mBroadcastReceiver = null;
    private final IntentFilter mIntentFilter = new IntentFilter();
    private boolean isWifiP2pEnable = false;
    private RecyclerView mDeviceList;
    private List<WifiP2pDevice> peers = new ArrayList<WifiP2pDevice>();

    private Button mBtnSecondAct;
    private Button mBtnThirdAct;
    private Button mBtnFourAct;
    private Button mBtnFifAct;

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

        mDeviceList = (RecyclerView) findViewById(R.id.rv_musicsharelst);

        mManager.discoverPeers(mChannel, new WifiP2pManager.ActionListener()
        {
            @Override
            public void onSuccess()
            {
//                Toast.makeText(MainActivity.this, "Discover Initiated", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int reason)
            {
//                Toast.makeText(MainActivity.this, "Discover Failed", Toast.LENGTH_LONG).show();
            }
        });

        initBtnSecondActivity();
        initBtnThirdActivity();
        initBtnFourthActivity();
        initBtnFifthActivity();
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        mBroadcastReceiver = new MusicShrBroadcastReceiver(mManager, mChannel, this);

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
//                Toast.makeText(MainActivity.this, "Discover Initiated", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int reason)
            {
//                Toast.makeText(MainActivity.this, "Discover Failed", Toast.LENGTH_LONG).show();
            }
        });

    }

    public void setIsWifiP2pEnabled(boolean isWifiP2pEnable)
    {
        this.isWifiP2pEnable = isWifiP2pEnable;

        if(isWifiP2pEnable)
        {
//            Toast.makeText(this, "Wifi true", Toast.LENGTH_SHORT).show();
        }
        else
        {
//            Toast.makeText(this, "Wifi false", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPeersAvailable(WifiP2pDeviceList peersList)
    {
        peers.clear();
        peers.addAll(peersList.getDeviceList());

        MusicShrAdapter mdata = new MusicShrAdapter(this, R.layout.item_musicshrlst, peers);

        mDeviceList.setAdapter(mdata);
    }

    private void initBtnSecondActivity()
    {
        mBtnSecondAct = (Button) findViewById(R.id.btn_secondActivity);
        mBtnSecondAct.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent();

                intent.setClass(MainActivity.this, SecondActivity.class);

                startActivity(intent);
            }
        });
    }

    private void initBtnThirdActivity()
    {
        mBtnThirdAct = (Button) findViewById(R.id.btn_thirdActivity);
        mBtnThirdAct.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent();

                intent.setClass(MainActivity.this, ThirdActivity.class);

                startActivity(intent);
            }
        });
    }

    private void initBtnFourthActivity()
    {
        mBtnFourAct = (Button) findViewById(R.id.btn_fourthActivity);
        mBtnFourAct.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent();

                intent.setClass(MainActivity.this, FourthActivity.class);

                startActivity(intent);
            }
        });
    }


    private void initBtnFifthActivity()
    {
        mBtnFifAct = (Button) findViewById(R.id.btn_fifthActivity);
        mBtnFifAct.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent();

                intent.setClass(MainActivity.this, FifthActivity.class);

                startActivity(intent);
            }
        });
    }

}

package com.weydio.weydio16oc23;

import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.nsd.WifiP2pDnsSdServiceInfo;
import android.net.wifi.p2p.nsd.WifiP2pDnsSdServiceRequest;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.Toast;

import com.weydio.weydio16oc23.Adapter.WeydioServiceDiscoverAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity
//        implements WifiP2pManager.DnsSdServiceResponseListener
{
    private static final String SERVER_PORT = "weydioBeam";
    private WifiP2pManager mManager;
    private WifiP2pManager.Channel mChannel;
    final HashMap<String, String> buddies = new HashMap<String, String>();

    private ListView serviceList;
    private List<WifiP2pDevice> peers = new ArrayList<WifiP2pDevice>();
    private WifiP2pDnsSdServiceRequest serviceRequest;

    private final IntentFilter mIntentFilter = new IntentFilter();

    private boolean isWifiP2pEnable = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);

        // 实例化WifiP2pManager。
        mManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);

        // 实例化Channel对象，需要通过mManager对象调用initialize实现。
        mChannel = mManager.initialize(this, getMainLooper(), null);

        serviceList = (ListView) findViewById(R.id.lv_serviceList);

        serviceRequest = WifiP2pDnsSdServiceRequest.newInstance();

        mManager.addServiceRequest(mChannel, serviceRequest, new WifiP2pManager.ActionListener()
        {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(int reason) {

            }
        });
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        // 在onResume方法里面去实现注册。
        startRegistration();
    }

    private void startRegistration()
    {
        Map record = new HashMap();

        record.put("listenport", String.valueOf(SERVER_PORT));
        record.put("buddyname", "Rock Tsui" + (int)(Math.random()*1000));
        record.put("available", "visble");

        WifiP2pDnsSdServiceInfo serviceInfo = WifiP2pDnsSdServiceInfo.newInstance("_test", "_presence._tcp", record);

        // 注意，这里的addLocalService，要求要传入channel参数，而channel是在onCreate中实例化的。
        mManager.addLocalService(mChannel, serviceInfo, new WifiP2pManager.ActionListener()
        {
            @Override
            public void onSuccess()
            {
                Toast.makeText(MainActivity.this, "Service Discover Initiated", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int reason)
            {
                Toast.makeText(MainActivity.this, "Failed Reason："+ reason, Toast.LENGTH_LONG).show();
            }
        });

        serviceRequest = WifiP2pDnsSdServiceRequest.newInstance();

        mManager.addServiceRequest(mChannel, serviceRequest, new WifiP2pManager.ActionListener()
        {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(int reason) {

            }
        });
    }

    public void onClick_DiscoverService(View view)
    {
        mManager.discoverServices(mChannel, new WifiP2pManager.ActionListener()
        {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(int reason) {

            }
        });
    }

    private void discoverService()
    {
        WifiP2pManager.DnsSdTxtRecordListener txtListener = new WifiP2pManager.DnsSdTxtRecordListener()
        {
            @Override
            public void onDnsSdTxtRecordAvailable(String fullDomainName, Map<String, String> record,
                                                  WifiP2pDevice device)
            {
                buddies.put(device.deviceAddress, record.get("buddyname"));
            }
        };

        WifiP2pManager.DnsSdServiceResponseListener serviceResponseListener = new WifiP2pManager.DnsSdServiceResponseListener()
        {
            @Override
            public void onDnsSdServiceAvailable(String instanceName, String registrationType, WifiP2pDevice srcDevice)
            {
                srcDevice.deviceName = buddies.containsKey(srcDevice.deviceAddress)?
                        buddies.get(srcDevice.deviceAddress):srcDevice.deviceName;

                peers.add(srcDevice);

                WeydioServiceDiscoverAdapter adapter = new WeydioServiceDiscoverAdapter(MainActivity.this,
                        R.layout.row_service, peers);

            }
        };
        mManager.setDnsSdResponseListeners(mChannel, serviceResponseListener, txtListener);
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
}

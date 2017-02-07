package com.weydio.weydio16oc22api14.Service;

import android.app.IntentService;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.nsd.WifiP2pDnsSdServiceInfo;
import android.net.wifi.p2p.nsd.WifiP2pServiceInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/10/25.
 */

public class WeydioBeamService extends IntentService
{
    private static final String SERVER_PORT = "weydioBeam";
    private WifiP2pManager mManager;


    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public WeydioBeamService(String name)
    {
        super(name);
        // 该方法为自带。
    }

    @Override
    protected void onHandleIntent(Intent intent)
    {
        startRegisteration();
    }

    private void startRegisteration()
    {
        Map record = new HashMap();

        record.put("listenport", String.valueOf(SERVER_PORT));
        record.put("buddyname", "Rock Tsui" + (int)(Math.random()*1000));
        record.put("available", "visble");

        WifiP2pDnsSdServiceInfo.newInstance("_test", "_presence._tcp", record);


    }
}

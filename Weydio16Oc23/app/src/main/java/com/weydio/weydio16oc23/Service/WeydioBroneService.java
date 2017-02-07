package com.weydio.weydio16oc23.Service;

import android.app.IntentService;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pManager;

/**
 * Created by Administrator on 2016/10/26.
 */

public class WeydioBroneService extends IntentService
{
    private WifiP2pManager mManager;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public WeydioBroneService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent)
    {
//        mManager.addLocalService();
    }
}

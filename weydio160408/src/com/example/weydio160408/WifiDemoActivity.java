package com.example.weydio160408;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiManager.WifiLock;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class WifiDemoActivity {
    /**
     * Called when the activity is first created.
     */

    private final static String TAG = "MyWifi";

    private StringBuffer mStringBuffer =
            new StringBuffer();

    public List<ScanResult> ListResult =
            new ArrayList<ScanResult>();

    private ScanResult mScanResult;

    private WifiManager mWifiManager;

    private WifiInfo mWifiInfo;

    private List<WifiConfiguration> wifiConfigList =
            new ArrayList<WifiConfiguration>();

    WifiManager.WifiLock mWifiLock;

    private ConnectivityManager mConnManager;

    private Context mContext;

    private List<WifiConfiguration> wifiConfigedSpecifiedList =
            new ArrayList<WifiConfiguration>();

    private NetworkInfo.State state;


    public WifiDemoActivity(Context context) {
        mContext = context;

        mWifiManager = (WifiManager) context.
                getSystemService(Context.WIFI_SERVICE);

        mWifiInfo = mWifiManager.getConnectionInfo();

        mConnManager = (ConnectivityManager) mContext.
                getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    public void againGetWifiInfo() {
        mWifiInfo = mWifiManager.getConnectionInfo();
    }

    public boolean idCoonectFriendly() {
        return mWifiManager.isWifiEnabled();
    }

    public boolean isConnectioning() {
        state = mConnManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).
                getState();

        if (NetworkInfo.State.CONNECTING == state) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isConnectioned() {
        state = mConnManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).
                getState();

        if (NetworkInfo.State.CONNECTED == state) {
            return true;
        } else {
            return false;
        }
    }

    public NetworkInfo.State getCurrentState() {
        state = mConnManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).
                getState();

        return state;
    }

    public void setWifiConfigedSpecifiedList(String ssid) {
        wifiConfigedSpecifiedList.clear();

        if (wifiConfigList != null) {
            for (WifiConfiguration item : wifiConfigList) {
                if (item.SSID.equalsIgnoreCase("\"" + ssid + "\"") &&
                        item.preSharedKey != null)//这个\"，是反斜杠。
                {
                    wifiConfigedSpecifiedList.add(item);
                }
            }
        }
    }

    public List<WifiConfiguration> getWifiConfigedSpecifiedList() {
        return wifiConfigedSpecifiedList;
    }

    public void openNetCard() {
        if (!mWifiManager.isWifiEnabled()) {
            mWifiManager.setWifiEnabled(true);
        }
    }

    public void closeNetCard() {
        if (!mWifiManager.isWifiEnabled()) {
            mWifiManager.setWifiEnabled(false);
        }
    }

    public void checkNetCardState() {
        if (mWifiManager.getWifiState() == 0) {
            Log.i(TAG, "网卡正在关闭");
        } else if (mWifiManager.getWifiState() == 1) {
            Log.i(TAG, "网卡已经关闭");
        } else if (mWifiManager.getWifiState() == 2) {
            Log.i(TAG, "网卡正在打开");
        } else if (mWifiManager.getWifiState() == 3) {
            Log.i(TAG, "网卡已经打开");
        }
    }

    public void scan() {
        mWifiManager.startScan();

        ListResult = mWifiManager.getScanResults();

        wifiConfigList = mWifiManager.getConfiguredNetworks();

        if (ListResult != null) {
            Log.i(TAG, "当前网络存在无限网络，请查看扫描结果。");
        } else {
            Log.i(TAG, "当前网络没有无限网络。");
        }
    }

    public List<ScanResult> getListResult() {
        return ListResult;
    }

    public String getScanList() {
        if (mStringBuffer != null) {
            mStringBuffer = new StringBuffer();
        }

        scan();

        ListResult = mWifiManager.getScanResults();
        if (ListResult != null) {
            for (int i = 0; i < ListResult.size(); i++) {
                mScanResult = ListResult.get(i);

                mStringBuffer = mStringBuffer.append("NO.").
                        append(i + 1).append(": ").append(mScanResult.SSID).
                        append("->").append(mScanResult.BSSID).
                        append("->").append(mScanResult.capabilities).
                        append("->").append(mScanResult.frequency).
                        append("->").append(mScanResult.level).
                        append("->").append(mScanResult.describeContents()).
                        append("\n\n");
            }
        }
        Log.i(TAG, mStringBuffer.toString());

        return mStringBuffer.toString();
    }

    public void disconnectWifi() {
        int netId = getNetworkId();

        mWifiManager.disableNetwork(netId);

        mWifiManager.disconnect();

        mWifiInfo = null;
    }

    public boolean checkNetworkState() {
        if (mWifiInfo != null) {
            return true;
        } else {
            return false;
        }
    }

    public int getNetworkId() {
        return (mWifiInfo == null) ? 0 : mWifiInfo.getNetworkId();
        //三目表达式，mWifiInfo=null的时候，输出0，非0，getNerworkId;
    }

    public int getIPAddress() {
        return (mWifiInfo == null) ? 0 : mWifiInfo.getIpAddress();
    }

    public void acquireWifiLock()
    //WifiLock，阻止手机关闭屏幕时关闭wifi，保持连接。
    {
        mWifiLock.acquire();
    }

    public void releaseWifiLock() {
        if (mWifiLock.isHeld()) {
            mWifiLock.release();
        }
    }

    public void createWifiLock() {
        mWifiLock = mWifiManager.createWifiLock("Test");
    }

    public List<WifiConfiguration> getConfiguration() {
        return wifiConfigList;
    }

    public boolean connectConfigurration(int index) {
        if (index >= wifiConfigList.size()) {
            return false;
        }//这里有没有else，是不是都是一样的？
        return mWifiManager.enableNetwork(wifiConfigedSpecifiedList.
                get(index).networkId, true);
    }

    public String getMacAddress() {
        return (mWifiInfo == null) ? "" : mWifiInfo.getMacAddress();
    }

    public String getBSSID() {
        return (mWifiInfo == null) ? "null" : mWifiInfo.getBSSID();
    }

    public String getWifiInfo() {
        return (mWifiInfo == null) ? "null" : mWifiInfo.toString();
    }

    public int addNwtwork(WifiConfiguration wcg) {
        int wcgID = mWifiManager.addNetwork(wcg);

        mWifiManager.enableNetwork(wcgID, true);

        return wcgID;
    }
}

package com.weydio.weydio16oc16;

import android.app.ListFragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/17.
 */

public class DeviceListFragment extends ListFragment implements WifiP2pManager.PeerListListener
{
    // 声明一个WifiP2pDevice数组对象peers，收集用于显示的设备列表。
    private List<WifiP2pDevice> peers = new ArrayList<WifiP2pDevice>();

    // 声明并初始化一个ProgressDialog对象，供onPeersAvailable()方法使用。
    ProgressDialog progressDialog = null;

    View mContentView = null;

    private  WifiP2pDevice device;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        mContentView = inflater.inflate(R.layout.list_devices, null);

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        // setAdapter方法在Fragment的onActivityCreated()生命周期中实现.

        this.setListAdapter(new WeydioBeamAdapter(getActivity(), R.layout.row_devices, peers));
    }

    // 以下是实现PeerListListenner接口所需要重写的方法。
    /*
    这里的fragment需要实现PeerListListener接口的原因是，当BroadcastReceiver监听到PEERS_CHANGED_ACTION的时候
    可以使用requestPeers方法来获得Peers设备列表，由于该方法第2个参数，需要一个PeerListListener对象。所以可以在
    fragment里面实现PeerListListener接口，并通过（PeerListListener）getFragmentMnanger().findFragmnetById(...)
    来获得requestPeers()方法的第2个参数。
     */
    @Override
    public void onPeersAvailable(WifiP2pDeviceList peerList)
    {
        // &&的意思是，如果以下2个条件都为true的时候，才会执行语句主体。
        if(progressDialog != null && progressDialog.isShowing())
        {
            progressDialog.dismiss();
        }

        peers.clear();
        /*
         这里是返回设备列表的关键。

         通过onPeersAvailable()方法，得到peerList，获得并把DeviceList装载给peers对象数组。
          */
        peers.addAll(peerList.getDeviceList());

        ((WeydioBeamAdapter) getListAdapter()).notifyDataSetChanged();

        if(peers.size() == 0)
        {
            return;
            // return的话，即就是返回该方法的顶部？
        }
    }

    /*
     Discover button点击后调用ProgressDialog的方法，由于是用fragment来更新UI，所以方法写在fragment里面。

     实际上就是调用ProgressDialog
      */
    public void onInitiateDiscover()
    {
        if(progressDialog != null && progressDialog.isShowing())
        {
            progressDialog.dismiss();
        }

        progressDialog = ProgressDialog.show(getActivity(),
                "Press back to cancel", "finding peers", true, true,
                new DialogInterface.OnCancelListener()
                {
                    @Override
                    public void onCancel(DialogInterface dialog)
                    {
                        // none.
                    }
                });
    }
}

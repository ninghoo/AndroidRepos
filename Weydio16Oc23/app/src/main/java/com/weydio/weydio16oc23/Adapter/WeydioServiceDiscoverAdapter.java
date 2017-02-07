package com.weydio.weydio16oc23.Adapter;

import android.content.Context;
import android.net.wifi.p2p.WifiP2pDevice;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.weydio.weydio16oc23.MainActivity;
import com.weydio.weydio16oc23.R;

import java.util.List;

/**
 * Created by Administrator on 2016/10/28.
 */

public class WeydioServiceDiscoverAdapter extends ArrayAdapter<WifiP2pDevice>
{
    private List<WifiP2pDevice> items;
    private MainActivity mMainActivity;
    private LayoutInflater vi;


    public WeydioServiceDiscoverAdapter(Context context, int textViewResourceId, List<WifiP2pDevice> objects)
    {
        super(context, textViewResourceId, objects);

        vi = LayoutInflater.from(context);

        items = objects;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        View v = convertView;

        if(v == null)
        {
            v = vi.inflate(R.layout.row_service, null);

        }

        WifiP2pDevice device = items.get(position);

        TextView serviceName = (TextView) v.findViewById(R.id.tv_ServiceName);

        serviceName.setText(device.deviceName);

//        if(device != null)
//        {
//            TextView peersName = (TextView) v.findViewById(R.id.tv_PeersName);
//
//            if(peersName != null)
//            {
//                peersName.setText(device.deviceName);
//            }
//        }

        return v;
    }
}

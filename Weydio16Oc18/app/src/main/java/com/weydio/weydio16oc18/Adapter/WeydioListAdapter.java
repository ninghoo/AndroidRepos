package com.weydio.weydio16oc18.Adapter;

import android.content.Context;
import android.net.wifi.p2p.WifiP2pDevice;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.weydio.weydio16oc18.MainActivity;
import com.weydio.weydio16oc18.R;

import java.util.List;

/**
 * Created by Administrator on 2016/10/19.
 */

public class WeydioListAdapter extends ArrayAdapter<WifiP2pDevice>
{
    private List<WifiP2pDevice> items;
    private MainActivity mMainActivity;
    private LayoutInflater vi;


    public WeydioListAdapter(Context context, int textViewResourceId, List<WifiP2pDevice> objects)
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
            v = vi.inflate(R.layout.row_devices, null);

        }

        WifiP2pDevice device = items.get(position);

        TextView peersName = (TextView) v.findViewById(R.id.tv_PeersName);

        peersName.setText(device.deviceName);

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

package com.weydio.weydio16oc16;

import android.content.Context;
import android.net.wifi.p2p.WifiP2pDevice;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.weydio.weydio16oc16.DeviceListFragment;
import com.weydio.weydio16oc16.R;

import java.util.List;

/**
 * Created by Administrator on 2016/10/17.
 */

public class WeydioBeamAdapter extends ArrayAdapter<WifiP2pDevice>
{
    private DeviceListFragment mDeviceListFragment;

    private List<WifiP2pDevice> items;

    // 自定义声明List<WifiP2pDevice>变量items，并重写Adapter数据结构。
    public WeydioBeamAdapter(Context context, int resource,
                             List<WifiP2pDevice> objects)
    {
        super(context, resource, objects);

        items = objects;
    }

    // 当你看到@Override标识时，你就知道，getView方法就是ArrayAdapter把数据填充到列表项的自带方法。
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        // 列表项。
        View v = convertView;

        if(v == null)
        {
            LayoutInflater vi = (LayoutInflater) mDeviceListFragment.getActivity()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            // 第2个参数是ViewGroup对象。
            v = vi.inflate(R.layout.row_devices, null);
        }

        // items.get(position)，就是单个列表项，开始渲染的初始步骤。
        WifiP2pDevice device = items.get(position);

        if(device != null)
        {
            TextView peersName = (TextView) v.findViewById(R.id.tv_PeersName);

            peersName.setText(device.deviceName);
        }

        return v;
    }
}

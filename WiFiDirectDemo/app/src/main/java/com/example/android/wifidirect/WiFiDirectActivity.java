/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.wifidirect;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.ActionListener;
import android.net.wifi.p2p.WifiP2pManager.Channel;
import android.net.wifi.p2p.WifiP2pManager.ChannelListener;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.android.wifidirect.DeviceListFragment.DeviceActionListener;

/**
 * An activity that uses WiFi Direct APIs to discover and connect with available
 * devices. WiFi Direct APIs are asynchronous and rely on callback mechanism
 * using interfaces to notify the application of operation success or failure.
 * The application should also register a BroadcastReceiver for notification of
 * WiFi state related events.
 */
public class WiFiDirectActivity extends Activity
//		implements ChannelListener,
//		DeviceActionListener
{

	public static final String TAG = "wifidirectdemo";
	private WifiP2pManager manager;
	// 声明一个WifiP2pManager对象。

	private boolean isWifiP2pEnabled = false;
	// 声明一个isWifiP2pEnabled开关，初始状态为false。

//	private boolean retryChannel = false;
	// 声明一个retryChannel开关，初始状态为false。

	private final IntentFilter intentFilter = new IntentFilter();
	// 实例化一个IntentFilter对象。

	private Channel channel;
	// 声明一个channel对象。

	private BroadcastReceiver receiver = null;
	// 声明一个BroadcatReceiver对象，初始值为null。

	/**
	 * @param isWifiP2pEnabled
	 *            the isWifiP2pEnabled to set
	 */
	public void setIsWifiP2pEnabled(boolean isWifiP2pEnabled)
	{
		// setIsWifiP2pEnabled方法，参数是isWifiP2pEnabled布尔开关。
		this.isWifiP2pEnabled = isWifiP2pEnabled;
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// add necessary intent values to be matched.
		// 隐式Intent。用于监听WifiP2pManager的状态。

		intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
		intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
		intentFilter
				.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
		intentFilter
				.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);

		manager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
		// 实例化WifiP2pManager对象，获取wifi系统服务。

		channel = manager.initialize(this, getMainLooper(), null);
		// channel对象，就是WifiP2pManager对象中，启动initialize()方法，返回的数据是一个channel对象。
	}

	/** register the BroadcastReceiver with the intent values to be matched */
	@Override
	public void onResume()
	{
		super.onResume();
		receiver = new WiFiDirectBroadcastReceiver(manager, channel, this);
		// 实例化receiver对象。

		registerReceiver(receiver, intentFilter);
		// 注册onResume()方法的receiver。即当程序正在运行当中的时候。
	}

	@Override
	public void onPause()
	{
		super.onPause();
		unregisterReceiver(receiver);
		// 当应用被防止在后台的时候，注销receiver。
	}

	public void onClick_Discover(View view)
	{
		if (!isWifiP2pEnabled)
		{
			// 当系统wifi并没有打开的时候，显示Toast信息，告知用户打开wifi。

			Toast.makeText(WiFiDirectActivity.this,
					R.string.p2p_off_warning, Toast.LENGTH_SHORT).show();
		}
		final DeviceListFragment fragment = (DeviceListFragment) getFragmentManager()
				.findFragmentById(R.id.frag_list);
		// DeviceListFragment对象，获取list界面。

		fragment.onInitiateDiscovery();
		// 这里首先出现ProgressDialog，当系统能够开始下面的discoverPeers程序时，ProgressDialog自然会消失。

		manager.discoverPeers(channel, new WifiP2pManager.ActionListener()
		{
			// WifiP2pManager对象开始发现对等设备。

			@Override
			public void onSuccess()
			{
				Toast.makeText(WiFiDirectActivity.this,
						"Discovery Initiated", Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onFailure(int reasonCode)
			{
				Toast.makeText(WiFiDirectActivity.this,
						"Discovery Failed : " + reasonCode,
						Toast.LENGTH_SHORT).show();
			}
		});
	}

	/**
	 * Remove all peers and clear all fields. This is called on
	 * BroadcastReceiver receiving a state change event.
	 *
	 * 当BroadcastReceiver接收到WiFi State转变的时候，移除所有的对等设备，清楚所有的数据。
	 */
//	public void resetData()
//	{
//		DeviceListFragment fragmentList = (DeviceListFragment) getFragmentManager()
//				.findFragmentById(R.id.frag_list);
//		// DeviceListFragment对象，获取list界面。
//
////		DeviceDetailFragment fragmentDetails = (DeviceDetailFragment) getFragmentManager()
////				.findFragmentById(R.id.frag_detail);
////		// DeviceDetailFragment对象，获取detail界面。
//
//		if (fragmentList != null)
//		{
//			fragmentList.clearPeers();
//		}
////		if (fragmentDetails != null)
////		{
////			fragmentDetails.resetViews();
////		}
////		// 当以上对象不为空的时候，清楚所有数据。
//	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.action_items, menu);
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())    // 通过item.getIntenId()方法，获取被点击对象的xml文件里的id，并做出相应。
		{
		case R.id.atn_direct_enable:
			if (manager != null && channel != null)
			{
				/*
			       当点击到的是atn_direct_enable，并且manger和channel不为空的时候,
			       跳转到系统的无线设置。
			    */

				// Since this is the system wireless settings activity, it's
				// not going to send us a result. We will be notified by
				// WiFiDeviceBroadcastReceiver instead.

				startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
			} else
			{
				Log.e(TAG, "channel or manager is null");
				// 否则打印出Log信息。
			}
			return true;
		// 这里return个true，到底有啥用。

		case R.id.atn_direct_discover:
			//  当点击的是wifiDiscover按键的时候，初始化wifi。

			if (!isWifiP2pEnabled)
			{
				// 当系统wifi并没有打开的时候，显示Toast信息，告知用户打开wifi。

				Toast.makeText(WiFiDirectActivity.this,
						R.string.p2p_off_warning, Toast.LENGTH_SHORT).show();
				return true;
			}
			final DeviceListFragment fragment = (DeviceListFragment) getFragmentManager()
					.findFragmentById(R.id.frag_list);
			// DeviceListFragment对象，获取list界面。

			fragment.onInitiateDiscovery();
			// 这里首先出现ProgressDialog，当系统能够开始下面的discoverPeers程序时，ProgressDialog自然会消失。

			manager.discoverPeers(channel, new WifiP2pManager.ActionListener()
			{
				// WifiP2pManager对象开始发现对等设备。

				@Override
				public void onSuccess()
				{
					Toast.makeText(WiFiDirectActivity.this,
							"Discovery Initiated", Toast.LENGTH_SHORT).show();
				}

				@Override
				public void onFailure(int reasonCode)
				{
					Toast.makeText(WiFiDirectActivity.this,
							"Discovery Failed : " + reasonCode,
							Toast.LENGTH_SHORT).show();
				}
			});
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

//	@Override
//	public void showDetails(WifiP2pDevice device)
//	{
////		DeviceDetailFragment fragment = (DeviceDetailFragment) getFragmentManager()
////				.findFragmentById(R.id.frag_detail);
////		fragment.showDetails(device);
//
//	}
//
//	@Override
//	public void connect(WifiP2pConfig config)
//	{
//		manager.connect(channel, config, new ActionListener()
//		{
//
//			@Override
//			public void onSuccess()
//			{
//				// WiFiDirectBroadcastReceiver will notify us. Ignore for now.
//			}
//
//			@Override
//			public void onFailure(int reason)
//			{
//				Toast.makeText(WiFiDirectActivity.this,
//						"Connect failed. Retry.", Toast.LENGTH_SHORT).show();
//			}
//		});
//	}
//
//	@Override
//	public void disconnect()
//	{
////		final DeviceDetailFragment fragment = (DeviceDetailFragment) getFragmentManager()
////				.findFragmentById(R.id.frag_detail);
////		fragment.resetViews();
//		manager.removeGroup(channel, new ActionListener()
//		{
//
//			@Override
//			public void onFailure(int reasonCode)
//			{
//				Log.d(TAG, "Disconnect failed. Reason :" + reasonCode);
//
//			}
//
//			@Override
//			public void onSuccess()
//			{
////				fragment.getView().setVisibility(View.GONE);
//			}
//
//		});
//	}
//
//	@Override
//	public void onChannelDisconnected()
//	{
//		// we will try once more
//		if (manager != null && !retryChannel)
//		{
//			Toast.makeText(this, "Channel lost. Trying again",
//					Toast.LENGTH_LONG).show();
//			resetData();
//			retryChannel = true;
//			manager.initialize(this, getMainLooper(), this);
//		} else
//		{
//			Toast.makeText(
//					this,
//					"Severe! Channel is probably lost premanently. Try Disable/Re-Enable P2P.",
//					Toast.LENGTH_LONG).show();
//		}
//	}
//
//	@Override
//	public void cancelDisconnect()
//	{
//
//		/*
//		 * A cancel abort request by user. Disconnect i.e. removeGroup if
//		 * already connected. Else, request WifiP2pManager to abort the ongoing
//		 * request
//		 */
//		if (manager != null)
//		{
//			final DeviceListFragment fragment = (DeviceListFragment) getFragmentManager()
//					.findFragmentById(R.id.frag_list);
//			if (fragment.getDevice() == null
//					|| fragment.getDevice().status == WifiP2pDevice.CONNECTED)
//			{
//				disconnect();
//			} else if (fragment.getDevice().status == WifiP2pDevice.AVAILABLE
//					|| fragment.getDevice().status == WifiP2pDevice.INVITED)
//			{
//
//				manager.cancelConnect(channel, new ActionListener()
//				{
//
//					@Override
//					public void onSuccess()
//					{
//						Toast.makeText(WiFiDirectActivity.this,
//								"Aborting connection", Toast.LENGTH_SHORT)
//								.show();
//					}
//
//					@Override
//					public void onFailure(int reasonCode)
//					{
//						Toast.makeText(
//								WiFiDirectActivity.this,
//								"Connect abort request failed. Reason Code: "
//										+ reasonCode, Toast.LENGTH_SHORT)
//								.show();
//					}
//				});
//			}
//		}
//	}
}

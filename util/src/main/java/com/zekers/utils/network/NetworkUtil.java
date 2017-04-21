package com.zekers.utils.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;

/**
 * com.bingo.sled.util.NetworkUtil.java
 * Function: 网络检查类
 * @author chenhui create 2013-7-9 下午4:24:16
 */

public class NetworkUtil {
	public static final String NOT_AVAILABLE_MESSAGE = "当前网络不可用，请检查你的网络设置。";
	public static final String MOBILE_AVAILABLE_MESSAGE = "在移动数据网络(3G等)下会耗费一定的流量，确认要继续？";

	/***
	 * 网络是否连通
	 *
	 * @param context
	 * @return
	 */
	public static boolean networkIsAvailable(Context context) {
		ConnectivityManager mConnectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
		if (networkIsWifi(context)) {
			return true;
		}
		if (networkIs3G(context)) {
			return true;
		}
		if (mNetworkInfo != null) {
			return mNetworkInfo.getState() == State.CONNECTED;
		}
		return false;
	}

	/***
	 * 检查网络是否连通，并给出提示
	 *
	 * @param context
	 * @return
	 */
	public static boolean checkNetwork(Context context) {
		/*
		 * if(NetworkUtil.networkIsAvailable(context) == false){
		 * AlertDialog.Builder alert = new AlertDialog.Builder(context);
		 * alert.setTitle("提示");
		 * alert.setMessage(NetworkUtil.NOT_AVAILABLE_MESSAGE);
		 * alert.setPositiveButton("关闭",null); alert.show(); return false; }
		 * return true;
		 */
		return NetworkUtil.networkIsAvailable(context);
	}

	/***
	 * WLAN网络是否连通
	 *
	 * @param context
	 * @return
	 */
	public static boolean networkIsWifi(Context context) {
		ConnectivityManager mConnectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mWiFiNetworkInfo = mConnectivityManager
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if (mWiFiNetworkInfo != null) {
			return mWiFiNetworkInfo.getState() == State.CONNECTED;
		}
		return false;
	}

	/***
	 * 移动数据网络是否连通
	 *
	 * @param context
	 * @return
	 */
	public static boolean networkIs3G(Context context) {
		ConnectivityManager mConnectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mMobileNetworkInfo = mConnectivityManager
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if (mMobileNetworkInfo != null) {
			return mMobileNetworkInfo.getState() == State.CONNECTED;
		}
		return false;
	}

	public static NetworkState getNetworkState(Context context) {
		NetworkState state = NetworkState.UNABLE;
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = connectivityManager.getActiveNetworkInfo();
		if (info != null) {
			if (info.getType() == ConnectivityManager.TYPE_WIFI) {
				state = NetworkState.WIFI;
			} else if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
				state = NetworkState.MOBILE;
			} else {
				state = NetworkState.WIFI;
			}
		}
		return state;
	}

	public enum NetworkState {
		UNABLE,
		WIFI,
		MOBILE
	}
}

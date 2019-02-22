package com.zom.media.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.IBinder;
import android.util.Log;


/**
 * Created by USERA on 2019/2/22.
 * 处理长连接
 */
public class NettyService extends Service {

    private String TAG = "NettyService";
    private NetWorkChangReceiver netWorkChangReceiver;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        registNetWorkChangReceiver();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregistNetWorkChangReceiver();
    }

    /**
     * 注册网络监听
     */
    private void registNetWorkChangReceiver(){
        netWorkChangReceiver = new NetWorkChangReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(netWorkChangReceiver, filter);
    }

    /**
     * 取消网络监听
     */
    private void unregistNetWorkChangReceiver(){
        unregisterReceiver(netWorkChangReceiver);
    }

    //网络状态监听
    public class NetWorkChangReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            NetworkInfo info = intent.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
            if (info != null) {
                if (info.isConnected() && info.isAvailable()) {
                    Log.i(TAG, "网络连上");
                } else {
                    Log.i(TAG, "网络断开");
                }
            }
        }
    }


}

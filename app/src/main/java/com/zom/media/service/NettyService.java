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

import com.zom.media.netty.client.NettyClient;
import com.zom.media.util.Config;


/**
 * Created by USERA on 2019/2/22.
 * 处理长连接
 */
public class NettyService extends Service {

    private String TAG = "NettyService";
    private NetWorkChangReceiver netWorkChangReceiver;
    private ActionReceiver actionReceiver;
    private NettyClient nettyClient = new NettyClient();

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG,"NettyService onCreate");

        registNetWorkChangReceiver();
        registActionReceiver();
        nettyStart();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregistNetWorkChangReceiver();
        unregistActionReceiver();
    }

    private void nettyStart(){
        if(isNetworkActive()){
            nettyClient.connect();
        }
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
                    nettyClient.connect();
                } else {
                    Log.i(TAG, "网络断开");
                    nettyClient.close();
                }
            }
        }
    }

    /**
     * 注册自定义类型监听
     */
    private void registActionReceiver(){
        actionReceiver = new ActionReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Config.ACTION_NETTY_LOST);
        registerReceiver(actionReceiver, filter);
    }

    /**
     * 取消网络监听
     */
    private void unregistActionReceiver(){
        unregisterReceiver(actionReceiver);
    }

    /**
     * 自定义action
     */
    public class ActionReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.d(TAG,"ActionReceiver：" + action);
            if(action.equals(Config.ACTION_NETTY_LOST)){
                nettyStart();
            }
        }
    }

    private boolean isNetworkActive(){
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null) {
            if (activeNetInfo.isConnected() && activeNetInfo.isAvailable()) {
                Log.i(TAG, "网络连上");
                return true;
            } else {
                Log.i(TAG, "网络断开");
                return false;
            }
        }
        return false;
    }

}

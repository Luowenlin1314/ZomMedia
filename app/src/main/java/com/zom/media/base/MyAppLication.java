package com.zom.media.base;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.zom.media.service.DownLoadService;
import com.zom.media.service.NettyService;
import com.zom.media.util.PreferenceUtils;

import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * 作者：Sky on 2018/4/28.
 * 用途：//TODO
 */

public class MyAppLication extends Application {

    private static MyAppLication myAppLication;

    @Override
    public void onCreate() {
        super.onCreate();
        myAppLication = this;
        PreferenceUtils.init(getContext());
        initRealm();
        nettyService();
        downLoadService();
    }

    public static Context getContext(){
        return myAppLication.getApplicationContext();
    }

    /**
     * 初始化realm
     */
    private void initRealm(){
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(config);
    }

    /**
     *  开启服务
     */
    private void nettyService(){
        Intent nettyService = new Intent(getApplicationContext(), NettyService.class);
        startService(nettyService);
    }

    /**
     *  开启服务
     */
    private void downLoadService(){
        Intent downLoadService = new Intent(getApplicationContext(), DownLoadService.class);
        startService(downLoadService);
    }

    public static String getUuid(){
        String localUuid = PreferenceUtils.getString("uuid", "");
        if(TextUtils.isEmpty(localUuid)){
            localUuid = UUID.randomUUID().toString().replaceAll("-","");
            PreferenceUtils.commitString("uuid",localUuid);
        }
        return localUuid;
    }


    public static void sendReceiver(String action){
        Log.e("sendReceiver","发送广播");
        Intent intent = new Intent(action);
        getContext().sendBroadcast(intent);
    }
}

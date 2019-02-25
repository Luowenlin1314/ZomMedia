package com.zom.media.base;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

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

    @Override
    public void onCreate() {
        super.onCreate();

        initRealm();
        nettyService();
//        downLoadService();
//        PreferenceUtils.init(getContext());
    }

    public static Context getContext(){
        return getContext();
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
            localUuid = UUID.randomUUID().toString();
            PreferenceUtils.commitString("uuid",localUuid);
        }
        return localUuid;
    }

}

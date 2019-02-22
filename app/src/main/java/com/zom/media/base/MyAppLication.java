package com.zom.media.base;

import android.app.Application;

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
    }

    /**
     * 初始化realm
     */
    private void initRealm(){
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(config);
    }

}

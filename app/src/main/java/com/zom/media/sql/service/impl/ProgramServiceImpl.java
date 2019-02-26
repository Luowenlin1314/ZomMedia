package com.zom.media.sql.service.impl;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zom.media.queue.DownLoadQueue;
import com.zom.media.sql.entity.CacheData;
import com.zom.media.sql.entity.Element;
import com.zom.media.sql.entity.Material;
import com.zom.media.sql.entity.Program;
import com.zom.media.sql.entity.vo.ElementVO;
import com.zom.media.sql.entity.vo.ProgramVO;
import com.zom.media.sql.service.ProgramService;
import com.zom.media.util.FastJsonutil;
import com.zom.media.util.GsonUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by USERA on 2019/2/22.
 */

public class ProgramServiceImpl implements ProgramService {

    private String TAG = "ProgramServiceImpl";

    @Override
    public int addProgram(ProgramVO programvo) {
        Realm realm = Realm.getDefaultInstance();
        List<CacheData> cacheDataList = realm.where(CacheData.class).findAll();
        int index = 1;
        if (cacheDataList != null && cacheDataList.size() > 0) {
            index = cacheDataList.size() + 1;
        }
        final int idIndex = index;
        final String json = GsonUtil.GsonString(programvo);
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                CacheData cacheData = realm.createObject(CacheData.class,idIndex);
                cacheData.setCreateTime(new Date());
                cacheData.setData(json);
                cacheData.setType(1);
            }
        });
        Log.e(TAG,"添加主题");

        DownLoadQueue.getInstance().addQueue(programvo);

        return 1;
    }

    @Override
    public int deleteAll() {
        Realm realm = Realm.getDefaultInstance();
        final RealmResults<CacheData> cacheDataList = realm.where(CacheData.class).equalTo("type", 1).findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                cacheDataList.deleteAllFromRealm();
            }
        });
        return 0;
    }


    @Override
    public ProgramVO getLastProgram() {
        Realm realm = Realm.getDefaultInstance();
        List<CacheData> cacheDataList = realm.where(CacheData.class).equalTo("type", 1).findAll();
        if (cacheDataList == null || cacheDataList.size() == 0) {
            return null;
        }
        ArrayList<CacheData> resultDatas = new ArrayList<>();
        resultDatas.addAll(cacheDataList);
        Collections.sort(resultDatas, new Comparator<CacheData>() {
            @Override
            public int compare(CacheData o1, CacheData o2) {
                if (o2.getCreateTime().compareTo(o1.getCreateTime()) < 0) {
                    return -1;
                }
                return 1;
            }
        });

        CacheData cacheData = resultDatas.get(0);
        String programJson = cacheData.getData();
        ProgramVO programvo = GsonUtil.GsonToBean(programJson, ProgramVO.class);
        return programvo;
    }


}

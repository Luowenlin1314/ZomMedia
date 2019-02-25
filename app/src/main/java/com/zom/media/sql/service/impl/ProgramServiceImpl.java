package com.zom.media.sql.service.impl;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
        return 1;
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
                if (o2.getCreateTime().compareTo(o1.getCreateTime()) > 0) {
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

    /**
     * 保存主题
     *
     * @param programvo
     */
    private void copyProgram(ProgramVO programvo) {
        Program program = new Program();
        program.setProgramId(programvo.getProgramId());
        program.setProgramName(programvo.getProgramName());
        program.setCreateTime(programvo.getCreateTime());
        program.setDuration(programvo.getDuration());
        program.setSize(programvo.getSize());
        program.setStatus(programvo.getStatus());
        program.setType(programvo.getType());
        program.setRemark(programvo.getRemark());
        Realm realm = Realm.getDefaultInstance();
        realm.copyFromRealm(program);
        realm.close();
    }

    /**
     * 保存元素
     *
     * @param programVO
     */
    private void copyElements(ProgramVO programVO) {
        List<ElementVO> elementList = programVO.getElementList();
        for (ElementVO elementVO : elementList) {
            Element element = new Element();
            element.setElementId(elementVO.getElementId());
            element.setProgramId(elementVO.getProgramId());
            element.setElementName(elementVO.getElementName());
            element.setLeft(elementVO.getLeft());
            element.setTop(elementVO.getTop());
            element.setWidth(elementVO.getWidth());
            element.setHeight(elementVO.getHeight());
            element.setDuration(elementVO.getDuration());
            element.setEffect(elementVO.getEffect());
            element.setType(elementVO.getType());
            element.setBackground(elementVO.getBackground());
            element.setMode(elementVO.getMode());
            element.setRemark(elementVO.getRemark());
            Realm realm = Realm.getDefaultInstance();
            realm.copyFromRealm(element);
            realm.close();
        }
    }

    /**
     * 保存元素素材
     *
     * @param elementVO
     */
    private void copyMaterials(ElementVO elementVO) {
        List<Material> materialList = elementVO.getMaterialList();
        for (Material material : materialList) {
            Realm realm = Realm.getDefaultInstance();
            realm.copyFromRealm(material);
            realm.close();
        }
    }

}

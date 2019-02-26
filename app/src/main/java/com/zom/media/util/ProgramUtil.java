package com.zom.media.util;

import android.app.Activity;
import android.content.Context;
import android.support.percent.PercentLayoutHelper;
import android.support.percent.PercentRelativeLayout;
import android.view.View;
import android.widget.ImageView;

import com.zom.media.R;
import com.zom.media.enums.ElementType;
import com.zom.media.sql.entity.CacheData;
import com.zom.media.sql.entity.Material;
import com.zom.media.sql.entity.vo.ElementVO;
import com.zom.media.sql.entity.vo.ProgramVO;
import com.zom.media.widget.ContainerView;
import com.zom.media.widget.YTVideoView;
import com.zom.media.widget.ZomImageView;
import com.zom.media.widget.ZomNoneView;
import com.zom.media.widget.ZomVideoView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by USERA on 2019/2/22.
 */

public class ProgramUtil {

    public static ContainerView buildProgramView(Context context,ProgramVO programVO){
        ContainerView containerView = new ContainerView(context);
        if(programVO == null){
            ZomNoneView zomNoneView = buildNoneView(context);
            containerView.addView(zomNoneView);
            return containerView;
        }
        List<ElementVO> elementVOList = programVO.getElementList();
        Collections.sort(elementVOList, new Comparator<ElementVO>() {
            @Override
            public int compare(ElementVO o1, ElementVO o2) {
                if (o2.getEindex().compareTo(o1.getEindex()) < 0) {
                    return -1;
                }
                return 1;
            }
        });
        for (ElementVO elementVO : elementVOList) {
            Integer type = elementVO.getEtype();
            if(type.equals(ElementType.IMAGE.getType())){
                ZomImageView zomImageView = buildImageView(context,elementVO);
                containerView.addView(zomImageView);
            }else if(type.equals(ElementType.VIDEO.getType())){
                YTVideoView zomVideoView = buildVideoView(context,elementVO);
                containerView.addView(zomVideoView);
            }
        }

        return containerView;
    }

    /**
     * 构建noneView
     * @return
     */
    private static ZomNoneView buildNoneView(Context context){
        ZomNoneView zomNoneView = new ZomNoneView(context);
        PercentRelativeLayout.LayoutParams params = new PercentRelativeLayout.LayoutParams(0,0);
        PercentLayoutHelper.PercentLayoutInfo percentLayoutInfo = params.getPercentLayoutInfo();
        percentLayoutInfo.widthPercent = 1f;
        percentLayoutInfo.heightPercent = 1f;
        percentLayoutInfo.leftMarginPercent = 0f;
        percentLayoutInfo.topMarginPercent = 0f;
        zomNoneView.setLayoutParams(params);
        return zomNoneView;
    }

    /**
     * 构建imageView
     * @param elementVO
     * @return
     */
    private static ZomImageView buildImageView(Context context, ElementVO elementVO){
        List<Material> materialList = elementVO.getMaterialList();
        ZomImageView zomImageView = new ZomImageView(context);
        PercentRelativeLayout.LayoutParams params = new PercentRelativeLayout.LayoutParams(0,0);
        PercentLayoutHelper.PercentLayoutInfo percentLayoutInfo = params.getPercentLayoutInfo();
        percentLayoutInfo.widthPercent = elementVO.getEwidth() / 100f;
        percentLayoutInfo.heightPercent = elementVO.getEheight() / 100f;
        percentLayoutInfo.leftMarginPercent = elementVO.getEleft() / 100f;
        percentLayoutInfo.topMarginPercent = elementVO.getEtop() / 100f;
        zomImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        zomImageView.setLayoutParams(params);

        ArrayList<String> fileList = new ArrayList<>();
        ArrayList<Long> durations = new ArrayList<>();
        for (Material material : materialList) {
            fileList.add(material.getMaterialName());
            durations.add(material.getDuration());
        }
        zomImageView.setFileList(fileList);
        zomImageView.setDuration(durations);
        return zomImageView;
    }


    /**
     * 构建videoView
     * @param elementVO
     * @return
     */
    private static YTVideoView buildVideoView(Context context, ElementVO elementVO){
        List<Material> materialList = elementVO.getMaterialList();
        YTVideoView zomVideoView = new YTVideoView(context);
        PercentRelativeLayout.LayoutParams params = new PercentRelativeLayout.LayoutParams(0,0);
        PercentLayoutHelper.PercentLayoutInfo percentLayoutInfo = params.getPercentLayoutInfo();
        percentLayoutInfo.widthPercent = elementVO.getEwidth() / 100f;
        percentLayoutInfo.heightPercent = elementVO.getEheight() / 100f;
        percentLayoutInfo.leftMarginPercent = elementVO.getEleft() / 100f;
        percentLayoutInfo.topMarginPercent = elementVO.getEtop() / 100f;
        zomVideoView.setLayoutParams(params);

        ArrayList<String> fileList = new ArrayList<>();
        for (Material material : materialList) {
            fileList.add(material.getMaterialName());
        }
        zomVideoView.setFileList(fileList);
        return zomVideoView;
    }

}

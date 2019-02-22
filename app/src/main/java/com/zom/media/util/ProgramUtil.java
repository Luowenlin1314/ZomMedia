package com.zom.media.util;

import android.app.Activity;
import android.content.Context;
import android.support.percent.PercentLayoutHelper;
import android.support.percent.PercentRelativeLayout;
import android.view.View;

import com.zom.media.R;
import com.zom.media.enums.ElementType;
import com.zom.media.sql.entity.CacheData;
import com.zom.media.sql.entity.Material;
import com.zom.media.sql.entity.vo.ElementVO;
import com.zom.media.sql.entity.vo.ProgramVO;
import com.zom.media.widget.ZomImageView;
import com.zom.media.widget.ZomVideoView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by USERA on 2019/2/22.
 */

public class ProgramUtil {

    public static void buildProgramView(Context context,ProgramVO programVO){
        View container = View.inflate(context, R.layout.activity_main,null);
        PercentRelativeLayout prl = (PercentRelativeLayout) container.findViewById(R.id.root);
        List<ElementVO> elementVOList = programVO.getElementList();
        Collections.sort(elementVOList, new Comparator<ElementVO>() {
            @Override
            public int compare(ElementVO o1, ElementVO o2) {
                if (o2.getIndex().compareTo(o1.getIndex()) < 0) {
                    return -1;
                }
                return 1;
            }
        });
        for (ElementVO elementVO : elementVOList) {
            Integer type = elementVO.getType();
            if(type.equals(ElementType.IMAGE)){
                ZomImageView zomImageView = buildImageView(context,elementVO);
                prl.addView(zomImageView);
            }else if(type.equals(ElementType.VIDEO)){
                ZomVideoView zomVideoView = buildVideoView(context,elementVO);
                prl.addView(zomVideoView);
            }
        }

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
        percentLayoutInfo.widthPercent = elementVO.getWidth() / 100f;
        percentLayoutInfo.heightPercent = elementVO.getHeight() / 100f;
        percentLayoutInfo.leftMarginPercent = elementVO.getLeft() / 100f;
        percentLayoutInfo.topMarginPercent = elementVO.getTop() / 100f;
        zomImageView.setLayoutParams(params);

        ArrayList<String> fileList = new ArrayList<>();
        ArrayList<Long> durations = new ArrayList<>();
        for (Material material : materialList) {
            fileList.add(material.getRelativePath());
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
    private static ZomVideoView buildVideoView(Context context, ElementVO elementVO){
        List<Material> materialList = elementVO.getMaterialList();
        ZomVideoView zomVideoView = new ZomVideoView(context);
        PercentRelativeLayout.LayoutParams params = new PercentRelativeLayout.LayoutParams(0,0);
        PercentLayoutHelper.PercentLayoutInfo percentLayoutInfo = params.getPercentLayoutInfo();
        percentLayoutInfo.widthPercent = elementVO.getWidth() / 100f;
        percentLayoutInfo.heightPercent = elementVO.getHeight() / 100f;
        percentLayoutInfo.leftMarginPercent = elementVO.getLeft() / 100f;
        percentLayoutInfo.topMarginPercent = elementVO.getTop() / 100f;
        zomVideoView.setLayoutParams(params);

        ArrayList<String> fileList = new ArrayList<>();
        for (Material material : materialList) {
            fileList.add(material.getRelativePath());
        }
        zomVideoView.setFileList(fileList);
        return zomVideoView;
    }

}

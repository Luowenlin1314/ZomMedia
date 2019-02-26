package com.zom.media.sql.entity.vo;

import com.zom.media.sql.entity.Element;
import com.zom.media.sql.entity.Material;

import java.util.List;

import io.realm.RealmModel;

/**
 * Created by USERA on 2019/2/22.
 */

public class ElementVO {

    private Long elementId;

    private Long programId;

    private String elementName;

    private Integer etype;

    private String background;

    private Integer eleft;

    private Integer etop;

    private Integer ewidth;

    private Integer eheight;

    private Integer eindex;

    private Integer duration;

    private Integer effect;

    private Integer emode;

    private String remark;

    //元素素材列表
    private List<Material> materialList;

    public Long getElementId() {
        return elementId;
    }

    public void setElementId(Long elementId) {
        this.elementId = elementId;
    }

    public Long getProgramId() {
        return programId;
    }

    public void setProgramId(Long programId) {
        this.programId = programId;
    }

    public String getElementName() {
        return elementName;
    }

    public void setElementName(String elementName) {
        this.elementName = elementName;
    }

    public Integer getEtype() {
        return etype;
    }

    public void setEtype(Integer etype) {
        this.etype = etype;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public Integer getEleft() {
        return eleft;
    }

    public void setEleft(Integer eleft) {
        this.eleft = eleft;
    }

    public Integer getEtop() {
        return etop;
    }

    public void setEtop(Integer etop) {
        this.etop = etop;
    }

    public Integer getEwidth() {
        return ewidth;
    }

    public void setEwidth(Integer ewidth) {
        this.ewidth = ewidth;
    }

    public Integer getEheight() {
        return eheight;
    }

    public void setEheight(Integer eheight) {
        this.eheight = eheight;
    }

    public Integer getEindex() {
        return eindex;
    }

    public void setEindex(Integer eindex) {
        this.eindex = eindex;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getEffect() {
        return effect;
    }

    public void setEffect(Integer effect) {
        this.effect = effect;
    }

    public Integer getEmode() {
        return emode;
    }

    public void setEmode(Integer emode) {
        this.emode = emode;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<Material> getMaterialList() {
        return materialList;
    }

    public void setMaterialList(List<Material> materialList) {
        this.materialList = materialList;
    }
}

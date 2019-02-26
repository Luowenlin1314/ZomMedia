package com.zom.media.sql.entity.vo;

import com.zom.media.sql.entity.Program;

import java.util.Date;
import java.util.List;

import io.realm.RealmModel;

/**
 * Created by USERA on 2019/2/22.
 */

public class ProgramVO {

    private Long programId;

    private Long corpId;

    private String programName;

    private Integer ptype;

    private Double psize;

    private Integer duration;

    private Integer status;

    private String remark;

    private Date createTime;

    //主题元素
    private List<ElementVO> elementList;

    public List<ElementVO> getElementList() {
        return elementList;
    }

    public void setElementList(List<ElementVO> elementList) {
        this.elementList = elementList;
    }

    public Long getProgramId() {
        return programId;
    }

    public void setProgramId(Long programId) {
        this.programId = programId;
    }

    public Long getCorpId() {
        return corpId;
    }

    public void setCorpId(Long corpId) {
        this.corpId = corpId;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public Integer getPtype() {
        return ptype;
    }

    public void setPtype(Integer ptype) {
        this.ptype = ptype;
    }

    public Double getPsize() {
        return psize;
    }

    public void setPsize(Double psize) {
        this.psize = psize;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}

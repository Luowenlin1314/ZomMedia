package com.zom.media.netty.pro.entity;

/**
 * Created by USERA on 2019/2/14.
 * 身份校验数据
 */
public class Identity {

    private String corpCode;

    private String deviceCode;

    private Integer netType;

    private String netIP;

    private String netMac;

    private String resolution;

    private String version;

    public String getCorpCode() {
        return corpCode;
    }

    public void setCorpCode(String corpCode) {
        this.corpCode = corpCode;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public Integer getNetType() {
        return netType;
    }

    public void setNetType(Integer netType) {
        this.netType = netType;
    }

    public String getNetIP() {
        return netIP;
    }

    public void setNetIP(String netIP) {
        this.netIP = netIP;
    }

    public String getNetMac() {
        return netMac;
    }

    public void setNetMac(String netMac) {
        this.netMac = netMac;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}

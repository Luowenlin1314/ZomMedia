package com.zom.media.entity;

/**
 * Created by USERA on 2019/2/25.
 */

public class FtpResult {

    private boolean flag;
    private long time;
    private double size;

    public FtpResult(boolean flag,long time,double size){
        this.flag = flag;
        this.time = time;
        this.size = size;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }
}

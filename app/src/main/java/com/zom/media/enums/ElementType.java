package com.zom.media.enums;

/**
 * Created by USERA on 2019/2/18.
 * 元素类型
 */
public enum ElementType {

    IMAGE(1,"图片"),
    VIDEO(2,"视频");

    private Integer type;
    private String msg;

    ElementType(Integer type, String msg) {
        this.type = type;
        this.msg = msg;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

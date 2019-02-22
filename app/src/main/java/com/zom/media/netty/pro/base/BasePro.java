package com.zom.media.netty.pro.base;


import com.zom.media.util.FastJsonutil;

/**
 * Created by USERA on 2019/2/14.
 */
public class BasePro {

    //协议名称
    protected Integer name;
    //协议数据
    protected Object data;

    public Integer getName() {
        return name;
    }

    public Object getData() {
        return data;
    }

    /**
     * 获取当前对象json数据
     * @return
     */
    public String toJson(){
        return FastJsonutil.convertObjectToJSON(this);
    }



}

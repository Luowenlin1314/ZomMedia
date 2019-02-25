package com.zom.media.netty.pro.constant;

/**
 * Created by USERA on 2019/2/14.
 * 协议名称
 */
public interface ProtocolCons {

    /********终端相关************/
    //终端身份校验
    Integer TERMINAL_IDENTITY = 1001;
    //终端身份成功
    Integer TERMINAL_IDENTITY_FAIL = 10010;
    //终端身份失败
    Integer TERMINAL_IDENTITY_SUCCESS = 10011;

    /********主题相关************/
    //更新主题
    Integer PROGRAM_UPDATE = 2001;

}

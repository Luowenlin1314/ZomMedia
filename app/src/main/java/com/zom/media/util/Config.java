package com.zom.media.util;

/**
 * Created by USERA on 2019/2/22.
 */

public interface Config {

    int RPOGRAM_NEXT = 1;

    /**FTP**/
    String FTP_HOST = "";
    String FTP_PORT = "";
    String FTP_PWD = "";

    /**intent aciont**/
    //客户端断开连接
    String ACTION_NETTY_LOST = "zom.action.netty.lost";
}

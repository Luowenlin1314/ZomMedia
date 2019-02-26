package com.zom.media.util;

/**
 * Created by USERA on 2019/2/22.
 */

public interface Config {

    int RPOGRAM_NEXT = 1;

    /**FTP**/
    String FTP_HOST = "172.16.1.103";
    String FTP_PORT = "21";
    String FTP_PUSER = "zom";
    String FTP_PWD = "root";

    /**intent aciont**/
    //客户端断开连接
    String ACTION_NETTY_LOST = "zom.action.netty.lost";
    //主题更新
    String ACTION_PROGRAM_UPDATE = "zom.action.program.update";
}

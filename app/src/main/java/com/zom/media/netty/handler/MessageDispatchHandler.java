package com.zom.media.netty.handler;

import com.zom.media.netty.pro.base.BasePro;
import com.zom.media.util.FastJsonutil;

import io.netty.channel.ChannelHandlerContext;

/**
 * Created by USERA on 2019/2/22.
 */

public class MessageDispatchHandler {

    /**
     * 消息接收
     * @param ctx
     * @param msg
     */
    public void messageReceive(ChannelHandlerContext ctx, String msg){
        try {
            BasePro basePro = FastJsonutil.toBean(msg, BasePro.class);
            Integer name = basePro.getName();


        }catch (Exception e){

        }
    }

}

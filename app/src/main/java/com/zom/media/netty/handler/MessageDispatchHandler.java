package com.zom.media.netty.handler;

import android.util.Log;

import com.zom.media.base.MyAppLication;
import com.zom.media.netty.handler.ibs.ProgramHandler;
import com.zom.media.netty.pro.base.BasePro;
import com.zom.media.netty.pro.constant.ProtocolCons;
import com.zom.media.util.GsonUtil;

import io.netty.channel.ChannelHandlerContext;

/**
 * Created by USERA on 2019/2/22.
 */

public class MessageDispatchHandler {

    private String TAG = "MessageDispatchHandler";
    private ProgramHandler programHandler = new ProgramHandler();

    /**
     * 消息接收
     * @param ctx
     * @param msg
     */
    public void messageReceive(ChannelHandlerContext ctx, String msg){
        try {
            BasePro basePro = GsonUtil.GsonToBean(msg,BasePro.class);
            Integer name = basePro.getName();
            Log.d(TAG,"messageReceive：" + name);

            if(name.equals(ProtocolCons.TERMINAL_IDENTITY_SUCCESS)){
                //校验成功
            }else if(name.equals(ProtocolCons.TERMINAL_IDENTITY_FAIL)){
                //校验失败
                ctx.close();
            }else if(name.equals(ProtocolCons.PROGRAM_UPDATE)){
                programHandler.handler(basePro.getData());
            }

        }catch (Exception e){
            Log.e(TAG,"messageReceive：" + e.getMessage());
        }
    }

}

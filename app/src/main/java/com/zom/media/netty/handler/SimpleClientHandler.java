package com.zom.media.netty.handler;

import android.util.Log;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

/**
 * Created by USERA on 2019/2/22.
 * 客户端消息处理
 */

public class SimpleClientHandler extends ChannelHandlerAdapter {

    private String TAG = "SimpleClientHandler";

    private MessageDispatchHandler messageDispatchHandler = new MessageDispatchHandler();

    /**
     * 客户端和服务端TCP链路建立成功后调用此方法
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        Log.d(TAG, "建立连接：" + ctx.channel());
    }

    /**
     * 服务端返回应答消息调用此方法
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);

        try{
            String data = ((ByteBuf) msg).toString(CharsetUtil.UTF_8);
            Log.d(TAG, "收到服务端" + ctx.channel().remoteAddress() + "的消息：" + data);

            messageDispatchHandler.messageReceive(ctx,data);

        }finally {
            ReferenceCountUtil.release(msg);
        }

    }

    /**
     * 错误回调
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        Log.e(TAG, "exceptionCaught：" + cause.getMessage());
        ctx.close();
    }

}

package com.zom.media.netty.handler;

import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.zom.media.base.MyAppLication;
import com.zom.media.netty.pro.base.BasePro;
import com.zom.media.netty.pro.constant.ProtocolCons;
import com.zom.media.netty.pro.entity.Identity;
import com.zom.media.util.Config;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
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
        //发送协议
        sendIdentityPro(ctx);
    }

    /**
     * 服务端返回应答消息调用此方法
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try{
            String data = ((ByteBuf) msg).toString(CharsetUtil.UTF_8);
            Log.d(TAG, "收到服务端" + ctx.channel().remoteAddress() + "的消息：" + data);

            messageDispatchHandler.messageReceive(ctx,data);

        }finally {
//            ReferenceCountUtil.release(msg);
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
        super.exceptionCaught(ctx,cause);
        Log.e(TAG, "exceptionCaught：" + cause.getMessage());
//        sendToNettyService();
    }

    /**
     * 错误处理
     */
    private void sendToNettyService(){
        Intent intent = new Intent(Config.ACTION_NETTY_LOST);
        MyAppLication.getContext().sendBroadcast(intent);
    }

    /**
     * 身份验证协议
     * @param ctx
     */
    private void sendIdentityPro(ChannelHandlerContext ctx){
        BasePro basePro = new BasePro();
        basePro.setName(ProtocolCons.TERMINAL_IDENTITY);
        Identity identity = new Identity();
        identity.setVersion(Build.VERSION.RELEASE);
        identity.setNetIP("test");
        identity.setNetMac("test");
        identity.setNetType(1);
        identity.setCorpCode("aaaa");
        identity.setResolution("test");
        identity.setDeviceCode(MyAppLication.getUuid());
        basePro.setData(identity);
        ByteBuf msg = Unpooled.unreleasableBuffer(Unpooled.copiedBuffer(basePro.toJson(), CharsetUtil.UTF_8));
        ctx.writeAndFlush(msg.duplicate()).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
    }
}

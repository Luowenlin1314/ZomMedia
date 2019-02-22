package com.zom.media.netty.client;

import com.zom.media.netty.handler.HeartbeatHandler;
import com.zom.media.netty.handler.SimpleClientHandler;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Created by USERA on 2019/2/22.
 * 客服端
 */

public class NettyClient {

    private static final String HOST = "172.16.1.103";
    private static final int PORT = 10123;

    private EventLoopGroup worker;

    /**
     * 连接
     */
    public void connect() {
        close();
        worker = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(worker);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new SimpleClientHandler());

                    ch.pipeline().addLast(new HeartbeatHandler());
                }
            });
            ChannelFuture channelFuture = b.connect(HOST, PORT).sync();
        } catch (Exception e){
            worker.shutdownGracefully();
        }
    }

    /**
     * 关闭
     */
    public void close(){
        if(worker != null){
            if(!worker.isShutdown()){
                worker.shutdownGracefully();
            }
        }
        worker = null;
    }

}

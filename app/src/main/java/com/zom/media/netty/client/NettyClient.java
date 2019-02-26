package com.zom.media.netty.client;

import android.util.Log;

import com.zom.media.netty.handler.HeartbeatHandler;
import com.zom.media.netty.handler.SimpleClientHandler;

import java.util.concurrent.TimeUnit;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * Created by USERA on 2019/2/22.
 * 客服端
 */

public class NettyClient {

    private static final String HOST = "172.16.1.103";
    private static final int PORT = 10123;

    private ChannelFuture channelFuture;
    private EventLoopGroup worker;

    /**
     * 连接
     */
    public synchronized void connect() {
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
                    ChannelPipeline pipeline = ch.pipeline();
                    pipeline.addLast(new IdleStateHandler(0, 10, 0, TimeUnit.SECONDS));

                    pipeline.addLast(new HeartbeatHandler());

                    pipeline.addLast(new SimpleClientHandler());
                }
            });
            channelFuture = b.connect(HOST, PORT).sync();
        } catch (Exception e) {
            worker.shutdownGracefully();
        }
    }

    /**
     * 关闭
     */
    public void close() {
        if (worker != null) {
            if (!worker.isShutdown()) {
                worker.shutdownGracefully();
            }
        }
        worker = null;
    }

}

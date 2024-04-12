package com.jiakoukou.nettylearn.source;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * Allocator与RevBuf_Allocator的区别
 *
 * 前者控制的是ChannelHandlerContext显示分配的ByteBuf  ByteBuf buf = ctx.alloc().buffer();
 * 后者控制的是使用handler时，传过来的Object msg，如果这里没有认为改动，实际上时隐式传过来的ByteBuf
 *
 * 这两个参数控制ByeBuf分配时是使用池化还是非池化，使用直接内存还是堆内存
 */
@Slf4j
public class TestByteBuf {
    public static void main(String[] args) {
        new ServerBootstrap()
                .group(new NioEventLoopGroup())
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) {
                        ch.pipeline().addLast(new LoggingHandler());
                        ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//                                ByteBuf buf = ctx.alloc().buffer();
//                                log.debug("alloc buf {}", buf);

                                log.debug("receive buf {}", msg);
                                System.out.println("");
                            }
                        });
                    }
                }).bind(8080);
    }
}

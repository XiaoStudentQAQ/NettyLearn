package com.jiakoukou.nettylearn.handler;

import com.jiakoukou.nettylearn.message.PingMessage;
import com.jiakoukou.nettylearn.message.PongMessage;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ChannelHandler.Sharable
public class PingRequestMessageHandler extends SimpleChannelInboundHandler<PingMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, PingMessage pingMessage) throws Exception {
        log.info("收到来自[" + channelHandlerContext.channel().remoteAddress() + "]的心跳");
        channelHandlerContext.writeAndFlush(new PongMessage());
    }
}

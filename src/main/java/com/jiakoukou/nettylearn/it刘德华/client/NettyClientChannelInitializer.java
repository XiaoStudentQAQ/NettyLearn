package com.jiakoukou.nettylearn.it刘德华.client;

import com.jiakoukou.nettylearn.it刘德华.handler.ClientHeartbeatHandler;
import com.jiakoukou.nettylearn.it刘德华.handler.ResponseChannelHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 这个实例中我没有使用注解方式，因为心跳处理逻辑会引用重新连接服务的bean，如果使用注解方式会出现循环依赖错误
 **/
@Component
@Slf4j
public class NettyClientChannelInitializer extends ChannelInitializer {

    private final ResponseChannelHandler responseChannelHandler;

    private final ClientHeartbeatHandler clientChannelHandler;

    public NettyClientChannelInitializer(ResponseChannelHandler responseChannelHandler,
                                         ClientHeartbeatHandler clientChannelHandler) {
        this.responseChannelHandler = responseChannelHandler;
        this.clientChannelHandler = clientChannelHandler;
    }

    @Override
    protected void initChannel(Channel channel) {
        channel.pipeline().addLast("decoder", new StringDecoder(CharsetUtil.UTF_8));
        channel.pipeline().addLast("encoder", new StringEncoder(CharsetUtil.UTF_8));
        channel.pipeline().addLast("responseChannelHandler", responseChannelHandler);
        channel.pipeline().addLast("clientChannelHandler", clientChannelHandler);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("error message {}", cause.getMessage(), cause);
        super.exceptionCaught(ctx, cause);
    }
}

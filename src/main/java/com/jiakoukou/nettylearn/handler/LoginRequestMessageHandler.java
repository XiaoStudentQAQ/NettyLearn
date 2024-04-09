package com.jiakoukou.nettylearn.handler;

import com.jiakoukou.nettylearn.message.LoginRequestMessage;
import com.jiakoukou.nettylearn.message.LoginResponseMessage;
import com.jiakoukou.nettylearn.server.service.UserServiceFactory;
import com.jiakoukou.nettylearn.server.session.SessionFactory;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author JiaKouKou
 * @create 2024-04-09 23:39
 * @Description 将登录请求消息处理抽离处理，因为这里是可以复用的
 */
@ChannelHandler.Sharable
public class LoginRequestMessageHandler extends SimpleChannelInboundHandler<LoginRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestMessage msg) throws Exception {
        String username = msg.getUsername();
        String password = msg.getPassword();
        boolean login = UserServiceFactory.getUserService().login(username, password);
        if (login) {
            // 登录成功后用户和channel绑定了
            SessionFactory.getSession().bind(ctx.channel(), username);
            ctx.writeAndFlush(new LoginResponseMessage(true, "登录成功"));
        } else {
            ctx.writeAndFlush(new LoginResponseMessage(false, "用户名或密码不正确"));
        }
    }
}

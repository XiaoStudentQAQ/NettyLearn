package com.jiakoukou.nettylearn.advance.c2;

import com.jiakoukou.nettylearn.message.LoginRequestMessage;
import com.jiakoukou.nettylearn.protocol.MessageCodec;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LoggingHandler;

/**
 * 自定义协议测试
 */
public class TestMessageCodec {
    public static void main(String[] args) throws Exception {
        EmbeddedChannel channel = new EmbeddedChannel(
                new LoggingHandler(),
                // 为什么我们规定了读取长度，还会出现粘包半包问题？
                // 因为网卡规定，发送或接收的数据是有限制长度的，长度超过了，会自动给你截取，分成多次发送或接收，所以可能出现尿包和半包问题
                // 即协议和硬件的限制最大数据长度限制和算法处理都会导致粘包、半包问题
                // 解决粘包、半包问题；最大长度，长度偏移，长度占用字节，长度调整，剥离字节数
                // 长度偏移=整个数-魔数长度=16-12
                new LengthFieldBasedFrameDecoder(
                        1024, 12, 4, 0, 0),
                // 数据不完整不往下传播（不传播给下一个handler）
                new MessageCodec()
        );
        // encode
        LoginRequestMessage message = new LoginRequestMessage("zhangsan", "123", "张三");
//        channel.writeOutbound(message);
        // decode
        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
        new MessageCodec().encode(null, message, buf);

        // 模拟半包现象
        ByteBuf s1 = buf.slice(0, 100);
        ByteBuf s2 = buf.slice(100, buf.readableBytes() - 100);
        s1.retain(); // 引用计数 2
        channel.writeInbound(s1); // release 1
        channel.writeInbound(s2);
    }
}

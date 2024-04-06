package com.jiakoukou.nettylearn.advance.c1;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * 通常情况下，自定义协议，一般都是前几个字节表示内容的长度，然后根据长度读取内容。
 */
public class TestLengthFieldDecoder {
    public static void main(String[] args) {
        EmbeddedChannel channel = new EmbeddedChannel(
                // 最大长度，长度偏移，长度占用字节，长度调整，剥离字节数
                new LengthFieldBasedFrameDecoder(
                        1024, 0, 4, 1,4),
                new LoggingHandler(LogLevel.DEBUG)
        );

        //  4 个字节的内容长度， 实际内容
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
        send(buffer, "Hello, world");
        send(buffer, "Hi!");
        channel.writeInbound(buffer);
    }

    /**
     * 将指定的内容发送到提供的ByteBuf缓冲区中。
     *
     * @param buffer 用于写入数据的ByteBuf缓冲区。
     * @param content 需要发送的实际内容，字符串形式。
     */
    private static void send(ByteBuf buffer, String content) {
        // 将字符串转换为字节数组
        byte[] bytes = content.getBytes();
        // 获取实际内容的长度
        int length = bytes.length;
        // 写入内容长度
        buffer.writeInt(length);
        // 假设在现在内容的长度后面加入版本信息
        buffer.writeByte(1);
        // 将实际内容写入缓冲区
        buffer.writeBytes(bytes);
    }
}


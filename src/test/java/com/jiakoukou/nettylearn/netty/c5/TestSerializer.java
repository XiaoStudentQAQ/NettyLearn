package com.jiakoukou.nettylearn.netty.c5;

import com.jiakoukou.nettylearn.config.Config;
import com.jiakoukou.nettylearn.message.LoginRequestMessage;
import com.jiakoukou.nettylearn.message.Message;
import com.jiakoukou.nettylearn.protocol.MessageCodecSharable;
import com.jiakoukou.nettylearn.protocol.Serializer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.logging.LoggingHandler;

public class TestSerializer {

    public static void main(String[] args)  {
        MessageCodecSharable CODEC = new MessageCodecSharable();
        LoggingHandler LOGGING = new LoggingHandler();
        /**
         * EmbeddedChannel是Netty框架中的一种特殊的Channel实现，
         * 主要用于测试、模拟网络通信以及单元测试场景。
         * 它在内存中模拟了一个完整的网络通道，
         * 使得开发者可以在不需要实际网络连接的情况下对网络通信逻辑进行测试。
         */
        EmbeddedChannel channel = new EmbeddedChannel(LOGGING, CODEC, LOGGING);

        LoginRequestMessage message = new LoginRequestMessage("zhangsan", "123", null);
//        channel.writeOutbound(message);
        ByteBuf buf = messageToByteBuf(message);
        channel.writeInbound(buf);
    }

    public static ByteBuf messageToByteBuf(Message msg) {
        int algorithm = Config.getSerializerAlgorithm().ordinal();
        ByteBuf out = ByteBufAllocator.DEFAULT.buffer();
        out.writeBytes(new byte[]{1, 2, 3, 4});
        out.writeByte(1);
        out.writeByte(algorithm);
        out.writeByte(msg.getMessageType());
        out.writeInt(msg.getSequenceId());
        out.writeByte(0xff);
        byte[] bytes = Serializer.Algorithm.values()[algorithm].serialize(msg);
        out.writeInt(bytes.length);
        out.writeBytes(bytes);
        return out;
    }
}

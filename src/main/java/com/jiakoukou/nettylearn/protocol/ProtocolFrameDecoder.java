package com.jiakoukou.nettylearn.protocol;

import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * 协议的解码定义后不会轻易改变，这里做一层封装
 *
 * 这个不能共享，原因，每个channel处理半包状态必须记录状态
 */
public class ProtocolFrameDecoder extends LengthFieldBasedFrameDecoder {

    /**
     * 无参构造
     *  解决粘包、半包问题；最大长度，长度偏移，长度占用字节，长度调整，剥离字节数
     *  长度偏移=整个数-魔数长度=16-12
     */
    public ProtocolFrameDecoder() {
        this(1024, 12, 4, 0, 0);
    }

    public ProtocolFrameDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip);
    }
}

package com.jiakoukou.nettylearn.nio.c2;

import java.nio.ByteBuffer;

public class TestByteBufferAllocate {
    public static void main(String[] args) {
        System.out.println(ByteBuffer.allocate(16).getClass()); // 创建堆内内存块HeapByteBuffer
        System.out.println(ByteBuffer.allocateDirect(16).getClass()); // 创建堆外内存块DirectByteBuffer
        /*
        class java.nio.HeapByteBuffer    - java 堆内存，读写效率较低，受到 GC（压缩拷贝算法等） 的影响
        class java.nio.DirectByteBuffer  - 直接内存，读写效率高（少一次拷贝），不会受 GC 影响，分配的效率低
         */
    }
}

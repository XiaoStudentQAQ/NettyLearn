package com.jiakoukou.nettylearn.netty.c3;

import io.netty.channel.DefaultEventLoopGroup;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.NettyRuntime;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class TestEventLoop {
    public static void main(String[] args) {
         // 输出当前电脑的核心数
        System.out.println(NettyRuntime.availableProcessors());

        // 1. 创建事件循环组
        EventLoopGroup group = new NioEventLoopGroup(2); // io 事件，普通任务，定时任务
//        EventLoopGroup group = new DefaultEventLoopGroup(); // 普通任务，定时任务
        // 2. 获取下一个事件循环对象
        System.out.println(group.next());
        System.out.println(group.next());
        System.out.println(group.next());
        System.out.println(group.next());

        //在线程池中， execute()  方法用于提交不需要返回值的任务，
        // 而  submit()  方法用于提交需要返回值的任务。
        // submit()  方法可以返回一个  Future  对象，
        // 通过这个对象可以获取任务执行的结果或者取消任务的执行。
        // 因此，主要区别在于 submit()  方法可以处理有返回值的任务，
        // 并提供更多的控制选项。
        // 3. 执行普通任务
        /*group.next().execute(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("ok");
        });*/

        // 4. 执行定时任务
        group.next().scheduleAtFixedRate(() -> {
            log.debug("ok");
        }, 0, 1, TimeUnit.SECONDS);

        log.debug("main");
    }
}

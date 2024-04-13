package com.jiakoukou.nettylearn;

import com.jiakoukou.nettylearn.it刘德华.server.NioNettyServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@Slf4j
@SpringBootApplication
public class NettyLearnApplication implements CommandLineRunner {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(NettyLearnApplication.class, args);
        // start to netty server
        context.getBean(NioNettyServer.class).start();
        log.info("jiakoukou提醒你，启动成功！");
    }

    @Override
    public void run(String... args) {
        log.info("========================server start success========================");
    }

}

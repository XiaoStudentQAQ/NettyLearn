package com.jiakoukou.nettylearn;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class NettyLearnApplication {

    public static void main(String[] args) {
        SpringApplication.run(NettyLearnApplication.class, args);
        log.info("jiakoukou提醒你，启动成功！");
    }

}

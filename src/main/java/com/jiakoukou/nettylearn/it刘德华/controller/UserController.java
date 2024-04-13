package com.jiakoukou.nettylearn.it刘德华.controller;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.jiakoukou.nettylearn.it刘德华.handler.ClientUserHandler;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 用户管理
 */
@RestController
@RequestMapping("/user")

public class UserController {
    @Autowired
    private ClientUserHandler clientUserHandler;

    /**
     * 获取token信息
     */
    @GetMapping("/token")
    public String getToken(){
        String token = IdUtil.fastSimpleUUID();
        ClientUserHandler.userMap.put(token,token);
        return token;
    }

    /**
     * 发送提醒
     */
    @PostMapping("/tips")
    public void sendToClient(@RequestParam("tips") String tips, @RequestParam("userId") String userId){
        Map<String, Channel> channelMap = clientUserHandler.channelMap;
        Channel channel = channelMap.get(userId);
        if(ObjectUtil.isNotNull(channel)){
            channel.writeAndFlush(tips);
        }
    }
}

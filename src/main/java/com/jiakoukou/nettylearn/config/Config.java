package com.jiakoukou.nettylearn.config;

import com.jiakoukou.nettylearn.protocol.Serializer;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 读取配置文件来确定使用哪种序列化算法
 */
public abstract class Config {
    /**
     * 静态属性会先于静态代码块加载。当一个类被加载时，静态属性会优先被初始化，然后才是静态代码块。
     */
    static Properties properties;
    static {
        try (InputStream in = Config.class.getResourceAsStream("/application.yml")) {
            properties = new Properties();
            properties.load(in);
        } catch (IOException e) {
            throw new ExceptionInInitializerError(e);
        }
    }
    public static int getServerPort() {
        String value = properties.getProperty("server.port");
        if(value == null) {
            return 8080;
        } else {
            return Integer.parseInt(value);
        }
    }
    public static Serializer.Algorithm getSerializerAlgorithm() {
        String value = properties.getProperty("serializer.algorithm");
        if(value == null) {
            return Serializer.Algorithm.Java;
        } else {
            return Serializer.Algorithm.valueOf(value);
        }
    }
}
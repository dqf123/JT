package com.jt.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;

import redis.clients.jedis.Jedis;


@Configuration//标识配置类
//引入主启动类所在的项目配置文件
@PropertySource("classpath:/properties/redis.properties")
public class RedisConfig {
	@Value("${redis.host}")
	private String host;
	@Value("${redis.port}")
	private Integer port;
    
	//默认单例对象,修改多列对象
	@Scope("prototype")
	@Bean
	public Jedis jedis() {
		return new Jedis(host,port);
	}
}

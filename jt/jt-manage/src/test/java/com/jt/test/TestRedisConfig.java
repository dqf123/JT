package com.jt.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import redis.clients.jedis.Jedis;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestRedisConfig {
    @Autowired
    private  Jedis jedis;
    
    @Test
    public void test01() {
    	jedis.set("aaa", "aaa");
    	System.out.println(jedis.get("aaa"));
    }
}

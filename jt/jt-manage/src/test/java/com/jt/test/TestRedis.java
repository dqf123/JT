package com.jt.test;



import org.junit.Before;
import org.junit.Test;

import redis.clients.jedis.Jedis;


public class TestRedis {
	
	private Jedis jedis;
	
	@Before //当执行@Test测试方法之前执行
	public void init() {
		 jedis=new Jedis("192.168.141.129", 6379);
	}
	
	/**
	 * 报错:
	 * 		1.执行关闭防火墙命令  service iptables stop
	 * 		2.redis-server redis.conf(默认启动 权限不正确)
	 * 		3.检查配置文件配置3处 ip绑定 保护模式关闭 后台启动开启
	 * 1.操作String类型
	 * @throws InterruptedException 
	 */
	@Test
	public void  testString() {
		jedis.set("1908", "好好学习,天天进步!!!!!");
		System.out.println(jedis.get("1908"));
		
		jedis.set("1908", "不好好学习,打屁股!!!!!");
		System.out.println(jedis.get("1908"));
		
		//3.如果key已经存在,则不允许操作redis.
		//原理:只能操作不存在的key  0 失败  /1 成功!!!
		Long flag =jedis.setnx("1909","今天周二!!!!");
		System.out.println(jedis.get("1909"));
		System.out.println("标识符:"+flag);
		
		
		
		
	}
}

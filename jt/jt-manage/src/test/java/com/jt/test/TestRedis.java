package com.jt.test;



import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;


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
	public void  testString() throws Exception {
		jedis.set("1908", "好好学习,天天进步!!!!!");
		System.out.println(jedis.get("1908"));
		
		jedis.set("1908", "不好好学习,打屁股!!!!!");
		System.out.println(jedis.get("1908"));
		
		//3.如果key已经存在,则不允许操作redis.
		//原理:只能操作不存在的key  0 失败  /1 成功!!!
		Long flag =jedis.setnx("1909","今天周二!!!!");
		System.out.println(jedis.get("1909"));
		System.out.println("标识符:"+flag);
		
		
		//4.为数据添加超时时间10秒
		//jedis.set("1908", "abc");
		//jedis.expire("1908", 10);
		jedis.setex("1908", 20, "随机");
		//jedis.psetex(key, milliseconds, value)
		Thread.sleep(3000L);		
		System.out.println("存活时间:"+jedis.ttl("1908"));	
		
	}
	
	/**
	 * 要求:setnx和setex的方法要求同时完成
	 * 实际用法: 实现redis分布式锁的关键.
	 */
	
	@Test
	public void testNXEX() {
		String result=jedis.set("abc", "小学生之手", "NX", "EX", 20);
		System.out.println("成功返回ok,不成功返回null");
		System.out.println("获得结果:"+result);
	}
	
	@Test
	public void test01() {
		jedis.hset("user", "id", "1");
		jedis.hset("user", "name", "tomcat");
		jedis.hset("user", "age", "18");
		Map<String, String> map = jedis.hgetAll("user");
		System.out.println(map);
	}
	
	/**
	 * redis中的事务
	 */
	
	   @Test
	   public void testTx() {
		   //1.开启事务
		   Transaction transaction = jedis.multi();
		   try {
			transaction.set("aaa", "aaa");
			transaction.set("bbb", "bbb");
			transaction.exec();
		} catch (Exception e) {
			e.printStackTrace();
			transaction.discard();
		}
	   }
	
}

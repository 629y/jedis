package com.imooc;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.Set;

public class JedisPoolTest {

    Jedis jedis = null;
    //建立连接
    @Before
    public void init() {
        //初始化 Jedis 客户端，声明主机和端口
         jedis = JedisPoolConnectRedis.getJedis();
        //身份认证
        jedis.auth("Courseimooc1");
        //PING PONG 心跳机制检测是否连接成功
        String pong = jedis.ping();
        System.out.println("pong = " + pong);
    }

    @Test
    public void testString(){
        //选择数据库
        String selResult = jedis.select(2);
        System.out.println("selResult = " + selResult);
        //插入一条数据
        String result = jedis.set("username", "zhangsan");
        System.out.println("result = " + result);
        //获取一条数据
        String username = jedis.get("username");
        System.out.println("username = " + username);
        //层级也是可以的
        //插入一条数据
        jedis.set("imooc:users:1","zhangsan");
        //获取一条数据
        String users1 = jedis.get("imooc:users:1");
        System.out.println("users1 = " + users1);
    }

    @Test
    public void testKeys() {
        jedis.select(2);
        Set<String> keys = jedis.keys("*");
        System.out.println(keys);
    }

    //释放资源
    @After
    public void close() {
        if (null != jedis){
            jedis.close();
        }
    }
}

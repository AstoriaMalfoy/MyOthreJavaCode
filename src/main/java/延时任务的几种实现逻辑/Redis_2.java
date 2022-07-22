package 延时任务的几种实现逻辑;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisPubSub;

import java.util.concurrent.CountDownLatch;

/**
 * @author litao34
 * @ClassName Redis_2
 * @Description TODO
 * @CreateDate 2022/1/10-2:44 PM
 **/
public class Redis_2 {
    private static final int THREAD_NUMBER = 10;

    private static CountDownLatch countdownLatch = new CountDownLatch(THREAD_NUMBER);

    private static final String ADDRESS = "127.0.0.1";

    private static final Integer PORT = 6379;

    private static JedisPool jedisPool ;

    private static RedisSub redisSub = new RedisSub();

    static{
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(500);
        jedisPoolConfig.setMaxIdle(200);
        jedisPoolConfig.setMaxWaitMillis(10000);
        jedisPool = new JedisPool(jedisPoolConfig,ADDRESS,PORT,20000,"astoria");
    }

    public static Jedis getJedis(){
        return jedisPool.getResource();
    }

    public static void init(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                jedisPool.getResource().subscribe(redisSub,"__keyevent@0__:expired");
            }
        }).start();
    }

    public static void main(String[] args) {
        init();
        for (int i = 0; i < 10; i++) {
            String orderId = "OID000000000" + i;
            jedisPool.getResource().setex(orderId,3,orderId);
            System.out.println(System.currentTimeMillis() + "ms:" + orderId + "订单生成");
        }
    }

    static class RedisSub extends JedisPubSub{
        @Override
        public void onMessage(String channel, String message) {
            System.out.println(System.currentTimeMillis() + "ms:" + message + "订单取消");
        }
    }
}

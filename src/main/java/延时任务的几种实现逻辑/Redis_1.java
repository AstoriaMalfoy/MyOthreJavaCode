package 延时任务的几种实现逻辑;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Tuple;

import java.util.Set;
import java.util.concurrent.CountDownLatch;

/**
 * @author litao34
 * @ClassName Redis_1
 * @Description TODO
 * @CreateDate 2022/1/8-3:16 PM
 **/
public class Redis_1 {

    private static final int THREAD_NUMBER = 10;

    private static CountDownLatch countdownLatch = new CountDownLatch(THREAD_NUMBER);

    private static final String ADDRESS = "127.0.0.1";

    private static final Integer PORT = 6379;

    private static JedisPool jedisPool ;


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

    // 生产者
    public void productionDelayMessage(){
        Jedis jedis = Redis_1.getJedis();
        for (int i = 0; i < 10; i++) {
            long lazyTime = System.currentTimeMillis();

            lazyTime /= 1000;
            // 订单延迟10秒钟
            lazyTime += 10;
            // 添加到redis中
            jedis.zadd("OrderId",lazyTime,"OID00000000" + i);
            System.out.println(System.currentTimeMillis() + "ms 生成了一个任务订单 : 订单ID : OID00000000" + i);

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        jedis.close();
    }

    public void consumerDelayMessage(){
        Jedis jedis = Redis_1.getJedis();
        while (true){
            // 定时调用redis获取score
            Set<Tuple> orders = jedis.zrangeWithScores("OrderId", 0, -1);

            if (orders.size() < 1){
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                continue;
            }

            Long nowTime = System.currentTimeMillis();
            nowTime /= 1000;
            Tuple tuple = (Tuple) orders.toArray()[0];
            int score = (int)((tuple).getScore());
            if (nowTime >= score){
                String orderId = tuple.getElement();
                jedis.zrem("OrderId",orderId);
                System.out.println(System.currentTimeMillis() + "ms : redis 消费了一个任务;消费的订单OrderId为" + orderId);
            }
        }
    }

    /**
     * 获取合法处理的订单
     * @return
     */
    public Tuple getOrders(){
        Jedis jedis = Redis_1.getJedis();
        Set<Tuple> orders = jedis.zrangeWithScores("OrderId", 0, -1);
        jedis.close();
        if (orders.size() < 1){
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        Long nowTime = System.currentTimeMillis();
        nowTime /= 1000;
        Tuple tuple = (Tuple) orders.toArray()[0];
        int score = (int)((tuple).getScore());
        if (nowTime >= score){
            return tuple;
        }
        return null;
    }

    public static void main(String[] args) {
        Redis_1 redis_1 = new Redis_1();
        redis_1.productionDelayMessage();
        redis_1.consumerDelayMessage();
    }


}

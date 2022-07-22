package redis;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

import java.util.Objects;
import java.util.Random;

/**
 * @author litao34
 * @ClassName DistributedLock
 * @Description Redis实现分布式锁 - 生产者*消费者
 * @CreateDate 2022/6/10-2:38 PM
 **/
@Slf4j
public class DistributedLock {
    public static final String ProductLock = "production_a";
    public static final String LockValue = "pr";


    public static void main(String[] args) throws InterruptedException {
        RedisUtil.getRedisClient().expire(ProductLock,0);
        Production production = new Production();
        Consumer consumer = new Consumer(RedisUtil.getRedisClient(),production);
        Privider privider = new Privider(RedisUtil.getRedisClient(),production);
        new Thread(consumer).start();
        new Thread(privider).start();
        Thread.sleep(100_000_000);
    }
}

@AllArgsConstructor
class Consumer implements Runnable{

    private Jedis jedis;
    private Production production;

    @SneakyThrows
    @Override
    public void run() {
        Random random = new Random();
        // 验证锁
        while (true){
            // 获得锁
            Long setnx = jedis.setnx(DistributedLock.ProductLock, DistributedLock.LockValue);
            if (RedisUtil.isLock(setnx)){
                if (Objects.isNull(production.getProd())){
                    int nextInt = random.nextInt() % 50 + 50;
                    production.setProd(nextInt);
                    System.out.println("生产线程生产产品 : " + nextInt);
                }
                // 释放锁
                jedis.expire(DistributedLock.ProductLock,0);
            }else{
                Thread.yield();
            }
        }
    }
}

@AllArgsConstructor
class Privider implements Runnable{

    private Jedis jedis;
    private Production production;

    @Override
    public void run() {
        while (true){
            Long setnx = jedis.setnx(DistributedLock.ProductLock, DistributedLock.LockValue);
            if (RedisUtil.isLock(setnx)){
                if (Objects.nonNull(production.getProd())){
                    Integer prod = production.getProd();
                    System.out.println("消费者消费产品: " + prod);
                    production.setProd(null);
                }
                jedis.expire(DistributedLock.ProductLock,0);
            }else{
                Thread.yield();
            }
        }
    }
}

@Data
class Production{
    private Integer prod;
}

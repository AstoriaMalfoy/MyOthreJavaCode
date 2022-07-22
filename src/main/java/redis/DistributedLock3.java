package redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;

import java.util.Arrays;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.locks.Lock;

/**
 * @author litao34
 * @ClassName DistributedLock3
 * @Description TODO
 * @CreateDate 2022/6/10-6:56 PM
 **/

public class DistributedLock3 {

    // 订单的数量
    private static Integer orderCount = 10000;

    // 处理订单的线程数量
    private static final Integer threadCount = 50;

    private static final String LockKey = "LOCK_KEY";

    public static void main(String[] args) {

        for (int i = 0 ; i < threadCount ; i ++){
            new Thread(){
                @Override
                public void run() {
                    Jedis redisClient = RedisUtil.getRedisClient();
                    while(true){
                    // 设置锁时候的lua脚本 将设置锁和设置TTL合并为原子操作
                        String uuid = UUID.randomUUID().toString();
                        String luaScripe =
                            "if " +
                                "redis.call('setnx',KEYS[1],ARGV[1]) == 1 " +
                            "then " +
                                "return redis.call('expire',KEYS[1],ARGV[2]) " +
                            "else " +
                                    "return -1 " +
                            "end";
//                        if redis.call('sexnx',KEYS[1],ARGV[1]) == 1 then return redis.call('expire',KEYS[1],ARGV[2]) else return -1 end
                        String scriptHash = redisClient.scriptLoad(luaScripe);
                        Object evalsha = redisClient.evalsha(scriptHash, Arrays.asList(LockKey), Arrays.asList(uuid, "3"));

                        if (RedisUtil.isLock(evalsha)){
                            redisClient.expire(LockKey,3);
                            Integer orderId = orderCount--;
                            if (orderId < 0){
                                redisClient.del(LockKey);
                                return;
                            }
                            redisClient.del(LockKey);
                            System.out.println("线程" + Thread.currentThread().getName() + "处理订单 " + orderId);
                        }else{

                            Long ttl = redisClient.ttl(LockKey);
                            if (ttl == -1){
                                String lockThread = redisClient.get(LockKey);
                                redisClient.del(LockKey);
                                System.out.println(lockThread + " 持有锁未释放 Lock锁未被释放,释放锁......");
                            }
                        }
                    }
                }
            }.start();
        }

    }

}


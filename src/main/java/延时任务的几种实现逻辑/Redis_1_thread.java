package 延时任务的几种实现逻辑;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.Objects;
import java.util.concurrent.CountDownLatch;

/**
 * @author litao34
 * @ClassName Redis_1_thread
 * @Description TODO
 * @CreateDate 2022/1/10-10:23 AM
 **/
public class Redis_1_thread {

    private static final int THREAD_COUNT = 10;

    private static CountDownLatch countDownLatch = new CountDownLatch(THREAD_COUNT);

    static class DelayMessage implements Runnable{

        Jedis jedis = Redis_1.getJedis();

        @Override
        public void run() {
            // 阻塞控制 统一执行
            try {
                countDownLatch.await();
                System.out.println("cus thread start....");
            }catch (Exception e){
                e.printStackTrace();
            }

            Redis_1 redis_1 = new Redis_1();
            while (true){
                // 消耗处理订单
                Tuple orders = redis_1.getOrders();
                if (Objects.isNull(orders)){
                    continue;
                }
                String key = orders.getElement();
                Long delRes = jedis.zrem("OrderId", key);
                if (delRes > 0 ){
                    System.out.println(System.currentTimeMillis() + " ms,消耗了一个OrderId为 : " + key + " 的订单");
                }
            }

        }
    }

    public static void main(String[] args) {
        Redis_1 redis_1 = new Redis_1();
        redis_1.productionDelayMessage();
        System.out.println("order create success ");
        for (int i = THREAD_COUNT; i  >= 0; i--) {
            System.out.println("create cus thread ... " + i);
            new Thread(new DelayMessage()).start();
            countDownLatch.countDown();
        }
    }
}

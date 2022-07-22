package redis;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.SneakyThrows;
import redis.clients.jedis.Jedis;

/**
 * @author litao34
 * @ClassName DistributedLock2
 * @Description Redis分布式锁 减扣订单
 * @CreateDate 2022/6/10-3:44 PM
 **/
public class DistributedLock2 {
    public static final String ITEM_KEY = "production_B";
    public static final String ITEM_VALUE = "item_b";


    public static void main(String[] args) {
        OrderService orderService = new OrderService();
        for(int i = 0 ; i < 10 ; i ++){
            new Thread(new OrderConsumer(RedisUtil.getRedisClient(),orderService), "Thread-" + i).start();
        }
    }
}

@Data
class OrderService{
    private Integer orderCount = 100;
    public Integer getOrder(){
        return orderCount--;
    }
}

@AllArgsConstructor
class OrderConsumer implements Runnable{

    private Jedis jedis;
    private OrderService orderService ;

    @SneakyThrows
    @Override
    public void run() {
        while (true){
            Long result = jedis.setnx(DistributedLock2.ITEM_KEY, DistributedLock2.ITEM_VALUE);
            if (RedisUtil.isLock(result)){
                Integer orderId = orderService.getOrderCount()-1;
                if (orderId < 0){
                    jedis.expire(DistributedLock2.ITEM_KEY,0);
                    return;
                }
                orderService.setOrderCount(orderId);
                Thread.sleep(100);
                System.out.println(Thread.currentThread().getName() + "\t消费线程消费订单 " + orderId);
                jedis.expire(DistributedLock2.ITEM_KEY,0);
            }
        }
    }
}
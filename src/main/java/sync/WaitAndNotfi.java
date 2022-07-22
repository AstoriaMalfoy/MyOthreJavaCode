package sync;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import sun.management.LockInfoCompositeData;

import java.util.Random;

/**
 * @author litao34
 * @ClassName WaitAndNotfi
 * @Description 模拟生产者消费者模型 单生产者和单消费者
 * @CreateDate 2022/6/1-6:59 PM
 **/
public class WaitAndNotfi {
    public static void main(String[] args) throws InterruptedException {
        LockProduce lockProduce = new LockProduce();
        Container container = new Container();
        Producer producer = new Producer(lockProduce, container, true);
        Comsumer comsumer = new Comsumer(lockProduce, container, true);
        new Thread(producer).start();
        Thread.sleep(1000);
        new Thread(comsumer).start();
        Thread.sleep(1000);
        producer.setFinish(false);
        comsumer.setFinish(false);
    }
}

/**
 * 生产者
 */

@Data
@AllArgsConstructor
class Producer implements Runnable{

    private LockProduce lockProduce = null;
    private Container container = null;
    private volatile boolean finish;

    @SneakyThrows
    @Override
    public void run() {
        Random random = new Random();
        Integer produce = 1;
        while (finish){
            synchronized (lockProduce){
                Integer createProduce ;
                container.setProduce(createProduce = produce + (random.nextInt()  % 20));
                System.out.println( "producer input " + createProduce);
                lockProduce.notify();
                lockProduce.wait();
            }
        }
    }

}

/**
 * 消费者
 */
@Data
@AllArgsConstructor
class Comsumer implements Runnable{

    private LockProduce lockProduce = null;
    private Container container = null;
    private volatile boolean finish;

    @SneakyThrows
    @Override
    public void run() {
        Random random = new Random();
        while (finish){
            synchronized (lockProduce){
                Integer produce = container.getProduce();
                System.out.println("consumer get " + produce);
                lockProduce.notify();
                lockProduce.wait();
            }
        }
    }
}

/**
 * 生产锁
 */
class LockProduce{

}


class LockConsumer{

}

@Data
class Container{
    private Integer produce;
    public void put(Integer produce){
        this.produce = produce;
    }
    public Integer get(){
        return this.produce;
    }
}
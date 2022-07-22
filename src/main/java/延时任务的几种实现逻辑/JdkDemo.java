package 延时任务的几种实现逻辑;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @author litao34
 * @ClassName JdkDemo
 * @Description TODO
 * @CreateDate 2022/1/7-3:34 PM
 **/
public class JdkDemo {



    @Data
    public static class JdkJob implements Delayed {

        private String orderId;

        private long timeOut;

        public JdkJob(String orderId, long timeOut) {
            this.orderId = orderId;
            this.timeOut = timeOut + System.currentTimeMillis();
        }


        @Override
        public long getDelay(TimeUnit unit) {
            long delay  = timeOut - System.currentTimeMillis();
            return unit.convert(delay, TimeUnit.MICROSECONDS);
        }

        @Override
        public int compareTo(Delayed o) {
            if (o == this){
                return 0;
            }
            JdkJob other = (JdkJob) o;
            long d = (getDelay(TimeUnit.MICROSECONDS) - other.getDelay(TimeUnit.MICROSECONDS));
            return (d==0) ? 0 : ((d < 0) ? -1 : 1);
        }

        void print(){
            System.out.println(orderId + " : 订单删除");
        }

    }

    public static void main(String[] args) {
        List<String> list = new ArrayList<String>();

        list.add("0000001");
        list.add("0000002");
        list.add("0000003");
        list.add("0000004");
        list.add("0000005");
        list.add("0000006");

        DelayQueue<JdkJob> queue = new DelayQueue<JdkJob>();

        long start = System.currentTimeMillis();
        System.out.println(start);
        for (String s : list) {
            JdkJob jdkJob = new JdkJob(
                    s,
                    3000
            );

            System.out.println(jdkJob);
            queue.put(jdkJob);

            try{
                JdkJob take = queue.take();
                take.print();
                System.out.println("After " + (System.currentTimeMillis() - start) + " MilliSeconds");
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }
}

package ScheduleExcuterService;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * @author litao34
 * @ClassName Demo
 * @Description TODO
 * @CreateDate 2022/7/21-2:25 PM
 **/
public class Demo {
    public static void main(String[] args) {
        // 创建循环线程
//        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5, new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setName("test nam");
                return thread;
            }
        });

        scheduledExecutorService.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("the time execture");
            }
        },5, TimeUnit.SECONDS);

        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println(new Date().toString());
                if (1 == 1){
                    throw  new RuntimeException();
                }
            }
        },1,5,TimeUnit.SECONDS);

//        scheduledExecutorService.scheduleWithFixedDelay(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println(new Date().toString() + "test demo");
//            }
//        },1,5,TimeUnit.SECONDS);


    }
}

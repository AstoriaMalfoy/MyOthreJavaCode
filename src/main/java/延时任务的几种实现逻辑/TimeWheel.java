package 延时任务的几种实现逻辑;


import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;
import io.netty.util.Timer;
import io.netty.util.TimerTask;

import java.util.concurrent.TimeUnit;

/**
 * @author litao34
 * @ClassName TimeWheel
 * @Description TODO
 * @CreateDate 2022/1/8-10:43 AM
 **/
public class TimeWheel {

    public static class  MyTimerTask implements TimerTask{
        private volatile boolean flag;

        public MyTimerTask(boolean flag) {
            this.flag = flag;
        }

        @Override
        public void run(Timeout timeout) throws Exception {
            System.out.println("the time task run ... ");
            this.flag = false;
        }
    }


    public static void main(String[] args) {

        MyTimerTask myTimerTask = new MyTimerTask(true);

        Timer timer = new HashedWheelTimer();
        timer.newTimeout(myTimerTask,3, TimeUnit.SECONDS);
        Long time = 1L;
        while (myTimerTask.flag){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("second : " + time ++);
        }
    }

}

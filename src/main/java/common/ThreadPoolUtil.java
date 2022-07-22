package common;

import org.quartz.spi.ThreadPool;

import java.sql.Time;
import java.util.concurrent.*;

/**
 * @author litao34
 * @ClassName ThreadPoolUtil
 * @Description TODO
 * @CreateDate 2022/4/26-4:18 PM
 **/
public class ThreadPoolUtil {
    /**
     * 核心线程数
     */
    private static Integer CORE_POOL_SIZE = 10;

    /**
     * 最大线程数
     */
    private static Integer MAX_POOL_SIZE = 20;

    /**
     * 线程空闲时间
     */
    private static Integer KEEP_ALIVE_TIME = 200;

    /**
     * 时间单位
     */
    private static TimeUnit timeUnit = TimeUnit.SECONDS;

    /**
     * 任务队列
     */
    private static BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>();

    /**
     * 线程工厂
     */
    private static ThreadFactory threadFactory = new ThreadFactory() {
        @Override
        public Thread newThread(Runnable runnable) {
            return new Thread(runnable);
        }
    };

    /**
     * 拒绝策略
     */
    private static RejectedExecutionHandler rejectedExecutionHandler = new RejectedExecutionHandler() {
        @Override
        public void rejectedExecution(Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {

        }
    };

    public static ThreadPoolExecutor getThreadPool(){
        return new ThreadPoolExecutor(CORE_POOL_SIZE,MAX_POOL_SIZE,KEEP_ALIVE_TIME, timeUnit,workQueue,threadFactory,rejectedExecutionHandler);
    }




}



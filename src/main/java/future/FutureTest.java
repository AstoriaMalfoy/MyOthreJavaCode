package future;


import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author litao34
 * @ClassName FutureTest
 * @Description TODO
 * @CreateDate 2022/4/26-4:16 PM
 **/
public class FutureTest {

//    private ThreadPoolExecutor poolExecutor = ThreadPoolUtil.getThreadPool();

    private ExecutorService executors = Executors.newFixedThreadPool(10);

    private CountDownLatch countDownLatch = new CountDownLatch(10);

    private ExecutorCompletionService executorCompletionService = new ExecutorCompletionService(executors);

    private class QuoteTask implements Callable<Integer>{
        private Integer sleepTime;

        QuoteTask(Integer task){
            this.sleepTime = task;
        }


        @Override
        public Integer call() throws Exception {
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return sleepTime;
        }
    }


    public void futureDemo1() throws InterruptedException, ExecutionException {
        List<Future<String>> list = new ArrayList();
        Random random = new Random(System.currentTimeMillis());
        AtomicReference<Long> synCost = new AtomicReference<>(0L);
        Long now = System.currentTimeMillis();
        for (int i=0;i<countDownLatch.getCount()+1;i++){

            int finalI = i;
            Future<String> submit = executorCompletionService.submit(() -> {
                int sleepTime =  random.nextInt(10000);
                System.out.println("thread coast " + sleepTime);
                Thread.sleep(sleepTime);
                synCost.updateAndGet(v -> v + sleepTime);
                countDownLatch.countDown();
                return "result" + finalI + " sleep:" + sleepTime;
            });

            list.add(submit);
        }
        for (int i = 0; i < 11 ; i ++){
            String str = (String) executorCompletionService.take().get();
            System.out.println("result------" + str);
        }

        countDownLatch.await();
        Long end = System.currentTimeMillis();
        System.out.println("串行耗时:" + (end - now)  + " 并行耗时:"+synCost.get());
        for (Future future : list) {
            String res = (String) future.get();
        }

    }




    public void futureTimeTest(){
        List<FutureTask> futureTasks = new ArrayList<>();
        FutureTask futureTask = new FutureTask(new QuoteTask(300));
        FutureTask futureTask2 = new FutureTask(new QuoteTask(680));
        FutureTask futureTask3 = new FutureTask(new QuoteTask(800));

    }



    public static void main(String[] args) throws ExecutionException, InterruptedException {
        new FutureTest().futureDemo1();
    }
}

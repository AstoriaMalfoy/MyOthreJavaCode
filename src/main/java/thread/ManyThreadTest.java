package thread;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.SneakyThrows;

import javax.swing.plaf.TableHeaderUI;
import java.util.Objects;

/**
 * @author litao34
 * @ClassName ManyThreadTest
 * @Description TODO
 * @CreateDate 2022/6/9-6:58 PM
 **/
public class ManyThreadTest {

    private static final int COUNT = 100_000_000;

    public static void main(String[] args) throws InterruptedException {
        TempThread tempThread = new TempThread(null);

        for (int i = 0 ; i < COUNT ; i++){
            tempThread = new TempThread(tempThread);
        }

        long t1 = System.currentTimeMillis();
        Thread thread = new Thread(tempThread);
        thread.start();
        thread.join();
        long t2 = System.currentTimeMillis();
        System.out.println("并行耗时 " + (t2 - t1));



        OneThread oneThread = new OneThread(COUNT);
        thread = new Thread(oneThread);
        thread.start();
        thread.join();
        long t3 = System.currentTimeMillis();
        System.out.println("串行耗时 " + (t3 - t3));


    }


    @Data
    @AllArgsConstructor
    static class TempThread implements Runnable{

        TempThread tempThread = null;

        @SneakyThrows
        @Override
        public void run() {
            long startTime = System.currentTimeMillis();
            for (int  i =0 ; i < 100; i ++){

            }
            if (Objects.nonNull(tempThread)){
                Thread thread = new Thread(tempThread);
                thread.join();
            }
        }
    }

    @Data
    @AllArgsConstructor
    static class OneThread implements Runnable{

        int count;

        @Override
        public void run() {
            for (int i =0; i <count ; i++){
                for (int j = 0; j < 100 ; j ++){

                }
            }
        }
    }
}

package volat;

import lombok.Data;
import lombok.SneakyThrows;

/**
 * @author litao34
 * @ClassName VolatileTest
 * @Description TODO
 * @CreateDate 2022/6/21-9:40 AM
 **/
@Data
public class VolatileTest {
    private volatile String str = "demoTest";

    public static void main(String[] args) {
        new VolatileTest().demoTest();
    }

    @SneakyThrows
    public void demoTest(){
        VolatileDemoTest volatileDemoTest = new VolatileDemoTest();
        new Thread(volatileDemoTest).run();
        for(;;){
            if (volatileDemoTest.isThreadParam()){
                System.out.println("hi");
            }
        }
    }
}

@Data
class VolatileDemoTest implements Runnable{

    private /*volatile*/ boolean threadParam = false;

    @Override
    @SneakyThrows
    public void run() {
        Thread.sleep(2000);
        this.threadParam = true;
    }
}

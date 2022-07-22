package ma;

import org.quartz.SimpleScheduleBuilder;

import java.util.Objects;

/**
 * @author litao34
 * @ClassName ma1
 * @Description TODO
 * @CreateDate 2022/5/18-3:14 PM
 **/
public class Ma1 {

    public static void main(String[] args) {

    }

}

class SingletonModeOneThread{
    private SingletonModeOneThread instance;

    private SingletonModeOneThread(){}

    public SingletonModeOneThread getInstance(){
        if (Objects.isNull(instance)){
            instance = new SingletonModeOneThread();
        }
        return instance;
    }
}


class SingletonModeManyThread{
    private SingletonModeManyThread instance;
    private SingletonModeManyThread(){}

    public SingletonModeManyThread getInstance(){
        if (Objects.isNull(instance)){
            synchronized (SingletonModeManyThread.class){
                if (Objects.isNull(instance)){
                    instance = new SingletonModeManyThread();
                }
            }
        }
        return instance;
    }
}

class SingletonModeHun{
    private static SingletonModeHun SINGLETON_MODE_HUN = null;
    static {
        SINGLETON_MODE_HUN = new SingletonModeHun();
    }
    public SingletonModeHun getInstance(){
        return SINGLETON_MODE_HUN;
    }
}


class SingletonModeInnerClass{

    private SingletonModeInnerClass(){}

    private static class InnerClass{
        private static SingletonModeInnerClass instance = new SingletonModeInnerClass();
    }

    public SingletonModeInnerClass getInstance(){
        return InnerClass.instance;
    }
}

enum SingletonModeEnum{
    INSTANCE;
}
package jvmHook;

import java.util.concurrent.Callable;

/**
 * @author litao34
 * @ClassName Demo
 * @Description TODO
 * @CreateDate 2022/7/21-5:19 PM
 **/
public class Demo {
    public static void main(String[] args) {
        // 添加JVM钩子函数
        Runtime.getRuntime().addShutdownHook(new Thread());
        // 移除JVM钩子函数
        Runtime.getRuntime().removeShutdownHook(new Thread());
    }
}

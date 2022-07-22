package jmh;

import org.openjdk.jmh.annotations.Benchmark;

/**
 * @author litao34
 * @ClassName MyBenchMark
 * @Description TODO
 * @CreateDate 2021/12/11-11:13 AM
 **/
public class MyBenchMark {


    @Benchmark
    public void testMethod(){
        // a demo for JMH benchMark template Edit as needed
        int a = 1;
        int b = 2;
        int sum = a+b;
    }
}

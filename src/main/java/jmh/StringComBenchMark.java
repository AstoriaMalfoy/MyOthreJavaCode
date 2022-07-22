package jmh;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;
/**
 * @author litao34
 * @ClassName StringComBenchMark
 * @Description TODO
 * @CreateDate 2021/12/11-2:30 PM
 **/
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Threads(2)
@Fork(2)
public class StringComBenchMark {


    @Benchmark
    public void testStringBenchmark(){
        String str = "";
        for (int i = 0; i < 10; i++) {
            str+=i;
        }
        print(str);
    }

    @Benchmark
    public void testStringBuilderBenchmark(){
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            str.append(i);
        }
        print(str.toString());
    }

    private void print(String str) {}

    public static void main(String[] args) throws RunnerException {
        Options build = new OptionsBuilder()
                .include(StringComBenchMark.class.getSimpleName())
                .build();
        new Runner(build).run();
    }
}

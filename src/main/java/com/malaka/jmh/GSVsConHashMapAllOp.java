package com.malaka.jmh;

import com.gs.collections.impl.map.mutable.ConcurrentHashMap;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.profile.StackProfiler;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by malakag on 1/27/16.
 */
@State(Scope.Benchmark)
public class GSVsConHashMapAllOp {
    @Param({ "250", "500", "750" })
    public long element;
    ConcurrentMap<Long, Long> hashMap = new ConcurrentHashMap<Long, Long>();
    ConcurrentMap<Long, Long> concurrentMap = new java.util.concurrent.ConcurrentHashMap<Long, Long>();
    Blackhole blackHole = new Blackhole();

    public static void main(String[] args) throws RunnerException {
        //Adding Profiler to Test
        Options options = new OptionsBuilder().include(".*" + GSVsConHashMapAllOp.class.getSimpleName() + ".*").forks(1)
                .addProfiler(StackProfiler.class).build();
        new Runner(options).run();
    }

    @Setup
    public void myBenchMarkSetUp() {
        for (long i = 0; i <= 1000; i++) {
            hashMap.put(i, i);
            concurrentMap.put(i, i);

        }

    }

    @Benchmark
    @Threads(8) //Define number of threads
    @OutputTimeUnit(TimeUnit.MICROSECONDS)

    public void clearCallGSHashMap() {
        hashMap.clear();
    }

    @Benchmark
    @Threads(8) //Define number of threads
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void clearCallConSkipList() {
        concurrentMap.clear();
    }

    @Benchmark
    @Threads(8) //Define number of threads
    @OutputTimeUnit(TimeUnit.MICROSECONDS)

    public void sizeCallGSHashMap() {
        blackHole.consume(hashMap.size());
    }

    @Benchmark
    @Threads(8) //Define number of threads
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void sizeCallConSkipList() {
        blackHole.consume(concurrentMap.size());
    }

    @Benchmark
    @Threads(8) //Define number of threads
    @OutputTimeUnit(TimeUnit.MICROSECONDS)

    public void getCallGSHashMap() {
        blackHole.consume(hashMap.get(element));
    }

    @Benchmark
    @Threads(8) //Define number of threads
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void getCallConSkipList() {
        blackHole.consume(concurrentMap.get(element));
    }

    @Benchmark
    @Threads(8) //Define number of threads
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void putCallGSHashMap() {
        hashMap.put(element, element);
    }

    @Benchmark
    @Threads(8) //Define number of threads
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void putCallConSkipList() {
        concurrentMap.put(element, element);
    }

    @Benchmark
    @Threads(8) //Define number of threads
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void removeCallGSHashMap() {
        hashMap.remove(element);
    }

    @Benchmark
    @Threads(8) //Define number of threads
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void removeCallConHashmap() {
        concurrentMap.remove(element);
    }

    @TearDown
    public void myBenchMarkTearDown() {

    }
}

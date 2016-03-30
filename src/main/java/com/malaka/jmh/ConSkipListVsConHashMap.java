package com.malaka.jmh;

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

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by malakag on 1/20/16.
 */
@State(Scope.Benchmark)
public class ConSkipListVsConHashMap {
    @Setup
    public void myBenchMarkSetup() {

    }

    ConcurrentMap<Long, Long> hashMap = new ConcurrentHashMap<Long, Long>();
    ConcurrentSkipListMap<Long, Long> concurrentMap = new ConcurrentSkipListMap<Long, Long>();
    Blackhole blackHole = new Blackhole();

    @Setup
    public void myBenchMarkSetUp() {
        for (long i = 0; i <= 1000; i++) {
            hashMap.put(i, i);
            concurrentMap.put(i, i);

        }

    }

    @Param({ "200", "500" })
    public long element;

    @Benchmark
    @Threads(16) //Define number of threads
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void putCallConHashMap() {
        concurrentMap.put(element, element);
    }

    @Benchmark
    @Threads(16) //Define number of threads
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void putCallConSkipListMap() {
        hashMap.put(element, element);
    }

    @TearDown
    public void myBenchMarkTearDown() {

    }

    public static void main(String[] args) throws RunnerException {
        //Adding Profiler to Test
        Options options = new OptionsBuilder().include(".*" + ConSkipListVsConHashMap.class.getSimpleName() + ".*")
                .forks(1).addProfiler(StackProfiler.class).build();
        new Runner(options).run();
    }

}

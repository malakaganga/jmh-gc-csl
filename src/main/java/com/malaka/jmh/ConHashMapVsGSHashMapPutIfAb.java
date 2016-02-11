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

import java.util.concurrent.TimeUnit;

/**
 * Created by malakag on 1/27/16.
 */
@State(Scope.Benchmark)
public class ConHashMapVsGSHashMapPutIfAb {
    ConcurrentHashMap<Long, Long> hashMap = new ConcurrentHashMap<Long, Long>();
    java.util.concurrent.ConcurrentHashMap<Long, Long> concurrentMap = new java.util.concurrent.ConcurrentHashMap<Long, Long>(
            16);
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
        concurrentMap.putIfAbsent(element, element);
    }

    @Benchmark
    @Threads(16) //Define number of threads
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void putCallGSHashMap() {
        hashMap.putIfAbsent(element, element);
    }

    @TearDown
    public void myBenchMarkTearDown() {

    }

    public static void main(String[] args) throws RunnerException {
        //Adding Profiler to Test
        Options options = new OptionsBuilder().include(".*" + ConHashMapVsGSHashMapPutIfAb.class.getSimpleName() + ".*")
                .forks(1).addProfiler(StackProfiler.class).build();
        new Runner(options).run();
    }
}

package com.malaka.jmh;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.profile.StackProfiler;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.TimeUnit;

import com.gs.collections.impl.map.mutable.ConcurrentHashMap;


/**
 * Created by malakag on 1/27/16.
 */
@State(Scope.Benchmark)
public class GSVsConSkipListMapPutOpNGetOp {
    ConcurrentHashMap<Long,Long> hashMap = new ConcurrentHashMap<Long, Long>();
    ConcurrentSkipListMap<Long,Long> concurrentMap = new ConcurrentSkipListMap<Long, Long>();
    Blackhole blackHole = new Blackhole();


    @Setup
    public void myBenchMarkSetUp() {
        for (long i=0;i<=100000;i++){
            hashMap.put(i,i);
            concurrentMap.put(i,i);

        }

    }

    @Param({"20000", "50000", "90000"})
    public long element;
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @Warmup(iterations = 10,time = 1,timeUnit = TimeUnit.MICROSECONDS)
    @Threads(16) //Define number of threads
    @OutputTimeUnit(TimeUnit.NANOSECONDS)

    public void getCallGSHashMap() {
        blackHole.consume(hashMap.get(element));
    }
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @Warmup(iterations = 10,time = 1,timeUnit = TimeUnit.MICROSECONDS)
    @Threads(16) //Define number of threads
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void getCallConSkipList() {
        blackHole.consume(concurrentMap.get(element));
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @Warmup(iterations = 10,time = 1,timeUnit = TimeUnit.MICROSECONDS)
    @Threads(16) //Define number of threads
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void putCallGSHashMap() {
        hashMap.put(element,element);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @Warmup(iterations = 10,time = 1,timeUnit = TimeUnit.MICROSECONDS)
    @Threads(16) //Define number of threads
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void putCallConSkipList() {
        concurrentMap.put(element,element);
    }

    @TearDown
    public void myBenchMarkTearDown() {

    }
    public static void main(String[] args) throws RunnerException {
        //Adding Profiler to Test
        Options options = new OptionsBuilder().include(".*"+GSVsConSkipListMapPutOpNGetOp.class.getSimpleName()+".*").forks(1).addProfiler(StackProfiler.class).build();
        new Runner(options).run();
    }
}

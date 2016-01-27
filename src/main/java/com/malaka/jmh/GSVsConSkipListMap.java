package com.malaka.jmh;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.profile.StackProfiler;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by malakag on 1/27/16.
 */
@State(Scope.Benchmark)
public class GSVsConSkipListMap {
    com.gs.collections.impl.map.mutable.ConcurrentHashMap<Long,Long> hashMap = new com.gs.collections.impl.map.mutable.ConcurrentHashMap<Long, Long>();
    ConcurrentSkipListMap<Long,Long> concurrentMap = new ConcurrentSkipListMap<Long, Long>();

    @Param({"100", "1000", "10000", "15000"})
    public long size;
    @Setup
    public void BenchMarkSetUp() {
        for (long i=0;i<=size;i++){
            hashMap.put(i,i);
            concurrentMap.put(i,i);
        }

    }
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @Warmup(iterations = 10,time = 1,timeUnit = TimeUnit.MICROSECONDS)
    @Threads(16) //Define number of threads
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public int sizeCallGCHashMap() {
        return hashMap.size();
    }
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @Warmup(iterations = 10,time = 1,timeUnit = TimeUnit.MICROSECONDS)
    @Threads(16) //Define number of threads
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public int sizeCallConSkipList(){
        return concurrentMap.size();
    }

    @TearDown
    public void myTearDown(){

    }
    public static void main(String[] args) throws RunnerException {
        //Adding Profiler to Test
        Options options = new OptionsBuilder().include(".*"+GSVsConSkipListMap.class.getSimpleName()+".*").forks(1).addProfiler(StackProfiler.class).resultFormat(ResultFormatType.TEXT).build();
        new Runner(options).run();
    }
}

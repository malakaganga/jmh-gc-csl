package com.malaka.jmh;

import com.gs.collections.impl.map.mutable.ConcurrentHashMapUnsafe;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.profile.StackProfiler;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by malakag on 1/27/16.
 */
@State(Scope.Benchmark)
public class GSVsConSkipListMap {
    ConcurrentMap<Long, Long> hashMap = new ConcurrentHashMapUnsafe<Long, Long>();
    ConcurrentMap<Long, Long> concurrentMap = new ConcurrentSkipListMap<Long, Long>();

    @Param({ "100", "500", "1000" })
    public long size;

    @Setup
    public void BenchMarkSetUp() {
        for (long i = 0; i <= size; i++) {
            hashMap.put(i, i);
            concurrentMap.put(i, i);
        }

    }

    @Benchmark
    @Threads(8) //Define number of threads
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public int sizeCallGCHashMap() {
        return hashMap.size();
    }

    @Benchmark
    @Threads(8) //Define number of threads
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public int sizeCallConSkipList() {
        return concurrentMap.size();
    }

    @TearDown
    public void myTearDown() {

    }

    public static void main(String[] args) throws RunnerException {
        //Adding Profiler to Test
        Options options = new OptionsBuilder().include(".*" + GSVsConSkipListMap.class.getSimpleName() + ".*").forks(1)
                .addProfiler(StackProfiler.class).resultFormat(ResultFormatType.TEXT).build();
        new Runner(options).run();
    }
}

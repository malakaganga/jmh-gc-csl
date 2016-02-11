package com.malaka.jmh;

import com.gs.collections.impl.map.mutable.ConcurrentHashMap;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
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

import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.TimeUnit;

/**
 * Created by malakag on 2/3/16.
 */
@State(Scope.Benchmark)
public class SkipListSetVsGSSet {
    ConcurrentMap<Long, Boolean> gsSet = new ConcurrentHashMap<Long, Boolean>();
    // Set<Long> gsSet = Collections.newSetFromMap(hash);
    Set<Long> concurrentSkipListSet = new ConcurrentSkipListSet<Long>();

    @Param({ "100", "500", "1000" })
    public long size;

    @Setup
    public void BenchMarkSetUp() {
        for (long i = 0; i <= size; i++) {
            gsSet.put(i, Boolean.TRUE);
            concurrentSkipListSet.add(i);
        }
    }

    @Benchmark
    @Threads(8) //Define number of threads
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public int sizeCallGCHashSet() {
        return gsSet.size();
    }

    @Benchmark
    @Threads(8) //Define number of threads
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public int sizeCallConSkipSet() {
        return concurrentSkipListSet.size();
    }

    @TearDown
    public void myTearDown() {

    }

    public static void main(String[] args) throws RunnerException {
        //Adding Profiler to Test
        Options options = new OptionsBuilder().include(".*" + SkipListSetVsGSSet.class.getSimpleName() + ".*").forks(1)
                .addProfiler(StackProfiler.class).resultFormat(ResultFormatType.TEXT).build();
        new Runner(options).run();
    }
}

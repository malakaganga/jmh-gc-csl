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
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.profile.StackProfiler;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.TimeUnit;

/**
 * Created by malakag on 2/3/16.
 */
@State(Scope.Benchmark)
public class GSSetVsSkipListSetAllOp {
    ConcurrentMap<Long, Boolean> hash = new ConcurrentHashMap<Long, Boolean>();
    Set<Long> gsSet = Collections.newSetFromMap(hash);
    Set<Long> concurrentSet = new ConcurrentSkipListSet<Long>();
    Set<Long> s = new HashSet<Long>();
    Blackhole blackHole = new Blackhole();

    @Setup
    public void myBenchMarkSetUp() {
        for (long i = 0; i <= 1000; i++) {
            gsSet.add(i);
            concurrentSet.add(i);
            s.add(i * 2);

        }

    }

    @Param({ "250", "500", "750", "1500" })
    public long element;

    @Benchmark
    @Threads(8) //Define number of threads
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)

    public boolean getCallGSHashSet() {
        return gsSet.remove(element);
    }

    @Benchmark
    @Threads(8) //Define number of threads
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public boolean getCallConSkipList() {
        return concurrentSet.remove(element);
    }

    @Benchmark
    @Threads(8) //Define number of threads
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void putCallGSHashSet() {
        gsSet.add(element);
    }

    @Benchmark
    @Threads(8) //Define number of threads
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void putCallConSkipList() {
        concurrentSet.add(element);
    }

    @Benchmark
    @Threads(8) //Define number of threads
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public boolean emptyCallGSHashMap() {
        return gsSet.isEmpty();
    }

    @Benchmark
    @Threads(8) //Define number of threads
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public boolean empptyCallConHashmap() {
        return concurrentSet.isEmpty();
    }

    @Benchmark
    @Threads(8) //Define number of threads
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void addAllCallGSHashSet() {
        gsSet.addAll(s);
    }

    @Benchmark
    @Threads(8) //Define number of threads
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void addAllCallConSkipList() {
        concurrentSet.addAll(s);
    }

    @TearDown
    public void myBenchMarkTearDown() {

    }

    public static void main(String[] args) throws RunnerException {
        //Adding Profiler to Test
        Options options = new OptionsBuilder().include(".*" + GSSetVsSkipListSetAllOp.class.getSimpleName() + ".*")
                .forks(1).addProfiler(StackProfiler.class).build();
        new Runner(options).run();
    }
}

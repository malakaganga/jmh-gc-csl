package com.malaka.jmh;

import com.gs.collections.impl.map.mutable.primitive.LongObjectHashMap;
import gnu.trove.map.hash.TLongObjectHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by malakag on 2/10/16.
 */
@State(Scope.Benchmark)
public class CompUtilMapVsPrimitiveHashMap {
    @Param({ "1000", "10000", "100000" })
    public long size;

    Map<Long, Long> hashMap = new HashMap<Long, Long>();
    Long2ObjectMap<Long> hashMapFastUtil = new Long2ObjectOpenHashMap<Long>();
    LongObjectHashMap<Long> hashMapGS = new LongObjectHashMap<Long>();
    TLongObjectHashMap<Long> hashMapTrove = new TLongObjectHashMap<Long>();
    Blackhole blackHole = new Blackhole();

    @Setup
    public void myBenchMarkSetUp() {
        for (long i = 0; i <= size; i++) {
            hashMap.put(i, i);
            hashMapFastUtil.put(i, new Long(i));
            hashMapGS.put(i, i);
            hashMapTrove.put(i, i);
        }
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void getCallHashMap() {
        blackHole.consume(hashMap.get(size / 2));
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void getCallGSHM() {
        blackHole.consume(hashMapGS.get(size / 2));
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void getCallTroveHM() {
        blackHole.consume(hashMapTrove.get(size / 2));
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void getCallFastUtilHM() {
        blackHole.consume(hashMapFastUtil.get(size / 2));
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void putCallHashMap() {
        hashMap.put(size / 2, new Long(size));
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void putCallGSHM() {
        hashMapGS.put(size / 2, size);
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void putCallTroveHashMap() {
        hashMapTrove.put(size / 2, new Long(size));
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void putCallFastUtilHM() {
        hashMapFastUtil.put(size / 2, new Long(size));
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void removeCallHashMap() {
        blackHole.consume(hashMap.remove(size / 2));
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void removeCallGSHM() {
        blackHole.consume(hashMapGS.remove(size / 2));
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void removeCallTroveHM() {
        blackHole.consume(hashMapTrove.remove(size / 2));
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void removeCallFastUtilHM() {
        blackHole.consume(hashMapFastUtil.remove(size / 2));
    }

    @TearDown
    public void myBenchMarkTearDown() {

    }

    public static void main(String[] args) throws RunnerException {
        //Adding Profiler to Test
        Options options = new OptionsBuilder()
                .include(".*" + CompUtilMapVsPrimitiveHashMap.class.getSimpleName() + ".*").forks(1).build();
        new Runner(options).run();
    }

}

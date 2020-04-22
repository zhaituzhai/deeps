package com.zhaojm.concurrency.c_sources;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * ThreadLoca!Random 类是JDK 7 在JUC 包下新增的随机数生成器，它弥补了Random
 * 类在多线程下的缺陷
 *
 * 本章首先讲解了Random 的实现原理以及Random 在多线程下需要竞争种子原子变量
 * 更新操作的缺点，从而引出ThreadLoca!Random 类。ThreadLoca!Random 使用ThreadLocal
 * 的原理，让每个线程都持有一个本地的种子变量，该种子变量只有在使用随机数时才会被
 * 初始化。在多线程下计算新种子时是根据自己线程内维护的种子变量进行更新，从而避免
 * 了竞争。
 *
 * @author zhaojm
 * @date 2020/4/22 22:05
 */
public class AaThreadLocalRandom {

    public static void main(String[] args) {
        // 普通的随机数生成
//        testRandom();

        // ThreadLocalRandom
        testThreadLocalRandom();
    }

    /**
     * Random 的缺点是多个线程会使用同一个原子性种子变量， 从而导致对原子变量更新的竞争.
     *
     * 如果每个线程都维护一个种子变量，则每个线程生成随机数时都根据自己老的种子计算新的种子，
     * 并使用新种子更新老的种子，再根据新种子计算随机数， 就不会存在竞争问题了，这会大大提高并发性能.
     *
     */
    public static void testThreadLocalRandom() {
        ThreadLocalRandom threadRandom = ThreadLocalRandom.current();
        for (int i = 0; i < 10; i++) {
            System.out.println(threadRandom.nextInt(5));
        }

        /*
        // Unsafe mechanics Unsafe 机制
        private static final sun.misc.Unsafe UNSAFE;
        private static final long SEED;
        private static final long PROBE;
        private static final long SECONDARY;
        static {
            try {
                // 获取一个 unsafe 实例
                UNSAFE = sun.misc.Unsafe.getUnsafe();
                Class<?> tk = Thread.class;
                // 获取 Thread 类里面的 threadLocalRandomSeed 变量在Thread实例里面的偏移量
                SEED = UNSAFE.objectFieldOffset
                    (tk.getDeclaredField("threadLocalRandomSeed"));
                // 获取 Thread 类里面的 threadLocalRandomProbe 变量里面的偏移量
                PROBE = UNSAFE.objectFieldOffset
                    (tk.getDeclaredField("threadLocalRandomProbe"));
                // 获取 Thread 类里面的 threadLocalRandomSecondarySeed 变量在Thread 实例里面的偏移量
                SECONDARY = UNSAFE.objectFieldOffset
                    (tk.getDeclaredField("threadLocalRandomSecondarySeed"));
            } catch (Exception e) {
                throw new Error(e);
            }
        }

        // current() 方法
        // The common ThreadLocalRandom
        static final ThreadLocalRandom instance = new ThreadLocalRandom();

        public static ThreadLocalRandom current() {
            // 如果当前线程中 threadLocalRandomProbe 的变量值变为0（默认情况下线程的这个变量值为0）
            // 则说明当前线程是第一次调用 ThreadLocalRandom 的 current 方法就需要调用 localInit 的方法
            // 计算当前线程的初始化种子的变量。
            if (UNSAFE.getInt(Thread.currentThread(), PROBE) == 0)
                // 首先根据probeGenerator 计算当前线程中threadLocalRandomProbe 的初始
                // 化值，然后根据seeder 计算当前线程的初始化种子，而后把这两个变量设置到当前线程。
                localInit();
            return instance;
        }

        static final void localInit() {
            int p = probeGenerator.addAndGet(PROBE_INCREMENT);
            int probe = (p == 0) ? 1 : p; // skip 0
            long seed = mix64(seeder.getAndAdd(SEEDER_INCREMENT));
            Thread t = Thread.currentThread();
            UNSAFE.putLong(t, SEED, seed);
            UNSAFE.putInt(t, PROBE, probe);
        }

        // nextInt()
        public int nextInt(int bound) {
            // 校验边界值
            if (bound <= 0)
                throw new IllegalArgumentException(BadBound);
            // 根据当前线程中的种子计算新种子
            int r = mix32(nextSeed());
            // 根据新种子和bound计算随机数
            int m = bound - 1;
            if ((bound & m) == 0) // power of two
                r &= m;
            else { // reject over-represented candidates
                for (int u = r >>> 1;
                     u + m - (r = u % bound) < 0;
                     u = mix32(nextSeed()) >>> 1)
                    ;
            }
            return r;
        }

        // 首先使用r = UNSAFE.getLo吨（t, SEED） 获取当前线程中threadLocalRandomSeed 变量的值，
        // 然后在种子的基础上累加GAMMA 值作为新种子，而后使用UNSAFE 的putLong 方法把新种子放入当前
        // 线程的threadLocalRandomSeed 变量中。
        final long nextSeed() {
            Thread t; long r; // read and update per-thread seed
            UNSAFE.putLong(t = Thread.currentThread(), SEED,
                           r = UNSAFE.getLong(t, SEED) + GAMMA);
            return r;
        }


         */

    }

    /**
     * 随机数生成需要一个默认的种子， 这个种子是一个 long (AtomicLong) 类型的数字
     *
     * 每个Random 实例里面都有一个原子性的种子变量用来记录当前的种子值，
     * 当要生成新的随机数时需要根据当前种子计算新的种子并更新回原子变量。在多线程
     * 下使用单个Random 实例生成随机数时，当多个线程同时计算随机数来计算新的种子
     * 时， 多个线程会竞争同一个原子变量的更新操作，由于原子变量的更新是CAS 操作，同
     * 时只有一个线程会成功，所以会造成大量线程进行自旋重试， 这会降低并发性能，所以
     * ThreadLocalRandom 应运而生。
     *
     */
    public static void testRandom() {
        // 创建一个随机数生成器 可以设置种子
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            // 输出 0~5 [0,5)
            System.out.println(random.nextInt(5));
        }
        /*

        生成新的随机数需要两个步骤
        1、根据老的种子生成新的种子
        2、根据新种子来计算生成随机数

        public int nextInt(int bound) {
            // 参数检查
            if (bound <= 0)
                throw new IllegalArgumentException(BadBound);
            // 根据老的种子生成新的种子
            int r = next(31);
            // 根据新种子计算随机数
            int m = bound - 1;
            if ((bound & m) == 0)  // i.e., bound is a power of 2
                r = (int)((bound * (long)r) >> 31);
            else {
                for (int u = r;
                     u - (r = u % bound) + m < 0;
                     u = next(31))
                    ;
            }
            return r;
        }

        // next()
        protected int next(int bits) {
            long oldseed, nextseed;
            AtomicLong seed = this.seed;
            do {
                // 当前原子变量的种子
                oldseed = seed.get();
                // 根据当前种子计算的新种子
                nextseed = (oldseed * multiplier + addend) & mask;
                // 使用 cas 操作，使它去更新老的种子，
            } while (!seed.compareAndSet(oldseed, nextseed));
            return (int)(nextseed >>> (48 - bits));
        }

        在多线程下可能多个线程拿到当前种子（同一个），计算新种子的值也是一样的，但是进行 cas
        时，之后保证一个线程可以更新老的种子，其他的失去

         */
    }

}

package com.zhaojm.concurrency.l_selfpool;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainRun {

    /**
     线程池几个核心参数

     corePoolSize 线程池的核心线程数
     maximumPoolSize 线程池允许的最大线程数
     keepAliveTime 超过核心线程数的线程不使用时存活时间
     unit 存活时间的单位
     workQueue 新任务被提交后，会先进入到此工作队列中，任务调度时再从队列中取出任务
        JDK提供四种工作队列
        1、{@link java.util.concurrent.ArrayBlockingQueue}  基于数组的有界阻塞队列（FIFO），新任务进来之后会防止该队列队尾，
     有界的数据组可以防止资源被耗尽，当线程池的线程数达到corePoolSize后，再有任务进入，则会讲任务放置该队列队尾，等待被调度，如果队
     列已经是满的时则会新增运行线程数，如果达到maximumPoolSize时，则会执行拒绝策略。
        2、{@link java.util.concurrent.LinkedBlockingQueue}  基于链表的无界阻塞队列（最大容量为{@link Integer#MAX_VALUE}）,
     按照FIFO排序。由于该队列时无界，当线程数达到corePooolSize之后，再有任务进入则会一直存入队列中，是maximunPoolSize参数失效。
        3、{@link java.util.concurrent.SynchronousQueue}  一个不缓存任务的队列，生产者放入一个任务，必须等到消费者取出这个任务，
     没有可用线程数则新建西瓜，超过maximumPoolSize则执行拒绝服务。
        4、{@link java.util.concurrent.PriorityBlockingQueue}
     threadFactory 创建一个新线程时使用的工厂，可以用来设定线程名、是否为deamon线程等。
     handler 拒绝策略；任务超过线程核心数之后，任务放入阻塞队列中，如果阻塞队列达到边界之后，新建线程至最大线程数值，如果还继续有任务
     则执行拒绝策略
        1、{@link java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy} 调用者线程直接执行被拒绝的run方法，除非线程池以及被关闭
        2、{@link java.util.concurrent.ThreadPoolExecutor.AbortPolicy} 抛出RejectedExecutionException异常  -- 默认
        3、{@link java.util.concurrent.ThreadPoolExecutor.DiscardPolicy} 直接抛弃异常
        4、{@link java.util.concurrent.ThreadPoolExecutor.DiscardOldestPolicy} 抛弃进入队列最早的任务，然和尝试把这次任务放入队列




    public ThreadPoolExecutor(int corePoolSize,
                              int maximumPoolSize,
                              long keepAliveTime,
                              TimeUnit unit,
                              BlockingQueue<Runnable> workQueue,
                              ThreadFactory threadFactory,
                              RejectedExecutionHandler handler);



     */

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newFixedThreadPool(5);

        executorService.execute(() -> {
            
            System.out.println("hello");
        });

        // testMapObj();

        // System.out.println(bigdecimalCheck(null, 4, "-00000007555.2233"));
        // System.out.println(new BigDecimal("333").setScale(2, BigDecimal.ROUND_HALF_DOWN).stripTrailingZeros());

    }

    public static boolean bigdecimalCheck(Integer integerLen, Integer decimalLen, String param) {
        BigDecimal decimal;
        if (param.length() <= 0) {
            return false;
        } else {
            decimal = new BigDecimal(param).stripTrailingZeros();
        }
        int precision = decimal.precision();
        int scale = decimal.scale();
        return (null == decimalLen || scale <= decimalLen) && (null == integerLen || precision - scale <= integerLen);
    }

    public static void testMapObj() {
        Map<String, Object> map = new HashMap<>();
        map.put("hello", new BigDecimal(100));

        System.out.println((BigDecimal)map.get("hello"));
    }

}

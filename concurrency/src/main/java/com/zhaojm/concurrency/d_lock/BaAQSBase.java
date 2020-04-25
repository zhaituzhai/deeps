package com.zhaojm.concurrency.d_lock;

/**
 * @author zhaojm
 * @date 2020/4/24 22:26
 */
public class BaAQSBase {

    /*
    AbstractQueuedSynchronizer 抽象同步队列 简称 AQS, 它是实现同步器的基础组件，并发包中锁底层就是使用AQS实现的

    独占方式下获取资源和释放资源

    void acquire(int arg)
    void acquireInterruptibly(int arg)
    boolean release(int arg)

    共享模式下获取和释放资源的方法
    void acquireShared(int arg)
    void acquireSharedInterruptibly(int arg)
    boolean releaseShared(int arg)


     */

    /*
    当一个线程调用 acquire(int arg) 方法获取独占资源时，会首先使用 tryAcquire 方法尝试获取资源，
    具体是设置状态变量 state 的值，成功则直接返回，失败则将当前线程封装为类型为 Node。EXCLUSIVE 的
    Node 节点后插入 AQS 阻塞队列的尾部，并调用 LockSupport.park(this) 的方法挂起自己
    public final void acquire(int arg) {
        if (!tryAcquire(arg) &&
                acquireQueued(addWaiter(AbstractQueuedSynchronizer.Node.EXCLUSIVE), arg))
            selfInterrupt();
    }
    */

    /*
    当一个线程调用 release(int arg) 方法时会尝试使用 tryRelease 操作释放资源，这里是设置状态变量 state
    然后调用 LockSupport.unpark(thread) 的方法激活 AQS 队列里面的被阻塞的一个线程(thread). 被激活的线程
    则使用 tryAcquire 尝试，看当前状态量 state 的值是否能满足自己的需要，满足则该线程被激活，然后继续运行，
    否则继续挂起
    public final boolean release(int arg) {
        if (tryRelease(arg)) {
            Node h = head;
            if (h != null && h.waitStatus != 0)
                unparkSuccessor(h);
            return true;
        }
        return false;
    }
     */

    /*

    TODO AQS 类并没有提供可用的句Acquire 和町Release 方法，正如AQS
    是锁阻塞和同步器的基础框架一样， t可Acquire 和tryRelease 需要由具体的子类来实现。
    子类在实现tryAcquire 和tryRelease 时要根据具体场景使用CAS 算法尝试修改state 状态值，
    成功则返回true，否则返回false 。子类还需要定义，在调用acquire 和release 方法时state
    状态值的增减代表什么含义。
     */

    // TODO 共享方式，获取与释放资源

    /*
    当线程调用acquireShared(int arg） 获取共享资源时，会首先使用trγAcq山reS hared
    尝试获取资源， 具体是设置状态变量state 的值，成功则直接返回，失败则将当前线
    程封装为类型为Node . SHARED 的Node 节点后插入到AQS 阻塞队列的尾部，并使用
    LockSupport. park( this) 方法挂起自己。
    public final void acquireShared(int arg) {
        if (tryAcquireShared(arg) < 0)
            doAcquireShared(arg);
    }
     */

    /*

    当一个线程调用releaseShared(int a电）时会尝试使用tryReleaseS hared 操作释放资
    源，这里是设置状态变量state 的值，然后使用LockSupport.unpark ( thread ）激活AQS 队
    列里面被阻塞的一个线程（thread） 。被激活的线程则使用tryReleaseS hared 查看当前状态变
    量state 的值是否能满足自己的需要，满足则该线程被撤活，然后继续向下运行，否则还
    是会被放入AQS 队列并被挂起。

    public final boolean releaseShared(int arg) {
        if (tryReleaseShared(arg)) {
            doReleaseShared();
            return true;
        }
        return false;
    }
     */

    /*

    isHeldExclusively

    重写用来判断锁是被当前线程独占还是被共享


     */

    // TODO 入队

    /*
    入队操作： 当一个线程获取锁失败后该线程会被转换为No de 节点，然后就会使用
    enq(final Node node） 方法将该节点插入到AQS 的阻塞队列。

    private Node enq(final Node node) {
        for (;;) {
            Node t = tail;
            if (t == null) {
            // Must initialize 使用 CAS 算法设置一个哨兵节点为头节点，如果 CAS 设置成功tail,head 指向哨兵节点
                if (compareAndSetHead(new Node()))
                    tail = head;
            } else {
                node.prev = t;
                if (compareAndSetTail(t, node)) {
                    t.next = node;
                    return t;
                }
            }
        }
    }

     */

}

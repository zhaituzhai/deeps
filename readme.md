#Deeps 深入学习

## MyBatis

MyBatis 源码  **mybaits-3**  
应用源码构建的 MyBatis 工程  **mybatis-self**
### 持久层实现
1. jdbc 实现持久层实例  
    + 注册驱动
    + 获取jdbc连接
    + 创建参数化预编译SQL
    + 绑定参数
    + 发送SQL给数据库进行执行
    + 对于查询，获取结果集到应用
2. MyBatis 实例
    1. MyBatis 初始化
        + Configuration.xml 解析
        + Mapper.xml 解析
    2. 执行 SQL 语句
        + 使用 session.selectOne("sql", "param"); 查询  
        + 使用 qlSession.getMapper(XxxMapper.class); 查询  
    3. 关闭 session.  
    
### 自动映射器的实现原理
1. 接口 XxxMapper.java 如何对应 XxxMapper.xml 中的 SQL 的。  
    XxxMapper.java 的全限名 + Method 名 == XxxMapper.xml 中的 namespace + 语句
2. XxxMapper.java 属于接口且没有实现方法，调用接口方法如何是处理 XxxMapper.xml 中的语句的  
    MapperProxy mapperProxyFactory.newInstance(sqlSession) 调用 Proxy 建立了一个 MapperProxy。
    MapperProxy.invoke();  

### MyBatis 事务管理
1. 事务四特性（ACID）
    1. 原子性
    2. 一致性
    3. 隔离性
    4. 持久性
2. 四大隔离级别
    1. 读未提交 Read Uncommitted  ==存在问题==> 脏读
    2. 读提交 Read Committed ==存在问题==> 不可重复读：一个事务中相同查询结果不一致
    3. 重复读 Repeatable Read MySQL 默认事务级别 
        ==解决问题==> 不可重复读  ==存在问题==> 幻读：幻读问题对应的是插入INSERT操作，而不是UPDATE操作
        幻读解决，InnoDB 使用 MVCC (multi version concurrency control) 解决。
    4. 序列化 Serializable 事务串行执行 
3. MyBatis 事务管理
    1. JdbcTransaction 
    
    
## 并发编程

### 并发编程基础
1. 基础知识  com.zhaojm.concurrency.a_base;
    1. 什么是线程
    2. 线程创建于运行
    3. 线程通知与等待
    4. 等待线程执行终止的 join 方法
    5. sleep 
    6. 上下文切换
    7. 线程中断
    8. 线程死锁
    9. 守护线程与用户线程
    10. ThreadLocal / InheritableThreadLocal
2. 其他基础知识  com.zhaojm.concurrency.b_base;
    1. 原子操作类
    2. 共享变量的内存可见性
    3. synchronized 关键字
    4. volatile 关键字
    5. CAS 操作
    6. Unsafe 类
    7. 锁的概述: 乐观锁与悲观锁，公平锁与非公平锁，独占锁与共享锁，可重入锁，自旋锁
3. Java 并发包中的 ThreadLocalRandom 类原理剖析
    1. Random 类及其局限性
    2. ThreadLocalRandom
4. Java 并发包中原子操作类 LongAdder com.zhaojm.concurrency.c_sources;
    1. AtomicXXX 等原子变量操作类 大量线程竞争一个原子变量，会造成大量线程竞争失败
    2. LongAdder 设置了 Cell 变量， 原子变量的总数就是为所有的 Cell 中的总和。 克服了大量线程竞争一个竞争量
    3. LongAccumulator：可以提供非 0 的初始值，LongAdder 只能提供 0 值。支持双目运算。
5. Java 并发包中的并发 List 源码
    1. CopyOnWriterArrayList 源码剖析
6. Java 并发包中锁原理
    1. LockSupport 工具类 park() / unpark()
    2. FIFO Mutex
    3. AQS 基础 / Condition
    4. 基于 AQS 的独占作 ReentrantLock 原理  公平 / 非公平机制
    5. 读写锁 ReentrantReadWriteLock 原理 state 高位 / 低位储存读写两个锁的机制
    6. StampedLock 原理
    7. doubleCheck 单例模式
7. Java 并发包中并发队列原理
    1. ConcurrentLinkedQueue 线程安全的无界非阻塞队列 单向链表
    2. LinkedBlockingQueue 独占锁实现阻塞队列 单向链表
    3. ArrayBlockingQueue 有边界的
    4. PriorityBlockingQueue 带优先级的无界阻塞队列，每次出列都返回优先级最高或者最低的元素；
    5. DelayQueue 内部使用 PriorityQueue 存放数据，使用 ReentrantLock 实现线程同步。
8. Java 并发包线程池原理
    1. 三种线程池
        newCachedThreadPool (线程池线程可用个数可达 Integer.MAX_VALUE)  
        newFixedThreadPool (固定大小的线程池)  
        newSingleThreadExecutor (单个线程池，单排队的线程可达 Integer.MAX_VALUE)  
    2. 线程池使用 Integer 的高位和低位记录线程池状态与线程个数。


## JVM

### java 类加载机制
1. 类加载器
    1. 常见类加载器只有3种
    2. 引导类加载器   ===> Bootstrap Class Loader
    3. 扩展类加载器   ===> Extension Class Loader
    4. 系统类加载器   ===> System Class Loader
    5. 自定义加载器   ===> User-Defined Class Loader
2. 双亲委派机制
    *工作原理*
    1. 如果一个类加载器收到一个类加载的请求，它并不会自己先去加载，而是把这个请求委托给父类加载器去执行。
    2. 如果还存在父类加载器，则继续向上委托。
    3. 如果父类加载器可以完成类加载任务，就成功返回，倘若父类加载器无法完成加载任务，子加载任务，子加载器才会自己尝试区加载
    *优势*
    1. 避免类的重复加载
    2. 保护程序安全，防止核心API被随意篡改
        java.lang.SelfClass ==> classNotFondClass
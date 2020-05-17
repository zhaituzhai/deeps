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

### PC 寄存器
1. PC寄存器用来存储指向下一条指令的地址，也即将要执行的指令代码。由执行引擎读取下一条指令。
    1. 是一块很小的内存空间，也是运行速度最快的存储区域。
    2. 每个线程都有自己的程序计数器，时线程私有的，生命周期与线程的生命周期一致。
    3. 任何时间一个线程都只有一个方法在执行，也就是当前方法。线程程序计数器会存储当前线程正在执行的Java 方法的JVM 指令地址；或者如歌执行native方法，则是指定（undefined）
    4. 程序控制流的指示器，分支、循环、跳转、异常处理、线程恢复等基础功能都需要依赖这个计数器来完成。
    5. 字节码解释器共做时就是通过改变这个计数器的值来选取下一条需要执行的字节码指令
    6. 它是唯一一个在 Java 虚拟机规范中没有规定 OutOfMemoryError 情况的区域。
2. 两个常见的问题
    1. 使用PC寄存器存储字节码指令地址有什么作用呢？为什么使用PC寄存器记录当前线程的执行地址呢？
        因为 CPU 需要不停的切换各个线程，这时候切换回来以后，就需要知道从哪继续开始。  
        JVM的字节码解释器就需要通过改变 PC 寄存器的值来明确下一条应该执行什么样的字节码指令。  
    2. PC寄存器为什么会被设定为线程私有？
        为了能够准确的记录各个线程正在执行的当前字节码指令地址，最好的办法是为了每一个线程都分配一个PC寄存器。  
        并使每个线程相互不影响。  
3. CPU时间片
    cpu执行线程是轮询时间片的形式。
    
### 栈
1. Java 虚拟机栈是什么？
    Java Virtual Machine Stack，每个线程在创建的时都会创建一个虚拟机栈，其内部保存一个一个的栈帧，对应着一次次的方法调用。
2. 栈中存储什么？
    每个线程有自己的栈，栈中的数据都是以栈帧的格式存在的。  
    在这个线程上正在执行的每个方法都对应一个栈帧。  
    栈帧是一个内存区块，是一个数据集，维系着方法执行过程的各种数据。
3. 栈中出现的异常
    Java 虚拟机规范允许 Java 栈的大小时动态的或者是固定不变的。
    1. 如果是固定大小不变，如果超过了栈的最大容量出现 StackOverflowError。
    2. 如果动态扩展，尝试扩展的时候无法申请到足够的内存，或者在创建新的线程是没有足够的内存去创建，则抛出 OutOfMemoryError。
4. 栈运行原理
    Java 栈操作只有两个，入栈/出栈，遵循FILO原则。
5. 栈帧内部结构
    1. 局部变量表
        1. 定义一个数字数组，主要用于存储方法参数和定义在方法体的局部变量，这些数据类型包括各类基本数据类型、对象引用、returnAddress.
        2. 参数值的存放总是在局部变量数组的index0klui ,到数组长度 -1 的索引结束。
        3. 局部变量表，最基本的存储单位是slot
        4. 局部变量表中，32位以内的类型占用一个 slot ，64 位的类型 占用两个 slot。
    2. 操作数栈  
        1. 每一个独立的栈帧中除了包含局部变量表以外，还包含一个后进先出的操作数栈，也可以称之为表达式栈。
        2.操作数栈，在方法执行过程中，根据字节码指令，往栈中写数据或提取数据，即入栈/出栈
        某些字节码指令将值压入操作数栈，其余的字节码指令将操作数取出栈。使用它们后再把结果压入栈。
        比如：执行复制、交换、求和等操作。
        3. 操作数栈，主要用于保存计算过程的中间结果，同时作为计算过程中变量的临时的存储空间。
        4. 操作数栈就是JVM执行引擎的一个工作区，当一个方法刚开始执行的时候，一个新的栈帧也会随之被创建出来，这个方法的操作数栈是空的。
        5. 每一个操作数据栈都会拥有一个明确的栈深度用于储存数值，其所需的最大神得了编译期就定义好了，保存在方法的Code属性中，为max_stack的值。
        6. 栈中的任何一个元素都是可以任意的Java数据类型。
        7. 操作数栈并非采用访问索引的方式来进行数据访问的，而是只能通过标准的入栈出栈来完成一次数据访问。
        8. 如果被调用的方法带有返回值的话，其返回值将会被压入当前栈的操作数栈中，并更新pc寄存器中下一条需要执行的字节码指令。
    3. 动态链接
        1. 每个栈帧内部都包含一个指向运行时常量池中该栈帧所属方法的引用。包含这个引用的目的就是为了支持当前方法的代码能够实现动态链接。
        2. 在Java源文件被编译到字节码文件中时，所有的变量合肥方法引用都作为符号引用保存在class文件的常量池里。比如：描述一个方法调用了另外的其他方法时，
        就是通过常量池中指向方法的符号引用来表示，那么动态链接的作用就是为了将这些符号转换在调用方法的直接引用。
    4. 方法返回地址
        JVM 中，将符号引用转换为调用方法的直接引用与方法的绑定机制相关。
    5. 附加信息

### 堆
1. 

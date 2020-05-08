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
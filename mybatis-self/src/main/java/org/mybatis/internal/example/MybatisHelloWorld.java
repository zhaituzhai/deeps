package org.mybatis.internal.example;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.mybatis.internal.example.pojo.User;

import java.io.IOException;
import java.io.Reader;

/**
 *
 * mybatis在执行期间，主要有四大核心接口对象：
 *
 * 执行器 Executor，执行器负责整个SQL执行过程的总体控制。
 * 参数处理器 ParameterHandler ，参数处理器负责PreparedStatement入参的具体设置。
 * 语句处理器 StatementHandler ，语句处理器负责和JDBC层具体交互，包括prepare语句，执行语句，以及调用ParameterHandler.parameterize()设置参数。
 * 结果集处理器 ResultSetHandler ，结果处理器负责将JDBC查询结果映射到java对象。
 *
 * @author zhaojm
 * @date 2020-03-30 14:10
 */
public class MybatisHelloWorld {
    public static void main(String[] args) {
        String resource = "org/mybatis/internal/example/Configuration.xml";
        Reader reader;
        try {
            // mybatis 初始化
            reader = Resources.getResourceAsReader(resource);
            SqlSessionFactory sqlMapper = new SqlSessionFactoryBuilder().build(reader);

            // 执行 sql 语句
            // 首先要拿到代表JDBC底层连接的一个对象，这在mybatis中的实现就是SqlSession
            // mybatis的事务管理模式分为两种，自动提交和手工提交，DefaultSqlSessionFactory的openSession中重载中，
            // 提供了一个参数用于控制是否自动提交事务， 该参数最终被传递给 java.sql.Connection.setAutoCommit()方法
            // 用于控制是否自动提交事务(默认情况下，连接是自动提交的)
            try (SqlSession session = sqlMapper.openSession(true)) {

                /*
                 * 简单来说，SqlSession就是jdbc连接的代表，openSession()就是获取jdbc连接（当然其背后可能是从jdbc连接池获取）；
                 * session中的各种selectXXX方法或者调用mapper的具体方法就是集合了JDBC调用的第3、4、5、6步。
                 *
                 * 根据sql语句使用xml进行维护或者在注解上配置,sql语句执行的入口分为两种：
                 * 第一种，调用 org.apache.ibatis.session.SqlSession 的 crud 方法比如 selectList/selectOne 传递完整的语句id直接执行；
                 * 第二种，先调用SqlSession的getMapper()方法得到mapper接口的一个实现，然后调用具体的方法。
                 * 除非早期，现在实际开发中，我们一般采用这种方式。
                 */
                User user = session.selectOne("org.mybatis.internal.example.mapper.UserMapper.getUser", 1);
                /*
                 * 通过SqlSession.getMapper执行CRUD语句
                 */
                // UserMapper mapper = session.getMapper(UserMapper.class);
                // // 这样当我们应用层执行List users = mapper.getUser(1);的时候，JVM会首先调用 MapperProxy.invoke，如下：
                // User user = mapper.getUser(1);
                System.out.println(user.getUserId() + "," + user.getUsername() + "," + user.getAge());
                // session.commit();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

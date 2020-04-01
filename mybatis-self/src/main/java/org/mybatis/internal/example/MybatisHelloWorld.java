package org.mybatis.internal.example;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.mybatis.internal.example.pojo.User;

import java.io.IOException;
import java.io.Reader;

/**
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
            try (SqlSession session = sqlMapper.openSession()) {
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
                System.out.println(user.getUserId() + "," + user.getUsername());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

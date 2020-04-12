package org.mybatis.internal.example.executor;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.mybatis.internal.example.mapper.UserMapper;
import org.mybatis.internal.example.pojo.User;

import java.io.IOException;
import java.io.Reader;

/**
 * @author zhaojm
 * @date 2020/4/12 17:18
 */
public class ExecutorTest {

    public static void main(String[] args) {
        String resource = "org/mybatis/internal/example/Configuration.xml";
        Reader reader;
        try {
            // mybatis 初始化
            reader = Resources.getResourceAsReader(resource);
            SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader);
            SqlSession sqlSession = factory.openSession(ExecutorType.BATCH);
            try {
                UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
                User addUser = new User("transaction", 14);
                userMapper.addUser(addUser);
                addUser = new User("Executor", 23);
                userMapper.addUser(addUser);
                addUser = new User("Executor1", 23);
                userMapper.addUser(addUser);
                sqlSession.commit();
            } catch (Exception e) {
                sqlSession.rollback();
            } finally {
                sqlSession.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    1、 使用简单 SqlSession sqlSession = factory.openSession(ExecutorType.SIMPLE);
    Created connection 985655350.
    Setting autocommit to false on JDBC Connection [com.mysql.jdbc.JDBC4Connection@3abfe836]
    ==>  Preparing: INSERT INTO self_user(username, age) VALUES (?, ?)
    ==> Parameters: transaction(String), 14(Integer)
    <==    Updates: 1
    ==>  Preparing: INSERT INTO self_user(username, age) VALUES (?, ?)
    ==> Parameters: Executor(String), 23(Integer)
    <==    Updates: 1
    ==>  Preparing: INSERT INTO self_user(username, age) VALUES (?, ?)
    ==> Parameters: Executor1(String), 23(Integer)
    <==    Updates: 1
    Committing JDBC Connection [com.mysql.jdbc.JDBC4Connection@3abfe836]
    Resetting autocommit to true on JDBC Connection [com.mysql.jdbc.JDBC4Connection@3abfe836]
    Closing JDBC Connection [com.mysql.jdbc.JDBC4Connection@3abfe836]
    Returned connection 985655350 to pool.
    =============================================================================================
    2、使用SqlSession sqlSession = factory.openSession(ExecutorType.REUSE);
    Created connection 985655350.
    Setting autocommit to false on JDBC Connection [com.mysql.jdbc.JDBC4Connection@3abfe836]
    ==>  Preparing: INSERT INTO self_user(username, age) VALUES (?, ?)
    ==> Parameters: transaction(String), 14(Integer)
    <==    Updates: 1
    ==> Parameters: Executor(String), 23(Integer)
    <==    Updates: 1
    ==> Parameters: Executor1(String), 23(Integer)
    <==    Updates: 1
    Committing JDBC Connection [com.mysql.jdbc.JDBC4Connection@3abfe836]
    Resetting autocommit to true on JDBC Connection [com.mysql.jdbc.JDBC4Connection@3abfe836]
    Closing JDBC Connection [com.mysql.jdbc.JDBC4Connection@3abfe836]
    Returned connection 985655350 to pool.
    =============================================================================================
    3、SqlSession sqlSession = factory.openSession(ExecutorType.BATCH);
    Created connection 985655350.
    Setting autocommit to false on JDBC Connection [com.mysql.jdbc.JDBC4Connection@3abfe836]
    ==>  Preparing: INSERT INTO self_user(username, age) VALUES (?, ?)
    ==> Parameters: transaction(String), 14(Integer)
    ==> Parameters: Executor(String), 23(Integer)
    ==> Parameters: Executor1(String), 23(Integer)
    Committing JDBC Connection [com.mysql.jdbc.JDBC4Connection@3abfe836]
    Resetting autocommit to true on JDBC Connection [com.mysql.jdbc.JDBC4Connection@3abfe836]
    Closing JDBC Connection [com.mysql.jdbc.JDBC4Connection@3abfe836]
    Returned connection 985655350 to pool.

     */


}

package org.mybatis.internal.example.transaction;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.mybatis.internal.example.mapper.UserMapper;
import org.mybatis.internal.example.pojo.User;

import java.io.IOException;
import java.io.Reader;

/**
 * @author zhaojm
 * @date 2020/4/12 10:53
 */
public class TransactionTest {
    public static void main(String[] args) {
        String resource = "org/mybatis/internal/example/Configuration.xml";
        Reader reader;
        try {
            // mybatis 初始化
            reader = Resources.getResourceAsReader(resource);
            SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader);
            SqlSession sqlSession = factory.openSession();
            try {
                UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
                User addUser = new User("transaction", 14);
                userMapper.addUser(addUser);
                sqlSession.commit();
                userMapper.addUser(addUser);
//                sqlSession.commit();
            } catch (Exception e) {
                sqlSession.rollback();
            } finally {
                sqlSession.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

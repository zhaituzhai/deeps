package org.mybatis.internal.example.handler;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.mybatis.internal.example.mapper.UserMapper;
import org.mybatis.internal.example.pojo.User;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 * @author zhaojm
 * @date 2020/4/11 21:29
 */
public class MapperProxy implements InvocationHandler {

    @SuppressWarnings("unchecked")
    public <T> T newInstance(Class<T> clz) {
        return (T) Proxy.newProxyInstance(clz.getClassLoader(), new Class[]{clz}, this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        if(Object.class.equals(method.getDeclaringClass())){
            try {
                return method.invoke(this, args);
            } catch (Throwable t) {

            }
        }

        return new User((Integer)args[0], "matt", 24);
    }

    // 自己的mapperProxy 测试
    /*public static void main(String[] args) {
        MapperProxy proxy = new MapperProxy();
        UserMapper mapper = proxy.newInstance(UserMapper.class);
        User user = mapper.getUser(2);
        System.out.println(user.getUserId());
        System.out.println(user.getUsername());
        System.out.println(user.getAge());
        System.out.println(mapper.toString());
    }*/

    public static void main(String[] args) {
        String resource = "org/mybatis/internal/example/Configuration.xml";
        Reader reader;
        try {
            // mybatis 初始化
            reader = Resources.getResourceAsReader(resource);
            SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader);
            try (SqlSession sqlSession = factory.openSession()) {
                UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
                List<User> userList = userMapper.getAllUser();
                for (User user : userList) {
                    System.out.println(user);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

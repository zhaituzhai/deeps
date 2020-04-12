package org.mybatis.internal.example.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * 在原生jdbc中，我们要执行一个sql语句，它的流程是这样的：
 *
 * 1、注册驱动；
 * 2、获取jdbc连接；
 * 3、创建参数化预编译SQL；
 * 4、绑定参数；
 * 5、发送SQL给数据库进行执行；
 * 6、对于查询，获取结果集到应用；
 *
 * 　　我们先回顾下典型JDBC的用法：
 * @author zhaojm
 * @date 2020/4/1 21:00
 */
public class JdbcHelloWord {

    public static void main(String[] args) throws Exception {
        //定义一个MYSQL链接对象
        Connection con = null;
        // MYSQL驱动
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        // 链接本地MYSQL
        con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/test_self?useUnicode=true", "root", "1234");

        //更新一条数据
        String updateSql = "UPDATE user SET age = '25' WHERE user_id = ?";
        PreparedStatement pstmt = con.prepareStatement(updateSql);
        pstmt.setString(1, "1");
        long updateRes = pstmt.executeUpdate();
        System.out.print("UPDATE:" + updateRes);

        // 查询数据并输出
        String sql = "select user_id, username, age from user where user_id = ?";
        PreparedStatement pstmt2 = con.prepareStatement(sql);
        pstmt2.setString(1, "1");
        ResultSet rs = pstmt2.executeQuery();
        while (rs.next()) { //循环输出结果集
            String lfPartyId = rs.getString("userId");
            String partyName = rs.getString("username");
            Integer age = rs.getInt("age");
            System.out.print("\r\n\r\n");
            System.out.print("lfPartyId:" + lfPartyId + "partyName:" + partyName + "age: " + age);
        }

        con.close();

    }

}

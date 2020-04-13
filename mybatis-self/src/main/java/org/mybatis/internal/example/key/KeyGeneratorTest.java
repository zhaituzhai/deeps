package org.mybatis.internal.example.key;

import java.sql.*;

/**
 * @author zhaojm
 * @date 2020/4/13 22:54
 */
public class KeyGeneratorTest {

    public static void main(String[] args) throws Exception {
        testJdbcKey();
    }

    private static void testJdbcKey() throws Exception {
        //定义一个MYSQL链接对象
        Connection con = null;
        // MYSQL驱动
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        // 链接本地MYSQL
        con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/test_self?useUnicode=true", "root", "1234");

        //更新一条数据
        String insertSql = "INSERT INTO self_user(username, age) VALUES (?, ?)";
        PreparedStatement pstmt = con.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
        pstmt.setString(1, "keyGenerator");
        pstmt.setString(2, "12");
        pstmt.addBatch();
        pstmt.setString(1, "keyGenerator1");
        pstmt.setString(2, "13");
        pstmt.addBatch();
        pstmt.executeBatch();

        ResultSet rs = pstmt.getGeneratedKeys();
        while (rs.next()) { //循环输出结果集
            String userId = rs.getString(1);
            System.out.println(userId);
        }
        rs.close();
        pstmt.close();
        con.close();
    }

}

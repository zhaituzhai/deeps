package org.mybatis.internal.example.type;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.mybatis.internal.example.pojo.Mobile;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 实现一个最简单的自定义类型处理器MobileTypeHandler。
 *
 * 我们实现了自定义的类型处理器后，只要在mybatis配置文件mybatis-config.xml中注册就可以使用了，如下：
 *
 *  <typeHandlers>
 *     <typeHandler handler="org.mybatis.internal.example.MobileTypeHandler" />
 * </typeHandlers>
 *
 * 上述完成之后，当我们在parameterType或者resultType或者resultMap中遇到Mobile类型的属性时，
 * 就会调用MobileTypeHandler进行代理出入参的设置和获取。
 * @author zhaojm
 * @date 2020-04-01 15:09
 */
public class MobileTypeHandler extends BaseTypeHandler<Mobile> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Mobile parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.getMobile());
    }

    @Override
    public Mobile getNullableResult(ResultSet rs, String columnName) throws SQLException {
        // mobile字段是VARCHAR类型，所以使用rs.getString
        return new Mobile(rs.getString(columnName));
    }

    @Override
    public Mobile getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return new Mobile(rs.getString(columnIndex));
    }

    @Override
    public Mobile getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return new Mobile(cs.getString(columnIndex));
    }
}

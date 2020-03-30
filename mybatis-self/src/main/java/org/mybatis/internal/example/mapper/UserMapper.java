package org.mybatis.internal.example.mapper;

import org.mybatis.internal.example.pojo.User;

/**
 * @author zhaojm
 * @date 2020-03-30 14:16
 */
public interface UserMapper {
    User getUser(Integer userId);
}

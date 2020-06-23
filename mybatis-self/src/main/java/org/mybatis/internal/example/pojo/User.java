package org.mybatis.internal.example.pojo;

import java.io.Serializable;

/**
 * @author zhaojm
 * @date 2020-03-30 14:11
 */
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer userId;
    private String username;
    private Integer age;

    public User() {
    }

    public User(Integer userId, String username, Integer age) {
        this.userId = userId;
        this.username = username;
        this.age = age;
    }

    public User(String username, Integer age) {
        this.username = username;
        this.age = age;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}

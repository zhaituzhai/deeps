package org.mybatis.internal.example.pojo;

/**
 * @author zhaojm
 * @date 2020-03-30 14:11
 */
public class User {

    private Integer userId;
    private String username;
    private Integer age;

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

package io.github.weijunfu.entity;

import java.io.Serializable;

/**
 *
 * @title  : 
 * @author : ijunfu <ijunfu@163.com>
 * @date   : 2024/6/28 21:49
 * @version: 1.0
 * @motto  : 简洁的代码是智慧的结晶 卓越的编码是对复杂性的优雅征服
 *
 */
public class User implements Serializable {

    static final long serialVersionUID = 121729L;

    /* 姓名 */
    private String name;

    /* 年龄 */
    private Integer age;

    /* 生日 */
    private String birthday;

    /* 头像 */
    private String avatar;

    public User() {
    }

    public User(String name, Integer age, String birthday, String avatar) {
        this.name = name;
        this.age = age;
        this.birthday = birthday;
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", birthday='" + birthday + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}

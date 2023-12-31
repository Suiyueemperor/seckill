package com.xxxx.seckill.pojo;

import com.baomidou.mybatisplus.annotation.TableName;


import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author Mr.Shi
 * @since 2023-09-03
 */
@TableName("t_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;


    private Long id;//用户ID,手机号码

    private String nickname;

    private String password;//密码

    private String salt;//盐值

    private String head;//MD5(MD5(pass明文+固定salt))

    private Date registerDate;//注册时间

    private Date lastLoginDate;//最后一次登陆时间


    private Integer loginCount;//登陆次数

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public Integer getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(Integer loginCount) {
        this.loginCount = loginCount;
    }

    @Override
    public String toString() {
        return "User{" +
        "id = " + id +
        ", nickname = " + nickname +
        ", password = " + password +
        ", salt = " + salt +
        ", head = " + head +
        ", registerDate = " + registerDate +
        ", lastLoginDate = " + lastLoginDate +
        ", loginCount = " + loginCount +
        "}";
    }


}
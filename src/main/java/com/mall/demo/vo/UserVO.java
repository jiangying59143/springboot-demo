package com.mall.demo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="user对象",description="用户对象user")
public class UserVO {
    @ApiModelProperty(value="用户登录账号",name="account",example="zhangsan")
    private String account;

    @ApiModelProperty(value="用户密码",name="password",example="123456")
    private String password;

    @ApiModelProperty(value="用户名",name="nickname",example="张三")
    private String nickname;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}

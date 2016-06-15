package com.eagle.springdome.entity;

import java.util.Date;

/**
 * Created by Wang Yong on 16-6-15.
 */
public class Account{

    private Long id;

    private String email;

    private String mobileNumber;

    private String password;

    private Date regTime;

    private String nickName;

    private Integer status;

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getMobileNumber(){
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber){
        this.mobileNumber = mobileNumber;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getNickName(){
        return nickName;
    }

    public void setNickName(String nickName){
        this.nickName = nickName;
    }

    public Date getRegTime(){
        return regTime;
    }

    public void setRegTime(Date regTime){
        this.regTime = regTime;
    }

    public Integer getStatus(){
        return status;
    }

    public void setStatus(Integer status){
        this.status = status;
    }
}

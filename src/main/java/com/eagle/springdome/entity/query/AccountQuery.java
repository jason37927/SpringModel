package com.eagle.springdome.entity.query;

import com.eagle.springdome.base.BaseQuery;

import java.util.Date;

/**
 * Created by Wang Yong on 16-6-16.
 */
public class AccountQuery extends BaseQuery{

    private Long id;

    private String email;

    private String mobileNumber;

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

    public String getNickName(){
        return nickName;
    }

    public void setNickName(String nickName){
        this.nickName = nickName;
    }

    public Integer getStatus(){
        return status;
    }

    public void setStatus(Integer status){
        this.status = status;
    }
}

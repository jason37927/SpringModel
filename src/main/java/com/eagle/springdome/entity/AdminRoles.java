package com.eagle.springdome.entity;

/**
 * Created by Wang Yong on 16-6-16.
 */
public class AdminRoles{

    private Integer id;

    private String roleName;

    private String remark;

    private Integer status;

    public Integer getId(){
        return id;
    }

    public void setId(Integer id){
        this.id = id;
    }

    public String getRoleName(){
        return roleName;
    }

    public void setRoleName(String roleName){
        this.roleName = roleName;
    }

    public String getRemark(){
        return remark;
    }

    public void setRemark(String remark){
        this.remark = remark;
    }

    public Integer getStatus(){
        return status;
    }

    public void setStatus(Integer status){
        this.status = status;
    }
}

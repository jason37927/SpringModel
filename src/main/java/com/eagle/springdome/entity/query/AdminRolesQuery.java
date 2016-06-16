package com.eagle.springdome.entity.query;

import com.eagle.springdome.base.BaseQuery;

/**
 * Created by Wang Yong on 16-6-16.
 */
public class AdminRolesQuery extends BaseQuery{

    private String roleNameFuzzy;

    private Integer status;

    public String getRoleNameFuzzy(){
        return roleNameFuzzy;
    }

    public void setRoleNameFuzzy(String roleNameFuzzy){
        this.roleNameFuzzy = roleNameFuzzy;
    }

    public Integer getStatus(){
        return status;
    }

    public void setStatus(Integer status){
        this.status = status;
    }
}

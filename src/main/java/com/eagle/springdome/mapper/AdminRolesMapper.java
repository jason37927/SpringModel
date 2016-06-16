package com.eagle.springdome.mapper;

import com.eagle.springdome.entity.AdminRoles;
import com.eagle.springdome.entity.query.AdminRolesQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Wang Yong on 16-6-16.
 */
@Repository
public interface AdminRolesMapper{

    int insert(AdminRoles entity);

    int updateByPrimaryKeySelective(AdminRoles entity);

    AdminRoles selectByPrimaryKey(Integer id);

    int matchConditionsResultsCount(AdminRolesQuery query);

    List<AdminRoles> matchConditionsResults(AdminRolesQuery query);

    int deleteByPrimaryKey(Integer id);
}

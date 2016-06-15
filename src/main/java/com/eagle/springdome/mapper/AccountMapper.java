package com.eagle.springdome.mapper;

import com.eagle.springdome.entity.Account;
import org.springframework.stereotype.Repository;

/**
 * Created by Wang Yong on 16-6-15.
 */
@Repository
public interface AccountMapper{

    public Account selectByPrimaryKey(Long id);
}

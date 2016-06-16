package com.eagle.springdome.mapper;

import com.eagle.springdome.entity.Account;
import com.eagle.springdome.entity.query.AccountQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Wang Yong on 16-6-15.
 */
@Repository
public interface AccountMapper{

    int insert(Account entity);

    int updateByPrimaryKeySelective(Account entity);

    Account selectByPrimaryKey(Long id);

    int matchConditionsResultsCount(AccountQuery accountQuery);

    List<Account> matchConditionsResults(AccountQuery accountQuery);
}

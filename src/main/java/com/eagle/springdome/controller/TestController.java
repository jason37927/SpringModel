/**
 *
 */
package com.eagle.springdome.controller;

import com.eagle.springdome.constants.CommonConstants;
import com.eagle.springdome.entity.Account;
import com.eagle.springdome.entity.query.AccountQuery;
import com.eagle.springdome.mapper.AccountMapper;
import com.eagle.springdome.util.MD5Encrypt;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import sun.security.provider.MD5;

import java.util.Date;
import java.util.List;

/**
 * @author Wang Yong
 */
@Controller
public class TestController{

    private static Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private AccountMapper accountMapper;

    @RequestMapping("/test.do")
    public String test(Model model){
        logger.info("dfgkjdfklgjdfkgj ");
        model.addAttribute("name", "WangYong");
        int size = jdbcTemplate.queryForList("select * from fee").size();
        Account account = accountMapper.selectByPrimaryKey(1L);

        AccountQuery query = new AccountQuery();
        query.setId(1L);
        int count = this.accountMapper.matchConditionsResultsCount(query);
        logger.info("match count：{}",count);
        List<Account> accountList = this.accountMapper.matchConditionsResults(query);
        logger.info("fetch list count:{}",accountList);
        if(account != null){
            logger.info(account.getEmail());
        }
        logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        logger.info("size:{}", size);
        logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>");

        account.setPassword(MD5Encrypt.md5("123456"));
        this.accountMapper.updateByPrimaryKeySelective(account);
        //test insert data
        Account accountEntity = new Account();
        accountEntity.setEmail(RandomStringUtils.random(6,Boolean.TRUE,Boolean.TRUE) + "@163.com");
        accountEntity.setMobileNumber(RandomStringUtils.randomNumeric(11));
        accountEntity.setPassword(MD5Encrypt.md5(accountEntity.getEmail() + "123456"));
        accountEntity.setRegTime(new Date());
        accountEntity.setStatus(CommonConstants.ACCOUNT_STATUS_ENABLED);
        accountEntity.setNickName("eagle");
        this.accountMapper.insert(accountEntity);
        return "index";
    }


}

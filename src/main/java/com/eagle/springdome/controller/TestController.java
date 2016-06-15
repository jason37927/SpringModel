/**
 *
 */
package com.eagle.springdome.controller;

import com.eagle.springdome.entity.Account;
import com.eagle.springdome.mapper.AccountMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.ProxyConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
        if(account != null){
            logger.info(account.getEmail());
        }
        logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        logger.info("size:{}", size);
        logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        return "index";
    }


}

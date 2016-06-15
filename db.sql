CREATE TABLE IF NOT EXISTS `account` (
  `id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(150) NOT NULL COMMENT '注册时的email账号',
  `mobile_number` VARCHAR(25) NULL COMMENT '绑定手机号',
  `password` VARCHAR(64) NOT NULL COMMENT '登陆密码(MD5+salt)',
  `reg_time` DATETIME NOT NULL COMMENT '注册时间',
  `nick_name` VARCHAR(45) NULL COMMENT '用户昵称(不设置随机生成)',
  `status` TINYINT(4) NOT NULL DEFAULT 0 COMMENT '账号状态(0-正常,1-锁定)',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC),
  UNIQUE INDEX `mobile_number_UNIQUE` (`mobile_number` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = '账号表';

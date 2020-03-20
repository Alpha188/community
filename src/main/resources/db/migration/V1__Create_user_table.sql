drop table if exists user;
CREATE TABLE `user` (
	`id` BIGINT unsigned NOT NULL AUTO_INCREMENT comment "主键",
	`account_id` varchar(100) NOT NULL comment "用户ID",
	`name` varchar(50) comment "用户名",
	`token` varchar(36) NOT NULL comment "令牌",
	`gmt_create` datetime NOT NULL comment "创建时间",
	`gmt_modified` datetime NOT NULL comment "修改时间",
	PRIMARY KEY (`id`)
) ENGINE=InnoDB, DEFAULT CHARSET=utf8;


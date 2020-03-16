drop table if exists question;
CREATE TABLE `community`.`question` (
  `id` INT NOT NULL AUTO_INCREMENT comment "主键",
  `creator` INT NOT NULL comment "创建者ID",
  `title` VARCHAR(50) NOT NULL comment "问题标题",
  `description` TEXT NOT NULL comment "问题描述",
  `comment_count` INT NULL DEFAULT 0 comment "评论数",
  `like_count` INT NULL DEFAULT 0 comment "点赞数",
  `view_count` INT NULL DEFAULT 0 comment "阅读数",
  `tag` VARCHAR(256) NULL comment "标签",
  `gmt_create` DATETIME NOT NULL comment "创建时间",
  `gmt_modified` DATETIME NOT NULL comment "修改时间",
  PRIMARY KEY (`id`)
)ENGINE=InnoDB, DEFAULT CHARSET=utf8;


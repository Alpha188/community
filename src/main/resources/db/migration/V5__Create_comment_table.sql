CREATE TABLE `comment` (
    `id` BIGINT unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
    `parent_id` BIGINT NOT NULL COMMENT '父类ID',
    `type` INT NOT NULL COMMENT '父类',
    `commentator` BIGINT NOT NULL COMMENT '评论者',
    `gmt_create` DATETIME NOT NULL COMMENT '创建时间',
    `gmt_Modified` DATETIME NOT NULL COMMENT '更新时间',
    `like_count` BIGINT DEFAULT 0 COMMENT '点赞数',
    PRIMARY KEY (`id`)
)  ENGINE=INNODB , DEFAULT CHARSET=UTF8;

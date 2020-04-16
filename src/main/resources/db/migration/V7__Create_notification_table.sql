CREATE TABLE `notification` (
    `id` BIGINT unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
    `notifier` BIGINT NOT NULL COMMENT '通知人ID',
    `notifier_name` VARCHAR(100) NOT NULL COMMENT '通知人名字',
    `receiver` BIGINT NOT NULL COMMENT '通知接收者ID',
    `outer_id` BIGINT NOT NULL COMMENT '问题ID/评论ID',
    `outer_title` VARCHAR(256) NOT NULL COMMENT '问题名称/评论内容',
    `gmt_create` DATETIME NOT NULL COMMENT '创建时间',
    `type` INT NOT NULL COMMENT '类型',
    `status` INT NOT NULL DEFAULT 0 COMMENT '状态 0未读 1已读',
    PRIMARY KEY (`id`)
)  ENGINE=INNODB , DEFAULT CHARSET=UTF8;

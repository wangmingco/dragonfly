CREATE DATABASE `dragonfly` DEFAULT CHARACTER SET = `utf8mb4`;

USE `dragonfly`;
SET session sql_mode = 'STRICT_TRANS_TABLES';

-- 用户表
DROP TABLE IF EXISTS `User`;
CREATE TABLE `User`
(
    `id`         bigint(20)    NOT NULL AUTO_INCREMENT COMMENT '主键',
    `username`   varchar(64)   NOT NULL COMMENT '用户名',
    `password`   varchar(2048) NOT NULL COMMENT '用户密码',
    `status`     int(1)        NOT NULL COMMENT '状态(1可用, 0不可用)',
    `createTime` datetime      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updateTime` datetime      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_username` (`username`),
    KEY `idx_createTime` (`createTime`),
    KEY `idx_updateTime` (`updateTime`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8 COMMENT ='用户表';
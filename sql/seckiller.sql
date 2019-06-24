-- MySQL Script generated by MySQL Workbench
-- Mon Jun 24 08:51:06 2019
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema seckiller
-- -----------------------------------------------------
-- 秒杀系统数据库设计

-- -----------------------------------------------------
-- Schema seckiller
--
-- 秒杀系统数据库设计
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `seckiller` DEFAULT CHARACTER SET utf8 COLLATE utf8_bin ;
USE `seckiller` ;

-- -----------------------------------------------------
-- Table `seckiller`.`user_account`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `seckiller`.`user_account` ;

CREATE TABLE IF NOT EXISTS `seckiller`.`user_account` (
  `user_id` BIGINT(20) NOT NULL,
  `user_name` VARCHAR(32) NOT NULL COMMENT '用户id，最长32个字符，',
  `user_password` VARCHAR(32) NOT NULL COMMENT '用户密码，EC(  传输密码段：密码EC(pass,固定salt) ,  数据库salt )',
  `user_pass_salt` VARCHAR(10) NULL COMMENT 'salt',
  `user_role` VARCHAR(20) NOT NULL DEFAULT 'ROLE_USER' COMMENT '用户角色 user-普通用户 ',
  `user_status` TINYINT(2) NOT NULL DEFAULT 1 COMMENT '用户账号状态 0-可用 其他',
  `user_reg_time` DATETIME NOT NULL COMMENT '注册时间',
  `user_last_login_time` DATETIME NULL COMMENT '最后一次登录时间',
  `user_login_cot` INT(11) NULL DEFAULT 0 COMMENT '用户登录计数',
  PRIMARY KEY (`user_id`),
  UNIQUE INDEX `user_id_hash_UNIQUE` (`user_id` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `seckiller`.`sk_goods`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `seckiller`.`sk_goods` ;

CREATE TABLE IF NOT EXISTS `seckiller`.`sk_goods` (
  `goods_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `goods_name` VARCHAR(45) NOT NULL COMMENT '商品名',
  `goods_sk_price` DECIMAL(10,2) NOT NULL DEFAULT 0.00 COMMENT '商品秒杀价格',
  `goods_sk_stock` INT(11) NOT NULL DEFAULT 0 COMMENT '秒杀总库存数',
  `goods_sk_cur_stock` INT(11) NOT NULL DEFAULT 0 COMMENT '当前剩余库存量',
  `sk_start_time` DATETIME NULL,
  `sk_end_time` DATETIME NULL,
  PRIMARY KEY (`goods_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `seckiller`.`goods_detail`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `seckiller`.`goods_detail` ;

CREATE TABLE IF NOT EXISTS `seckiller`.`goods_detail` (
  `goods_id` BIGINT(20) NOT NULL,
  `goods_name` VARCHAR(45) NOT NULL COMMENT '商品名',
  `goods_detail` VARCHAR(255) NULL COMMENT '商品秒杀',
  `goods_img` VARCHAR(128) NULL COMMENT '图片文件路径',
  `goods_price` DECIMAL(10,2) NOT NULL DEFAULT 0.00 COMMENT '商品价格',
  `goods_stock` INT(11) NOT NULL DEFAULT 0 COMMENT '当前库存数',
  PRIMARY KEY (`goods_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `seckiller`.`sk_order`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `seckiller`.`sk_order` ;

CREATE TABLE IF NOT EXISTS `seckiller`.`sk_order` (
  `order_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `goods_id` BIGINT(20) NOT NULL,
  `user_id` BIGINT(20) NOT NULL,
  PRIMARY KEY (`order_id`),
  UNIQUE INDEX `goods_id_UNIQUE` (`user_id` ASC, `goods_id` ASC) INVISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `seckiller`.`order_info`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `seckiller`.`order_info` ;

CREATE TABLE IF NOT EXISTS `seckiller`.`order_info` (
  `order_id` BIGINT(20) NOT NULL,
  `user_id` BIGINT(20) NOT NULL,
  `goods_id` BIGINT(20) NOT NULL,
  `goods_name` VARCHAR(45) NULL,
  `goods_price` DECIMAL(10,2) NULL DEFAULT 0.00,
  `goods_count` INT(11) NULL DEFAULT 0,
  `order_status` TINYINT(4) NULL DEFAULT 1 COMMENT '订单状态 0未支付 1已支付，2已发货，3已收货，4已退款，5已完成 6已失效',
  `order_crt_time` DATETIME NULL,
  `order_pay_time` DATETIME NULL,
  `order_addr` INT NULL COMMENT '地址',
  PRIMARY KEY (`order_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

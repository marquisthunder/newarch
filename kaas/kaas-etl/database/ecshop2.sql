-- phpMyAdmin SQL Dump
-- version 2.11.2.1
-- http://www.phpmyadmin.net
--
-- 主机: localhost
-- 生成日期: 2012 年 08 月 30 日 01:47
-- 服务器版本: 5.0.45
-- PHP 版本: 5.2.5

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";

--
-- 数据库: `ecshop2`
--

-- --------------------------------------------------------

--
-- 表的结构 `ecs_goods`
--

CREATE TABLE `ecs_goods` (
  `goods_id` int(11) NOT NULL auto_increment,
  `goods_name` varchar(20) collate utf8_bin NOT NULL,
  `goods_number` varchar(20) collate utf8_bin NOT NULL,
  `goods_weight` int(11) NOT NULL,
  `market_price` float NOT NULL,
  `shop_price` float NOT NULL,
  `promote_price` float NOT NULL,
  `promote_start_date` date NOT NULL,
  `promote_end_date` date NOT NULL,
  `goods_brief` varchar(20) collate utf8_bin NOT NULL,
  `goods_desc` text collate utf8_bin NOT NULL,
  `is_real` tinyint(1) NOT NULL,
  `add_time` date NOT NULL,
  `bin` binary(1) NOT NULL,
  PRIMARY KEY  (`goods_id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=4 ;

--
-- 导出表中的数据 `ecs_goods`
--

INSERT INTO `ecs_goods` (`goods_id`, `goods_name`, `goods_number`, `goods_weight`, `market_price`, `shop_price`, `promote_price`, `promote_start_date`, `promote_end_date`, `goods_brief`, `goods_desc`, `is_real`, `add_time`, `bin`) VALUES
(1, 'A', '23', 2, 12.5, 11, 10, '2012-08-20', '2012-08-21', 'A', 0x41, 1, '2012-08-19', '\0'),
(2, 'B', '23', 3, 12, 11, 7, '2012-08-19', '2012-08-24', 'B', 0x42, 1, '2012-08-19', '\0'),
(3, 'san', 'ww', 2, 2, 2, 2, '2012-08-20', '2012-08-20', '2', 0x3232, 1, '2012-08-20', '\0');

-- --------------------------------------------------------

--
-- 表的结构 `ecs_order`
--

CREATE TABLE `ecs_order` (
  `order_id` int(11) NOT NULL auto_increment,
  `name` varchar(20) collate utf8_bin NOT NULL,
  `goods_id1` int(11) NOT NULL,
  `goods_id2` int(11) NOT NULL,
  `goods_id3` int(11) NOT NULL,
  `goods_id4` int(11) NOT NULL,
  `goods_id5` int(11) NOT NULL,
  PRIMARY KEY  (`order_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=3 ;

--
-- 导出表中的数据 `ecs_order`
--

INSERT INTO `ecs_order` (`order_id`, `name`, `goods_id1`, `goods_id2`, `goods_id3`, `goods_id4`, `goods_id5`) VALUES
(1, '22', 1, 2, 3, 0, 0),
(2, '33', 2, 3, 0, 0, 0);

-- --------------------------------------------------------

--
-- 表的结构 `ecs_order_info`
--

CREATE TABLE `ecs_order_info` (
  `order_id` int(11) NOT NULL auto_increment,
  `user_id` int(11) NOT NULL,
  `order_sn` varchar(20) collate utf8_bin NOT NULL,
  `order_statue` int(11) NOT NULL,
  `goods_amount` float NOT NULL,
  `create_date` date NOT NULL,
  PRIMARY KEY  (`order_id`),
  KEY `user_id` (`user_id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=2 ;

--
-- 导出表中的数据 `ecs_order_info`
--

INSERT INTO `ecs_order_info` (`order_id`, `user_id`, `order_sn`, `order_statue`, `goods_amount`, `create_date`) VALUES
(1, 1, 'DDS34234234234', 1, 14, '2012-08-19');

-- --------------------------------------------------------

--
-- 表的结构 `ecs_users`
--

CREATE TABLE `ecs_users` (
  `user_id` int(11) NOT NULL auto_increment,
  `email` varchar(20) collate utf8_bin NOT NULL,
  `user_name` varchar(20) collate utf8_bin NOT NULL,
  `address` varchar(20) collate utf8_bin NOT NULL,
  `region` varchar(20) collate utf8_bin NOT NULL,
  PRIMARY KEY  (`user_id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=2 ;

--
-- 导出表中的数据 `ecs_users`
--

INSERT INTO `ecs_users` (`user_id`, `email`, `user_name`, `address`, `region`) VALUES
(1, 'usera@qq.com', 'usera', 'DHU', 'SH');

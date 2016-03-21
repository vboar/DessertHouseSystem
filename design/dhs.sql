/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50505
Source Host           : localhost:3306
Source Database       : dhs

Target Server Type    : MYSQL
Target Server Version : 50505
File Encoding         : 65001

Date: 2016-03-21 09:07:43
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for book
-- ----------------------------
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `customer_id` int(10) unsigned NOT NULL,
  `shop_id` int(10) unsigned NOT NULL,
  `user_id` int(10) unsigned DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `sale_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `discount` double NOT NULL,
  `original_total` double NOT NULL,
  `actual_total` double NOT NULL,
  `total_point` int(11) NOT NULL,
  `buy_date` date NOT NULL,
  `status` tinyint(1) unsigned NOT NULL COMMENT '订单状态 0未买 1已买 2取消 3过期',
  PRIMARY KEY (`id`),
  KEY `customer_id` (`customer_id`),
  KEY `shop_id` (`shop_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `book_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `book_ibfk_2` FOREIGN KEY (`shop_id`) REFERENCES `shop` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `book_ibfk_3` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of book
-- ----------------------------
INSERT INTO `book` VALUES ('19', '1', '1', null, '2016-03-21 08:39:59', '2016-03-21 08:56:14', '0.95', '48.2', '45.79', '270', '2016-03-21', '1');
INSERT INTO `book` VALUES ('20', '1', '1', null, '2016-03-21 08:51:20', '2016-03-21 08:51:20', '0.95', '17.8', '16.91', '100', '2016-03-22', '2');
INSERT INTO `book` VALUES ('21', '1', '1', null, '2016-03-21 08:56:28', '2016-03-21 08:56:28', '0.95', '25.6', '24.32', '120', '2016-03-21', '1');
INSERT INTO `book` VALUES ('22', '1', '1', null, '2016-03-21 08:56:37', '2016-03-21 08:56:37', '0.95', '19.6', '18.62', '120', '2016-03-21', '1');
INSERT INTO `book` VALUES ('23', '1', '1', null, '2016-03-21 09:01:43', null, '0.95', '57.6', '54.72', '300', '2016-03-23', '0');

-- ----------------------------
-- Table structure for book_item
-- ----------------------------
DROP TABLE IF EXISTS `book_item`;
CREATE TABLE `book_item` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `book_id` int(10) unsigned NOT NULL,
  `product_id` int(10) unsigned NOT NULL,
  `number` int(10) unsigned NOT NULL,
  `price` double NOT NULL,
  PRIMARY KEY (`id`),
  KEY `book_id` (`book_id`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `book_item_ibfk_1` FOREIGN KEY (`book_id`) REFERENCES `book` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `book_item_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of book_item
-- ----------------------------
INSERT INTO `book_item` VALUES ('17', '19', '1', '1', '9.8');
INSERT INTO `book_item` VALUES ('18', '19', '7', '1', '18.8');
INSERT INTO `book_item` VALUES ('19', '19', '2', '2', '9.8');
INSERT INTO `book_item` VALUES ('20', '20', '7', '1', '17.8');
INSERT INTO `book_item` VALUES ('21', '21', '6', '2', '12.8');
INSERT INTO `book_item` VALUES ('22', '22', '2', '2', '9.8');
INSERT INTO `book_item` VALUES ('23', '23', '3', '2', '28.8');

-- ----------------------------
-- Table structure for consumption
-- ----------------------------
DROP TABLE IF EXISTS `consumption`;
CREATE TABLE `consumption` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `customer_id` int(10) unsigned NOT NULL,
  `book_id` int(10) unsigned DEFAULT NULL,
  `money` double NOT NULL,
  `pay_type` tinyint(1) NOT NULL COMMENT '支付方式 1卡 2现金',
  `point` int(10) unsigned NOT NULL DEFAULT '0',
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `customer_id` (`customer_id`),
  KEY `book_id` (`book_id`),
  CONSTRAINT `consumption_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `consumption_ibfk_2` FOREIGN KEY (`book_id`) REFERENCES `book` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of consumption
-- ----------------------------
INSERT INTO `consumption` VALUES ('1', '1', '19', '45.79', '1', '270', '2016-03-21 08:56:13');
INSERT INTO `consumption` VALUES ('2', '1', '21', '24.32', '1', '120', '2016-03-21 08:56:28');
INSERT INTO `consumption` VALUES ('3', '1', '22', '18.62', '2', '120', '2016-03-21 08:56:37');

-- ----------------------------
-- Table structure for customer
-- ----------------------------
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `code` int(7) unsigned NOT NULL COMMENT '7位识别码',
  `phone` varchar(11) NOT NULL,
  `password` varchar(60) NOT NULL,
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '会员状态 0未激活 1有效 2暂停 3停止',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`),
  UNIQUE KEY `phone` (`phone`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of customer
-- ----------------------------
INSERT INTO `customer` VALUES ('1', '6366384', '18000000001', 'e10adc3949ba59abbe56e057f20f883e', '1');
INSERT INTO `customer` VALUES ('2', '4580629', '18000000002', 'e10adc3949ba59abbe56e057f20f883e', '0');
INSERT INTO `customer` VALUES ('3', '2952534', '18000000003', 'e10adc3949ba59abbe56e057f20f883e', '1');
INSERT INTO `customer` VALUES ('4', '2845107', '18000000004', 'e10adc3949ba59abbe56e057f20f883e', '1');
INSERT INTO `customer` VALUES ('5', '2256006', '18000000005', 'e10adc3949ba59abbe56e057f20f883e', '3');
INSERT INTO `customer` VALUES ('6', '6939227', '18000000006', 'e10adc3949ba59abbe56e057f20f883e', '0');
INSERT INTO `customer` VALUES ('7', '1194428', '18000000007', 'e10adc3949ba59abbe56e057f20f883e', '1');
INSERT INTO `customer` VALUES ('8', '2832467', '18000000008', 'e10adc3949ba59abbe56e057f20f883e', '3');

-- ----------------------------
-- Table structure for customer_account
-- ----------------------------
DROP TABLE IF EXISTS `customer_account`;
CREATE TABLE `customer_account` (
  `customer_id` int(10) unsigned NOT NULL,
  `bank_id` varchar(20) DEFAULT NULL COMMENT '银行卡卡号',
  `balance` double NOT NULL DEFAULT '0' COMMENT '会员卡余额',
  `point` int(11) NOT NULL DEFAULT '0' COMMENT '积分',
  `vip_level` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '会员等级',
  PRIMARY KEY (`customer_id`),
  UNIQUE KEY `bank_id` (`bank_id`) USING BTREE,
  KEY `vip_level` (`vip_level`),
  CONSTRAINT `customer_account_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `customer_account_ibfk_2` FOREIGN KEY (`vip_level`) REFERENCES `vip_level` (`level`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of customer_account
-- ----------------------------
INSERT INTO `customer_account` VALUES ('1', '600000000000000001', '1920.89', '210', '3');
INSERT INTO `customer_account` VALUES ('2', '600000000000000002', '0', '0', '0');
INSERT INTO `customer_account` VALUES ('3', '600000000000000003', '200', '0', '1');
INSERT INTO `customer_account` VALUES ('4', '600000000000000004', '15', '0', '1');
INSERT INTO `customer_account` VALUES ('5', '600000000000000005', '300', '0', '1');
INSERT INTO `customer_account` VALUES ('6', '600000000000000006', '0', '0', '0');
INSERT INTO `customer_account` VALUES ('7', '600000000000000007', '5000', '0', '5');
INSERT INTO `customer_account` VALUES ('8', '600000000000000008', '6', '0', '1');

-- ----------------------------
-- Table structure for customer_info
-- ----------------------------
DROP TABLE IF EXISTS `customer_info`;
CREATE TABLE `customer_info` (
  `customer_id` int(10) unsigned NOT NULL,
  `name` varchar(32) DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `gender` tinyint(1) DEFAULT NULL COMMENT '性别 1男0女',
  `province` varchar(10) DEFAULT NULL,
  `city` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`customer_id`),
  CONSTRAINT `customer_info_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of customer_info
-- ----------------------------
INSERT INTO `customer_info` VALUES ('1', '客户1', '1995-06-30', '1', '广东', '东莞');
INSERT INTO `customer_info` VALUES ('2', '客户2', '1950-05-10', '1', '江苏', '南京');
INSERT INTO `customer_info` VALUES ('3', '客户3', '1980-06-18', '0', '上海', '上海');
INSERT INTO `customer_info` VALUES ('4', '客户4', '1990-03-31', '1', '江苏', '南京');
INSERT INTO `customer_info` VALUES ('5', '客户5', '2000-03-14', '0', '北京', '北京');
INSERT INTO `customer_info` VALUES ('6', '客户6', '1994-03-09', '1', '上海', '上海');
INSERT INTO `customer_info` VALUES ('7', '客户7', '1997-03-05', '1', '江苏', '南京');
INSERT INTO `customer_info` VALUES ('8', '客户8', '1974-03-06', '1', '江苏', '南京');

-- ----------------------------
-- Table structure for customer_status
-- ----------------------------
DROP TABLE IF EXISTS `customer_status`;
CREATE TABLE `customer_status` (
  `customer_id` int(10) unsigned NOT NULL,
  `start_time` timestamp NULL DEFAULT NULL COMMENT '激活时间/重新激活时间',
  `pause_time` timestamp NULL DEFAULT NULL COMMENT '暂停时间',
  `stop_time` timestamp NULL DEFAULT NULL COMMENT '停止时间',
  PRIMARY KEY (`customer_id`),
  CONSTRAINT `customer_status_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of customer_status
-- ----------------------------
INSERT INTO `customer_status` VALUES ('1', '2016-03-19 18:09:45', '2017-03-19 18:09:45', '2018-03-19 18:09:45');
INSERT INTO `customer_status` VALUES ('2', null, null, null);
INSERT INTO `customer_status` VALUES ('3', '2016-03-20 17:59:07', '2017-03-20 17:59:07', '2018-03-20 17:59:07');
INSERT INTO `customer_status` VALUES ('4', '2016-03-21 09:05:50', '2017-03-21 09:05:50', '2018-03-21 09:05:50');
INSERT INTO `customer_status` VALUES ('5', '2016-03-21 08:16:27', '2017-03-21 08:16:27', '2018-03-21 08:16:27');
INSERT INTO `customer_status` VALUES ('6', null, null, null);
INSERT INTO `customer_status` VALUES ('7', '2016-03-21 08:18:24', '2017-03-21 08:18:24', '2018-03-21 08:18:24');
INSERT INTO `customer_status` VALUES ('8', '2014-03-21 08:59:21', '2015-03-21 08:59:21', '2016-03-21 08:59:21');

-- ----------------------------
-- Table structure for payment
-- ----------------------------
DROP TABLE IF EXISTS `payment`;
CREATE TABLE `payment` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `customer_id` int(10) unsigned NOT NULL,
  `money` double NOT NULL,
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `customer_id` (`customer_id`),
  CONSTRAINT `payment_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of payment
-- ----------------------------
INSERT INTO `payment` VALUES ('1', '1', '200', '2016-03-19 15:34:20');
INSERT INTO `payment` VALUES ('2', '1', '500', '2016-03-19 18:09:28');
INSERT INTO `payment` VALUES ('3', '1', '1288', '2016-03-19 18:09:45');
INSERT INTO `payment` VALUES ('4', '3', '200', '2016-03-20 17:59:07');
INSERT INTO `payment` VALUES ('5', '4', '250', '2016-03-21 08:15:27');
INSERT INTO `payment` VALUES ('6', '5', '300', '2016-03-21 08:16:27');
INSERT INTO `payment` VALUES ('7', '7', '5000', '2016-03-21 08:18:24');
INSERT INTO `payment` VALUES ('8', '8', '200', '2016-03-21 08:59:21');
INSERT INTO `payment` VALUES ('9', '4', '10', '2016-03-21 09:05:50');

-- ----------------------------
-- Table structure for plan
-- ----------------------------
DROP TABLE IF EXISTS `plan`;
CREATE TABLE `plan` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `shop_id` int(10) unsigned NOT NULL,
  `start_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `end_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `status` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '计划状态 0未审批 1批准 2不批准',
  PRIMARY KEY (`id`),
  KEY `FK_plan_shop` (`shop_id`),
  CONSTRAINT `FK_plan_shop` FOREIGN KEY (`shop_id`) REFERENCES `shop` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of plan
-- ----------------------------
INSERT INTO `plan` VALUES ('3', '1', '2016-03-21 08:27:34', '2016-03-27 23:59:59', '1');
INSERT INTO `plan` VALUES ('4', '1', '2016-03-28 00:00:00', '2016-04-03 23:59:59', '2');
INSERT INTO `plan` VALUES ('5', '2', '2016-03-21 00:00:00', '2016-03-27 23:59:59', '1');

-- ----------------------------
-- Table structure for plan_item
-- ----------------------------
DROP TABLE IF EXISTS `plan_item`;
CREATE TABLE `plan_item` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `plan_id` int(10) unsigned NOT NULL,
  `product_id` int(10) unsigned NOT NULL,
  `price` double NOT NULL,
  `number` int(10) unsigned NOT NULL,
  `remaining` int(10) unsigned NOT NULL COMMENT '剩余数量',
  `point` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '返还积分',
  `date` date NOT NULL,
  PRIMARY KEY (`id`),
  KEY `plan_id` (`plan_id`),
  KEY `FK_plan_item_product` (`product_id`),
  CONSTRAINT `FK_plan_item_product` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `plan_item_ibfk_1` FOREIGN KEY (`plan_id`) REFERENCES `plan` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of plan_item
-- ----------------------------
INSERT INTO `plan_item` VALUES ('16', '3', '7', '17.8', '60', '60', '100', '2016-03-23');
INSERT INTO `plan_item` VALUES ('17', '3', '6', '12.8', '50', '48', '60', '2016-03-21');
INSERT INTO `plan_item` VALUES ('18', '3', '2', '9.8', '50', '50', '60', '2016-03-25');
INSERT INTO `plan_item` VALUES ('19', '3', '7', '18.8', '50', '49', '100', '2016-03-21');
INSERT INTO `plan_item` VALUES ('20', '3', '5', '8.8', '50', '50', '50', '2016-03-22');
INSERT INTO `plan_item` VALUES ('21', '3', '1', '9.8', '45', '45', '50', '2016-03-22');
INSERT INTO `plan_item` VALUES ('22', '3', '4', '26.8', '30', '30', '120', '2016-03-21');
INSERT INTO `plan_item` VALUES ('23', '3', '1', '9.8', '45', '44', '50', '2016-03-21');
INSERT INTO `plan_item` VALUES ('24', '3', '4', '25.8', '30', '30', '120', '2016-03-24');
INSERT INTO `plan_item` VALUES ('25', '3', '7', '17.8', '60', '60', '100', '2016-03-22');
INSERT INTO `plan_item` VALUES ('26', '3', '9', '9.8', '50', '50', '60', '2016-03-26');
INSERT INTO `plan_item` VALUES ('27', '3', '3', '28.8', '15', '15', '150', '2016-03-22');
INSERT INTO `plan_item` VALUES ('28', '3', '3', '28.8', '10', '8', '150', '2016-03-23');
INSERT INTO `plan_item` VALUES ('29', '3', '7', '20.8', '50', '50', '100', '2016-03-25');
INSERT INTO `plan_item` VALUES ('30', '3', '8', '8.8', '80', '80', '50', '2016-03-21');
INSERT INTO `plan_item` VALUES ('31', '3', '5', '9.8', '50', '50', '50', '2016-03-23');
INSERT INTO `plan_item` VALUES ('32', '3', '10', '22.8', '20', '20', '100', '2016-03-23');
INSERT INTO `plan_item` VALUES ('33', '3', '10', '22.8', '20', '20', '100', '2016-03-22');
INSERT INTO `plan_item` VALUES ('34', '3', '8', '8.8', '50', '50', '50', '2016-03-22');
INSERT INTO `plan_item` VALUES ('35', '3', '7', '18.8', '50', '50', '100', '2016-03-26');
INSERT INTO `plan_item` VALUES ('36', '3', '4', '25.8', '30', '30', '120', '2016-03-22');
INSERT INTO `plan_item` VALUES ('37', '3', '9', '9.8', '60', '60', '60', '2016-03-21');
INSERT INTO `plan_item` VALUES ('38', '3', '6', '12.8', '50', '50', '60', '2016-03-22');
INSERT INTO `plan_item` VALUES ('39', '3', '8', '8.8', '100', '100', '50', '2016-03-23');
INSERT INTO `plan_item` VALUES ('40', '3', '5', '8.8', '50', '50', '50', '2016-03-21');
INSERT INTO `plan_item` VALUES ('41', '3', '2', '9.8', '30', '26', '60', '2016-03-21');
INSERT INTO `plan_item` VALUES ('42', '3', '1', '9.8', '50', '50', '50', '2016-03-23');
INSERT INTO `plan_item` VALUES ('43', '3', '6', '10.8', '50', '50', '60', '2016-03-23');
INSERT INTO `plan_item` VALUES ('44', '3', '4', '25.8', '30', '30', '120', '2016-03-23');
INSERT INTO `plan_item` VALUES ('45', '3', '7', '18.8', '50', '50', '100', '2016-03-24');
INSERT INTO `plan_item` VALUES ('46', '4', '8', '9.8', '50', '50', '50', '2016-03-28');
INSERT INTO `plan_item` VALUES ('47', '5', '12', '9.8', '50', '50', '50', '2016-03-27');
INSERT INTO `plan_item` VALUES ('48', '5', '11', '12.8', '50', '50', '70', '2016-03-27');

-- ----------------------------
-- Table structure for point
-- ----------------------------
DROP TABLE IF EXISTS `point`;
CREATE TABLE `point` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `customer_id` int(10) unsigned NOT NULL,
  `type` tinyint(1) NOT NULL COMMENT '类型 0消费 1兑换',
  `consumption_id` int(10) unsigned DEFAULT NULL,
  `point` int(10) unsigned NOT NULL,
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `customer_id` (`customer_id`),
  KEY `consumption_id` (`consumption_id`),
  CONSTRAINT `point_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `point_ibfk_2` FOREIGN KEY (`consumption_id`) REFERENCES `consumption` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of point
-- ----------------------------
INSERT INTO `point` VALUES ('4', '1', '0', '1', '270', '2016-03-21 08:56:14');
INSERT INTO `point` VALUES ('5', '1', '0', '2', '120', '2016-03-21 08:56:28');
INSERT INTO `point` VALUES ('6', '1', '0', '3', '120', '2016-03-21 08:56:37');
INSERT INTO `point` VALUES ('7', '1', '1', null, '300', '2016-03-21 08:57:55');

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL,
  `description` text,
  `shop_id` int(10) unsigned NOT NULL,
  `img` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_product_shop` (`shop_id`),
  CONSTRAINT `FK_product_shop` FOREIGN KEY (`shop_id`) REFERENCES `shop` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of product
-- ----------------------------
INSERT INTO `product` VALUES ('1', '芝麻花生冻饼', '芝麻花生冻饼。', '1', '/assets/resource/img/products/1458275411268芝麻花生冻饼.jpg');
INSERT INTO `product` VALUES ('2', '椰汁凉粉', '椰汁凉粉。', '1', '/assets/resource/img/products/1458275448492椰汁凉粉.jpg');
INSERT INTO `product` VALUES ('3', '椰汁莲子炖雪蛤膏', '椰汁莲子炖雪蛤膏。', '1', '/assets/resource/img/products/1458275462669椰汁莲子炖雪蛤膏.jpg');
INSERT INTO `product` VALUES ('4', '雪山榴莲', '雪山榴莲。', '1', '/assets/resource/img/products/1458275476596雪山榴莲.jpg');
INSERT INTO `product` VALUES ('5', '雪山红豆小圆子', '雪山红豆小圆子。', '1', '/assets/resource/img/products/1458275490419雪山红豆小圆子.jpg');
INSERT INTO `product` VALUES ('6', '生磨芝麻糊汤丸', '生磨芝麻糊汤丸。', '1', '/assets/resource/img/products/1458275501660生磨芝麻糊汤丸.jpg');
INSERT INTO `product` VALUES ('7', '芒果双皮奶', '芒果双皮奶。', '1', '/assets/resource/img/products/1458275512155芒果双皮奶.jpg');
INSERT INTO `product` VALUES ('8', '莲子红豆沙', '莲子红豆沙', '1', '/assets/resource/img/products/1458275521595莲子红豆沙.jpg');
INSERT INTO `product` VALUES ('9', '红豆沙豆腐花', '红豆沙豆腐花', '1', '/assets/resource/img/products/1458275532531红豆沙豆腐花.jpg');
INSERT INTO `product` VALUES ('10', '海底椰西米露', '海底椰西米露', '1', '/assets/resource/img/products/1458275543955海底椰西米露.jpg');
INSERT INTO `product` VALUES ('11', '草莓蛋糕', '草莓蛋糕。', '2', '/assets/resource/img/products/1458520347183草莓蛋糕.jpg');
INSERT INTO `product` VALUES ('12', '红豆沙豆腐花', '红豆沙豆腐花。', '2', '/assets/resource/img/products/1458520361768红豆沙豆腐花.jpg');

-- ----------------------------
-- Table structure for shop
-- ----------------------------
DROP TABLE IF EXISTS `shop`;
CREATE TABLE `shop` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL,
  `description` text,
  `address` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of shop
-- ----------------------------
INSERT INTO `shop` VALUES ('1', '南京新街口旗舰店', '南京新街口旗舰店。', '南京新街口国际金融中心B123');
INSERT INTO `shop` VALUES ('2', '南京马群一店', '南京马群一店。', '南京马群地铁站A123');
INSERT INTO `shop` VALUES ('3', '南京鼓楼南大店', '南京鼓楼南大店。', '南京汉口路22号');
INSERT INTO `shop` VALUES ('4', '上海世博一店', '上海世博一店。', '上海世博园区B555');
INSERT INTO `shop` VALUES ('5', '东莞第一国际二店', '东莞第一国际二店。', '东莞第一国际C区88号');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(16) NOT NULL,
  `name` varchar(32) NOT NULL,
  `password` varchar(60) NOT NULL,
  `role` tinyint(1) unsigned NOT NULL COMMENT '角色 1系统管理员 2总店服务员 3分店服务员 4经理',
  `shop_id` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'admin', '系统管理员', 'e10adc3949ba59abbe56e057f20f883e', '1', null);
INSERT INTO `user` VALUES ('2', 'a001', '王一聪', 'e10adc3949ba59abbe56e057f20f883e', '2', '1');
INSERT INTO `user` VALUES ('3', 'a002', '王二聪', 'e10adc3949ba59abbe56e057f20f883e', '3', '1');
INSERT INTO `user` VALUES ('4', 'a003', '王三聪', 'e10adc3949ba59abbe56e057f20f883e', '2', '2');
INSERT INTO `user` VALUES ('5', 'a004', '王四聪', 'e10adc3949ba59abbe56e057f20f883e', '3', '2');
INSERT INTO `user` VALUES ('6', 'manager', '王大聪', 'e10adc3949ba59abbe56e057f20f883e', '4', null);

-- ----------------------------
-- Table structure for vip_level
-- ----------------------------
DROP TABLE IF EXISTS `vip_level`;
CREATE TABLE `vip_level` (
  `level` tinyint(1) unsigned NOT NULL,
  `name` varchar(10) NOT NULL,
  `discount` double NOT NULL,
  `money` double NOT NULL COMMENT '一年内缴费金额',
  PRIMARY KEY (`level`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of vip_level
-- ----------------------------
INSERT INTO `vip_level` VALUES ('0', '非会员', '1', '0');
INSERT INTO `vip_level` VALUES ('1', '普通会员', '1', '200');
INSERT INTO `vip_level` VALUES ('2', '青铜会员', '0.98', '688');
INSERT INTO `vip_level` VALUES ('3', '白银会员', '0.95', '1288');
INSERT INTO `vip_level` VALUES ('4', '黄金会员', '0.88', '1888');
INSERT INTO `vip_level` VALUES ('5', '钻石会员', '0.85', '3888');
INSERT INTO `vip_level` VALUES ('6', '超级会员', '0.8', '8888');

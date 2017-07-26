/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50540
Source Host           : localhost:3306
Source Database       : ssm_fin

Target Server Type    : MYSQL
Target Server Version : 50540
File Encoding         : 65001

Date: 2017-07-26 00:42:34
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tbl_permission_groud
-- ----------------------------
DROP TABLE IF EXISTS `tbl_permission_groud`;
CREATE TABLE `tbl_permission_groud` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `parentID` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_permission_groud
-- ----------------------------
INSERT INTO `tbl_permission_groud` VALUES ('1', '论坛系统', '1');
INSERT INTO `tbl_permission_groud` VALUES ('2', '论坛管理', '1');
INSERT INTO `tbl_permission_groud` VALUES ('3', '论坛测试', '1');
INSERT INTO `tbl_permission_groud` VALUES ('4', '功能大全', '1');
INSERT INTO `tbl_permission_groud` VALUES ('5', '用户管理', '2');
INSERT INTO `tbl_permission_groud` VALUES ('6', '权限管理', '2');
INSERT INTO `tbl_permission_groud` VALUES ('7', '角色管理', '2');
INSERT INTO `tbl_permission_groud` VALUES ('8', '模块测试', '3');
INSERT INTO `tbl_permission_groud` VALUES ('11', '音乐专区', '4');

/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50540
Source Host           : localhost:3306
Source Database       : ssm_fin

Target Server Type    : MYSQL
Target Server Version : 50540
File Encoding         : 65001

Date: 2017-07-17 15:35:31
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tbl_permission
-- ----------------------------
DROP TABLE IF EXISTS `tbl_permission`;
CREATE TABLE `tbl_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `expression` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=90 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_permission
-- ----------------------------
INSERT INTO `tbl_permission` VALUES ('70', 'com.etop.webgeController.permissionManagement', '权限查询');
INSERT INTO `tbl_permission` VALUES ('71', 'com.etop.webgeController.userManagement', '用户查询');
INSERT INTO `tbl_permission` VALUES ('72', 'com.etop.webgeController.roleManagement', '角色查询');
INSERT INTO `tbl_permission` VALUES ('73', 'com.etop.websionController.updatePermissionJson', '分配角色权限');
INSERT INTO `tbl_permission` VALUES ('74', 'com.etop.websionController.getPermissionJson', '浏览角色所有权限');
INSERT INTO `tbl_permission` VALUES ('75', 'com.etop.websionController.roleDelete', '权限删除');
INSERT INTO `tbl_permission` VALUES ('76', 'com.etop.websionController.roleEdit', '权限编辑');
INSERT INTO `tbl_permission` VALUES ('77', 'com.etop.websionController.permissionAdd', '权限添加');
INSERT INTO `tbl_permission` VALUES ('78', 'com.etop.webntroller.roleHandlePermission', '权限分配');
INSERT INTO `tbl_permission` VALUES ('79', 'com.etop.webntroller.roleDelete', '角色删除');
INSERT INTO `tbl_permission` VALUES ('80', 'com.etop.webntroller.roleEdit', '角色编辑');
INSERT INTO `tbl_permission` VALUES ('81', 'com.etop.webntroller.permissionBrowse', '权限浏览');
INSERT INTO `tbl_permission` VALUES ('82', 'com.etop.webntroller.updateRoleJson', '分配用户角色');
INSERT INTO `tbl_permission` VALUES ('83', 'com.etop.webntroller.getRoleJson', '浏览用户所有角色');
INSERT INTO `tbl_permission` VALUES ('84', 'com.etop.webntroller.permissionAdd', '角色添加');
INSERT INTO `tbl_permission` VALUES ('85', 'com.etop.webtionController.permissionBrowse', '播放音乐');
INSERT INTO `tbl_permission` VALUES ('86', 'com.etop.webntroller.roleDelete', '用户删除');
INSERT INTO `tbl_permission` VALUES ('87', 'com.etop.webntroller.roleEdit', '用户编辑');
INSERT INTO `tbl_permission` VALUES ('88', 'com.etop.webntroller.permissionAdd', '用户添加');
INSERT INTO `tbl_permission` VALUES ('89', 'com.etop.webntroller.browse', '角色浏览');

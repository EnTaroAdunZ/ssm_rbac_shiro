/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50540
Source Host           : localhost:3306
Source Database       : ssm_fin

Target Server Type    : MYSQL
Target Server Version : 50540
File Encoding         : 65001

Date: 2017-07-26 00:42:27
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
  `parentID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `expression` (`expression`) USING BTREE,
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_permission
-- ----------------------------
INSERT INTO `tbl_permission` VALUES ('1', 'homePage:userManagement', '用户查询', '5');
INSERT INTO `tbl_permission` VALUES ('2', 'homePage:roleManagement', '角色查询', '7');
INSERT INTO `tbl_permission` VALUES ('3', 'homePage:permissionManagement', '权限查询', '6');
INSERT INTO `tbl_permission` VALUES ('4', 'simulationController:playMusic', '播放音乐', '11');
INSERT INTO `tbl_permission` VALUES ('5', 'permission:permissionAdd', '权限添加', '6');
INSERT INTO `tbl_permission` VALUES ('6', 'permission:permissionEdit', '权限编辑', '6');
INSERT INTO `tbl_permission` VALUES ('7', 'permission:permissionDelete', '权限删除', '6');
INSERT INTO `tbl_permission` VALUES ('8', 'permission:updatePermissionJson:ajax', '分配角色权限', '6');
INSERT INTO `tbl_permission` VALUES ('9', 'permission:getPermissionJson:ajax', '浏览角色所有权限', '6');
INSERT INTO `tbl_permission` VALUES ('10', 'role:permissionBrowse', '权限浏览', '6');
INSERT INTO `tbl_permission` VALUES ('11', 'role:getRoleJson:ajax', '浏览用户所有角色', '7');
INSERT INTO `tbl_permission` VALUES ('12', 'role:roleEdit', '角色编辑', '7');
INSERT INTO `tbl_permission` VALUES ('13', 'role:roleDelete', '角色删除', '7');
INSERT INTO `tbl_permission` VALUES ('14', 'role:roleAdd', '角色添加', '7');
INSERT INTO `tbl_permission` VALUES ('15', 'role:updateRoleJson:ajax', '分配用户角色', '7');
INSERT INTO `tbl_permission` VALUES ('16', 'role:roleHandlePermission', '权限分配', '6');
INSERT INTO `tbl_permission` VALUES ('17', 'role:treePermission_echo:ajax', '树形分配:获得回显信息', '7');
INSERT INTO `tbl_permission` VALUES ('18', 'role:treePermission_alter:ajax', '树形分配:修改', '7');
INSERT INTO `tbl_permission` VALUES ('19', 'user:userAdd:ajax', '用户添加', '5');
INSERT INTO `tbl_permission` VALUES ('20', 'role:userDelete', '用户删除', '5');
INSERT INTO `tbl_permission` VALUES ('21', 'user:getUserById:ajax', '编辑时回显用户信息', '5');
INSERT INTO `tbl_permission` VALUES ('22', 'role:roleBrowse', '角色浏览', '5');
INSERT INTO `tbl_permission` VALUES ('23', 'user:getUserByKeyWord:ajax', '通过关键字返回用户', '5');
INSERT INTO `tbl_permission` VALUES ('24', 'user:userEdit:ajax', '用户编辑', '5');
INSERT INTO `tbl_permission` VALUES ('25', 'permission:permissionTest', '权限测试页面', '8');

/*
 Navicat Premium Data Transfer

 Source Server         : @200
 Source Server Type    : MySQL
 Source Server Version : 50637
 Source Host           : 192.168.10.200:3306
 Source Schema         : oneclick_release

 Target Server Type    : MySQL
 Target Server Version : 50637
 File Encoding         : 65001

 Date: 19/08/2021 15:39:59
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_menu
-- ----------------------------
DROP TABLE IF EXISTS `tb_menu`;
CREATE TABLE `tb_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '菜单/按钮ID',
  `parent_id` bigint(20) DEFAULT '0' COMMENT '上级菜单ID',
  `menu_name` varchar(50) NOT NULL COMMENT '菜单/按钮名称',
  `url` varchar(50) DEFAULT NULL COMMENT '菜单URL',
  `perms` text COMMENT '权限标识',
  `icon` varchar(50) DEFAULT NULL COMMENT '图标',
  `type` char(2) NOT NULL COMMENT '类型 0菜单 1按钮',
  `order_num` bigint(20) DEFAULT NULL COMMENT '排序',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modified_time` datetime DEFAULT NULL COMMENT '修改时间',
  `available` int(11) DEFAULT '1' COMMENT '0：不可用，1：可用',
  `open` int(1) DEFAULT '0' COMMENT '0:不展开，1：展开',
  `deleted` smallint(1) NOT NULL DEFAULT '0',
  `insert_uid` varchar(20) NOT NULL DEFAULT '0',
  `update_uid` varchar(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='菜单表';

-- ----------------------------
-- Records of tb_menu
-- ----------------------------
BEGIN;
INSERT INTO `tb_menu` VALUES (1, 0, '系统管理', NULL, NULL, 'el-icon-setting', '0', 1, '2021-08-19 14:49:31', '2021-08-19 14:49:35', 1, 0, 0, '0', '1');
INSERT INTO `tb_menu` VALUES (2, 1, '菜单权限', '/menus', 'menu:tree', 'el-icon-help', '0', 1, '2020-09-27 18:37:56', '2020-09-27 18:37:56', 1, 0, 0, '0', '0');
INSERT INTO `tb_menu` VALUES (3, 2, '添加菜单', NULL, 'menu:add', 'el-icon-s-opportunity', '1', 1, '2020-09-27 18:37:56', '2020-09-27 18:37:56', 1, 0, 0, '0', '0');
INSERT INTO `tb_menu` VALUES (4, 2, '编辑菜单', NULL, 'menu:edit', 'el-icon-edit', '1', 2, '2020-09-27 18:37:56', '2020-09-27 18:37:56', 1, 0, 0, '0', '0');
INSERT INTO `tb_menu` VALUES (5, 2, '修改菜单', NULL, 'menu:update', 'el-icon-refresh', '1', 3, '2020-09-27 18:37:56', '2020-09-27 18:37:56', 1, 0, 0, '0', '0');
INSERT INTO `tb_menu` VALUES (6, 2, '导出菜单', NULL, 'menu:export', 'el-icon-edit', '1', 4, '2020-09-27 18:37:56', '2020-09-27 18:37:56', 1, 0, 0, '0', '0');
INSERT INTO `tb_menu` VALUES (7, 2, '删除菜单', NULL, 'menu:delete', 'el-icon-delete', '1', 5, '2020-09-27 18:37:56', '2020-09-27 18:37:56', 1, 0, 0, '0', '0');
INSERT INTO `tb_menu` VALUES (8, 1, '用户管理', '/users', 'user:listPage', 'el-icon-user', '0', 2, '2020-09-27 18:37:56', '2020-09-27 18:37:56', 1, 0, 0, '0', '0');
INSERT INTO `tb_menu` VALUES (9, 8, '用户添加', NULL, 'user:add', 'el-icon-plus', '1', 1, '2020-09-27 18:37:56', '2020-09-27 18:37:56', 1, 0, 0, '0', '0');
INSERT INTO `tb_menu` VALUES (10, 8, '用户删除', NULL, 'user:delete', 'el-icon-delete', '1', 2, '2020-09-27 18:37:56', '2020-09-27 18:37:56', 1, 0, 0, '0', '0');
INSERT INTO `tb_menu` VALUES (11, 8, '用户编辑', NULL, 'user:edit', 'el-icon-edit', '1', 3, '2020-09-27 18:37:56', '2020-09-27 18:37:56', 1, 0, 0, '0', '0');
INSERT INTO `tb_menu` VALUES (12, 8, '禁用用户', NULL, 'user:status', 'el-icon-circle-close', '1', 4, '2020-09-27 18:37:56', '2020-09-27 18:37:56', 1, 0, 0, '0', '0');
INSERT INTO `tb_menu` VALUES (13, 8, '用户更新', NULL, 'user:update', 'el-icon-refresh', '1', 5, '2020-09-27 18:37:56', '2020-09-27 18:37:56', 1, 0, 0, '0', '0');
INSERT INTO `tb_menu` VALUES (14, 8, '导出表格', NULL, 'user:export', 'el-icon-edit', '1', 6, '2020-09-27 18:37:56', '2020-09-27 18:37:56', 1, 0, 0, '0', '0');
INSERT INTO `tb_menu` VALUES (15, 8, '分配角色', NULL, 'user:assign', 'el-icon-s-tools', '1', 7, '2020-09-27 18:37:56', '2020-09-27 18:37:56', 1, 0, 0, '0', '0');
INSERT INTO `tb_menu` VALUES (16, 8, '上传头像', NULL, 'upload:image', 'el-icon-picture', '1', 8, '2020-09-27 18:37:56', '2020-09-27 18:37:56', 1, 0, 0, '0', '0');
INSERT INTO `tb_menu` VALUES (17, 8, '头像/密码修改', NULL, 'user:modifyAvatarAndPwd', 'el-icon-refresh', '1', 9, '2020-09-27 18:37:56', '2020-09-27 18:37:56', 1, 0, 0, '0', '0');
INSERT INTO `tb_menu` VALUES (18, 8, '更新头像', NULL, 'user:modifyUserAvatar', 'el-icon-refresh', '1', 10, '2020-09-27 18:37:56', '2020-09-27 18:37:56', 1, 0, 0, '0', '0');
INSERT INTO `tb_menu` VALUES (19, 1, '角色管理', '/roles', 'role:findRoleList', 'el-icon-postcard', '0', 3, '2020-09-27 18:37:56', '2020-09-27 18:37:56', 1, 0, 0, '0', '0');
INSERT INTO `tb_menu` VALUES (20, 19, '角色编辑', NULL, 'role:edit', 'el-icon-edit', '1', 1, '2020-09-27 18:37:56', '2020-09-27 18:37:56', 1, 0, 0, '0', '0');
INSERT INTO `tb_menu` VALUES (21, 19, '角色删除', NULL, 'role:delete', 'el-icon-delete', '1', 2, '2020-09-27 18:37:56', '2020-09-27 18:37:56', 1, 0, 0, '0', '0');
INSERT INTO `tb_menu` VALUES (22, 19, '分配权限', NULL, 'role:authority', 'el-icon-document-add', '1', 3, '2020-09-27 18:37:56', '2020-09-27 18:37:56', 1, 0, 0, '0', '0');
INSERT INTO `tb_menu` VALUES (23, 19, '添加角色', NULL, 'role:add', 'el-icon-plus', '1', 4, '2020-09-27 18:37:56', '2020-09-27 18:37:56', 1, 0, 0, '0', '0');
INSERT INTO `tb_menu` VALUES (24, 19, '角色更新', NULL, 'role:update', 'el-icon-refresh-left', '1', 5, '2020-09-27 18:37:56', '2020-09-27 18:37:56', 1, 0, 0, '0', '0');
INSERT INTO `tb_menu` VALUES (25, 19, '状态更新', NULL, 'role:status', 'el-icon-refresh', '1', 6, '2020-09-27 18:37:56', '2020-09-27 18:37:56', 1, 0, 0, '0', '0');
INSERT INTO `tb_menu` VALUES (26, 19, '角色菜单', NULL, 'role:findRoleMenu', NULL, '1', 7, '2020-09-27 18:37:56', '2020-09-27 18:37:56', 1, 0, 0, '0', '0');
INSERT INTO `tb_menu` VALUES (27, 1, '图标管理', '/icons', NULL, 'el-icon-star-off', '0', 4, '2020-09-27 18:37:56', '2020-09-27 18:37:56', 1, 0, 0, '0', '0');
COMMIT;

-- ----------------------------
-- Table structure for tb_role
-- ----------------------------
DROP TABLE IF EXISTS `tb_role`;
CREATE TABLE `tb_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(100) NOT NULL COMMENT '角色名称',
  `remark` varchar(100) DEFAULT NULL COMMENT '角色描述',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `modified_time` datetime DEFAULT NULL COMMENT '修改时间',
  `status` int(1) DEFAULT '1' COMMENT '是否可用,0:不可用，1：可用',
  `deleted` smallint(1) NOT NULL DEFAULT '0',
  `insert_uid` varchar(20) DEFAULT NULL,
  `update_uid` varchar(20) DEFAULT NULL,
  `perm_rule_type` smallint(1) DEFAULT NULL COMMENT '数据权限：0 默认无限制 1 添加人 2 添加人及直属上级 3 添加人及直属下级 4 本部门',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='角色表';

-- ----------------------------
-- Records of tb_role
-- ----------------------------
BEGIN;
INSERT INTO `tb_role` VALUES (1, '系统管理员', '系统管理', '2021-08-19 14:48:33', '2021-08-19 14:48:39', 1, 0, '1', '1', 0);
COMMIT;

-- ----------------------------
-- Table structure for tb_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `tb_role_menu`;
CREATE TABLE `tb_role_menu` (
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `menu_id` bigint(20) NOT NULL COMMENT '菜单/按钮ID',
  `create_time` datetime DEFAULT NULL,
  `modified_time` datetime DEFAULT NULL,
  `deleted` smallint(1) NOT NULL DEFAULT '0',
  `insert_uid` varchar(20) DEFAULT NULL,
  `update_uid` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='角色菜单关联表';

-- ----------------------------
-- Records of tb_role_menu
-- ----------------------------
BEGIN;
INSERT INTO `tb_role_menu` VALUES (1, 1, '2021-08-19 14:50:30', '2021-08-19 14:50:32', 0, '1', '1');
INSERT INTO `tb_role_menu` VALUES (1, 2, '2021-08-19 14:55:06', '2021-08-19 14:55:06', 0, '1', '1');
INSERT INTO `tb_role_menu` VALUES (1, 3, '2021-08-19 14:55:06', '2021-08-19 14:55:06', 0, '1', '1');
INSERT INTO `tb_role_menu` VALUES (1, 4, '2021-08-19 14:55:06', '2021-08-19 14:55:06', 0, '1', '1');
INSERT INTO `tb_role_menu` VALUES (1, 5, '2021-08-19 14:55:06', '2021-08-19 14:55:06', 0, '1', '1');
INSERT INTO `tb_role_menu` VALUES (1, 6, '2021-08-19 14:55:06', '2021-08-19 14:55:06', 0, '1', '1');
INSERT INTO `tb_role_menu` VALUES (1, 7, '2021-08-19 14:55:07', '2021-08-19 14:55:07', 0, '1', '1');
INSERT INTO `tb_role_menu` VALUES (1, 8, '2021-08-19 14:55:07', '2021-08-19 14:55:07', 0, '1', '1');
INSERT INTO `tb_role_menu` VALUES (1, 9, '2021-08-19 14:55:07', '2021-08-19 14:55:07', 0, '1', '1');
INSERT INTO `tb_role_menu` VALUES (1, 10, '2021-08-19 14:55:07', '2021-08-19 14:55:07', 0, '1', '1');
INSERT INTO `tb_role_menu` VALUES (1, 11, '2021-08-19 14:55:07', '2021-08-19 14:55:07', 0, '1', '1');
INSERT INTO `tb_role_menu` VALUES (1, 12, '2021-08-19 14:55:07', '2021-08-19 14:55:07', 0, '1', '1');
INSERT INTO `tb_role_menu` VALUES (1, 13, '2021-08-19 14:55:07', '2021-08-19 14:55:07', 0, '1', '1');
INSERT INTO `tb_role_menu` VALUES (1, 14, '2021-08-19 14:55:07', '2021-08-19 14:55:07', 0, '1', '1');
INSERT INTO `tb_role_menu` VALUES (1, 15, '2021-08-19 14:55:07', '2021-08-19 14:55:07', 0, '1', '1');
INSERT INTO `tb_role_menu` VALUES (1, 16, '2021-08-19 14:55:07', '2021-08-19 14:55:07', 0, '1', '1');
INSERT INTO `tb_role_menu` VALUES (1, 17, '2021-08-19 14:55:07', '2021-08-19 14:55:07', 0, '1', '1');
INSERT INTO `tb_role_menu` VALUES (1, 18, '2021-08-19 14:55:48', '2021-08-19 14:55:48', 0, '1', '1');
INSERT INTO `tb_role_menu` VALUES (1, 19, '2021-08-19 14:55:48', '2021-08-19 14:55:48', 0, '1', '1');
INSERT INTO `tb_role_menu` VALUES (1, 20, '2021-08-19 14:55:48', '2021-08-19 14:55:48', 0, '1', '1');
INSERT INTO `tb_role_menu` VALUES (1, 21, '2021-08-19 14:55:48', '2021-08-19 14:55:48', 0, '1', '1');
INSERT INTO `tb_role_menu` VALUES (1, 22, '2021-08-19 14:55:48', '2021-08-19 14:55:48', 0, '1', '1');
INSERT INTO `tb_role_menu` VALUES (1, 23, '2021-08-19 14:55:48', '2021-08-19 14:55:48', 0, '1', '1');
INSERT INTO `tb_role_menu` VALUES (1, 24, '2021-08-19 14:55:49', '2021-08-19 14:55:49', 0, '1', '1');
INSERT INTO `tb_role_menu` VALUES (1, 25, '2021-08-19 14:55:49', '2021-08-19 14:55:49', 0, '1', '1');
INSERT INTO `tb_role_menu` VALUES (1, 26, '2021-08-19 14:55:49', '2021-08-19 14:55:49', 0, '1', '1');
INSERT INTO `tb_role_menu` VALUES (1, 27, '2021-08-19 14:55:49', '2021-08-19 14:55:49', 0, '1', '1');
COMMIT;

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `user_num` varchar(20) DEFAULT NULL COMMENT '用户编号',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `nickname` varchar(20) DEFAULT NULL COMMENT '昵称',
  `salt` varchar(255) NOT NULL COMMENT '盐',
  `password` varchar(128) NOT NULL COMMENT '密码',
  `email` varchar(128) DEFAULT NULL COMMENT '邮箱',
  `avatar` varchar(250) DEFAULT NULL COMMENT '头像',
  `phone_number` varchar(20) NOT NULL COMMENT '联系电话',
  `status` smallint(1) NOT NULL DEFAULT '1' COMMENT '状态 0锁定 1有效',
  `sex` smallint(1) DEFAULT NULL COMMENT '性别 0男 1女 2保密',
  `type` smallint(1) NOT NULL DEFAULT '1' COMMENT '0:超级管理员，1：系统用户',
  `birthday` date DEFAULT NULL COMMENT '生日',
  `deleted` smallint(1) NOT NULL DEFAULT '0' COMMENT '是否删除 0否 1是',
  `insert_uid` bigint(20) DEFAULT NULL,
  `update_uid` bigint(20) DEFAULT NULL,
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `mobileUnique` (`phone_number`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='用户表';

-- ----------------------------
-- Records of tb_user
-- ----------------------------
BEGIN;
INSERT INTO `tb_user` VALUES (1, '1629355625107', '张三', '张三', '123456', 'fa89e5302256d3da3007cad3234742a4', NULL, NULL, '17717553457', 1, 0, 1, '2020-05-13', 0, 1, 1, '2021-07-14 16:13:54', '2021-08-19 14:47:41', '1629355625107');
INSERT INTO `tb_user` VALUES (3, '1629358565755', '李四', '李四', '14040ea2-90b8-4366-9408-d70c8f9b', '402995dc74a56ef949b3f3796e10184d', '123456@qq.com', NULL, '18888888888', 1, 0, 1, NULL, 0, 1, 1, '2021-08-19 15:36:06', '2021-08-19 15:36:06', '1629358565756');
COMMIT;

-- ----------------------------
-- Table structure for tb_user_role
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_role`;
CREATE TABLE `tb_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `create_time` datetime DEFAULT NULL,
  `modified_time` datetime DEFAULT NULL,
  `insert_uid` varchar(20) DEFAULT NULL,
  `update_uid` varchar(20) DEFAULT NULL,
  `deleted` smallint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='用户角色关联表';

-- ----------------------------
-- Records of tb_user_role
-- ----------------------------
BEGIN;
INSERT INTO `tb_user_role` VALUES (1, 1, 1, '2021-08-19 14:50:06', '2021-08-19 14:50:08', '1', '1', 0);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;

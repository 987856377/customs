/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 80012
Source Host           : 127.0.0.1:3306
Source Database       : customs

Target Server Type    : MYSQL
Target Server Version : 80012
File Encoding         : 65001

Date: 2019-07-11 10:41:51
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for announce
-- ----------------------------
DROP TABLE IF EXISTS `announce`;
CREATE TABLE `announce` (
  `a_u_id` bigint(20) NOT NULL,
  `a_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `a_title` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `a_content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `a_date` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`a_id`),
  UNIQUE KEY `a_id_UNIQUE` (`a_id`) USING BTREE,
  KEY `fK_a_u_id` (`a_u_id`),
  CONSTRAINT `fK_a_u_id` FOREIGN KEY (`a_u_id`) REFERENCES `user` (`u_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of announce
-- ----------------------------
INSERT INTO `announce` VALUES ('1', '1', '本系统使用通知', '本系统服务于政府部门, 属垃圾分类管理', '2019-07-05 00:00:00');
INSERT INTO `announce` VALUES ('1', '7', '曲儿若', '是费收入的非官方发电公司阿什顿', '2019-07-05 17:55:37');
INSERT INTO `announce` VALUES ('1', '10', '公司的风格', '收费的故事房东热特', '2019-07-06 09:38:55');

-- ----------------------------
-- Table structure for classify
-- ----------------------------
DROP TABLE IF EXISTS `classify`;
CREATE TABLE `classify` (
  `c_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `c_name` varchar(32) COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of classify
-- ----------------------------
INSERT INTO `classify` VALUES ('1', '可回收垃圾');
INSERT INTO `classify` VALUES ('2', '厨余垃圾');
INSERT INTO `classify` VALUES ('3', '有害垃圾');
INSERT INTO `classify` VALUES ('4', '其他垃圾');
INSERT INTO `classify` VALUES ('5', '干垃圾');
INSERT INTO `classify` VALUES ('6', '湿垃圾');
INSERT INTO `classify` VALUES ('7', '特殊垃圾');

-- ----------------------------
-- Table structure for junk
-- ----------------------------
DROP TABLE IF EXISTS `junk`;
CREATE TABLE `junk` (
  `j_c_id` bigint(20) NOT NULL,
  `j_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `j_name` varchar(32) COLLATE utf8mb4_general_ci NOT NULL,
  `j_type` varchar(32) COLLATE utf8mb4_general_ci NOT NULL,
  `j_describe` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `j_date` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`j_id`,`j_c_id`),
  UNIQUE KEY `j_id_UNIQUE` (`j_id`) USING BTREE,
  KEY `j_name_UNIQUE` (`j_name`) USING BTREE,
  KEY `j_type` (`j_type`) USING BTREE,
  KEY `j_c_id_UNIQUE` (`j_c_id`) USING BTREE,
  CONSTRAINT `fk_j_c_id` FOREIGN KEY (`j_c_id`) REFERENCES `classify` (`c_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of junk
-- ----------------------------
INSERT INTO `junk` VALUES ('1', '1', '报纸', '纸类', '报纸恶趣味去玩儿群翁', '2019-07-06 13:22:28');
INSERT INTO `junk` VALUES ('7', '3', '厕纸', '纸类', '发生的无非常须知发送到', '2019-07-06 20:23:21');
INSERT INTO `junk` VALUES ('4', '4', '头发', '财产保险', '大法师打发而去', '2019-07-07 09:21:21');
INSERT INTO `junk` VALUES ('4', '5', '石块', '石头类', '去玩儿去玩儿去', '2019-07-07 14:05:59');

-- ----------------------------
-- Table structure for news
-- ----------------------------
DROP TABLE IF EXISTS `news`;
CREATE TABLE `news` (
  `n_u_id` bigint(20) NOT NULL,
  `n_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `n_title` varchar(64) COLLATE utf8mb4_general_ci NOT NULL,
  `n_content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `n_date` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`n_id`),
  UNIQUE KEY `n_id_UNIQUE` (`n_id`) USING BTREE,
  KEY `fk_n_u_id` (`n_u_id`),
  CONSTRAINT `fk_n_u_id` FOREIGN KEY (`n_u_id`) REFERENCES `user` (`u_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of news
-- ----------------------------
INSERT INTO `news` VALUES ('1', '4', '乏味故事', '二维若群的分公司法定电子相册阿萨德', '2019-07-05 17:59:50');
INSERT INTO `news` VALUES ('1', '6', '而亲仁翁', '奥术大师大无程序中', '2019-07-05 19:38:00');
INSERT INTO `news` VALUES ('54', '9', '秩序', '阿斯蒂芬无人区如图测', '2019-07-06 09:02:00');
INSERT INTO `news` VALUES ('1', '11', '叶荣添', '儿童业态', '2019-07-07 14:08:18');

-- ----------------------------
-- Table structure for reply
-- ----------------------------
DROP TABLE IF EXISTS `reply`;
CREATE TABLE `reply` (
  `r_u_id` bigint(20) NOT NULL,
  `r_t_id` bigint(20) NOT NULL,
  `r_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `r_content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `r_date` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`r_id`),
  UNIQUE KEY `r_id_UNIQUE` (`r_id`) USING BTREE,
  KEY `fk_r_u_id` (`r_u_id`),
  KEY `fk_r_t_id` (`r_t_id`),
  CONSTRAINT `fk_r_t_id` FOREIGN KEY (`r_t_id`) REFERENCES `topic` (`t_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_r_u_id` FOREIGN KEY (`r_u_id`) REFERENCES `user` (`u_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of reply
-- ----------------------------
INSERT INTO `reply` VALUES ('54', '2', '4', '地方', '2019-07-05 22:29:36');
INSERT INTO `reply` VALUES ('55', '3', '5', '多发点', '2019-07-06 09:14:29');
INSERT INTO `reply` VALUES ('53', '4', '6', '发的格式的', '2019-07-10 16:06:15');
INSERT INTO `reply` VALUES ('53', '5', '7', '小从V字形从', '2019-07-10 16:17:53');
INSERT INTO `reply` VALUES ('1', '4', '8', '达到', '2019-07-10 16:41:36');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `r_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `r_name` varchar(12) COLLATE utf8mb4_general_ci NOT NULL,
  `r_flag` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`r_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', 'DBA', 'ROLE_DBA');
INSERT INTO `role` VALUES ('2', 'ADMIN', 'ROLE_ADMIN');
INSERT INTO `role` VALUES ('3', 'USER', 'ROLE_USER');

-- ----------------------------
-- Table structure for topic
-- ----------------------------
DROP TABLE IF EXISTS `topic`;
CREATE TABLE `topic` (
  `t_u_id` bigint(20) NOT NULL,
  `t_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `t_title` varchar(64) COLLATE utf8mb4_general_ci NOT NULL,
  `t_content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `t_date` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`t_id`),
  UNIQUE KEY `t_id_UNIQUE` (`t_id`) USING BTREE,
  KEY `fk_t_u_id` (`t_u_id`),
  CONSTRAINT `fk_t_u_id` FOREIGN KEY (`t_u_id`) REFERENCES `user` (`u_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of topic
-- ----------------------------
INSERT INTO `topic` VALUES ('1', '2', '阿什顿发', '程序', '2019-07-05 22:18:02');
INSERT INTO `topic` VALUES ('53', '3', '阿凡达', '为', '2019-07-05 22:18:18');
INSERT INTO `topic` VALUES ('53', '4', '大叫好', '刷卡机的哈时空裂缝打开流式', '2019-07-10 16:05:42');
INSERT INTO `topic` VALUES ('53', '5', '埃里克森的', '诶怄气哟喂哦i驱蚊器', '2019-07-10 16:17:43');
INSERT INTO `topic` VALUES ('1', '6', '成粘丝的', '代发发送到韦尔奇翁', '2019-07-10 16:41:12');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `u_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `u_username` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `u_password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `u_register_date` datetime NOT NULL,
  PRIMARY KEY (`u_id`),
  UNIQUE KEY `u_id_UNIQUE` (`u_id`),
  UNIQUE KEY `u_username_UNIQUE` (`u_username`)
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'url', '$2a$10$xyLIygV6FmxiFwKBWl/KAeoX5OHteVWhUcFcpTBoUi4.pEi5PtQrC', '2019-06-06 00:00:00');
INSERT INTO `user` VALUES ('53', 'franklin', '$2a$10$.Lopo09DBhieLM4MtIuSKuyMM6DiEqbozBEYIZYDBfY0zJIWa06ou', '2019-06-23 00:00:00');
INSERT INTO `user` VALUES ('54', 'jackson', '$2a$10$6UJfVzirvlMAH4vrFu5yreZNVvo91HP8EAOhIcJ.5DGdzouk2Epk6', '2019-07-05 00:00:00');
INSERT INTO `user` VALUES ('55', 'sarry', '$2a$10$Mzm1XIcfy2CepftLxk9W8.8.FA4TatrNIHXiQJsXczEM6kLivavvS', '2019-07-05 12:16:38');
INSERT INTO `user` VALUES ('56', 'lucy', '$2a$10$0cV2qs426Sgnq9udVOKZOuAwVVyYZGX5zxGwkjv9hH5iE33E2nHrG', '2019-07-07 11:01:40');
INSERT INTO `user` VALUES ('57', 'robot', '$2a$10$kBiz8XgDlE8z8JH6crl78eYgL8WcvuCXEiGB49cRNzMBtSiE6KnQO', '2019-07-07 11:07:32');
INSERT INTO `user` VALUES ('58', 'frank', '$2a$10$jIhXSWo9QeNMnjDiYIDsbOckyOB5ul3VVo3rKSz7PTBwABq1dm4PO', '2019-07-08 19:12:49');

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
  `ui_id` bigint(20) NOT NULL,
  `ui_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `ui_identity` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `ui_phone` char(11) COLLATE utf8mb4_general_ci NOT NULL,
  `ui_mail` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `ui_start_date` date NOT NULL,
  `ui_end_date` date NOT NULL,
  PRIMARY KEY (`ui_id`),
  UNIQUE KEY `ui_phone_UNIQUE` (`ui_phone`),
  UNIQUE KEY `ui_identity_UNIQUE` (`ui_identity`),
  UNIQUE KEY `ui_id_UNIQUE` (`ui_id`),
  CONSTRAINT `fk_user_u_id` FOREIGN KEY (`ui_id`) REFERENCES `user` (`u_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO `user_info` VALUES ('1', '许振奎', '410928199702151659', '13783939561', 'ahaxzk@gmail', '2014-12-31', '2019-12-31');
INSERT INTO `user_info` VALUES ('53', '张昌帅', '410928199702151654', '13783939564', '987856377@qq.com', '2014-12-31', '2019-12-31');
INSERT INTO `user_info` VALUES ('54', '许振奎', '410928199702151655', '13783939565', '987856377@qq.com', '2014-12-31', '2019-12-31');
INSERT INTO `user_info` VALUES ('55', '许振奎', '410928199702151656', '13783939566', '981728851@qq.com', '2014-12-31', '2019-12-31');
INSERT INTO `user_info` VALUES ('56', '许振奎', '410928199702151657', '13783939567', '987856377@qq.com', '2014-12-31', '2019-12-31');
INSERT INTO `user_info` VALUES ('57', '许振奎', '410928199702151658', '13783939568', '987856377@qq.com', '2014-12-31', '2019-12-31');
INSERT INTO `user_info` VALUES ('58', '许振奎', '410928199702151661', '13783939569', '987856377@qq.com', '2014-12-31', '2019-12-31');

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `ur_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `ur_u_id` bigint(20) NOT NULL,
  `ur_r_id` bigint(20) NOT NULL,
  PRIMARY KEY (`ur_id`),
  UNIQUE KEY `ur_id_UNIQUE` (`ur_id`),
  KEY `fk_user_u_id_idx` (`ur_u_id`),
  KEY `fk_role_r_id_idx` (`ur_r_id`),
  CONSTRAINT `fk_role_ur_r_id` FOREIGN KEY (`ur_r_id`) REFERENCES `role` (`r_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_user_ur_u_id` FOREIGN KEY (`ur_u_id`) REFERENCES `user` (`u_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=72 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES ('1', '1', '1');
INSERT INTO `user_role` VALUES ('66', '53', '2');
INSERT INTO `user_role` VALUES ('67', '54', '2');
INSERT INTO `user_role` VALUES ('68', '55', '3');
INSERT INTO `user_role` VALUES ('69', '56', '3');
INSERT INTO `user_role` VALUES ('70', '57', '3');
INSERT INTO `user_role` VALUES ('71', '58', '3');

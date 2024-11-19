/*
 Navicat Premium Dump SQL

 Source Server         : 8.138.89.192
 Source Server Type    : MySQL
 Source Server Version : 50737 (5.7.37)
 Source Host           : 8.138.89.192:3306
 Source Schema         : base_cart

 Target Server Type    : MySQL
 Target Server Version : 50737 (5.7.37)
 File Encoding         : 65001

 Date: 19/11/2024 15:16:16
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for js_cart_info
-- ----------------------------
DROP TABLE IF EXISTS `js_cart_info`;
CREATE TABLE `js_cart_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '序号',
  `cart_id` bigint(20) DEFAULT NULL COMMENT '购物袋编号',
  `user_id` int(11) DEFAULT NULL COMMENT '用户编码',
  `goods_code` int(11) DEFAULT NULL COMMENT '商品编码',
  `goods_id` int(11) DEFAULT NULL COMMENT '商品id',
  `sku_id` int(11) DEFAULT NULL COMMENT 'sku编码',
  `sku_num` int(11) DEFAULT NULL COMMENT '商品购买数目',
  `goods_version` int(11) DEFAULT NULL COMMENT '商品版本',
  `pt_id` int(11) DEFAULT NULL COMMENT '拼团ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `barcode` bigint(20) DEFAULT NULL COMMENT '条码',
  `trade_id` varchar(64) DEFAULT NULL COMMENT '链路ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=70 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='购物车信息表';

-- ----------------------------
-- Records of js_cart_info
-- ----------------------------
BEGIN;
INSERT INTO `js_cart_info` (`id`, `cart_id`, `user_id`, `goods_code`, `goods_id`, `sku_id`, `sku_num`, `goods_version`, `pt_id`, `create_time`, `update_time`, `barcode`, `trade_id`) VALUES (69, 1712403262, 1122, 2, 2, 2, 3, 4, 5, '2024-04-06 19:34:23', NULL, 2, '9b25918d-36a4-481a-bebc-95c92f61b208');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;

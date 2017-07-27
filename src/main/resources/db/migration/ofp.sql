/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50718
Source Host           : 127.0.0.1:3306
Source Database       : webside

Target Server Type    : MYSQL
Target Server Version : 50718
File Encoding         : 65001

Date: 2017-07-17 23:23:30
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for interest_rate
-- ----------------------------
DROP TABLE IF EXISTS `interest_rate`;
CREATE TABLE `interest_rate` (
  `INTEREST_RATE_ID` int(11) NOT NULL AUTO_INCREMENT,
  `RATE` decimal(10,2) DEFAULT NULL COMMENT '利率,算利息的',
  PRIMARY KEY (`INTEREST_RATE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for customer
-- ----------------------------
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer` (
  `CUSTOMER_ID` int(11) NOT NULL AUTO_INCREMENT,
  `CUSTOMER_NAME` varchar(50) DEFAULT NULL COMMENT '客户名称',
  `TELEPHONE` varchar(30) DEFAULT NULL COMMENT '电话',
  `COUNTRY` varchar(50) DEFAULT NULL COMMENT '国家',
  `IS_DELETE` int(1) DEFAULT NULL COMMENT '逻辑删除（1.删除，0，否）',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `MODIFY_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  `CREATE_USER` int(11) DEFAULT NULL COMMENT '创建人',
  `MODIFY_USER` int(11) DEFAULT NULL COMMENT '修改人',
  `DESCRIPTION` varchar(500) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`CUSTOMER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of customer
-- ----------------------------

-- ----------------------------
-- Table structure for product_type
-- ----------------------------
DROP TABLE IF EXISTS `product_type`;
CREATE TABLE `product_type` (
  `PRODUCT_TYPE_ID` int(11) NOT NULL AUTO_INCREMENT,
  `CN_NAME` varchar(100) DEFAULT NULL COMMENT '产品类型中文名称',
  `EN_NAME` varchar(100) DEFAULT NULL COMMENT '产品类型英文名称',
  `PARENT_ID` int(11) DEFAULT NULL COMMENT '父类',
  `LEVEL` int(1) DEFAULT NULL COMMENT '等级',
  `ORDERBY` int(4) DEFAULT NULL COMMENT '排序',
  `IS_DELETE` int(1) DEFAULT NULL COMMENT '逻辑删除（1.删除，0，否）',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `MODIFY_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  `CREATE_USER` int(11) DEFAULT NULL COMMENT '创建人',
  `MODIFY_USER` int(11) DEFAULT NULL COMMENT '修改人',
  `DESCRIPTION` varchar(500) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`PRODUCT_TYPE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `PRODUCT_ID` int(11) NOT NULL AUTO_INCREMENT,
  `PRODUCT_TYPE_ID` int(11) DEFAULT NULL COMMENT '产品类型ID,外键',
  `PRODUCT_CODE` varchar(40) DEFAULT NULL COMMENT '商品编码',
  `FACTORY_CODE` varchar(40) DEFAULT NULL COMMENT '工厂编码',
  `UNIT` varchar(10) DEFAULT NULL COMMENT '单位',
  `CUSTOMS_CODE` varchar(40) DEFAULT NULL COMMENT '海关编码',
  `USD_PRICE` decimal(10,2) DEFAULT NULL COMMENT '美金单价',
  `CN_NAME` varchar(100) DEFAULT NULL COMMENT '中文名称',
  `EN_NAME` varchar(40) DEFAULT NULL COMMENT '英文名称',
  `VAT_RATE` decimal(10,2) DEFAULT NULL COMMENT '增值税率',
  `BUY_PRICE` decimal(10,2) DEFAULT NULL COMMENT '收购单价',
  `WEIGHT` int(8) DEFAULT NULL COMMENT '重量（单位：g）',
  `VOLUME` int(8) DEFAULT NULL COMMENT '容量（单位：ml）',
  `TOP` decimal(10,0) DEFAULT NULL COMMENT 'TOP（单位：mm）',
  `BOTTOM` decimal(10,0) DEFAULT NULL COMMENT 'BOTTOM（单位：mm）',
  `HEIGHT` decimal(10,0) DEFAULT NULL COMMENT 'HEIGHT(单位：mm)',
  `LENGTH` decimal(10,2) DEFAULT NULL COMMENT '外包装长度（单位:cm）',
  `WIDTH` decimal(10,2) DEFAULT NULL COMMENT '外包装宽度（单位：cm）',
  `PACK_HEIGHT` decimal(10,2) DEFAULT NULL COMMENT '外包装高度（单位：cm）',
  `GW` decimal(10,2) DEFAULT NULL COMMENT 'GW',
  `PACKING_RATE` decimal(10,2) DEFAULT NULL COMMENT '装箱率',
  `TAX_REBATE_RATE` decimal(10,2) DEFAULT NULL COMMENT '退税率',
  `CBM` decimal(10,4) DEFAULT NULL COMMENT 'CBM',
  `PACKING` varchar(500) DEFAULT NULL COMMENT '包装描述',
  `HD_MAP_URL` varchar(100) DEFAULT NULL COMMENT '产品高清图路径',
  `THUMBNAIL` mediumblob COMMENT '缩略图（最大16M）',
  `QR_CODE_PIC` mediumblob COMMENT '二维码图片',
  `IS_DELETE` int(1) DEFAULT NULL COMMENT '逻辑删除（1.删除，0，否）',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `MODIFY_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  `CREATE_USER` int(11) DEFAULT NULL COMMENT '创建人',
  `MODIFY_USER` int(11) DEFAULT NULL COMMENT '修改人',
  `DESCRIPTION` varchar(500) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`PRODUCT_ID`),
  KEY `FK_Reference_1` (`PRODUCT_TYPE_ID`),
  CONSTRAINT `FK_Reference_1` FOREIGN KEY (`PRODUCT_TYPE_ID`) REFERENCES `product_type` (`PRODUCT_TYPE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='产品表';

-- ----------------------------
-- Records of product
-- ----------------------------

-- ----------------------------
-- Table structure for quotation_sheet
-- ----------------------------
DROP TABLE IF EXISTS `quotation_sheet`;
CREATE TABLE `quotation_sheet` (
  `QUOTATION_SHEET_ID` int(11) NOT NULL AUTO_INCREMENT,
  `CUSTOMER_ID` int(11) DEFAULT NULL COMMENT '客户ID,外键',
  `INTEREST_RATE_ID` int(11) DEFAULT NULL COMMENT '利率ID,外键',
  `QUOTATION_SHEET_CODE` varchar(30) DEFAULT NULL COMMENT '报价单号',
  `QUOTATION_DATE` date DEFAULT NULL COMMENT '报价日期',
  `PRICE_TERMS` varchar(30) DEFAULT NULL COMMENT '价格术语',
  `CURRENCY` varchar(20) DEFAULT NULL COMMENT '币种',
  `EXCHANGE_RATE` decimal(10,2) DEFAULT NULL COMMENT '汇率',
  `EXPIRATION_DATE` int(6) DEFAULT NULL COMMENT '有效期限',
  `PAY_MODE` varchar(20) DEFAULT NULL COMMENT '付款方式',
  `RESOURCE` varchar(50) DEFAULT NULL COMMENT '起运地',
  `DEST` varchar(50) DEFAULT NULL COMMENT '目的地',
  `DELIVERY_DATE` int(6) DEFAULT NULL COMMENT '交货期限',
  `INSURANCE_COST` decimal(10,2) DEFAULT NULL COMMENT '保险费',
  `FOREIGN_GREIGHT` decimal(10,2) DEFAULT NULL COMMENT '国外运费',
  `HOME_GREIGHT` decimal(10,2) DEFAULT NULL COMMENT '国内运费',
  `OPERATION_COST` decimal(10,2) DEFAULT NULL COMMENT '管理费',
  `COMMISSION` decimal(10,2) DEFAULT NULL COMMENT '佣金',
  `REBATE` decimal(10,2) DEFAULT NULL COMMENT '折扣',
  `TOTAL_CBM` decimal(10,2) DEFAULT NULL COMMENT 'CBM合计',
  `PROFIT` decimal(10,2) DEFAULT NULL COMMENT '利润',
  `SWAP_RATE` decimal(10,2) DEFAULT NULL COMMENT '换汇率',
  `INTEREST_MONTH` int(2) DEFAULT NULL COMMENT '计息月',
  `IS_DELETE` int(1) DEFAULT NULL COMMENT '逻辑删除（1.删除，0，否）',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `MODIFY_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  `CREATE_USER` int(11) DEFAULT NULL COMMENT '创建人',
  `MODIFY_USER` int(11) DEFAULT NULL COMMENT '修改人',
  `DESCRIPTION` varchar(500) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`QUOTATION_SHEET_ID`),
  KEY `FK_Reference_4` (`CUSTOMER_ID`),
  KEY `FK_Reference_5` (`INTEREST_RATE_ID`),
  CONSTRAINT `FK_Reference_4` FOREIGN KEY (`CUSTOMER_ID`) REFERENCES `customer` (`CUSTOMER_ID`),
  CONSTRAINT `FK_Reference_5` FOREIGN KEY (`INTEREST_RATE_ID`) REFERENCES `interest_rate` (`INTEREST_RATE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for quotation_sub_sheet
-- ----------------------------
DROP TABLE IF EXISTS `quotation_sub_sheet`;
CREATE TABLE `quotation_sub_sheet` (
  `QUOTATION_SUB_SHEET_ID` int(11) NOT NULL AUTO_INCREMENT,
  `PRODUCT_ID` int(11) DEFAULT NULL COMMENT '产品ID,外键',
  `QUOTATION_SHEET_ID` int(11) DEFAULT NULL COMMENT '报价单ID,外键',
  `USD_PRICE` decimal(10,2) DEFAULT NULL COMMENT '美金单价',
  `BUY_PRICE` decimal(10,2) DEFAULT NULL COMMENT '收购单价',
  `UNIT` varchar(10) DEFAULT NULL COMMENT '单位',
  `WEIGHT` int(8) DEFAULT NULL COMMENT '重量（单位：g）',
  `VOLUME` int(8) DEFAULT NULL COMMENT '容量（单位：ml）',
  `TOP` decimal(10,0) DEFAULT NULL COMMENT 'TOP（单位：mm）',
  `BOTTOM` decimal(10,0) DEFAULT NULL COMMENT 'BOTTOM（单位：mm）',
  `HEIGHT` decimal(10,0) DEFAULT NULL COMMENT 'HEIGHT(单位：mm)',
  `PACKING` varchar(500) DEFAULT NULL COMMENT 'PACKING',
  `PACKING_RATE` decimal(10,2) DEFAULT NULL COMMENT '装箱率',
  `NUMBER` int(11) DEFAULT NULL COMMENT '数量',
  `PACK_NUM` int(11) DEFAULT NULL COMMENT '箱数',
  `TOTALCBM` decimal(10,4) DEFAULT NULL COMMENT 'TOTALCBM',
  `GW` decimal(10,2) DEFAULT NULL COMMENT 'GW',
  `TOTAL_GW` decimal(10,2) DEFAULT NULL COMMENT '总毛重',
  PRIMARY KEY (`QUOTATION_SUB_SHEET_ID`),
  KEY `FK_Reference_2` (`PRODUCT_ID`),
  KEY `FK_Reference_3` (`QUOTATION_SHEET_ID`),
  CONSTRAINT `FK_Reference_2` FOREIGN KEY (`PRODUCT_ID`) REFERENCES `product` (`PRODUCT_ID`),
  CONSTRAINT `FK_Reference_3` FOREIGN KEY (`QUOTATION_SHEET_ID`) REFERENCES `quotation_sheet` (`QUOTATION_SHEET_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
create DATABASE guide;
use guide;

#消息表
CREATE TABLE `guide_message` (
  `id` int(32) NOT NULL AUTO_INCREMENT,
  `content` varchar(1024) NOT NULL,
  `sendUserId` INT(32) NOT NULL,
  `sendUserName` varchar(32) NOT NULL,
  `receiveUserId` INT(32) NOT NULL,
  `createTime` DATETIME NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

#用户表
CREATE TABLE `guide_user` (
  `id` int(32) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL,
  `email` varchar(128) NOT NULL,
  `password` varchar(256) NOT NULL,
  `createTime` DATETIME NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

#用户引擎表
CREATE TABLE `guide_engine` (
  `id` int(32) NOT NULL AUTO_INCREMENT,
  `userId` INT(32) NOT NULL,
  `engineId` INT(4) NOT NULL,
  `createTime` DATETIME NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

#用户分组表
CREATE TABLE `guide_group` (
  `id` int(32) NOT NULL AUTO_INCREMENT,
  `userId` INT(32) NOT NULL,
  `groupName` VARCHAR(32) NOT NULL,
  `sort` INT(4) NOT NULL,
  `status` INT(4) NOT NULL,
  `createTime` DATETIME NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

#用户分组明细表
CREATE TABLE `guide_group_tag` (
  `id` int(32) NOT NULL AUTO_INCREMENT,
  `userId` INT(32) NOT NULL,
  `groupId` INT(32) NOT NULL,
  `groupName` VARCHAR(32) NOT NULL,
  `tagName` VARCHAR(32) NOT NULL,
  `tagLink` VARCHAR(256) NOT NULL,
  `sort` INT(4) NOT NULL,
  `status` INT(4) NOT NULL,
  `createTime` DATETIME NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
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
CREATE TABLE `bit_user` (
  `id` int(32) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL,
  `nickName` varchar(64) DEFAULT '',
  `avatarUrl` varchar(256) DEFAULT '',
  `email` varchar(128) DEFAULT '',
  `password` varchar(256) DEFAULT '',
  `sessionKey` varchar(256) DEFAULT '',
  `unionId` varchar(256) DEFAULT '',
  `openId` varchar(256) DEFAULT '',
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

#视频表
CREATE TABLE `guide_video` (
  `id` int(32) NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(128) NOT NULL,
  `qiniuKey` VARCHAR(64) NOT NULL,
  `createTime` DATETIME NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

#视频表
CREATE TABLE `guide_article_video` (
  `id` int(32) NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(128) NOT NULL,
  `videoId` int(32),
  `videoImg` VARCHAR(128) DEFAULT '',
  `mdContent` TEXT NOT NULL,
  `content` TEXT NOT NULL,
  `desc` VARCHAR(128) NOT NULL,
  `displayTime` DATETIME NOT NULL,
  `importance` VARCHAR(128) NOT NULL,
  `likeNum` VARCHAR(128) NOT NULL,
  `viewNum` VARCHAR(128) NOT NULL,
  `createTime` DATETIME DEFAULT now(),
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
-- MySQL dump 10.13  Distrib 5.7.17, for macos10.12 (x86_64)
--
-- Host: 47.101.183.63    Database: cinema
-- ------------------------------------------------------
-- Server version	5.7.24

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `activity`
--

SET @@session.sql_mode ='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION';

DROP TABLE IF EXISTS `activity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `activity_name` varchar(45) NOT NULL,
  `a_description` varchar(255) NOT NULL,
  `end_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `coupon_id` int(11) DEFAULT NULL,
  `start_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity`
--

LOCK TABLES `activity` WRITE;
/*!40000 ALTER TABLE `activity` DISABLE KEYS */;
INSERT INTO `activity` VALUES (2,'春季外卖节','春季外卖节','2019-07-23 17:55:59',5,'2019-05-20 17:55:59'),(3,'春季外卖节','春季外卖节','2019-07-23 17:55:59',6,'2019-05-20 17:55:59'),(4,'测试活动','测试活动','2019-07-26 16:00:00',8,'2019-05-20 16:00:00');
/*!40000 ALTER TABLE `activity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_movie`
--

DROP TABLE IF EXISTS `activity_movie`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_movie` (
  `activity_id` int(11) DEFAULT NULL,
  `movie_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_movie`
--

LOCK TABLES `activity_movie` WRITE;
/*!40000 ALTER TABLE `activity_movie` DISABLE KEYS */;
INSERT INTO `activity_movie` VALUES (2,10),(2,11),(2,16),(4,10);
/*!40000 ALTER TABLE `activity_movie` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `coupon`
--

DROP TABLE IF EXISTS `coupon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `coupon` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(45) DEFAULT NULL,
  `target_amount` float DEFAULT NULL,
  `discount_amount` float DEFAULT NULL,
  `start_time` timestamp NOT NULL ON UPDATE CURRENT_TIMESTAMP,
  `end_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `coupon`
--

LOCK TABLES `coupon` WRITE;
/*!40000 ALTER TABLE `coupon` DISABLE KEYS */;
INSERT INTO `coupon` VALUES (1,'测试优惠券','春季电影节',20,5,'2019-05-20 17:47:54','2019-07-23 17:47:59'),(5,'测试优惠券','品质联盟',30,4,'2019-05-20 21:14:46','2019-07-24 21:14:51'),(6,'春节电影节优惠券','电影节优惠券',50,10,'2019-05-20 21:15:11','2019-07-21 21:14:56'),(8,'测试优惠券','123',100,99,'2019-05-20 16:00:00','2019-07-26 16:00:00');
/*!40000 ALTER TABLE `coupon` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `coupon_user`
--

DROP TABLE IF EXISTS `coupon_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `coupon_user` (
  `coupon_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `coupon_user`
--

LOCK TABLES `coupon_user` WRITE;
/*!40000 ALTER TABLE `coupon_user` DISABLE KEYS */;
INSERT INTO `coupon_user` VALUES (8,15),(5,15),(8,15),(6,15),(5,15),(8,15),(6,15);
/*!40000 ALTER TABLE `coupon_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hall`
--

DROP TABLE IF EXISTS `hall`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hall` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `column` int(11) DEFAULT NULL,
  `row` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hall`
--

LOCK TABLES `hall` WRITE;
/*!40000 ALTER TABLE `hall` DISABLE KEYS */;
INSERT INTO `hall` VALUES (1,'1号厅',10,5),(2,'2号厅',12,8);
/*!40000 ALTER TABLE `hall` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `seats`
--

DROP TABLE IF EXISTS `seats`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `seats` (
                               `hall_id` int(11) NOT NULL,
                               `column` int(11) NOT NULL,
                               `row` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `seats`
--

LOCK TABLES `seats` WRITE;
/*!40000 ALTER TABLE `seats` DISABLE KEYS */;
INSERT INTO `seats` VALUES (1,1,1),(1,1,2),(1,1,3),(1,1,4),(1,1,5),(1,2,1),(1,2,2),(1,2,3),(1,2,4),(1,2,5),(1,3,1),(1,3,2),(1,3,3),(1,3,4),(1,3,5),(1,4,1),(1,4,2),(1,4,3),(1,4,4),(1,4,5),(1,5,1),(1,5,2),(1,5,3),(1,5,4),(1,5,5),(1,6,1),(1,6,2),(1,6,3),(1,6,4),(1,6,5),(1,7,1),(1,7,2),(1,7,3),(1,7,4),(1,7,5),(1,8,1),(1,8,2),(1,8,3),(1,8,4),(1,8,5),(1,9,1),(1,9,2),(1,9,3),(1,9,4),(1,9,5),(1,10,1),(1,10,2),(1,10,3),(1,10,4),(1,10,5),(2,1,1),(2,1,2),(2,1,3),(2,1,4),(2,1,5),(2,1,6),(2,1,7),(2,1,8),(2,2,1),(2,2,2),(2,2,3),(2,2,4),(2,2,5),(2,2,6),(2,2,7),(2,2,8),(2,3,1),(2,3,2),(2,3,3),(2,3,4),(2,3,5),(2,3,6),(2,3,7),(2,3,8),(2,4,1),(2,4,2),(2,4,3),(2,4,4),(2,4,5),(2,4,6),(2,4,7),(2,4,8),(2,5,1),(2,5,2),(2,5,3),(2,5,4),(2,5,5),(2,5,6),(2,5,7),(2,5,8),(2,6,1),(2,6,2),(2,6,3),(2,6,4),(2,6,5),(2,6,6),(2,6,7),(2,6,8),(2,7,1),(2,7,2),(2,7,3),(2,7,4),(2,7,5),(2,7,6),(2,7,7),(2,7,8),(2,8,1),(2,8,2),(2,8,3),(2,8,4),(2,8,5),(2,8,6),(2,8,7),(2,8,8),(2,9,1),(2,9,2),(2,9,3),(2,9,4),(2,9,5),(2,9,6),(2,9,7),(2,9,8),(2,10,1),(2,10,2),(2,10,3),(2,10,4),(2,10,5),(2,10,6),(2,10,7),(2,10,8),(2,11,1),(2,11,2),(2,11,3),(2,11,4),(2,11,5),(2,11,6),(2,11,7),(2,11,8),(2,12,1),(2,12,2),(2,12,3),(2,12,4),(2,12,5),(2,12,6),(2,12,7),(2,12,8);
/*!40000 ALTER TABLE `seats` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `refund_strategy`
--

DROP TABLE IF EXISTS `refund_strategy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `refund_strategy` (
                         `id` int(11) NOT NULL AUTO_INCREMENT,
                         `name` varchar(255) NOT NULL,
                         `refundable` tinyint(4) NOT NULL,
                         `available_hour` int(11) NOT NULL default 0,
                         `charge` int(11) NOT NULL,
                         `state` tinyint(1) NOT NULL default 0,
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `refund_strategy`
--

LOCK TABLES `refund_strategy` WRITE;
/*!40000 ALTER TABLE `refund_strategy` DISABLE KEYS */;
INSERT INTO `refund_strategy` VALUES (1,'用户无法退票',0,0,0,0), (2,'退票策略1',1,0,5,1), (3,'退票策略2',1,24,5,0);
/*!40000 ALTER TABLE `refund_strategy` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `movie`
--

DROP TABLE IF EXISTS `movie`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `movie` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `poster_url` varchar(255) DEFAULT NULL,
  `director` varchar(255) DEFAULT NULL,
  `screen_writer` varchar(255) DEFAULT NULL,
  `starring` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `language` varchar(255) DEFAULT NULL,
  `length` int(11) NOT NULL,
  `start_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `name` varchar(255) NOT NULL,
  `description` text,
  `status` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movie`
--

LOCK TABLES `movie` WRITE;
/*!40000 ALTER TABLE `movie` DISABLE KEYS */;
INSERT INTO `movie` VALUES (10,'https://i.loli.net/2019/06/13/5d02122c9e38944366.jpg','西蒙·金伯格','','苏菲·特纳 /詹姆斯·麦卡沃伊 /迈克尔·法斯宾德 /詹妮弗·劳伦斯 ','动作/冒险/科幻','美国','英语',114,'2019-06-06 14:54:31','X战警：黑凤凰','在一次危及生命的太空营救行动中，琴·葛蕾（苏菲·特纳 饰）被神秘的宇宙力量击中，成为最强大的变种人。此后琴不仅要设法掌控日益增长、极不稳定的力量，更要与自己内心的恶魔抗争，她的失控让整个X战警大家庭分崩离析，也让整个星球陷入毁灭的威胁之中…… ',0),(16,'https://i.loli.net/2019/06/13/5d01fadad06bc61936.jpg','F·加里·格雷',NULL,'克里斯·海姆斯沃斯/泰莎·汤普森/丽贝卡·弗格森/连姆·尼森','动作/喜剧/科幻','美国','英语',115,'2019-06-14 14:55:31','黑衣人：全球通缉','英国黑衣人总部王牌探员H（克里斯·海姆斯沃斯 饰）与新晋探员M（泰莎·汤普森 饰）在阻止外星团伙入侵的过程中意外铲除了隐藏在黑衣人组织中的内奸，成功拯救世界。',0),(12,'https://i.loli.net/2019/06/13/5d01fe24cd74c74464.jpg','章笛沙',NULL,'陈飞宇/何蓝逗/惠英红/陶慧敏/汪苏泷','爱情/青春','中国大陆','中文',110,'2019-06-06 14:57:31','最好的我们','每个人的心里大概都藏着一个念念不忘的人。一个偶然被提及的名字，让女摄影师耿耿（何蓝逗 饰）内心掀起万千波澜，触动了回忆的开关，那个撩人心动的少年余淮（陈飞宇 饰）再度闯进她的思绪。那是记忆里最好的时光，“学渣”耿耿和“学霸”余淮成了同桌，还结识了简单（王初伊 饰）、贝塔（周楚濋 饰）、徐延亮（陈帅 饰）。校园里充盈着专属少男少女们的懵懂、青涩、怦然心动和勇敢，耿耿余淮也拥有了他们的约定。高考后，当耿耿满怀期待憧憬约定兑现之时，余淮却忽然消失不见了。七年后两人重逢，余淮当年未说出口的那句话、他不辞而别的秘密，耿耿能否得到解答？这段耿耿于怀的过往，让两人再度面临情感的抉择……',0),(11,'https://i.loli.net/2019/06/13/5d020217d530183455.jpg','闫非/彭大魔',NULL,'沈腾/马丽/尹正','喜剧/爱情','中国大陆','中文',104,'2014-09-30 00:00:00','夏洛特烦恼','在学生时代的初恋秋雅（王智饰）的婚礼上，毕业后吃软饭靠老婆养的夏洛（沈腾饰）假充大款，出尽其丑，中间还被老婆马冬梅（马丽饰）戳穿暴捶。混乱之中，夏洛意外穿越时空，回到了1997年的学生时代的课堂里。他懵懵懂懂，以为是场真实感极强的梦，于是痛揍王老师，强吻秋雅，还尝试跳楼让自己醒来。当受伤的他从病床上苏醒时，他意识到自己真的穿越了时空。既然有机会重新来过，那不如好好折腾一回。他勇敢追求秋雅、奚落优等生袁华（尹正饰）、拒绝马冬梅的死缠烂打。后来夏洛凭借“创作”朴树、窦唯等人的成名曲而进入娱乐圈。他的人生发生翻天覆地的巨变，但是内心某个地方却越来越感到空虚…… ',1),(13,'https://i.loli.net/2019/06/20/5d0b706a9048d32971.jpg','陈思诚',null,'王宝强/刘昊然/陈赫','喜剧/动作/悬疑','中国大陆','中文',135,'2015-12-31 00:00:00','唐人街探案','影片讲述了一对表叔侄被卷入离奇黄金凶杀案，多方追捕下到处逃窜途中发生的搞笑故事。天赋异禀的结巴少年秦风（刘昊然 饰）警校落榜，被姥姥遣送泰国找远房表舅“唐人街第一神探”唐仁（王宝强 饰）散心。不想一夜花天酒地后，唐仁沦为离奇凶案嫌疑人，不得不和秦风亡命天涯…… 穷追不舍的警探黄兰登（陈赫 饰）；无敌幸运的警探坤泰（肖央 饰）；穷凶极恶、阴差阳错的“匪帮三人组”；高深莫测的“唐人街教父”等悉数登场！他们能否在躲避警察追捕、匪帮追杀、黑帮围剿的同时，在短短七天内，完成找到失落的黄金、查明真凶、为自己洗清罪名这些逆天的任务？',0),(14,'https://i.loli.net/2019/06/20/5d0b7068641bb24238.jpg','吴京',null,'吴京/余男/倪大红','动作/战争','中国大陆','中文',90,'2015-04-02 00:00:00','战狼','在南疆围捕贩毒分子的行动中，特种部队狙击手冷锋（吴京 饰）公然违抗上级的命令，开枪射杀伤害了战友的暴徒武吉（周晓鸥 饰）。这一行动令冷锋遭到军方禁闭甚至强制退伍的处罚，不过各特种部队精英组成超级特种部队战狼中队的中队长龙小云（余男 饰），却十分欣赏这个敢作敢为且业务过硬的血性男儿，于是将其召入自己的麾下。在新近的一次演习中，冷锋凭借冷静的判断力从老首长处拔得一城，并且成功击退了突然出现的狼群。谁知在毫无准备的情况下，战狼遭到了一伙荷枪实弹分子的袭击。原来武吉的哥哥敏登（倪大红 饰）是一个冷酷无情的国际通缉犯，他手下豢养了一大批身怀绝技的雇佣兵。为了给弟弟报仇，敏登派出雇佣兵千里迢迢奔着冷锋而来…… ',0),(15,'https://i.loli.net/2019/06/20/5d0b70ed8376b26148.jpg','田晓鹏',null,'张磊/张欣/林子杰','动画/动作/奇幻','中国大陆','中文',89,'2015-07-10 00:00:00','西游记之大圣归来','大闹天宫后四百年多年，齐天大圣成了一个传说，在山妖横行的长安城，孤儿江流儿（林子杰 配音）与行脚僧法明（吴文伦 配音）相依为命，小小少年常常神往大闹天宫的孙悟空（张磊 配音）。 有一天，山妖（吴迪 配音）来劫掠童男童女，江流儿救了一个小女孩，惹得山妖追杀，他一路逃跑，跑进了五行山，盲打误撞地解除了孙悟空的封印。悟空自由之后只想回花果山，却无奈腕上封印未解，又欠江流儿人情，勉强地护送他回长安城。 一路上八戒（刘九容 配音）和白龙马也因缘际化地现身，但或落魄或魔性大发，英雄不再。妖王（童自荣 配音）为抢女童，布下夜店迷局，却发现悟空法力尽失，轻而易举地抓走了女童。悟空不愿再去救女童，江流儿决定自己去救。 日全食之日，在悬空寺，妖王准备将童男童女投入丹炉中，江流儿却冲进了道场，最后一战开始了…… ',0);
/*!40000 ALTER TABLE `movie` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `movie_like`
--

DROP TABLE IF EXISTS `movie_like`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `movie_like` (
  `movie_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `like_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`movie_id`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movie_like`
--

LOCK TABLES `movie_like` WRITE;
/*!40000 ALTER TABLE `movie_like` DISABLE KEYS */;
INSERT INTO `movie_like` VALUES (10,12,'2019-03-25 02:40:19'),(11,1,'2019-03-22 09:38:12'),(11,2,'2019-03-23 09:38:12'),(11,3,'2019-03-22 08:38:12'),(12,1,'2019-03-23 09:48:46'),(12,3,'2019-03-25 06:36:22'),(14,1,'2019-03-23 09:38:12'),(16,12,'2019-03-23 15:27:48');
/*!40000 ALTER TABLE `movie_like` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `schedule`
--

DROP TABLE IF EXISTS `schedule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `schedule` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `hall_id` int(11) NOT NULL,
  `movie_id` int(11) NOT NULL,
  `start_time` timestamp NOT NULL ON UPDATE CURRENT_TIMESTAMP,
  `end_time` timestamp NOT NULL,
  `fare` double NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=69 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `schedule`
--

LOCK TABLES `schedule` WRITE;
/*!40000 ALTER TABLE `schedule` DISABLE KEYS */;
INSERT INTO `schedule` VALUES (20,1,12,'2019-04-13 17:00:00','2019-04-13 18:00:00',20.5),(21,1,10,'2019-04-11 12:00:00','2019-04-11 13:00:00',90),(27,1,11,'2019-04-17 18:01:00','2019-04-17 20:01:00',20.5),(28,1,11,'2019-04-19 16:00:00','2019-04-19 18:00:00',20.5),(30,1,11,'2019-04-18 18:01:00','2019-04-18 20:01:00',20.5),(31,1,11,'2019-04-12 16:00:00','2019-04-12 18:00:00',20.5),(32,1,11,'2019-04-12 20:00:00','2019-04-12 22:00:00',20.5),(37,1,11,'2019-04-15 00:00:00','2019-04-15 02:00:00',20.5),(38,1,11,'2019-04-14 17:00:00','2019-04-14 19:00:00',20.5),(40,1,10,'2019-04-10 16:00:00','2019-04-10 18:00:00',20.5),(41,1,11,'2019-04-10 19:00:00','2019-04-10 21:00:00',20.5),(42,1,11,'2019-04-10 22:00:00','2019-04-11 00:00:00',20.5),(43,1,10,'2019-04-11 01:00:00','2019-04-11 03:00:00',20.5),(44,2,10,'2019-04-11 01:00:00','2019-04-11 03:00:00',20.5),(45,2,10,'2019-04-10 22:00:00','2019-04-11 00:00:00',20.5),(46,2,11,'2019-04-10 19:00:00','2019-04-10 21:00:00',20.5),(47,2,11,'2019-04-10 16:00:00','2019-04-10 18:00:00',20.5),(48,2,10,'2019-04-11 13:00:00','2019-04-11 15:59:00',20.5),(50,1,10,'2019-04-15 16:00:00','2019-04-15 19:00:00',2),(51,1,10,'2019-04-17 05:00:00','2019-04-17 07:00:00',9),(52,1,10,'2019-04-18 05:00:00','2019-04-18 07:00:00',9),(53,1,16,'2019-04-19 07:00:00','2019-04-19 10:00:00',9),(54,1,16,'2019-04-16 19:00:00','2019-04-16 22:00:00',9),(55,1,15,'2019-04-17 23:00:00','2019-04-18 01:00:00',9),(56,2,10,'2019-04-19 13:00:00','2019-04-19 15:59:00',20.5),(57,2,10,'2019-04-20 13:00:00','2019-04-20 15:59:00',20.5),(58,2,10,'2019-04-21 13:00:00','2019-04-21 15:59:00',20.5),(61,1,13,'2019-04-20 11:00:00','2019-04-20 13:00:00',25),(62,1,11,'2019-04-20 08:00:00','2019-04-20 10:00:00',25),(63,2,15,'2019-04-20 16:01:30','2019-04-21 05:30:00',30),(64,1,16,'2019-04-22 02:00:00','2019-04-22 05:30:00',30),(65,1,10,'2019-04-23 02:00:00','2019-04-23 05:30:00',30),(66,2,13,'2019-04-21 07:31:29','2019-04-16 15:59:00',20.5),(67,2,10,'2019-04-25 13:00:00','2019-04-25 15:59:00',20.5),(68,2,10,'2019-04-26 13:00:00','2019-04-26 15:59:00',20.5);
/*!40000 ALTER TABLE `schedule` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ticket`
--

DROP TABLE IF EXISTS `ticket`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ticket` (
  `user_id` int(11) DEFAULT NULL,
  `schedule_id` int(11) DEFAULT NULL,
  `column_index` int(11) DEFAULT NULL,
  `row_index` int(11) DEFAULT NULL,
  `state` tinyint(4) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=63 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ticket`
--

LOCK TABLES `ticket` WRITE;
/*!40000 ALTER TABLE `ticket` DISABLE KEYS */;
INSERT INTO `ticket` VALUES (12,50,5,3,2,1,'2019-04-23 13:50:52'),(12,50,5,3,2,2,'2019-04-23 13:50:52'),(12,50,5,3,2,3,'2019-04-23 13:50:52'),(12,50,5,3,2,4,'2019-04-23 13:50:52'),(12,50,5,3,0,5,'2019-04-23 13:50:52'),(15,50,4,3,0,6,'2019-04-23 13:50:52'),(15,58,0,0,1,15,'2019-04-23 13:50:52'),(15,58,2,0,1,16,'2019-04-23 13:50:52'),(15,58,1,1,1,17,'2019-04-23 13:50:52'),(15,58,11,7,1,18,'2019-04-23 13:50:52'),(13,50,4,2,1,19,'2019-04-23 13:50:52'),(15,66,3,2,1,20,'2019-04-23 13:50:52'),(12,50,1,1,1,21,'2019-04-23 13:50:52'),(13,50,4,3,1,22,'2019-04-23 13:50:52'),(15,50,2,2,1,23,'2019-04-23 13:50:52'),(15,58,0,7,0,24,'2019-04-23 13:50:52'),(15,58,5,4,0,25,'2019-04-23 13:50:52'),(15,58,6,4,0,26,'2019-04-23 13:50:52'),(15,58,6,2,0,27,'2019-04-23 13:50:52'),(15,58,7,2,0,28,'2019-04-23 13:50:52'),(15,58,0,4,0,29,'2019-04-23 13:50:52'),(15,58,0,3,0,30,'2019-04-23 13:50:52'),(15,58,0,2,0,31,'2019-04-23 13:50:52'),(15,58,10,0,0,32,'2019-04-23 13:50:52'),(15,58,11,0,0,33,'2019-04-23 13:50:52'),(15,58,8,0,0,34,'2019-04-23 13:50:52'),(15,58,9,0,0,35,'2019-04-23 13:50:52'),(15,58,5,0,0,36,'2019-04-23 13:50:52'),(15,58,6,0,0,37,'2019-04-23 13:50:52'),(15,58,6,7,0,38,'2019-04-23 13:50:52'),(15,58,7,7,0,39,'2019-04-23 13:50:52'),(15,58,8,7,0,40,'2019-04-23 13:50:52'),(15,58,11,4,0,41,'2019-04-23 13:50:52'),(15,58,11,5,0,42,'2019-04-23 13:50:52'),(15,58,9,6,0,43,'2019-04-23 13:50:52'),(15,58,10,6,0,44,'2019-04-23 13:50:52'),(15,58,11,6,0,45,'2019-04-23 13:50:52'),(15,58,3,5,1,46,'2019-04-23 13:50:52'),(15,58,4,5,1,47,'2019-04-23 13:50:52'),(15,58,5,5,1,48,'2019-04-23 13:50:52'),(15,58,11,2,0,49,'2019-04-23 13:50:52'),(15,58,11,3,0,50,'2019-04-23 13:50:52'),(15,58,9,4,0,51,'2019-04-23 13:50:52'),(15,58,9,3,1,52,'2019-04-23 13:50:52'),(15,58,10,3,1,53,'2019-04-23 13:50:52'),(15,65,7,4,0,54,'2019-04-23 13:50:52'),(15,65,8,4,0,55,'2019-04-23 13:50:52'),(15,65,9,4,0,56,'2019-04-23 13:50:52'),(15,65,7,3,0,57,'2019-04-23 13:50:52'),(15,65,8,3,0,58,'2019-04-23 13:50:52'),(15,65,9,3,0,59,'2019-04-23 13:50:52'),(15,65,0,0,1,60,'2019-04-23 13:50:52'),(15,65,0,1,1,61,'2019-04-23 13:50:52'),(15,65,0,2,1,62,'2019-04-23 13:50:52');
/*!40000 ALTER TABLE `ticket` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `identity` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id_uindex` (`id`),
  UNIQUE KEY `user_username_uindex` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,0,'testname','123456'),(3,0,'test','bcbfacabae00b238a2e1b4db63dd7d70'),(5,0,'test1','123456'),(7,0,'test121','123456'),(8,2,'root','bcbfacabae00b238a2e1b4db63dd7d70'),(10,3,'roottt','bcbfacabae00b238a2e1b4db63dd7d70'),(12,0,'zhourui','123456'),(13,0,'abc123','abc123'),(15,0,'dd','123');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_info`
--

DROP TABLE IF EXISTS `user_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_info` (
                        `id` int(11) NOT NULL,
                        `name` varchar(255) default NULL,
                        `profile_picture` varchar(255) default NULL,
                        UNIQUE KEY `user_id_uindex` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user_info` WRITE;
/*!40000 ALTER TABLE `user_info` DISABLE KEYS */;
INSERT INTO `user_info` VALUES (1,null,null),(3,null,null),(5,null,null),(7,null,null),(8,null,null),(10,null,null),(12,null,null),(13,null,null),(15,null,null);
/*!40000 ALTER TABLE `user_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `recharge_present`
--
DROP TABLE IF EXISTS `recharge_present`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `recharge_present`(
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `target_amount` float DEFAULT NULL,
  `present_amount` float DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `recharge_present`
--
LOCK TABLES `recharge_present` WRITE;
/*!40000 ALTER TABLE `recharge_present` DISABLE KEYS */;
INSERT INTO `recharge_present` VALUES (1,200,30),(2,300,40);
/*!40000 ALTER TABLE `recharge_present` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `view`
--

DROP TABLE IF EXISTS `view`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `view` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `day` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `view`
--

LOCK TABLES `view` WRITE;
/*!40000 ALTER TABLE `view` DISABLE KEYS */;
INSERT INTO `view` VALUES (1,7);
/*!40000 ALTER TABLE `view` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vip_card`
--

DROP TABLE IF EXISTS `vip_card`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vip_card` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `balance` float DEFAULT NULL,
  `join_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `vip_card_user_id_uindex` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vip_card`
--

LOCK TABLES `vip_card` WRITE;
/*!40000 ALTER TABLE `vip_card` DISABLE KEYS */;
INSERT INTO `vip_card` VALUES (1,15,375,'2019-04-21 13:54:38'),(2,12,660,'2019-04-17 18:47:42');
/*!40000 ALTER TABLE `vip_card` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vip_charge`
--

DROP TABLE IF EXISTS `vip_charge`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vip_charge` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `vip_id` int(11) DEFAULT NULL,
  `charge_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `charge_sum` float DEFAULT NULL,
  `bonus_sum` float DEFAULT NULL,
  `balance` float DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vip_charge`
--

LOCK TABLES `vip_charge` WRITE;
/*!40000 ALTER TABLE `vip_charge` DISABLE KEYS */;
INSERT INTO `vip_charge` VALUES (1,15,1,'2019-04-21 13:54:38',500,50,550),(2,12,2,'2019-04-17 18:47:42',200,10,210);
/*!40000 ALTER TABLE `vip_charge` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_form`
--

DROP TABLE IF EXISTS `order_form`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order_form` (
  `user_id` int(11) DEFAULT NULL,
  `tickets_id` varchar(50) DEFAULT NULL,
  `movie_id` int(11) DEFAULT NULL,
  `cost` float DEFAULT NULL,
  `payment_mode` tinyint(2) DEFAULT NULL,
  `state` tinyint(4) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `purchase_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Dumping data for table `order_form`
--

LOCK TABLES `order_form` WRITE;
/*!40000 ALTER TABLE `order_form` DISABLE KEYS */;
INSERT INTO `order_form` VALUES (12,'1&2&3&4',10,8,0,0,1,'2019-04-23 13:50:52');
/*!40000 ALTER TABLE `order_form` ENABLE KEYS */;
UNLOCK TABLES;


--
-- Dumping events for database 'cinema'
--

--
-- Dumping routines for database 'cinema'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-04-24 21:20:52
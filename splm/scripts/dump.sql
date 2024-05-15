-- MySQL dump 10.13  Distrib 8.0.23, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: splm
-- ------------------------------------------------------
-- Server version	8.0.30

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `pt_board`
--

DROP TABLE IF EXISTS `pt_board`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pt_board` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_time` datetime(6) NOT NULL,
  `deleted` bit(1) NOT NULL,
  `update_time` datetime(6) DEFAULT NULL,
  `version` int DEFAULT NULL,
  `enabled` bit(1) NOT NULL,
  `ip` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `port` int DEFAULT NULL,
  `status` tinyint NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `pt_board_chk_1` CHECK ((`status` between 0 and 2))
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pt_board`
--

LOCK TABLES `pt_board` WRITE;
/*!40000 ALTER TABLE `pt_board` DISABLE KEYS */;
INSERT INTO `pt_board` VALUES (1,'2024-04-29 07:11:02.000000',_binary '\0','2024-04-29 07:11:02.000000',0,_binary '','127.0.0.1','铁板1',502,1);
/*!40000 ALTER TABLE `pt_board` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pt_plate`
--

DROP TABLE IF EXISTS `pt_plate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pt_plate` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_time` datetime(6) NOT NULL,
  `deleted` bit(1) NOT NULL,
  `update_time` datetime(6) DEFAULT NULL,
  `version` int DEFAULT NULL,
  `board_id` bigint NOT NULL,
  `cx` int NOT NULL,
  `cy` int NOT NULL,
  `enabled` bit(1) NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `name1` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `name2` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pt_plate`
--

LOCK TABLES `pt_plate` WRITE;
/*!40000 ALTER TABLE `pt_plate` DISABLE KEYS */;
INSERT INTO `pt_plate` VALUES (1,'2024-04-29 07:32:46.000000',_binary '\0','2024-05-11 12:55:31.146748',0,1,1,1,_binary '','压板1-1','备用1','备用1'),(2,'2024-04-29 07:32:46.000000',_binary '\0','2024-04-29 07:32:46.000000',0,1,2,1,_binary '','压板1-2','',''),(3,'2024-04-29 07:32:46.000000',_binary '\0','2024-05-01 18:54:36.153687',0,1,3,1,_binary '','压板1-3','',''),(4,'2024-04-29 07:32:46.000000',_binary '\0','2024-04-29 07:32:46.000000',0,1,4,1,_binary '','压板1-4','',''),(5,'2024-04-29 07:32:46.000000',_binary '\0','2024-04-29 07:32:46.000000',0,1,5,1,_binary '','压板1-5','',''),(6,'2024-04-29 07:32:46.000000',_binary '\0','2024-04-29 07:32:46.000000',0,1,6,1,_binary '','压板1-6','',''),(7,'2024-04-29 07:32:46.000000',_binary '\0','2024-04-29 07:32:46.000000',0,1,7,1,_binary '','压板1-7','',''),(8,'2024-04-29 07:32:46.000000',_binary '\0','2024-04-29 07:32:46.000000',0,1,8,1,_binary '','压板1-8','',''),(9,'2024-04-29 07:32:46.000000',_binary '\0','2024-04-29 07:32:46.000000',0,1,9,1,_binary '','压板1-9','',''),(10,'2024-04-29 07:32:46.000000',_binary '\0','2024-04-29 07:32:46.000000',0,1,1,2,_binary '','压板2-1','',''),(11,'2024-04-29 07:32:46.000000',_binary '\0','2024-04-29 07:32:46.000000',0,1,2,2,_binary '','压板2-2','',''),(12,'2024-04-29 07:32:46.000000',_binary '\0','2024-04-29 07:32:46.000000',0,1,3,2,_binary '','压板2-3','',''),(13,'2024-04-29 07:32:46.000000',_binary '\0','2024-04-29 07:32:46.000000',0,1,4,2,_binary '','压板2-4','',''),(14,'2024-04-29 07:32:46.000000',_binary '\0','2024-04-29 07:32:46.000000',0,1,5,2,_binary '','压板2-5','',''),(15,'2024-04-29 07:32:46.000000',_binary '\0','2024-04-29 07:32:46.000000',0,1,6,2,_binary '','压板2-6','',''),(16,'2024-04-29 07:32:46.000000',_binary '\0','2024-04-29 07:32:46.000000',0,1,7,2,_binary '','压板2-7','',''),(17,'2024-04-29 07:32:46.000000',_binary '\0','2024-04-29 07:32:46.000000',0,1,8,2,_binary '','压板2-8','',''),(18,'2024-04-29 07:32:46.000000',_binary '\0','2024-04-29 07:32:46.000000',0,1,9,2,_binary '','压板2-9','',''),(19,'2024-04-29 07:32:46.000000',_binary '\0','2024-04-29 07:32:46.000000',0,1,1,3,_binary '','压板3-1','',''),(20,'2024-04-29 07:32:46.000000',_binary '\0','2024-04-29 07:32:46.000000',0,1,2,3,_binary '','压板3-2','',''),(21,'2024-04-29 07:32:46.000000',_binary '\0','2024-04-29 07:32:46.000000',0,1,3,3,_binary '','压板3-3','',''),(22,'2024-04-29 07:32:46.000000',_binary '\0','2024-04-29 07:32:46.000000',0,1,4,3,_binary '','压板3-4','',''),(23,'2024-04-29 07:32:46.000000',_binary '\0','2024-04-29 07:32:46.000000',0,1,5,3,_binary '','压板3-5','',''),(24,'2024-04-29 07:32:46.000000',_binary '\0','2024-04-29 07:32:46.000000',0,1,6,3,_binary '','压板3-6','',''),(25,'2024-04-29 07:32:46.000000',_binary '\0','2024-04-29 07:32:46.000000',0,1,7,3,_binary '','压板3-7','',''),(26,'2024-04-29 07:32:46.000000',_binary '\0','2024-04-29 07:32:46.000000',0,1,8,3,_binary '','压板3-8','',''),(27,'2024-04-29 07:32:46.000000',_binary '\0','2024-04-29 07:32:46.000000',0,1,9,3,_binary '','压板3-9','',''),(28,'2024-04-29 07:32:46.000000',_binary '\0','2024-04-29 07:32:46.000000',0,1,1,4,_binary '','压板4-1','',''),(29,'2024-04-29 07:32:46.000000',_binary '\0','2024-04-29 07:32:46.000000',0,1,2,4,_binary '','压板4-2','',''),(30,'2024-04-29 07:32:46.000000',_binary '\0','2024-04-29 07:32:46.000000',0,1,3,4,_binary '','压板4-3','',''),(31,'2024-04-29 07:32:46.000000',_binary '\0','2024-04-29 07:32:46.000000',0,1,4,4,_binary '','压板4-4','',''),(32,'2024-04-29 07:32:46.000000',_binary '\0','2024-04-29 07:32:46.000000',0,1,5,4,_binary '','压板4-5','',''),(33,'2024-04-29 07:32:46.000000',_binary '\0','2024-04-29 07:32:46.000000',0,1,6,4,_binary '','压板4-6','',''),(34,'2024-04-29 07:32:46.000000',_binary '\0','2024-04-29 07:32:46.000000',0,1,7,4,_binary '','压板4-7','',''),(35,'2024-04-29 07:32:46.000000',_binary '\0','2024-04-29 07:32:46.000000',0,1,8,4,_binary '','压板4-8','',''),(36,'2024-04-29 07:32:46.000000',_binary '\0','2024-04-29 07:32:46.000000',0,1,9,4,_binary '','压板4-9','',''),(37,'2024-04-29 07:32:46.000000',_binary '\0','2024-04-29 07:32:46.000000',0,1,1,5,_binary '','压板5-1','',''),(38,'2024-04-29 07:32:46.000000',_binary '\0','2024-04-29 07:32:46.000000',0,1,2,5,_binary '','压板5-2','',''),(39,'2024-04-29 07:32:46.000000',_binary '\0','2024-04-29 07:32:46.000000',0,1,3,5,_binary '','压板5-3','',''),(40,'2024-04-29 07:32:46.000000',_binary '\0','2024-04-29 07:32:46.000000',0,1,4,5,_binary '','压板5-4','',''),(41,'2024-04-29 07:32:46.000000',_binary '\0','2024-04-29 07:32:46.000000',0,1,5,5,_binary '','压板5-5','',''),(42,'2024-04-29 07:32:46.000000',_binary '\0','2024-04-29 07:32:46.000000',0,1,6,5,_binary '','压板5-6','',''),(43,'2024-04-29 07:32:46.000000',_binary '\0','2024-04-29 07:32:46.000000',0,1,7,5,_binary '','压板5-7','',''),(44,'2024-04-29 07:32:46.000000',_binary '\0','2024-04-29 07:32:46.000000',0,1,8,5,_binary '','压板5-8','',''),(45,'2024-04-29 07:32:46.000000',_binary '\0','2024-04-29 07:32:46.000000',0,1,9,5,_binary '','压板5-9','',''),(46,'2024-04-29 07:32:46.000000',_binary '\0','2024-04-29 07:32:46.000000',0,1,1,6,_binary '','压板6-1','',''),(47,'2024-04-29 07:32:46.000000',_binary '\0','2024-04-29 07:32:46.000000',0,1,2,6,_binary '','压板6-2','',''),(48,'2024-04-29 07:32:46.000000',_binary '\0','2024-04-29 07:32:46.000000',0,1,3,6,_binary '','压板6-3','',''),(49,'2024-04-29 07:32:46.000000',_binary '\0','2024-04-29 07:32:46.000000',0,1,4,6,_binary '','压板6-4','',''),(50,'2024-04-29 07:32:46.000000',_binary '\0','2024-04-29 07:32:46.000000',0,1,5,6,_binary '','压板6-5','',''),(51,'2024-04-29 07:32:46.000000',_binary '\0','2024-04-29 07:32:46.000000',0,1,6,6,_binary '','压板6-6','',''),(52,'2024-04-29 07:32:46.000000',_binary '\0','2024-04-29 07:32:46.000000',0,1,7,6,_binary '','压板6-7','',''),(53,'2024-04-29 07:32:46.000000',_binary '\0','2024-04-29 07:32:46.000000',0,1,8,6,_binary '','压板6-8','',''),(54,'2024-04-29 07:32:46.000000',_binary '\0','2024-04-29 07:32:46.000000',0,1,9,6,_binary '','压板6-9','',''),(55,'2024-04-29 07:32:46.000000',_binary '\0','2024-04-29 07:32:46.000000',0,1,1,7,_binary '','压板7-1','',''),(56,'2024-04-29 07:32:46.000000',_binary '\0','2024-04-29 07:32:46.000000',0,1,2,7,_binary '','压板7-2','',''),(57,'2024-04-29 07:32:46.000000',_binary '\0','2024-04-29 07:32:46.000000',0,1,3,7,_binary '','压板7-3','',''),(58,'2024-04-29 07:32:46.000000',_binary '\0','2024-04-29 07:32:46.000000',0,1,4,7,_binary '','压板7-4','',''),(59,'2024-04-29 07:32:46.000000',_binary '\0','2024-04-29 07:32:46.000000',0,1,5,7,_binary '','压板7-5','',''),(60,'2024-04-29 07:32:46.000000',_binary '\0','2024-04-29 07:32:46.000000',0,1,6,7,_binary '','压板7-6','',''),(61,'2024-04-29 07:32:46.000000',_binary '\0','2024-04-29 07:32:46.000000',0,1,7,7,_binary '','压板7-7','',''),(62,'2024-04-29 07:32:46.000000',_binary '\0','2024-04-29 07:32:46.000000',0,1,8,7,_binary '','压板7-8','',''),(63,'2024-04-29 07:32:46.000000',_binary '\0','2024-04-29 07:32:46.000000',0,1,9,7,_binary '','压板7-9','','');
/*!40000 ALTER TABLE `pt_plate` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pt_plate_status`
--

DROP TABLE IF EXISTS `pt_plate_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pt_plate_status` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_time` datetime(6) NOT NULL,
  `deleted` bit(1) NOT NULL,
  `update_time` datetime(6) DEFAULT NULL,
  `version` int DEFAULT NULL,
  `actual_value` tinyint NOT NULL,
  `plate_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `pt_plate_status_chk_1` CHECK ((`actual_value` between 0 and 1))
) ENGINE=InnoDB AUTO_INCREMENT=207 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-05-11 13:08:41

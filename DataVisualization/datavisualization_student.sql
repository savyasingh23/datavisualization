-- MySQL dump 10.13  Distrib 8.0.21, for Win64 (x86_64)
--
-- Host: localhost    Database: datavisualization
-- ------------------------------------------------------
-- Server version	8.0.21

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
-- Table structure for table `student`
--

DROP TABLE IF EXISTS `student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `student` (
  `stu_id` int NOT NULL AUTO_INCREMENT,
  `Name` varchar(45) DEFAULT NULL,
  `percentage` double DEFAULT NULL,
  `semester` int DEFAULT NULL,
  `branch` varchar(50) DEFAULT NULL,
  `course` varchar(45) DEFAULT NULL,
  `year` int DEFAULT NULL,
  `Gender` varchar(30) DEFAULT NULL,
  `admission_year` int DEFAULT NULL,
  PRIMARY KEY (`stu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student`
--

LOCK TABLES `student` WRITE;
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
INSERT INTO `student` VALUES (1,'sanya',50,3,'CS','BTECH',2,'Female',2019),(2,'savya',94,5,'Data Analytics','MCA',3,'Female',2018),(3,'riya',78,1,'Bussiness','MBA',1,'Female',2019),(4,'anita',45,3,'EC','BTECH',2,'Female',2018),(5,'sunita',66,7,'Finance','MCom',2,'Female',2017),(6,'mohan',90,1,'Economics','BA',1,'Male',2020),(7,'soham',33,3,'IT','BTECH',2,'Male',2019),(8,'elisha',78,5,'Psychology','BA',3,'Female',2019),(9,'vipul',88,7,'ME','BTECH',4,'Male',2017),(10,'Tanmay',95,3,'Accounts','MCom',2,'Male',2019),(11,'anam',45,1,'IT','BTECH',1,'Male',2020),(12,'dev',92,3,'Finance','MCom',2,'Male',2019),(13,'shreya',88,7,'Civil ','BTECH',4,'Female',2017),(14,'fenil',66,3,'Accounts','MCom',2,'Male',2019),(15,'dhruvi',85,3,'CS','BTECH',2,'Female',2019),(16,'riya',45,3,'CS','BTECH',2,'Female',2019),(17,'aman',45,3,'ME','BTECH',2,'Male',2019),(18,'Satyam',78,5,'Economics','BCom',3,'Male',2018),(21,'ishika',94,7,'EX','BE',4,'Female',2019),(22,'Vruti',33,1,'Finance','MCom',1,'Female',2020),(23,'Rajesh',44,3,'ME','BTECH',2,'Male',2019),(24,'Shalin',56,1,'IT','BTECH',1,'Male',2020),(25,'Dhruv',45,1,'IT','BTECH',1,'Male',2020),(27,'Ishaan',34,3,'Data Analytics','MCA',2,'Male',2019),(28,'Shreya',77,7,'CE','BTECH',4,'Female',2018),(29,'Shalin',23,1,'CS','BTECH',1,'Male',2020),(30,'Shalaija',78,1,'Finance','MCom',1,'Female',2020),(31,'Garima',98,1,'CS','BTECH',1,'Female',2020),(32,'Vipul',66,1,'CS','BTECH',1,'Male',2020),(33,'Aman',89,1,'Bussiness','MBA',1,'Male',2020),(34,'Farhan',73,3,'IT','BTECH',2,'Male',2019),(35,'Gaurav',67,1,'CE','BTECH',1,'Male',2020),(36,'Savita',34,5,'CS','BTECH',3,'Female',2018),(37,'Harman',77,7,'ME','BTECH',4,'Male',2017),(38,'Tanmay',65,1,'CS','BTECH',1,'Male',2020),(39,'Sakshi',45,3,'EC','BTECH',2,'Female',2019),(40,'Elisha',78,1,'CS','BTECH',1,'Female',2020),(41,'Wiliam',60,1,'CS','BTECH',1,'Male',2020),(42,'Rajesh',34,1,'CS','BTECH',1,'Male',2020);
/*!40000 ALTER TABLE `student` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-12-10 15:43:05

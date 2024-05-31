-- MySQL dump 10.13  Distrib 8.0.36, for macos14 (x86_64)
--
-- Host: sql.freedb.tech    Database: freedb_carrentalproject
-- ------------------------------------------------------
-- Server version	8.0.36-0ubuntu0.22.04.1

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
-- Table structure for table `CLIENT`
--

DROP TABLE IF EXISTS `CLIENT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `CLIENT` (
  `C_Id` int NOT NULL AUTO_INCREMENT,
  `Fname` varchar(20) NOT NULL,
  `Username` varchar(30) NOT NULL,
  `Pword` varchar(30) NOT NULL,
  `Phone_no` varchar(10) NOT NULL,
  `License_no` varchar(8) NOT NULL,
  PRIMARY KEY (`C_Id`),
  UNIQUE KEY `Username` (`Username`),
  UNIQUE KEY `License_no` (`License_no`)
) ENGINE=InnoDB AUTO_INCREMENT=570 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CLIENT`
--

LOCK TABLES `CLIENT` WRITE;
/*!40000 ALTER TABLE `CLIENT` DISABLE KEYS */;
INSERT INTO `CLIENT` VALUES (517,'John','johnsmith','password1','0211234567','ABCD1234'),(518,'Jane','janesmith','password2','0212345678','EFGH5678'),(519,'Michael','michaeljones','password3','0213456789','IJKL9012'),(520,'Emily','emilywilson','password4','0221234567','MNOP3456'),(521,'David','davidbrown','password5','0222345678','QRST7890'),(522,'Sarah','sarahmiller','password6','0223456789','UVWX1234'),(523,'Daniel','danieldavis','password7','0201234567','YZAB5678'),(524,'Jessica','jessicathomas','password8','0202345678','CDEF9012'),(525,'Matthew','matthewmartinez','password9','0203456789','GHIJ3456'),(526,'Jennifa','jenniferwhite','password10','0210123456','KLMN7890'),(527,'Christopher','christopherlee','password11','0211234567','OPQR1234'),(528,'Amanda','amandajackson','password12','0212345678','STUV5678'),(529,'James','jamesharris','password13','0220123456','WXYZ9012'),(530,'Elizabeth','elizabethclark','password14','0221234567','ABCD3456'),(531,'Joshua','joshuathompson','password15','0222345678','EFGH7890'),(532,'Megan','meganroberts','password16','0200123456','IJKL1234'),(533,'Andrew','andrewwalker','password17','0201234567','MNOP5678'),(534,'Ashley','ashleyevans','password18','0202345678','QRST9012'),(535,'Justin','justinphillips','password19','0210345678','UVWX3456'),(536,'Nicole','nicoletaylor','password20','0211456789','YZAB7890'),(537,'Ryan','ryanrodriguez','password21','0220345678','CDEF1234'),(538,'Samantha','samanthamorris','password22','0221456789','GHIJ5678'),(539,'Robert','robertcook','password23','0200345678','KLMN9012'),(540,'Lauren','laurenstewart','password24','0201456789','OPQR3456'),(541,'Brandon','brandonrivera','password25','0210567890','STUV7890'),(542,'Stephanie','stephanieperez','password26','0211678901','WXYZ1234'),(543,'William','williamsanchez','password27','0220567890','ABCD5678'),(544,'Taylor','taylormartin','password28','0221678901','EFGH9012'),(545,'Jonathan','jonathannguyen','password29','0200567890','IJKL3456'),(546,'Rachel','rachelgarcia','password30','0201678901','MNOP7890'),(547,'Kevin','kevincooper','password31','0210789012','QRST1234'),(548,'Olivia','oliviamorris','password32','0211890123','UVWX5678'),(549,'Eric','ericmorgan','password33','0220789012','YZAB9012'),(550,'Kayla','kaylalong','password34','0221890123','CDEF3456'),(551,'Jason','jasonparker','password35','0200789012','GHIJ7890'),(552,'Hannah','hannahwright','password36','0201890123','KLMN1234'),(553,'Kyle','kyleking','password37','0210901234','OPQR5678'),(554,'Kimberly','kimberlyward','password38','0211012345','STUV9012'),(555,'Joseph','josephturner','password39','0220901234','WXYZ3456'),(556,'Melissa','melissamorales','password40','0221012345','ABCD6789'),(557,'Zachary','zacharylopez','password41','0200901234','EFGH1234'),(558,'Victoria','victoriacarter','password42','0201012345','IJKL5678'),(559,'Steven','stevengonzalez','password43','0210123456','MNOP9012'),(560,'Katherine','katherinehughes','password44','0211234567','QRST3456'),(561,'Brian','brianreed','password45','0220123456','UVWX7890'),(562,'Lindsay','lindsaytorres','password46','0221234567','YZAB1234'),(563,'Timothy','timothybarnes','password47','0200123456','CDEF5678'),(564,'Christina','christinawatson','password48','0201234567','GHIJ9012'),(565,'Nathan','nathanmorris','password49','0210345678','KLMN3456'),(566,'Danielle','danielleperry','password50','0211456789','OPQR7890'),(567,'Andrew','andrew1','password1','0211111111','NNHN1212'),(568,'a','a','a','0211111111','BNBN1212'),(569,'Andy','andy12','password999','0211111222','NBGH1122');
/*!40000 ALTER TABLE `CLIENT` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `RESERVATION`
--

DROP TABLE IF EXISTS `RESERVATION`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `RESERVATION` (
  `Rental_Id` int NOT NULL AUTO_INCREMENT,
  `Client_Id` int NOT NULL,
  `Vehicle_Id` int NOT NULL,
  `Total_rate` decimal(10,2) NOT NULL,
  `Vehicle_reg` varchar(6) NOT NULL,
  `License_no` varchar(8) NOT NULL,
  `Start_date` date NOT NULL,
  `Return_date` date NOT NULL,
  `Insurance_type` varchar(20) NOT NULL,
  PRIMARY KEY (`Rental_Id`),
  KEY `Vehicle_Id` (`Vehicle_Id`),
  KEY `Client_Id` (`Client_Id`),
  CONSTRAINT `RESERVATION_ibfk_1` FOREIGN KEY (`Vehicle_Id`) REFERENCES `VEHICLE` (`V_Id`),
  CONSTRAINT `RESERVATION_ibfk_2` FOREIGN KEY (`Client_Id`) REFERENCES `CLIENT` (`C_Id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `RESERVATION`
--

LOCK TABLES `RESERVATION` WRITE;
/*!40000 ALTER TABLE `RESERVATION` DISABLE KEYS */;
INSERT INTO `RESERVATION` VALUES (1,567,10,490.50,'RTW204','NNHN1212','2024-09-19','2024-09-22','Basic Cover'),(2,517,13,326.84,'FDN492','ABCD1234','2024-10-10','2024-10-12','Limited Cover'),(3,523,15,224.45,'JYT941','YZAB5678','2024-08-16','2024-08-17','Premium Cover'),(4,534,18,982.26,'JPK850','QRST9012','2024-09-11','2024-09-20','Basic Cover'),(5,536,24,1047.42,'LJW063','YZAB7890','2024-11-07','2024-11-29','Limited Cover'),(6,562,7,120.47,'VNB320','YZAB1234','2024-07-31','2024-08-01','Premium Cover'),(9,521,56,1942.05,'YRH239','QRST7890','2024-08-09','2024-08-24','Basic Cover'),(10,532,30,544.53,'JSY340','IJKL1234','2024-06-14','2024-06-21','Basic Cover'),(11,537,26,698.94,'NXS179','CDEF1234','2024-05-31','2024-06-06','Basic Cover'),(15,546,3,553.35,'TJC092','MNOP7890','2024-05-31','2024-06-05','Limited Cover'),(16,548,6,774.40,'FYN083','UVWX5678','2024-05-31','2024-06-05','Limited Cover'),(18,569,8,5721.99,'UHT974','NBGH1122','2024-05-31','2024-06-29','Premium Cover');
/*!40000 ALTER TABLE `RESERVATION` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `VEHICLE`
--

DROP TABLE IF EXISTS `VEHICLE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `VEHICLE` (
  `V_Id` int NOT NULL AUTO_INCREMENT,
  `Brand` varchar(20) NOT NULL,
  `Model` varchar(20) NOT NULL,
  `Make_year` year NOT NULL,
  `Reg_no` varchar(6) NOT NULL,
  `Colour` varchar(20) NOT NULL,
  `Fuel_option` enum('Premium','Regular','Diesel') NOT NULL,
  `Daily_rate` decimal(10,2) NOT NULL,
  `Availability` tinyint(1) NOT NULL DEFAULT '1',
  `image_path` varchar(255) NOT NULL,
  PRIMARY KEY (`V_Id`),
  UNIQUE KEY `Reg_no` (`Reg_no`)
) ENGINE=InnoDB AUTO_INCREMENT=66 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `VEHICLE`
--

LOCK TABLES `VEHICLE` WRITE;
/*!40000 ALTER TABLE `VEHICLE` DISABLE KEYS */;
INSERT INTO `VEHICLE` VALUES (1,'Mazda','MX5',2001,'ZCK689','Black','Regular',102.81,1,'Black_Mazda_MX5.png'),(2,'Toyota','Corolla',2009,'MMV163','White','Premium',36.92,1,'White_Toyota_Corolla.png'),(3,'BMW','M4',2022,'TJC092','Grey','Premium',110.67,0,'Grey_BMW_M4.png'),(4,'Volkswagen','Golf GTI',2006,'SYG942','Blue','Premium',178.74,1,'Blue_Volkswagen_Golf_GTI.png'),(5,'Toyota','Corolla',2014,'RHQ752','White','Regular',113.26,1,'White_Toyota_Corolla.png'),(6,'Ford','Mustang',2020,'FYN083','Yellow','Premium',154.88,0,'Yellow_Ford_Mustang.png'),(7,'Audi','A3',2013,'VNB320','Aqua','Diesel',85.47,0,'Aqua_Audi_A3_Convertible.png'),(8,'Nissan','350z',2018,'UHT974','Black','Diesel',162.31,0,'Black_Nissan_350z.png'),(9,'Mazda','Axela',2015,'GTR218','Red','Regular',49.34,1,'Red_Mazda_Axela.png'),(10,'Toyota','Rav4',2017,'RTW204','White','Regular',139.50,0,'White_Toyota_Rav4.png'),(11,'Mazda','MX5',2003,'YXP783','Black','Regular',105.64,1,'Black_Mazda_MX5.png'),(12,'Volkswagen','Golf GTI',2021,'KVM692','Blue','Premium',75.83,1,'Blue_Volkswagen_Golf_GTI.png'),(13,'BMW','M4',2011,'FDN492','Grey','Premium',163.42,0,'Grey_BMW_M4.png'),(14,'Audi','A3',2020,'QPL310','Aqua','Regular',91.74,1,'Aqua_Audi_A3_Convertible.png'),(15,'Ford','Mustang',2006,'JYT941','Yellow','Diesel',189.45,0,'Yellow_Ford_Mustang.png'),(16,'Mazda','Axela',2019,'MFC123','Red','Premium',132.58,1,'Red_Mazda_Axela.png'),(17,'Toyota','Corolla',2016,'ZWJ675','White','Diesel',45.21,1,'White_Toyota_Corolla.png'),(18,'Nissan','350z',2015,'JPK850','Black','Regular',85.14,0,'Black_Nissan_350z.png'),(19,'Volkswagen','Golf GTI',2014,'WVL732','Blue','Diesel',168.29,1,'Blue_Volkswagen_Golf_GTI.png'),(20,'BMW','M4',2018,'RPT486','Grey','Premium',139.87,1,'Grey_BMW_M4.png'),(21,'Mazda','MX5',2022,'TXS381','Blue','Diesel',101.95,1,'Blue_Mazda_MX5.png'),(22,'Audi','A3',2009,'GHM259','Aqua','Regular',156.72,1,'Aqua_Audi_A3_Convertible.png'),(23,'Ford','Mustang',2012,'MBR417','Yellow','Regular',123.84,1,'Yellow_Ford_Mustang.png'),(24,'Mazda','Axela',2014,'LJW063','Red','Regular',47.61,0,'Red_Mazda_Axela.png'),(25,'Toyota','Rav4',2013,'CTM248','White','Diesel',168.74,1,'White_Toyota_Rav4.png'),(26,'Volkswagen','Golf GTI',2010,'NXS179','Blue','Premium',92.49,0,'Blue_Volkswagen_Golf_GTI.png'),(27,'BMW','M4',2007,'KLW348','Grey','Regular',137.58,1,'Grey_BMW_M4.png'),(28,'Audi','A3',2011,'PFT274','Aqua','Diesel',147.96,1,'Aqua_Audi_A3_Convertible.png'),(29,'Mazda','MX5',2013,'TXD981','Black','Premium',129.53,1,'Black_Mazda_MX5.png'),(30,'Ford','Mustang',2021,'JSY340','Yellow','Diesel',53.79,0,'Yellow_Ford_Mustang.png'),(31,'Mazda','Axela',2020,'QHC612','Red','Regular',86.19,1,'Red_Mazda_Axela.png'),(32,'Toyota','Corolla',2004,'LVG203','White','Regular',33.75,1,'White_Toyota_Corolla.png'),(33,'Nissan','350z',2002,'PXZ849','Black','Diesel',60.12,1,'Black_Nissan_350z.png'),(34,'Volkswagen','Golf GTI',2016,'YCM529','Blue','Diesel',182.34,1,'Blue_Volkswagen_Golf_GTI.png'),(35,'BMW','M4',2005,'NSF327','Grey','Premium',113.49,1,'Grey_BMW_M4.png'),(36,'Mazda','MX5',2007,'HVJ291','Blue','Regular',74.26,1,'Blue_Mazda_MX5.png'),(37,'Audi','A3',2008,'RCX175','Aqua','Premium',110.28,1,'Aqua_Audi_A3_Convertible.png'),(38,'Ford','Mustang',2003,'BVG841','Yellow','Regular',91.64,1,'Yellow_Ford_Mustang.png'),(39,'Mazda','Axela',2011,'WTN930','Red','Diesel',76.94,1,'Red_Mazda_Axela.png'),(40,'Toyota','Rav4',2023,'NYD781','White','Regular',105.39,1,'White_Toyota_Rav4.png'),(41,'Volkswagen','Golf GTI',2012,'FVJ250','Blue','Premium',91.12,1,'Blue_Volkswagen_Golf_GTI.png'),(42,'BMW','M4',2006,'WYX695','Grey','Regular',121.25,1,'Grey_BMW_M4.png'),(43,'Audi','A3',2023,'GMF184','Aqua','Diesel',138.69,1,'Aqua_Audi_A3_Convertible.png'),(44,'Mazda','MX5',2019,'NXF302','Black','Diesel',74.81,1,'Black_Mazda_MX5.png'),(45,'Ford','Mustang',2015,'RHJ697','Yellow','Premium',91.05,1,'Yellow_Ford_Mustang.png'),(46,'Mazda','Axela',2010,'KRV512','Red','Regular',144.77,1,'Red_Mazda_Axela.png'),(47,'Toyota','Corolla',2020,'LKG871','White','Diesel',155.14,1,'White_Toyota_Corolla.png'),(48,'Nissan','350z',2001,'ZSM982','Black','Regular',74.53,1,'Black_Nissan_350z.png'),(49,'Ford','Mustang',2022,'NQN359','Yellow','Diesel',173.24,1,'Yellow_Ford_Mustang.png'),(50,'Mazda','MX5',2008,'EIZ071','Blue','Regular',140.19,1,'Blue_Mazda_MX5.png'),(51,'Volkswagen','Golf GTI',2013,'VIQ201','Blue','Regular',199.52,1,'Blue_Volkswagen_Golf_GTI.png'),(52,'Mazda','MX5',2013,'HMV738','Blue','Premium',120.55,1,'Blue_Mazda_MX5.png'),(53,'Nissan','350z',2018,'FBS994','Black','Regular',110.82,1,'Black_Nissan_350z.png'),(54,'Audi','A3',2003,'VQE263','Aqua','Diesel',132.87,1,'Aqua_Audi_A3_Convertible.png'),(55,'Toyota','Corolla',2017,'MUV156','White','Premium',93.00,1,'White_Toyota_Corolla.png'),(56,'Mazda','Axela',2012,'YRH239','Red','Regular',105.47,0,'Red_Mazda_Axela.png'),(57,'BMW','M4',2014,'LDX580','Grey','Diesel',124.90,1,'Grey_BMW_M4.png'),(58,'Toyota','Rav4',2001,'WGX927','White','Diesel',98.32,1,'White_Toyota_Rav4.png'),(59,'Ford','Mustang',2011,'CSB748','Yellow','Regular',87.60,1,'Yellow_Ford_Mustang.png'),(60,'Mazda','MX5',2007,'TXZ980','Black','Premium',115.39,1,'Black_Mazda_MX5.png'),(61,'Volkswagen','Golf GTI',2016,'JWL481','Blue','Diesel',65.83,1,'Blue_Volkswagen_Golf_GTI.png'),(62,'Audi','A3',2015,'KMN513','Aqua','Regular',184.10,1,'Aqua_Audi_A3_Convertible.png'),(64,'BMW','M4',2010,'YCN086','Grey','Regular',125.41,1,'Grey_BMW_M4.png');
/*!40000 ALTER TABLE `VEHICLE` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-06-01  5:30:43

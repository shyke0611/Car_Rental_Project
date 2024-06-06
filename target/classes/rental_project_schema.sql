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
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CLIENT`
--

LOCK TABLES `CLIENT` WRITE;
/*!40000 ALTER TABLE `CLIENT` DISABLE KEYS */;
INSERT INTO `CLIENT` VALUES (1,'John','john_doe1','password1','0214567890','ABCD1231'),(2,'Jane','jane_doe2','password2','0224567891','ABCD1232'),(3,'Alice','alice_wonder3','password3','0204567892','ABCD1233'),(4,'Bob','bob_builder4','password4','0214567893','ABCD1234'),(5,'Charlie','charlie_brown5','password5','0224567894','ABCD1235'),(6,'David','david_king6','password6','0204567895','ABCD1236'),(7,'Eva','eva_queen7','password7','0214567896','ABCD1237'),(8,'Frank','frank_white8','password8','0224567897','ABCD1238'),(9,'Grace','grace_black9','password9','0204567898','ABCD1239'),(10,'Hannah','hannah_green10','password10','0214567899','ABCD1240'),(11,'Ivy','ivy_blue11','password11','0224567810','ABCD1241'),(12,'Jack','jack_black12','password12','0204567811','ABCD1242'),(13,'Kate','kate_white13','password13','0214567812','ABCD1243'),(14,'Liam','liam_young14','password14','0224567813','ABCD1244'),(15,'Mia','mia_smith15','password15','0204567814','ABCD1245'),(16,'Noah','noah_jones16','password16','0214567815','ABCD1246'),(17,'Olivia','olivia_james17','password17','0224567816','ABCD1247'),(18,'Paul','paul_green18','password18','0204567817','ABCD1248'),(19,'Quinn','quinn_harper19','password19','0214567818','ABCD1249'),(20,'Ruby','ruby_clark20','password20','0224567819','ABCD1250'),(21,'Sam','sam_adams21','password21','0204567820','ABCD1251'),(22,'Tina','tina_ross22','password22','0214567821','ABCD1252'),(23,'Uma','uma_hall23','password23','0224567822','ABCD1253'),(24,'Vince','vince_bryant24','password24','0204567823','ABCD1254'),(25,'Wade','wade_morgan25','password25','0214567824','ABCD1255'),(26,'Xena','xena_lawson26','password26','0224567825','ABCD1256'),(27,'Yara','yara_watson27','password27','0204567826','ABCD1257'),(28,'Zane','zane_hunt28','password28','0214567827','ABCD1258'),(29,'Aaron','aaron_hayes29','password29','0224567828','ABCD1259'),(30,'Bella','bella_king30','password30','0204567829','ABCD1260'),(31,'Caleb','caleb_wood31','password31','0214567830','ABCD1261'),(32,'Diana','diana_price32','password32','0224567831','ABCD1262'),(33,'Evan','evan_jenkins33','password33','0204567832','ABCD1263'),(34,'Faith','faith_wells34','password34','0214567833','ABCD1264'),(35,'George','george_lowe35','password35','0224567834','ABCD1265'),(36,'Holly','holly_hunt36','password36','0204567835','ABCD1266'),(37,'Ian','ian_miles37','password37','0214567836','ABCD1267'),(38,'Judy','judy_owens38','password38','0224567837','ABCD1268'),(39,'Kyle','kyle_bishop39','password39','0204567838','ABCD1269'),(40,'Lara','lara_cook40','password40','0214567839','ABCD1270'),(41,'Mason','mason_perry41','password41','0224567840','ABCD1271'),(42,'Nina','nina_ross42','password42','0204567841','ABCD1272'),(43,'Oscar','oscar_foster43','password43','0214567842','ABCD1273'),(44,'Penny','penny_warren44','password44','0224567843','ABCD1274'),(45,'Quincy','quincy_ellis45','password45','0204567844','ABCD1275'),(46,'Rachel','rachel_ford46','password46','0214567845','ABCD1276'),(47,'Steve','steve_bryant47','password47','0224567846','ABCD1277'),(48,'Tara','tara_ellis48','password48','0204567847','ABCD1278'),(49,'Ulysses','ulysses_rice49','password49','0214567848','ABCD1279'),(50,'Vivian','vivian_law50','password50','0224567849','ABCD1280');
/*!40000 ALTER TABLE `CLIENT` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `PAYMENT`
--

DROP TABLE IF EXISTS `PAYMENT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `PAYMENT` (
  `Payment_Id` int NOT NULL AUTO_INCREMENT,
  `Rental_Id` int NOT NULL,
  `Client_Id` int NOT NULL,
  `Payment_Date` date NOT NULL,
  `Amount` decimal(10,2) NOT NULL,
  `Payment_Method` enum('Credit Card','Debit Card') NOT NULL,
  PRIMARY KEY (`Payment_Id`),
  KEY `Rental_Id` (`Rental_Id`),
  KEY `Client_Id` (`Client_Id`),
  CONSTRAINT `PAYMENT_ibfk_1` FOREIGN KEY (`Rental_Id`) REFERENCES `RESERVATION` (`Rental_Id`),
  CONSTRAINT `PAYMENT_ibfk_2` FOREIGN KEY (`Client_Id`) REFERENCES `CLIENT` (`C_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PAYMENT`
--

LOCK TABLES `PAYMENT` WRITE;
/*!40000 ALTER TABLE `PAYMENT` DISABLE KEYS */;
/*!40000 ALTER TABLE `PAYMENT` ENABLE KEYS */;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `RESERVATION`
--

LOCK TABLES `RESERVATION` WRITE;
/*!40000 ALTER TABLE `RESERVATION` DISABLE KEYS */;
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
  `Fuel_economy` varchar(3) NOT NULL,
  `Daily_rate` decimal(10,2) NOT NULL,
  `Availability` tinyint(1) NOT NULL DEFAULT '1',
  `image_path` varchar(255) NOT NULL,
  PRIMARY KEY (`V_Id`),
  UNIQUE KEY `Reg_no` (`Reg_no`)
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `VEHICLE`
--

LOCK TABLES `VEHICLE` WRITE;
/*!40000 ALTER TABLE `VEHICLE` DISABLE KEYS */;
INSERT INTO `VEHICLE` VALUES (1,'Audi','A3 Convertible',2019,'AAA111','Aqua','Premium','100',150.00,1,'Aqua_Audi_A3_Convertible.png'),(2,'Mazda','MX5',2020,'BBB222','Black','Regular','80',120.00,1,'Black_Mazda_MX5.png'),(3,'Nissan','350z',2018,'CCC333','Black','Diesel','120',130.00,1,'Black_Nissan_350z.png'),(4,'Mazda','MX5',2021,'DDD444','Blue','Regular','90',110.00,1,'Blue_Mazda_MX5.png'),(5,'Volkswagen','Golf GTI',2020,'EEE555','Blue','Regular','80',140.00,1,'Blue_Volkswagen_Golf_GTI.png'),(6,'BMW','M4',2019,'FFF666','Grey','Premium','100',200.00,1,'Grey_BMW_M4.png'),(7,'Mazda','Axela',2018,'GGG777','Red','Regular','70',90.00,1,'Red_Mazda_Axela.png'),(8,'Toyota','Corolla',2020,'HHH888','White','Regular','80',80.00,1,'White_Toyota_Corolla.png'),(9,'Toyota','Rav4',2021,'III999','White','Diesel','90',100.00,1,'White_Toyota_Rav4.png'),(10,'Ford','Mustang',2019,'JJJ000','Yellow','Premium','120',180.00,1,'Yellow_Ford_Mustang.png'),(11,'Audi','A3 Convertible',2020,'KKK111','Aqua','Premium','100',150.00,1,'Aqua_Audi_A3_Convertible.png'),(12,'Mazda','MX5',2021,'LLL222','Black','Regular','80',120.00,1,'Black_Mazda_MX5.png'),(13,'Nissan','350z',2019,'MMM333','Black','Diesel','120',130.00,1,'Black_Nissan_350z.png'),(14,'Mazda','MX5',2018,'NNN444','Blue','Regular','90',110.00,1,'Blue_Mazda_MX5.png'),(15,'Volkswagen','Golf GTI',2019,'OOO555','Blue','Regular','80',140.00,1,'Blue_Volkswagen_Golf_GTI.png'),(16,'BMW','M4',2020,'PPP666','Grey','Premium','100',200.00,1,'Grey_BMW_M4.png'),(17,'Mazda','Axela',2021,'QQQ777','Red','Regular','70',90.00,1,'Red_Mazda_Axela.png'),(18,'Toyota','Corolla',2018,'RRR888','White','Regular','80',80.00,1,'White_Toyota_Corolla.png'),(19,'Toyota','Rav4',2019,'SSS999','White','Diesel','90',100.00,1,'White_Toyota_Rav4.png'),(20,'Ford','Mustang',2020,'TTT000','Yellow','Premium','120',180.00,1,'Yellow_Ford_Mustang.png'),(21,'Audi','A3 Convertible',2021,'UUU111','Aqua','Premium','100',150.00,1,'Aqua_Audi_A3_Convertible.png'),(22,'Mazda','MX5',2019,'VVV222','Black','Regular','80',120.00,1,'Black_Mazda_MX5.png'),(23,'Nissan','350z',2020,'WWW333','Black','Diesel','120',130.00,1,'Black_Nissan_350z.png'),(24,'Mazda','MX5',2019,'XXX444','Blue','Regular','90',110.00,1,'Blue_Mazda_MX5.png'),(25,'Volkswagen','Golf GTI',2021,'YYY555','Blue','Regular','80',140.00,1,'Blue_Volkswagen_Golf_GTI.png'),(26,'BMW','M4',2021,'ZZZ666','Grey','Premium','100',200.00,1,'Grey_BMW_M4.png'),(27,'Mazda','Axela',2020,'AAA777','Red','Regular','70',90.00,1,'Red_Mazda_Axela.png'),(28,'Toyota','Corolla',2021,'BBB888','White','Regular','80',80.00,1,'White_Toyota_Corolla.png'),(29,'Toyota','Rav4',2018,'CCC999','White','Diesel','90',100.00,1,'White_Toyota_Rav4.png'),(30,'Ford','Mustang',2018,'DDD000','Yellow','Premium','120',180.00,1,'Yellow_Ford_Mustang.png'),(31,'Audi','A3 Convertible',2018,'EEE111','Aqua','Premium','100',150.00,1,'Aqua_Audi_A3_Convertible.png'),(32,'Mazda','MX5',2018,'FFF222','Black','Regular','80',120.00,1,'Black_Mazda_MX5.png'),(33,'Nissan','350z',2019,'GGG333','Black','Diesel','120',130.00,1,'Black_Nissan_350z.png'),(34,'Mazda','MX5',2020,'HHH444','Blue','Regular','90',110.00,1,'Blue_Mazda_MX5.png'),(35,'Volkswagen','Golf GTI',2018,'III555','Blue','Regular','80',140.00,1,'Blue_Volkswagen_Golf_GTI.png'),(36,'BMW','M4',2018,'JJJ666','Grey','Premium','100',200.00,1,'Grey_BMW_M4.png'),(37,'Mazda','Axela',2019,'KKK777','Red','Regular','70',90.00,1,'Red_Mazda_Axela.png'),(38,'Toyota','Corolla',2019,'LLL888','White','Regular','80',80.00,1,'White_Toyota_Corolla.png'),(39,'Toyota','Rav4',2020,'MMM999','White','Diesel','90',100.00,1,'White_Toyota_Rav4.png'),(40,'Ford','Mustang',2021,'NNN000','Yellow','Premium','120',180.00,1,'Yellow_Ford_Mustang.png'),(41,'Audi','A3 Convertible',2020,'OOO111','Aqua','Premium','100',150.00,1,'Aqua_Audi_A3_Convertible.png'),(42,'Mazda','MX5',2021,'PPP222','Black','Regular','80',120.00,1,'Black_Mazda_MX5.png'),(43,'Nissan','350z',2020,'QQQ333','Black','Diesel','120',130.00,1,'Black_Nissan_350z.png'),(44,'Mazda','MX5',2018,'RRR444','Blue','Regular','90',110.00,1,'Blue_Mazda_MX5.png'),(45,'Volkswagen','Golf GTI',2021,'SSS555','Blue','Regular','80',140.00,1,'Blue_Volkswagen_Golf_GTI.png'),(46,'BMW','M4',2019,'TTT666','Grey','Premium','100',200.00,1,'Grey_BMW_M4.png'),(47,'Mazda','Axela',2018,'UUU777','Red','Regular','70',90.00,1,'Red_Mazda_Axela.png'),(48,'Toyota','Corolla',2020,'VVV888','White','Regular','80',80.00,1,'White_Toyota_Corolla.png'),(49,'Toyota','Rav4',2019,'WWW999','White','Diesel','90',100.00,1,'White_Toyota_Rav4.png'),(50,'Ford','Mustang',2018,'XXX000','Yellow','Premium','120',180.00,1,'Yellow_Ford_Mustang.png'),(51,'Audi','A3 Convertible',2021,'YYY111','Aqua','Premium','100',150.00,1,'Aqua_Audi_A3_Convertible.png'),(52,'Mazda','MX5',2020,'ZZZ222','Black','Regular','80',120.00,1,'Black_Mazda_MX5.png'),(53,'Nissan','350z',2019,'AAA333','Black','Diesel','120',130.00,1,'Black_Nissan_350z.png'),(54,'Mazda','MX5',2021,'BBB444','Blue','Regular','90',110.00,1,'Blue_Mazda_MX5.png'),(55,'Volkswagen','Golf GTI',2020,'CCC555','Blue','Regular','80',140.00,1,'Blue_Volkswagen_Golf_GTI.png'),(56,'BMW','M4',2021,'DDD666','Grey','Premium','100',200.00,1,'Grey_BMW_M4.png'),(57,'Mazda','Axela',2019,'EEE777','Red','Regular','70',90.00,1,'Red_Mazda_Axela.png'),(58,'Toyota','Corolla',2018,'FFF888','White','Regular','80',80.00,1,'White_Toyota_Corolla.png'),(59,'Toyota','Rav4',2021,'GGG999','White','Diesel','90',100.00,1,'White_Toyota_Rav4.png'),(60,'Ford','Mustang',2019,'HHH000','Yellow','Premium','120',180.00,1,'Yellow_Ford_Mustang.png');
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

-- Dump completed on 2024-06-06 13:48:56

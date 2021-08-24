CREATE DATABASE  IF NOT EXISTS `Biblioteket_jenluj9` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `Biblioteket_jenluj9`;
-- MySQL dump 10.13  Distrib 8.0.25, for Win64 (x86_64)
--
-- Host: localhost    Database: Biblioteket_jenluj9
-- ------------------------------------------------------
-- Server version	8.0.25

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
-- Table structure for table `bokförfattare`
--

DROP TABLE IF EXISTS `bokförfattare`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bokförfattare` (
  `FörfattareID` int NOT NULL,
  `ObjektID` int NOT NULL,
  PRIMARY KEY (`FörfattareID`,`ObjektID`),
  KEY `fk_Författare_has_Produkt_Författare_idx` (`FörfattareID`),
  KEY `fk_BokFörfattare_Objekt1_idx` (`ObjektID`),
  CONSTRAINT `fk_BokFörfattare_Objekt1` FOREIGN KEY (`ObjektID`) REFERENCES `objekt` (`ObjektID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_Författare_has_Produkt_Författare` FOREIGN KEY (`FörfattareID`) REFERENCES `författare` (`FörfattareID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bokförfattare`
--

LOCK TABLES `bokförfattare` WRITE;
/*!40000 ALTER TABLE `bokförfattare` DISABLE KEYS */;
INSERT INTO `bokförfattare` VALUES (1,98),(2,4),(3,5),(3,96),(4,4),(4,5),(4,97),(6,6),(6,7),(6,8),(6,97);
/*!40000 ALTER TABLE `bokförfattare` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `filmregisöraktör`
--

DROP TABLE IF EXISTS `filmregisöraktör`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `filmregisöraktör` (
  `RegisörAktörID` int NOT NULL,
  `ObjektID` int NOT NULL,
  `typ` varchar(45) NOT NULL,
  PRIMARY KEY (`RegisörAktörID`,`ObjektID`),
  KEY `fk_FilmRegisör_Författare_copy11_idx` (`RegisörAktörID`),
  KEY `fk_FilmRegisörAktör_Objekt1_idx` (`ObjektID`),
  CONSTRAINT `fk_FilmRegisör_Författare_copy11` FOREIGN KEY (`RegisörAktörID`) REFERENCES `regisöraktör` (`RegisörAktörID`),
  CONSTRAINT `fk_FilmRegisörAktör_Objekt1` FOREIGN KEY (`ObjektID`) REFERENCES `objekt` (`ObjektID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `filmregisöraktör`
--

LOCK TABLES `filmregisöraktör` WRITE;
/*!40000 ALTER TABLE `filmregisöraktör` DISABLE KEYS */;
INSERT INTO `filmregisöraktör` VALUES (1,2,'Reg'),(1,11,'Akt'),(2,2,'Akt'),(4,10,'Reg'),(5,10,'Akt'),(6,10,'Akt');
/*!40000 ALTER TABLE `filmregisöraktör` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `författare`
--

DROP TABLE IF EXISTS `författare`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `författare` (
  `FörfattareID` int NOT NULL AUTO_INCREMENT,
  `fNamn` varchar(45) NOT NULL,
  `eNamn` varchar(45) NOT NULL,
  PRIMARY KEY (`FörfattareID`),
  UNIQUE KEY `författarID_UNIQUE` (`FörfattareID`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `författare`
--

LOCK TABLES `författare` WRITE;
/*!40000 ALTER TABLE `författare` DISABLE KEYS */;
INSERT INTO `författare` VALUES (1,'Stephen','King'),(2,'Robert','Jordan'),(3,'Astrid','Lindgren'),(4,'Kamilla','Läcke'),(5,'Ursula K','LeGuinn'),(6,'Karin','Eldig');
/*!40000 ALTER TABLE `författare` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `klassificering`
--

DROP TABLE IF EXISTS `klassificering`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `klassificering` (
  `KlassificeringID` int NOT NULL AUTO_INCREMENT,
  `Ämnesord` varchar(45) NOT NULL,
  `KlassificeringTyp` varchar(45) DEFAULT NULL COMMENT 'Film, tidsskrift, bok\n',
  PRIMARY KEY (`KlassificeringID`),
  UNIQUE KEY `Ämnesord_UNIQUE` (`Ämnesord`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `klassificering`
--

LOCK TABLES `klassificering` WRITE;
/*!40000 ALTER TABLE `klassificering` DISABLE KEYS */;
INSERT INTO `klassificering` VALUES (1,'Thriller','Allmän'),(2,'Skogsbruk','Allmän'),(3,'Fantasy','Allmän'),(4,'Skräck','Allmän'),(5,'Drama','Allmän'),(6,'Populärvetenskap','Allmän');
/*!40000 ALTER TABLE `klassificering` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `kopia`
--

DROP TABLE IF EXISTS `kopia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `kopia` (
  `streckkod` int NOT NULL,
  `ObjektID` int NOT NULL,
  `LåneKategori` varchar(20) NOT NULL,
  `Placering` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`streckkod`),
  KEY `fk_Kopia_Produkt1_idx` (`ObjektID`),
  KEY `fk_Kopia_Lånetid1_idx` (`LåneKategori`),
  CONSTRAINT `fk_Kopia_Lånetid1` FOREIGN KEY (`LåneKategori`) REFERENCES `maxlånetid` (`Kategori`),
  CONSTRAINT `fk_Kopia_Produkt1` FOREIGN KEY (`ObjektID`) REFERENCES `objekt` (`ObjektID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kopia`
--

LOCK TABLES `kopia` WRITE;
/*!40000 ALTER TABLE `kopia` DISABLE KEYS */;
INSERT INTO `kopia` VALUES (111,97,'Bok','A'),(112,96,'Kurslitteratur','A'),(113,96,'Referenslitteratur','C'),(159,98,'Bok','A'),(160,98,'Kurslitteratur','A'),(161,98,'Referenslitteratur','C'),(222,97,'Kurslitteratur','A'),(333,5,'Kurslitteratur','A'),(334,11,'Film','o'),(444,11,'Bok','Valv'),(555,5,'Bok','B'),(666,8,'Bok','A'),(667,8,'Kurslitteratur','A'),(668,8,'Referenslitteratur','C'),(777,4,'Bok','AB'),(1234,6,'Referenslitteratur','D'),(8877,4,'Bok','AB'),(8887,4,'Kurslitteratur','A'),(55555,7,'Kurslitteratur','Ku'),(89555,10,'Film','kq'),(111225,2,'Film','Film'),(951753,9,'Referenslitteratur','Oo');
/*!40000 ALTER TABLE `kopia` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lån`
--

DROP TABLE IF EXISTS `lån`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `lån` (
  `lånID` int NOT NULL AUTO_INCREMENT,
  `DatumLån` date NOT NULL,
  `ReturSenast` date NOT NULL,
  `DatumRetur` date DEFAULT NULL,
  `streckkod` int NOT NULL,
  `Låntagare` int NOT NULL,
  `Skuld` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`lånID`),
  UNIQUE KEY `lånID_UNIQUE` (`lånID`),
  KEY `fk_Lån_Kopia1_idx` (`streckkod`),
  KEY `fk_Lån_Individ1_idx` (`Låntagare`),
  CONSTRAINT `fk_Lån_Kopia` FOREIGN KEY (`streckkod`) REFERENCES `kopia` (`streckkod`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_Lån_Kund` FOREIGN KEY (`Låntagare`) REFERENCES `låntagare` (`PersonID`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=74 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lån`
--

LOCK TABLES `lån` WRITE;
/*!40000 ALTER TABLE `lån` DISABLE KEYS */;
INSERT INTO `lån` VALUES (12,'2021-07-22','2021-07-29','2021-08-19',111225,15,'OBETALD'),(21,'2021-08-03','2021-08-17','2021-08-15',55555,11,'NONE'),(27,'2021-08-12','2021-08-19','2021-08-19',89555,18,'NONE'),(41,'2021-08-15','2021-08-22','2021-08-19',444,11,'NONE'),(42,'2021-08-15','2021-08-22','2021-08-19',334,11,'NONE'),(61,'2021-08-18','2021-09-01','2021-08-18',8887,18,'NONE'),(62,'2021-08-18','2021-09-17','2021-08-19',555,18,'NONE'),(63,'2021-07-18','2021-08-17','2021-08-19',666,18,'NONE'),(65,'2021-08-18','2021-09-17','2021-08-19',8877,18,'NONE'),(66,'2021-08-19','2021-09-02','2021-08-19',667,11,'NONE'),(67,'2021-07-19','2021-08-18',NULL,8877,11,NULL),(68,'2021-08-20','2021-09-03','2021-08-22',8887,18,'NONE'),(69,'2021-08-20','2021-09-03','2021-08-20',112,18,'NONE'),(70,'2021-08-20','2021-09-19','2021-08-22',111,18,'NONE'),(71,'2021-08-22','2021-08-29',NULL,111225,18,NULL),(72,'2021-07-22','2021-08-05',NULL,333,18,NULL),(73,'2021-08-22','2021-09-21',NULL,555,18,NULL);
/*!40000 ALTER TABLE `lån` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `låntagare`
--

DROP TABLE IF EXISTS `låntagare`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `låntagare` (
  `PersonID` int NOT NULL AUTO_INCREMENT,
  `telNr` int NOT NULL,
  `gatuaddress` varchar(45) DEFAULT NULL,
  `postNr` int NOT NULL,
  `låntagareKategori` varchar(22) NOT NULL,
  PRIMARY KEY (`PersonID`),
  UNIQUE KEY `personID_UNIQUE` (`PersonID`),
  KEY `fk_Låntagare_LåntagareKategori1_idx` (`låntagareKategori`),
  CONSTRAINT `fk_Låntagare_LåntagareKategori1` FOREIGN KEY (`låntagareKategori`) REFERENCES `låntagarekategori` (`LåntagareKategori`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `låntagare`
--

LOCK TABLES `låntagare` WRITE;
/*!40000 ALTER TABLE `låntagare` DISABLE KEYS */;
INSERT INTO `låntagare` VALUES (11,701232250,'Kolvgatan 5',56444,'Student'),(12,705552269,'Ingens väg2',45655,'Forskare'),(13,731235555,'Ingens väg 5',85974,'Allmänn'),(14,70658585,'Gjallarvägen 88',85462,'Universitetsanställd'),(15,725421144,' Allas väg 5',85462,'Student'),(18,123456,'Allas väg 5',85462,'Allmänn');
/*!40000 ALTER TABLE `låntagare` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `låntagarekategori`
--

DROP TABLE IF EXISTS `låntagarekategori`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `låntagarekategori` (
  `LåntagareKategori` varchar(22) NOT NULL,
  `simultanaLån` int DEFAULT NULL,
  PRIMARY KEY (`LåntagareKategori`),
  UNIQUE KEY `LåntagareKategori_UNIQUE` (`LåntagareKategori`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `låntagarekategori`
--

LOCK TABLES `låntagarekategori` WRITE;
/*!40000 ALTER TABLE `låntagarekategori` DISABLE KEYS */;
INSERT INTO `låntagarekategori` VALUES ('Allmänn',5),('Forskare',20),('Student',15),('Universitetsanställd',10);
/*!40000 ALTER TABLE `låntagarekategori` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `maxlånetid`
--

DROP TABLE IF EXISTS `maxlånetid`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `maxlånetid` (
  `MaxLånetidID` int NOT NULL AUTO_INCREMENT,
  `Kategori` varchar(20) NOT NULL,
  `MaxLånetid` int NOT NULL,
  PRIMARY KEY (`MaxLånetidID`),
  UNIQUE KEY `kategoriID_UNIQUE` (`Kategori`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `maxlånetid`
--

LOCK TABLES `maxlånetid` WRITE;
/*!40000 ALTER TABLE `maxlånetid` DISABLE KEYS */;
INSERT INTO `maxlånetid` VALUES (1,'Kurslitteratur',14),(2,'Bok',30),(3,'Film',7),(4,'Referenslitteratur',0);
/*!40000 ALTER TABLE `maxlånetid` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `objekt`
--

DROP TABLE IF EXISTS `objekt`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `objekt` (
  `ObjektID` int NOT NULL AUTO_INCREMENT,
  `Titel` varchar(45) NOT NULL,
  `Typ` varchar(45) DEFAULT NULL COMMENT 'Bok, Tidsskrift, Film',
  `BokISBN` varchar(45) DEFAULT NULL,
  `FilmÅldersbegr` varchar(45) DEFAULT NULL COMMENT 'Klasser',
  `FilmProdLand` varchar(45) DEFAULT NULL,
  `TidskrifterID` int DEFAULT NULL,
  `TidskriftDatum` date DEFAULT NULL,
  `TidskriftNr` int DEFAULT NULL,
  PRIMARY KEY (`ObjektID`),
  UNIQUE KEY `produktID_UNIQUE` (`ObjektID`),
  KEY `TidskriftID_idx` (`TidskrifterID`),
  CONSTRAINT `TidskriftID` FOREIGN KEY (`TidskrifterID`) REFERENCES `tidsskrifter` (`TidskriftID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `objekt`
--

LOCK TABLES `objekt` WRITE;
/*!40000 ALTER TABLE `objekt` DISABLE KEYS */;
INSERT INTO `objekt` VALUES (2,'Good Will Hunting','Film',NULL,'Från 7 år','USA',NULL,NULL,NULL),(3,'Vetenskapens Värld 2005','Tidskrift',NULL,NULL,NULL,1,'2021-05-31',2003),(4,'Wheel of time','Bok','555',NULL,NULL,NULL,NULL,NULL),(5,'Kalle och hans bra vänner','Bok','4444',NULL,NULL,NULL,NULL,NULL),(6,'En gång i livet','Bok','55544433',NULL,NULL,NULL,NULL,NULL),(7,'Skam','Bok','5557778',NULL,NULL,NULL,NULL,NULL),(8,'Ingen mer i livet','Bok','1231112',NULL,NULL,NULL,NULL,NULL),(9,'Eldliv','Tidskrift',NULL,NULL,NULL,4,'2021-05-04',3),(10,'Resan till Melonia','Film',NULL,'Barntillåten','Sverige',NULL,NULL,NULL),(11,'Kalle Anka','Film',NULL,'Barntillåten','USA',NULL,NULL,NULL),(96,'Ingen förstår','Bok','777777',NULL,NULL,NULL,NULL,NULL),(97,'Borta med vinden','Bok','123',NULL,NULL,NULL,NULL,NULL),(98,'Jag är snart klar med detta','Bok','159159159',NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `objekt` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `objektklass`
--

DROP TABLE IF EXISTS `objektklass`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `objektklass` (
  `ObjektID` int NOT NULL,
  `KategoriID` int NOT NULL,
  PRIMARY KEY (`ObjektID`,`KategoriID`),
  KEY `fk_Produkt_has_Sökord_Produkt1_idx` (`ObjektID`),
  KEY `fk_ObjektÄmnesord_Ämnesord1_idx` (`KategoriID`) /*!80000 INVISIBLE */,
  CONSTRAINT `fk_Produkt_has_Sökord_Produkt1` FOREIGN KEY (`ObjektID`) REFERENCES `objekt` (`ObjektID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `objektklass`
--

LOCK TABLES `objektklass` WRITE;
/*!40000 ALTER TABLE `objektklass` DISABLE KEYS */;
INSERT INTO `objektklass` VALUES (2,2),(2,5),(2,7),(3,6),(4,3),(5,3),(6,3),(7,1),(7,2),(7,3),(7,4),(7,5),(8,3),(9,1),(10,3),(10,5),(11,5),(96,2),(96,3),(96,5),(96,6),(97,5),(98,4);
/*!40000 ALTER TABLE `objektklass` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `person`
--

DROP TABLE IF EXISTS `person`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `person` (
  `PersonID` int NOT NULL AUTO_INCREMENT,
  `fNamn` varchar(45) NOT NULL,
  `eNamn` varchar(45) NOT NULL,
  `eMail` varchar(45) NOT NULL,
  `lösenord` varchar(45) NOT NULL,
  `personTyp` varchar(45) NOT NULL,
  PRIMARY KEY (`PersonID`),
  UNIQUE KEY `personID_UNIQUE` (`PersonID`),
  UNIQUE KEY `eMail_UNIQUE` (`eMail`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `person`
--

LOCK TABLES `person` WRITE;
/*!40000 ALTER TABLE `person` DISABLE KEYS */;
INSERT INTO `person` VALUES (11,'J','All','email','pass','Låntagare'),(12,'A','Så','post@se','123','Låntagare'),(13,'B','Åhs','a@b','123','Låntagare'),(14,'C','Ceder','c@C','pass','Låntagare'),(15,'D','Dag','d@D','555','Låntagare'),(16,'Stina','Wolt','SW@biblio.se','Pass123','Bibliotekarie'),(17,'Anna','Libra','epost','pass','Bibliotekarie'),(18,'Jenni','Ljung','jelj','aaa','Allmänn');
/*!40000 ALTER TABLE `person` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `regisöraktör`
--

DROP TABLE IF EXISTS `regisöraktör`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `regisöraktör` (
  `RegisörAktörID` int NOT NULL AUTO_INCREMENT,
  `fNamn` varchar(45) NOT NULL,
  `eNamn` varchar(45) NOT NULL,
  `Regisör` tinyint NOT NULL,
  `Aktör` tinyint NOT NULL,
  PRIMARY KEY (`RegisörAktörID`),
  UNIQUE KEY `författarID_UNIQUE` (`RegisörAktörID`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `regisöraktör`
--

LOCK TABLES `regisöraktör` WRITE;
/*!40000 ALTER TABLE `regisöraktör` DISABLE KEYS */;
INSERT INTO `regisöraktör` VALUES (1,'Minnie','Driver',1,1),(2,'Mat','Daimon',1,1),(3,'Julie','Andrews',0,1),(4,'Peter','Jackson',1,0),(5,'Peter','Jöback',0,1),(6,'Juliann','Moore',0,1);
/*!40000 ALTER TABLE `regisöraktör` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reservation`
--

DROP TABLE IF EXISTS `reservation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reservation` (
  `reservID` int NOT NULL AUTO_INCREMENT,
  `reservDatum` timestamp(6) NOT NULL,
  `objektID` int NOT NULL,
  `låntagareID` int NOT NULL,
  PRIMARY KEY (`reservID`),
  UNIQUE KEY `reservID_UNIQUE` (`reservID`),
  KEY `fk_Reservation_Produkt1_idx` (`objektID`),
  KEY `fk_Reservation_Individ1_idx` (`låntagareID`),
  CONSTRAINT `fk_Reservation_Kund` FOREIGN KEY (`låntagareID`) REFERENCES `låntagare` (`PersonID`) ON UPDATE CASCADE,
  CONSTRAINT `fk_Reservation_Produkt` FOREIGN KEY (`objektID`) REFERENCES `objekt` (`ObjektID`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reservation`
--

LOCK TABLES `reservation` WRITE;
/*!40000 ALTER TABLE `reservation` DISABLE KEYS */;
/*!40000 ALTER TABLE `reservation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tidsskrifter`
--

DROP TABLE IF EXISTS `tidsskrifter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tidsskrifter` (
  `TidskriftID` int NOT NULL AUTO_INCREMENT,
  `Namn` varchar(45) NOT NULL,
  `Aktiv` tinyint NOT NULL,
  PRIMARY KEY (`TidskriftID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tidsskrifter`
--

LOCK TABLES `tidsskrifter` WRITE;
/*!40000 ALTER TABLE `tidsskrifter` DISABLE KEYS */;
INSERT INTO `tidsskrifter` VALUES (1,'Illustrerad vetenskap',1),(2,'Kamratposten',0),(3,'VK',1),(4,'Eldliv',1);
/*!40000 ALTER TABLE `tidsskrifter` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-08-22 19:31:25

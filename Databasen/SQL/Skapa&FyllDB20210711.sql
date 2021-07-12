DROP TABLE IF EXISTS `tidsskrifter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tidsskrifter` (
  `TidskriftID` int NOT NULL AUTO_INCREMENT,
  `Namn` varchar(45) NOT NULL,
  `Aktiv` tinyint NOT NULL,
  PRIMARY KEY (`TidskriftID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

INSERT INTO `tidsskrifter` VALUES (1,'Illustrerad vetenskap',1),(2,'Kamratposten',0),(3,'VK',1),(4,'Eldliv',1);

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
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;


INSERT INTO `objekt` VALUES (1,'Eldfödd','Bok','55556655',NULL,NULL,NULL,NULL,NULL),(2,'Good Will Hunting','Film',NULL,'Från 7 år','USA',NULL,NULL,NULL),(3,'Vetenskapens Värld 2005','Tidskrift',NULL,NULL,NULL,1,'2021-05-31',2003),(4,'Wheel of time','Bok','55512333',NULL,NULL,NULL,NULL,NULL),(5,'Kalle och hans vänner','Bok','55544455',NULL,NULL,NULL,NULL,NULL),(6,'En gång i livet','Bok','55544433',NULL,NULL,NULL,NULL,NULL),(7,'Skam','Bok','5557778',NULL,NULL,NULL,NULL,NULL),(8,'Ingen mer i livet','Bok','1231112',NULL,NULL,NULL,NULL,NULL),(9,'Eldliv','Tidskrift',NULL,NULL,NULL,4,'2021-05-04',3),(10,'Resan till Melonia','Film',NULL,'Barntillåten','Sverige',NULL,NULL,NULL),(11,'Kalle Anka','Film',NULL,'Barntillåten','USA',NULL,NULL,NULL);

CREATE TABLE `författare` (
  `FörfattareID` int NOT NULL AUTO_INCREMENT,
  `fNamn` varchar(45) NOT NULL,
  `eNamn` varchar(45) NOT NULL,
  PRIMARY KEY (`FörfattareID`),
  UNIQUE KEY `författarID_UNIQUE` (`FörfattareID`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

INSERT INTO `författare` VALUES (1,'Stephen','King'),(2,'Robert','Jordan'),(3,'Astrid','Lindgren'),(4,'Kamilla','Läcke'),(5,'Ursula K','LeGuinn'),(6,'Karin','Eldig');

CREATE TABLE `klassificering` (
  `KlassificeringID` int NOT NULL AUTO_INCREMENT,
  `Ämnesord` varchar(45) NOT NULL,
  `KlassificeringTyp` varchar(45) DEFAULT NULL COMMENT 'Film, tidsskrift, bok\n',
  PRIMARY KEY (`KlassificeringID`),
  UNIQUE KEY `Ämnesord_UNIQUE` (`Ämnesord`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

INSERT INTO `klassificering` VALUES (1,'Thriller','Allmän'),(2,'Skogsbruk','Allmän'),(3,'Fantasy','Allmän'),(4,'Skräck','Allmän'),(5,'Drama','Allmän'),(6,'Populärvetenskap','Allmän');

CREATE TABLE `maxlånetid` (
  `MaxLånetidID` int NOT NULL AUTO_INCREMENT,
  `Kategori` varchar(20) NOT NULL,
  `MaxLånetid` int NOT NULL,
  PRIMARY KEY (`MaxLånetidID`),
  UNIQUE KEY `kategoriID_UNIQUE` (`Kategori`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

INSERT INTO `maxlånetid` VALUES (1,'Kurslitteratur',14),(2,'Bok',30),(3,'Film',7),(4,'Referenslitteratur',0);

CREATE TABLE `kopia` (
  `streckkod` int NOT NULL,
  `ObjektID` int NOT NULL,
  `LåneKategori` varchar(20) NOT NULL,
  `Placering` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`streckkod`),
  KEY `fk_Kopia_Produkt1_idx` (`ObjektID`),
  KEY `fk_Kopia_Lånetid1_idx` (`LåneKategori`),
  CONSTRAINT `fk_Kopia_Lånetid1` FOREIGN KEY (`LåneKategori`) REFERENCES `maxlånetid` (`Kategori`) ON UPDATE CASCADE,
  CONSTRAINT `fk_Kopia_Produkt1` FOREIGN KEY (`ObjektID`) REFERENCES `objekt` (`ObjektID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `kopia` VALUES (111225,2,'Film','Film'),(112230,1,'Bok','A'),(112231,1,'Referenslitteratur','A'),(555522,1,'Kurslitteratur','A');

CREATE TABLE `låntagarekategori` (
  `LåntagareKategori` varchar(22) NOT NULL,
  `simultanaLån` int DEFAULT NULL,
  PRIMARY KEY (`LåntagareKategori`),
  UNIQUE KEY `LåntagareKategori_UNIQUE` (`LåntagareKategori`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `låntagarekategori` VALUES ('Allmänn',5),('Forskare',20),('Student',15),('Universitetsanställd',10);

CREATE TABLE `objektklass` (
  `ObjektID` int NOT NULL,
  `KategoriID` int NOT NULL,
  PRIMARY KEY (`ObjektID`,`KategoriID`),
  KEY `fk_Produkt_has_Sökord_Produkt1_idx` (`ObjektID`),
  KEY `fk_ObjektÄmnesord_Ämnesord1_idx` (`KategoriID`) /*!80000 INVISIBLE */,
  CONSTRAINT `fk_Produkt_has_Sökord_Produkt1` FOREIGN KEY (`ObjektID`) REFERENCES `objekt` (`ObjektID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `objektklass` VALUES (1,1),(1,4),(2,2),(2,5),(2,7),(3,6),(7,1),(7,2),(7,3),(7,4),(7,5),(10,3),(10,5);

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
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;


INSERT INTO `person` VALUES (11,'J','All','email','pass','Låntagare'),(12,'A','Så','post@se','123','Låntagare'),(13,'B','Åhs','a@b','123','Låntagare'),(14,'C','Ceder','c@C','pass','Låntagare'),(15,'D','Dag','d@D','555','Låntagare'),(16,'Stina','Wolt','SW@biblio.se','Pass123','Bibliotekarie'),(17,'Anna','Libra','epost','pass','Bibliotekarie');

CREATE TABLE `regisöraktör` (
  `RegisörAktörID` int NOT NULL AUTO_INCREMENT,
  `fNamn` varchar(45) NOT NULL,
  `eNamn` varchar(45) NOT NULL,
  `Regisör` tinyint NOT NULL,
  `Aktör` tinyint NOT NULL,
  PRIMARY KEY (`RegisörAktörID`),
  UNIQUE KEY `författarID_UNIQUE` (`RegisörAktörID`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

INSERT INTO `regisöraktör` VALUES (1,'Minnie','Driver',1,1),(2,'Mat','Daimon',1,1),(3,'Julie','Andrews',0,1),(4,'Peter','Jackson',1,0),(5,'Peter','Jöback',0,1),(6,'Juliann','Moore',0,1);


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
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

INSERT INTO `låntagare` VALUES (11,701232250,'Kolvgatan 5',56444,'Student'),(12,705552269,NULL,45655,'Forskare'),(13,731235555,NULL,85974,'Allmänn'),(14,70658585,NULL,85462,'Universitetsanställd'),(15,725421144,' Allas väg 5',85462,'Student');


CREATE TABLE `lån` (
  `lånID` int NOT NULL AUTO_INCREMENT,
  `DatumLån` date NOT NULL,
  `ReturSenast` date NOT NULL,
  `DatumRetur` date DEFAULT NULL,
  `streckkod` int NOT NULL,
  `Låntagare` int NOT NULL,
  PRIMARY KEY (`lånID`),
  UNIQUE KEY `lånID_UNIQUE` (`lånID`),
  KEY `fk_Lån_Kopia1_idx` (`streckkod`),
  KEY `fk_Lån_Individ1_idx` (`Låntagare`),
  CONSTRAINT `fk_Lån_Kopia` FOREIGN KEY (`streckkod`) REFERENCES `kopia` (`streckkod`) ON UPDATE CASCADE,
  CONSTRAINT `fk_Lån_Kund` FOREIGN KEY (`Låntagare`) REFERENCES `låntagare` (`PersonID`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

INSERT INTO `lån` VALUES (1,'2021-07-03','2021-08-03',NULL,111225,11),(2,'2021-06-06','2021-07-06',NULL,112230,12),(3,'2021-05-05','2021-06-05',NULL,111225,13),(4,'2021-03-03','2021-04-03','2021-05-01',112231,15),(5,'2021-04-14','2021-05-15','2021-05-01',111225,12);

CREATE TABLE `filmregisöraktör` (
  `RegisörAktörID` int NOT NULL,
  `ObjektID` int NOT NULL,
  `typ` varchar(45) NOT NULL,
  PRIMARY KEY (`RegisörAktörID`,`ObjektID`),
  KEY `fk_FilmRegisör_Författare_copy11_idx` (`RegisörAktörID`),
  KEY `fk_FilmRegisörAktör_Objekt1_idx` (`ObjektID`),
  CONSTRAINT `fk_FilmRegisör_Författare_copy11` FOREIGN KEY (`RegisörAktörID`) REFERENCES `regisöraktör` (`RegisörAktörID`),
  CONSTRAINT `fk_FilmRegisörAktör_Objekt1` FOREIGN KEY (`ObjektID`) REFERENCES `objekt` (`ObjektID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `filmregisöraktör` VALUES (1,1,'Reg'),(1,2,'Reg'),(2,2,'Akt'),(4,10,'Reg'),(5,10,'Akt'),(6,10,'Akt');

CREATE TABLE `bokförfattare` (
  `FörfattareID` int NOT NULL,
  `ObjektID` int NOT NULL,
  PRIMARY KEY (`FörfattareID`,`ObjektID`),
  KEY `fk_Författare_has_Produkt_Författare_idx` (`FörfattareID`),
  KEY `fk_BokFörfattare_Objekt1_idx` (`ObjektID`),
  CONSTRAINT `fk_BokFörfattare_Objekt1` FOREIGN KEY (`ObjektID`) REFERENCES `objekt` (`ObjektID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_Författare_has_Produkt_Författare` FOREIGN KEY (`FörfattareID`) REFERENCES `författare` (`FörfattareID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `bokförfattare` VALUES (1,1),(2,4),(3,5),(4,6),(5,4),(6,7),(6,8);

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;





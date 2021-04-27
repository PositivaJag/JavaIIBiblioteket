-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema JavaIIBiblioteket
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema JavaIIBiblioteket
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `JavaIIBiblioteket` DEFAULT CHARACTER SET utf8 ;
USE `JavaIIBiblioteket` ;

-- -----------------------------------------------------
-- Table `JavaIIBiblioteket`.`Författare`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `JavaIIBiblioteket`.`Författare` (
  `FörfattareID` INT NOT NULL AUTO_INCREMENT,
  `fNamn` VARCHAR(45) NOT NULL,
  `eNamn` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`FörfattareID`),
  UNIQUE INDEX `författarID_UNIQUE` (`FörfattareID` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `JavaIIBiblioteket`.`Objekt`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `JavaIIBiblioteket`.`Objekt` (
  `ObjektID` INT NOT NULL AUTO_INCREMENT,
  `Titel` VARCHAR(45) NOT NULL,
  `Typ` VARCHAR(45) NULL COMMENT 'Bok, Tidsskrift, Film',
  `BokISBN` VARCHAR(45) NULL,
  `FilmÅldersbegr` VARCHAR(45) NULL COMMENT 'Klasser',
  `FilmProdLand` VARCHAR(45) NULL,
  PRIMARY KEY (`ObjektID`),
  UNIQUE INDEX `produktID_UNIQUE` (`ObjektID` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `JavaIIBiblioteket`.`BokFörfattare`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `JavaIIBiblioteket`.`BokFörfattare` (
  `FörfattareID` INT NOT NULL,
  `ObjektID` INT NOT NULL,
  PRIMARY KEY (`FörfattareID`, `ObjektID`),
  INDEX `fk_Författare_has_Produkt_Författare_idx` (`FörfattareID` ASC) VISIBLE,
  INDEX `fk_BokFörfattare_Objekt1_idx` (`ObjektID` ASC) VISIBLE,
  CONSTRAINT `fk_Författare_has_Produkt_Författare`
    FOREIGN KEY (`FörfattareID`)
    REFERENCES `JavaIIBiblioteket`.`Författare` (`FörfattareID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_BokFörfattare_Objekt1`
    FOREIGN KEY (`ObjektID`)
    REFERENCES `JavaIIBiblioteket`.`Objekt` (`ObjektID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `JavaIIBiblioteket`.`MaxLånetid`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `JavaIIBiblioteket`.`MaxLånetid` (
  `MaxLånetidID` INT NOT NULL AUTO_INCREMENT,
  `Kategori` VARCHAR(8) NOT NULL,
  `MaxLånetid` INT NOT NULL,
  UNIQUE INDEX `kategoriID_UNIQUE` (`Kategori` ASC) VISIBLE,
  PRIMARY KEY (`MaxLånetidID`),
  UNIQUE INDEX `MaxLånetidID_UNIQUE` (`MaxLånetidID` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `JavaIIBiblioteket`.`Kopia`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `JavaIIBiblioteket`.`Kopia` (
  `streckkod` INT NOT NULL,
  `ObjektID` INT NOT NULL,
  `LåneKategori` VARCHAR(8) NOT NULL,
  `Placering` VARCHAR(45) NULL,
  PRIMARY KEY (`streckkod`),
  INDEX `fk_Kopia_Produkt1_idx` (`ObjektID` ASC) VISIBLE,
  INDEX `fk_Kopia_Lånetid1_idx` (`LåneKategori` ASC) VISIBLE,
  CONSTRAINT `fk_Kopia_Produkt1`
    FOREIGN KEY (`ObjektID`)
    REFERENCES `JavaIIBiblioteket`.`Objekt` (`ObjektID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_Kopia_Lånetid1`
    FOREIGN KEY (`LåneKategori`)
    REFERENCES `JavaIIBiblioteket`.`MaxLånetid` (`Kategori`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `JavaIIBiblioteket`.`LåntagareKategori`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `JavaIIBiblioteket`.`LåntagareKategori` (
  `LåntagareKategori` VARCHAR(8) NOT NULL,
  UNIQUE INDEX `LåntagareKategori_UNIQUE` (`LåntagareKategori` ASC) VISIBLE,
  PRIMARY KEY (`LåntagareKategori`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `JavaIIBiblioteket`.`Låntagare`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `JavaIIBiblioteket`.`Låntagare` (
  `LåntagareID` INT NOT NULL AUTO_INCREMENT,
  `fNamn` VARCHAR(45) NOT NULL,
  `eNamn` VARCHAR(45) NOT NULL,
  `telNr` INT NOT NULL,
  `gatuaddress` VARCHAR(45) NOT NULL,
  `eMail` VARCHAR(45) NOT NULL,
  `postNr` INT NULL,
  `låntagareKategori` VARCHAR(8) NOT NULL,
  PRIMARY KEY (`LåntagareID`),
  UNIQUE INDEX `personID_UNIQUE` (`LåntagareID` ASC) VISIBLE,
  UNIQUE INDEX `eMail_UNIQUE` (`eMail` ASC) VISIBLE,
  INDEX `fk_Låntagare_LåntagareKategori1_idx` (`låntagareKategori` ASC) VISIBLE,
  CONSTRAINT `fk_Låntagare_LåntagareKategori1`
    FOREIGN KEY (`låntagareKategori`)
    REFERENCES `JavaIIBiblioteket`.`LåntagareKategori` (`LåntagareKategori`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `JavaIIBiblioteket`.`Lån`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `JavaIIBiblioteket`.`Lån` (
  `lånID` INT NOT NULL AUTO_INCREMENT,
  `DatumLån` DATE NOT NULL,
  `ReturSenast` DATE NOT NULL,
  `DatumRetur` DATE NULL,
  `streckkod` INT NOT NULL,
  `Låntagare` INT NOT NULL,
  PRIMARY KEY (`lånID`),
  UNIQUE INDEX `lånID_UNIQUE` (`lånID` ASC) VISIBLE,
  INDEX `fk_Lån_Kopia1_idx` (`streckkod` ASC) VISIBLE,
  INDEX `fk_Lån_Individ1_idx` (`Låntagare` ASC) VISIBLE,
  CONSTRAINT `fk_Lån_Kopia`
    FOREIGN KEY (`streckkod`)
    REFERENCES `JavaIIBiblioteket`.`Kopia` (`streckkod`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT `fk_Lån_Kund`
    FOREIGN KEY (`Låntagare`)
    REFERENCES `JavaIIBiblioteket`.`Låntagare` (`LåntagareID`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `JavaIIBiblioteket`.`Reservation`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `JavaIIBiblioteket`.`Reservation` (
  `reservID` INT NOT NULL AUTO_INCREMENT,
  `reservDatum` TIMESTAMP(6) NOT NULL,
  `objektID` INT NOT NULL,
  `låntagareID` INT NOT NULL,
  PRIMARY KEY (`reservID`),
  INDEX `fk_Reservation_Produkt1_idx` (`objektID` ASC) VISIBLE,
  UNIQUE INDEX `reservID_UNIQUE` (`reservID` ASC) VISIBLE,
  INDEX `fk_Reservation_Individ1_idx` (`låntagareID` ASC) VISIBLE,
  CONSTRAINT `fk_Reservation_Produkt`
    FOREIGN KEY (`objektID`)
    REFERENCES `JavaIIBiblioteket`.`Objekt` (`ObjektID`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT `fk_Reservation_Kund`
    FOREIGN KEY (`låntagareID`)
    REFERENCES `JavaIIBiblioteket`.`Låntagare` (`LåntagareID`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `JavaIIBiblioteket`.`Klassificering`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `JavaIIBiblioteket`.`Klassificering` (
  `KlassificeringID` INT NOT NULL AUTO_INCREMENT,
  `Ämnesord` VARCHAR(45) NOT NULL,
  `KlassificeringTyp` VARCHAR(45) NULL COMMENT 'Film, tidsskrift, bok\n',
  PRIMARY KEY (`KlassificeringID`),
  UNIQUE INDEX `Ämnesord_UNIQUE` (`Ämnesord` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `JavaIIBiblioteket`.`ObjektÄmnesord`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `JavaIIBiblioteket`.`ObjektÄmnesord` (
  `ObjektID` INT NOT NULL,
  `ÄmnesordID` INT NOT NULL,
  PRIMARY KEY (`ObjektID`, `ÄmnesordID`),
  INDEX `fk_Produkt_has_Sökord_Produkt1_idx` (`ObjektID` ASC) VISIBLE,
  INDEX `fk_ObjektÄmnesord_Ämnesord1_idx` (`ÄmnesordID` ASC) VISIBLE,
  CONSTRAINT `fk_Produkt_has_Sökord_Produkt1`
    FOREIGN KEY (`ObjektID`)
    REFERENCES `JavaIIBiblioteket`.`Objekt` (`ObjektID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_ObjektÄmnesord_Ämnesord1`
    FOREIGN KEY (`ÄmnesordID`)
    REFERENCES `JavaIIBiblioteket`.`Klassificering` (`KlassificeringID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `JavaIIBiblioteket`.`RegisörAktör`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `JavaIIBiblioteket`.`RegisörAktör` (
  `RegisörAktörID` INT NOT NULL AUTO_INCREMENT,
  `fNamn` VARCHAR(45) NOT NULL,
  `eNamn` VARCHAR(45) NOT NULL,
  `Regisör` TINYINT NOT NULL,
  `Aktör` TINYINT NOT NULL,
  PRIMARY KEY (`RegisörAktörID`),
  UNIQUE INDEX `författarID_UNIQUE` (`RegisörAktörID` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `JavaIIBiblioteket`.`FilmRegisörAktör`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `JavaIIBiblioteket`.`FilmRegisörAktör` (
  `RegisörAktörID` INT NOT NULL,
  `Objekt_ObjektID` INT NOT NULL,
  PRIMARY KEY (`RegisörAktörID`, `Objekt_ObjektID`),
  INDEX `fk_FilmRegisör_Författare_copy11_idx` (`RegisörAktörID` ASC) VISIBLE,
  INDEX `fk_FilmRegisörAktör_Objekt1_idx` (`Objekt_ObjektID` ASC) VISIBLE,
  CONSTRAINT `fk_FilmRegisör_Författare_copy11`
    FOREIGN KEY (`RegisörAktörID`)
    REFERENCES `JavaIIBiblioteket`.`RegisörAktör` (`RegisörAktörID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_FilmRegisörAktör_Objekt1`
    FOREIGN KEY (`Objekt_ObjektID`)
    REFERENCES `JavaIIBiblioteket`.`Objekt` (`ObjektID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `JavaIIBiblioteket`.`TidsskriftNummer`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `JavaIIBiblioteket`.`TidsskriftNummer` (
  `TidsskriftNrID` INT NOT NULL AUTO_INCREMENT,
  `ÅrMånad` VARCHAR(45) NOT NULL,
  `ObjektID` INT NOT NULL,
  PRIMARY KEY (`TidsskriftNrID`),
  INDEX `fk_TidsskriftNummer_Objekt1_idx` (`ObjektID` ASC) VISIBLE,
  CONSTRAINT `fk_TidsskriftNummer_Objekt1`
    FOREIGN KEY (`ObjektID`)
    REFERENCES `JavaIIBiblioteket`.`Objekt` (`ObjektID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `JavaIIBiblioteket`.`TidskriftKopia`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `JavaIIBiblioteket`.`TidskriftKopia` (
  `TidskriftKopiaID` INT NOT NULL AUTO_INCREMENT,
  `streckkod` VARCHAR(45) NOT NULL,
  `Placering` VARCHAR(45) NOT NULL,
  `TidsskriftNrID` INT NOT NULL,
  PRIMARY KEY (`TidskriftKopiaID`),
  UNIQUE INDEX `streckkod_UNIQUE` (`streckkod` ASC) VISIBLE,
  INDEX `fk_TidskriftKopia_TidsskriftNummer1_idx` (`TidsskriftNrID` ASC) VISIBLE,
  CONSTRAINT `fk_TidskriftKopia_TidsskriftNummer1`
    FOREIGN KEY (`TidsskriftNrID`)
    REFERENCES `JavaIIBiblioteket`.`TidsskriftNummer` (`TidsskriftNrID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

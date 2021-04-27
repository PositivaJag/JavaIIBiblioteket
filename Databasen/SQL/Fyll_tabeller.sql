INSERT INTO `javaiibiblioteket`.`maxlånetid`
(`Kategori`,`MaxLånetid`)
VALUES("Kurslitteratur", 14),
("Bok", 30),
("Film", 7),
("Referenslitteratur", 0),
("Tidskrift", 0);

select * from maxlånetid;

INSERT INTO `javaiibiblioteket`.`författare`
( `fNamn`, `eNamn`)
VALUES( "Stephen", "King"),
("Robert", "Jordan"),
("Astrid", "Lindgren"),
("Kamilla", "Läcke"),
("Ursula K", "LeGuinn");

select * from författare;

INSERT INTO `javaiibiblioteket`.`regisöraktör`
(`fNamn`,`eNamn`,`Regisör`,`Aktör`)
VALUES("Minnie", "Driver", 1, 1),
("Mat", "Daimon", 1, 1),
("Julie", "Andrews", 0, 1),
("Peter", "Jackson", 1, 0);

select * from regisöraktör;


INSERT INTO `javaiibiblioteket`.`klassificering`
(`Ämnesord`,`KlassificeringTyp`)
VALUES("Thriller", "Allmän"),
("Skogsbruk", "Allmän"),
("Fantasy", "Allmän"),
("Skräck", "Allmän"),
("Drama", "Allmän"),
("Populärvetenskap", "Allmän");

select * from klassificering;

INSERT INTO `javaiibiblioteket`.`objekt`
(`Titel`,`Typ`,`BokISBN`,`FilmÅldersbegr`,`FilmProdLand`)
VALUES
("Eldfödd", "Bok", 55556655, null, null),
("Good Will Hunting", "Film", null, "Från 7 år", "USA"),
("Vetenskapens Värld", "Tidskrift", null, null, null);

select * from objekt;

INSERT INTO `javaiibiblioteket`.`objektämnesord`
(`ObjektID`,`ÄmnesordID`)
VALUES(1, 1),
(1, 4),
(2, 5),
(3, 6);

select * from objektämnesord;

INSERT INTO `javaiibiblioteket`.`filmregisöraktör`
(`RegisörAktörID`,`Objekt_ObjektID`)
VALUES
(1, 2),
(2, 2);

select * from filmregisöraktör;

INSERT INTO `javaiibiblioteket`.`bokförfattare`
(`FörfattareID`,
`ObjektID`)
VALUES
(1,1);

select * from bokförfattare;


INSERT INTO `javaiibiblioteket`.`tidsskriftnummer`
(`ÅrMånad`,`ObjektID`)
VALUES
("2020-01", 3),
("2020-03", 3),
("2020-04", 3);

select * from tidsskriftnummer;

INSERT INTO `javaiibiblioteket`.`tidskriftkopia`
(`streckkod`,`Placering`,`TidsskriftNrID`)
VALUES
("5599851", "Tidskrifter A", 1),
("5599852", "Arkiv", 1),
("5599853", "Tidsskrifter A", 2),
("5599854", "Arkiv", 2);

select * from tidskriftkopia;

INSERT INTO `javaiibiblioteket`.`kopia`
(`streckkod`,`ObjektID`,`LåneKategori`,`Placering`)
VALUES
(112230, 1, "Bok", "A"),
(112231, 1, "Referenslitteratur", "A"),
(111225, 2, "Film", "Film"),
(555522, 1, "Kurslitteratur", "A");
;
select * from kopia;













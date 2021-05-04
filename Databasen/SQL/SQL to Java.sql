
SELECT o.ObjektID, o.Titel, o.Typ, FROM objekt o, bokförfattare b where o.objektID = b.objektID;

select o.ObjektID, o.titel, o.typ, f.författare from författare f, bokförfattare b, objekt o where f.FörfattareID = b.FörfattareID and b.ObjektID = o.objektid;


select * from bokförfattare;
select * from författare;
select * from bokförfattare;
select * from tidsskrifter;
select * from objekt;


(Select o.ObjektID, o.Titel, o.Typ  from objekt o where Titel like "%Eld%")
union
(Select o.ObjektID, o.Titel, o.Typ from objekt o 
join bokförfattare b on o.ObjektID = b.ObjektID
join författare f on b.FörfattareID = f.FörfattareID
where fNamn like "%Eld%" or eNamn like "%Eld%");


SET SCHEMA ESENZIONIFASCE;


CREATE VIEW
    ESENZIONIFASCE.COD_BELF_COMUNI (PK, COD_ISTAT, COD_BELFIORE, DATA_INIZIO, DATA_FINE ) AS
SELECT DISTINCT
    COD_COMUNE CONCAT '-' CONCAT CHAR(DATA_INIZIO) CONCAT '-' CONCAT CHAR(DATA_INIZIO_RCT) AS PK,
    COD_COMUNE,
    COD_BELFIORE,
    DATA_INIZIO,
    DATA_FINE
FROM
    RCT.COMUNI C
WHERE
    C.DATA_FINE_RCT = '9999-12-31'
AND C.DATA_ANNULLAMENTO= '0001-01-01 00:00:00.000'
UNION
SELECT DISTINCT
    COD_COMUNE CONCAT '-' CONCAT CHAR(DATA_INIZIO) CONCAT '-' CONCAT CHAR(DATA_INIZIO_RCT) AS PK,
    COD_COMUNE,
    COD_BELFIORE,
    DATA_INIZIO,
    DATA_FINE
FROM
    RCT.COMUNI_SOPPRESSI CS
WHERE
    CS.DATA_FINE_RCT = '9999-12-31'
AND CS.DATA_ANNULLAMENTO= '0001-01-01 00:00:00.000';


CREATE VIEW
    ESENZIONIFASCE.COD_BELF_STATI ( PK, COD_ISTAT, COD_BELFIORE, DATA_INIZIO, DATA_FINE ) AS
SELECT DISTINCT
    COD_STATO CONCAT '-' CONCAT CHAR(DATA_INIZIO) CONCAT '-' CONCAT CHAR(DATA_INIZIO_RCT) AS PK,
    COD_STATO,
    COD_BELFIORE,
    DATA_INIZIO,
    DATA_FINE
FROM
    RCT.STATI C
WHERE
    C.DATA_FINE_RCT = '9999-12-31'
AND C.DATA_ANNULLAMENTO= '0001-01-01 00:00:00.000';
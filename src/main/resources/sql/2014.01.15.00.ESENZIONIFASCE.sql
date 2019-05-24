-- Aggiunta indici tabella ESENZIONI_FASCE

SET SCHEMA ESENZIONIFASCE;

CREATE INDEX IDX_ESE002 ON ESENZIONI_FASCE ( ID_UNIVERSALE_ASSISTITO, DATA_INIZIO, DATA_FINE, FLAG_TIPOLOGIA );
CREATE INDEX INDEX_CHIAVE_APPLICATIVA_0 ON ESENZIONI_FASCE ( ID_UNIVERSALE_ASSISTITO, DATA_INIZIO, DATA_FINE, DATA_INIZIO_EROGAZIONE, DATA_FINE_EROGAZIONE, TIPO_ESENZIONE, FLAG_TIPOLOGIA );
CREATE UNIQUE INDEX PK_ESENZIONI_FASCE ON ESENZIONI_FASCE(ID);



-- Alter table

ALTER TABLE ESENZIONI_FASCE ADD     PROTOCOLLO              VARCHAR (18) DEFAULT '' NOT NULL;
ALTER TABLE ESENZIONI_FASCE ADD     ID_UNI_DICHIARANTE      VARCHAR (24) DEFAULT '' NOT NULL;
ALTER TABLE ESENZIONI_FASCE ADD     ID_UNI_TITOLARE         VARCHAR (24) DEFAULT '' NOT NULL;
ALTER TABLE ESENZIONI_FASCE ADD     TITOLO                  CHAR    (1)  DEFAULT '' NOT NULL;
ALTER TABLE ESENZIONI_FASCE ADD     NOTA                    VARCHAR (512);
ALTER TABLE ESENZIONI_FASCE ADD     DATA_FINE_OLD           DATE;
ALTER TABLE ESENZIONI_FASCE ADD     DATA_ORDINAMENTO        TIMESTAMP;
ALTER TABLE ESENZIONI_FASCE ADD     DATA_INSERIMENTO        TIMESTAMP DEFAULT current timestamp NOT NULL;
ALTER TABLE ESENZIONI_FASCE ADD     DATA_AGGIORNAMENTO      TIMESTAMP DEFAULT current timestamp NOT NULL;


-- Create Index
CREATE INDEX ID_UNI_DICHIARANTE ON ESENZIONI_FASCE (ID_UNI_DICHIARANTE);
CREATE INDEX ID_UNI_TITOLARE ON ESENZIONI_FASCE (ID_UNI_TITOLARE);
CREATE INDEX ID_UNIVERSALE_ASSISTITO ON ESENZIONI_FASCE (ID_UNIVERSALE_ASSISTITO);


CREATE TABLE UPLOAD_AUTOCERTIFICAZIONE (

    ID                      BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1  INCREMENT BY 1  NO CYCLE  NO CACHE),

	USER_ID                 VARCHAR (256),
	UTENTE_TOCKEN           VARCHAR (256),

    FK_ESENZIONI_FASCE      BIGINT NOT NULL,
    --FK_AUTOCERTIFICAZIONE   BIGINT NOT NULL,
    STATO_ESENZIONE         VARCHAR (10) NOT NULL, -- ATTIVA,ANNULLATA,REVOCATA
    COMUNE_CENTRO_IMPIEGO   VARCHAR (100),

    ATTIVITA		        VARCHAR (9) NOT NULL, --INSERISCI,CANCELLA,REVOCA

    DATA_INS_REC            TIMESTAMP, --dd/MM/yyyy hh.mm.ss
    DATA_AGG_REC            TIMESTAMP, --dd/MM/yyyy hh.mm.ss

    ESITO                   CHAR (4),
    DIAGNOSTICO             VARCHAR (512),

    DATA_INSERIMENTO        TIMESTAMP DEFAULT current timestamp NOT NULL,
    DATA_AGGIORNAMENTO      TIMESTAMP DEFAULT current timestamp NOT NULL


) IN TBS_ESENZIONIFA_N0 INDEX IN TBS_ESENZIONIFA_N0 COMPRESS YES;

ALTER TABLE UPLOAD_AUTOCERTIFICAZIONE ADD PRIMARY KEY (ID);
ALTER TABLE UPLOAD_AUTOCERTIFICAZIONE ADD CONSTRAINT UPLOAD_AUTOCERTIFICAZIONE_ESENZIONI_FASCE FOREIGN KEY (FK_ESENZIONI_FASCE) REFERENCES ESENZIONI_FASCE (ID) ON UPDATE NO ACTION ON DELETE NO ACTION;

CREATE UNIQUE INDEX PK_UPLOAD_AUTOCERTIFICAZIONE ON UPLOAD_AUTOCERTIFICAZIONE (ID);
CREATE UNIQUE INDEX FK_UA_ESENZIONI_FASCE ON UPLOAD_AUTOCERTIFICAZIONE (FK_ESENZIONI_FASCE);



CREATE TABLE RICEVUTA_DOWNLOAD_SOGEI(

    ID                      BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1  INCREMENT BY 1  NO CYCLE  NO CACHE),

	USER_ID                 VARCHAR (256),
	UTENTE_TOCKEN           VARCHAR (256),

    COD_REGIONE             CHAR (3),
    COD_ASL                 CHAR (3), --
    DATA_ORA_LIMITE         CHAR(12), --ddmmyyyyhhmm / yyyyMMddhhmm
    TIPO_AUTOCERTIFICAZIONE CHAR (1),
    NUMERO_RECORD_TROVATI   BIGINT,
    ESITO                   CHAR (4),
    DIAGNOSTICO             VARCHAR (512),

    DATA_INSERIMENTO        TIMESTAMP DEFAULT current timestamp NOT NULL,
    DATA_AGGIORNAMENTO      TIMESTAMP DEFAULT current timestamp NOT NULL

) IN TBS_ESENZIONIFA_N0 INDEX IN TBS_ESENZIONIFA_N0 COMPRESS YES;

ALTER TABLE RICEVUTA_DOWNLOAD_SOGEI ADD PRIMARY KEY (ID);

CREATE UNIQUE INDEX PK_RICEVUTA_DOWNLOAD_SOGEI ON RICEVUTA_DOWNLOAD_SOGEI (ID);




CREATE TABLE DOWNLOAD_AUTOCERTIFICAZIONE (

    ID                          BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1  INCREMENT BY 1 NO CYCLE  NO CACHE),

    FK_ESENZIONI_FASCE          BIGINT NOT NULL,
    --FK_AUTOCERTIFICAZIONE       BIGINT NOT NULL,
    ANNO_ESENZIONE              CHAR (4),
    FLAG_STATO                  VARCHAR (16),
    COD_ASL				        CHAR (6),
    DATA_AUTOCERTIFICAZIONE     DATE,

    FK_RICEVUTA_DOWNLOAD_SOGEI  BIGINT NOT NULL,

    DATA_INSERIMENTO        TIMESTAMP DEFAULT current timestamp NOT NULL,
    DATA_AGGIORNAMENTO      TIMESTAMP DEFAULT current timestamp NOT NULL


) IN TBS_ESENZIONIFA_N0 INDEX IN TBS_ESENZIONIFA_N0 COMPRESS YES;

ALTER TABLE DOWNLOAD_AUTOCERTIFICAZIONE ADD PRIMARY KEY (ID);
ALTER TABLE DOWNLOAD_AUTOCERTIFICAZIONE ADD CONSTRAINT DOWNLOAD_AUTOCERTIFICAZIONE_ESENZIONI_FASCE FOREIGN KEY (FK_ESENZIONI_FASCE) REFERENCES ESENZIONI_FASCE (ID) ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE DOWNLOAD_AUTOCERTIFICAZIONE ADD CONSTRAINT DOWNLOAD_AUTOCERTIFICAZIONE_RICEVUTA_DOWNLOAD_SOGEI FOREIGN KEY (FK_RICEVUTA_DOWNLOAD_SOGEI) REFERENCES RICEVUTA_DOWNLOAD_SOGEI (ID) ON UPDATE NO ACTION ON DELETE NO ACTION;

CREATE UNIQUE INDEX PK_DOWNLOAD_AUTOCERTIFICAZIONE ON DOWNLOAD_AUTOCERTIFICAZIONE (ID);
CREATE UNIQUE INDEX FK_DA_ESENZIONI_FASCE ON DOWNLOAD_AUTOCERTIFICAZIONE (FK_ESENZIONI_FASCE);
CREATE INDEX FK_DA_RICEVUTA_DOWNLOAD_SOGEI ON DOWNLOAD_AUTOCERTIFICAZIONE (FK_RICEVUTA_DOWNLOAD_SOGEI);



CREATE VIEW ESENZIONIFASCE.RCT_COMUNI AS
(
   SELECT
   COD_COMUNE CONCAT '-' CONCAT char(DATA_INIZIO) CONCAT '-' CONCAT char(DATA_INIZIO_RCT) as PK,
   COD_COMUNE,
   DATA_INIZIO,
   DATA_FINE,
   DESCRIZIONE,
   COD_PROVINCIA,
   COD_REGIONE,
   COD_ZONA,
   DATA_INIZIO_RCT,
   DATA_FINE_RCT,
   ULTIMO_AGGIORNAMENTO
   FROM RCT.COMUNI
   WHERE DATA_FINE_RCT = '9999-12-31'
   AND DATA_ANNULLAMENTO= '0001-01-01 00:00:00.000'
);

SET SCHEMA ESENZIONIFASCE;

-- NUOVA TABELLA CERTIFICAZIONE_TMP
CREATE TABLE CERTIFICAZIONE_TMP
(
   ID BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1  INCREMENT BY 1 NO CYCLE  NO CACHE),
   FK_RICEVUTA_DOWNLOAD_SOGEI BIGINT NOT NULL,
   RICEVUTA_INDEX BIGINT NOT NULL,
   NUMERO_ELABORAZIONI BIGINT DEFAULT 0 NOT NULL,
   DATA_ORDINAMENTO TIMESTAMP,
   ANNO_ESENZIONE CHAR(4),
   CODICE_ESENZIONE CHAR(3),
   DATA_INIZIO_VALID DATE,
   DATA_FINE_VALID DATE,
   DATA_FINE_VALID_OLD DATE,
   CF_SOG_ESENTE CHAR(16),
   DATA_INSERIMENTO timestamp DEFAULT CURRENT TIMESTAMP NOT NULL,
   DATA_AGGIORNAMENTO timestamp DEFAULT CURRENT TIMESTAMP NOT NULL,
   SORGENTE varchar(10)
)  IN TBS_ESENZIONIFA_N0 INDEX IN TBS_ESENZIONIFA_N0 COMPRESS YES;

ALTER TABLE CERTIFICAZIONE_TMP ADD PRIMARY KEY (ID);

CREATE INDEX FK_AT_RICEVUTA_DOWNLOAD_CERT_SOGEI ON CERTIFICAZIONE_TMP (FK_RICEVUTA_DOWNLOAD_SOGEI );

-- NUOVA TABELLA CERTIFICAZIONE_SCARTATE
CREATE TABLE CERTIFICAZIONE_SCARTATE
(
   ID BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1  INCREMENT BY 1 NO CYCLE  NO CACHE),

   OLD_ID BIGINT NOT NULL,

   FK_RICEVUTA_DOWNLOAD_SOGEI BIGINT NOT NULL,
   RICEVUTA_INDEX BIGINT NOT NULL,
   NUMERO_ELABORAZIONI BIGINT DEFAULT 0 NOT NULL,
   DATA_ORDINAMENTO TIMESTAMP,
   ANNO_ESENZIONE CHAR(4),
   CODICE_ESENZIONE CHAR(3),
   DATA_INIZIO_VALID DATE,
   DATA_FINE_VALID DATE,
   DATA_FINE_VALID_OLD DATE,
   CF_SOG_ESENTE CHAR(16),
   DATA_INSERIMENTO timestamp DEFAULT CURRENT TIMESTAMP NOT NULL,
   DATA_AGGIORNAMENTO timestamp DEFAULT CURRENT TIMESTAMP NOT NULL,
   OLD_DATA_INSERIMENTO timestamp NOT NULL,
   OLD_DATA_AGGIORNAMENTO timestamp NOT NULL,
   SORGENTE varchar(10),
   RIELABORA bigint DEFAULT 0 NOT NULL
) IN TBS_ESENZIONIFA_N0 INDEX IN TBS_ESENZIONIFA_N0 COMPRESS YES;

ALTER TABLE CERTIFICAZIONE_SCARTATE ADD PRIMARY KEY (ID);


-- CERTIFICAZIONE_TMP 
GRANT INSERT, SELECT, UPDATE, DELETE, INDEX  ON TABLE CERTIFICAZIONE_TMP TO USER yneng;
GRANT INSERT, SELECT, UPDATE, DELETE, INDEX  ON TABLE CERTIFICAZIONE_TMP TO USER esefasce;
GRANT INSERT, SELECT, UPDATE, DELETE, INDEX  ON TABLE CERTIFICAZIONE_TMP TO USER afeng;

-- CERTIFICAZIONE_SCARTATE 
GRANT INSERT, SELECT, UPDATE, DELETE, INDEX  ON TABLE CERTIFICAZIONE_SCARTATE TO USER yneng;
GRANT INSERT, SELECT, UPDATE, DELETE, INDEX  ON TABLE CERTIFICAZIONE_SCARTATE TO USER esefasce;
GRANT INSERT, SELECT, UPDATE, DELETE, INDEX  ON TABLE CERTIFICAZIONE_SCARTATE TO USER afeng;


-- NUOVO RECORD IN RECUPERO_DOWNLOAD_SOGEI
INSERT INTO RECUPERO_DOWNLOAD_SOGEI (DATA_ORA_LIMITE_START,DATA_ORA_LIMITE_STOP,TIPO_AUTOCERTIFICAZIONE) values ('311299992359','010100010000', 'A');

-- DOWNLOAD CERTIFICAZIONE
CREATE TABLE DOWNLOAD_CERTIFICAZIONE (

    ID                          BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1  INCREMENT BY 1 NO CYCLE  NO CACHE),
    FK_ESENZIONI_FASCE          BIGINT NOT NULL,
    ANNO_ESENZIONE              CHAR (4),
    DATA_CERTIFICAZIONE     	DATE,
    FK_RICEVUTA_DOWNLOAD_SOGEI  BIGINT NOT NULL,
    DATA_INSERIMENTO        TIMESTAMP DEFAULT current timestamp NOT NULL,
    DATA_AGGIORNAMENTO      TIMESTAMP DEFAULT current timestamp NOT NULL
    
) IN TBS_WSODS_N0 INDEX IN TBS_WSODS_N0 COMPRESS YES;

ALTER TABLE DOWNLOAD_CERTIFICAZIONE ADD PRIMARY KEY (ID);
ALTER TABLE DOWNLOAD_CERTIFICAZIONE ADD CONSTRAINT DOWNLOAD_CERTIFICAZIONE_ESENZIONI_FASCE FOREIGN KEY (FK_ESENZIONI_FASCE) REFERENCES ESENZIONI_FASCE (ID) ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE DOWNLOAD_CERTIFICAZIONE ADD CONSTRAINT DOWNLOAD_CERTIFICAZIONE_RICEVUTA_DOWNLOAD_SOGEI FOREIGN KEY (FK_RICEVUTA_DOWNLOAD_SOGEI) REFERENCES RICEVUTA_DOWNLOAD_SOGEI (ID) ON UPDATE NO ACTION ON DELETE NO ACTION;

CREATE UNIQUE INDEX PK_DOWNLOAD_CERTIFICAZIONE ON DOWNLOAD_CERTIFICAZIONE (ID);
CREATE INDEX FK_DA_ESENZIONI_CERT_FASCE ON DOWNLOAD_CERTIFICAZIONE (FK_ESENZIONI_FASCE);
CREATE INDEX FK_DA_RICEVUTA_DOWNLOAD_CERT_SOGEI ON DOWNLOAD_CERTIFICAZIONE (FK_RICEVUTA_DOWNLOAD_SOGEI);

																
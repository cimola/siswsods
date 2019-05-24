
SET SCHEMA ESENZIONIFASCE;
--SET SCHEMA ESENZIONIFASCE_LOCAL;  -- dati prod
--SET SCHEMA ESENZIONIFASCE_LOCAL2; -- primi test da locale 

--DROP TABLE DOWNLOAD_AUTOCERTIFICAZIONE;
--DROP TABLE RICEVUTA_DOWNLOAD_SOGEI;
--DROP TABLE UPLOAD_AUTOCERTIFICAZIONE;
--DROP TABLE ESENZIONI_FASCE;

--DROP TABLE AUTOCERTIFICAZIONE_TMP;
--DROP TABLE AUTOCERTIFICAZIONE_SCARTATE;

--DROP TABLE WSODS_SOAP_TRACKING;
--DROP TABLE PROROGHE;

--DROP TABLE DECODIFICA_STATO_PROROGHE;
--DROP TABLE DECODIFICA_FLAG_VALUTAZIONE;
--DROP TABLE RECUPERO_DOWNLOAD_SOGEI;

-- DROP TABLE IDUNI_DA_AGGIORNARE ;
-- DROP TABLE TRACCIA_OBSOLESCENZA_ID_UNI ;
-- DROP TABLE DECODIFICA_STATO_PROROGHE;
 

CREATE TABLE ESENZIONI_FASCE
(
   ID BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1  INCREMENT BY 1  NO CYCLE  NO CACHE),
   TIPO_ESENZIONE varchar(3),
   ID_UNIVERSALE_ASSISTITO varchar(24) DEFAULT '' NOT NULL,
   DATA_INIZIO date,
   DATA_FINE date,
   DATA_FORNITURA date,
   DATA_INIZIO_EROGAZIONE date,
   DATA_FINE_EROGAZIONE date,
   DATA_NASCITA date,
   SESSO varchar(1),
   FLAG_TIPOLOGIA varchar(1)
) IN TBS_WSODS_N0 INDEX IN TBS_WSODS_N0 COMPRESS YES;
ALTER TABLE ESENZIONI_FASCE ADD PRIMARY KEY (ID);

CREATE INDEX IDX_ESE002 ON ESENZIONI_FASCE ( ID_UNIVERSALE_ASSISTITO, DATA_INIZIO, DATA_FINE, FLAG_TIPOLOGIA );
CREATE INDEX INDEX_CHIAVE_APPLICATIVA_0 ON ESENZIONI_FASCE ( ID_UNIVERSALE_ASSISTITO, DATA_INIZIO, DATA_FINE, DATA_INIZIO_EROGAZIONE, DATA_FINE_EROGAZIONE, TIPO_ESENZIONE, FLAG_TIPOLOGIA );
CREATE UNIQUE INDEX PK_ESENZIONI_FASCE ON ESENZIONI_FASCE(ID);



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


) IN TBS_WSODS_N0 INDEX IN TBS_WSODS_N0 COMPRESS YES;

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

) IN TBS_WSODS_N0 INDEX IN TBS_WSODS_N0 COMPRESS YES;

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


) IN TBS_WSODS_N0 INDEX IN TBS_WSODS_N0 COMPRESS YES;

ALTER TABLE DOWNLOAD_AUTOCERTIFICAZIONE ADD PRIMARY KEY (ID);
ALTER TABLE DOWNLOAD_AUTOCERTIFICAZIONE ADD CONSTRAINT DOWNLOAD_AUTOCERTIFICAZIONE_ESENZIONI_FASCE FOREIGN KEY (FK_ESENZIONI_FASCE) REFERENCES ESENZIONI_FASCE (ID) ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE DOWNLOAD_AUTOCERTIFICAZIONE ADD CONSTRAINT DOWNLOAD_AUTOCERTIFICAZIONE_RICEVUTA_DOWNLOAD_SOGEI FOREIGN KEY (FK_RICEVUTA_DOWNLOAD_SOGEI) REFERENCES RICEVUTA_DOWNLOAD_SOGEI (ID) ON UPDATE NO ACTION ON DELETE NO ACTION;

CREATE UNIQUE INDEX PK_DOWNLOAD_AUTOCERTIFICAZIONE ON DOWNLOAD_AUTOCERTIFICAZIONE (ID);
CREATE UNIQUE INDEX FK_DA_ESENZIONI_FASCE ON DOWNLOAD_AUTOCERTIFICAZIONE (FK_ESENZIONI_FASCE);
CREATE INDEX FK_DA_RICEVUTA_DOWNLOAD_SOGEI ON DOWNLOAD_AUTOCERTIFICAZIONE (FK_RICEVUTA_DOWNLOAD_SOGEI);




ALTER TABLE RICEVUTA_DOWNLOAD_SOGEI ADD NUMERO_RECORD_ELABORATI   BIGINT DEFAULT 0 NOT NULL ;
ALTER TABLE RICEVUTA_DOWNLOAD_SOGEI ADD NUMERO_RECORD_NUOVI       BIGINT DEFAULT 0 NOT NULL ;


CREATE TABLE AUTOCERTIFICAZIONE_TMP
(
   ID BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1  INCREMENT BY 1 NO CYCLE  NO CACHE),
   FK_RICEVUTA_DOWNLOAD_SOGEI BIGINT NOT NULL,
   RICEVUTA_INDEX BIGINT NOT NULL,
   NUMERO_ELABORAZIONI BIGINT DEFAULT 0 NOT NULL,
   DATA_ORDINAMENTO TIMESTAMP,

   ANNO_ESENZIONE CHAR(4),
   CODICE_ESENZIONE CHAR(3),
   FLAG_STATO CHAR(1),
   COD_ASL CHAR(3),
   DATA_AUTOCERTIFICAZIONE DATE,
   PROTOCOLLO VARCHAR(18),
   NOTE VARCHAR(512),
   DATA_INIZIO_VALID DATE,
   DATA_FINE_VALID DATE,
   DATA_FINE_VALID_OLD DATE,
   TITOLO CHAR(1),
   TIPO_AUTOCERTIFICAZIONE CHAR(1),

   CF_DICHIARANTE CHAR(16),
   COGNOME_DICHIARANTE VARCHAR(256),
   NOME_DICHIARANTE VARCHAR(256),
   DATA_NASCITA_DICHIARANTE DATE,
   SESSO_DICHIARANTE CHAR(1),
   COMUNE_NASCITA_DICHIARANTE VARCHAR(256),

   CF_TITOLARE CHAR(16),
   COGNOME_TITOLARE VARCHAR(256),
   NOME_TITOLARE VARCHAR(256),
   DATA_NASCITA_TITOLARE DATE,
   SESSO_TITOLARE CHAR(1),
   COMUNE_NASCITA_TITOLARE VARCHAR(256),

   CF_BENEFICIARIO CHAR(16),
   COGNOME_BENEFICIARIO VARCHAR(256),
   NOME_BENEFICIARIO VARCHAR(256),
   DATA_NASCITA_BENEFICIARIO DATE,
   SESSO_BENEFICIARIO CHAR(1),
   COMUNE_NASCITA_BENEFICIARIO VARCHAR(256),

   DATA_INSERIMENTO TIMESTAMP DEFAULT CURRENT TIMESTAMP NOT NULL,
   DATA_AGGIORNAMENTO TIMESTAMP DEFAULT CURRENT TIMESTAMP NOT NULL
)  IN TBS_WSODS_N0 INDEX IN TBS_WSODS_N0 COMPRESS YES;

ALTER TABLE AUTOCERTIFICAZIONE_TMP ADD PRIMARY KEY (ID);

CREATE INDEX FK_AT_RICEVUTA_DOWNLOAD_SOGEI ON AUTOCERTIFICAZIONE_TMP (FK_RICEVUTA_DOWNLOAD_SOGEI );

CREATE TABLE AUTOCERTIFICAZIONE_SCARTATE
(
   ID BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1  INCREMENT BY 1 NO CYCLE  NO CACHE),

   OLD_ID BIGINT NOT NULL,

   FK_RICEVUTA_DOWNLOAD_SOGEI BIGINT NOT NULL,
   RICEVUTA_INDEX BIGINT NOT NULL,
   NUMERO_ELABORAZIONI BIGINT DEFAULT 0 NOT NULL,
   DATA_ORDINAMENTO TIMESTAMP,

   ANNO_ESENZIONE CHAR(4),
   CODICE_ESENZIONE CHAR(3),
   FLAG_STATO CHAR(1),
   COD_ASL CHAR(3),
   DATA_AUTOCERTIFICAZIONE DATE,
   PROTOCOLLO VARCHAR(18),
   NOTE VARCHAR(512),
   DATA_INIZIO_VALID DATE,
   DATA_FINE_VALID DATE,
   DATA_FINE_VALID_OLD DATE,
   TITOLO CHAR(1),
   TIPO_AUTOCERTIFICAZIONE CHAR(1),

   CF_DICHIARANTE CHAR(16),
   COGNOME_DICHIARANTE VARCHAR(256),
   NOME_DICHIARANTE VARCHAR(256),
   DATA_NASCITA_DICHIARANTE DATE,
   SESSO_DICHIARANTE CHAR(1),
   COMUNE_NASCITA_DICHIARANTE VARCHAR(256),

   CF_TITOLARE CHAR(16),
   COGNOME_TITOLARE VARCHAR(256),
   NOME_TITOLARE VARCHAR(256),
   DATA_NASCITA_TITOLARE DATE,
   SESSO_TITOLARE CHAR(1),
   COMUNE_NASCITA_TITOLARE VARCHAR(256),

   CF_BENEFICIARIO CHAR(16),
   COGNOME_BENEFICIARIO VARCHAR(256),
   NOME_BENEFICIARIO VARCHAR(256),
   DATA_NASCITA_BENEFICIARIO DATE,
   SESSO_BENEFICIARIO CHAR(1),
   COMUNE_NASCITA_BENEFICIARIO VARCHAR(256),

   OLD_DATA_INSERIMENTO TIMESTAMP NOT NULL,
   OLD_DATA_AGGIORNAMENTO TIMESTAMP NOT NULL,

   DATA_INSERIMENTO TIMESTAMP DEFAULT CURRENT TIMESTAMP NOT NULL,
   DATA_AGGIORNAMENTO TIMESTAMP DEFAULT CURRENT TIMESTAMP NOT NULL

) IN TBS_WSODS_N0 INDEX IN TBS_WSODS_N0 COMPRESS YES;

ALTER TABLE AUTOCERTIFICAZIONE_SCARTATE ADD PRIMARY KEY (ID);



ALTER TABLE ESENZIONI_FASCE ADD SORGENTE VARCHAR (10) DEFAULT 'ALTRO' NOT NULL;
ALTER TABLE AUTOCERTIFICAZIONE_SCARTATE ADD SORGENTE VARCHAR (10);
ALTER TABLE AUTOCERTIFICAZIONE_TMP ADD SORGENTE VARCHAR (10);

CREATE TABLE WSODS_SOAP_TRACKING (

    ID					BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1  INCREMENT BY 1  NO CYCLE  NO CACHE),

   	SRC					VARCHAR (26) NOT NULL, -- WSODS / APS_ESE / TOTEM / AUTOCERTIFICAZIONI-REDDITO
    DATA_REQUEST		TIMESTAMP,
    REQUEST				CLOB (536870912), -- 5 MB
    
    DEST				VARCHAR (7) NOT NULL, -- SOGEI / APS_ESE 
    DATA_RESPONSE		TIMESTAMP,
    RESPONSE			CLOB (1073741824), -- 10 MB
    
	ESITO				CHAR (2),	-- OK oppure KO
	STACK_TRACE			CLOB (2097152), -- 2 MB
	
	FK_LINK				BIGINT,
    
    DATA_INSERIMENTO	TIMESTAMP DEFAULT current timestamp NOT NULL,
    DATA_AGGIORNAMENTO	TIMESTAMP DEFAULT current timestamp NOT NULL


) IN TBS_WSODS_N0 INDEX IN TBS_WSODS_N0 COMPRESS YES;



-- PROROGHE 


CREATE TABLE PROROGHE  (
		  ID BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH +1 INCREMENT BY +1 MINVALUE +1  MAXVALUE +9223372036854775807 NO CYCLE ) , 
		  ESENZIONI_FASCE_ID BIGINT NOT NULL , 
		  DATA_FINE_PROROGA DATE , 
		  ANNO_PROROGA CHAR(4) 
) IN TBS_WSODS_N0 INDEX IN TBS_WSODS_N0 COMPRESS YES;


ALTER TABLE PROROGHE  ADD PRIMARY KEY (ID);

ALTER TABLE PROROGHE ADD DATA_INIZIO_PROROGA 	DATE;
ALTER TABLE PROROGHE ADD ATTIVITA				CHAR(6) ; -- APRI/CHIUDI/ALTRO
ALTER TABLE PROROGHE ADD STATO					BIGINT DEFAULT 0 NOT NULL ;
ALTER TABLE PROROGHE ADD NUMERO_ELABORAZIONI	BIGINT DEFAULT 0 NOT NULL ;
ALTER TABLE PROROGHE ADD ESITO					CHAR(2);
ALTER TABLE PROROGHE ADD CODICE_ESITO			VARCHAR(4);
ALTER TABLE PROROGHE ADD DESCRIZIONE_ESITO		VARCHAR(512);
ALTER TABLE PROROGHE ADD FK_SOAP_TRACKING		BIGINT ;
ALTER TABLE PROROGHE ADD DATA_INSERIMENTO       TIMESTAMP DEFAULT current timestamp NOT NULL;
ALTER TABLE PROROGHE ADD DATA_AGGIORNAMENTO     TIMESTAMP DEFAULT current timestamp NOT NULL;


-- AUTOCERTIFICAZIONE_SCARTATE 
ALTER TABLE AUTOCERTIFICAZIONE_SCARTATE ADD RIELABORA	BIGINT DEFAULT 0 NOT NULL ;



-- RICEVUTA_DOWNLOAD_SOGEI 
ALTER TABLE RICEVUTA_DOWNLOAD_SOGEI ADD XML_DOCUMENT 		CLOB (1073741824); -- 10 MB
ALTER TABLE RICEVUTA_DOWNLOAD_SOGEI ADD STATO_ELABORAZIONE	BIGINT DEFAULT 2 NOT NULL ;
ALTER TABLE RICEVUTA_DOWNLOAD_SOGEI ADD PROTOCOLLO			VARCHAR(18);




-- PROROGHE 
ALTER TABLE PROROGHE ADD TIPO_ESENZIONE							VARCHAR(3);
ALTER TABLE PROROGHE ADD ID_UNIVERSALE_ASSISTITO				VARCHAR(24);
ALTER TABLE PROROGHE ADD DATA_INIZIO_ESENZIONE_DA_PROREGARE 	DATE;
ALTER TABLE PROROGHE ADD DATA_FINE_ESENZIONE_DA_PROREGARE 		DATE;


-- DECODIFICA_STATO_PROROGHE 
CREATE TABLE DECODIFICA_STATO_PROROGHE  (
		  ID BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH +1 INCREMENT BY +1 MINVALUE +1  MAXVALUE +9223372036854775807 NO CYCLE ) , 
		  CODE BIGINT NOT NULL , 
		  DESCRIZIONE VARCHAR(32)
) IN TBS_WSODS_N0 INDEX IN TBS_WSODS_N0 COMPRESS YES;

ALTER TABLE DECODIFICA_STATO_PROROGHE  ADD PRIMARY KEY (ID);

INSERT INTO DECODIFICA_STATO_PROROGHE (CODE,DESCRIZIONE) values (0,'NESSUN_OPERAZIONE');
INSERT INTO DECODIFICA_STATO_PROROGHE (CODE,DESCRIZIONE) values (10,'PRONTO_PER_LA_PROPAGAZIONE');
INSERT INTO DECODIFICA_STATO_PROROGHE (CODE,DESCRIZIONE) values (50,'IN_PROPAGAZIONE');
INSERT INTO DECODIFICA_STATO_PROROGHE (CODE,DESCRIZIONE) values (100,'PROPAGATO');
INSERT INTO DECODIFICA_STATO_PROROGHE (CODE,DESCRIZIONE) values (999,'IN_ERRORE');

-- ESENZIONI_FASCE
ALTER TABLE ESENZIONI_FASCE ADD DATA_ULTIMA_VALUTAZIONE TIMESTAMP ;
ALTER TABLE ESENZIONI_FASCE ADD DATA_PROSSIMA_VALUTAZIONE DATE;
ALTER TABLE ESENZIONI_FASCE ADD FLAG_VALUTAZIONE integer NOT NULL DEFAULT 0 ;
	-- 0 mai valutato
	-- 1 storico in valutazione
	-- 2 valutato
	-- 3-8 altri impieghi
	-- 9 invalutabile
	
-- PROROGHE
ALTER TABLE PROROGHE ADD TIPO_ESENZIONE_DA_PROROGARE VARCHAR(3) ;
ALTER TABLE PROROGHE ADD MODALITA_PROROGAZIONE VARCHAR(256) ;
--RETTIFICATORE
--OPERATORE



-- PROROGHE
ALTER TABLE PROROGHE DROP TIPO_ESENZIONE_DA_PROROGARE;
ALTER TABLE PROROGHE ADD TIPO_ESENZIONE_GENERATA VARCHAR(3);
ALTER TABLE PROROGHE ADD ORDINE_DI_ESECUZIONE integer NOT NULL DEFAULT 0 ;


-- CREATE INDEX ON ESENZIONI_FASCE
CREATE INDEX IDX_ESE_FAS_RETT01 ON ESENZIONI_FASCE ( FLAG_TIPOLOGIA, TIPO_ESENZIONE, ID_UNIVERSALE_ASSISTITO ); -- RecuperoCERD e EsenzioniPerTipologia
CREATE INDEX IDX_ESE_FAS_RETT02 ON ESENZIONI_FASCE ( FLAG_TIPOLOGIA, TIPO_ESENZIONE, ID_UNIVERSALE_ASSISTITO, DATA_INIZIO ); --  storico Autocertificazione
CREATE INDEX IDX_ESE_FAS_RETT03 ON ESENZIONI_FASCE ( FLAG_TIPOLOGIA, TIPO_ESENZIONE, ID_UNIVERSALE_ASSISTITO, DATA_INIZIO, DATA_ORDINAMENTO, DATA_FORNITURA ); -- esiste Un esenzioneSuccessiva
CREATE INDEX IDX_ESE_FAS_RETT04 ON ESENZIONI_FASCE ( FLAG_TIPOLOGIA, TIPO_ESENZIONE, DATA_FINE, FLAG_VALUTAZIONE ); --  Esenzioni Da Valutare
CREATE INDEX IDX_ESE_FAS_RETT05 ON ESENZIONI_FASCE ( FLAG_TIPOLOGIA, TIPO_ESENZIONE, DATA_PROSSIMA_VALUTAZIONE ); --  Esenzioni Da Rettificare
CREATE INDEX IDX_ESE_FAS_RETT06 ON ESENZIONI_FASCE ( DATA_AGGIORNAMENTO); -- Ordinamento

-- CREATE INDEX ON PROROGHE
CREATE INDEX IDX_PROROGHE_RETT01 ON PROROGHE (STATO, ESENZIONI_FASCE_ID );  -- recupero proroghe generate dal rettificatore, pronte ad essere inviate 
CREATE INDEX IDX_PROROGHE_RETT02 ON PROROGHE ( DATA_AGGIORNAMENTO); -- Ordinamento




INSERT INTO DECODIFICA_STATO_PROROGHE (CODE,DESCRIZIONE) values (5,'INSERITE_PER_LA_RETTIFICA');
INSERT INTO DECODIFICA_STATO_PROROGHE (CODE,DESCRIZIONE) values (200,'SCARTATE_PER_LA_RETTIFICA');

-- DECODIFICA_STATO_PROROGHE 
CREATE TABLE DECODIFICA_FLAG_VALUTAZIONE  (
		  ID BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH +1 INCREMENT BY +1 MINVALUE +1  MAXVALUE +9223372036854775807 NO CYCLE ) , 
		  CODE BIGINT,
		  DESCRIZIONE VARCHAR(512)
) IN TBS_WSODS_N0 INDEX IN TBS_WSODS_N0 COMPRESS YES;


INSERT INTO DECODIFICA_FLAG_VALUTAZIONE (CODE,DESCRIZIONE) values (0,'DA_VALUTARE');
INSERT INTO DECODIFICA_FLAG_VALUTAZIONE (CODE,DESCRIZIONE) values (1,'VALUTATO_UN_ELEMENTO_DELLA_SUA_STORIA');
INSERT INTO DECODIFICA_FLAG_VALUTAZIONE (CODE,DESCRIZIONE) values (2,'VALUTATO_E_DA_RETTIFICARE_IN_SEGUITO');
INSERT INTO DECODIFICA_FLAG_VALUTAZIONE (CODE,DESCRIZIONE) values (3,'VALUTATO_E_DA_RETTIFICARE_INMEDIATAMENTE');
INSERT INTO DECODIFICA_FLAG_VALUTAZIONE (CODE,DESCRIZIONE) values (4,'RIVALUTATO');
INSERT INTO DECODIFICA_FLAG_VALUTAZIONE (CODE,DESCRIZIONE) values (9,'DA_NON_VALUTARE');



-- PROROGHE
ALTER TABLE PROROGHE ADD PROTOCOLLO_ESENZIONE_DA_PROROGARE VARCHAR(18);

-- definisce se la ricevuta e' dovuta ad un download relativo alla linea di scaricamento corrente
-- utile a mantenere allineato il sistema al momento corrente oppute se e' una linea di recupero.
-- valori amessi : CORRENTE/RECUPERO
ALTER TABLE RICEVUTA_DOWNLOAD_SOGEI ADD TIPOLOGIA_RICEVUTA	VARCHAR(32) DEFAULT 'CORRENTE' NOT NULL;



-- RECUPERO_DOWNLOAD_SOGEI 
CREATE TABLE RECUPERO_DOWNLOAD_SOGEI  (
	ID 						BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH +1 INCREMENT BY +1 MINVALUE +1  MAXVALUE +9223372036854775807 NO CYCLE ) , 
	DATA_ORA_LIMITE_START   CHAR(12) NOT NULL, --ddmmyyyyhhmm / yyyyMMddhhmm
	DATA_ORA_LIMITE_STOP 	CHAR(12) NOT NULL, --ddmmyyyyhhmm / yyyyMMddhhmm
	TIPO_AUTOCERTIFICAZIONE CHAR (1) NOT NULL,
	
	DATA_INSERIMENTO        TIMESTAMP DEFAULT current timestamp NOT NULL,
	DATA_AGGIORNAMENTO      TIMESTAMP DEFAULT current timestamp NOT NULL
) IN TBS_WSODS_N0 INDEX IN TBS_WSODS_N0 COMPRESS YES;


INSERT INTO RECUPERO_DOWNLOAD_SOGEI (DATA_ORA_LIMITE_START,DATA_ORA_LIMITE_STOP,TIPO_AUTOCERTIFICAZIONE) values ('311299992359','010100010000', 'I');
INSERT INTO RECUPERO_DOWNLOAD_SOGEI (DATA_ORA_LIMITE_START,DATA_ORA_LIMITE_STOP,TIPO_AUTOCERTIFICAZIONE) values ('311299992359','010100010000', 'V');
INSERT INTO RECUPERO_DOWNLOAD_SOGEI (DATA_ORA_LIMITE_START,DATA_ORA_LIMITE_STOP,TIPO_AUTOCERTIFICAZIONE) values ('31/12/9998','31/12/9999', 'A');





-- DROP TABLE IDUNI_DA_AGGIORNARE 
CREATE TABLE IDUNI_DA_AGGIORNARE  (
	ID 							BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH +1 INCREMENT BY +1 MINVALUE +1  MAXVALUE +9223372036854775807 NO CYCLE ) , 

	DATA_ULTIMO_AGGIORNAMENTO	TIMESTAMP NOT NULL,
	SLAVE						VARCHAR(24) NOT NULL,
	MASTER						VARCHAR(24) NOT NULL,
	STATO_UTILIZZO				BIGINT NOT NULL, 
	
	DATA_INSERIMENTO        	TIMESTAMP DEFAULT current timestamp NOT NULL,
	DATA_AGGIORNAMENTO      	TIMESTAMP DEFAULT current timestamp NOT NULL
) IN TBS_WSODS_N0 INDEX IN TBS_WSODS_N0 COMPRESS YES;

ALTER TABLE IDUNI_DA_AGGIORNARE ADD PRIMARY KEY (ID);

-- DROP TABLE TRACCIA_OBSOLESCENZA_ID_UNI 
CREATE TABLE TRACCIA_OBSOLESCENZA_ID_UNI  (
	ID 						BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH +1 INCREMENT BY +1 MINVALUE +1  MAXVALUE +9223372036854775807 NO CYCLE ) , 

	FK_IDUNI_DA_AGGIORNARE  BIGINT NOT NULL,
	FK_ESENZIONI_FASCE      BIGINT NOT NULL,
	RUOLO					VARCHAR(64) NOT NULL, -- DICHIARANTE/TITOLARE/ASSISTITO 
	
	DATA_INSERIMENTO        TIMESTAMP DEFAULT current timestamp NOT NULL,
	DATA_AGGIORNAMENTO      TIMESTAMP DEFAULT current timestamp NOT NULL
) IN TBS_WSODS_N0 INDEX IN TBS_WSODS_N0 COMPRESS YES;

ALTER TABLE TRACCIA_OBSOLESCENZA_ID_UNI ADD PRIMARY KEY (ID);

ALTER TABLE TRACCIA_OBSOLESCENZA_ID_UNI ADD CONSTRAINT TRACCIA_OBSOLESCENZA_ID_UNI_IDUNI_DA_AGGIORNARE FOREIGN KEY (FK_IDUNI_DA_AGGIORNARE) REFERENCES IDUNI_DA_AGGIORNARE (ID) ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE TRACCIA_OBSOLESCENZA_ID_UNI ADD CONSTRAINT TRACCIA_OBSOLESCENZA_ID_UNI_ESENZIONI_FASCE FOREIGN KEY (FK_ESENZIONI_FASCE) REFERENCES ESENZIONI_FASCE (ID) ON UPDATE NO ACTION ON DELETE NO ACTION;


-- DROP TABLE DECODIFICA_STATO_PROROGHE 
CREATE TABLE DECODIFICA_STATO_UTILIZZO  (
		  ID BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH +1 INCREMENT BY +1 MINVALUE +1  MAXVALUE +9223372036854775807 NO CYCLE ) , 
		  CODE BIGINT,
		  DESCRIZIONE VARCHAR(512)
) IN TBS_WSODS_N0 INDEX IN TBS_WSODS_N0 COMPRESS YES;

ALTER TABLE DECODIFICA_STATO_UTILIZZO ADD PRIMARY KEY (ID);

INSERT INTO DECODIFICA_STATO_UTILIZZO (CODE,DESCRIZIONE) values (0,'MAI_USATO');
INSERT INTO DECODIFICA_STATO_UTILIZZO (CODE,DESCRIZIONE) values (1,'RICERCATO_E_TROVATO');
INSERT INTO DECODIFICA_STATO_UTILIZZO (CODE,DESCRIZIONE) values (2,'RICERCATO_MA_NON_USATO');

ALTER TABLE ESENZIONIFASCE.ESENZIONI_FASCE ADD COLUMN AVVIATRG CHAR(1) DEFAULT 'A' NOT NULL;

REORG TABLE ESENZIONIFASCE.ESENZIONI_FASCE;








SET SCHEMA ESENZIONIFASCE;


DROP TABLE AUTOCERTIFICAZIONE_APERTURE_ETL;
DROP TABLE AUTOCERTIFICAZIONE_CHIUSURE_ETL;
DROP TABLE ESITO_INVIO_APERTURE_ETL;
DROP TABLE ESITO_INVIO_CHIUSURE_ETL;

CREATE TABLE AUTOCERTIFICAZIONE_APERTURE_ETL
(
   ID								BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1  INCREMENT BY 1 NO CYCLE  NO CACHE),
   FK_ESENZIONE_PROROGATA			BIGINT NOT NULL,
   STATO_ELABORAZIONE				BIGINT DEFAULT 0 NOT NULL,
   
   CODICE_ESENZIONE					CHAR(3) NOT NULL,
   ID_UNI_BENEFICIARIO				CHAR(24) NOT NULL,
   ID_UNI_DICHIARANTE				CHAR(24) NOT NULL,
   ID_UNI_TITOLARE					CHAR(24) NOT NULL,
   TITOLO							CHAR(1) NOT NULL,
   
   DATA_FINE						DATE NOT NULL,

   DATA_INVIO						DATE,
   NUMERO_ELABORAZIONI				BIGINT DEFAULT 0 NOT NULL,
   
   DATA_INSERIMENTO					TIMESTAMP DEFAULT CURRENT TIMESTAMP NOT NULL,
   DATA_AGGIORNAMENTO				TIMESTAMP DEFAULT CURRENT TIMESTAMP NOT NULL
)  IN TBS_WSODS_N0 INDEX IN TBS_WSODS_N0 COMPRESS YES;

ALTER TABLE AUTOCERTIFICAZIONE_APERTURE_ETL ADD PRIMARY KEY (ID);

CREATE INDEX NDX_AUTOCERTIFICAZIONE_APERTURE_ETL_000 ON AUTOCERTIFICAZIONE_APERTURE_ETL (ID,STATO_ELABORAZIONE,NUMERO_ELABORAZIONI );
CREATE INDEX NDX_AUTOCERTIFICAZIONE_APERTURE_ETL_001 ON AUTOCERTIFICAZIONE_APERTURE_ETL (STATO_ELABORAZIONE,DATA_AGGIORNAMENTO );


CREATE TABLE AUTOCERTIFICAZIONE_CHIUSURE_ETL
(
   ID								BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1  INCREMENT BY 1 NO CYCLE  NO CACHE),
   FK_ESENZIONE_PROROGATA			BIGINT NOT NULL,
   STATO_ELABORAZIONE				BIGINT DEFAULT 0 NOT NULL,
   
   CODICE_ESENZIONE					CHAR(3) NOT NULL,
   ID_UNI_BENEFICIARIO				CHAR(24) NOT NULL,
   ID_UNI_DICHIARANTE				CHAR(24) NOT NULL,
   ID_UNI_TITOLARE					CHAR(24) NOT NULL,
   TITOLO							CHAR(1) NOT NULL,
   
   PROTOCOLLO						VARCHAR(18) NOT NULL,
   DATA_INIZIO						DATE NOT NULL,
   DATA_FINE_OLD					DATE NOT NULL,
   

   DATA_INVIO						DATE ,
   NUMERO_ELABORAZIONI				BIGINT DEFAULT 0 NOT NULL,
   
   DATA_INSERIMENTO					TIMESTAMP DEFAULT CURRENT TIMESTAMP NOT NULL,
   DATA_AGGIORNAMENTO				TIMESTAMP DEFAULT CURRENT TIMESTAMP NOT NULL
)  IN TBS_WSODS_N0 INDEX IN TBS_WSODS_N0 COMPRESS YES;

ALTER TABLE AUTOCERTIFICAZIONE_CHIUSURE_ETL ADD PRIMARY KEY (ID);


CREATE INDEX NDX_AUTOCERTIFICAZIONE_CHIUSURE_ETL_000 ON AUTOCERTIFICAZIONE_CHIUSURE_ETL (ID,STATO_ELABORAZIONE,NUMERO_ELABORAZIONI );
CREATE INDEX NDX_AUTOCERTIFICAZIONE_CHIUSURE_ETL_001 ON AUTOCERTIFICAZIONE_CHIUSURE_ETL (STATO_ELABORAZIONE,DATA_AGGIORNAMENTO );

CREATE TABLE ESITO_INVIO_APERTURE_ETL
(
	ID									BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1  INCREMENT BY 1 NO CYCLE  NO CACHE),
	FK_AUTOCERTIFICAZIONE_ETL			BIGINT NOT NULL,
	FK_WSODS_SOAP_TRACKING				BIGINT NOT NULL, -- 0 se non c'e tracciatura
 
	ESITO								CHAR (2),	-- OK oppure KO
	ESITO_COD							VARCHAR (16),	-- codice esito
	ESITO_DESC							VARCHAR (512),	-- descrizione esito
	STACK_TRACE							CLOB (2097152), -- 2 MB
   
	DATA_INSERIMENTO					TIMESTAMP DEFAULT CURRENT TIMESTAMP NOT NULL,
	DATA_AGGIORNAMENTO					TIMESTAMP DEFAULT CURRENT TIMESTAMP NOT NULL
)  IN TBS_WSODS_N0 INDEX IN TBS_WSODS_N0 COMPRESS YES;

ALTER TABLE ESITO_INVIO_APERTURE_ETL ADD PRIMARY KEY (ID);


CREATE TABLE ESITO_INVIO_CHIUSURE_ETL
(
	ID									BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1  INCREMENT BY 1 NO CYCLE  NO CACHE),
	FK_AUTOCERTIFICAZIONE_ETL			BIGINT NOT NULL,
	FK_WSODS_SOAP_TRACKING				BIGINT NOT NULL, -- 0 se non c'e tracciatura
    
	ESITO								CHAR (2),	-- OK oppure KO
	ESITO_COD							VARCHAR (16),	-- codice esito
	ESITO_DESC							VARCHAR (512),	-- descrizione esito
	STACK_TRACE							CLOB (2097152), -- 2 MB
   
   DATA_INSERIMENTO						TIMESTAMP DEFAULT CURRENT TIMESTAMP NOT NULL,
   DATA_AGGIORNAMENTO					TIMESTAMP DEFAULT CURRENT TIMESTAMP NOT NULL
)  IN TBS_WSODS_N0 INDEX IN TBS_WSODS_N0 COMPRESS YES;

ALTER TABLE ESITO_INVIO_CHIUSURE_ETL ADD PRIMARY KEY (ID);



SET SCHEMA ESENZIONIFASCE;



CREATE INDEX NDX_ESENZIONI_FASCE_000 ON ESENZIONI_FASCE ( TITOLO, PROTOCOLLO );
CREATE INDEX NDX_ESENZIONI_FASCE_001 ON ESENZIONI_FASCE ( DATA_INIZIO );


CREATE INDEX NDX_AUTOCERTIFICAZIONE_SCARTATE_000 ON AUTOCERTIFICAZIONE_SCARTATE (ID,TITOLO,NUMERO_ELABORAZIONI,RIELABORA );
CREATE INDEX NDX_AUTOCERTIFICAZIONE_SCARTATE_001 ON AUTOCERTIFICAZIONE_SCARTATE (DATA_AGGIORNAMENTO );






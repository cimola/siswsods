--delete campi relativi alle tabelle coinvolte

delete from ESENZIONIFASCE.DIZIONARIO_CAMPI where NOME_TABELLA in ( 'AUTOCERTIFICAZIONE_APERTURE_ETL', 'AUTOCERTIFICAZIONE_CHIUSURE_ETL', 'ESITO_INVIO_APERTURE_ETL', 'ESITO_INVIO_CHIUSURE_ETL' );
-- delete tabelle coinvolte
delete from ESENZIONIFASCE.DIZIONARIO_TABELLE where NOME_TABELLA in ( 'AUTOCERTIFICAZIONE_APERTURE_ETL', 'AUTOCERTIFICAZIONE_CHIUSURE_ETL', 'ESITO_INVIO_APERTURE_ETL', 'ESITO_INVIO_CHIUSURE_ETL' );



-- insert tabele ciinvolte
INSERT INTO ESENZIONIFASCE.DIZIONARIO_TABELLE (NOME_TABELLA,DESCRIZIONE) VALUES ('AUTOCERTIFICAZIONE_APERTURE_ETL','Dati relativi ad una aperutra di Autocertificazioni generati da ETL per le proroghe');
INSERT INTO ESENZIONIFASCE.DIZIONARIO_TABELLE (NOME_TABELLA,DESCRIZIONE) VALUES ('AUTOCERTIFICAZIONE_CHIUSURE_ETL','Dati relativi ad una chiusura di Autocertificazioni generati da ETL per le proroghe');
INSERT INTO ESENZIONIFASCE.DIZIONARIO_TABELLE (NOME_TABELLA,DESCRIZIONE) VALUES ('ESITO_INVIO_APERTURE_ETL','esito storico degli invii generati dalla tabella AUTOCERTIFICAZIONE_APERTURE_ETL');
INSERT INTO ESENZIONIFASCE.DIZIONARIO_TABELLE (NOME_TABELLA,DESCRIZIONE) VALUES ('ESITO_INVIO_CHIUSURE_ETL','esito storico degli invii generati dalla tabella AUTOCERTIFICAZIONE_CHIUSURE_ETL');


-- insert campi coinvolti
INSERT INTO ESENZIONIFASCE.DIZIONARIO_CAMPI (NOME_TABELLA,NOME_CAMPO,DESCRIZIONE,PK,FK,TABELLA_RIF) VALUES ('AUTOCERTIFICAZIONE_APERTURE_ETL','ID',                    'identificativo univoco tabella',1,0,null);

INSERT INTO ESENZIONIFASCE.DIZIONARIO_CAMPI (NOME_TABELLA,NOME_CAMPO,DESCRIZIONE,PK,FK,TABELLA_RIF) VALUES ('AUTOCERTIFICAZIONE_APERTURE_ETL','FK_ESENZIONE_PROROGATA','chiave primaria della tabella ESENZIONI_FASCE relativa all''esenzione che ha generato l''autocertificazione di apertura.',0,0,null);
INSERT INTO ESENZIONIFASCE.DIZIONARIO_CAMPI (NOME_TABELLA,NOME_CAMPO,DESCRIZIONE,PK,FK,TABELLA_RIF) VALUES ('AUTOCERTIFICAZIONE_APERTURE_ETL','STATO_ELABORAZIONE',    'stato di elaborazione del record, 0 -- inserito e non ancora elaborato, 1 -- elaborato con esito positovo, 2 -- elaborato con esito negativo bloccante, 3 elaborato con esito negativo non bloccante',0,0,null);

INSERT INTO ESENZIONIFASCE.DIZIONARIO_CAMPI (NOME_TABELLA,NOME_CAMPO,DESCRIZIONE,PK,FK,TABELLA_RIF) VALUES ('AUTOCERTIFICAZIONE_APERTURE_ETL','CODICE_ESENZIONE',      'Codice dell''esenzione da generare',0,0,null);
INSERT INTO ESENZIONIFASCE.DIZIONARIO_CAMPI (NOME_TABELLA,NOME_CAMPO,DESCRIZIONE,PK,FK,TABELLA_RIF) VALUES ('AUTOCERTIFICAZIONE_APERTURE_ETL','ID_UNI_BENEFICIARIO',   'id universale del beneficiario dell''esenzione da generare',0,0,null);
INSERT INTO ESENZIONIFASCE.DIZIONARIO_CAMPI (NOME_TABELLA,NOME_CAMPO,DESCRIZIONE,PK,FK,TABELLA_RIF) VALUES ('AUTOCERTIFICAZIONE_APERTURE_ETL','ID_UNI_DICHIARANTE',    'id universale del dichiarante dell''esenzione da generare',0,0,null);
INSERT INTO ESENZIONIFASCE.DIZIONARIO_CAMPI (NOME_TABELLA,NOME_CAMPO,DESCRIZIONE,PK,FK,TABELLA_RIF) VALUES ('AUTOCERTIFICAZIONE_APERTURE_ETL','ID_UNI_TITOLARE',       'id universale del titolare dell''esenzione da generare',0,0,null);
INSERT INTO ESENZIONIFASCE.DIZIONARIO_CAMPI (NOME_TABELLA,NOME_CAMPO,DESCRIZIONE,PK,FK,TABELLA_RIF) VALUES ('AUTOCERTIFICAZIONE_APERTURE_ETL','TITOLO',                'titolo tra i soggetti dell''esenzione generata',0,0,null);

INSERT INTO ESENZIONIFASCE.DIZIONARIO_CAMPI (NOME_TABELLA,NOME_CAMPO,DESCRIZIONE,PK,FK,TABELLA_RIF) VALUES ('AUTOCERTIFICAZIONE_APERTURE_ETL','DATA_FINE',             'data fine dell''esenzione generata',0,0,null);

INSERT INTO ESENZIONIFASCE.DIZIONARIO_CAMPI (NOME_TABELLA,NOME_CAMPO,DESCRIZIONE,PK,FK,TABELLA_RIF) VALUES ('AUTOCERTIFICAZIONE_APERTURE_ETL','DATA_INVIO',            'data dell''ultimo invio effettuato, e quindi data inizio settata sull''esenzione inviata',0,0,null);
INSERT INTO ESENZIONIFASCE.DIZIONARIO_CAMPI (NOME_TABELLA,NOME_CAMPO,DESCRIZIONE,PK,FK,TABELLA_RIF) VALUES ('AUTOCERTIFICAZIONE_APERTURE_ETL','NUMERO_ELABORAZIONI',   'numero elaborazioni del presente record',0,0,null)


INSERT INTO ESENZIONIFASCE.DIZIONARIO_CAMPI (NOME_TABELLA,NOME_CAMPO,DESCRIZIONE,PK,FK,TABELLA_RIF) VALUES ('AUTOCERTIFICAZIONE_APERTURE_ETL','DATA_INSERIMENTO',      'data inserimento record',0,0,null);
INSERT INTO ESENZIONIFASCE.DIZIONARIO_CAMPI (NOME_TABELLA,NOME_CAMPO,DESCRIZIONE,PK,FK,TABELLA_RIF) VALUES ('AUTOCERTIFICAZIONE_APERTURE_ETL','DATA_AGGIORNAMENTO',    'data aggiornamento record',0,0,null);





INSERT INTO ESENZIONIFASCE.DIZIONARIO_CAMPI (NOME_TABELLA,NOME_CAMPO,DESCRIZIONE,PK,FK,TABELLA_RIF) VALUES ('AUTOCERTIFICAZIONE_CHIUSURE_ETL','ID',                    'identificativo univoco tabella',1,0,null);

INSERT INTO ESENZIONIFASCE.DIZIONARIO_CAMPI (NOME_TABELLA,NOME_CAMPO,DESCRIZIONE,PK,FK,TABELLA_RIF) VALUES ('AUTOCERTIFICAZIONE_CHIUSURE_ETL','FK_ESENZIONE_PROROGATA','chiave primaria della tabella ESENZIONI_FASCE relativa all''esenzione che ha generato l''autocertificazione di chiusura.',0,0,null);
INSERT INTO ESENZIONIFASCE.DIZIONARIO_CAMPI (NOME_TABELLA,NOME_CAMPO,DESCRIZIONE,PK,FK,TABELLA_RIF) VALUES ('AUTOCERTIFICAZIONE_CHIUSURE_ETL','STATO_ELABORAZIONE',    'stato di elaborazione del record, 0 -- inserito e non ancora elaborato, 1 -- elaborato con esito positovo, 2 -- elaborato con esito negativo bloccante, 3 elaborato con esito negativo non bloccante',0,0,null);

INSERT INTO ESENZIONIFASCE.DIZIONARIO_CAMPI (NOME_TABELLA,NOME_CAMPO,DESCRIZIONE,PK,FK,TABELLA_RIF) VALUES ('AUTOCERTIFICAZIONE_CHIUSURE_ETL','CODICE_ESENZIONE',      'Codice dell''esenzione da generare',0,0,null);
INSERT INTO ESENZIONIFASCE.DIZIONARIO_CAMPI (NOME_TABELLA,NOME_CAMPO,DESCRIZIONE,PK,FK,TABELLA_RIF) VALUES ('AUTOCERTIFICAZIONE_CHIUSURE_ETL','ID_UNI_BENEFICIARIO',   'id universale del beneficiario dell''esenzione da generare',0,0,null);
INSERT INTO ESENZIONIFASCE.DIZIONARIO_CAMPI (NOME_TABELLA,NOME_CAMPO,DESCRIZIONE,PK,FK,TABELLA_RIF) VALUES ('AUTOCERTIFICAZIONE_CHIUSURE_ETL','ID_UNI_DICHIARANTE',    'id universale del dichiarante dell''esenzione da generare',0,0,null);
INSERT INTO ESENZIONIFASCE.DIZIONARIO_CAMPI (NOME_TABELLA,NOME_CAMPO,DESCRIZIONE,PK,FK,TABELLA_RIF) VALUES ('AUTOCERTIFICAZIONE_CHIUSURE_ETL','ID_UNI_TITOLARE',       'id universale del titolare dell''esenzione da generare',0,0,null);
INSERT INTO ESENZIONIFASCE.DIZIONARIO_CAMPI (NOME_TABELLA,NOME_CAMPO,DESCRIZIONE,PK,FK,TABELLA_RIF) VALUES ('AUTOCERTIFICAZIONE_CHIUSURE_ETL','TITOLO',                'titolo tra i soggetti dell''esenzione generata',0,0,null);


INSERT INTO ESENZIONIFASCE.DIZIONARIO_CAMPI (NOME_TABELLA,NOME_CAMPO,DESCRIZIONE,PK,FK,TABELLA_RIF) VALUES ('AUTOCERTIFICAZIONE_CHIUSURE_ETL','PROTOCOLLO',            'protocollo dell''esenzione cha andiamo a chiudere',0,0,null);
INSERT INTO ESENZIONIFASCE.DIZIONARIO_CAMPI (NOME_TABELLA,NOME_CAMPO,DESCRIZIONE,PK,FK,TABELLA_RIF) VALUES ('AUTOCERTIFICAZIONE_CHIUSURE_ETL','DATA_INIZIO',           'data inizio dell''esenzione che andiamo a chiuere',0,0,null);
INSERT INTO ESENZIONIFASCE.DIZIONARIO_CAMPI (NOME_TABELLA,NOME_CAMPO,DESCRIZIONE,PK,FK,TABELLA_RIF) VALUES ('AUTOCERTIFICAZIONE_CHIUSURE_ETL','DATA_FINE_OLD',         'vecchia data fine dell''esenzione che andiamo a chiudere',0,0,null);



INSERT INTO ESENZIONIFASCE.DIZIONARIO_CAMPI (NOME_TABELLA,NOME_CAMPO,DESCRIZIONE,PK,FK,TABELLA_RIF) VALUES ('AUTOCERTIFICAZIONE_CHIUSURE_ETL','DATA_INVIO',            'data dell''ultimo invio effettuato, e quindi data fine settata sull''esenzione inviata',0,0,null);
INSERT INTO ESENZIONIFASCE.DIZIONARIO_CAMPI (NOME_TABELLA,NOME_CAMPO,DESCRIZIONE,PK,FK,TABELLA_RIF) VALUES ('AUTOCERTIFICAZIONE_CHIUSURE_ETL','NUMERO_ELABORAZIONI',   'numero elaborazioni del presente record',0,0,null)


INSERT INTO ESENZIONIFASCE.DIZIONARIO_CAMPI (NOME_TABELLA,NOME_CAMPO,DESCRIZIONE,PK,FK,TABELLA_RIF) VALUES ('AUTOCERTIFICAZIONE_CHIUSURE_ETL','DATA_INSERIMENTO',      'data inserimento record',0,0,null);
INSERT INTO ESENZIONIFASCE.DIZIONARIO_CAMPI (NOME_TABELLA,NOME_CAMPO,DESCRIZIONE,PK,FK,TABELLA_RIF) VALUES ('AUTOCERTIFICAZIONE_CHIUSURE_ETL','DATA_AGGIORNAMENTO',    'data aggiornamento record',0,0,null);



INSERT INTO ESENZIONIFASCE.DIZIONARIO_CAMPI (NOME_TABELLA,NOME_CAMPO,DESCRIZIONE,PK,FK,TABELLA_RIF) VALUES ('ESITO_INVIO_APERTURE_ETL','ID',                     'identificativo univoco tabella',1,0,null);

INSERT INTO ESENZIONIFASCE.DIZIONARIO_CAMPI (NOME_TABELLA,NOME_CAMPO,DESCRIZIONE,PK,FK,TABELLA_RIF) VALUES ('ESITO_INVIO_APERTURE_ETL','FK_ESITO_INVIO_ETL',     'chiave primaria della tabella ESITO_INVIO_APERTURE_ETL per il record a cui va legato l''esito.',0,0,null);
INSERT INTO ESENZIONIFASCE.DIZIONARIO_CAMPI (NOME_TABELLA,NOME_CAMPO,DESCRIZIONE,PK,FK,TABELLA_RIF) VALUES ('ESITO_INVIO_APERTURE_ETL','FK_WSODS_SOAP_TRACKING', 'se tracking abilitato e record non svecchiato contiene la chiave primaria della tabella WSODS_SOAP_TRACKING',0,0,null);

INSERT INTO ESENZIONIFASCE.DIZIONARIO_CAMPI (NOME_TABELLA,NOME_CAMPO,DESCRIZIONE,PK,FK,TABELLA_RIF) VALUES ('ESITO_INVIO_APERTURE_ETL','ESITO',                  'Esito dell''elaborazione',0,0,null);
INSERT INTO ESENZIONIFASCE.DIZIONARIO_CAMPI (NOME_TABELLA,NOME_CAMPO,DESCRIZIONE,PK,FK,TABELLA_RIF) VALUES ('ESITO_INVIO_APERTURE_ETL','ESITO_COD',              'codice del''esito se presente',0,0,null);
INSERT INTO ESENZIONIFASCE.DIZIONARIO_CAMPI (NOME_TABELLA,NOME_CAMPO,DESCRIZIONE,PK,FK,TABELLA_RIF) VALUES ('ESITO_INVIO_APERTURE_ETL','ESITO_DESC',             'descrizione dell''esito se presente',0,0,null);
INSERT INTO ESENZIONIFASCE.DIZIONARIO_CAMPI (NOME_TABELLA,NOME_CAMPO,DESCRIZIONE,PK,FK,TABELLA_RIF) VALUES ('ESITO_INVIO_APERTURE_ETL','STACK_TRACE',            'stack dell''eccezione se presente',0,0,null);


INSERT INTO ESENZIONIFASCE.DIZIONARIO_CAMPI (NOME_TABELLA,NOME_CAMPO,DESCRIZIONE,PK,FK,TABELLA_RIF) VALUES ('ESITO_INVIO_APERTURE_ETL','DATA_INSERIMENTO',       'data inserimento record',0,0,null);
INSERT INTO ESENZIONIFASCE.DIZIONARIO_CAMPI (NOME_TABELLA,NOME_CAMPO,DESCRIZIONE,PK,FK,TABELLA_RIF) VALUES ('ESITO_INVIO_APERTURE_ETL','DATA_AGGIORNAMENTO',     'data aggiornamento record',0,0,null);




INSERT INTO ESENZIONIFASCE.DIZIONARIO_CAMPI (NOME_TABELLA,NOME_CAMPO,DESCRIZIONE,PK,FK,TABELLA_RIF) VALUES ('ESITO_INVIO_CHIUSURE_ETL','ID',                     'identificativo univoco tabella',1,0,null);

INSERT INTO ESENZIONIFASCE.DIZIONARIO_CAMPI (NOME_TABELLA,NOME_CAMPO,DESCRIZIONE,PK,FK,TABELLA_RIF) VALUES ('ESITO_INVIO_CHIUSURE_ETL','FK_ESITO_INVIO_ETL',     'chiave primaria della tabella ESITO_INVIO_CHIUSURE_ETL per il record a cui va legato l''esito.',0,0,null);
INSERT INTO ESENZIONIFASCE.DIZIONARIO_CAMPI (NOME_TABELLA,NOME_CAMPO,DESCRIZIONE,PK,FK,TABELLA_RIF) VALUES ('ESITO_INVIO_CHIUSURE_ETL','FK_WSODS_SOAP_TRACKING', 'se tracking abilitato e record non svecchiato contiene la chiave primaria della tabella WSODS_SOAP_TRACKING',0,0,null);

INSERT INTO ESENZIONIFASCE.DIZIONARIO_CAMPI (NOME_TABELLA,NOME_CAMPO,DESCRIZIONE,PK,FK,TABELLA_RIF) VALUES ('ESITO_INVIO_CHIUSURE_ETL','ESITO',                  'Esito dell''elaborazione',0,0,null);
INSERT INTO ESENZIONIFASCE.DIZIONARIO_CAMPI (NOME_TABELLA,NOME_CAMPO,DESCRIZIONE,PK,FK,TABELLA_RIF) VALUES ('ESITO_INVIO_CHIUSURE_ETL','ESITO_COD',              'codice del''esito se presente',0,0,null);
INSERT INTO ESENZIONIFASCE.DIZIONARIO_CAMPI (NOME_TABELLA,NOME_CAMPO,DESCRIZIONE,PK,FK,TABELLA_RIF) VALUES ('ESITO_INVIO_CHIUSURE_ETL','ESITO_DESC',             'descrizione dell''esito se presente',0,0,null);
INSERT INTO ESENZIONIFASCE.DIZIONARIO_CAMPI (NOME_TABELLA,NOME_CAMPO,DESCRIZIONE,PK,FK,TABELLA_RIF) VALUES ('ESITO_INVIO_CHIUSURE_ETL','STACK_TRACE',            'stack dell''eccezione se presente',0,0,null);

INSERT INTO ESENZIONIFASCE.DIZIONARIO_CAMPI (NOME_TABELLA,NOME_CAMPO,DESCRIZIONE,PK,FK,TABELLA_RIF) VALUES ('ESITO_INVIO_CHIUSURE_ETL','DATA_INSERIMENTO',       'data inserimento record',0,0,null);
INSERT INTO ESENZIONIFASCE.DIZIONARIO_CAMPI (NOME_TABELLA,NOME_CAMPO,DESCRIZIONE,PK,FK,TABELLA_RIF) VALUES ('ESITO_INVIO_CHIUSURE_ETL','DATA_AGGIORNAMENTO',     'data aggiornamento record',0,0,null);


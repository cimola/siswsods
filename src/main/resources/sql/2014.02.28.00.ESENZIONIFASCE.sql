SET SCHEMA ESENZIONIFASCE;

-- ESENZIONI_FASCE 
ALTER TABLE ESENZIONI_FASCE ADD SORGENTE VARCHAR (10) DEFAULT 'ALTRO' NOT NULL;

-- AUTOCERTIFICAZIONE_SCARTATE 
ALTER TABLE AUTOCERTIFICAZIONE_SCARTATE ADD SORGENTE VARCHAR (10);

-- AUTOCERTIFICAZIONE_TMP 
ALTER TABLE AUTOCERTIFICAZIONE_TMP ADD SORGENTE VARCHAR (10);
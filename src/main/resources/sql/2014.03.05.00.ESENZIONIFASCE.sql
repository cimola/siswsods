SET SCHEMA ESENZIONIFASCE;

-- creazione tabella per il tracking

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


) IN TBS_ESENZIONIFA_N0 INDEX IN TBS_ESENZIONIFA_N0 COMPRESS YES;


GRANT INSERT, SELECT, UPDATE, DELETE, INDEX  ON TABLE WSODS_SOAP_TRACKING TO USER yneng;
GRANT INSERT, SELECT, UPDATE, DELETE, INDEX  ON TABLE WSODS_SOAP_TRACKING TO USER esefasce;
GRANT INSERT, SELECT, UPDATE, DELETE, INDEX  ON TABLE WSODS_SOAP_TRACKING TO USER afeng;


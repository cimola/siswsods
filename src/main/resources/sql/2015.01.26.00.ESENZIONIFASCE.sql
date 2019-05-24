SET SCHEMA ESENZIONIFASCE;

-- definisce se la ricevuta e' dovuta ad un download relativo alla linea di scaricamento corrente
-- utile a mantenere allineato il sistema al momento corrente oppute se e' una linea di recupero.
-- valori amessi : CORRENTE/RECUPERO
ALTER TABLE RICEVUTA_DOWNLOAD_SOGEI ADD TIPOLOGIA_RICEVUTA	VARCHAR(32) DEFAULT 'CORRENTE' NOT NULL;

CREATE INDEX IDX_RIC_DOW_SOG_01 ON RICEVUTA_DOWNLOAD_SOGEI ( STATO_ELABORAZIONE ); --  recupero per elaborazione asincrona
CREATE INDEX IDX_RIC_DOW_SOG_02 ON RICEVUTA_DOWNLOAD_SOGEI ( TIPO_AUTOCERTIFICAZIONE, TIPOLOGIA_RICEVUTA ); --  recupero last datooralimite
CREATE INDEX IDX_RIC_DOW_SOG_03 ON RICEVUTA_DOWNLOAD_SOGEI ( DATA_INSERIMENTO); -- Ordinamento


-- RECUPERO_DOWNLOAD_SOGEI 
CREATE TABLE RECUPERO_DOWNLOAD_SOGEI  (
	ID 						BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH +1 INCREMENT BY +1 MINVALUE +1  MAXVALUE +9223372036854775807 NO CYCLE ) , 
	DATA_ORA_LIMITE_START   CHAR(12) NOT NULL, --ddmmyyyyhhmm / yyyyMMddhhmm
	DATA_ORA_LIMITE_STOP 	CHAR(12) NOT NULL, --ddmmyyyyhhmm / yyyyMMddhhmm
	TIPO_AUTOCERTIFICAZIONE CHAR (1) NOT NULL,
	
	DATA_INSERIMENTO        TIMESTAMP DEFAULT current timestamp NOT NULL,
	DATA_AGGIORNAMENTO      TIMESTAMP DEFAULT current timestamp NOT NULL
) IN TBS_ESENZIONIFA_N0 INDEX IN TBS_ESENZIONIFA_N0 COMPRESS YES;


INSERT INTO RECUPERO_DOWNLOAD_SOGEI (DATA_ORA_LIMITE_START,DATA_ORA_LIMITE_STOP,TIPO_AUTOCERTIFICAZIONE) values ('311299992359','010100010000', 'I');
INSERT INTO RECUPERO_DOWNLOAD_SOGEI (DATA_ORA_LIMITE_START,DATA_ORA_LIMITE_STOP,TIPO_AUTOCERTIFICAZIONE) values ('311299992359','010100010000', 'V');

GRANT INSERT, SELECT, UPDATE, DELETE, INDEX  ON TABLE RECUPERO_DOWNLOAD_SOGEI TO USER yneng;
GRANT INSERT, SELECT, UPDATE, DELETE, INDEX  ON TABLE RECUPERO_DOWNLOAD_SOGEI TO USER esefasce;
GRANT INSERT, SELECT, UPDATE, DELETE, INDEX  ON TABLE RECUPERO_DOWNLOAD_SOGEI TO USER afeng;
																
SET SCHEMA ESENZIONIFASCE;

-- RICEVUTA_DOWNLOAD_SOGEI 
GRANT INSERT, SELECT, UPDATE, DELETE, INDEX  ON TABLE RICEVUTA_DOWNLOAD_SOGEI TO USER yneng;
GRANT INSERT, SELECT, UPDATE, DELETE, INDEX  ON TABLE RICEVUTA_DOWNLOAD_SOGEI TO USER esefasce;
GRANT INSERT, SELECT, UPDATE, DELETE, INDEX  ON TABLE RICEVUTA_DOWNLOAD_SOGEI TO USER afeng;

-- AUTOCERTIFICAZIONE_TMP 
GRANT INSERT, SELECT, UPDATE, DELETE, INDEX  ON TABLE AUTOCERTIFICAZIONE_TMP TO USER yneng;
GRANT INSERT, SELECT, UPDATE, DELETE, INDEX  ON TABLE AUTOCERTIFICAZIONE_TMP TO USER esefasce;
GRANT INSERT, SELECT, UPDATE, DELETE, INDEX  ON TABLE AUTOCERTIFICAZIONE_TMP TO USER afeng;

-- AUTOCERTIFICAZIONE_SCARTATE 
GRANT INSERT, SELECT, UPDATE, DELETE, INDEX  ON TABLE AUTOCERTIFICAZIONE_SCARTATE TO USER yneng;
GRANT INSERT, SELECT, UPDATE, DELETE, INDEX  ON TABLE AUTOCERTIFICAZIONE_SCARTATE TO USER esefasce;
GRANT INSERT, SELECT, UPDATE, DELETE, INDEX  ON TABLE AUTOCERTIFICAZIONE_SCARTATE TO USER afeng;
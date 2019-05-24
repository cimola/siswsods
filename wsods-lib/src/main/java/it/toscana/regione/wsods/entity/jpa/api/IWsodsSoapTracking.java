package it.toscana.regione.wsods.entity.jpa.api;

import java.sql.Timestamp;

public interface IWsodsSoapTracking extends IDbEntity {
	
	
	final static String SRC_WSODS = "WSODS";
	final static String SRC_APS_ESE = "APS_ESE";
	final static String SRC_TOTEM = "TOTEM";
	final static String SRC_AUTOCERTIFICAZIONI_REDDITO = "AUTOCERTIFICAZIONI_REDDITO";
	final static String DEST_SOGEI = "SOGEI";
	final static String DEST_APS_ESE = "APS_ESE";
	
	Timestamp getDataRequest();
	
	
	void setDataRequest(Timestamp dataRequest);
	
	
	Timestamp getDataResponse();
	
	
	void setDataResponse(Timestamp dataResponse);
	
	
	String getRequest();
	
	
	void setRequest(String request);
	
	
	String getResponse();
	
	
	void setResponse(String response);
	
	
	String getSrc();
	
	
	void setSrc(String src);
	
	
	String getDest();
	
	
	void setDest(String dest);
	
	
	long getFkLink();
	
	
	void setFkLink(long fkLink);
	
	
	String getEsito();
	
	
	void setEsito(String esito);
	
	
	String getStackTrace();
	
	
	void setStackTrace(String stackTrace);
	
}

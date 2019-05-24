package it.toscana.regione.wsods.ejb.tracker.api;


import java.io.Serializable;

import javax.ejb.Local;

@Local
public interface ISoapTracking extends Serializable {
	
	public static final String EJB_REF = "soapTracking";
	
	long tracke(final boolean enable, final String src,final String dest);
	long tracke(final boolean enable, final String src,final String dest,final long fkLink);
	
	
	void removeTracke(final boolean enable, final long id);
	
	void trackeReqest(final boolean enable, final long id,final String request);
	void trackeResponse(final boolean enable, final long id,final String response);
	
	void trackeOk(final boolean enable, final long id);
	void trackeKo(final boolean enable, final long id);
	void trackeKo(final boolean enable, final long id,final String stackTrace);
}

package it.toscana.regione.wsods.ejb.download.api;

import javax.ejb.Local;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

@Local
public interface IRecuperoDownloadManager {
	

	final static String EJB_REF = "RecuperoDownloadManager";
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	void configuraDownloadSogeiRecupero(final String dataOraLimiteMax, final String dataOraLimiteMin, final String tipoDownload);
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	void configuraDownloadSogeiRecupero();
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	void skipDownloadSogeiCorrente(final String  dataOraLimiteMin,final String tipoDownload);
	
}

/** */
package it.toscana.regione.wsods.ejb.download.api;

import it.toscana.regione.wsods.exception.WsOdsRuntimeException;

import javax.ejb.Local;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;


/**
 * @author cciurli
 *
 */
@Local
public interface IManagerDownloader {

	final static String EJB_REF = "ManagerDownload";
	final static String EJB_CERT_REF = "ManagerCertDownload";
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	void download(final String tipoRichiesta, final String tipologiaRicevuta) throws WsOdsRuntimeException;
	

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void elaboraAsincrono();
}

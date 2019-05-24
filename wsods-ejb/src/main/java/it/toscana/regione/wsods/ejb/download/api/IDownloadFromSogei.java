/** */
package it.toscana.regione.wsods.ejb.download.api;

import it.toscana.regione.wsods.exception.DownloadException;

import javax.ejb.Local;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;



/**
 * @author cciurli
 *
 */
@Local
public interface IDownloadFromSogei {

	public final static String 	EJB_REF	= "DownloadFromSogei";

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	it.toscana.regione.autocertificazionireddito.entity.jaxb.sogei.download.response.Ricevuta send(final String dataOraLimite, final String tipologia) throws DownloadException;
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	it.toscana.regione.certificazionireddito.entity.jaxb.sogei.download.response.Ricevuta sendCert(final String dataLimite, final String tipologia) throws DownloadException;
}

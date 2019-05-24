/** */
package it.toscana.regione.wsods.ejb.download.api;

import it.toscana.regione.wsods.entity.jpa.api.IAutocertificazioneTmp;
import it.toscana.regione.wsods.entity.jpa.api.ICertificazioneTmp;
import it.toscana.regione.wsods.entity.jpa.api.IRicevutaDownloadSogei;
import it.toscana.regione.wsods.exception.DownloadException;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;


/**
 * @author cciurli
 *
 */
@Local
public interface IPersistAutocertificazioniSogei {

	public final static String 	EJB_REF	= "PersistAutocertificazioniSogei";

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	void store(final it.toscana.regione.autocertificazionireddito.entity.jaxb.sogei.download.response.Ricevuta ricevutaDownloadSogei, final String tipologiaRicevuta, final String xml) throws DownloadException;
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	void storeCert(final it.toscana.regione.certificazionireddito.entity.jaxb.sogei.download.response.Ricevuta ricevutaDownloadSogeiCert, final String tipologiaRicevuta, final String xml) throws DownloadException;
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	void store(final IRicevutaDownloadSogei ricevutaDownloadSogei) throws DownloadException;
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	void storeCert(final IRicevutaDownloadSogei ricevutaDownloadSogei) throws DownloadException;
	
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	void store(final List<IAutocertificazioneTmp> lista) throws DownloadException;	
	
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	void storeCert(final List<ICertificazioneTmp> lista) throws DownloadException;	
}

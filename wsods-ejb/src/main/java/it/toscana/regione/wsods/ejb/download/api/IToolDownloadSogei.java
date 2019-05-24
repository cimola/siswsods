package it.toscana.regione.wsods.ejb.download.api;

import it.toscana.regione.wsods.exception.ConvertitoreException;
import it.toscana.regione.wsods.exception.DownloadException;

import javax.ejb.Local;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

@Local
public interface IToolDownloadSogei {

	final static String EJB_REF = "IToolDownloadSogei";
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	it.toscana.regione.autocertificazionireddito.entity.jaxb.sogei.download.response.Ricevuta sendRequestDownload(String tipoRichiesta, long newDataOraLimite) throws ConvertitoreException;
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	it.toscana.regione.certificazionireddito.entity.jaxb.sogei.download.response.Ricevuta sendRequestDownloadCertificazioni(String tipoOperazione, long newDataLimite) throws ConvertitoreException;
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	long newDataLimite(String tipoRichiesta, final String tipologiaRicevuta) throws ConvertitoreException;
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	void serializaEsalva(it.toscana.regione.autocertificazionireddito.entity.jaxb.sogei.download.response.Ricevuta ricevutaDownloadInsersioniSogei, final String tipologiaRicevuta);
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	void serializaEsalvaCert(it.toscana.regione.certificazionireddito.entity.jaxb.sogei.download.response.Ricevuta ricevutaDownloadInsersioniSogei, final String tipologiaRicevuta);
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	void convertiEsalva(it.toscana.regione.autocertificazionireddito.entity.jaxb.sogei.download.response.Ricevuta ricevutaDownloadSogei, final String tipologiaRicevuta) throws DownloadException;

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	void convertiEsalvaCert(it.toscana.regione.certificazionireddito.entity.jaxb.sogei.download.response.Ricevuta ricevutaDownloadSogei, final String tipologiaRicevuta) throws DownloadException;

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	long aggiornaRicevutaDownload();

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	long aggiornaRicevutaDownloadCert();
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	it.toscana.regione.autocertificazionireddito.entity.jaxb.sogei.download.response.Ricevuta deserializza(final String ricevutaXml);
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	it.toscana.regione.certificazionireddito.entity.jaxb.sogei.download.response.Ricevuta deserializzaCert(final String ricevutaXml);
}

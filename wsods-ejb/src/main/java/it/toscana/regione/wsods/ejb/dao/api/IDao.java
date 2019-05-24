/** */
package it.toscana.regione.wsods.ejb.dao.api;

import it.toscana.regione.wsods.entity.jpa.api.IDbEntity;
import it.toscana.regione.wsods.exception.DaoException;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;


/**
 * @author cciurli
 */
@Local
public interface IDao<E extends IDbEntity> extends Serializable {

	final static String EJB_REF_AUTOCERTIFICAZIONE_SCARTATE = "AutocertificazioneScartateDao";
	final static String EJB_REF_AUTOCERTIFICAZIONE_TMP = "AutocertificazioneTmpDao";
	final static String EJB_REF_DOWNLOAD_AUTOCERTIFICAZIONE = "DownloadAutocertificazioneDao";
	final static String EJB_REF_CERTIFICAZIONE_SCARTATE = "CertificazioneScartateDao";
	final static String EJB_REF_CERTIFICAZIONE_TMP = "CertificazioneTmpDao";
	final static String EJB_REF_DOWNLOAD_CERTIFICAZIONE = "DownloadCertificazioneDao";	
	final static String EJB_REF_ESENZIONI_FASCE = "EsenzioniFasceDao";
	final static String EJB_REF_RICEVUTA_DOWNLOAD_SOGEI = "RicevutaDownloadSogeiDao";
	final static String EJB_REF_UPLOAD_AUTOCERTIFICAZIONE = "UploadAutocertificazioneDao";
	final static String EJB_REF_WSODS_SOAP_TRACKING = "SoapTrackingDao";
//	final static String EJB_REF_PROROGHE = "prorogheDao";
	final static String EJB_REF_RECUPERO_DOWNLOAD_SOGEI = "RecuperoDownloadSogeiDao";
	final static String EJB_REF_ID_UNI_DA_AGGIORNARE = "IdUniDaAggiornareDao";
	final static String EJB_REF_TRACCIA_OBSOLESCENZA_ID_UNI = "TracciaObsolescenzaIdUniDao";
	final static String EJB_REF_AUTOCERTIFICAZIONE_APERTURE_ETL = "autocertificazioniApertureEtlDao";
	final static String EJB_REF_AUTOCERTIFICAZIONE_CHIUSURE_ETL = "autocertificazioniChiusureEtlDao";

	final static String EJB_REF_ESITO_INVIO_APERTURE_ETL = "esitoInvioApertureEtlDao";
	final static String EJB_REF_ESITO_INVIO_CHIUSURE_ETL = "esitoInvioChiusureEtlDao";
	
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public void  flush()  throws DaoException;

	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	void insert(final E entity, final boolean flush) throws DaoException;

	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	int update(final String jpql, final Map<String, Object> param) throws DaoException;

	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	int remove(final String jpql, final Map<String, Object> whereParam) throws DaoException;

	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	void remove(final E entity) throws DaoException;

	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	void remove(final long id) throws DaoException;

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	E get(String jpql, Map<String, Object> param) throws DaoException;
	
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	E getFirst(final String jpql, final Map<String, Object> param) throws DaoException;
	
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	E get(final long id) throws DaoException;
	
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	List<E> get(final int max, final String jpql, final Map<String, Object> param) throws DaoException;
}

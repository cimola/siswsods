/** */
package it.toscana.regione.wsods.ejb.sdm.api;

import it.toscana.regione.eng.common.rest.response.entity.sisdatamanager.listaAssociazioniIdUni.ListaAssociazioniIdUni;
import it.toscana.regione.wsods.entity.bean.api.ISoggetto;
import it.toscana.regione.wsods.exception.SisDataManagerException;

import java.sql.Timestamp;

import javax.ejb.Local;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;


/**
 * @author cciurli
 *
 */
@Local
public interface ISdm {

	public static final String EJB_REF = "SDM";
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	void ricercaAnagraficheByCfWSODS(final String codiceFiscale) throws SisDataManagerException;
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	void ricercaAnagraficheByCfETrattiWSODS(final String codiceFiscale, final String nome, final String cognome, final String sesso, final String dataNascita, final String comuneNascita, final String statoNascita) throws SisDataManagerException;
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	ISoggetto getFromCodiceFiscaleNoTransaction(final String codiceFiscale) throws SisDataManagerException;
	
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	ISoggetto getFromCodiceFiscale(final String codiceFiscale) throws SisDataManagerException;

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	ISoggetto getFromIdUniversaleNoTransaction(final String idUniversale) throws SisDataManagerException;

	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	ISoggetto getFromIdUniversale(final String idUniversale) throws SisDataManagerException;

	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	ISoggetto getFromCodiceFiscaleNoCache(final String codiceFiscale) throws SisDataManagerException;

	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	ISoggetto getFromIdUniversaleNoCache(final String idUniversale) throws SisDataManagerException;
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	ListaAssociazioniIdUni getLinks(final Timestamp lastTime) throws SisDataManagerException;
} 

/** */
package it.toscana.regione.wsods.ejb.dao.api;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import it.toscana.regione.wsods.entity.jpa.api.IRicevutaDownloadSogei;
import it.toscana.regione.wsods.exception.DaoException;


/**
 * @author cciurli
 *
 */
@Local
public interface IRicevutaDownloadSogeiDao  extends IDao<IRicevutaDownloadSogei>{

	final static String EJB_REF = IDao.EJB_REF_RICEVUTA_DOWNLOAD_SOGEI;
	
	/** */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	String getLastDataOraLimite(final String tipoRichiesta, final String tipologiaRicevuta) throws DaoException;

	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	IRicevutaDownloadSogei getDaElaborare(final long statoElaborazione, final List<String> tipiEsclusivi) throws DaoException;

	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	void aggiornaElaborati(final long id, final boolean inserito) throws DaoException;
	

	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	void insertStartRecupero(final String tipoRichiesta, final String dataOraLimite) throws DaoException;
	

	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public void insertSkipCorrente(final String tipoRichiesta, final String dataOraLimite) throws DaoException;
		
	
}

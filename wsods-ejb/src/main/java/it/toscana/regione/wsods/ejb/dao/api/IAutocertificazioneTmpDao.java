/** */
package it.toscana.regione.wsods.ejb.dao.api;

import it.toscana.regione.wsods.entity.jpa.api.IAutocertificazioneTmp;
import it.toscana.regione.wsods.exception.DaoException;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;


/**
 * @author cciurli
 *
 */
@Local
public interface IAutocertificazioneTmpDao extends IDao<IAutocertificazioneTmp> {

	final static String EJB_REF = IDao.EJB_REF_AUTOCERTIFICAZIONE_TMP;

	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	List<IAutocertificazioneTmp> loadFromDiscriminante(final int sizeWindow, final long numeroTimer , final long discriminante) throws DaoException;
	
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	void incrementaElaborazioi(final Long id) throws DaoException;
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	void setElaborazioi(final Long id, final long numeroElaborazioni) throws DaoException;
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	boolean notExist(final IAutocertificazioneTmp entity);
	
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	long lastDataOrdinamento();
	
	
}

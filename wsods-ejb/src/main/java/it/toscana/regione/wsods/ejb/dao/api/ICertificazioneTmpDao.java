/** */
package it.toscana.regione.wsods.ejb.dao.api;

import it.toscana.regione.wsods.entity.jpa.api.ICertificazioneTmp;
import it.toscana.regione.wsods.exception.DaoException;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;


/**
 * @author vmaltese
 */
@Local
public interface ICertificazioneTmpDao extends IDao<ICertificazioneTmp> {

	final static String EJB_REF = IDao.EJB_REF_CERTIFICAZIONE_TMP;

	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	List<ICertificazioneTmp> loadFromDiscriminante(final int sizeWindow, final long numeroTimer , final long discriminante) throws DaoException;
	
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	void incrementaElaborazioi(final Long id) throws DaoException;
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	void setElaborazioi(final Long id, final long numeroElaborazioni) throws DaoException;
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	boolean notExist(final ICertificazioneTmp entity);
	
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	long lastDataOrdinamento();
}

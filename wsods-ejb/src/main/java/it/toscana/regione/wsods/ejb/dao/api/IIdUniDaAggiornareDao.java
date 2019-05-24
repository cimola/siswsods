/** */
package it.toscana.regione.wsods.ejb.dao.api;

import java.sql.Timestamp;
import java.util.List;

import it.toscana.regione.wsods.entity.jpa.api.IIdUniDaAggiornare;

import javax.ejb.Local;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;


/**
 * @author cciurli
 *
 */
@Local
public interface IIdUniDaAggiornareDao extends IDao<IIdUniDaAggiornare> {

	final static String EJB_REF = IDao.EJB_REF_ID_UNI_DA_AGGIORNARE;
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	boolean notExist(final IIdUniDaAggiornare entity);

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	Timestamp getLast();
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	List<Long> getFirstLinksToUpdate(final int size);
	
}

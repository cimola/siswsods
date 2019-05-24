/** */
package it.toscana.regione.wsods.ejb.dao.api;

import it.toscana.regione.wsods.entity.jpa.api.ICertificazioneScartate;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;


/**
 * @author vmaltese
 *
 */
@Local
public interface ICertificazioneScartateDao extends IDao<ICertificazioneScartate> {
	
	final static String EJB_REF = IDao.EJB_REF_CERTIFICAZIONE_SCARTATE;
	
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public List<ICertificazioneScartate> getForMove(final int size);
	
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public List<Long> getForGianov4(final int size);
}

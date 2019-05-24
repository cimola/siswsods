/** */
package it.toscana.regione.wsods.ejb.dao.api;

import it.toscana.regione.wsods.entity.jpa.api.IAutocertificazioneScartate;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;


/**
 * @author cciurli
 *
 */
@Local
public interface IAutocertificazioneScartateDao extends IDao<IAutocertificazioneScartate> {
	
	final static String EJB_REF = IDao.EJB_REF_AUTOCERTIFICAZIONE_SCARTATE;
	
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public List<IAutocertificazioneScartate> getForMove(final int size);

	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	List<IAutocertificazioneScartate> getForTitoloNull(final long numeroTimer, final long discriminante, final int size);
	
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public List<Long> getForGianov4(final int size);

	
	
}

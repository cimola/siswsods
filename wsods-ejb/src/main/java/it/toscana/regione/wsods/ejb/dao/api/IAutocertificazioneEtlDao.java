/** */
package it.toscana.regione.wsods.ejb.dao.api;

import it.toscana.regione.wsods.entity.jpa.api.IAutocertificazioneEtl;
import it.toscana.regione.wsods.exception.DaoException;

import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;


/**
 * @author cciurli
 *
 */
public interface IAutocertificazioneEtlDao<E extends IAutocertificazioneEtl > extends IDao<E> {
	
	
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	List<E> loadFromDiscriminante(final int maxRow,final long maxElaborazioni, final long numeroTimer, final long discriminante) throws DaoException;
	

}

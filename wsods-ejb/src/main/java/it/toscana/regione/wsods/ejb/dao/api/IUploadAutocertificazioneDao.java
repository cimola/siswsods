/** */
package it.toscana.regione.wsods.ejb.dao.api;

import it.toscana.regione.wsods.entity.jpa.api.IUploadAutocertificazione;
import it.toscana.regione.wsods.exception.DaoException;

import javax.ejb.Local;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;


/**
 * @author cciurli
 *
 */
@Local
public interface IUploadAutocertificazioneDao extends IDao<IUploadAutocertificazione> {
	
	final static String EJB_REF = IDao.EJB_REF_UPLOAD_AUTOCERTIFICAZIONE;
	
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	IUploadAutocertificazione legata(final long fkEsenzioniFasce) throws DaoException ;
}

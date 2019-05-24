/** */
package it.toscana.regione.wsods.ejb.dao.api;

import it.toscana.regione.wsods.entity.jpa.api.IDownloadAutocertificazione;
import it.toscana.regione.wsods.exception.DaoException;

import javax.ejb.Local;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;


/**
 * @author cciurli
 *
 */
@Local
public interface IDownloadAutocertificazioneDao extends IDao<IDownloadAutocertificazione> {

	final static String EJB_REF = IDao.EJB_REF_DOWNLOAD_AUTOCERTIFICAZIONE;


	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	IDownloadAutocertificazione legata(final long fkEsenzioniFasce) throws DaoException;
			
}

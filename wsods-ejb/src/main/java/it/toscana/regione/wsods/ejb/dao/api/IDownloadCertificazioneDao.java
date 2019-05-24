/** */
package it.toscana.regione.wsods.ejb.dao.api;

import it.toscana.regione.wsods.entity.jpa.api.IDownloadCertificazione;
import it.toscana.regione.wsods.exception.DaoException;

import javax.ejb.Local;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;


/**
 * @author vmaltese
 *
 */
@Local
public interface IDownloadCertificazioneDao extends IDao<IDownloadCertificazione> {

	final static String EJB_REF = IDao.EJB_REF_DOWNLOAD_CERTIFICAZIONE;

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	IDownloadCertificazione legata(final long fkEsenzioniFasce) throws DaoException;
}

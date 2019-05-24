/** */
package it.toscana.regione.wsods.ejb.dao.impl;

import it.toscana.regione.wsods.ejb.dao.abs.EntityDao;
import it.toscana.regione.wsods.ejb.dao.api.IDao;
import it.toscana.regione.wsods.ejb.dao.api.IDownloadCertificazioneDao;
import it.toscana.regione.wsods.entity.jpa.api.IDownloadCertificazione;
import it.toscana.regione.wsods.entity.jpa.impl.DownloadCertificazione;
import it.toscana.regione.wsods.exception.DaoException;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author vmaltese
 *
 */
@Stateless(name = IDownloadCertificazioneDao.EJB_REF)
public class DownloadCertificazioneDao extends EntityDao<IDownloadCertificazione> implements IDownloadCertificazioneDao, IDao<IDownloadCertificazione> {

	private static final Logger PERFORMANCE = LoggerFactory.getLogger("WSODS-DAO-PERFORMANCE");
	
	
	/** fild serialVersionUID {@like long}*/
	private static final long	serialVersionUID	= -690586674187416510L;
	
	private final static String EJB_REF = IDao.EJB_REF_DOWNLOAD_CERTIFICAZIONE;
	
	/** */ public DownloadCertificazioneDao() { super(); }

	/** {@inheritDoc} */ @Override public String ejbRef() { return EJB_REF; }

	/** {@inheritDoc} */ @Override protected Class<IDownloadCertificazione> clazz() { return IDownloadCertificazione.class; }

	/** {@inheritDoc} */ @Override protected String refTable() { return DownloadCertificazione.class.getName(); }

	
	/** {@inheritDoc} */
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public IDownloadCertificazione legata(final long fkEsenzioniFasce) throws DaoException {
		final long start = System.currentTimeMillis();
		try{
			final String jpql = "SELECT t FROM "+refTable()+" t WHERE t.fkEsenzioniFasce = :fkEsenzioniFasce ORDER BY t.dataInserimento";
			
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("fkEsenzioniFasce", fkEsenzioniFasce);
			
			return get(jpql, param);
		} finally {
			PERFORMANCE.debug("{} ms - downloadCertificazioneDao.legata({}) ",(System.currentTimeMillis() - start),fkEsenzioniFasce);
		}
	}
}

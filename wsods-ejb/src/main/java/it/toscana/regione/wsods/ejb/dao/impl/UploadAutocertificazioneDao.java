/** */
package it.toscana.regione.wsods.ejb.dao.impl;

import it.toscana.regione.wsods.ejb.dao.abs.EntityDao;
import it.toscana.regione.wsods.ejb.dao.api.IDao;
import it.toscana.regione.wsods.ejb.dao.api.IUploadAutocertificazioneDao;
import it.toscana.regione.wsods.entity.jpa.api.IUploadAutocertificazione;
import it.toscana.regione.wsods.entity.jpa.impl.UploadAutocertificazione;
import it.toscana.regione.wsods.exception.DaoException;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author cciurli
 *
 */
@Stateless(name = IUploadAutocertificazioneDao.EJB_REF)
public class UploadAutocertificazioneDao extends EntityDao<IUploadAutocertificazione> implements IUploadAutocertificazioneDao,IDao<IUploadAutocertificazione> {


	private static final Logger PERFORMANCE = LoggerFactory.getLogger("WSODS-DAO-PERFORMANCE");

	/** fild serialVersionUID {@like long}*/
	private static final long	serialVersionUID	= 1514479627634014131L;
	
	private final static String EJB_REF = IDao.EJB_REF_UPLOAD_AUTOCERTIFICAZIONE;
	
	/**
	 * 
	 */
	public UploadAutocertificazioneDao() {
		super();
	}

	/** {@inheritDoc} */ @Override public String ejbRef() { return EJB_REF; }

	/** {@inheritDoc} */ @Override protected Class<IUploadAutocertificazione> clazz() { return IUploadAutocertificazione.class; }

	/** {@inheritDoc} */ @Override protected String refTable() { return UploadAutocertificazione.class.getName(); }
	
	
	/** {@inheritDoc} */
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public IUploadAutocertificazione legata(final long fkEsenzioniFasce) throws DaoException {
		final long start = System.currentTimeMillis();
		try{
			final String jpql = "SELECT t FROM "+refTable()+" t WHERE t.fkEsenzioniFasce = :fkEsenzioniFasce ORDER BY t.dataInserimento";
			
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("fkEsenzioniFasce", fkEsenzioniFasce);
			
			return get(jpql, param);
		} finally {
			PERFORMANCE.debug("{} ms - uploadAutocertificazioneDao.legata({}) ",(System.currentTimeMillis() - start),fkEsenzioniFasce);
		}
	}
}

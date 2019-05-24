/** */
package it.toscana.regione.wsods.ejb.dao.impl;

import it.toscana.regione.wsods.ejb.dao.api.IRCTCodBelfComuniDao;
import it.toscana.regione.wsods.entity.jpa.api.IRCTCodBelfComuni;
import it.toscana.regione.wsods.exception.DaoException;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author gorlando
 * 
 */
@Stateless(name = IRCTCodBelfComuniDao.EJB_REF)
public class RCTCodBelfComuniDao implements IRCTCodBelfComuniDao {

	private final Logger log = LoggerFactory.getLogger(RCTCodBelfComuniDao.class);

	private static final Logger PERFORMANCE = LoggerFactory.getLogger("WSODS-DAO-PERFORMANCE");

	/** fild serialVersionUID {@like long} */
	private static final long serialVersionUID = -9040104826533172823L;

	@PersistenceContext(unitName = "wsods")
	private EntityManager manager;

	/** */
	public RCTCodBelfComuniDao() {
		super();
	}

	/** {@inheritDoc} */
	@Override
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public String getCodiceIstatByCodBelfiore(String codBelfiore) throws DaoException {
		final long start = System.currentTimeMillis();
		try {

			final String jpql = "SELECT t.codIstat FROM " + IRCTCodBelfComuni.class.getName() + " t WHERE t.codBelfiore = :codBelfiore ORDER BY t.dataInizio DESC";
			final Query query = manager.createQuery(jpql);
			query.setParameter("codBelfiore", codBelfiore);
			final List<String> result = query.getResultList();
			log.debug("Si e' caricato " + result.size() + " record");
			if (result != null && !result.isEmpty()) {
				return result.get(0);
			}
			return null;
		} finally {
			PERFORMANCE.debug("{} ms - rctCodBelfComuniDao.getCodiceIstatByCodBelfiore({}) ", (System.currentTimeMillis() - start), codBelfiore);
		}
	}

}

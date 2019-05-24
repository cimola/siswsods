/** */
package it.toscana.regione.wsods.ejb.dao.impl;

import it.toscana.regione.wsods.ejb.dao.abs.EntityDao;
import it.toscana.regione.wsods.ejb.dao.api.IAutocertificazioneScartateDao;
import it.toscana.regione.wsods.ejb.dao.api.IDao;
import it.toscana.regione.wsods.entity.jpa.api.IAutocertificazioneScartate;
import it.toscana.regione.wsods.entity.jpa.api.IAutocertificazioneTmp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
@Stateless(name = IAutocertificazioneScartateDao.EJB_REF)
public class AutocertificazioneScartateDao extends EntityDao<IAutocertificazioneScartate> implements IAutocertificazioneScartateDao, IDao<IAutocertificazioneScartate> {

	private static final Logger PERFORMANCE = LoggerFactory.getLogger("WSODS-DAO-PERFORMANCE");

	/** fild serialVersionUID {@like long}*/
	private static final long	serialVersionUID	= -690586674187416510L;
	
	
	
	/** */ public AutocertificazioneScartateDao() { super(); }

	/** {@inheritDoc} */ @Override public String ejbRef() { return EJB_REF; }

	/** {@inheritDoc} */ @Override protected Class<IAutocertificazioneScartate> clazz() { return IAutocertificazioneScartate.class; }

	/** {@inheritDoc} */ @Override protected String refTable() { return IAutocertificazioneScartate.class.getName(); }
	
	/** {@inheritDoc} */ 
	@Override
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public List<IAutocertificazioneScartate> getForMove(final int size) {
		final long start = System.currentTimeMillis();
		try{
			final String jpql = "SELECT t FROM AutocertificazioneScartate t WHERE t.rielabora = :rielabora ORDER BY t.dataInserimento ";
			final Map<String,Object> param = new HashMap<String, Object>();
			param.put("rielabora", new Long(1L));
			
			final List<IAutocertificazioneScartate> result = get(size, jpql, param);
			
			return result;
		} finally {
			PERFORMANCE.debug("{} ms - autocertificazioneScartateDao.getForMove({}) ",(System.currentTimeMillis() - start),size);
		}
	}

	/** {@inheritDoc} */ 
	@Override
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public List<IAutocertificazioneScartate> getForTitoloNull(final long numeroTimer, final long discriminante, final int size) {
		final long start = System.currentTimeMillis();
		try{
			final String jpql = 
				  "SELECT t "
				+ "FROM AutocertificazioneScartate t "
				+ "WHERE 1=1 "
				+ "AND mod(t.id,:numeroTimer) = :discriminante "
				+ "AND t.titolo is null "
				+ "AND t.numeroElaborazioni = :numeroElaborazioni "
				+ "AND t.rielabora = :rielabora "
				+ "ORDER BY t.dataAggiornamento ";
			final Map<String,Object> param = new HashMap<String, Object>();
			param.put("numeroTimer", new Integer(Long.toString(numeroTimer)));
			param.put("discriminante", new Integer(Long.toString(discriminante)));
			param.put("numeroElaborazioni", IAutocertificazioneTmp.NUMERO_ELABORAZIONI_TITOLO_NULLO);
			param.put("rielabora", new Long(0L));
			
			final List<IAutocertificazioneScartate> result = get(size, jpql, param);
			
			return result;
		} finally {
			PERFORMANCE.debug("{} ms - autocertificazioneScartateDao.getForMove({}) ",(System.currentTimeMillis() - start),size);
		}
	}
	/** {@inheritDoc} */ 
	@Override
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public List<Long> getForGianov4(final int size) {
		final long start = System.currentTimeMillis();
		try{
			final String jpql = "SELECT t FROM AutocertificazioneScartate t WHERE t.numeroElaborazioni = :numeroElaborazioni AND t.rielabora = :rielabora ORDER BY t.dataInserimento ";
			final Map<String,Object> param = new HashMap<String, Object>();
			param.put("numeroElaborazioni", IAutocertificazioneTmp.NUMERO_ELABORAZIONI_SOGGETTI_PARTECIPANTI_NON_RISOLTI_O_CON_TRATTI_PARZIALI_IN_ANAGRAFE);
			param.put("rielabora", new Long(0L));
			
			final List<IAutocertificazioneScartate> resultTmp = get(size, jpql, param);
			
			final List<Long> result = new ArrayList<Long>();
			
			if (resultTmp != null && !resultTmp.isEmpty()) {
				for (IAutocertificazioneScartate a : resultTmp) {
					result.add(a.getId());
				}
			}
			
			return result;
		} finally {
			PERFORMANCE.debug("{} ms - autocertificazioneScartateDao.getForGianov4({}) ",(System.currentTimeMillis() - start),size);
		}
	}

}

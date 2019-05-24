/** */
package it.toscana.regione.wsods.ejb.dao.impl;

import it.toscana.regione.wsods.ejb.dao.abs.EntityDao;
import it.toscana.regione.wsods.ejb.dao.api.ICertificazioneTmpDao;
import it.toscana.regione.wsods.ejb.dao.api.IDao;
import it.toscana.regione.wsods.entity.jpa.api.ICertificazioneTmp;
import it.toscana.regione.wsods.exception.DaoException;
import it.toscana.regione.wsods.type.Code;

import java.sql.Timestamp;
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
 * @author vmaltese
 *
 */
@Stateless(name = ICertificazioneTmpDao.EJB_REF)
public class CertificazioneTmpDao extends EntityDao<ICertificazioneTmp> implements ICertificazioneTmpDao, IDao<ICertificazioneTmp> {
	
	private final Logger log = LoggerFactory.getLogger(CertificazioneTmpDao.class);

	private static final Logger PERFORMANCE = LoggerFactory.getLogger("WSODS-DAO-PERFORMANCE");
	
	/** fild serialVersionUID {@like long}*/
	private static final long serialVersionUID = -2985497475338315006L;
	
	
	/** */ public CertificazioneTmpDao() { super(); }

	/** {@inheritDoc} */ @Override public String ejbRef() { return EJB_REF; }

	/** {@inheritDoc} */ @Override protected Class<ICertificazioneTmp> clazz() { return ICertificazioneTmp.class; }

	/** {@inheritDoc} */ @Override protected String refTable() { return ICertificazioneTmp.class.getName(); }

	/** {@inheritDoc} */
	@Override
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public List<ICertificazioneTmp> loadFromDiscriminante(int size, final long numeroTimer, long discriminante) throws DaoException{
		final long start = System.currentTimeMillis();
		try{
		
			final String jpql = "SELECT t FROM "+refTable()+" t ORDER BY t.dataAggiornamento";
			final Map<String, Object> param = new HashMap<String, Object>();
			final int nT = new Long(numeroTimer).intValue();
			final  List<ICertificazioneTmp> rowList = get((size*nT), jpql, param);
			final  List<ICertificazioneTmp> result = new ArrayList<ICertificazioneTmp>();
			for(ICertificazioneTmp  entity : rowList){
				if(entity!=null && entity.getId()% numeroTimer == discriminante){
					result.add(entity);
				}
			}
			log.debug("Si e' caricato "+rowList.size()+" record di cui se ne e' selezionati "+result.size()+"");
			return result;
		} finally {
			PERFORMANCE.debug("{} ms - certificazioneTmpDao.loadFromDiscriminante({},{},{}) ",(System.currentTimeMillis() - start),size,numeroTimer,discriminante);
		}
	}

	/** {@inheritDoc} */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void incrementaElaborazioi(final Long id) throws DaoException {
		if(id == null ){throw new DaoException(Code.NULL_INATTESSO,"passato un id null");}
		final long start = System.currentTimeMillis();
		try{
			final ICertificazioneTmp certificazione = get(id);
			certificazione.setNumeroElaborazioni(certificazione.getNumeroElaborazioni()+1L);
			certificazione.setDataAggiornamento(new Timestamp(System.currentTimeMillis()));
		} finally {
			PERFORMANCE.debug("{} ms - certificazioneTmpDao.incrementaElaborazioi({}) ",(System.currentTimeMillis() - start),id);
		}
	}
	
	/** {@inheritDoc} */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void setElaborazioi(final Long id, final long numeroElaborazioni) throws DaoException {
		if(id == null ){throw new DaoException(Code.NULL_INATTESSO,"passato un id null");}
		final long start = System.currentTimeMillis();
		try{
			final ICertificazioneTmp certificazione = get(id);
			certificazione.setNumeroElaborazioni(numeroElaborazioni);
			certificazione.setDataAggiornamento(new Timestamp(System.currentTimeMillis()));
		} finally {
			PERFORMANCE.debug("{} ms - certificazioneTmpDao.setElaborazioi({},{}) ",(System.currentTimeMillis() - start),id,numeroElaborazioni);
		}
	}

	/** {@inheritDoc} */
	@Override @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public boolean notExist(final ICertificazioneTmp entity) {
		if(entity == null ){throw new DaoException(Code.NULL_INATTESSO,"passato un ICertificazioneTmp null");}
		final long start = System.currentTimeMillis();
		try{
			final StringBuffer sb = new StringBuffer("");
			final Map<String, Object> param = new HashMap<String, Object>();
			
			sb.append("SELECT t FROM ").append(refTable()).append(" t WHERE 1=1");
			
			paramConfronto(sb, param,"annoEsenzione", entity.getAnnoEsenzione());		
			paramConfronto(sb, param,"cfSogEsente", entity.getCfSogEsente());
			paramConfronto(sb, param,"codiceEsenzione", entity.getCodiceEsenzione());
			paramConfronto(sb, param,"dataInizioValid", entity.getDataInizioValid());
			paramConfronto(sb, param,"dataFineValid", entity.getDataFineValid());
			paramConfronto(sb, param,"dataFineValidOld", entity.getDataFineValidOld());			
			paramConfronto(sb, param,"sorgente", entity.getSorgente());
			
			final String jpql = sb.toString();
			final List<ICertificazioneTmp> result =  get(0,jpql, param);
			
			return result==null || result.isEmpty();
		} finally {
			PERFORMANCE.debug("{} ms - certificazioneTmpDao.notExist(entityId:{}) ",(System.currentTimeMillis() - start),entity.getId());
		}
	}
	
	/** {@inheritDoc} */
	@Override @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public long lastDataOrdinamento(){
		final long start = System.currentTimeMillis();
		try{
			final StringBuffer sb = new StringBuffer("");
			final Map<String, Object> param = new HashMap<String, Object>();
			
			sb.append("SELECT t FROM ").append(refTable()).append(" t ORDER BY dataOrdinamento");
			
			final String jpql = sb.toString();
			final List<ICertificazioneTmp> result =  get(1,jpql, param);
			if(result== null || result.isEmpty()){return Long.MAX_VALUE;}
			final ICertificazioneTmp autocertificazioneTmp = result.get(0);
			if(autocertificazioneTmp == null){ return Long.MAX_VALUE;}
			final Timestamp time = autocertificazioneTmp.getDataOrdinamento();
			if(time == null){ return Long.MAX_VALUE;}
			return time.getTime();
		} finally {
			PERFORMANCE.debug("{} ms - certificazioneTmpDao.lastDataOrdinamento() ",(System.currentTimeMillis() - start));
		}
		
	}

}

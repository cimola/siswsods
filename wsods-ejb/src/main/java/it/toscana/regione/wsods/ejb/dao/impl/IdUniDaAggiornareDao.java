/** */
package it.toscana.regione.wsods.ejb.dao.impl;

import it.toscana.regione.wsods.ejb.dao.abs.EntityDao;
import it.toscana.regione.wsods.ejb.dao.api.IDao;
import it.toscana.regione.wsods.ejb.dao.api.IIdUniDaAggiornareDao;
import it.toscana.regione.wsods.entity.jpa.api.IIdUniDaAggiornare;
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
 * @author cciurli
 *
 */
@Stateless(name = IIdUniDaAggiornareDao.EJB_REF)
public class IdUniDaAggiornareDao extends EntityDao<IIdUniDaAggiornare> implements IIdUniDaAggiornareDao, IDao<IIdUniDaAggiornare> {
	
	private static final long serialVersionUID = 4203824609909873713L;

	private final Logger log = LoggerFactory.getLogger(IdUniDaAggiornareDao.class);

	private static final Logger PERFORMANCE = LoggerFactory.getLogger("WSODS-DAO-PERFORMANCE");
	
	
	
	/** */ public IdUniDaAggiornareDao() { super(); }

	/** {@inheritDoc} */ @Override public String ejbRef() { return EJB_REF; }

	/** {@inheritDoc} */ @Override protected Class<IIdUniDaAggiornare> clazz() { return IIdUniDaAggiornare.class; }

	/** {@inheritDoc} */ @Override protected String refTable() { return IIdUniDaAggiornare.class.getName(); }


	/** {@inheritDoc} */
	@Override @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public boolean notExist(final IIdUniDaAggiornare entity) {
		if(entity == null ){throw new DaoException(Code.NULL_INATTESSO,"passata un IIdUniDaAggiornare null");}
		log.debug("verifico esistenza del'entity");
		final long start = System.currentTimeMillis();
		try{
			final StringBuffer sb = new StringBuffer("");
			final Map<String, Object> param = new HashMap<String, Object>();
			
			sb.append("SELECT t FROM ").append(refTable()).append(" t WHERE 1=1");
			
	
			paramConfronto(sb, param,"dataUltimoAggiornamento", entity.getDataUltimoAggiornamento());
			paramConfronto(sb, param,"slave", entity.getSlave());
			paramConfronto(sb, param,"master", entity.getMaster());
			
	
			final String jpql = sb.toString();
			final List<IIdUniDaAggiornare> result =  get(0,jpql, param);
			
			return result==null || result.isEmpty();
		} finally {
			PERFORMANCE.debug("{} ms - idUniDaAggiornareDao.notExist(dataUltimoAggiornamento:{},slave:{},master:{}) ",(System.currentTimeMillis() - start),entity.getDataUltimoAggiornamento(),entity.getSlave(),entity.getMaster());
		}
	}

	/** {@inheritDoc} */
	@Override @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Timestamp getLast() {
		log.debug("recuporo l'ultimo record scaricato");
		final long start = System.currentTimeMillis();
		try{
			final Map<String, Object> param = new HashMap<String, Object>();	
			
			final String jpql = "SELECT t FROM "+refTable()+" t WHERE 1=1 ORDER BY t.dataUltimoAggiornamento desc";
			
			final IIdUniDaAggiornare result =  getFirst(jpql, param);
			
			if(result!=null){
				return result.getDataUltimoAggiornamento();
			}
			
		} finally {
			PERFORMANCE.debug("{} ms - idUniDaAggiornareDao.getLast() ",(System.currentTimeMillis() - start));
		}
		return null;
	}

	@Override @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Long> getFirstLinksToUpdate(final int size) {
		log.debug("recuporo gli id dei record di pertinenza di questo timer");
		final long start = System.currentTimeMillis();
		try{
		
			final String jpql = "SELECT t FROM "+refTable()+" t WHERE t.statoUtilizzo = :statoUtilizzo ORDER BY t.dataUltimoAggiornamento";
			final Map<String, Object> param = new HashMap<String, Object>();
			param.put("statoUtilizzo", IIdUniDaAggiornare.STATO_UTILIZZO_MAI_USATO);
			
			final  List<IIdUniDaAggiornare> rowList = get(size, jpql, param);
			final  List<Long> result = new ArrayList<Long>();
			for(IIdUniDaAggiornare  entity : rowList){
				if(entity!=null){
					result.add(entity.getId());
				}
			}
			log.debug("Si e' caricato "+rowList.size()+" record di cui se ne e' selezionati "+result.size()+"");
			return result;
		} finally {
			PERFORMANCE.debug("{} ms - idUniDaAggiornareDao.loadFromDiscriminante({}) ",(System.currentTimeMillis() - start),size);
		}
	}
	

}

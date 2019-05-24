/** */
package it.toscana.regione.wsods.ejb.dao.abs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import it.toscana.regione.wsods.ejb.dao.api.IDao;
import it.toscana.regione.wsods.entity.jpa.api.IDbEntity;
import it.toscana.regione.wsods.exception.DaoException;
import it.toscana.regione.wsods.type.Code;


/**
 * @author cciurli
 *
 */
public abstract class EntityDao<E extends IDbEntity> implements IDao<E> {

	/** fild serialVersionUID {@like long}*/
	private static final long	serialVersionUID	= 8529151649423456266L;
	
	@PersistenceContext(unitName="wsods")
	private EntityManager manager;

	protected abstract Class<E> clazz();
	protected abstract String refTable();
	
	
	/**
	 * 
	 */
	public EntityDao() {
		super();
	}

	public abstract String ejbRef();

	/** {@inheritDoc} */
	@Override @TransactionAttribute(TransactionAttributeType.MANDATORY)
	public void  flush()  throws DaoException {
		try { 
			manager.flush();
		}catch(Exception e){
			throw new DaoException(Code.DAO_EXECUTE_UPDATE,"problemi durante la flush",e);
		}
	}

	/** {@inheritDoc} */
	@Override @TransactionAttribute(TransactionAttributeType.MANDATORY)
	public void insert(final E entity, final boolean flush)  throws DaoException {
		try { 
			manager.persist(entity);
			if(flush){
				flush();
			}
		}catch(Exception e){
			throw new DaoException(Code.DAO_EXECUTE_UPDATE,"problemi durante la insert",e);
		}
	}
	
	/** {@inheritDoc} */
	@Override @TransactionAttribute(TransactionAttributeType.MANDATORY)
	public int update(final String jpql, final Map<String, Object> param) throws DaoException {
		int result = 0;
		final Query query = genQuery(0,jpql);
		setParameters(query,param);
		try {
			result = query.executeUpdate();
		}catch(Exception e){
			final StringBuffer sb = new StringBuffer("\n - ");
			sb.append("query jpql:\n\t").append(jpql).append("\n -  param:\n");
			for(String key : param.keySet()){
				sb.append("\t. ").append(key).append(" - ").append(param.get(key).toString()).append("\n");
			}
			throw new DaoException(Code.DAO_EXECUTE_UPDATE,sb.toString(),e);
		}
		return result;
	}
	
	/** {@inheritDoc} */
	@Override @TransactionAttribute(TransactionAttributeType.MANDATORY)
	public int remove(final String jpql, final Map<String, Object> whereParam) throws DaoException {
		return update(jpql, whereParam);
	}


	/** {@inheritDoc} */
	@Override @TransactionAttribute(TransactionAttributeType.MANDATORY)
	public void remove(final long id) throws DaoException {
		final String jpql = "DELETE FROM "+refTable()+" t WHERE t.id = :id";
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("id", id);
		remove(jpql, param);
	}
	
	/** {@inheritDoc} */
	@Override @TransactionAttribute(TransactionAttributeType.MANDATORY)
	public void remove(final E entity) throws DaoException {
		if(entity != null){
			remove(entity.getId());
		}		
	}

	/** {@inheritDoc} */
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public E get(final long id) throws DaoException {
		final String jpql = "SELECT t FROM "+refTable()+" t WHERE t.id = :id";
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("id", id);
		
		return get(jpql,param);
	}

	/** {@inheritDoc} */
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<E> get(final int max, final String jpql, final Map<String, Object> param) throws DaoException {
	
		final Query query = genQuery(max,jpql);
		setParameters(query,param);
		
		return getResult(clazz(), query);
	}
	/** {@inheritDoc} */
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public E get(final String jpql, final Map<String, Object> param) throws DaoException {
		Object result = null;
		final Query query = genQuery(0,jpql);
		setParameters(query,param);
		List<?> rowResult = query.getResultList();
		if(rowResult != null && rowResult.size()>1){
			StringBuffer sb = new StringBuffer("");
			sb.append("Quando ci aspettavamo al massimo un record ne abbiamo trovati [").append(rowResult.size()).append("]\n");
			sb.append("\tquery: [").append(jpql).append("]\n");
			sb.append("\tparam:\n");
			if(param!=null && param.size()>0){
				Set<String> keys =param.keySet();
				for(String key : keys){
					sb.append("\t\t key: [").append(key).append("] ").append("value: [").append(param.get(key)).append("]\n");
				}
			}
			
			throw new DaoException(Code.DAO_RISULTATO_INATTESO, sb.toString());
		} else if(rowResult != null && rowResult.size()==1){
			result = rowResult.get(0);
		}
		
		
		if(result !=null && clazz().isInstance(result)){
			return clazz().cast(result);
		} else {
			return null;
		}
	}
	/** {@inheritDoc} */
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public E getFirst(final String jpql, final Map<String, Object> param) throws DaoException {
		final Object result;
		final Query query = genQuery(1,jpql);
		setParameters(query,param);
		List<?> rowResult = query.getResultList();
		if(rowResult != null && rowResult.size()==1){
			result = rowResult.get(0);
		} else {
			result = null;
		}
		
		
		if(result !=null && clazz().isInstance(result)){
			return clazz().cast(result);
		} else {
			return null;
		}
	}
	
	
	protected EntityManager getEntityManager() throws DaoException{
		if(manager==null){ throw new DaoException(Code.DAO_ENTITY_MANAGER);}
		return manager;
		
	}
	

	protected Query genQuery(final int max, final String jpql) throws DaoException{
		if(jpql==null){ throw new DaoException(Code.DAO_PARAMETRO);}
		final Query query;
		try{
			query = getEntityManager().createQuery(jpql);
		}catch (Exception e){ 
			throw new DaoException(Code.DAO_CREATE_QUERY,e);
		}
		if(query == null){ throw new DaoException(Code.DAO_CREATE_QUERY); }
		
		if(max > 0){
			query.setMaxResults(max);
		}
		
		return query;
	}
	

	protected void setParameters(final Query query, final Map<String, Object> params){
		if(params != null && params.size()>0){
			Set<String> keys = params.keySet();
			for(String key : keys){
				query.setParameter(key, params.get(key));
			}
		}
	}

	protected List<E> getResult(final Class<E> clazz, final Query query){
		final List<?> rowResult = query.getResultList();
		final List<E> result = new ArrayList<E>();
		if(rowResult != null && rowResult.size()>0){
			for(Object val : rowResult){
				if(clazz.isInstance(val)){
					result.add(clazz.cast(val));
				}
			}
		}
		return result;
	}
	

	
	protected final static void paramConfronto(final StringBuffer sb,final Map<String, Object> param, final String fildName, final Object fildValue){
		if(fildValue !=null){
			sb.append(" AND t.").append(fildName).append(" = :").append(fildName).append(" ");
			param.put(fildName, fildValue);
		} else {
			sb.append(" AND t.").append(fildName).append(" is null ");
		}
	}

}

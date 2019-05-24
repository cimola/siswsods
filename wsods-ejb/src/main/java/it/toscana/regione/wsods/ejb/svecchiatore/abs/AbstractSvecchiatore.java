/** */
package it.toscana.regione.wsods.ejb.svecchiatore.abs;

import it.toscana.regione.wsods.ejb.dao.api.IDao;
import it.toscana.regione.wsods.ejb.svecchiatore.api.ISvecchiatore;
import it.toscana.regione.wsods.entity.jpa.api.IDbEntity;
import it.toscana.regione.wsods.singleton.Conf;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author cciurli
 *
 */
public abstract class AbstractSvecchiatore < E extends IDbEntity > implements ISvecchiatore< E > {
	

	/** fild serialVersionUID {@like long}*/
	private static final long	serialVersionUID	= -3534666484546667111L;
	
	private static final Logger LOG = LoggerFactory.getLogger(AbstractSvecchiatore.class);

	protected final  static int FINESTRA = 10;
	
	protected abstract IDao<E> getDao();
	protected abstract Class<E> getTable();
	protected abstract String getOtherWhere();
	protected abstract int recordMinimiMantenuti();
	protected abstract long deltaSvecchiatura();

	/**
	 * 
	 */
	public AbstractSvecchiatore() { }

	/** {@inheritDoc} */
	@Override
	public int svecchia() {
		
		final long start = System.currentTimeMillis();
		int removed = 0;
		try{
			final IDao<E> dao = getDao();
			
			final String jpql = "DELETE FROM "+getTable().getName()+" t WHERE t.dataAggiornamento < :dataAggiornamento "+getOtherWhere();
			
			final Map<String,Object> whereParam = new HashMap<String, Object>();
			
			final Timestamp dataAggiornamento = ultimaModificaUtile();
			
			whereParam.put("dataAggiornamento",dataAggiornamento);
			
			removed = dao.remove(jpql, whereParam);
			
			return removed;
		} finally{
			LOG.debug("[PERFORMANCE] - sono stati rimossi ["+removed+"] record dalla ["+getTable().getName()+"] in ("+(System.currentTimeMillis()-start)+")ms");
		}
	}

	protected Timestamp ultimaModificaUtile() {
		final int finestra;
		final long deltaSvecchiatura = System.currentTimeMillis()-deltaSvecchiatura();
		if(recordMinimiMantenuti()>0){
			finestra = Conf.getSvecchiatoreRecordMinimiMantenuti();
		} else {
			finestra = FINESTRA;
		}
		
		final String jpql = "SELECT t FROM "+getTable().getName()+" t WHERE 1=1  "+getOtherWhere()+" ORDER BY t.dataAggiornamento DESC";
		
		final Map<String,Object> whereParam = new HashMap<String, Object>();
		
		final List< E > result = getDao().get(finestra,jpql, whereParam);
		final long ultimaModificaUtile;
		if(result != null && result.size()>=finestra){
			int pos = result.size()-1;
			if(pos<0){pos = 0;}
			if(result.get(pos)!=null && result.get(pos).getDataAggiornamento() !=null){
				ultimaModificaUtile = result.get(pos).getDataAggiornamento().getTime();
			} else {
				ultimaModificaUtile = 0 ;
			}
			
		} else {
			ultimaModificaUtile = 0;
		}
		
		
		return new Timestamp(ultimaModificaUtile<deltaSvecchiatura?ultimaModificaUtile:deltaSvecchiatura);
	}
	
}

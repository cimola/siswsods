package it.toscana.regione.wsods.ejb.dao.impl;

import it.toscana.regione.wsods.ejb.dao.abs.EntityDao;
import it.toscana.regione.wsods.ejb.dao.api.IDao;
import it.toscana.regione.wsods.ejb.dao.api.IRecuperoDownloadSogeiDao;
import it.toscana.regione.wsods.entity.jpa.api.IRecuperoDownloadSogei;
import it.toscana.regione.wsods.entity.jpa.impl.RecuperoDownloadSogei;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Stateless(name = IRecuperoDownloadSogeiDao.EJB_REF)
public class RecuperoDownloadSogeiDao extends EntityDao<IRecuperoDownloadSogei> implements IRecuperoDownloadSogeiDao,IDao<IRecuperoDownloadSogei> {
	

	private static final Logger PERFORMANCE = LoggerFactory.getLogger("WSODS-DAO-PERFORMANCE");
	
	private static final long serialVersionUID = 6948639688995133725L;

	private final static String EJB_REF = IDao.EJB_REF_RECUPERO_DOWNLOAD_SOGEI;

	public RecuperoDownloadSogeiDao() { }
	
	@Override protected Class<IRecuperoDownloadSogei> clazz() { return IRecuperoDownloadSogei.class; }
	
	@Override protected String refTable() { return RecuperoDownloadSogei.class.getName(); }
	
	@Override public String ejbRef() { return EJB_REF; }
	
	
	@Override @TransactionAttribute(TransactionAttributeType.MANDATORY)
	public void aggiornaConfigurazione(final String min,final String max, final String tipo) {
		final long start = System.currentTimeMillis();
		try{
			final String jpql = "SELECT t FROM " + refTable() + " t WHERE t.tipoAutocertificazione = :tipoAutocertificazione ORDER BY t.dataAggiornamento DESC";
			
			final Map<String,Object> param = new HashMap<String, Object>(); 
			param.put("tipoAutocertificazione", tipo);
			
			final IRecuperoDownloadSogei entity = get(jpql, param);
			
			if (entity == null){
				insert(new RecuperoDownloadSogei(min,max,tipo),true);
			} else {
				entity.setDataOraLimiteStart(min);
				entity.setDataOraLimiteStop(max);
				entity.setDataAggiornamento(new Timestamp(System.currentTimeMillis()));
			}
			flush();
		} finally {
			PERFORMANCE.debug("{} ms - recuperoDownloadSogeiDao.aggiornaConfigurazione({},{},{}) ",(System.currentTimeMillis() - start),min,max,tipo);
		}
	}


	@Override @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<IRecuperoDownloadSogei> recuperaConfigurazioni(){
		final long start = System.currentTimeMillis();
		try{
			 
			final String jpql = "SELECT t FROM " + refTable() + " t ORDER BY t.dataAggiornamento DESC";
			
			final Map<String,Object> param = new HashMap<String, Object>(); 
			
			return  get(0,jpql, param);

		} finally {
			PERFORMANCE.debug("{} ms - recuperoDownloadSogeiDao.recuperaConfigurazioni() ",(System.currentTimeMillis() - start));
		}
	}
	
}

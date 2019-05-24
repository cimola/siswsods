package it.toscana.regione.wsods.ejb.dao.abs;

import it.toscana.regione.wsods.ejb.dao.api.IAutocertificazioneEtlDao;
import it.toscana.regione.wsods.entity.jpa.api.IAutocertificazioneEtl;
import it.toscana.regione.wsods.exception.DaoException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AutocertificazioniEtlDao<E extends IAutocertificazioneEtl > extends EntityDao< E >  implements IAutocertificazioneEtlDao< E >  {

	private static final long serialVersionUID = -7135400825391099549L;

	private static final Logger PERFORMANCE = LoggerFactory.getLogger("WSODS-DAO-PERFORMANCE");
	private static final Logger LOG = LoggerFactory.getLogger(AutocertificazioniEtlDao.class);
	
	@Override
	protected abstract Class<E> clazz();

	@Override
	protected abstract String refTable();

	@Override
	public abstract String ejbRef();
	
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public List<E> loadFromDiscriminante(final int maxRow,final long maxElaborazioni, final long numeroTimer, final long discriminante) throws DaoException {
		final long start = System.currentTimeMillis();
		try{
		
			final String jpql = "SELECT t "
					+ "FROM "+refTable()+" t "
					+ "WHERE 1=1 "
					+ "AND mod(t.id,:numeroTimer) = :discriminante "
					+ "AND t.numeroElaborazioni < :maxElaborazioni "
					+ "AND t.statoElaborazione in (:nuova,:rielaborabile) "
					+ "ORDER BY t.statoElaborazione, t.dataAggiornamento";
			
			final Map<String, Object> param = new HashMap<String, Object>();
			param.put("numeroTimer", new Integer(Long.toString(numeroTimer)));
			param.put("discriminante", new Integer(Long.toString(discriminante)));
			param.put("maxElaborazioni", maxElaborazioni);
			param.put("nuova", IAutocertificazioneEtl.STATO_INSERITO);
			param.put("rielaborabile", IAutocertificazioneEtl.STATO_INVIATA_CON_ESITO_NEGATIVO_REINVIABILE);
			
			final List<E> result = get(maxRow, jpql, param);

			
			LOG.debug("Si e' caricato "+result.size()+" record ");
			return result;
		} finally {
			PERFORMANCE.debug("{} ms - AutocertificazioniEtlDao.loadFromDiscriminante({},{},{},{}) ",(System.currentTimeMillis() - start),maxRow,maxElaborazioni,numeroTimer,discriminante);
		}

	}
	

}

/** */
package it.toscana.regione.wsods.ejb.dao.impl;

import it.toscana.regione.wsods.ejb.dao.abs.EntityDao;
import it.toscana.regione.wsods.ejb.dao.api.IDao;
import it.toscana.regione.wsods.ejb.dao.api.IRicevutaDownloadSogeiDao;
import it.toscana.regione.wsods.entity.jpa.api.IRecuperoDownloadSogei;
import it.toscana.regione.wsods.entity.jpa.api.IRicevutaDownloadSogei;
import it.toscana.regione.wsods.entity.jpa.impl.RicevutaDownloadSogei;
import it.toscana.regione.wsods.exception.DaoException;
import it.toscana.regione.wsods.type.Code;

import java.sql.Timestamp;
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
@Stateless(name = IRicevutaDownloadSogeiDao.EJB_REF)
public class RicevutaDownloadSogeiDao extends EntityDao<IRicevutaDownloadSogei> implements IRicevutaDownloadSogeiDao, IDao<IRicevutaDownloadSogei> {


	private static final Logger PERFORMANCE = LoggerFactory.getLogger("WSODS-DAO-PERFORMANCE");
	
	/** fild serialVersionUID {@like long}*/
	private static final long serialVersionUID = -305688947053362948L;
	
	private final static String EJB_REF = IDao.EJB_REF_RICEVUTA_DOWNLOAD_SOGEI;
	
	
	/**
	 * 
	 */
	public RicevutaDownloadSogeiDao() {
		super();
	}
	
	
	/** {@inheritDoc} */
	@Override
	public String ejbRef() {
		return EJB_REF;
	}
	
	
	/** {@inheritDoc} */
	@Override
	protected Class<IRicevutaDownloadSogei> clazz() {
		return IRicevutaDownloadSogei.class;
	}
	
	
	/** {@inheritDoc} */
	@Override
	protected String refTable() {
		return RicevutaDownloadSogei.class.getName();
	}
	
	
	/** {@inheritDoc} */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public String getLastDataOraLimite(final String tipoRichiesta, final String tipologiaRicevuta) throws DaoException {
		if(tipoRichiesta == null ){throw new DaoException(Code.NULL_INATTESSO,"passato un tipoRichiesta null");}
		final long start = System.currentTimeMillis();
		try{
			final String jpql = "SELECT t FROM " + refTable() + " t WHERE t.esito = :esito AND t.tipoAutocertificazione = :tipoAutocertificazione AND t.tipologiaRicevuta = :tipologiaRicevuta ORDER BY t.dataInserimento DESC";
			final Map<String, Object> param = new HashMap<String, Object>();
			if(IRecuperoDownloadSogei.TIPO_CERTIFICAZIONE.equals(tipoRichiesta)) {
			
				param.put("esito", String.valueOf(Code.SOGEI_CERTIFICAZIONI_ELABORAZIONE_CORRETTAMENTE_EFFETTUATA.code));
			} else {
			
				param.put("esito", String.valueOf(Code.SOGEI_DOWNLOAD_ELABORAZIONE_CORRETTAMENTE_EFFETTUATA.code));
			}
			param.put("tipoAutocertificazione", tipoRichiesta);
			param.put("tipologiaRicevuta", tipologiaRicevuta);
			final List<IRicevutaDownloadSogei> resultList = get(1, jpql, param);
			
			if (resultList != null && resultList.size() == 1) {
				final IRicevutaDownloadSogei result = resultList.get(0);
				if (result != null) { return result.getDataOraLimite(); }
			}
			
			return null;
		} finally {
			PERFORMANCE.debug("{} ms - ricevutaDownloadSogeiDao.getLastDataOraLimite({}) ",(System.currentTimeMillis() - start),tipoRichiesta);
		}
		
	}
	
	/** {@inheritDoc} */
	@Override
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public IRicevutaDownloadSogei getDaElaborare(final long statoElaborazione, final List<String> tipiEsclusivi) throws DaoException {
		final long start = System.currentTimeMillis();
		try{
			final String jpql = "SELECT t FROM " + refTable() + " t WHERE t.statoElaborazione = :statoElaborazione AND t.tipoAutocertificazione IN (:tipiEsclusivi) ORDER BY t.dataInserimento";
			final Map<String, Object> param = new HashMap<String, Object>();
			param.put("statoElaborazione", statoElaborazione);
			param.put("tipiEsclusivi", tipiEsclusivi);
			final List<IRicevutaDownloadSogei> resultList = get(1, jpql, param);
				
			final IRicevutaDownloadSogei result;
			
			if (resultList != null && resultList.size() == 1) {
				result = resultList.get(0);
			} else {
				result = null;
			}
			return result;
		} finally {
			PERFORMANCE.debug("{} ms - ricevutaDownloadSogeiDao.getDaElaborare({}) ",(System.currentTimeMillis() - start),statoElaborazione);
		}
	}
	
	/** {@inheritDoc} */
	@Override
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public void aggiornaElaborati(final long id, final boolean inserito) throws DaoException {
		final long start = System.currentTimeMillis();
		try{
			if(inserito){
				final IRicevutaDownloadSogei entity = get(id);
				entity.setNumeroRecordNuovi(entity.getNumeroRecordNuovi()+1L);
				entity.setDataAggiornamento(new Timestamp(System.currentTimeMillis()));
			}
		} finally {
			PERFORMANCE.debug("{} ms - ricevutaDownloadSogeiDao.aggiornaElaborati({},{}) ",(System.currentTimeMillis() - start),id,inserito);
		}
	}


	@Override
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public void insertStartRecupero(final String tipoRichiesta, final String dataOraLimite) throws DaoException {
		final IRicevutaDownloadSogei entity = new RicevutaDownloadSogei();
		
		entity.setDataOraLimite(dataOraLimite);
		entity.setTipoAutocertificazione(tipoRichiesta);
		entity.setTipologiaRicevuta(IRicevutaDownloadSogei.TIPOLOGIA_RICEVUTA_RECUPERO);
		entity.setEsito(String.valueOf(Code.SOGEI_DOWNLOAD_ELABORAZIONE_CORRETTAMENTE_EFFETTUATA.code));
		if(IRecuperoDownloadSogei.TIPO_CERTIFICAZIONE.equals(tipoRichiesta)) {
			entity.setEsito(String.valueOf(Code.SOGEI_CERTIFICAZIONI_ELABORAZIONE_CORRETTAMENTE_EFFETTUATA.code));
		}
		entity.setNumeroRecordTrovati(0L);
		entity.setNumeroRecordElaborati(0L);
		entity.setNumeroRecordNuovi(0L);
		entity.setStatoElaborazione(IRicevutaDownloadSogei.STATO_ELABORAZIONE_RECORD_FAKE);;
		insert(entity, true);
		
	}
	

	@Override
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public void insertSkipCorrente(final String tipoRichiesta, final String dataOraLimite) throws DaoException {
		final IRicevutaDownloadSogei entity = new RicevutaDownloadSogei();
		
		entity.setDataOraLimite(dataOraLimite);
		entity.setTipoAutocertificazione(tipoRichiesta);
		entity.setTipologiaRicevuta(IRicevutaDownloadSogei.TIPOLOGIA_RICEVUTA_CORRENTE);
		entity.setEsito(String.valueOf(Code.SOGEI_DOWNLOAD_ELABORAZIONE_CORRETTAMENTE_EFFETTUATA.code));
		if(IRecuperoDownloadSogei.TIPO_CERTIFICAZIONE.equals(tipoRichiesta)) {
			entity.setEsito(String.valueOf(Code.SOGEI_CERTIFICAZIONI_ELABORAZIONE_CORRETTAMENTE_EFFETTUATA.code));
		}
		entity.setNumeroRecordTrovati(0L);
		entity.setNumeroRecordElaborati(0L);
		entity.setNumeroRecordNuovi(0L);
		entity.setStatoElaborazione(IRicevutaDownloadSogei.STATO_ELABORAZIONE_RECORD_FAKE);;
		insert(entity, true);
		
	}
}

/** */
package it.toscana.regione.wsods.ejb.dao.impl;

import it.toscana.regione.autocertificazionireddito.entity.jaxb.rt.RuoloSogettoType;
import it.toscana.regione.wsods.costanti.Varie;
import it.toscana.regione.wsods.ejb.dao.abs.EntityDao;
import it.toscana.regione.wsods.ejb.dao.api.IDao;
import it.toscana.regione.wsods.ejb.dao.api.IEsenzioniFasceDao;
import it.toscana.regione.wsods.entity.jpa.api.IEsenzioniFasce;
import it.toscana.regione.wsods.entity.jpa.impl.EsenzioniFasce;
import it.toscana.regione.wsods.exception.ConvertitoreException;
import it.toscana.regione.wsods.exception.DaoException;
import it.toscana.regione.wsods.lib.Convertitore;
import it.toscana.regione.wsods.type.Code;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
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
@Stateless(name = IEsenzioniFasceDao.EJB_REF)
public class EsenzioniFasceDao extends EntityDao<IEsenzioniFasce> implements IEsenzioniFasceDao, IDao<IEsenzioniFasce> {

	private static final Logger PERFORMANCE = LoggerFactory.getLogger("WSODS-DAO-PERFORMANCE");

	private static final Logger LOG = LoggerFactory.getLogger(EsenzioniFasceDao.class);
	
	/** fild serialVersionUID {@like long}*/
	private static final long	serialVersionUID	= -943487757808246383L;

	private final static String EJB_REF = IDao.EJB_REF_ESENZIONI_FASCE;
	
	/**
	 * 
	 */
	public EsenzioniFasceDao() {
		super();
	}

	/** {@inheritDoc} */ @Override public String ejbRef() { return EJB_REF; }

	/** {@inheritDoc} */ @Override protected Class<IEsenzioniFasce> clazz() { return IEsenzioniFasce.class; }

	/** {@inheritDoc} */ @Override protected String refTable() { return EsenzioniFasce.class.getName(); }

	/** {@inheritDoc} */
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<IEsenzioniFasce> autocertificazioniAssociate(final String idUni) throws DaoException {
		if(idUni == null ){throw new DaoException(Code.NULL_INATTESSO,"passato un id uni null");}
		final long start = System.currentTimeMillis();
		try{
			
			final StringBuffer sb = new StringBuffer("");
			sb.append("SELECT t FROM ").append(refTable()).append(" t WHERE ");
			sb.append(" (t.tipoEsenzione in ('E01','E02','E03','E04','E90','E91','E92','E93','ERA','ERB','ERC') ) ");
			sb.append(" AND ");
			sb.append(" (t.idUniDichiarante = :idUniDichiarante OR t.idUniTitolare = :idUniTitolare OR t.idUniversaleAssistito = :idUniversaleAssistito ) ");
			sb.append(" AND ");
			sb.append(" ( (t.flagTipologia is null AND t.dataFineOld is not null AND t.dataFineOld >= :adesso ) ");
			sb.append("   OR ");
			sb.append("   (t.flagTipologia = 'F' AND t.dataFineOld is null AND (t.dataFine >= :adesso OR t.dataFineErogazione >= :adesso ) ) ");
			sb.append(" ) ORDER BY t.dataOrdinamento, t.dataFornitura, t.dataAggiornamento ");
	
			final String jpql = sb.toString();
			final Map<String, Object> param = new HashMap<String, Object>();
			param.put("idUniDichiarante", idUni);
			param.put("idUniTitolare", idUni);
			param.put("idUniversaleAssistito", idUni);
			param.put("adesso", new Date());
			param.put("adesso", new Date());
			param.put("adesso", new Date());
			return get(0,jpql, param);
		} finally {
			PERFORMANCE.debug("{} ms - esenzioniFasceDao.autocertificazioniAssociate({}) ",(System.currentTimeMillis() - start),idUni);
		}
	}
	
	/** {@inheritDoc} */
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<IEsenzioniFasce> autocertificazioniAppartenenti(final String idUni) throws DaoException{
		if(idUni == null ){throw new DaoException(Code.NULL_INATTESSO,"passato un id uni null");}
		final long start = System.currentTimeMillis();
		try{
			final StringBuffer sb = new StringBuffer("");
			sb.append("SELECT t FROM ").append(refTable()).append(" t WHERE ");
			sb.append(" ( t.idUniversaleAssistito = :idUniversaleAssistito ) ");
			sb.append(" AND ");
			sb.append(" (t.tipoEsenzione in ('E01','E02','E03','E04','ERA','ERB','ERC') ) ");
			sb.append(" AND ");
			sb.append(" ( (t.flagTipologia is null AND t.dataFineOld is not null AND t.dataFineOld >= :adesso ) ");
			sb.append("   OR ");
			sb.append("   ( (t.flagTipologia = 'F'  OR t.flagTipologia = 'C' ) AND (t.dataFine >= :adesso OR t.dataFineErogazione >= :adesso )) ");
			sb.append(" ) ORDER BY t.dataOrdinamento, t.dataFornitura, t.dataAggiornamento ");
	
			final String jpql = sb.toString();
			
			final Map<String, Object> param = new HashMap<String, Object>();
			param.put("idUniversaleAssistito", idUni);
			param.put("adesso", new Date());
			param.put("adesso", new Date());
			param.put("adesso", new Date());
			return get(0,jpql, param);
		} finally {
			PERFORMANCE.debug("{} ms - esenzioniFasceDao.autocertificazioniAppartenenti({}) ",(System.currentTimeMillis() - start),idUni);
		}
	}

	@Override
	public List<IEsenzioniFasce> esenzioniAssociate(final String idUni, final List<RuoloSogettoType> listaRuoli) throws DaoException {
		if(idUni == null ){throw new DaoException(Code.NULL_INATTESSO,"passato un id uni null");}
		if(listaRuoli == null ){throw new DaoException(Code.NULL_INATTESSO,"passata una lista ruoli nulla");}
		if(listaRuoli.isEmpty() ){throw new DaoException(Code.NULL_INATTESSO,"passata una lista ruoli vuota");}
		
		final long start = System.currentTimeMillis();
		try{
			final StringBuffer sb = new StringBuffer("");
			sb.append("SELECT t FROM ").append(refTable()).append(" t WHERE ");
			sb.append(" (t.tipoEsenzione in ('E01','E02','E03','E04','E90','E91','E92','E93','ERA','ERB','ERC') ) ");
			sb.append(" AND ");
			sb.append(" ( ");
			final RuoloSogettoType[] arrayRuoli = listaRuoli.toArray(new RuoloSogettoType[0]);
			for( int i = 0 ; i<arrayRuoli.length ; i++){
				if(i>0){
					sb.append(" OR ");
				}
				if(RuoloSogettoType.DICHIARANTE.equals(arrayRuoli[i])){
					sb.append(" t.idUniDichiarante = :idUniDichiarante ");
				} else if (RuoloSogettoType.TITOLARE.equals(arrayRuoli[i])){
					sb.append(" t.idUniTitolare = :idUniTitolare ");
					
				} else if (RuoloSogettoType.BENEFICIARIO.equals(arrayRuoli[i])){
					sb.append(" t.idUniversaleAssistito = :idUniversaleAssistito ");
				} 
				
			}
					
			sb.append(" ) ");
			sb.append(" AND ");
			sb.append(" ( (t.flagTipologia is null AND t.dataFineOld is not null AND t.dataFineOld >= :adesso ) ");
			sb.append("   OR ");
			sb.append("   ( (t.flagTipologia = 'F'  OR t.flagTipologia = 'C' ) AND (t.dataFine >= :adesso OR t.dataFineErogazione >= :adesso ) ) ");
			sb.append(" ) ORDER BY t.dataOrdinamento, t.dataFornitura, t.dataAggiornamento ");
	
			final String jpql = sb.toString();
			
			final Map<String, Object> param = new HashMap<String, Object>();
			
			for( int i = 0 ; i<arrayRuoli.length ; i++){
				if(RuoloSogettoType.DICHIARANTE.equals(arrayRuoli[i])){
					param.put("idUniDichiarante", idUni);
				} else if (RuoloSogettoType.TITOLARE.equals(arrayRuoli[i])){
					param.put("idUniTitolare", idUni);
				} else if (RuoloSogettoType.BENEFICIARIO.equals(arrayRuoli[i])){
					param.put("idUniversaleAssistito", idUni);
				} 
				
			}		
			param.put("adesso", new Date());
			param.put("adesso", new Date());
			param.put("adesso", new Date());
			return get(0,jpql, param);
		} finally {
			PERFORMANCE.debug("{} ms - esenzioniFasceDao.esenzioniAssociate({},{}) ",(System.currentTimeMillis() - start),idUni,listaRuoli);
		}
	}
	
	/** {@inheritDoc} */
	@Override @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public boolean notExist(final IEsenzioniFasce entity) {
		if(entity == null ){throw new DaoException(Code.NULL_INATTESSO,"passato un IEsenzioniFasce nulla");}
		final long start = System.currentTimeMillis();
		try{
			final StringBuffer sb = new StringBuffer("");
			final Map<String, Object> param = new HashMap<String, Object>();
			
			sb.append("SELECT t FROM ").append(refTable()).append(" t WHERE 1=1");
			
			paramConfronto(sb, param,"dataFine", entity.getDataFine());
	
			paramConfronto(sb, param,"dataFineOld", entity.getDataFineOld());
	
			paramConfronto(sb, param,"dataInizioErogazione", entity.getDataInizioErogazione());
	
			paramConfronto(sb, param,"flagTipologia", entity.getFlagTipologia());
			paramConfronto(sb, param,"idUniDichiarante", entity.getIdUniDichiarante());
			paramConfronto(sb, param,"idUniTitolare", entity.getIdUniTitolare());
			paramConfronto(sb, param,"idUniversaleAssistito", entity.getIdUniversaleAssistito());
			paramConfronto(sb, param,"nota", entity.getNota());
			paramConfronto(sb, param,"protocollo", entity.getProtocollo());
	
			paramConfronto(sb, param,"tipoEsenzione", entity.getTipoEsenzione());
			paramConfronto(sb, param,"titolo", entity.getTitolo());
			paramConfronto(sb, param,"sorgente", entity.getSorgente());
			
	
			final String jpql = sb.toString();
			final List<IEsenzioniFasce> result =  get(0,jpql, param);
			
			return result==null || result.isEmpty();
		} finally {
			PERFORMANCE.debug("{} ms - esenzioniFasceDao.notExist(idEsenzioneFasce: [{}]) ",(System.currentTimeMillis() - start),entity.getId());
		}
	}
	@Override @TransactionAttribute(TransactionAttributeType.MANDATORY)
	public boolean existNewRecord(final String idUniversaleAssistito, final Date dataDaValutare) {
		
		if(idUniversaleAssistito == null ){throw new DaoException(Code.NULL_INATTESSO,"passato un idAssistito null");}
		if(dataDaValutare == null ){throw new DaoException(Code.NULL_INATTESSO,"passato una dataDaValutare null");}
		final long start = System.currentTimeMillis();
		try{
			
			final Map<String, Object> param = new HashMap<String, Object>();
			final StringBuffer sb = new StringBuffer("");
			sb.append("SELECT t FROM ").append(refTable()).append(" t WHERE 1=1 AND dataInserimento > :dataInserimento ");

			param.put("dataInserimento", dataDaValutare);
			paramConfronto(sb, param,"idUniversaleAssistito", idUniversaleAssistito);
			
			final String jpql = sb.toString();
			final List<IEsenzioniFasce> result =  get(2,jpql, param);
			
			return ( result!=null && result.size()>0 );
		} finally {
			PERFORMANCE.debug("{} ms - esenzioniFasceDao.existNewRecord({}, {}) ",(System.currentTimeMillis() - start),idUniversaleAssistito,dataDaValutare);
		}
	}
	@Override @TransactionAttribute(TransactionAttributeType.MANDATORY)
	public boolean existEsenzioneSuccessiva(final String tipoEsenzione, final String idUniversaleAssistito, final Date dataInizio, final Date dataDaValutare) {
		if(tipoEsenzione == null ){throw new DaoException(Code.NULL_INATTESSO,"passato un tipoEsenzione null");}
		if(idUniversaleAssistito == null ){throw new DaoException(Code.NULL_INATTESSO,"passato un idAssistito null");}
		if(dataInizio == null ){throw new DaoException(Code.NULL_INATTESSO,"passato una dataInizio null");}
		if(dataDaValutare == null ){throw new DaoException(Code.NULL_INATTESSO,"passato una dataDaValutare null");}
		final long start = System.currentTimeMillis();
		try{
			
			final Map<String, Object> param = new HashMap<String, Object>();
			final StringBuffer sb = new StringBuffer("");
			sb
				.append("SELECT t FROM ").append(refTable()).append(" t WHERE 1=1 AND ( flagTipologia = 'F' OR flagTipologia is null ) ")
				.append(" AND tipoEsenzione != :tipoEsenzione AND dataInizio != :dataInizio ")
				.append(" AND ( ")
					.append(" ( dataFornitura is not null AND dataFornitura > :dataFornitura ) ")
					.append(" OR ")
					.append(" (  dataOrdinamento is not null AND dataOrdinamento > :dataOrdinamento ) ")
				.append(" ) ")
				;
			param.put("tipoEsenzione", tipoEsenzione);
			param.put("dataInizio", dataInizio);
			param.put("dataFornitura", dataDaValutare);
			param.put("dataOrdinamento", dataDaValutare);
			paramConfronto(sb, param,"idUniversaleAssistito", idUniversaleAssistito);
			
			final String jpql = sb.toString();
			final List<IEsenzioniFasce> result =  get(2,jpql, param);
			
			return ( result!=null && result.size()>0 );
		} finally {
			PERFORMANCE.debug("{} ms - esenzioniFasceDao.existEsenzioneSuccessiva({}, {}, {}, {}) ",(System.currentTimeMillis() - start),tipoEsenzione,idUniversaleAssistito,dataInizio,dataDaValutare);
		}
	}
	@Override @TransactionAttribute(TransactionAttributeType.MANDATORY)
	public List<IEsenzioniFasce> storicoAutocertificazioni(final String tipoEsenzione, final String idUniversaleAssistito, final Date dataInizio) {
		if(tipoEsenzione == null ){throw new DaoException(Code.NULL_INATTESSO,"passato un tipoEsenzione null");}
		if(idUniversaleAssistito == null ){throw new DaoException(Code.NULL_INATTESSO,"passato un idAssistito null");}
		if(dataInizio == null ){throw new DaoException(Code.NULL_INATTESSO,"passato una dataInizio null");}
		final long start = System.currentTimeMillis();
		try{
		
			final Map<String, Object> param = new HashMap<String, Object>();
			final StringBuffer sb = new StringBuffer("");
			sb.append("SELECT t FROM ").append(refTable()).append(" t WHERE 1=1 AND ( flagTipologia = 'F' OR flagTipologia is null ) ");
					
			paramConfronto(sb, param,"idUniversaleAssistito", idUniversaleAssistito);
			paramConfronto(sb, param,"tipoEsenzione", tipoEsenzione);
			paramConfronto(sb, param,"dataInizio", dataInizio);
	
			final String jpql = sb.toString();
			final List<IEsenzioniFasce> result =  get(0,jpql, param);
			
			return result;
		} finally {
			PERFORMANCE.debug("{} ms - esenzioniFasceDao.storicoAutocertificazioni({}, {},{}) ",(System.currentTimeMillis() - start),tipoEsenzione,idUniversaleAssistito,dataInizio);
		}
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.MANDATORY)
	public List<IEsenzioniFasce> storicoEsenzioniCERD(final String tipoEsenzione, final String idUniversaleAssistito) {
		if(tipoEsenzione == null ){throw new DaoException(Code.NULL_INATTESSO,"passato un tipoEsenzione null");}
		if(idUniversaleAssistito == null ){throw new DaoException(Code.NULL_INATTESSO,"passato un idAssistito null");}
		final long start = System.currentTimeMillis();
		try{
		
			final Map<String, Object> param = new HashMap<String, Object>();
			final StringBuffer sb = new StringBuffer("");
			sb.append("SELECT t FROM ").append(refTable()).append(" t WHERE 1=1 AND flagTipologia = 'C' ");
	
			paramConfronto(sb, param,"idUniversaleAssistito", idUniversaleAssistito);
			paramConfronto(sb, param,"tipoEsenzione", tipoEsenzione);
			
	
			final String jpql = sb.toString();
			final List<IEsenzioniFasce> result =  get(0,jpql, param);
			
			return result;
		} finally {
			PERFORMANCE.debug("{} ms - esenzioniFasceDao.storicoEsenzioniCERD({}, {}) ",(System.currentTimeMillis() - start),tipoEsenzione,idUniversaleAssistito);
		}
	}
	@Override @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public List<IEsenzioniFasce> esenzioniDaValutare(int size, String tipoEsenzione) {
		if(tipoEsenzione == null ){throw new DaoException(Code.NULL_INATTESSO,"passato un tipoEsenzione null");}
		if(size <1 ){throw new DaoException(Code.DATO_INCONGRUO,"si e' cercato una finestra con numero negativo o infinito, attivita' non permessa");}
		
		final long start = System.currentTimeMillis();
		try{
		
			final Map<String, Object> param = new HashMap<String, Object>();
			final StringBuffer sb = new StringBuffer("");
			sb.append("SELECT t FROM ").append(refTable()).append(" t WHERE ( flagTipologia = 'F'  OR  flagTipologia is null ) AND dataFine >= :dataFine   ");
			paramConfronto(sb, param,"tipoEsenzione", tipoEsenzione);
			paramConfronto(sb, param, "flagValutazione", IEsenzioniFasce.FLAG_VALUTAZIONE_DA_VALUTARE); // Mai Valutati
			sb.append(" ORDER BY dataAggiornamento ");
			
			try {
				final Date ieri = new Date(Convertitore.convertiTimeInizioGiornata(System.currentTimeMillis())-Varie.TIME_GIORNO);
				param.put("dataFine", ieri);
			} catch (ConvertitoreException e) {
				throw new DaoException(Code.FORMATO,"impossibile ottenere la data di ieri",e);
			}
			
			
			final String jpql = sb.toString();
			final List<IEsenzioniFasce> result =  get(size,jpql, param);
			
			return marcaEFiltraCollegati(result);
		} finally {
			PERFORMANCE.debug("{} ms - esenzioniFasceDao.esenzioniDaValutare({}, {}) ",(System.currentTimeMillis() - start),size,tipoEsenzione);
		}
	}

	private static List<IEsenzioniFasce> marcaEFiltraCollegati(final List<IEsenzioniFasce> listaRecuperata){
		LOG.info("rendo riconoscibili gli elementi storici di un esenzione gia' presa in considerazione");
		for(final IEsenzioniFasce el : listaRecuperata){
			if(el != null && IEsenzioniFasce.FLAG_VALUTAZIONE_VALUTATO_UN_ELEMENTO_DELLA_SUA_STORIA!= el.getFlagValutazione()){
				final String key = estraiKey(el);
				final long id = el.getId();
				for(final IEsenzioniFasce el2 : listaRecuperata){
					if(el2 !=null && id!=el2.getId() && key.equals(estraiKey(el2))){
						el2.setDataAggiornamento(new Timestamp(System.currentTimeMillis()));
						el2.setFlagValutazione(IEsenzioniFasce.FLAG_VALUTAZIONE_VALUTATO_UN_ELEMENTO_DELLA_SUA_STORIA);
					}
				}
			}
		}
		final List<IEsenzioniFasce> listaFiltrata = new ArrayList<IEsenzioniFasce>();
		for(final IEsenzioniFasce el : listaRecuperata){
			if(el.getFlagValutazione() != IEsenzioniFasce.FLAG_VALUTAZIONE_VALUTATO_UN_ELEMENTO_DELLA_SUA_STORIA){
				listaFiltrata.add(el);
			}
		}
		return listaFiltrata;
	}
	private static String estraiKey(final IEsenzioniFasce esenzioniFasce){
		StringBuffer sb = new StringBuffer("");
		if(esenzioniFasce != null) {
			if (esenzioniFasce.getTipoEsenzione()!=null) {sb.append(esenzioniFasce.getTipoEsenzione().trim());}
			if (esenzioniFasce.getIdUniversaleAssistito()!=null) {sb.append(esenzioniFasce.getIdUniversaleAssistito().trim());}
			if (esenzioniFasce.getDataInizio()!=null) {
				long dataInizio;
				try {
					dataInizio = Convertitore.convertiTimeInizioGiornata(esenzioniFasce.getDataInizio());
					sb.append(dataInizio);
				} catch (ConvertitoreException e) {
					LOG.warn("e' stato impossibile estrarre la data inizio del record con id [{}]",esenzioniFasce.getId());
				}
			}
		}
		
		return sb.toString();
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.MANDATORY)
	public List<IEsenzioniFasce> esenzioniDaRettificare(int size, String tipoEsenzione) {
		if(tipoEsenzione == null ){throw new DaoException(Code.NULL_INATTESSO,"passato un tipoEsenzione null");}
		if(size <1 ){throw new DaoException(Code.DATO_INCONGRUO,"si e' cercato una finestra con numero negativo o infinito, attivita' non permessa");}

		final long start = System.currentTimeMillis();
		try{	
			final Date oggi;
			try {
				oggi = new Date(Convertitore.convertiTimeInizioGiornata(System.currentTimeMillis()));
			} catch (ConvertitoreException e) {
				throw new DaoException(Code.FORMATO,"impossibile ottenere la data correntre",e);
			}
			
			final Map<String, Object> param = new HashMap<String, Object>();
			final StringBuffer sb = new StringBuffer("");
			sb.append("SELECT t FROM ").append(refTable()).append(" t WHERE (flagTipologia = 'F' OR flagTipologia is null) AND dataProssimaValutazione <= :dataProssimaElaborazione");
			param.put("dataProssimaElaborazione", oggi);
			paramConfronto(sb, param,"tipoEsenzione", tipoEsenzione);
			paramConfronto(sb, param, "flagValutazione", IEsenzioniFasce.FLAG_VALUTAZIONE_VALUTATO_E_DA_RETTIFICARE_IN_SEGUITO); // gia' Valutati
			sb.append(" ORDER BY dataAggiornamento ");
			
			final String jpql = sb.toString();
			final List<IEsenzioniFasce> result =  get(size,jpql, param);
			return result;
		} finally {
			PERFORMANCE.debug("{} ms - esenzioniFasceDao.esenzioniDaRettificare({}, {}) ",(System.currentTimeMillis() - start),size,tipoEsenzione);
		}
	}

	
	
	
	/**
	 * Recupera le autocertificazioni della tipologia richiesta valide alla data corrente 
	 *  
	 *  
	 */
	@Override @TransactionAttribute(TransactionAttributeType.MANDATORY)
	public List<IEsenzioniFasce> getAutocertificazioniXTipo(final String tipoEsenzione, final String idUniversaleAssistito) {
		if(tipoEsenzione == null ){throw new DaoException(Code.NULL_INATTESSO,"passato un tipoEsenzione null");}
		if(idUniversaleAssistito == null ){throw new DaoException(Code.NULL_INATTESSO,"passato un idAssistito null");}
		final long start = System.currentTimeMillis();
		try{	
			final Map<String, Object> param = new HashMap<String, Object>();
			final StringBuffer sb = new StringBuffer("");
			sb.append("SELECT t FROM ").append(refTable()).append(" t WHERE 1=1 AND ( flagTipologia = 'F' OR flagTipologia is null ) ");
					
			paramConfronto(sb, param,"idUniversaleAssistito", idUniversaleAssistito);
			paramConfronto(sb, param,"tipoEsenzione", tipoEsenzione);
			final String jpql = sb.toString();
			final List<IEsenzioniFasce> result =  get(0,jpql, param);
		
			return result;
		} finally {
			PERFORMANCE.debug("{} ms - esenzioniFasceDao.getAutocertificazioniXTipo({}, {}) ",(System.currentTimeMillis() - start),tipoEsenzione,idUniversaleAssistito);
		}
	}
	

	@Override @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void updateDataAggiornamentoSotricoAutocertificazione(final String tipoEsenzione, final String idUniversaleAssistito, final Date dataInizio) {
		
		if(tipoEsenzione == null ){throw new DaoException(Code.NULL_INATTESSO,"passato un tipoEsenzione null");}
		if(idUniversaleAssistito == null ){throw new DaoException(Code.NULL_INATTESSO,"passato un idAssistito null");}
		if(dataInizio == null ){throw new DaoException(Code.NULL_INATTESSO,"passato una dataInizio null");}
		
		final long start = System.currentTimeMillis();
		try{
		
			final Timestamp adesso = new Timestamp(System.currentTimeMillis());
			
			final List<IEsenzioniFasce> storico = storicoAutocertificazioni(tipoEsenzione, idUniversaleAssistito, dataInizio);
			
			if(storico!=null && !storico.isEmpty()){
				for(final IEsenzioniFasce esenzione : storico){
					if(esenzione != null){
						esenzione.setDataAggiornamento(adesso);
					}
				}
				flush();
			}
		} finally {
			PERFORMANCE.debug("{} ms - esenzioniFasceDao.updateDataAggiornamentoSotricoAutocertificazione({}, {}, {}) ",(System.currentTimeMillis() - start),tipoEsenzione,idUniversaleAssistito,dataInizio);
		}
	}
	

	@Override @TransactionAttribute(TransactionAttributeType.MANDATORY)
	public List<IEsenzioniFasce> selectFromIdUniBeneficiario(final String idUniSlave){
		
		if( idUniSlave == null ) { throw new DaoException(Code.NULL_INATTESSO,"passato un idUniSlave null"); }
		
		final long start = System.currentTimeMillis();
		try{
			final Map<String, Object> param = new HashMap<String, Object>();
			final String jpql = "SELECT t FROM "+refTable()+" t WHERE 1=1 and t.idUniversaleAssistito = :idUniversaleAssistito";
			param.put("idUniversaleAssistito", idUniSlave);
			final List<IEsenzioniFasce> lista = get(0,jpql, param);
			return lista;
		} finally {
			PERFORMANCE.debug("{} ms - esenzioniFasceDao.selectFromIdUniBeneficiario({}) ",(System.currentTimeMillis() - start),idUniSlave);
		}
		
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.MANDATORY)
	public List<IEsenzioniFasce> selectFromIdUniTitolare(final String idUniSlave){
		
		if( idUniSlave == null ) { throw new DaoException(Code.NULL_INATTESSO,"passato un idUniSlave null"); }
		
		final long start = System.currentTimeMillis();
		try{
			final Map<String, Object> param = new HashMap<String, Object>();
			final String jpql = "SELECT t FROM "+refTable()+" t WHERE 1=1 and t.idUniTitolare = :idUniTitolare";
			param.put("idUniTitolare", idUniSlave);
			final List<IEsenzioniFasce> lista = get(0,jpql, param);
			return lista;
		} finally {
			PERFORMANCE.debug("{} ms - esenzioniFasceDao.selectFromIdUniTitolare({}) ",(System.currentTimeMillis() - start),idUniSlave);
		}
		
	}
	@Override @TransactionAttribute(TransactionAttributeType.MANDATORY)
	public List<IEsenzioniFasce> selectFromIdUniDichiarante(final String idUniSlave){
		
		if( idUniSlave == null ) { throw new DaoException(Code.NULL_INATTESSO,"passato un idUniSlave null"); }
		
		final long start = System.currentTimeMillis();
		try{
			final Map<String, Object> param = new HashMap<String, Object>();
			final String jpql = "SELECT t FROM "+refTable()+" t WHERE 1=1 and t.idUniDichiarante = :idUniDichiarante";
			param.put("idUniDichiarante", idUniSlave);
			final List<IEsenzioniFasce> lista = get(0,jpql, param);
			return lista;
		} finally {
			PERFORMANCE.debug("{} ms - esenzioniFasceDao.selectFromIdUniDichiarante({}) ",(System.currentTimeMillis() - start),idUniSlave);
		}
		
	}

	@Override @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<IEsenzioniFasce> selectFromProtocollo(final String protocollo) {

			final long start = System.currentTimeMillis();
			try{
				final String jpql = "SELECT t FROM "+refTable()+" t WHERE t.titolo is not null AND t.protocollo = :protocollo ORDER BY t.dataInizio desc ";
				final Map<String,Object> param = new HashMap<String, Object>();
				param.put("protocollo", protocollo);
				
				final List<IEsenzioniFasce> result = get(0, jpql, param);
				
				return result;
			} finally {
				PERFORMANCE.debug("{} ms - EsenzioniFasceDao.selectFromProtocollo({}) ",(System.currentTimeMillis() - start),protocollo);
			}
	}
}

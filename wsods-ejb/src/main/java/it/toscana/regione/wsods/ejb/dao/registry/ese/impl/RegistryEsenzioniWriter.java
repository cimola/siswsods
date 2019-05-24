//package it.toscana.regione.wsods.ejb.dao.registry.ese.impl;
//
//import it.toscana.regione.wsods.costanti.Varie;
//import it.toscana.regione.wsods.ejb.dao.registry.anag.api.IRegistryConverter;
//import it.toscana.regione.wsods.ejb.dao.registry.ese.api.IRegistryEsenzioniWriter;
//import it.toscana.regione.wsods.entity.bean.api.IAutocertificazioneReg;
//import it.toscana.regione.wsods.entity.jpa.registry.api.IEsenzione;
//import it.toscana.regione.wsods.entity.jpa.registry.api.IEvento;
//import it.toscana.regione.wsods.entity.jpa.registry.impl.Esenzione;
//import it.toscana.regione.wsods.entity.jpa.registry.impl.Evento;
//import it.toscana.regione.wsods.exception.ConvertitoreException;
//import it.toscana.regione.wsods.exception.WsOdsException;
//import it.toscana.regione.wsods.exception.WsOdsRuntimeException;
//import it.toscana.regione.wsods.lib.Convertitore;
//import it.toscana.regione.wsods.lib.Util;
//import it.toscana.regione.wsods.type.Code;
//
//import java.util.Date;
//import java.util.List;
//
//import javax.ejb.EJB;
//import javax.ejb.Stateless;
//import javax.ejb.TransactionAttribute;
//import javax.ejb.TransactionAttributeType;
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import javax.persistence.Query;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//@Stateless(name = IRegistryEsenzioniWriter.EJB_REF)
//public class RegistryEsenzioniWriter implements IRegistryEsenzioniWriter {
//	
//	
//	private static final Logger LOG = LoggerFactory.getLogger(RegistryEsenzioniWriter.class);
//	
//	
//	@PersistenceContext(unitName="registry-ese-writer")
//	private EntityManager manager;
//	
//	@EJB(beanName = IRegistryConverter.EJB_REF)
//	private IRegistryConverter converter;
//
//	private final static String MAGIC_SEED="NcStNgRtKcT";
//	
//	public RegistryEsenzioniWriter() {super();}
//	
//	
//	@Override
//	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
//	public int writerOnRegistry(final IAutocertificazioneReg autocertificazione) throws WsOdsRuntimeException {
//		try {
//			convertiIdUniToInternalId(autocertificazione);
//			
//			final String codiceEsenzione = autocertificazione.getTipoEsenzione();
//			final String internalIdAssistito = autocertificazione.getInternalIdAssistito();
//			final String cfAssistito = autocertificazione.getCfAssistito();
//			final Date dataInizio = autocertificazione.getDataInizio();
//			final Date dataFine = autocertificazione.getDataFine();
//			
//			
//			final boolean notExist = notExist(codiceEsenzione, internalIdAssistito, dataInizio, dataFine);
//			
//			if(notExist){
//				final String nce = genNce(codiceEsenzione, cfAssistito, dataInizio, dataFine);
//				
//				final IEsenzione esenzione;
//				try{
//					esenzione = new Esenzione(nce, autocertificazione);
//				} catch (ConvertitoreException e){
//					throw new WsOdsException(Code.PARSER," impossibile generare l'entity Esenzione",e);
//				}
//				try {
//					manager.persist(esenzione);
//				} catch(RuntimeException e){
//					throw new WsOdsException(Code.DAO_ENTITY_MANAGER_PERSIST," unit name [registry-ese-writer] ",e);
//				}
//				
//				try {
//					final IEvento evento = new Evento(generaIdEvento(esenzione),"WSODS",esenzione.getId());
//					manager.persist(evento);
//				} catch(RuntimeException e){
//					throw new WsOdsException(Code.DAO_ENTITY_MANAGER_PERSIST," unit name [registry-ese-writer] ",e);
//				}
//				
//				return 1;
//			} else {
//				return 0;
//			}
//		} catch(WsOdsException e){
//			LOG.warn("Non e' stato possibile inserire il record sul Registry");
//			throw new WsOdsRuntimeException("impossibile memorizzare l'esenzione sul componente registry",e);
//		}
//	}
//
//	private final static String generaIdEvento(final IEsenzione esenzione){
//		final StringBuffer sb = new StringBuffer("");
//		if(esenzione!=null){
//			sb.append(esenzione.getId()).append("_");
//			sb.append(esenzione.getCodEsenzione()).append("_");
//			sb.append(esenzione.getAutocertificatoreEntityId()).append("_");
//			sb.append(esenzione.getTitolareEntityId()).append("_");
//			sb.append(esenzione.getAssistitoEntityId()).append("_");
//			sb.append(convertiNoException(esenzione.getDataInizio())).append("_");
//			sb.append(convertiNoException(esenzione.getDataFine())).append("_");
//			sb.append(convertiNoException(esenzione.getDataFineOld())).append("_");
//			
//		}
//		sb.append(String.valueOf(System.currentTimeMillis()));
//		final String idEvento = sb.toString();
//		if(idEvento!=null && idEvento.length()>255){
//			return idEvento.substring(0, 255);
//		} else {
//			return idEvento;
//		}
//	}
//	private final static String convertiNoException(final Date d){
//
//		try{
//			final long time = Convertitore.convertTime(d);
//			return  Convertitore.convertTime(time,Varie.FORMATO_TIME_YYYY_MM_DD_HH_MM_SS_SSS);
//		} catch(Throwable t){
//			return String.valueOf(System.currentTimeMillis());
//		}
//	}
//	private boolean notExist(final String codiceEsenzione, final String internalIdAssistito, final Date dataInizio, final Date dataFine) throws WsOdsException {
//		final String jpql = "SELECT t FROM Esenzione t WHERE autocertificatoreEntityId= :autocertificatoreEntityId  AND codEsenzione = :codEsenzione AND dataInizio =:dataInizio  AND dataFine = :dataFine";
//		try {
//			final Query query;
//			try {
//				query = manager.createQuery(jpql);
//			}catch(RuntimeException e){
//				throw new WsOdsException(Code.DAO_CREATE_QUERY,"impossiblie creare la query sul registry-ese-writer ["+jpql+"]",e);
//			}
//			
//			query.setParameter("autocertificatoreEntityId", internalIdAssistito);
//			query.setParameter("codEsenzione", codiceEsenzione);
//			query.setParameter("dataInizio", dataFine);
//			query.setParameter("dataFine", dataFine);
//			final List< ? > rowResults;
//			try {
//				rowResults = query.getResultList();
//			}catch(RuntimeException e){
//				throw new WsOdsException(Code.DAO_EXECUTE_SELECT,"impossiblie eseguire la query sul registry-ese-writer ["+jpql+"]",e);
//			}
//			return rowResults==null||rowResults.isEmpty();
//		}catch(WsOdsException e){
//			throw new WsOdsException("impossibile Verificare l'esistenza del record su registry-ese-writer ",e);
//		}
//	}
//	
//	private static String genNce(final String codiceEsenzione, final String cfAssistito, final Date dataInizio, final Date dataFine){
//		final String nce;
//		try{
//			
//			final String dataAggiornamentoS = Convertitore.convertTime(System.currentTimeMillis(), Varie.FORMATO_DATA_SOGEI);
//			final String dataInizioS = dataInizio==null?"":Convertitore.convertTime(dataInizio.getTime(), Varie.FORMATO_DATA_SOGEI);
//			final String dataFineS = dataFine==null?"":Convertitore.convertTime(dataFine.getTime(), Varie.FORMATO_DATA_SOGEI);
//			final String codiceEsenzioneS = codiceEsenzione==null?"":codiceEsenzione;
//			final String cfAssistitoS = cfAssistito==null?"":cfAssistito;
//			
//			final String codiceDaDecodificare =  codiceEsenzioneS+dataInizioS+dataFineS+dataAggiornamentoS+MAGIC_SEED+cfAssistitoS;
//			nce = Util.MD5(codiceDaDecodificare);
//		} catch (WsOdsException e){
//			return "******************************************";
//		}
//		return nce;
//	}
//	private void convertiIdUniToInternalId(final IAutocertificazioneReg autocertificazione) throws WsOdsException {
//		try {
//			autocertificazione.setInternalIdDichiarante(converter.getInternalIdFromCode(autocertificazione.getIdUniDichiarante(), IRegistryConverter.ROOT_ID_UNI));
//		}catch(WsOdsException e){
//			throw new WsOdsException("inpossibile convertire l'idUniversale del dichiarante in internalId ",e);
//		}
//		try {
//			autocertificazione.setInternalIdTitolare(converter.getInternalIdFromCode(autocertificazione.getIdUniTitolare(), IRegistryConverter.ROOT_ID_UNI));
//		}catch(WsOdsException e){
//			throw new WsOdsException("inpossibile convertire l'idUniversale del titolare in internalId ",e);
//		}
//		try {
//			autocertificazione.setInternalIdAssistito(converter.getInternalIdFromCode(autocertificazione.getIdUniversaleAssistito(), IRegistryConverter.ROOT_ID_UNI));
//		}catch(WsOdsException e){
//			throw new WsOdsException("inpossibile convertire l'idUniversale del beneficiario in internalId ",e);
//		}
//	}
//}

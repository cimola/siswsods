package it.toscana.regione.wsods.ejb.iduniobsoleti.impl;

import it.toscana.regione.wsods.ejb.dao.api.IEsenzioniFasceDao;
import it.toscana.regione.wsods.ejb.dao.api.IIdUniDaAggiornareDao;
import it.toscana.regione.wsods.ejb.dao.api.ITracciaObsolescenzaIdUniDao;
import it.toscana.regione.wsods.ejb.iduniobsoleti.api.IAggiornatoreIdUniObsoleti;
import it.toscana.regione.wsods.entity.jpa.api.IEsenzioniFasce;
import it.toscana.regione.wsods.entity.jpa.api.IIdUniDaAggiornare;
import it.toscana.regione.wsods.entity.jpa.api.ITracciaObsolescenzaIdUni;
import it.toscana.regione.wsods.entity.jpa.impl.TracciaObsolescenzaIdUni;
import it.toscana.regione.wsods.exception.WsOdsRuntimeException;
import it.toscana.regione.wsods.singleton.Conf;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless(name=IAggiornatoreIdUniObsoleti.EJB_REF)
public class AggiornatoreIdUniObsoleti implements IAggiornatoreIdUniObsoleti {


	private static final Logger LOG = LoggerFactory.getLogger(AggiornatoreIdUniObsoleti.class);

	@EJB(beanName= ITracciaObsolescenzaIdUniDao.EJB_REF)
	private ITracciaObsolescenzaIdUniDao tracciaObsolescenzaIdUniDao;


	@EJB(beanName= IIdUniDaAggiornareDao.EJB_REF)
	private IIdUniDaAggiornareDao idUniDaAggiornareDao;
	
	@EJB(beanName= IEsenzioniFasceDao.EJB_REF)
	private IEsenzioniFasceDao esenzioniFasceDao;
	
	public AggiornatoreIdUniObsoleti() { super(); }
	

	@Override @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Long> recuperaIdLinkDaElaborare() throws WsOdsRuntimeException {
		final int size = Conf.getAggiornaIdUniObsoletiDimensioneFienstra();
		return idUniDaAggiornareDao.getFirstLinksToUpdate(size);
	}

	@Override @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void riportaAssociazione(final Long id) {
		final IIdUniDaAggiornare associazione = idUniDaAggiornareDao.get(id);
		if(associazione!=null){
			
			final List<Long> idsOldBeneficiario = recuperaIdBeneficiariEAggiornaIdUni(associazione.getSlave(),associazione.getMaster());
			final List<Long> idsOldDichiarante = recuperaIdDichiarantiEAggiornaIdUni(associazione.getSlave(),associazione.getMaster());
			final List<Long> idsOldTitolare = recuperaIdTitolareEAggiornaIdUni(associazione.getSlave(),associazione.getMaster());
			
			tracciaAttivita(idsOldBeneficiario, idsOldDichiarante, idsOldTitolare, associazione);
			
			marcaRecordIdUniDaAggiornare(idsOldBeneficiario, idsOldDichiarante, idsOldTitolare, associazione);
			
		}
	}


	private List<Long> recuperaIdBeneficiariEAggiornaIdUni(final String idUniSlave, final String idUniMaster) throws WsOdsRuntimeException {
		LOG.info("reupera gli id e aggiorna i record delle esenzioni avendo {} come soggetto beneficiario e sostituendolo con {}",idUniSlave,idUniMaster);
		final List<IEsenzioniFasce> lista = esenzioniFasceDao.selectFromIdUniBeneficiario(idUniSlave);
		final List<Long> ids = new ArrayList<Long>();
		final Timestamp adesso = new Timestamp(System.currentTimeMillis());
		if(isNotEmpty(lista)){
			for(final IEsenzioniFasce el: lista){
				if(el!=null){
					el.setIdUniversaleAssistito(idUniMaster);
					el.setDataAggiornamento(adesso);
					ids.add(el.getId());
				}
			}
		}
		return ids;
	}


	private List<Long> recuperaIdDichiarantiEAggiornaIdUni(final String idUniSlave, final String idUniMaster) throws WsOdsRuntimeException {
		LOG.info("reupera gli id e aggiorna i record delle esenzioni avendo {} come soggetto dichiarante e sostituendolo con {}",idUniSlave,idUniMaster);
		final List<IEsenzioniFasce> lista = esenzioniFasceDao.selectFromIdUniDichiarante(idUniSlave);
		final List<Long> ids = new ArrayList<Long>();
		final Timestamp adesso = new Timestamp(System.currentTimeMillis());
		if(isNotEmpty(lista)){
			for(final IEsenzioniFasce el: lista){
				if(el!=null){
					el.setIdUniDichiarante(idUniMaster);
					el.setDataAggiornamento(adesso);
					ids.add(el.getId());
				}
			}
		}
		return ids;
	}


	private List<Long> recuperaIdTitolareEAggiornaIdUni(final String idUniSlave, final String idUniMaster) throws WsOdsRuntimeException {
		LOG.info("reupera gli id e aggiorna i record delle esenzioni avendo {} come soggetto titolare e sostituendolo con {}",idUniSlave,idUniMaster);
		final List<IEsenzioniFasce> lista = esenzioniFasceDao.selectFromIdUniTitolare(idUniSlave);
		final List<Long> ids = new ArrayList<Long>();
		final Timestamp adesso = new Timestamp(System.currentTimeMillis());
		if(isNotEmpty(lista)){
			for(final IEsenzioniFasce el: lista){
				if(el!=null){
					el.setIdUniTitolare(idUniMaster);
					el.setDataAggiornamento(adesso);
					ids.add(el.getId());
				}
			}
		}
		return ids;
	}
	
	private void marcaRecordIdUniDaAggiornare(final List<Long> idsOldBeneficiario, final List<Long> idsOldDichiarante, final List<Long> idsOldTitolare, final IIdUniDaAggiornare associazione) {
		LOG.info("marco il record in base all'utilizzo fatto degli id");
		if(isNotEmpty(idsOldBeneficiario) ||isNotEmpty(idsOldDichiarante) ||isNotEmpty(idsOldTitolare)  ){
			associazione.setStatoUtilizzo(IIdUniDaAggiornare.STATO_UTILIZZO_RICERCATO_E_TROVATO);
		} else {
			associazione.setStatoUtilizzo(IIdUniDaAggiornare.STATO_UTILIZZO_RICERCATO_MA_NON_USATO);
		}
		associazione.setDataAggiornamento(new Timestamp(System.currentTimeMillis()));
		
	}
	
	private void tracciaAttivita(final List<Long> idsOldBeneficiario, final List<Long> idsOldDichiarante, final List<Long> idsOldTitolare, final IIdUniDaAggiornare associazione) {
		LOG.info("inseriamo i recor utili a tenere traccia delle modifiche");
		final List<ITracciaObsolescenzaIdUni> listaTraccie = generaTraccia(idsOldBeneficiario, idsOldDichiarante, idsOldTitolare, associazione.getId());
		
		if(isNotEmpty(listaTraccie)){
			for(final ITracciaObsolescenzaIdUni entity : listaTraccie){
				if(entity!=null){
					tracciaObsolescenzaIdUniDao.insert(entity, false);
				}
			}
			tracciaObsolescenzaIdUniDao.flush();
		}
		
		
	}
		
		
	private final static List<ITracciaObsolescenzaIdUni> generaTraccia(final List<Long> idsOldBeneficiario ,final List<Long> idsOldDichiarante,final List<Long> idsOldTitolare, final long fkIdUniDaAggiornare){
		final List<ITracciaObsolescenzaIdUni> lista = new ArrayList<ITracciaObsolescenzaIdUni>();
		
		addsRow( ITracciaObsolescenzaIdUni.RUOLO_BENEFICIARIO,idsOldBeneficiario, fkIdUniDaAggiornare, lista);
		addsRow( ITracciaObsolescenzaIdUni.RUOLO_DICHIARANTE,idsOldDichiarante, fkIdUniDaAggiornare, lista);
		addsRow( ITracciaObsolescenzaIdUni.RUOLO_TITOLARE,idsOldTitolare, fkIdUniDaAggiornare, lista);
		
		return lista;
	}



	private static void addsRow(final String ruolo, final List<Long> listaFkEsenzioniFace, final long fkIdUniDaAggiornare, final List<ITracciaObsolescenzaIdUni> lista) {
		if(isNotEmpty(listaFkEsenzioniFace)){
			for(final Long fkEsenzioniFasce : listaFkEsenzioniFace){
				if(fkEsenzioniFasce != null){
					lista.add(createEnety(fkEsenzioniFasce, fkIdUniDaAggiornare,ruolo));
				}
			}
		}
	}
	private final static ITracciaObsolescenzaIdUni createEnety(final long fkEsenzioniFasce,final long fkIdUniDaAggiornare , final String ruolo){
		final ITracciaObsolescenzaIdUni entity = new TracciaObsolescenzaIdUni();
		
		entity.setFkEsenzioniFasce(fkEsenzioniFasce);
		entity.setFkIdUniDaAggiornare(fkIdUniDaAggiornare);
		entity.setRuolo(ruolo);
		return entity;
	}
	
	private final static boolean isNotEmpty(final List< ? > ids){
		return ids!=null && !ids.isEmpty();
	}



}

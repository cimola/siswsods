package it.toscana.regione.wsods.ejb.ricalcolatitolonull.impl;

import it.toscana.regione.wsods.ejb.dao.api.IEsenzioniFasceDao;
import it.toscana.regione.wsods.ejb.ricalcolatitolonull.api.IRicalcolaTitoloNull;
import it.toscana.regione.wsods.ejb.sdm.api.ISdm;
import it.toscana.regione.wsods.entity.bean.api.ISoggetto;
import it.toscana.regione.wsods.entity.jpa.api.IAutocertificazioneScartate;
import it.toscana.regione.wsods.entity.jpa.api.IAutocertificazioneTmp;
import it.toscana.regione.wsods.entity.jpa.api.IEsenzioniFasce;
import it.toscana.regione.wsods.exception.MapperException;
import it.toscana.regione.wsods.exception.RicalcolaTitoloNullException;
import it.toscana.regione.wsods.exception.SisDataManagerException;
import it.toscana.regione.wsods.exception.WsOdsRuntimeException;
import it.toscana.regione.wsods.type.Code;
import it.toscana.regione.wsods.type.TitoloType;

import java.sql.Timestamp;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless(name = IRicalcolaTitoloNull.EJB_REF)
public class RicalcolaTitoloNull implements IRicalcolaTitoloNull {

	private static final Logger LOG = LoggerFactory.getLogger(RicalcolaTitoloNull.class);

	@EJB(beanName = ISdm.EJB_REF)
	private ISdm sdm;

	@EJB(beanName = IEsenzioniFasceDao.EJB_REF)
	private IEsenzioniFasceDao esenzioniFasceDao;
	
	private String anonimizza(final String cf){
		if (cf == null || cf.trim().length() == 0) { throw new RicalcolaTitoloNullException(Code.SDM_SOGGETTO_NON_TROVATO_O_CON_TRATTI_PARZIALI_O_DATI_INPUT_MANCANTI,"cf soggetto nullo"); }

		final ISoggetto soggetto;
		try{
			
			soggetto = sdm.getFromCodiceFiscaleNoTransaction(cf);
			
		} catch(SisDataManagerException e){
			if (Code.SDM_SERVIZIO_NON_DISPONIBILE.equals(e.code) || Code.SDM_SERVIZIO_RESTITUISCE_ERRORE.equals(e.code)) {
				throw new RicalcolaTitoloNullException(e);
			}
			throw new RicalcolaTitoloNullException(Code.SDM_SOGGETTO_NON_TROVATO_O_CON_TRATTI_PARZIALI_O_DATI_INPUT_MANCANTI, e);
		}
		
		
		if (soggetto == null || soggetto.getIdUni() == null || soggetto.getIdUni().trim().length() == 0 ) { throw new RicalcolaTitoloNullException(Code.SDM_SOGGETTO_NON_TROVATO_O_CON_TRATTI_PARZIALI_O_DATI_INPUT_MANCANTI); }
		 
		return soggetto.getIdUni().trim();
	}
	
	@Override 
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void ricalcolaTitoloNull( final IAutocertificazioneScartate autocertificazioneScartata) throws WsOdsRuntimeException {
	

		try {

			final String protocollo = autocertificazioneScartata.getProtocollo();
			final String idUniBeneficiario = anonimizza(autocertificazioneScartata.getCfBeneficiario());
			final String idUniTitolare = anonimizza(autocertificazioneScartata.getCfTitolare());
			final String idUniDichiarante  = anonimizza(autocertificazioneScartata.getCfDichiarante());
			
			final List<IEsenzioniFasce> list = esenzioniFasceDao.selectFromProtocollo(protocollo);
			
			if(list != null && list.size() >0){
				boolean trovato = false;
				for(final IEsenzioniFasce esenzioniFasce : list){
					if(!trovato && esenzioniFasce != null){
						final String idUniBeneficiario_db = esenzioniFasce.getIdUniversaleAssistito();
						final String idUniTitolare_db = esenzioniFasce.getIdUniTitolare();
						final String idUniDichiarante_db = esenzioniFasce.getIdUniDichiarante();
						if(
							idUniBeneficiario_db != null && 
							idUniTitolare_db != null && 
							idUniDichiarante_db != null &&
							idUniBeneficiario.equalsIgnoreCase(idUniBeneficiario_db.trim()) &&
							idUniTitolare.equalsIgnoreCase(idUniTitolare_db.trim()) &&
							idUniDichiarante.equalsIgnoreCase(idUniDichiarante_db.trim())
						){
							final String titolo = esenzioniFasce.getTitolo();
							autocertificazioneScartata.setTitolo(titolo);
							try{
								TitoloType.decodifica(titolo) ;
								autocertificazioneScartata.setNumeroElaborazioni(0L);
								autocertificazioneScartata.setRielabora(1L);
							}catch(final MapperException e) {
								autocertificazioneScartata.setNumeroElaborazioni(IAutocertificazioneTmp.NUMERO_ELABORAZIONI_TITOLO_NULLO_TITOLO_TROVATO_NON_VALIDO);
							}
							trovato = true;
						}
						
					}
					
				}
				if(!trovato){
					autocertificazioneScartata.setNumeroElaborazioni(IAutocertificazioneTmp.NUMERO_ELABORAZIONI_TITOLO_NULLO_SOGGETTI_DIVERGENTI);
				}
			} else {
				autocertificazioneScartata.setNumeroElaborazioni(IAutocertificazioneTmp.NUMERO_ELABORAZIONI_TITOLO_NULLO_PROTOCOLLO_NON_TROVATO);
			}
			
			autocertificazioneScartata.setDataAggiornamento(new Timestamp(System.currentTimeMillis()));
			
		} catch (final RicalcolaTitoloNullException e){
			LOG.warn("inpossibile procedere con l'elaborazione del'autocertificazioneScartata id ["+autocertificazioneScartata.getId()+"] ",e);
		}
		
		
		
		
		

	}

}

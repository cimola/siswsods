/** */
package it.toscana.regione.wsods.ejb.upload.impl;

import it.toscana.regione.autocertificazionireddito.entity.jaxb.rt.SoggettoType;
import it.toscana.regione.autocertificazionireddito.entity.jaxb.rt.UploadAps2WsOdsRequest;
import it.toscana.regione.wsods.ejb.dao.api.IDao;
import it.toscana.regione.wsods.ejb.dao.api.IEsenzioniFasceDao;
import it.toscana.regione.wsods.ejb.dao.api.IUploadAutocertificazioneDao;
import it.toscana.regione.wsods.ejb.sdm.api.ISdm;
import it.toscana.regione.wsods.ejb.upload.api.IPersistUpload;
import it.toscana.regione.wsods.entity.bean.api.ISoggetto;
//import it.toscana.regione.wsods.entity.bean.impl.AutocertificazioneReg;
import it.toscana.regione.wsods.entity.jpa.api.IEsenzioniFasce;
import it.toscana.regione.wsods.entity.jpa.api.IUploadAutocertificazione;
import it.toscana.regione.wsods.exception.MapperException;
import it.toscana.regione.wsods.exception.SisDataManagerException;
import it.toscana.regione.wsods.exception.UploadException;
import it.toscana.regione.wsods.exception.WsOdsRuntimeException;
import it.toscana.regione.wsods.lib.Mapper;
import it.toscana.regione.wsods.type.Code;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import it.toscana.regione.wsods.ejb.dao.registry.ese.api.IRegistryEsenzioniWriter;


/**
 * @author cciurli
 *
 */
@Stateless(name = IPersistUpload.EJB_REF)
public class PersistUpload implements IPersistUpload {
	
	private static final Logger LOG = LoggerFactory.getLogger(PersistUpload.class);

	@EJB(beanName = IDao.EJB_REF_ESENZIONI_FASCE)
	private IEsenzioniFasceDao esenzioniFasceDao;
	
	@EJB(beanName = IDao.EJB_REF_UPLOAD_AUTOCERTIFICAZIONE)
	private IUploadAutocertificazioneDao uploadAutocertificazioneDao;

	@EJB(beanName = ISdm.EJB_REF)
	private ISdm sdm;	
//
//	@EJB(beanName = IRegistryEsenzioniWriter.EJB_REF)
//	private IRegistryEsenzioniWriter registryEsenzioniWriter;
	
	/**
	 * 
	 */
	public PersistUpload() { }
	
	
	/** {@inheritDoc} */
	@Override @TransactionAttribute(TransactionAttributeType.MANDATORY)
	public void store(final UploadAps2WsOdsRequest value) throws UploadException {
		if(value != null && value.getAutocertificazione()!=null && value.getMetadatiSOGEI()!=null){
			LOG.info("store richiesta di upload");
			try{
				LOG.debug("mappatura elaborazione");
				final IEsenzioniFasce esenzioniFasce = Mapper.map(value.getAutocertificazione(), value.getMetadatiSOGEI().getDataAggRec());

				LOG.debug("anonimizza record esenzioniFasce");
				anonimizza(value, esenzioniFasce);

				LOG.debug("inserisci record esenzioniFasce");
				esenzioniFasceDao.insert(esenzioniFasce, true);

				LOG.debug("mappatura record uploadAutocertificazione");
				final IUploadAutocertificazione uploadAutocertificazione = Mapper.map(esenzioniFasce.getId(),value.getAutocertificazione(),value.getMetadatiSOGEI(), value.getAttivita());

				LOG.debug("inserisci record uploadAutocertificazione");
				uploadAutocertificazioneDao.insert(uploadAutocertificazione, true);

//				final String cfBeneficiario;
//				if(value.getAutocertificazione()!=null && value.getAutocertificazione().getBeneficiario() != null && value.getAutocertificazione().getBeneficiario().getCodiceFiscale()!= null && value.getAutocertificazione().getBeneficiario().getCodiceFiscale().getValue()!=null) {
//					cfBeneficiario= value.getAutocertificazione().getBeneficiario().getCodiceFiscale().getValue();
//				} else {
//					cfBeneficiario = "";
//				}
//				
//				final int inserted = registryEsenzioniWriter.writerOnRegistry(new AutocertificazioneReg(cfBeneficiario, esenzioniFasce));
//				
//				if(inserted==1){
//					LOG.info("inserito record sul registry");
//				} else if(inserted==0){
//					LOG.info("nonostante l'operazione, non sono stati inseriti record sul registry perche' considerate informazioni duplicate");
//				} else {
//					LOG.warn("Questo Caso non dovrebbe mai verificarsi!");
//				}
				
			} catch(WsOdsRuntimeException e){
				throw new UploadException(e);
			} catch(final MapperException e){
				throw new UploadException("impossibile mappare il messaggio di Request ottenuto da aps",e);
			} 
		} else {
			LOG.warn("impossibile effettuare lo store dati insufficenti");
		}
		
	}


	


	private void anonimizza(final UploadAps2WsOdsRequest value, final IEsenzioniFasce esenzioniFasce) throws UploadException {
		if(value.getAutocertificazione()!=null){
			esenzioniFasce.setIdUniDichiarante(extractIdUni(value.getAutocertificazione().getDichiarante()));
			esenzioniFasce.setIdUniTitolare(extractIdUni(value.getAutocertificazione().getTitolare()));
			esenzioniFasce.setIdUniversaleAssistito(extractIdUni(value.getAutocertificazione().getBeneficiario()));
		}
	}
	
	private String extractIdUni(final SoggettoType soggetto) throws UploadException {
		final String idUni;
		
			if(soggetto== null ){
				idUni = null;
			} else if(soggetto.getIdUniversale()!= null && soggetto.getIdUniversale().getValue() != null){
				idUni = soggetto.getIdUniversale().getValue();
			} else if(soggetto.getCodiceFiscale() != null && soggetto.getCodiceFiscale().getValue() !=null) {
				try {
					final ISoggetto s =  sdm.getFromCodiceFiscale(soggetto.getCodiceFiscale().getValue());
					if(s == null ){throw new UploadException(Code.SDM_SOGGETTO_NON_TROVATO);}
					idUni = s.getIdUni();
				} catch (final SisDataManagerException e){
					throw new UploadException("impossibile anonimizzare il soggetto ["+soggetto.getCodiceFiscale().getValue()+"]",e);
				}
			} else {
				idUni = null;;
			}
		
		return idUni;
	}

}

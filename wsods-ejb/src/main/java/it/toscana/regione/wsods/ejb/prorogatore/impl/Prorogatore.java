package it.toscana.regione.wsods.ejb.prorogatore.impl;

import it.toscana.regione.autocertificazionireddito.entity.jaxb.rt.AttivitaType;
import it.toscana.regione.autocertificazionireddito.entity.jaxb.rt.AutocertificazioneType;
import it.toscana.regione.autocertificazionireddito.entity.jaxb.rt.EsitoType;
import it.toscana.regione.autocertificazionireddito.entity.jaxb.rt.ID4SOGEIType;
import it.toscana.regione.autocertificazionireddito.entity.jaxb.rt.UploadWebApp2ApsRequest;
import it.toscana.regione.autocertificazionireddito.entity.jaxb.rt.UploadWebApp2ApsResponse;
import it.toscana.regione.wsods.ejb.prorogatore.api.IProrogatore;
import it.toscana.regione.wsods.ejb.prorogatore.api.ISenderApsUpload;
import it.toscana.regione.wsods.ejb.prorogatore.api.ITransformApsUpload;
import it.toscana.regione.wsods.ejb.sdm.api.ISdm;
import it.toscana.regione.wsods.ejb.tracker.api.ISoapTracking;
import it.toscana.regione.wsods.entity.bean.api.ISoggetto;
import it.toscana.regione.wsods.entity.jpa.api.IAutocertificazioneApertureEtl;
import it.toscana.regione.wsods.entity.jpa.api.IAutocertificazioneChiusureEtl;
import it.toscana.regione.wsods.entity.jpa.api.IAutocertificazioneEtl;
import it.toscana.regione.wsods.entity.jpa.api.IEsitoInvioApertureEtl;
import it.toscana.regione.wsods.entity.jpa.api.IEsitoInvioChiusureEtl;
import it.toscana.regione.wsods.entity.jpa.api.IEsitoInvioEtl;
import it.toscana.regione.wsods.entity.jpa.impl.EsitoInvioApertureEtl;
import it.toscana.regione.wsods.entity.jpa.impl.EsitoInvioChiusureEtl;
import it.toscana.regione.wsods.exception.EjbException;
import it.toscana.regione.wsods.exception.MapperException;
import it.toscana.regione.wsods.exception.ProrogatoreException;
import it.toscana.regione.wsods.exception.SisDataManagerException;
import it.toscana.regione.wsods.exception.TransformException;
import it.toscana.regione.wsods.exception.WsOdsRuntimeException;
import it.toscana.regione.wsods.factory.JaxbElementFactory;
import it.toscana.regione.wsods.lib.Convertitore;
import it.toscana.regione.wsods.lib.Mapper;
import it.toscana.regione.wsods.lib.SOAPTool;
import it.toscana.regione.wsods.type.Code;

import java.sql.Timestamp;
import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.xml.bind.JAXBElement;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;


@Stateless(name=IProrogatore.EJB_REF)
public class Prorogatore implements IProrogatore {
	
	private static final Logger LOG = LoggerFactory.getLogger(Prorogatore.class);
	
	public Prorogatore() { }
	

	@EJB(beanName= ISoapTracking.EJB_REF)
	private ISoapTracking soapTracking;

	@EJB(beanName= ITransformApsUpload.EJB_REF)
	private ITransformApsUpload transformer;

	@EJB(beanName= ISenderApsUpload.EJB_REF)
	private ISenderApsUpload sender;
	
	
	@EJB(beanName = ISdm.EJB_REF)
	private ISdm sdm;
	
	@Override @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public IEsitoInvioApertureEtl proroga(final IAutocertificazioneApertureEtl autocertificazioniETL) throws ProrogatoreException {
		final IEsitoInvioApertureEtl esito = new EsitoInvioApertureEtl();
		proroga(autocertificazioniETL, esito);
		return esito;
	}


	@Override @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public IEsitoInvioChiusureEtl proroga(final IAutocertificazioneChiusureEtl autocertificazioniETL) throws ProrogatoreException {
		final IEsitoInvioChiusureEtl esito = new EsitoInvioChiusureEtl();
		proroga(autocertificazioniETL, esito);
		return esito;
	}
	
	private IEsitoInvioEtl proroga(final IAutocertificazioneEtl autocertificazioniETL, final IEsitoInvioEtl esitoInvioEtl) throws ProrogatoreException {
		if(autocertificazioniETL == null){ throw new ProrogatoreException(Code.PARAMETRO_NULLO, "impossibile creatre un SOAPMessage di prorogaa partire da un autocertificazione nulla"); }
		if(esitoInvioEtl == null){ throw new ProrogatoreException(Code.PARAMETRO_NULLO, "impossibile procedere sensa aver preparato un esito"); }
			
		final boolean trackedIsEnabled = sender.isTrackerEnabled();
		final String src = sender.getSrc();
		final String dest = sender.getDest();
		
		final long idTrack = soapTracking.tracke(trackedIsEnabled, src, dest);
		
		autocertificazioniETL.setDataInvio(new Date());
		esitoInvioEtl.setFkAutocertificazioneEtl(autocertificazioniETL.getId());
		esitoInvioEtl.setFkWsodsSoapTracking(idTrack);
		
		try {
			LOG.debug(" si costruise il messaggio di request per la proroga con id {}", autocertificazioniETL.getId());
			
			final JAXBElement<UploadWebApp2ApsRequest> jaxbRequest = costruisciUploadWebApp2ApsRequest(autocertificazioniETL);
		
			
		
			final SOAPElement element;
			try {
				element = transformer.transform(jaxbRequest);
			} catch (TransformException e) {
				throw new ProrogatoreException(e);
			}
			
			final SOAPMessage request;
			try {
				request = sender.getEmptySoap();
			} catch (EjbException e) {
				throw new ProrogatoreException(e);
			}
			
			if (request == null) { throw new ProrogatoreException(Code.GENERICO, "impossibile creatre un SOAPMessage vuoto  per la request"); }
			
			try {
				SOAPTool.addBody(request, element);
			} catch (WsOdsRuntimeException e) {
				throw new ProrogatoreException(e);
			}

			final SOAPMessage response;
			try {
				response = sender.send(idTrack,request);
			} catch (EjbException e) {
				throw new ProrogatoreException(e);
			}
			
			final Node node;
			try {
				node = SOAPTool.extractNode(response);
			} catch (WsOdsRuntimeException e) {
				throw new ProrogatoreException(e);
			}
			
			if (node == null) { throw new ProrogatoreException(Code.GENERICO, "non e' stato possibile estrarre dalla response il contenuto del body"); }
			
			final JAXBElement<UploadWebApp2ApsResponse> jaxbResponse;
			try {
				jaxbResponse = transformer.transform(node);
			} catch (TransformException e) {
				throw new ProrogatoreException(e);
			}
		
			if(jaxbResponse== null || jaxbResponse.getValue()==null){ throw new ProrogatoreException(Code.GENERICO, "non e' stato possibile ottenere l'oggetto jaxb dalla response");}
			
			final EsitoType esito = jaxbResponse.getValue().getEsito();
			final String codiceEsito = jaxbResponse.getValue().getCodiceErrore();
			final String descrizione = jaxbResponse.getValue().getDescrizione();
			
			esitoInvioEtl.setEsito(esito.toString());
			esitoInvioEtl.setEsitoCod(codiceEsito);
			esitoInvioEtl.setEsitoDesc(descrizione);
			
			

			if(EsitoType.OK.equals(esito)){
				soapTracking.trackeOk(trackedIsEnabled, idTrack);
				autocertificazioniETL.setStatoElaborazione(IAutocertificazioneEtl.STATO_INVIATA_CON_ESITO_POSITIVO);
			} else {
				soapTracking.trackeKo(trackedIsEnabled, idTrack);
				
				if(isBrackerEsito(esitoInvioEtl)){
					autocertificazioniETL.setStatoElaborazione(IAutocertificazioneEtl.STATO_INVIATA_CON_ESITO_NEGATIVO_BLOCCANTE);
				} else {
					autocertificazioniETL.setStatoElaborazione(IAutocertificazioneEtl.STATO_INVIATA_CON_ESITO_NEGATIVO_REINVIABILE);
				}
			}
			
			
			
		} catch(final ProrogatoreException e){

			final String stackTrace = Convertitore.converti(e);
			soapTracking.trackeKo(trackedIsEnabled, idTrack, stackTrace);
			
			if(isBrackerException(e)){
				autocertificazioniETL.setStatoElaborazione(IAutocertificazioneEtl.STATO_INVIATA_CON_ESITO_NEGATIVO_BLOCCANTE);
			} else {
				autocertificazioniETL.setStatoElaborazione(IAutocertificazioneEtl.STATO_INVIATA_CON_ESITO_NEGATIVO_REINVIABILE);
			}
			
			esitoInvioEtl.setEsito(EsitoType.KO.toString());
			esitoInvioEtl.setStackTrace(stackTrace);
		}

		autocertificazioniETL.incrementaElaborazioni();
		autocertificazioniETL.setDataAggiornamento(new Timestamp(System.currentTimeMillis()));
		
		return esitoInvioEtl;
		
	}


	private static boolean isBrackerEsito(final IEsitoInvioEtl esitoInvioEtl ){
		boolean isBrackerEsito = true;
		if(esitoInvioEtl!= null && esitoInvioEtl.getEsitoCod()!=null && esitoInvioEtl.getEsitoCod().trim().length()>0){
			final String codiceEsito = esitoInvioEtl.getEsitoCod().trim();
			final String[] codiciEsitoAmmessi= new String[]{
//					"2002", //Servizio SOGEI per le aperture non risponde al compoente APS
//					"2002", //Servizio SOGEI per le aperture non risponde al compoente APS
//					"2003"  //Servizio SOGEI per le chiusure non risponde al compoente APS
				};
			if(codiciEsitoAmmessi!= null && codiciEsitoAmmessi.length>0){
				for(String codiceEsitoAmmesso : codiciEsitoAmmessi){
					if(isBrackerEsito) {isBrackerEsito = ! codiceEsito.equalsIgnoreCase(codiceEsitoAmmesso);}
				}
			}
			
		}
		return isBrackerEsito;
	}

	private static boolean isBrackerException(final ProrogatoreException e){
		if(Code.DATO_INCONGRUO.equals(e.code) || Code.DATO_MANCANTE.equals(e.code)){
			return true;
		} else {
			return false;
		}
		
	}
	private JAXBElement<UploadWebApp2ApsRequest> costruisciUploadWebApp2ApsRequest(final IAutocertificazioneEtl autocertificazioniETL) throws ProrogatoreException {
		
		final JAXBElement<UploadWebApp2ApsRequest> jaxb = JaxbElementFactory.createUploadWebApp2ApsRequest();
		if (jaxb.getValue() == null) { throw new ProrogatoreException(Code.GENERICO, "impossibile creatre un la request a causa del jaxb vuoto"); }


		final ID4SOGEIType id4sogeiType = JaxbElementFactory.createID4SOGEIType();

		id4sogeiType.setUserId("");
		id4sogeiType.setUtenteToken("DAL__PROROGATORE");
		
		jaxb.getValue().setId4Sogei(id4sogeiType);
		
		final AutocertificazioneType autocertificazioneType = JaxbElementFactory.createAutocertificazioneType();

		try {
			if(autocertificazioniETL instanceof IAutocertificazioneApertureEtl){
				jaxb.getValue().setAttivita(AttivitaType.INSERISCI);
				Mapper.map((IAutocertificazioneApertureEtl)autocertificazioniETL, autocertificazioneType);
			} else if (autocertificazioniETL instanceof IAutocertificazioneChiusureEtl) {
				jaxb.getValue().setAttivita(AttivitaType.REVOCA);
				Mapper.map((IAutocertificazioneChiusureEtl)autocertificazioniETL, autocertificazioneType);
			} else {
				throw new ProrogatoreException(Code.DATO_MANCANTE,"impossibile cortruire la request attivita' non identificata");
			}
		} catch (final MapperException e) {
			throw new ProrogatoreException("impossibile cortruire la request",e);
		}

		mappaSoggetti(autocertificazioniETL, autocertificazioneType);
		jaxb.getValue().setAutocertificazione(autocertificazioneType);
		
		
		return jaxb;
	}
	private void mappaSoggetti(final IAutocertificazioneEtl autocertificazioniETL, final AutocertificazioneType autocertificazioneType) throws ProrogatoreException {
		if(autocertificazioniETL== null){throw new ProrogatoreException(Code.NULL_INATTESSO,"impossibile mappare una autocertificazioniETL nulla"); }

		final String beneficiario =  convertiIdUniInCf(autocertificazioniETL.getIdUniBeneficiario());
		final String dichiarante = convertiIdUniInCf(autocertificazioniETL.getIdUniDichiarante());
		final String titolare = convertiIdUniInCf(autocertificazioniETL.getIdUniTitolare());

		
		autocertificazioneType.setDichiarante(JaxbElementFactory.createSoggettoTypeCodiceFiscale(dichiarante));
		autocertificazioneType.setTitolare(JaxbElementFactory.createSoggettoTypeCodiceFiscale(titolare));
		autocertificazioneType.setBeneficiario(JaxbElementFactory.createSoggettoTypeCodiceFiscale(beneficiario));
		
	}
	private String convertiIdUniInCf(final String idUni) throws ProrogatoreException {
		if(idUni == null ){ return null; }
		try {
			final ISoggetto soggetto = sdm.getFromIdUniversaleNoTransaction(idUni);
			
			if(soggetto==null){ return null; }
			return soggetto.getCodiceFiscale();
		} catch(SisDataManagerException e){
			throw new ProrogatoreException("Impossibile deanoninmizzare il soggetto con idUni ["+idUni+"] ",e);
		}
	}

	
}

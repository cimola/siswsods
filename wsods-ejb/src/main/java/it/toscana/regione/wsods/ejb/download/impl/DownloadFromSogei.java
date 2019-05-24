package it.toscana.regione.wsods.ejb.download.impl;

import it.toscana.regione.autocertificazionireddito.entity.jaxb.sogei.download.request.DownloadAutocertificazione;
import it.toscana.regione.certificazionireddito.entity.jaxb.sogei.download.request.DownloadCertificazione;
import it.toscana.regione.wsods.ejb.download.api.IDownloadFromSogei;
import it.toscana.regione.wsods.ejb.download.api.ISenderDownload;
import it.toscana.regione.wsods.ejb.download.api.ITransformDownload;
import it.toscana.regione.wsods.ejb.download.api.ITransformDownloadCert;
import it.toscana.regione.wsods.ejb.tracker.api.ISoapTracking;
import it.toscana.regione.wsods.exception.ConvertitoreException;
import it.toscana.regione.wsods.exception.DownloadException;
import it.toscana.regione.wsods.factory.JaxbElementFactory;
import it.toscana.regione.wsods.lib.Convertitore;
import it.toscana.regione.wsods.lib.SOAPTool;
import it.toscana.regione.wsods.singleton.Conf;
import it.toscana.regione.wsods.type.Code;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.xml.bind.JAXBElement;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;

/**
 * @author cciurli
 *
 */
@Stateless(name = IDownloadFromSogei.EJB_REF)
public class DownloadFromSogei implements IDownloadFromSogei {
	
	private final static Logger LOG = LoggerFactory.getLogger(DownloadFromSogei.class);
	
	@EJB(beanName=ITransformDownload.EJB_REF)
	private ITransformDownload transformer;
	
	@EJB(beanName=ISenderDownload.EJB_REF)
	private ISenderDownload sender;
	
	@EJB(beanName=ITransformDownloadCert.EJB_REF)
	private ITransformDownloadCert transformerCert;
	
	@EJB(beanName=ISenderDownload.EJB_CERT_REF)
	private ISenderDownload senderCert;
	
	@EJB(beanName=ISoapTracking.EJB_REF)
	private ISoapTracking tracker;
	
	
	public DownloadFromSogei() {
		super();
	}
	
	private JAXBElement<DownloadAutocertificazione> generaJaxb(final String dataOraLimite, final String tipologia) throws DownloadException {
		final long start = System.currentTimeMillis();
		try {
			final JAXBElement<DownloadAutocertificazione> jaxb = JaxbElementFactory.createSogeiDownloadRequest();
			
			if (jaxb == null) { throw new DownloadException(Code.GENERICO, "impossibile creatre un jaxb per la request"); }
			
			if (jaxb.getValue() != null) {
				jaxb.getValue().setDataOraLimite(dataOraLimite);
				jaxb.getValue().setTipoAccesso(tipologia);
				jaxb.getValue().setUserId(Conf.getSogeiUserId());
				jaxb.getValue().setUtenteToken(Conf.getSogeiUserToken());
			} else {
				throw new DownloadException(Code.GENERICO, "il messaggio nel jaxb creato per la request e' nullo");
			}
			return jaxb;
		} finally {
			LOG.debug("[PERFORMANCE] - costruisci messaggio jaxb di request in {}.", System.currentTimeMillis() - start);
		}
	}
	
	private JAXBElement<DownloadCertificazione> generaJaxbCert(final String dataLimite, final String tipologia) throws DownloadException {
		final long start = System.currentTimeMillis();
		try {
				final JAXBElement<DownloadCertificazione> jaxb = JaxbElementFactory.createSogeiDownloadRequestCert();
				
				if (jaxb == null) { throw new DownloadException(Code.GENERICO, "impossibile creatre un jaxb per la request DownloadCertificazione"); }
				
				if (jaxb.getValue() != null) {
					jaxb.getValue().setDataLimite(dataLimite);
					jaxb.getValue().setOperazione(tipologia);
					jaxb.getValue().setUtente(Conf.getSogeiCertUserId());
					jaxb.getValue().setUtenteToken(Conf.getSogeiCertUserToken());
				} else {
					throw new DownloadException(Code.GENERICO, "il messaggio nel jaxb creato per la request e' nullo");
				}
				return jaxb;
		} finally {
			LOG.debug("[PERFORMANCE] - costruisci messaggio jaxb di request in {}.", System.currentTimeMillis() - start);
		}
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public it.toscana.regione.autocertificazionireddito.entity.jaxb.sogei.download.response.Ricevuta send(final String dataOraLimite, final String tipologia) throws DownloadException {
		final long start = System.currentTimeMillis();
		try {
			LOG.info("invio messaggio di richiesta download per le tipologie {} nella data limite {} ", tipologia, dataOraLimite);
	
			final JAXBElement<DownloadAutocertificazione> jaxb = generaJaxb(dataOraLimite, tipologia);
			
			final SOAPElement element = transformer.transform(jaxb);
			 
			final SOAPMessage request = sender.getEmptySoap(); 
	
			if (request == null) { throw new DownloadException(Code.GENERICO, "impossibile creatre un SOAPMessage vuoto  per la request"); }
			
			SOAPTool.addBody(request, element);
			
			final long idTrack = tracker.tracke(sender.isTrackerEnabled(), sender.getSrc(),sender.getDest());
			final it.toscana.regione.autocertificazionireddito.entity.jaxb.sogei.download.response.Ricevuta value;
			final String esito;
			try{
				final SOAPMessage response = sender.send(idTrack,request);
			
				final Node node = SOAPTool.extractNode(response);
			
				if (node == null) {
					throw new DownloadException(Code.GENERICO, "non e' stato possibile estrarre dalla response il contenuto del body");
				}
			
				final JAXBElement<it.toscana.regione.autocertificazionireddito.entity.jaxb.sogei.download.response.Ricevuta> result = transformer.transform(node);
			
				value = result.getValue();
			
				if (value == null) {
					throw new DownloadException(Code.GENERICO, "il messaggio contenuto nel jaxb di response e' nullo");
				}
				
				esito = value.getEsito();
	
				
			} catch(final DownloadException e){
				tracker.trackeKo(sender.isTrackerEnabled(),idTrack, Convertitore.converti(e));
				throw e;
			} catch(final RuntimeException e){
				tracker.trackeKo(sender.isTrackerEnabled(),idTrack, Convertitore.converti(e));
				throw e;
			}
			final Code codeEsito;
			try{
				codeEsito = Code.identifica(esito);
			} catch (ConvertitoreException e){
				final DownloadException ex = new DownloadException("esito non decodificato",e);
				tracker.trackeKo(sender.isTrackerEnabled(),idTrack, Convertitore.converti(ex));
				throw ex;
			}
			if(Code.SOGEI_DOWNLOAD_ELABORAZIONE_CORRETTAMENTE_EFFETTUATA.equals(codeEsito)){
				tracker.trackeOk(sender.isTrackerEnabled(),idTrack);
			} else {
				tracker.trackeKo(sender.isTrackerEnabled(),idTrack);
			}
			return value;
		} finally{
			LOG.debug("[PERFORMANCE] - invio richiesta di download in {}.", System.currentTimeMillis() - start);
		}
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public it.toscana.regione.certificazionireddito.entity.jaxb.sogei.download.response.Ricevuta sendCert(final String dataLimite, final String tipologia) throws DownloadException {
		final long start = System.currentTimeMillis();
		try {
			LOG.info("invio messaggio di richiesta download per le tipologie {} nella data limite {} ", tipologia, dataLimite);
	
			final JAXBElement<DownloadCertificazione> jaxb = generaJaxbCert(dataLimite, tipologia);
			
			final SOAPElement element = transformerCert.transform(jaxb);
			 
			final SOAPMessage request = senderCert.getEmptySoap(); 
			
			if (request == null) { throw new DownloadException(Code.GENERICO, "impossibile creare un SOAPMessage vuoto per la request"); }
			
			SOAPTool.addBody(request, element);
			
			final long idTrack = tracker.tracke(senderCert.isTrackerEnabled(), senderCert.getSrc(),senderCert.getDest());
			final it.toscana.regione.certificazionireddito.entity.jaxb.sogei.download.response.Ricevuta value;
			final String esito;
			try{
				final SOAPMessage response = senderCert.send(idTrack,request);
				
				final Node node = SOAPTool.extractNode(response);
			
				if (node == null) {
					throw new DownloadException(Code.GENERICO, "non e' stato possibile estrarre dalla response il contenuto del body");
				}
			
				final JAXBElement<it.toscana.regione.certificazionireddito.entity.jaxb.sogei.download.response.Ricevuta> result = transformerCert.transform(node);
			
				value = result.getValue();
			
				if (value == null) {
					throw new DownloadException(Code.GENERICO, "il messaggio contenuto nel jaxb di response e' nullo");
				}
				
				esito = value.getEsito();
				
			} catch(final DownloadException e){
				tracker.trackeKo(senderCert.isTrackerEnabled(),idTrack, Convertitore.converti(e));
				throw e;
			} catch(final RuntimeException e){
				tracker.trackeKo(senderCert.isTrackerEnabled(),idTrack, Convertitore.converti(e));
				throw e;
			}
			final Code codeEsito;
			try{
				codeEsito = Code.identifica(esito);
			} catch (ConvertitoreException e){
				final DownloadException ex = new DownloadException("esito non decodificato",e);
				tracker.trackeKo(senderCert.isTrackerEnabled(),idTrack, Convertitore.converti(ex));
				throw ex;
			}
			if(Code.SOGEI_CERTIFICAZIONI_ELABORAZIONE_CORRETTAMENTE_EFFETTUATA.equals(codeEsito)){
				tracker.trackeOk(senderCert.isTrackerEnabled(),idTrack);
			} else {
				tracker.trackeKo(senderCert.isTrackerEnabled(),idTrack);
			}
			return value;
		} finally{
			LOG.debug("[PERFORMANCE] - invio richiesta di download in {}.", System.currentTimeMillis() - start);
		}
	}
	
	public static String getXml(SOAPMessage msg)
	{
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			msg.writeTo(out);
		} catch (SOAPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String strMsg = new String(out.toByteArray());
		LOG.info("xml del messaggio soap ", strMsg);
		
		return strMsg;
	}
	
}

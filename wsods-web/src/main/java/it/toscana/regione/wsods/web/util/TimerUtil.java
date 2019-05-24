package it.toscana.regione.wsods.web.util;

import it.toscana.regione.wsods.ejb.timer.api.ITimer;
import it.toscana.regione.wsods.ejb.timer.bean.InfoTimer;
import it.toscana.regione.wsods.singleton.Conf;
import it.toscana.regione.wsods.singleton.PIL;

import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TimerUtil {
	
	private final static Logger	LOG	= LoggerFactory.getLogger(TimerUtil.class);
	
	public TimerUtil() { super(); }
	
	private static void initTimer(final InfoTimer infoTimer){
		try{
			final long timeout = infoTimer.getTimeOut();
			final ITimer timer = PIL.lookup(infoTimer.getEjbRef(),ITimer.class);
			if(timeout>0L){
				timer.createTimer(infoTimer);
				LOG.info("Timer [{}] enabled",infoTimer.getEjbRef());
			} else {
				LOG.info("Timer [{}] disabled",infoTimer.getEjbRef());
			}
		} catch (NamingException e) {
			LOG.error("Impossiblie effettuare la lookup dei timer",e);
		}
		
	}
	public static void destroyTimer(final String ejbRef){
		if(ejbRef!= null && ejbRef.trim().length()>0){
		try{
			final ITimer timer = PIL.lookup(ejbRef.trim(),ITimer.class);
			timer.stopTimers();
		} catch (NamingException e) {
			LOG.warn("Impossiblie effettuare la lookup dei timer e quindi la sua destroy");
		}
		} else {
			LOG.warn("si e' cercato di stoppare un time con un ejbRef nulla o vuota");
		}
	}

	public static int initTimer(final String ejbRef,final long numeroTimer,final long timeout){
		if(getListaEjbRefTimer().contains(ejbRef)){
			final InfoTimer[] infoTimer = InfoTimer.createArrayInfoTimer(ejbRef, numeroTimer, timeout);
			for(int i = 0; i < infoTimer.length; i++){
				initTimer(infoTimer[i]);
			}
			return infoTimer.length;
		} else {
			LOG.warn("creazione del timer {} fallita",ejbRef);
			return 0;
		}
	}
	

	public static void initTimer(){
		final List<String> ejbRefTimers = getListaEjbRefTimer();
		for(final String ejbRef : ejbRefTimers){
			initTimer(ejbRef);
		}
		
	}
	
	public static void destroyTimer(){	
		final List<String> ejbRefTimers = getListaEjbRefTimer();
		for(final String ejbRef : ejbRefTimers){
			destroyTimer(ejbRef);
		}
	}
	
	public static List<String> getListaEjbRefTimer(){		
		final List<String> ejbRefTimers = new ArrayList<String>();
		
		
		ejbRefTimers.add(ITimer.EJB_REF_DOWNLOAD_INSERIMENTI_FROM_SOGEI);
		ejbRefTimers.add(ITimer.EJB_REF_DOWNLOAD_VARIAZIONI_FROM_SOGEI);

		ejbRefTimers.add(ITimer.EJB_REF_SVECCHIATORE);
		
		ejbRefTimers.add(ITimer.EJB_REF_RIELABORATORE);
		
		ejbRefTimers.add(ITimer.EJB_REF_RIELABORATORE_CERT);
		
		ejbRefTimers.add(ITimer.EJB_REF_RECUPERA_SCARTATI);
		
		ejbRefTimers.add(ITimer.EJB_REF_RECUPERA_SCARTATI_CERT);
		
		ejbRefTimers.add(ITimer.EJB_REF_PROROGATORE);

		ejbRefTimers.add(ITimer.EJB_REF_ELABORATORE_ASINCORNO);
		
		ejbRefTimers.add(ITimer.EJB_REF_ELABORATORE_ASINCORNO_CERT);
		
//		ejbRefTimers.add(ITimer.EJB_REF_VALUTATORE_DI_RETTIFICA_E01_TIMER);
//		ejbRefTimers.add(ITimer.EJB_REF_VALUTATORE_DI_RETTIFICA_ERA_TIMER);
//		
//		ejbRefTimers.add(ITimer.EJB_REF_RETTIFICATORE_E01_TIMER);
//		ejbRefTimers.add(ITimer.EJB_REF_RETTIFICATORE_ERA_TIMER);

		ejbRefTimers.add(ITimer.EJB_REF_DOWNLOAD_RECUPERO_INSERIMENTI_FROM_SOGEI);
		ejbRefTimers.add(ITimer.EJB_REF_DOWNLOAD_RECUPERO_VARIAZIONI_FROM_SOGEI);
		ejbRefTimers.add(ITimer.EJB_REF_DOWNLOAD_RECUPERO_VARIAZIONI_AL_MINUTO_FROM_SOGEI);
		
		ejbRefTimers.add(ITimer.EJB_CERT_REF_DOWNLOAD_ATTESTAZIONI_FROM_SOGEI);
		ejbRefTimers.add(ITimer.EJB_CERT_REF_DOWNLOAD_RECUPERO_ATTESTAZIONI_FROM_SOGEI);
		
		
		ejbRefTimers.add(ITimer.EJB_REF_RECUPERO_ID_UNI_OBSOLETI);
		ejbRefTimers.add(ITimer.EJB_REF_AGGIORNAMENTO_ID_UNI_OBSOLETI);
		
		ejbRefTimers.add(ITimer.EJB_REF_RECUPERA_SCARTATI_GIANOV4);
		ejbRefTimers.add(ITimer.EJB_REF_RECUPERA_SCARTATI_GIANOV4_CERT);
		
		
		ejbRefTimers.add(ITimer.EJB_REF_RICALCOLA_TITOLO_NULL);
		
		return ejbRefTimers;
		
	}
	public static int initTimer(final String ejbRef){
		
		if(getListaEjbRefTimer().contains(ejbRef)){
			final long numeroTimer;
			final long timeout;
			if(ITimer.EJB_REF_DOWNLOAD_INSERIMENTI_FROM_SOGEI.equals(ejbRef)){
				numeroTimer = 1L;
				timeout = Conf.getTimeoutDownloadInserimenti();
				
			} else if (ITimer.EJB_REF_DOWNLOAD_VARIAZIONI_FROM_SOGEI.equals(ejbRef)) {
				numeroTimer = 1L;
				timeout = Conf.getTimeoutDownloadVariazioni();
				
			} else if (ITimer.EJB_REF_SVECCHIATORE.equals(ejbRef)) {
				numeroTimer = 1L;
				timeout = Conf.getSvecchiatoreTimeout();
				
			} else if (ITimer.EJB_REF_RIELABORATORE.equals(ejbRef)) {	
				numeroTimer = Conf.getNumroTimerRielaborazione();
				timeout = Conf.getRielaboratoreTimeout();
				
			}  else if (ITimer.EJB_REF_RIELABORATORE_CERT.equals(ejbRef)) {	
				numeroTimer = Conf.getNumroTimerRielaborazioneCert();
				timeout = Conf.getRielaboratoreTimeoutCert();
				
			} else if (ITimer.EJB_REF_RECUPERA_SCARTATI.equals(ejbRef)) {	
				numeroTimer = 1L;
				timeout = Conf.getRecuperoScartatiTimeOut();	
				
			} else if (ITimer.EJB_REF_RECUPERA_SCARTATI_CERT.equals(ejbRef)) {	
				numeroTimer = 1L;
				timeout = Conf.getRecuperoScartatiTimeOutCert();	
				
			} else if (ITimer.EJB_REF_PROROGATORE.equals(ejbRef)) {	
				numeroTimer = Conf.getProrogheNumroTimer();
				timeout = Conf.getProrogheTimeOut();
				
			} else if (ITimer.EJB_REF_ELABORATORE_ASINCORNO.equals(ejbRef)) {	
				numeroTimer = 1L;
				timeout = Conf.getTimeoutElaboratoreAsincrono();
				
			} else if (ITimer.EJB_REF_ELABORATORE_ASINCORNO_CERT.equals(ejbRef)) {	
				numeroTimer = 1L;
				timeout = Conf.getTimeoutElaboratoreAsincronoCert();
				
//			} else if (ITimer.EJB_REF_VALUTATORE_DI_RETTIFICA_E01_TIMER.equals(ejbRef)) {	
//				numeroTimer = 1L;
//				timeout = Conf.getTimeoutValutatoreDiRettificaAutocertificazioniE01();
//				
//			} else if (ITimer.EJB_REF_VALUTATORE_DI_RETTIFICA_ERA_TIMER.equals(ejbRef)) {
//				numeroTimer = 1L;
//				timeout = Conf.getTimeoutValutatoreDiRettificaAutocertificazioniERA();
//				
//			} else if (ITimer.EJB_REF_RETTIFICATORE_E01_TIMER.equals(ejbRef)) {
//				numeroTimer = 1L;
//				timeout = Conf.getTimeoutRettificatoreAutocertificazioniE01();
//				
//			} else if (ITimer.EJB_REF_RETTIFICATORE_ERA_TIMER.equals(ejbRef)) {
//				numeroTimer = 1L;
//				timeout = Conf.getTimeoutVRettificatoreAutocertificazioniERA();
//				
			} else  if(ITimer.EJB_REF_DOWNLOAD_RECUPERO_INSERIMENTI_FROM_SOGEI.equals(ejbRef)){
				numeroTimer = 1L;
				timeout = Conf.getTimeoutDownloadRecuperoInserimenti();
				
			} else if (ITimer.EJB_REF_DOWNLOAD_RECUPERO_VARIAZIONI_FROM_SOGEI.equals(ejbRef)) {
				numeroTimer = 1L;
				timeout = Conf.getTimeoutDownloadRecuperoVariazioni();
				
			} else if (ITimer.EJB_REF_DOWNLOAD_RECUPERO_VARIAZIONI_AL_MINUTO_FROM_SOGEI.equals(ejbRef)) {
				numeroTimer = 1L;
				timeout = Conf.getTimeoutDownloadRecuperoVariazioniAlMinuto();
				
			} else if(ITimer.EJB_CERT_REF_DOWNLOAD_ATTESTAZIONI_FROM_SOGEI.equals(ejbRef)) {
				numeroTimer = 1L;
				timeout = Conf.getTimeoutDownloadAttestazioni();
				
			} else if(ITimer.EJB_CERT_REF_DOWNLOAD_RECUPERO_ATTESTAZIONI_FROM_SOGEI.equals(ejbRef)) {
					numeroTimer = 1L;
					timeout = Conf.getTimeoutDownloadRecuperoAttestazioni();
					
			} else if (ITimer.EJB_REF_RECUPERO_ID_UNI_OBSOLETI.equals(ejbRef)) {
				numeroTimer = 1L;
				timeout = Conf.getRecuperoIdUniObsoletiTimeOut();
				
			} else if (ITimer.EJB_REF_AGGIORNAMENTO_ID_UNI_OBSOLETI.equals(ejbRef)) {	
				numeroTimer = 1L;
				timeout = Conf.getAggiornaIdUniObsoletiTimeOut();

			} else if (ITimer.EJB_REF_RECUPERA_SCARTATI_GIANOV4.equals(ejbRef)) {	
				numeroTimer = 1L;
				timeout = Conf.getRecuperoScartatiGianov4TimeOut();	
				
			} else if (ITimer.EJB_REF_RECUPERA_SCARTATI_GIANOV4_CERT.equals(ejbRef)) {	
				numeroTimer = 1L;
				timeout = Conf.getRecuperoScartatiGianov4TimeOutCert();	
				
			} else if (ITimer.EJB_REF_RICALCOLA_TITOLO_NULL.equals(ejbRef)) {	
				numeroTimer =  Conf.getRicalcolaTitoloNullNumroTimer();
				timeout = Conf.getRicalcolaTitoloNullTimeOut();
				
			} else {
				LOG.warn("timer presente nella lista ma di cui non si e' stabilito il sistema di start inesistente [{}]",ejbRef);
				numeroTimer = 0L;
				timeout = 0L;
				
			}
			
			return initTimer(ejbRef, numeroTimer, timeout);
			
			
		} else {
			LOG.warn("cercato di inizializzare un timer inesistente [{}]",ejbRef);
			return 0;
		}
	}
}

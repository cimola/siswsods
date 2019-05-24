/** */
package it.toscana.regione.wsods.ejb.timer.api;


import it.toscana.regione.wsods.ejb.timer.bean.InfoTimer;

import java.io.Serializable;
import java.util.Collection;

import javax.ejb.Timer;


/**
 * @author cciurli
 *
 */
public interface ITimer extends Serializable {
	
	static final String EJB_REF_DOWNLOAD_INSERIMENTI_FROM_SOGEI = "DownloadInserimentiFormSogeiTimer";
	static final String EJB_REF_DOWNLOAD_VARIAZIONI_FROM_SOGEI = "DownloadVariazioniFormSogeiTimer";
	static final String EJB_REF_SVECCHIATORE = "SvecchiatoreTimer";
	static final String EJB_REF_RIELABORATORE = "RielaboratoreTimer";
	static final String EJB_REF_RIELABORATORE_CERT = "RielaboratoreTimerCert";
	static final String EJB_REF_RECUPERA_SCARTATI = "RecuperaScartatiTimer";
	static final String EJB_REF_RECUPERA_SCARTATI_GIANOV4 = "RecuperaScartatiGianov4Timer";
	static final String EJB_REF_RECUPERA_SCARTATI_CERT = "RecuperaScartatiTimerCert";
	static final String EJB_REF_RECUPERA_SCARTATI_GIANOV4_CERT = "RecuperaScartatiGianov4TimerCert";
	static final String EJB_REF_PROROGATORE = "ProrogatoreTimer";
	static final String EJB_REF_DOWNLOAD_FROM_SOGEI = "DownloadFormSogeiTimer";
	static final String EJB_REF_ELABORATORE_ASINCORNO = "ElaborazioneAsincronaDownloadTimer";
	static final String EJB_REF_ELABORATORE_ASINCORNO_CERT = "ElaborazioneAsincronaDownloadTimerCert";
//	static final String EJB_REF_RETTIFICATORE_E01_TIMER = "RettificatoreE01Timer";
//	static final String EJB_REF_RETTIFICATORE_ERA_TIMER = "RettificatoreERATimer";
//	static final String EJB_REF_VALUTATORE_DI_RETTIFICA_E01_TIMER = "ValutatoreDiRettificaE01Timer";
//	static final String EJB_REF_VALUTATORE_DI_RETTIFICA_ERA_TIMER = "ValutatoreDiRettificaERATimer";


	static final String EJB_REF_DOWNLOAD_RECUPERO_INSERIMENTI_FROM_SOGEI = "DownloadRecuperoInserimentiFormSogeiTimer";
	static final String EJB_REF_DOWNLOAD_RECUPERO_VARIAZIONI_FROM_SOGEI = "DownloadRecuperoVariazioniFormSogeiTimer";
	static final String EJB_REF_DOWNLOAD_RECUPERO_VARIAZIONI_AL_MINUTO_FROM_SOGEI = "DownloadRecuperoVariazioniAlMinutoFormSogeiTimer";
	static final String EJB_CERT_REF_DOWNLOAD_RECUPERO_ATTESTAZIONI_FROM_SOGEI = "DownloadRecuperoAttestazioniFormSogeiTimer";
	static final String EJB_CERT_REF_DOWNLOAD_ATTESTAZIONI_FROM_SOGEI = "DownloadAttestazioniFormSogeiTimer";

	static final String EJB_REF_RECUPERO_ID_UNI_OBSOLETI = "RecuperoIdUniObsoletiTimer";
	static final String EJB_REF_AGGIORNAMENTO_ID_UNI_OBSOLETI = "AggiornamentoIdUniObsoletiTimer";
	static final String EJB_REF_RICALCOLA_TITOLO_NULL = "RicalcoaTitoloNullTimer";
	
	void createTimer(final InfoTimer info);

	void stopTimers();
	

	Collection<Timer> getTimers();
}

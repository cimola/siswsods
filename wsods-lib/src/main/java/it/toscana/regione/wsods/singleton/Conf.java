/** */
package it.toscana.regione.wsods.singleton;

import it.toscana.regione.wsods.costanti.Prop;
import it.toscana.regione.wsods.costanti.Varie;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * @author cciurli
 *
 */
public class Conf {

	private final static Logger	LOG	 = LoggerFactory.getLogger(Conf.class);
	
	private final static String DEFAULT_PROPERTIES_FILE  = "META-INF/conf.properties";
	public final static String DEFAULT_ENABLED_VALUE  = "ENABLED";
	public final static String DEFAULT_TRUE_VALUE  = Boolean.TRUE.toString();
	private final Properties prop;
	
	
	/** */
	private Conf() { 
		super();
		prop = new Properties();
	}

	private static Conf instance_ = null;
	
	private synchronized static Conf get(){
		if(instance_ == null){
			instance_ = new Conf();
		}
		return instance_;
	}
	private synchronized static void set(final String key, final String value){
		if(key!=null && key.trim().length()>0 && value!=null && value.trim().length()>0){
			get().prop.setProperty(key, value);
		} else {
			final String msg = "impossibile impostare la properties key ["+key+"] value ["+value+"]";
			final RuntimeException re = new RuntimeException(msg);
			LOG.error(msg,re);
			throw re;
		}
	}
	private synchronized static String get(final String key, final String defaultValue){
		if(key==null || key.trim().length()==0 || !get().prop.containsKey(key)){return defaultValue;}
		else {
			return get().prop.getProperty(key, defaultValue);
		}
	}
	private synchronized static boolean getBool(final String key, final String positiveValue){
		if(key==null || key.trim().length()==0 || !get().prop.containsKey(key)){
			return false;
		} else {
			String val = get().prop.getProperty(key);
			String ceck = Boolean.TRUE.toString().toLowerCase();
			if (positiveValue!=null && positiveValue.trim().length()>0){
				ceck = positiveValue.trim().toLowerCase();
			}
			return val !=null && val.trim().length()>0 && ceck.equals(val.toLowerCase());
		}
	}
	private synchronized static boolean isEnabled(final String key){
		return getBool(key, DEFAULT_ENABLED_VALUE);
	}
	private synchronized static boolean isTrue(final String key){
		return getBool(key, DEFAULT_TRUE_VALUE);
	}
	private synchronized static long getLong(final String key, final long defaultValue){
		final String s = get(key, null);
		if(s == null){return defaultValue;}
		try{
			return new Long(s).longValue();
		}catch(NumberFormatException e){
			LOG.warn("problemi durante il parsing della properties numerica, ritorno il valore di default. key:[{}] value:[{}] default:[{}]",key,s,defaultValue);
			return defaultValue;
		}
	}
	private synchronized static long getLong(final String key){
		return getLong(key, Long.MIN_VALUE);
	}
	private synchronized static int getInt(final String key, final int defaultValue){
		final String s = get(key, null);
		if(s == null){return defaultValue;}
		try{
			return new Integer(s).intValue();
		}catch(NumberFormatException e){
			LOG.warn("problemi durante il parsing della properties numerica, ritorno il valore di default. key:[{}] value:[{}] default:[{}]",key,s,defaultValue);
			return defaultValue;
		}
	}
	private synchronized static int getInt(final String key){
		return getInt(key, Integer.MIN_VALUE);
	}
	private synchronized static String get(final String key){
		return get(key, null);
	}
	public synchronized static void init() {
		init(DEFAULT_PROPERTIES_FILE);
	}
	public synchronized static void init(final String fileProperties) {
		if(fileProperties == null || fileProperties.trim().length()==0 ){
			init();
		} else {
			FileInputStream fis = null;
			try{
				final File f = new File(fileProperties);
				
				fis = new FileInputStream(f);
				
				get().prop.load(fis);
				
			} catch (FileNotFoundException e) {
				final String msg = "impossibile caricare le properties da file ["+fileProperties+"] ["+e.getMessage()+"]";
				final RuntimeException re = new RuntimeException(msg,e);
				LOG.error(msg,re);
				throw re;
			} catch (IOException e) {
				final String msg = "impossibile caricare le properties da file ["+fileProperties+"] ["+e.getMessage()+"]";
				final RuntimeException re = new RuntimeException(msg,e);
				LOG.error(msg,re);
				throw re;
			} finally {
				if(fis!=null) {
					try {
						fis.close();
					} catch (IOException e) {
						final String msg = "impossibile chiudere lo stream del file ["+fileProperties+"] ["+e.getMessage()+"]";
						final RuntimeException re = new RuntimeException(msg,e);
						LOG.error(msg,re);
						throw re;
					}
				}
			}
		}
	}
	
	public synchronized static String getEarName(){ return get(Prop.PROP_EAR_NAME); } 
	public synchronized static String getEjbJarName(){ return get(Prop.PROP_EJB_JAR_NAME); } 
	
	public synchronized static String getMonitoraggioConfAbsolutePathFileConf(){ return get(Prop.MONITORAGGIO_CONF_ABSOLUTE_PATH_FILE_CONF); }
	public synchronized static String getMonitoraggioConfContextRoot(){ return get(Prop.MONITORAGGIO_CONF_CONTEXT_ROOT); }
	public synchronized static String getMonitoraggioConfUrlWs(){ return get(Prop.MONITORAGGIO_CONF_URL_WS); }
	public synchronized static String getMonitoraggioConfUrlJsp(){ return get(Prop.MONITORAGGIO_CONF_URL_JSP); }
	
  	public synchronized static String getSisDataManagerUrl(){return get(Prop.SISDM_URL);}
  	public synchronized static String getSisDataManagerRequestMethod(){return get(Prop.SISDM_REQUEST_METHOD);}
  	public synchronized static String getSisDataManagerReadTimeout(){return get(Prop.SISDM_READ_TIMEOUT);}
  	public synchronized static String getSisDataManagerConnectionTimeout(){return get(Prop.SISDM_CONNECTION_TIMEOUT);}
  	public synchronized static String getSisDataManagerSslEnabled(){return get(Prop.SISDM_SSL_ENABLE);}
  	public synchronized static String getSisDataManagerSslKeyStore(){return get(Prop.SISDM_SSL_KEY_STORE);}
  	public synchronized static String getSisDataManagerSslKeyStorePwd(){return get(Prop.SISDM_SSL_KEY_STORE_PWD);}
  	
	public synchronized static boolean getSogeiProxyEnable(){ return isEnabled(Prop.SOGEI_PROXY_ENABLE); }
	public synchronized static String getSogeiProxyHost(){ return get(Prop.SOGEI_PROXY_HOST); }
	public synchronized static String getSogeiProxyPort(){ return get(Prop.SOGEI_PROXY_PORT); }
	public synchronized static String getSogeiProxyUsr(){ return get(Prop.SOGEI_PROXY_USR); }
	public synchronized static String getSogeiProxyPwd(){ return get(Prop.SOGEI_PROXY_PWD); }
	
	public synchronized static boolean getSogeiSslEnable(){ return isEnabled(Prop.SOGEI_SSL_ENABLE); }
	public synchronized static String getSogeiSslKeyStore(){ return get(Prop.SOGEI_SSL_KEY_STORE); }
	public synchronized static String getSogeiSslKeyStoreType(){ return get(Prop.SOGEI_SSL_KEY_STORE_TYPE); }
	public synchronized static String getSogeiSslKeyStorePwd(){ return get(Prop.SOGEI_SSL_KEY_STORE_PWD); }
	public synchronized static String getSogeiSslTrustStore(){ return get(Prop.SOGEI_SSL_TRUST_STORE); }
	public synchronized static String getSogeiSslTrustStoreType(){ return get(Prop.SOGEI_SSL_TRUST_STORE_TYPE); }
	public synchronized static String getSogeiSslTrustStorePwd(){ return get(Prop.SOGEI_SSL_TRUST_STORE_PWD); }
	
	public synchronized static String getSogeiDownloadEndpoint(){ return get(Prop.SOGEI_DOWNLOAD_ENDPOINT); }
	public synchronized static String getSogeiDownloadNameSpace(){ return get(Prop.SOGEI_DOWNLOAD_NAMESPACE); }
	public synchronized static String getSogeiDownloadPortName(){ return get(Prop.SOGEI_DOWNLOAD_PORTNAME); }
	public synchronized static String getSogeiDownloadServiceName(){ return get(Prop.SOGEI_DOWNLOAD_SERVICENAME); }
	public synchronized static String getSogeiDownloadUsr(){ return get(Prop.SOGEI_DOWNLOAD_USR); }
	public synchronized static String getSogeiDownloadPwd(){ return get(Prop.SOGEI_DOWNLOAD_PWD); }
	
	public synchronized static String getSogeiDownloadAction(){ return get(Prop.SOGEI_DOWNLOAD_ACTION); }
	public synchronized static String getSogeiDownloadOperation(){ return get(Prop.SOGEI_DOWNLOAD_OPERATION); }
	public synchronized static long getSogeiDownloadWsTimeout(){ return getLong(Prop.SOGEI_DOWNLOAD_WS_TIMEOUT,120000L); }

	public synchronized static boolean getSogeiDownloadSslEnable(){ return isEnabled(Prop.SOGEI_DOWNLOAD_SSL_ENABLE); }
	public synchronized static String getSogeiDownloadSslKeyStore(){ return get(Prop.SOGEI_DOWNLOAD_SSL_KEY_STORE); }
	public synchronized static String getSogeiDownloadSslKeyStoreType(){ return get(Prop.SOGEI_DOWNLOAD_SSL_KEY_STORE_TYPE); }
	public synchronized static String getSogeiDownloadSslKeyStorePwd(){ return get(Prop.SOGEI_DOWNLOAD_SSL_KEY_STORE_PWD); }
	public synchronized static String getSogeiDownloadSslTrustStore(){ return get(Prop.SOGEI_DOWNLOAD_SSL_TRUST_STORE); }
	public synchronized static String getSogeiDownloadSslTrustStoreType(){ return get(Prop.SOGEI_DOWNLOAD_SSL_TRUST_STORE_TYPE); }
	public synchronized static String getSogeiDownloadSslTrustStorePwd(){ return get(Prop.SOGEI_DOWNLOAD_SSL_TRUST_STORE_PWD); }
	public synchronized static boolean getSogeiDownloadSslOnJboss(){ return isTrue(Prop.SOGEI_DOWNLOAD_SSL_ON_JBOSS); }
	
	
	/* certificazioni */
	public synchronized static String getSogeiDownloadEndpointCert(){ return get(Prop.SOGEI_DOWNLOAD_ENDPOINT_CERT); }
	public synchronized static String getSogeiDownloadNameSpaceCert(){ return get(Prop.SOGEI_DOWNLOAD_NAMESPACE_CERT); }
	public synchronized static String getSogeiDownloadPortNameCert(){ return get(Prop.SOGEI_DOWNLOAD_PORTNAME_CERT); }
	public synchronized static String getSogeiDownloadServiceNameCert(){ return get(Prop.SOGEI_DOWNLOAD_SERVICENAME_CERT); }
	public synchronized static String getSogeiDownloadUsrCert(){ return get(Prop.SOGEI_DOWNLOAD_USR_CERT); }
	public synchronized static String getSogeiDownloadPwdCert(){ return get(Prop.SOGEI_DOWNLOAD_PWD_CERT); }

	public synchronized static String getSogeiDownloadActionCert(){ return get(Prop.SOGEI_DOWNLOAD_ACTION_CERT); }
	public synchronized static String getSogeiDownloadOperationCert(){ return get(Prop.SOGEI_DOWNLOAD_OPERATION_CERT); }
	public synchronized static long getSogeiDownloadWsTimeoutCert(){ return getLong(Prop.SOGEI_DOWNLOAD_WS_TIMEOUT_CERT,120000L); }

	public synchronized static boolean getSogeiDownloadSslEnableCert(){ return isEnabled(Prop.SOGEI_DOWNLOAD_SSL_ENABLE_CERT); }
	public synchronized static String getSogeiDownloadSslKeyStoreCert(){ return get(Prop.SOGEI_DOWNLOAD_SSL_KEY_STORE_CERT); }
	public synchronized static String getSogeiDownloadSslKeyStoreTypeCert(){ return get(Prop.SOGEI_DOWNLOAD_SSL_KEY_STORE_TYPE_CERT); }
	public synchronized static String getSogeiDownloadSslKeyStorePwdCert(){ return get(Prop.SOGEI_DOWNLOAD_SSL_KEY_STORE_PWD_CERT); }
	public synchronized static String getSogeiDownloadSslTrustStoreCert(){ return get(Prop.SOGEI_DOWNLOAD_SSL_TRUST_STORE_CERT); }
	public synchronized static String getSogeiDownloadSslTrustStoreTypeCert(){ return get(Prop.SOGEI_DOWNLOAD_SSL_TRUST_STORE_TYPE_CERT); }
	public synchronized static String getSogeiDownloadSslTrustStorePwdCert(){ return get(Prop.SOGEI_DOWNLOAD_SSL_TRUST_STORE_PWD_CERT); }
	public synchronized static boolean getSogeiDownloadSslOnJbossCert(){ return isTrue(Prop.SOGEI_DOWNLOAD_SSL_ON_JBOSS_CERT); }	
	
	public synchronized static String getApsUploadEndpoint(){ return get(Prop.APS_UPLOAD_ENDPOINT); }
	public synchronized static String getApsUploadNamespace(){ return get(Prop.APS_UPLOAD_NAMESPACE); }
	public synchronized static String getApsUploadPortname(){ return get(Prop.APS_UPLOAD_PORTNAME); }
	public synchronized static String getApsUploadServicename(){ return get(Prop.APS_UPLOAD_SERVICENAME); }
	public synchronized static String getApsUploadUsr(){ return get(Prop.APS_UPLOAD_USR); }
	public synchronized static String getApsUploadPwd(){ return get(Prop.APS_UPLOAD_PWD); }
	public synchronized static String getApsUploadAction(){ return get(Prop.APS_UPLOAD_ACTION); }
	public synchronized static String getApsUploadOperation(){ return get(Prop.APS_UPLOAD_OPERATION); }
	public synchronized static long getApsUploadWsTimeout(){ return getLong(Prop.APS_UPLOAD_WS_TIMEOUT,60000L); }

	public synchronized static boolean getApsUploadSslEnable(){ return isEnabled(Prop.APS_UPLOAD_SSL_ENABLE); }
	public synchronized static String getApsUploadSslKeyStore(){ return get(Prop.APS_UPLOAD_SSL_KEY_STORE); }
	public synchronized static String getApsUploadSslKeyStoreType(){ return get(Prop.APS_UPLOAD_SSL_KEY_STORE_TYPE); }
	public synchronized static String getApsUploadSslKeyStorePwd(){ return get(Prop.APS_UPLOAD_SSL_KEY_STORE_PWD); }
	public synchronized static String getApsUploadSslTrustStore(){ return get(Prop.APS_UPLOAD_SSL_TRUST_STORE); }
	public synchronized static String getApsUploadSslTrustStoreType(){ return get(Prop.APS_UPLOAD_SSL_TRUST_STORE_TYPE); }
	public synchronized static String getApsUploadSslTrustStorePwd(){ return get(Prop.APS_UPLOAD_SSL_TRUST_STORE_PWD); }
	public synchronized static boolean getApsUploadSslOnJboss(){ return isTrue(Prop.APS_UPLOAD_SSL_ON_JBOSS); }
	
	
	public synchronized static String getSogeiUserId(){ return get(Prop.SOGEI_USER_ID); }
	public synchronized static String getSogeiUserToken(){ return get(Prop.SOGEI_USER_TOKEN); }
	
	/* certificazioni */
	public synchronized static String getSogeiCertUserId(){ return get(Prop.SOGEI_CERTIFICAZIONI_USER_ID); }
	public synchronized static String getSogeiCertUserToken(){ return get(Prop.SOGEI_CERTIFICAZIONI_USER_TOKEN); }	
	public synchronized static String getDataLimiteCertStart(){ return get(Prop.DATA_LIMITE_CERT_START); }
	public synchronized static String getMaxDataLimiteDownloadRecuperoCertificazioni() { return get(Prop.MAX_DATA_LIMITE_DOWNLOAD_RECUPERO_CERTIFICAZIONI); }
	public synchronized static void setMaxDataLimiteDownloadRecuperoCertificazioni(final String maxDataLimiteDownloadRecupero) { set(Prop.MAX_DATA_LIMITE_DOWNLOAD_RECUPERO_CERTIFICAZIONI,maxDataLimiteDownloadRecupero); }
	public synchronized static int getMassimaFinestraElaborazioneCertificazioni(){ return getInt(Prop.MASSIMA_FINESTRA_ELABORAZIONE_CERTIFICAZIONI,10); }		
	
	/* autocertificazioni */
	public synchronized static String getDataOraLimiteStart(){ return get(Prop.DATA_ORA_LIMITE_START); }
	public synchronized static long getDeltaNuovaDataOraLimiteDownload() { return getLong(Prop.DELTA_NUOVA_DATA_ORA_LIMITE_DOWNLOAD,3L*Varie.TIME_MINUTO); }

	public synchronized static String getMaxDataOraLimiteDownloadRecuperoInserimento() { return get(Prop.MAX_DATA_ORA_LIMITE_DOWNLOAD_RECUPERO_INSERIMENTI); }
	public synchronized static void setMaxDataOraLimiteDownloadRecuperoInserimento(final String maxDataOraLimiteDownloadRecupero) { set(Prop.MAX_DATA_ORA_LIMITE_DOWNLOAD_RECUPERO_INSERIMENTI,maxDataOraLimiteDownloadRecupero); }
	
	public synchronized static String getMaxDataOraLimiteDownloadRecuperoVariazioni() { return get(Prop.MAX_DATA_ORA_LIMITE_DOWNLOAD_RECUPERO_VARIAZIONI); }
	public synchronized static void setMaxDataOraLimiteDownloadRecuperoVariazioni(final String maxDataOraLimiteDownloadRecupero) { set(Prop.MAX_DATA_ORA_LIMITE_DOWNLOAD_RECUPERO_VARIAZIONI,maxDataOraLimiteDownloadRecupero); }

	public synchronized static String getMaxDataOraLimiteDownloadRecuperoVariazioniAlMinuto() { return get(Prop.MAX_DATA_ORA_LIMITE_DOWNLOAD_RECUPERO_VARIAZIONI_AL_MINUTO); }
	public synchronized static void setMaxDataOraLimiteDownloadRecuperoVariazioniAlMinuto(final String maxDataOraLimiteDownloadRecupero) { set(Prop.MAX_DATA_ORA_LIMITE_DOWNLOAD_RECUPERO_VARIAZIONI_AL_MINUTO,maxDataOraLimiteDownloadRecupero); }
	
	public synchronized static int getMassimaFinestraElaborazioneAutocertificazioni(){ return getInt(Prop.MASSIMA_FINESTRA_ELABORAZIONE_AUTOCETIFICAZIONI,10); }
	public synchronized static long getTimeoutElaboratoreAsincrono(){ return getLong(Prop.TIMEOUT_ELABORATORE_ASINCRONO,0L); }
	public synchronized static long getTimeoutElaboratoreAsincronoCert(){ return getLong(Prop.TIMEOUT_ELABORATORE_ASINCRONO_CERT,0L); }
	
	public synchronized static long getTimeoutDownloadInserimenti(){ return getLong(Prop.TIMEOUT_DOWNLOAD_INSERIMENTI); }
	public synchronized static long getTimeoutDownloadVariazioni(){ return getLong(Prop.TIMEOUT_DOWNLOAD_VARIAZIONI); }
	public synchronized static long getTimeoutDownloadAttestazioni(){ return getLong(Prop.TIMEOUT_DOWNLOAD_ATTESTAZIONI); }

	public synchronized static long getTimeoutDownloadRecuperoInserimenti(){ return getLong(Prop.TIMEOUT_DOWNLOAD_RECUPERO_INSERIMENTI); }
	public synchronized static long getTimeoutDownloadRecuperoVariazioni(){ return getLong(Prop.TIMEOUT_DOWNLOAD_RECUPERO_VARIAZIONI); }
	public synchronized static long getTimeoutDownloadRecuperoVariazioniAlMinuto(){ return getLong(Prop.TIMEOUT_DOWNLOAD_RECUPERO_VARIAZIONI_AL_MINUTO); }
	public synchronized static long getTimeoutDownloadRecuperoAttestazioni(){ return getLong(Prop.TIMEOUT_DOWNLOAD_RECUPERO_ATTESTAZIONI); }
	
	
	public synchronized static long getSvecchiatoreTimeout(){ return getLong(Prop.SVECCHIATORE_TIMEOUT); }
	public synchronized static int getSvecchiatoreRecordMinimiMantenuti(){ return getInt(Prop.SVECCHIATORE_RECOR_MINIMI_MANTENUTI); }
	public synchronized static long getDeltaSvecchiatura(){ return getLong(Prop.DELTA_SCVECCHIATURA); }
	
	public synchronized static boolean getLoadSoggettiFake(){ return getBool(Prop.LOAD_SOGGETTI_FAKE,"TRUE"); }
	

	public synchronized static boolean isSoapTrackingToSogeiEnable(){ return getBool(Prop.SOAP_TRACKING_TO_SOGEI,"TRUE"); }
	

	public synchronized static long getRielaboratoreTimeout(){ return getLong(Prop.RIELABORATORE_TIMEOUT); }
	public synchronized static long getNumroTimerRielaborazione(){ return getLong(Prop.NUMERO_TIMER_RIELABORAZIONE,0L); }
	public synchronized static long getRielaboratoreTimeoutCert(){ return getLong(Prop.RIELABORATORE_TIMEOUT_CERT); }
	public synchronized static long getNumroTimerRielaborazioneCert(){ return getLong(Prop.NUMERO_TIMER_RIELABORAZIONE_CERT,0L); }	
	public synchronized static int getFinestraDiRielaborazione(){ return getInt(Prop.FINERSTRA_DI_RIELABORAZIONE); }
	public synchronized static int getFinestraDiRielaborazioneCert(){ return getInt(Prop.FINESTRA_DI_RIELABORAZIONE_CERT); }
	public synchronized static long getNumeroMassimoDiRielaborazioni(){ return getLong(Prop.NUMERO_MASSIMO_DI_RIELABORAZIONI); }
	public synchronized static long getNumeroMassimoDiRielaborazioniCert(){ return getLong(Prop.NUMERO_MASSIMO_DI_RIELABORAZIONI_CERT); }
	
	public synchronized static long getInitialDelay(){ return getLong(Prop.RITARDO_INIZIALE_DEI_TIMER); }
	
	public synchronized static long getProrogheNumroTimer(){ return getLong(Prop.PROROGHE_NUMERO_TIMER,0L); }
	public synchronized static int getProrogheDimensioneFienstra(){ return getInt(Prop.PROROGHE_DIMENSIONE_FINESTRA,0); }
	public synchronized static long getProrogheNumeroTentativi(){ return getLong(Prop.PROROGHE_NUMERO_TENTATIVI,0L); }
	public synchronized static long getProrogheTimeOut(){ return getLong(Prop.PROROGHE_TIMEOUT,0L); }
	public synchronized static long getProrogheSvecchiatoreTimeOut(){ return getLong(Prop.PROROGHE_SVECCHIATORE_TIMEOUT,0L); }
	public synchronized static int getProrogheSvecchiatoreRecorMinimiMantenuti(){ return getInt(Prop.PROROGHE_SVECCHIATORE_RECOR_MINIMI_MANTENUTI,0); }
	public synchronized static int getProrogheDeltaSvecchiatore(){ return getInt(Prop.PROROGHE_DELTA_SCVECCHIATURA,0); }
	public synchronized static boolean isProrogheTrackingIsEnabled(){ return getBool(Prop.PROROGHE_TRACKING_IS_ENABLED,"TRUE"); }
	
	public synchronized static long getRicalcolaTitoloNullTimeOut(){ return getLong(Prop.RICALCOLA_TITOLO_NULL_TIMEOUT,0L); }
	public synchronized static long getRicalcolaTitoloNullNumroTimer(){ return getLong(Prop.RICALCOLA_TITOLO_NULL_NUMERO_TIMER,0L); }
	public synchronized static int getRicalcolaTitoloNullDimensioneFienstra(){ return getInt(Prop.RICALCOLA_TITOLO_NULL_DIMENSIONE_FINESTRA,0); }
	
	public synchronized static long getRecuperoScartatiTimeOutCert(){ return getLong(Prop.RECUPERA_SCARTATI_TIMEOUT_CERT,0L); }
	public synchronized static long getRecuperoScartatiTimeOut(){ return getLong(Prop.RECUPERA_SCARTATI_TIMEOUT,0L); }
	public synchronized static int getFinestraRecuperoScartati(){ return getInt(Prop.FINESTRA_RECUPERO_SCARTATI,0); }
	public synchronized static int getFinestraRecuperoScartatiCert(){ return getInt(Prop.FINESTRA_RECUPERO_SCARTATI_CERT,0); }
	
	public synchronized static long getRecuperoScartatiGianov4TimeOutCert(){ return getLong(Prop.RECUPERA_SCARTATI_TIMEOUT_CERT_GIANOV4,0L); }
	public synchronized static long getRecuperoScartatiGianov4TimeOut(){ return getLong(Prop.RECUPERA_SCARTATI_TIMEOUT_GIANOV4,0L); }
	public synchronized static int getFinestraRecuperoScartatiGianov4(){ return getInt(Prop.FINESTRA_RECUPERO_SCARTATI_GIANOV4,0); }
	public synchronized static int getFinestraRecuperoScartatiCertGianov4(){ return getInt(Prop.FINESTRA_RECUPERO_SCARTATI_CERT_GIANOV4,0); }

	
	public static long getTimeoutValutatoreDiRettificaAutocertificazioniE01(){ return getLong(Prop.VALUTATORE_DI_RETTIFICA_AUTOCERTIFICAZIONI_E01_TIMEOUT,0L); }
	public static long getTimeoutValutatoreDiRettificaAutocertificazioniERA() { return getLong(Prop.VALUTATORE_DI_RETTIFICA_AUTOCERTIFICAZIONI_ERA_TIMEOUT,0L); }
	public static long getTimeoutRettificatoreAutocertificazioniE01() { return getLong(Prop.RETTIFICATORE_AUTOCERTIFICAZIONI_E01_TIMEOUT,0L); }
	public static long getTimeoutVRettificatoreAutocertificazioniERA() { return getLong(Prop.RETTIFICATORE_AUTOCERTIFICAZIONI_ERA_TIMEOUT,0L); }

	public static int getDimensioneFinestraElaborazioneValutatoreDiRettificaAutocertificazioni(){ return getInt(Prop.VALUTATORE_DI_RETTIFICA_AUTOCERTIFICAZIONI_DIMENSIONE_FINESTRA_ELABORAZIONE,0); }
	public static int getDimensioneFinestraElaborazioneRettificatoreAutocertificazioniE01() { return getInt(Prop.RETTIFICATORE_AUTOCERTIFICAZIONI_DIMENSIONE_FINESTRA_ELABORAZIONE,0); }


	public synchronized static long getRecuperoIdUniObsoletiTimeOut(){ return getLong(Prop.RECUPERO_IDUNI_OBSOLETI_TIMEOUT,0L); }
	public synchronized static int getRecuperoIdUniObsoletiNumeroMassimoRecordDaRecuperare() { return getInt(Prop.RECUPERO_IDUNI_OBSOLETI_NUMERO_MASSIMO_RECORD_DA_RECUPERARE,100); }
	public synchronized static String getRecuperoIdUniObsoletiDataAggiornamentoMinimaRecordDaRecuperareDefault(){ return get(Prop.RECUPERO_IDUNI_OBSOLETI_DATA_AGGIORNAMENTO_MINIMA_RECORD_DA_RECUPERARE_DEFAULT); }

	
	
	public synchronized static long getAggiornaIdUniObsoletiTimeOut(){ return getLong(Prop.AGGIORNA_IDUNI_OBSOLETI_TIMEOUT,0L); }
	public synchronized static int  getAggiornaIdUniObsoletiDimensioneFienstra(){ return getInt(Prop.AGGIORNA_IDUNI_OBSOLETI_DIMENSIONE_FINESTRA,0); }
	
	public synchronized static String getOidSenderGianov4(){ return get(Prop.OID_SENDER_GIANOV4); }
	
}

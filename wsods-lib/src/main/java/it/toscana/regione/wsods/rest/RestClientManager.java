package it.toscana.regione.wsods.rest;

import it.toscana.regione.eng.common.rest.entity.Risposta;
import it.toscana.regione.eng.common.rest.exception.RestException;
import it.toscana.regione.eng.common.rest.exception.RestRuntimeException;
import it.toscana.regione.eng.common.rest.lib.SecurityTool;
import it.toscana.regione.eng.common.rest.lib.SenderRest;
import it.toscana.regione.eng.common.rest.mapper.JSONConverter;
import it.toscana.regione.eng.common.rest.response.entity.sisdatamanager.estraiAnagraficaRegistryMaster.Soggetto;
import it.toscana.regione.eng.common.rest.response.entity.sisdatamanager.listaAssociazioniIdUni.ListaAssociazioniIdUni;
import it.toscana.regione.wsods.exception.SisDataManagerException;
import it.toscana.regione.wsods.exception.WsOdsRuntimeException;
import it.toscana.regione.wsods.type.Code;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyStore;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RestClientManager {

	/** Log */
	private static final Logger LOG = LoggerFactory.getLogger(RestClientManager.class);
	private static final Integer DEFAULT_READ_TIMEOUT = new Integer(3000000);
	private static final Integer DEFAULT_CONNECTION_TIMEOUT = new Integer(3000000);

	private String urlSISDM;

	private String requestMethod;

	private Integer readTimeout;

	private Integer connectionTimeout;

	private SSLSocketFactory sslSocketFactory;

	public RestClientManager(String urlSISDM, String requestMethod, String readTimeout, String connectionTimeout, String enableSsl, String keyStoreUrl, String keyStorePass) {
		this.urlSISDM = urlSISDM;
		this.requestMethod = requestMethod;

		try {
			this.readTimeout = Integer.parseInt(readTimeout);
		} catch (NumberFormatException nfe) {
			LOG.debug("Errore durante la conversione della property readTimeout per la creazione del RestManager. Settaggio valore di default");
			this.readTimeout = DEFAULT_READ_TIMEOUT;
		}

		try {
			this.connectionTimeout = Integer.parseInt(connectionTimeout);
		} catch (NumberFormatException nfe) {
			LOG.debug("Errore durante la conversione della property connectionTimeout per la creazione del RestManager. Settaggio valore di default");
			this.readTimeout = DEFAULT_CONNECTION_TIMEOUT;
		}

		if (enableSsl != null && !enableSsl.isEmpty() && enableSsl.equals("ENABLED")&& keyStorePass!=null) {
			try {
				final KeyStore keyStore= SecurityTool.loadKeyStore(keyStoreUrl, "JKS", keyStorePass.toCharArray());
				sslSocketFactory =  SecurityTool.createSSLSocketFactory(new URL(urlSISDM), keyStore, keyStore, keyStorePass.toCharArray());
			} catch(final RestException e){
				throw new WsOdsRuntimeException(Code.SDM_GENERICO, "Impossibile costruire sslSocketFactory",e);
			} catch (final MalformedURLException e){
				throw new WsOdsRuntimeException(Code.SDM_GENERICO, "Url del servizio rest malformata ["+urlSISDM+"]",e);
				
			}
		}
	}

	private <T> Risposta<T> invoke(RestConstant.ID_DOMINIO_VALUE idDominio, Map<String, String> param, Class<T> responseClass) throws SisDataManagerException {
		LOG.info("invokazione servizio REST con id dominio [" + idDominio + "]");
		param.put("Content-Type", "application/json");
		param.put(RestConstant.ID_DOMINIO_NAME, idDominio.descr);
		final byte[] requestMessage = JSONConverter.buildRequest(param);
		StringBuffer response = new StringBuffer("");
		URLConnection connection = SenderRest.createConnect(urlSISDM, sslSocketFactory, requestMethod, param, readTimeout, connectionTimeout);

		if (connection instanceof HttpsURLConnection) {
			SecurityTool.setFakeHostnameVerifer((HttpsURLConnection) connection);
		}

		try {
			SenderRest.send(connection, requestMessage, response);
		} catch (RestRuntimeException rre) {
			throw new SisDataManagerException(Code.SDM_SERVIZIO_NON_DISPONIBILE, rre);
		}

		Risposta<T> risp = null;

		try {
			risp = JSONConverter.mappaResponse(response.toString(), responseClass);
		} catch (IllegalArgumentException e) {
			throw new SisDataManagerException(Code.SDM_SERVIZIO_NON_DISPONIBILE, e);
		} catch (RestException e) {
			throw new SisDataManagerException(Code.SDM_SERVIZIO_NON_DISPONIBILE, e);
		}

		return risp;
	}

	public Soggetto getSoggetto(Map<String, String> param) {
		RestConstant.ID_DOMINIO_VALUE idDominio = RestConstant.ID_DOMINIO_VALUE.ESTRAI_ANAGRAFICA_REGISTRY_MASTER;
		Risposta<Soggetto> risp = invoke(idDominio, param, Soggetto.class);
		if(risp.getErrore()!=null){
			final StringBuffer sb = new StringBuffer("");
			final String cod = risp.getErrore().getCodice();
			final String desc = risp.getErrore().getDescrizione();
			final boolean codFind = cod != null && cod.trim().length()>0;
			if(codFind){
				sb.append(cod);
			}
			if(desc != null){
				if(codFind){
					sb.append(" - ");
				}
				sb.append(desc);
			}
			if(sb.toString().trim().length()>0){
				throw new SisDataManagerException(Code.SDM_SERVIZIO_RESTITUISCE_ERRORE,sb.toString().trim());
			}
			
		}
		return risp.getContenutoRisposta();
	}
	
	public ListaAssociazioniIdUni getNextLinksIdUniObs(final int maxRow, final String lastTime){
		final Map<String,String> param = new HashMap<String, String>();
		param.put(RestConstant.NUMERO_MASSIMO_RECORD_DA_RECUPERARE, String.valueOf(maxRow));
		param.put(RestConstant.DATA_AGGIORNAMENTO_MINIMA_RECORD_DA_RECUPERARE, lastTime);
		final Risposta<ListaAssociazioniIdUni> risp = invoke(RestConstant.ID_DOMINIO_VALUE.LISTA_ASSOCIAZIONI_ID_UNI , param, ListaAssociazioniIdUni.class);
		if(risp.getErrore()!=null){
			final StringBuffer sb = new StringBuffer("");
			final String cod = risp.getErrore().getCodice();
			final String desc = risp.getErrore().getDescrizione();
			final boolean codFind = cod != null && cod.trim().length()>0;
			if(codFind){
				sb.append(cod);
			}
			if(desc != null){
				if(codFind){
					sb.append(" - ");
				}
				sb.append(desc);
			}
			if(sb.toString().trim().length()>0){
				throw new SisDataManagerException(Code.SDM_SERVIZIO_RESTITUISCE_ERRORE,sb.toString().trim());
			}
			
		}
		return risp.getContenutoRisposta();
	}
	
	public String ricercaAnagraficheByCfWSODS(Map<String, String> param) {
		RestConstant.ID_DOMINIO_VALUE idDominio = RestConstant.ID_DOMINIO_VALUE.RICERCA_ANAGRAFICHE_BY_CF_WSODS;
		return this.ricercaAnagraficheWSODS(param, idDominio);
	}
	
	public String ricercaAnagraficheByCfETrattiWSODS(Map<String, String> param) {
		RestConstant.ID_DOMINIO_VALUE idDominio = RestConstant.ID_DOMINIO_VALUE.RICERCA_ANAGRAFICHE_BY_CF_E_TRATTI_WSODS;
		return this.ricercaAnagraficheWSODS(param, idDominio);
	}
	
	private String ricercaAnagraficheWSODS(Map<String, String> param, RestConstant.ID_DOMINIO_VALUE idDominio) {
		LOG.info("invokazione servizio REST con id dominio [" + idDominio + "]");
		param.put("Content-Type", "application/json");
		param.put(RestConstant.ID_DOMINIO_NAME, idDominio.descr);
		final byte[] requestMessage = JSONConverter.buildRequest(param);
		StringBuffer response = new StringBuffer("");
		URLConnection connection = SenderRest.createConnect(urlSISDM, sslSocketFactory, requestMethod, param, readTimeout, connectionTimeout);

		if (connection instanceof HttpsURLConnection) {
			SecurityTool.setFakeHostnameVerifer((HttpsURLConnection) connection);
		}

		try {
			SenderRest.send(connection, requestMessage, response);
		} catch (RestRuntimeException rre) {
			throw new SisDataManagerException(Code.SDM_SERVIZIO_NON_DISPONIBILE, rre);
		}
		return response.toString();
	}
	

	
	
	
	
	
	
	
	
}

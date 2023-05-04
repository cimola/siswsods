//package it.toscana.regione.wsods.lib;
//
//
//import it.toscana.regione.wsods.exception.WsException;
//import it.toscana.regione.wsods.exception.SecurityException;
//import it.toscana.regione.wsods.type.Code;
//
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.security.KeyStore;
//import java.util.Set;
//
//import javax.net.ssl.KeyManagerFactory;
//import javax.net.ssl.SSLSocketFactory;
//import javax.net.ssl.TrustManagerFactory;
//import javax.xml.namespace.QName;
//import javax.xml.soap.SOAPMessage;
//import javax.xml.ws.BindingProvider;
//import javax.xml.ws.Dispatch;
//import javax.xml.ws.Service;
//import javax.xml.ws.soap.SOAPBinding;
//import javax.xml.ws.soap.SOAPFaultException;
//
//import org.apache.cxf.configuration.jsse.TLSClientParameters;
//import org.apache.cxf.endpoint.Client;
//import org.apache.cxf.transport.Conduit;
//import org.apache.cxf.transport.http.HTTPConduit;
//import org.apache.cxf.transports.http.configuration.ConnectionType;
//import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//
///**
// * @author cciurli
// *
// */
//public class WsTool {
//
//	private static final Logger LOG = LoggerFactory.getLogger(WsTool.class);
//	/**
//	 * 
//	 */
//	private WsTool() { }
//	
//    public static SOAPMessage sendMessage(final Dispatch<SOAPMessage> dispatch, final SOAPMessage request) throws WsException, SOAPFaultException {
//    	final SOAPMessage response;
//    	try {
//    		LOG.debug("request_msg:\n{}",SOAPTool.soapToByteArray(request));
//			response = dispatch.invoke(request);
//    		LOG.debug("response_msg:\n{}",SOAPTool.soapToByteArray(response));
//			clearContextResponseDispatch(dispatch);
//		} catch (final Throwable t) {
//			if(t instanceof SOAPFaultException){ throw (SOAPFaultException)t; }
//			throw new WsException(Code.SENDER_SERVIZIO_NON_DISPONIBILE, "impossibile invocare il servizio", t);
//		}
//    	return response;
//	}
//    public static Dispatch<SOAPMessage> createDispatch(final QName serviceName, final QName portName, final URL endPoint) {
//    	LOG.debug("creo il dispatch");
//		final Service service = Service.create(serviceName);
//		service.addPort(portName, SOAPBinding.SOAP11HTTP_BINDING, endPoint.toExternalForm());
//		final Dispatch<SOAPMessage> dispatch = service.createDispatch(portName, SOAPMessage.class , Service.Mode.MESSAGE);
//		return dispatch;
//	}
//    public static void clearContextResponseDispatch(final Dispatch<SOAPMessage> dispatch) {
//    	LOG.debug("ripulisco il responseContext del dispatch");
//    	if(dispatch!=null && dispatch.getResponseContext()!=null){
//    		dispatch.getResponseContext().clear();
//    		if(dispatch.getResponseContext().size()>0){
//    			final Set<String> keys = dispatch.getResponseContext().keySet();
//    			for(final String key : keys){
//    				if(dispatch.getResponseContext().containsKey(key)){
//    					dispatch.getResponseContext().remove(key);
//    				}
//    			}
//    		}
//    	}
//	}
//    public static void setUserAndPassword(final Dispatch<SOAPMessage> dispatch, final String username, final String password) {
//    	
//    	if (password != null && username != null) {
//    		LOG.debug("setto user {} e password {} sul dispatch",username,password);
//    		((BindingProvider) dispatch).getRequestContext().put(BindingProvider.USERNAME_PROPERTY, username);
//			((BindingProvider) dispatch).getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, password);
//		}
//    }
//    
//    public static void setSoapAction(final Dispatch<SOAPMessage> dispatch, final String soapAction) {
//    	if (soapAction != null ) {
//    		LOG.debug("setto soapAction {} sul dispatch",soapAction);
//			((BindingProvider) dispatch).getRequestContext().put(BindingProvider.SOAPACTION_URI_PROPERTY, soapAction);
//			((BindingProvider) dispatch).getRequestContext().put(BindingProvider.SOAPACTION_USE_PROPERTY, soapAction);
//		}
//    }
//    
//    public static void setTimeOut(final Dispatch<SOAPMessage> dispatch, final long connectionTimeout) throws WsException {
//    	final HTTPConduit httpConduit = extractConduit(dispatch);
//    	httpConduit.setClient(genHTTPClientPolicy(connectionTimeout));
//    }
//    public static boolean isOnCxf(final Dispatch<?> dispatch){ return dispatch instanceof org.apache.cxf.jaxws.DispatchImpl<?>; }
//    
//    public static void setAliasCert(final Dispatch<SOAPMessage> dispatch, final String alias) throws WsException {
//    	if(dispatch!=null && alias!=null && alias.trim().length()>0) {
//	    	final HTTPConduit httpConduit = extractConduit(dispatch);
//	    	if(httpConduit!=null) {
//		    	final TLSClientParameters tlsClientParameters =  httpConduit.getTlsClientParameters();
//		    	if(tlsClientParameters!=null) {
//		    		tlsClientParameters.setCertAlias(alias);
//		    		LOG.debug("settato il certificato [{}]",alias);
//		    	}
//	    	}
//    	}
//    }
//    public static HTTPConduit extractConduit(final Dispatch<SOAPMessage> dispatch) throws WsException { 
//    	if(isOnCxf(dispatch)){
//    		final Client client =  ((org.apache.cxf.jaxws.DispatchImpl<?>)dispatch).getClient();
//    		final Conduit conduit = client.getConduit();
//    		if(conduit instanceof HTTPConduit){
//	    		final HTTPConduit httpConduit = (HTTPConduit) client.getConduit();
//	    		return httpConduit;
//    		}
//    	}
//    	throw new WsException(Code.SECURE_GENERIC,"impossibile configurare la connessione ssl");
//	}
//
//    public static  HTTPClientPolicy genHTTPClientPolicy() {
//    	return genHTTPClientPolicy(36000L);
//	}
//    public static  HTTPClientPolicy genHTTPClientPolicy(final long connectionTimeout) {
//    	LOG.debug("setto un timeout di {} ms sulle HTTPClientPolicy",connectionTimeout);
//		final HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
//		httpClientPolicy.setConnectionTimeout(connectionTimeout);
//		httpClientPolicy.setConnection(ConnectionType.KEEP_ALIVE);
//		httpClientPolicy.setAllowChunking(false);
//		httpClientPolicy.setReceiveTimeout(connectionTimeout);
//		return httpClientPolicy;
//	}
//    public static URL convertToURL(final String url) throws WsException{
//    	if(url==null|| url.trim().length()==0){throw new WsException(Code.TOOL_URL,"url nulla o vuota");}
//    	try {
//			return new URL(url);
//		} catch (MalformedURLException e) {
//			throw new WsException(Code.TOOL_URL,"impossibile costruire l'url dalla stirnga ["+url+"]",e);
//		}
//    }
//    public static void setSSL(final SSLSocketFactory  sslSocketFactory,final KeyStore truststore, final KeyStore keystore,final char[] pwdKeyStore,  final boolean disableCNCheck, final boolean useHostnameVerifier, final Dispatch<SOAPMessage> dispatch) throws WsException{	
//		final HTTPConduit httpConduit = extractConduit(dispatch);
//		final TrustManagerFactory trustManagerFactory;
//		final KeyManagerFactory keyManagerFactory;
//		if(truststore!=null){
//			try {
//				trustManagerFactory = SecurityUtil.getTrustManagerFactory(truststore);
//			} catch (SecurityException e) {
//				throw new WsException("impossibile configurare la connessione ssl",e);
//			}
//		} else {
//			trustManagerFactory = null;
//		}
//		
//		if(keystore!=null){
//			try {
//				keyManagerFactory = SecurityUtil.getKeyManagerFactory(keystore, pwdKeyStore);
//			} catch (SecurityException e) {
//				throw new WsException("impossibile configurare la connessione ssl",e);
//			}
//		} else {
//			keyManagerFactory = null;
//		}
//		
//		final TLSClientParameters tlsClientParameters  = SecurityUtil.getTLSClientParameters(sslSocketFactory,keyManagerFactory,trustManagerFactory, SecurityUtil.PROTOCOLLO_SSL, disableCNCheck, useHostnameVerifier);
//		httpConduit.setTlsClientParameters(tlsClientParameters);
//		//httpConduit.setClient(genHTTPClientPolicy());
//	}
//    public static void setSSL(final SSLSocketFactory  sslSocketFactory, final boolean disableCNCheck, final boolean useHostnameVerifier, final Dispatch<SOAPMessage> dispatch) throws WsException{	
//    	setSSL(sslSocketFactory, null, null, new char[0], disableCNCheck, useHostnameVerifier, dispatch);
//	}
//    public static void setSSL(final String endpoint, final String keyStorePath,final String keyStoreType,final char[] keyStorePwd,final String trustStorePath,final String trustStoreType,final char[] trustStorePwd, final boolean onJboss, final Dispatch<SOAPMessage> dispatch) throws WsException{
//    	setSSL(endpoint,keyStorePath, keyStoreType, keyStorePwd, trustStorePath, trustStoreType, trustStorePwd, onJboss, isOnCxf(dispatch), dispatch);
//    }
//    public static void setSSL(final String endpoint, final String keyStorePath,final String keyStoreType,final char[] keyStorePwd,final String trustStorePath,final String trustStoreType,final char[] trustStorePwd, final boolean onJboss,final boolean onCxf, final Dispatch<SOAPMessage> dispatch) throws WsException{
//    	if(onJboss){
//    		if(onCxf){
//    			try {
//	    			final KeyStore trustStore = SecurityUtil.loadKeyStore(trustStorePath, trustStoreType, trustStorePwd);
//	    			final KeyStore keyStore = SecurityUtil.loadKeyStore(keyStorePath, keyStoreType, keyStorePwd);
//	    			final SSLSocketFactory  sslSocketFactory = SecurityUtil.createSSLSocketFactory(convertToURL(endpoint), trustStore, keyStore, keyStorePwd);
//	    			setSSL(sslSocketFactory, true, false, dispatch);
//    			} catch (SecurityException e) {
//    				throw new WsException("impossibile configurare la connessione ssl",e);
//    			}
//    		} else {
//    			dispatch.getRequestContext().put(org.jboss.ws.core.StubExt.PROPERTY_KEY_STORE, keyStorePath);
//    			dispatch.getRequestContext().put(org.jboss.ws.core.StubExt.PROPERTY_KEY_STORE_TYPE, keyStoreType);
//    			dispatch.getRequestContext().put(org.jboss.ws.core.StubExt.PROPERTY_KEY_STORE_PASSWORD, String.valueOf(keyStorePwd));
//    			dispatch.getRequestContext().put(org.jboss.ws.core.StubExt.PROPERTY_TRUST_STORE, trustStorePath);
//    			dispatch.getRequestContext().put(org.jboss.ws.core.StubExt.PROPERTY_TRUST_STORE_TYPE, trustStoreType);
//    			dispatch.getRequestContext().put(org.jboss.ws.core.StubExt.PROPERTY_TRUST_STORE_PASSWORD, String.valueOf(trustStorePwd));
//    		}
//    	} else {
//    		dispatch.getRequestContext().put("javax.net.ssl.ketStore", keyStorePath);
//			dispatch.getRequestContext().put("javax.net.ssl.ketStoreType", keyStoreType);
//			dispatch.getRequestContext().put("javax.net.ssl.ketStorePassword", String.valueOf(keyStorePwd));
//			dispatch.getRequestContext().put("javax.net.ssl.trustStore", trustStorePath);
//			dispatch.getRequestContext().put("javax.net.ssl.trustStoreType", trustStoreType);
//			dispatch.getRequestContext().put("javax.net.ssl.trustStorePassword", String.valueOf(trustStorePwd));
//    	}
//    }
//	
//    
//    
//    
//}

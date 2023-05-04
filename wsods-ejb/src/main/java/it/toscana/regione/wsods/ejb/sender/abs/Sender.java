package it.toscana.regione.wsods.ejb.sender.abs;

import javax.ejb.EJB;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.Dispatch;
import javax.xml.ws.soap.SOAPFaultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.toscana.regione.common.webservicetool.exception.WsToolServiceFaultRuntimeException;
import it.toscana.regione.common.webservicetool.token.api.IConfSenderSoap;
import it.toscana.regione.common.webservicetool.util.Factory;
import it.toscana.regione.wsods.ejb.sender.api.ISender;
import it.toscana.regione.wsods.ejb.tracker.api.ISoapTracking;
import it.toscana.regione.wsods.exception.DownloadException;
import it.toscana.regione.wsods.lib.SOAPTool;
//import it.toscana.regione.wsods.lib.SecurityUtil;
//import it.toscana.regione.wsods.lib.WsTool;
import it.toscana.regione.wsods.lib.XMLTool;
import it.toscana.regione.wsods.type.Code;

/**
 * @author cciurli
 *
 */
public abstract class Sender implements ISender {
	
	private final static Logger LOG = LoggerFactory.getLogger(Sender.class);

	@EJB(beanName=ISoapTracking.EJB_REF)
	private ISoapTracking tracker;
	
	protected Dispatch<SOAPMessage> dispatch;

	public Sender() {
		super();
	}
	
	public abstract boolean isTrackerEnabled();
	public abstract String getSrc();
	public abstract String getDest();
	
//	protected abstract QName getPortName();
//	protected abstract QName getServiceName();
//	protected abstract String getEndpoint();
	
//	protected abstract String getUser();
//	protected abstract String getPwd();
//	protected abstract String getSoapAction();
//	protected abstract long getWsTimeout();
	
//	protected abstract boolean getOnJboss();
//	protected abstract boolean getSslEnable();
//	protected abstract String getSslKeyStoreType();
//	protected abstract String getSslKeyStore();
//	protected abstract char[] getSslKeyStorePwd();
//	protected abstract String getSslCertAlias();
//	protected abstract String getSslTrustStoreType();
//	protected abstract String getSslTrustStore();
//	protected abstract char[] getSslTrustStorePwd();

	protected abstract  IConfSenderSoap getConf();
	
//	private KeyStore getKeyStore() throws SecurityException { return SecurityUtil.loadKeyStore(getSslKeyStore(), getSslKeyStoreType(), getSslKeyStorePwd(),getSslCertAlias()); }
//	private KeyStore getTrustStore() throws SecurityException { return SecurityUtil.loadKeyStore(getSslTrustStore(), getSslTrustStoreType(), getSslTrustStorePwd()); }
//	
//
//	
//	private URL getUrlEndpoint() throws WsException  {
//		final String text = getEndpoint();
//		return WsTool.convertToURL(text);
//	}
//	
//	private void verificaRisorse() {
//
//		if (dispatch == null) {
//			initDispatch();
//		}
//	}
//	
//	private void initDispatch() throws EjbException {
//		dispatch = createDispatch();	
//	}
//	private Dispatch<SOAPMessage> createDispatch() throws EjbException {
//		final long start = System.currentTimeMillis();
//		LOG.info("init Dispatch");
//		try {
//			final Dispatch<SOAPMessage> dispatch;
//			try {
//				dispatch = WsTool.createDispatch(getServiceName(),getPortName(), getUrlEndpoint());
//			} catch (WsException e){
//				throw new EjbException("impossibile creare il dispatch ",e);
//			}
//			
//			WsTool.setUserAndPassword(dispatch, getUser(), getPwd());
//			
//			if ( getSoapAction() != null && getSoapAction().trim().length() > 0 ){ WsTool.setSoapAction(dispatch, getSoapAction().trim()); }
//			
//			try {
//				if (getSslEnable()) {WsTool.setSSL(getSSLSocketFactory(),true,false, dispatch);}
//			} catch (WsException e) {
//				throw new EjbException(e);
//			}
//			
//			try {
//				WsTool.setTimeOut(dispatch,getWsTimeout());
//			} catch (WsException e) {
//				throw new EjbException(e);
//			}
//			
//			final String alias = getSslCertAlias();
//			
//			if (alias != null && alias.trim().length() > 0 && dispatch!=null) {
//				
//			}
//
//			
//			return dispatch;
//		} finally {
//			LOG.debug("[PERFORMANCE] - init Dispatch in {}.", System.currentTimeMillis() - start);
//		}
//	}
//	private SSLSocketFactory getSSLSocketFactory() throws EjbException {
//		final SSLSocketFactory sslSocketFactory;
//		if(getSslEnable()){
//			try {
//				sslSocketFactory = SecurityUtil.createSSLSocketFactory(getUrlEndpoint(),getKeyStore(), getTrustStore(),getSslKeyStorePwd());
//			} catch (SecurityException e) {
//				throw new EjbException(e);
//			} catch (WsException e) {
//				throw new EjbException(e);
//			}
//		} else {
//			sslSocketFactory = null;
//		}
//		return sslSocketFactory;
//	}
//	
	private static void gestisciFault(final long idTrake, final String fault, final WsToolServiceFaultRuntimeException e) {
		if (fault != null) {
			final String errorMsg = "fault ricevuto:\n"+fault+"";
			if(e!=null){
				throw new DownloadException(Code.SENDER_SOAP_FAULT, errorMsg,e);
			} else {
				throw new DownloadException(Code.SENDER_SOAP_FAULT, errorMsg);
			}
		}
	}
	private static void gestisciFault(final long idTrake, final SOAPFault fault, final SOAPFaultException e) {
		if (fault != null) {
			final String errorMsg = "fault ricevuto:\n"+XMLTool.node2string(fault, false)+"";
			if(e!=null){
				throw new DownloadException(Code.SENDER_SOAP_FAULT, errorMsg,e);
			} else {
				throw new DownloadException(Code.SENDER_SOAP_FAULT, errorMsg);
			}
		}
	}
	
	
//	@PostConstruct
//	public void init() throws EjbException {
//		try {
//			LOG.info("Init {} ejb ", ISender.EJB_REF);
//			initDispatch();
//		} catch (final Throwable t) {
//			throw new EjbException(Code.EJB_POST_CONSTRUCT, t);
//		}
//		
//	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public SOAPMessage getEmptySoap(){
//		verificaRisorse();
		return SOAPTool.newEmptySOAPMessage();
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public SOAPMessage send(final long idTrake,final SOAPMessage soap) throws DownloadException {
		
		final long start = System.currentTimeMillis();
		try {
			SOAPMessage response = null;
			
//			verificaRisorse();
			
			try {
				final String stringReq = SOAPTool.soapToString(soap);
				LOG.debug("Request:\n" + stringReq);
				
				tracker.trackeReqest(isTrackerEnabled(),idTrake, stringReq);
					
				response = Factory.createSoapSender(getConf()).invoke(soap);
//				response = WsTool.sendMessage(dispatch,soap);
	
			} catch (final Throwable t) {
				if(t instanceof SOAPFaultException){
					
					final SOAPFault fault = ((SOAPFaultException)t).getFault();	
					
					tracker.trackeResponse(isTrackerEnabled(),idTrake,  XMLTool.node2string(fault, false));
					
					gestisciFault(idTrake, fault,(SOAPFaultException)t);
				} else if(t instanceof WsToolServiceFaultRuntimeException) {
					final byte[] byteArrayResponse = ((WsToolServiceFaultRuntimeException)t).getResponse();
					final String stringResponse;
					if(byteArrayResponse !=null) {
						stringResponse = new String(byteArrayResponse);
					} else {
						stringResponse = "Risposta nulla";
					}
					tracker.trackeResponse(isTrackerEnabled(),idTrake, stringResponse );
					
					gestisciFault(idTrake, stringResponse,(WsToolServiceFaultRuntimeException)t);
				}
				
				throw new DownloadException(Code.SENDER_SERVIZIO_NON_DISPONIBILE, "impossibile invocare il servizio", t);
			}
			
			if (response == null) { throw new DownloadException(Code.GENERICO, "si e' ottenuto una response vuota"); }
			
			final String stringRes = SOAPTool.soapToString(response);
			
			tracker.trackeResponse(isTrackerEnabled(),idTrake, stringRes);
			
			LOG.debug("Response:\n" + stringRes);
			
			final SOAPFault fault = SOAPTool.extractFault(response);
			
			gestisciFault(idTrake, fault, null);
			
			return response;
		} finally {
			LOG.debug("[PERFORMANCE] - invio messaggio soap di request in {}.", System.currentTimeMillis() - start);
		}
		
	}
//	private static String printSizeMapElementNotNull(final Map<String,Object> map){
//		final StringBuffer sb = new StringBuffer("");
//		int sizeNotNullMap = 0;
//		final int sizeAllMap = map.size();
//		final Set<String> keys = map.keySet();
//		for(final String key : keys){
//			if(map.containsKey(key)){
//				final Object obj = map.get(key);
//				if(obj!=null){
//					sizeNotNullMap++;
//				} 
//			}
//		}
//		sb.append("size [").append(sizeAllMap).append("] elementi non nulli [").append(sizeNotNullMap).append("]");
//		return sb.toString();
//	}
	
	
	
}

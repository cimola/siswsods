/** */
package it.toscana.regione.wsods.ejb.ws.impl;

import it.toscana.regione.wsods.ejb.getesenzioni.api.IManagerGetEsenzioni;
import it.toscana.regione.wsods.ejb.getlista.api.IManagerGetLista;
import it.toscana.regione.wsods.ejb.upload.api.IManagerUpload;
import it.toscana.regione.wsods.ejb.ws.api.IWsOds;
import it.toscana.regione.wsods.exception.WsOdsException;
import it.toscana.regione.wsods.exception.WsOdsRuntimeException;
import it.toscana.regione.wsods.lib.SOAPTool;
import it.toscana.regione.wsods.type.Code;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.Service;
import javax.xml.ws.ServiceMode;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.WebServiceProvider;
import javax.xml.ws.handler.MessageContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author cciurli
 *
 */
@Stateless(name = IWsOds.EJB_REF_WS_ODS)
@ServiceMode(value = Service.Mode.MESSAGE)
@WebServiceProvider(serviceName = "WsOds", portName = "WsOdsSOAP", targetNamespace = "http://www.regione.toscana.it/autocertificatore-reddito/", wsdlLocation = "META-INF/WsOds.wsdl")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class WsOds implements IWsOds {
	
	private static final Logger LOG = LoggerFactory.getLogger(WsOds.class);
	
	@Resource
	private WebServiceContext wsCtx;
	
	@EJB(beanName = IManagerGetLista.EJB_REF)
	private IManagerGetLista getLista;
	
	@EJB(beanName = IManagerUpload.EJB_REF)
	private IManagerUpload upload; 
	
	@EJB(beanName = IManagerGetEsenzioni.EJB_REF)
	private IManagerGetEsenzioni getEsenzioni;
	
	/**
	 * 
	 */
	public WsOds() { super();}

	/** {@inheritDoc} */
	@Override @TransactionAttribute(TransactionAttributeType.NEVER)
	public SOAPMessage invoke(final SOAPMessage request) {
		try{
			final SOAPMessage response = SOAPTool.newEmptySOAPMessage();
	
			final SOAPBody body = estractBody(response);
			
			final QName operation = extractOperation();
		
			selectAction(request, body, operation);
			
			saveResponse(response);
				
			return response;
		}catch(WsOdsRuntimeException e){
			LOG.error(e.code.msg+" - "+e.getMessage(),e);
			throw e;
		}catch(RuntimeException e){
			LOG.error(e.getMessage(),e);
			throw e;
		}
	}

	private SOAPBody estractBody(final SOAPMessage response) {
		final SOAPBody body;
		
		try {
			body = response.getSOAPBody();
		} catch (SOAPException e) {
			final WsOdsRuntimeException wsOdsException = new WsOdsRuntimeException(Code.WS_BODY,e);
			LOG.error(wsOdsException.getMessage(),wsOdsException);
			throw wsOdsException;
		}
		return body;
	}

	private void saveResponse(final SOAPMessage response) {
		try {
			response.saveChanges();
		} catch (SOAPException e) {
			final WsOdsRuntimeException wsOdsException = new WsOdsRuntimeException(Code.WS_SAVE,e);
			LOG.error(wsOdsException.getMessage(),wsOdsException);
			throw wsOdsException;
		}
	}

	private void selectAction(final SOAPMessage request, final SOAPBody body, final QName operation) {
		if(operation != null){
			try{
				if("Upload".equals(operation.getLocalPart())){
					upload.upload(request, body);
				} else if("GetLista".equals(operation.getLocalPart())){
					getLista.getLista(request, body);
				} else if("GetEsenzioni".equals(operation.getLocalPart())){
					getEsenzioni.getEsenzioni(request, body);
				} else {
					setFault(body,Code.WS_OPERATION_UK,operation.getLocalPart());
				}
			}catch(Throwable t){
				gestisciEccezione(body, t);
			}
		} else {
			setFault(body,Code.WS_OPERATION_NULL,"impossibile procedere con le attivita'");
			LOG.warn("ricevuta una richiesta senza operation");
		}
	}

	private void gestisciEccezione(final SOAPBody body,final Throwable t) {
		final WsOdsRuntimeException e;
		
		if(t!= null && t instanceof WsOdsRuntimeException) {
			e =  (WsOdsRuntimeException)t;
			setFault(body,e.code,e.getMessage());
		} else if(t!= null && t instanceof WsOdsException) { 
			e = new WsOdsRuntimeException((WsOdsException)t);
			setFault(body,e.code,e.getMessage());
		} else{
			e = new WsOdsRuntimeException(Code.WS_GENERICO,t);
			LOG.error("riscontrato un problema durante una richiesta al ws",e);
			throw e;
		}
		LOG.warn("problemi durante la soddisfazione di una richiesta [{}]",e.getMessage(), e);
	}
	
	
	
	private QName extractOperation(){
		final MessageContext mCtx = wsCtx.getMessageContext();
		final Object obj = mCtx.get(MessageContext.WSDL_OPERATION);
		if(obj!= null && obj instanceof QName){
			return (QName)obj;
		} else {
			return null;
		}
	}
	
	private static void setFault(final SOAPBody body, final Code code, final String dettaglio){
		if(body == null){
			final WsOdsRuntimeException wsOdsException = new WsOdsRuntimeException(Code.WS_BODY,"body nullo");
			LOG.error(wsOdsException.getMessage(),wsOdsException);
			throw wsOdsException;
		}
		try{
			final SOAPFault fault = body.addFault();
			fault.setFaultCode(String.valueOf(code.code));
			fault.setFaultString(code.msg+"["+dettaglio+"]");
		} catch (SOAPException e){
			LOG.error("impossibile creare il fault",e);
			final WsOdsRuntimeException wsOdsException = new WsOdsRuntimeException(code);
			LOG.error(wsOdsException.getMessage(),wsOdsException);
			throw wsOdsException;
		}
	}
	
	
}

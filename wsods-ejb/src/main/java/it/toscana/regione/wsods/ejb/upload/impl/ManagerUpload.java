/** */
package it.toscana.regione.wsods.ejb.upload.impl;

import it.toscana.regione.autocertificazionireddito.entity.jaxb.rt.EsitoType;
import it.toscana.regione.autocertificazionireddito.entity.jaxb.rt.UploadAps2WsOdsRequest;
import it.toscana.regione.autocertificazionireddito.entity.jaxb.rt.UploadAps2WsOdsResponse;
import it.toscana.regione.wsods.ejb.upload.api.IManagerUpload;
import it.toscana.regione.wsods.ejb.upload.api.IPersistUpload;
import it.toscana.regione.wsods.ejb.upload.api.ITransformUpload;
import it.toscana.regione.wsods.exception.UploadException;
import it.toscana.regione.wsods.factory.JaxbElementFactory;
import it.toscana.regione.wsods.lib.SOAPTool;
import it.toscana.regione.wsods.type.Code;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.xml.bind.JAXBElement;
import javax.xml.soap.SOAPBody;
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
@Stateless(name = IManagerUpload.EJB_REF)
public class ManagerUpload implements IManagerUpload {
	

	private static final Logger LOG = LoggerFactory.getLogger(ManagerUpload.class);

	@EJB(beanName= ITransformUpload.EJB_REF)
	private ITransformUpload transformUpload;
	
	@EJB(beanName= IPersistUpload.EJB_REF)
	private IPersistUpload persistUpload;
	
	/**
	 * 
	 */
	public ManagerUpload() { }
	
	
	/** {@inheritDoc} */
	@Override @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void upload(final SOAPMessage request, final SOAPBody bodyResponse) throws UploadException {
		LOG.info("Elabora richioesta di uploas");
		
		final JAXBElement<UploadAps2WsOdsRequest> req = convertiRequest(request);

		persistUpload.store(req.getValue());
		
		final JAXBElement<UploadAps2WsOdsResponse> res = convertiResponse(EsitoType.OK, "Elaborazione andata a buon fine", null);
		
		addElement(bodyResponse,res);
				
	}


	private JAXBElement<UploadAps2WsOdsRequest> convertiRequest(final SOAPMessage request) {
		final Node node = SOAPTool.extractNode(request);
		final JAXBElement<UploadAps2WsOdsRequest>  jaxb = transformUpload.transform(node);
		return jaxb;
	}

	private void addElement(final SOAPBody bodyResponse,final JAXBElement<UploadAps2WsOdsResponse> jaxb){
		
		final SOAPElement element = transformUpload.transform(jaxb);
		
		try {
			bodyResponse.addChildElement(element);
		} catch (SOAPException e) {
			throw new UploadException(Code.GENERICO, "impossibile appendere l'element di risposta al SOAPMessage",e);
		}
		
	}
	
	private static JAXBElement<UploadAps2WsOdsResponse> convertiResponse(final EsitoType esito,final String  descrizione, final String errore) {
		JAXBElement<UploadAps2WsOdsResponse> jaxbResp = JaxbElementFactory.createUploadWAps2WsOdsResponse();
		if(jaxbResp.getValue()!=null){
			jaxbResp.getValue().setEsito(esito);
			jaxbResp.getValue().setDescrizione(descrizione);
			if(errore!=null && errore.trim().length()>0){
				jaxbResp.getValue().setCodiceErrore(errore);
			}else{
				jaxbResp.getValue().setCodiceErrore(null);
			}
		}
		return jaxbResp;
	}
	
}

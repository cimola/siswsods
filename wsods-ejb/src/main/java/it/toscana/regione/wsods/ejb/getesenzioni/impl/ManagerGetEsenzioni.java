package it.toscana.regione.wsods.ejb.getesenzioni.impl;

import it.toscana.regione.autocertificazionireddito.entity.jaxb.rt.GetEsenzioniAps2WsOdsRequest;
import it.toscana.regione.autocertificazionireddito.entity.jaxb.rt.GetEsenzioniAps2WsOdsResponse;
import it.toscana.regione.wsods.ejb.getesenzioni.api.ILoaderGetEsenzioni;
import it.toscana.regione.wsods.ejb.getesenzioni.api.IManagerGetEsenzioni;
import it.toscana.regione.wsods.ejb.getesenzioni.api.ITransformerGetEsenzioni;
import it.toscana.regione.wsods.exception.GetEsenzioniException;
import it.toscana.regione.wsods.exception.TransformException;
import it.toscana.regione.wsods.lib.SOAPTool;
import it.toscana.regione.wsods.type.Code;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.xml.bind.JAXBElement;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;


@Stateless(name= IManagerGetEsenzioni.EJB_REF)
public class ManagerGetEsenzioni implements IManagerGetEsenzioni {

	private static final Logger LOG = LoggerFactory.getLogger(ManagerGetEsenzioni.class);
	
	@EJB(beanName = ITransformerGetEsenzioni.EJB_REF)
	private ITransformerGetEsenzioni transformer;
	
	@EJB(beanName = ILoaderGetEsenzioni.EJB_REF)
	private ILoaderGetEsenzioni loader;
	
	
	public ManagerGetEsenzioni() {super();}
	
	
	@Override
	public void getEsenzioni(SOAPMessage request, SOAPBody bodyResponse) throws GetEsenzioniException {
		
		LOG.info("Elabora richioesta di getEsenzioni");
		
		final Node node = SOAPTool.extractNode(request);

		final JAXBElement<GetEsenzioniAps2WsOdsRequest> req = convertiRequest(node);
		
		final JAXBElement<GetEsenzioniAps2WsOdsResponse> res = loader.load(req);
		
		final SOAPElement element = convertiResponse(res);
		
		addElement(bodyResponse, element);
	}
	
	



	private void addElement(final SOAPBody bodyResponse, final SOAPElement element) throws GetEsenzioniException {
		try {
			bodyResponse.addChildElement(element);
		} catch (SOAPException e) {
			throw new GetEsenzioniException(Code.EJB_GENERICO, "impossibile appendere l'element di risposta al SOAPMessage",e);
		}
	}



	private SOAPElement convertiResponse(final JAXBElement<GetEsenzioniAps2WsOdsResponse> res) throws GetEsenzioniException {
		try {
			final SOAPElement element = transformer.transform(res);
			return element;
		}catch (TransformException e){
			throw new GetEsenzioniException("Impossibile convertire la response del servizio getList",e);
		}
	}



	private JAXBElement<GetEsenzioniAps2WsOdsRequest> convertiRequest(final Node node) throws GetEsenzioniException {
		try {
			final JAXBElement<GetEsenzioniAps2WsOdsRequest> req = transformer.transform(node);
			return req;
		}catch (TransformException e){
			throw new GetEsenzioniException("Impossibile convertire la request del servizio getList",e);
		}
		
	}
	
}

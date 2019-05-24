/** */
package it.toscana.regione.wsods.ejb.getlista.impl;

import it.toscana.regione.autocertificazionireddito.entity.jaxb.rt.GetListaAps2WsOdsRequest;
import it.toscana.regione.autocertificazionireddito.entity.jaxb.rt.GetListaAps2WsOdsResponse;
import it.toscana.regione.wsods.ejb.getlista.api.ILoaderGetLista;
import it.toscana.regione.wsods.ejb.getlista.api.IManagerGetLista;
import it.toscana.regione.wsods.ejb.getlista.api.ITransformerGetLista;
import it.toscana.regione.wsods.exception.GetListaException;
import it.toscana.regione.wsods.exception.TransformException;
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
@Stateless(name = IManagerGetLista.EJB_REF)
public class ManagerGetLista implements IManagerGetLista {
	

	private static final Logger LOG = LoggerFactory.getLogger(ManagerGetLista.class);

	@EJB(beanName = ITransformerGetLista.EJB_REF)
	private ITransformerGetLista transformer;
	
	@EJB(beanName = ILoaderGetLista.EJB_REF)
	private ILoaderGetLista loader;
	
	public ManagerGetLista() { }



	/** {@inheritDoc} */
	@Override @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void getLista(final SOAPMessage request, final SOAPBody bodyResponse) throws GetListaException {
		LOG.info("Elabora richioesta di getLista");
		final Node node = SOAPTool.extractNode(request);
		
		final JAXBElement<GetListaAps2WsOdsRequest> req = convertiRequest(node);
		
		final JAXBElement<GetListaAps2WsOdsResponse> res = loader.load(req);
		
		final SOAPElement element = convertiResponse(res);
		
		addElement(bodyResponse, element);
	}



	private void addElement(final SOAPBody bodyResponse, final SOAPElement element) throws GetListaException {
		try {
			bodyResponse.addChildElement(element);
		} catch (SOAPException e) {
			throw new GetListaException(Code.EJB_GENERICO, "impossibile appendere l'element di risposta al SOAPMessage",e);
		}
	}



	private SOAPElement convertiResponse(final JAXBElement<GetListaAps2WsOdsResponse> res) throws GetListaException {
		try {
			final SOAPElement element = transformer.transform(res);
			return element;
		}catch (TransformException e){
			throw new GetListaException("Impossibile convertire la response del servizio getList",e);
		}
	}



	private JAXBElement<GetListaAps2WsOdsRequest> convertiRequest(final Node node) throws GetListaException {
		try {
			final JAXBElement<GetListaAps2WsOdsRequest> req = transformer.transform(node);
			return req;
		}catch (TransformException e){
			throw new GetListaException("Impossibile convertire la request del servizio getList",e);
		}
		
	}
	
}

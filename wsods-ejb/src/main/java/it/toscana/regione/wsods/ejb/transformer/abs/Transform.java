/** */
package it.toscana.regione.wsods.ejb.transformer.abs;

import it.toscana.regione.wsods.ejb.transformer.api.ITransform;
import it.toscana.regione.wsods.exception.TransformException;
import it.toscana.regione.wsods.lib.JAXBTool;
import it.toscana.regione.wsods.lib.SOAPTool;
import it.toscana.regione.wsods.lib.XMLTool;
import it.toscana.regione.wsods.type.Code;

import javax.annotation.PostConstruct;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.soap.SOAPElement;
import javax.xml.validation.Schema;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;


/**
 * @author cciurli
 *
 */
public abstract class Transform <MT,UT> implements ITransform <MT,UT> {
	
	private static final Logger LOG = LoggerFactory.getLogger(Transform.class);
	
	private Schema schemaMarshaller;
	private Schema schemaUnmarshaller;
	private Marshaller marshaller;
	private Unmarshaller unmarshaller;
	
	/**
	 * 
	 */
	public Transform() {super();}
	
	
	@PostConstruct
	public void init() throws TransformException {
		try {
			LOG.info("Init {} ejb ", ITransform.EJB_REF);
			initSchema();
			initMarshaller();
			initUnmarshaller();
		} catch (final Throwable t) {
			throw new TransformException(Code.EJB_POST_CONSTRUCT, t);
		}
		
	}
	
	
	/** {@inheritDoc} */
	@Override @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public JAXBElement<UT> transform(final Node node) {
		verificaRisorse();
		if (node == null) { throw new TransformException(Code.TRANSFORM_UNMARSCHALLER, "impossibile effettuare l'unmarshal di una request nulla"); }
		try {
			return 	unmarshaller.unmarshal(node, getClassTypeUnmarshaller());
		} catch (JAXBException e) {
			throw new TransformException(Code.TRANSFORM_UNMARSCHALLER,"impossibile effettuare il unmarshal della request",e);
		}
	}
	
	
	/** {@inheritDoc} */
	@Override @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public SOAPElement transform(JAXBElement<MT> jaxb) {
		verificaRisorse();
		final Document doc = XMLTool.createEmptyDocument(schemaMarshaller, true, true);
		if (jaxb == null) { throw new TransformException(Code.TRANSFORM_MARSCHALLER, "impossibile effettuare il marshal di una response nulla"); }
		if (doc == null) { throw new TransformException(Code.GENERICO, "impossibile creatre un Document vuoto per la response"); }
		
		try {
			marshaller.marshal(jaxb, doc);
		} catch (JAXBException e) {
			throw new TransformException(Code.TRANSFORM_MARSCHALLER,"impossibile effettuare il marshal della response",e);
		}
		final Element element = doc.getDocumentElement();
		if (element == null) { throw new TransformException(Code.TRANSFORM_MARSCHALLER, "impossibile estrarre un element dal document ottenuto con il Marscaller"); }
		final SOAPElement soapElement = SOAPTool.toSOAPElement(element);
		if (soapElement == null) { throw new TransformException(Code.TRANSFORM_MARSCHALLER, "impossibile estrarre un soapelement dal document ottenuto con il Marscaller"); }
		return soapElement;
		
		
	}

	/** {@inheritDoc} */
	@Override @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Document transform(MT jaxb) {
		verificaRisorse();
		final Document doc = XMLTool.createEmptyDocument(schemaMarshaller, true, true);
		if (jaxb == null) { throw new TransformException(Code.TRANSFORM_MARSCHALLER, "impossibile effettuare il marshal di una response nulla"); }
		if (doc == null) { throw new TransformException(Code.GENERICO, "impossibile creatre un Document vuoto per la response"); }
		
		try {
			marshaller.marshal(jaxb, doc);
		} catch (JAXBException e) {
			throw new TransformException(Code.TRANSFORM_MARSCHALLER,"impossibile effettuare il marshal della response",e);
		}
		return doc;
	}
	private void verificaRisorse() {
		if (schemaMarshaller == null || schemaUnmarshaller == null ) {
			initSchema();
		}
		if (marshaller == null) {
			initMarshaller();
		}
		if (unmarshaller == null) {
			initUnmarshaller();
		}
	}
	private void initSchema() {
		final long start = System.currentTimeMillis();
		LOG.info("load schema");
		try {
			schemaMarshaller = XMLTool.loadSchema(getXsdMarshaller());
			schemaUnmarshaller = XMLTool.loadSchema(getXsdUnmarshaller());
		} finally {
			LOG.debug("[PERFORMANCE] - load schema in {}.", System.currentTimeMillis() - start);
		}
		
	}
	
	
	private void initUnmarshaller() {
		final long start = System.currentTimeMillis();
		LOG.info("init Unmarshaller");
		try {
			final JAXBContext jaxbContext = JAXBTool.createJAXBContext(getClassTypeUnmarshaller().getPackage().getName());
			unmarshaller = JAXBTool.createUnmarshaller(schemaUnmarshaller, jaxbContext);
		} finally {
			LOG.debug("[PERFORMANCE] - init Unmarshaller in {}.", System.currentTimeMillis() - start);
		}
	}
	
	
	private void initMarshaller() {
		final long start = System.currentTimeMillis();
		LOG.info("init Marshaller");
		try {
			final JAXBContext jaxbContext = JAXBTool.createJAXBContext(getClassTypeMarshaller().getPackage().getName());
			marshaller = JAXBTool.createMarshaller(schemaMarshaller, jaxbContext, true);
		} finally {
			LOG.debug("[PERFORMANCE] - init Marshaller in {}.", System.currentTimeMillis() - start);
		}
	}

	protected abstract String getEjbRef();
	protected abstract String getXsdMarshaller();
	protected abstract String getXsdUnmarshaller();

	protected abstract Class<MT> getClassTypeMarshaller();
	protected abstract Class<UT> getClassTypeUnmarshaller();
	
	
}

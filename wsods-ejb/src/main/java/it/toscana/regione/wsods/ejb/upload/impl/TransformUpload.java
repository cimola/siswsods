/** */
package it.toscana.regione.wsods.ejb.upload.impl;

import it.toscana.regione.autocertificazionireddito.entity.jaxb.rt.UploadAps2WsOdsRequest;
import it.toscana.regione.autocertificazionireddito.entity.jaxb.rt.UploadAps2WsOdsResponse;
import it.toscana.regione.wsods.ejb.upload.api.ITransformUpload;

import javax.ejb.Stateless;

import it.toscana.regione.wsods.ejb.transformer.abs.Transform;

/**
 * @author cciurli
 *
 */
@Stateless(name = ITransformUpload.EJB_REF)
public class TransformUpload extends Transform< UploadAps2WsOdsResponse, UploadAps2WsOdsRequest> implements ITransformUpload {

	/** {@inheritDoc} */
	@Override
	protected String getEjbRef() {
		return ITransformUpload.EJB_REF;
	}

	/** {@inheritDoc} */
	@Override
	protected String getXsdMarshaller() {
		return "/META-INF/xsd/AutocertificazioniReddito.xsd";
	}

	/** {@inheritDoc} */
	@Override
	protected String getXsdUnmarshaller() {
		return "/META-INF/xsd/AutocertificazioniReddito.xsd";
	}

	/** {@inheritDoc} */
	@Override
	protected Class<UploadAps2WsOdsResponse> getClassTypeMarshaller() {
		return UploadAps2WsOdsResponse.class;
	}

	/** {@inheritDoc} */
	@Override
	protected Class<UploadAps2WsOdsRequest> getClassTypeUnmarshaller() {
		return UploadAps2WsOdsRequest.class;
	}
	
//
//
//	
//	private static final Logger LOG = LoggerFactory.getLogger(ManagerUpload.class);
//	
//	private Schema schema;
//	private Marshaller marshaller;
//	private Unmarshaller unmarshaller;
//	
//	/**
//	 * 
//	 */
//	public TransformUpload() {super();}
//	
//	
//	@PostConstruct
//	public void init() throws EjbException {
//		try {
//			LOG.info("Init {} ejb ", ITransformUpload.EJB_REF);
//			initSchema();
//			initMarshaller();
//			initUnmarshaller();
//		} catch (final Throwable t) {
//			throw new EjbException(Code.EJB_POST_CONSTRUCT, t);
//		}
//		
//	}
//	
//	
//	/** {@inheritDoc} */
//	@Override @TransactionAttribute(TransactionAttributeType.MANDATORY)
//	public JAXBElement<UploadAps2WsOdsRequest> transform(final Node node) {
//		verificaRisorse();
//		if (node == null) { throw new WsOdsRuntimeException(Code.UNMARSHAL, "impossibile effettuare l'unmarshal di una request nulla"); }
//		try {
//			return 	unmarshaller.unmarshal(node, UploadAps2WsOdsRequest.class);
//		} catch (JAXBException e) {
//			throw new WsOdsRuntimeException(Code.UNMARSHAL,"impossibile effettuare il unmarshal della request",e);
//		}
//	}
//	
//	
//	/** {@inheritDoc} */
//	@Override @TransactionAttribute(TransactionAttributeType.MANDATORY)
//	public SOAPElement transform(JAXBElement<UploadAps2WsOdsResponse> jaxb) {
//		verificaRisorse();
//		final Document doc = XMLTool.createEmptyDocument(schema, true, true);
//		if (jaxb == null) { throw new WsOdsRuntimeException(Code.MARSHAL, "impossibile effettuare il marshal di una response nulla"); }
//		if (doc == null) { throw new WsOdsRuntimeException(Code.GENERICO, "impossibile creatre un Document vuoto per la response"); }
//		
//		try {
//			marshaller.marshal(jaxb, doc);
//		} catch (JAXBException e) {
//			throw new WsOdsRuntimeException(Code.MARSHAL,"impossibile effettuare il marshal della response",e);
//		}
//		final Element element = doc.getDocumentElement();
//		if (element == null) { throw new WsOdsRuntimeException(Code.MARSHAL, "impossibile estrarre un element dal document ottenuto con il Marscaller"); }
//		final SOAPElement soapElement = SOAPTool.toSOAPElement(element);
//		if (soapElement == null) { throw new WsOdsRuntimeException(Code.MARSHAL, "impossibile estrarre un soapelement dal document ottenuto con il Marscaller"); }
//		return soapElement;
//		
//		
//	}
//	
//	private void verificaRisorse() {
//		if (schema == null) {
//			initSchema();
//		}
//		if (marshaller == null) {
//			initMarshaller();
//		}
//		if (unmarshaller == null) {
//			initUnmarshaller();
//		}
//	}
//	private void initSchema() {
//		final long start = System.currentTimeMillis();
//		LOG.info("load schema");
//		try {
//			schema = XMLTool.loadSchema("/META-INF/xsd/AutocertificazioniReddito.xsd");
//		} finally {
//			LOG.debug("load schema in {}.", System.currentTimeMillis() - start);
//		}
//		
//	}
//	
//	
//	private void initUnmarshaller() {
//		final long start = System.currentTimeMillis();
//		LOG.info("init Unmarshaller");
//		try {
//			final JAXBContext jaxbContext = JAXBTool.createJAXBContext(UploadAps2WsOdsRequest.class.getPackage().getName());
//			unmarshaller = JAXBTool.createUnmarshaller(schema, jaxbContext);
//		} finally {
//			LOG.debug("init Unmarshaller in {}.", System.currentTimeMillis() - start);
//		}
//	}
//	
//	
//	private void initMarshaller() {
//		final long start = System.currentTimeMillis();
//		LOG.info("init Marshaller");
//		try {
//			final JAXBContext jaxbContext = JAXBTool.createJAXBContext(UploadAps2WsOdsResponse.class.getPackage().getName());
//			marshaller = JAXBTool.createMarshaller(schema, jaxbContext, true);
//		} finally {
//			LOG.debug("init Marshaller in {}.", System.currentTimeMillis() - start);
//		}
//	}
	
}

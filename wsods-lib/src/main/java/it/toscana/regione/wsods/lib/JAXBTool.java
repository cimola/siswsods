/**
 * 
 * your comment here.
 *
 */
package it.toscana.regione.wsods.lib;

import it.toscana.regione.wsods.exception.WsOdsRuntimeException;
import it.toscana.regione.wsods.type.Code;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * @author cciurli
 *
 */
public final class JAXBTool {
	
	/** 
	 * costruttore di default.
	 * Privato per impedire l'instanzazione della classe che deve contenere
	 * solo metodi statici.
	 */
	private JAXBTool() {
	}
	
	/** logger. */
	private static final Logger LOG = LoggerFactory.getLogger(JAXBTool.class);
	
	/**
	 * Istanzia il Jaxb Context per gli oggetti jazb del path fornito in ingresso.
	 * 
	 * @param jaxbObjectPackage		package deigli oggetti JAXB
	 * @return						jaxb context
	 * @throws WsOdsRuntimeException	sollevata in caso di problemi sull'instanzazione del conteso.
	 */
	public static JAXBContext createJAXBContext(final String jaxbObjectPackage) throws WsOdsRuntimeException  {
		long startTime = System.currentTimeMillis();
		try {
			return JAXBContext.newInstance(jaxbObjectPackage); 
		} catch (JAXBException e) {
			LOG.warn("impossibile generale il jaxb context del package {}.",  jaxbObjectPackage);
			throw new WsOdsRuntimeException(Code.TOOL_JAXB_CONTEXT,"impossibile generale il jaxb context del package ["+jaxbObjectPackage+"]", e);
		} finally {
			LOG.debug("[PERFORMANCE] - Istance JAXBContext relativo ai package {} in {} ms" , jaxbObjectPackage, (System.currentTimeMillis()-startTime));
		}
	}
	
	/**
	 * Si ottiene Unmarshaller sul JaxbContext passatoli.
	 * @param schema				Schema xsd da impostare sul'unmarshaller.
	 * @param jaxbContext			contestgo deigli oggetti JAXB per cui generare il JAXBContext.
	 * @return						Unmarshaller sul jaxb context passatoli.
	 * @throws WsOdsRuntimeException	sollevata in caso di problemi sulla creazione del Unmarshaller.
	 */
	public static Unmarshaller createUnmarshaller(final Schema schema, final JAXBContext jaxbContext) throws WsOdsRuntimeException {
		long startTime = System.currentTimeMillis();
		try {
			Unmarshaller unmarshaller;
			try {
				unmarshaller = jaxbContext.createUnmarshaller();
			} catch (JAXBException e) {
				String msg = "impossibile generale il l' unmarshaller del jaxbContext ";
				LOG.warn(msg);
				throw new WsOdsRuntimeException(Code.TOOL_UNMARSHALLER,msg, e);
			}
			if (schema != null) {
				unmarshaller.setSchema(schema);
			} else {
				LOG.warn("generato l'Unmarshaller senza impostargli lo schema");
			}
			return unmarshaller;
		} finally {
			LOG.debug("[PERFORMANCE] - Creo l'Unmarshaller in {}. ms", (System.currentTimeMillis()-startTime));
		}
	}
	
	/**
	 * Si ottiene Marshaller sul JaxbContext passatoli e lo schema passatoli, e` possibile anche settare se si desidera un output formattato..
	 * 
	 * @param schema					Schema xsd da impostare il'marshaller.
	 * @param jaxbContext				contestgo deigli oggetti JAXB per cui generare il JAXBContext.
	 * @param jaxbFormatOutput			Stabilisce de formattare l'output.
	 * @return							Marshaller sul jaxb context passatoli.
	 * @throws WsOdsRuntimeException		sollevata in caso di problemi sulla creazione del Marshaller o sul set delle properties.
	 */
	public static Marshaller createMarshaller(final Schema schema, final JAXBContext jaxbContext, final Boolean jaxbFormatOutput) throws WsOdsRuntimeException {
		long startTime = System.currentTimeMillis();
		try {
			Marshaller marshaller;
			try {
				marshaller = jaxbContext.createMarshaller();
			} catch (JAXBException e) {
				String msg = "impossibile generale il l' marshaller del jaxbContext ";
				LOG.warn(msg);
				throw new WsOdsRuntimeException(Code.TOOL_MARSHALLER,msg, e);
			}
			if (schema != null) {
				marshaller.setSchema(schema);
			} else {
				LOG.warn("generato il Marshaller senza impostargli lo schema");
			}
			
			try {
				marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, jaxbFormatOutput);
			} catch (PropertyException e) {
				LOG.warn("impossibile settare la properties {}. a  {}. ",Marshaller.JAXB_FORMATTED_OUTPUT, jaxbFormatOutput.toString());
				throw new WsOdsRuntimeException(Code.TOOL_MARSHALLER,"impossibile settare la properties "+ Marshaller.JAXB_FORMATTED_OUTPUT+ " a "+ jaxbFormatOutput.toString(), e);
			}
			return marshaller;
		} finally {
			LOG.debug("[PERFORMANCE] - Creo il Marshaller in {}. ms", (System.currentTimeMillis()-startTime));
		}
	}
	
	
	/**
	 * Facciamo il Marshaller del JaxbElement passatoli.
	 * 
	 * @param marshaller			Marshaller
	 * @param elemento				JaxbElement su cui operare il Marshaller
	 * @param validating			setta la validazione sul document.
	 * @param namespaceAware		definisce se il document richiede namespace.
	 * @return						Document rappresentante il JaxbElement passatoli.
	 * @throws WsOdsRuntimeException	sollecvata in caso siano presenti problemi sulla creazioen del document di destinazione o sul marschaller del JAXBElement.
	 */
	public static Document marshal(final Marshaller marshaller, final JAXBElement< ? > elemento, final boolean validating, final boolean namespaceAware)
			throws WsOdsRuntimeException {
		return marshal(marshaller, null, elemento, validating, namespaceAware);
	}
	
	/**
	 * Facciamo il Marshaller del JaxbElement passatoli.
	 * 
	 * @param marshaller			Marshaller.
	 * @param schema				Schema xsd da impostare il'marshaller.
	 * @param elemento				JaxbElement su cui operare il Marshaller
	 * @param validating			setta la validazione sul document.
	 * @param namespaceAware		definisce se il document richiede namespace.
	 * @return						Document rappresentante il JaxbElement passatoli.
	 * @throws WsOdsRuntimeException	sollecvata in caso siano presenti problemi sulla creazioen del document di destinazione o sul marschaller del JAXBElement.
	 */
	public static Document marshall(final Marshaller marshaller, final Schema schema, final JAXBElement< ? > elemento, final boolean validating, final boolean namespaceAware) throws WsOdsRuntimeException {
		long startTime = System.currentTimeMillis();
		try {
			Document doc = null;
			if (schema != null) {
				doc = XMLTool.createEmptyDocument(schema, validating, namespaceAware);
			} else {
				doc = XMLTool.createEmptyDocument(namespaceAware);
				if (validating) {
					LOG.warn("Creo il document senza Validazione.");
				}
				LOG.warn("Creo il document senza Schema.");
			}
			
			try {
				marshaller.marshal(elemento, doc);
			} catch (JAXBException e) {
				String msg = "impossibile fare il marschel del jaxb sul document ";
				LOG.warn(msg);
				throw new WsOdsRuntimeException(Code.TOOL_MARSHALLER,msg, e);
			}
			doc.normalizeDocument();
			return doc;
		} finally {
			LOG.debug("[PERFORMANCE] - effettuo il marshal del elemento jaxb in {}.",(System.currentTimeMillis() - startTime));
		}
	}
	
	/**
	 * Facciamo il Marshaller del Object passatoli.
	 * 
	 * @param marshaller			Marshaller
	 * @param obj					Object su cui operare il Marshaller
	 * @param validating			setta la validazione sul document.
	 * @param namespaceAware		definisce se il document richiede namespace.
	 * @return						Document rappresentante il JaxbElement passatoli.
	 * @throws WsOdsRuntimeException	sollecvata in caso siano presenti problemi sulla creazioen del document di destinazione o sul marschaller del Object.
	 */
	public static Document marshal(final Marshaller marshaller, final Object obj, final boolean validating, final boolean namespaceAware)
			throws WsOdsRuntimeException {
		return marshal(marshaller, null, obj, validating, namespaceAware);
	}
	
	/**
	 * Facciamo il Marshaller del Object passatoli.
	 * 
	 * @param marshaller			Marshaller.
	 * @param schema				Schema xsd da impostare il'marshaller.
	 * @param obj					Object su cui operare il Marshaller
	 * @param validating			setta la validazione sul document.
	 * @param namespaceAware		definisce se il document richiede namespace.
	 * @return						Document rappresentante il JaxbElement passatoli.
	 * @throws WsOdsRuntimeException	sollecvata in caso siano presenti problemi sulla creazioen del document di destinazione o sul marschaller del Object.
	 */
	public static Document marshal(final Marshaller marshaller, final Schema schema, final Object obj, final boolean validating, final boolean namespaceAware)
			throws WsOdsRuntimeException {
		long startTime = System.currentTimeMillis();
		try {
			Document doc = null;
			if (schema != null) {
				doc = XMLTool.createEmptyDocument(schema, validating, namespaceAware);
			} else {
				doc = XMLTool.createEmptyDocument(namespaceAware);
				if (validating) {
					LOG.warn("Creo il document senza Validazione.");
				}
				LOG.warn("Creo il document senza Schema.");
			}
			try {
				marshaller.marshal(obj, doc);
			} catch (JAXBException e) {
				String msg = "impossibile fare il marschel del jaxb sul document ";
				LOG.warn(msg);
				throw new WsOdsRuntimeException(Code.TOOL_MARSHALLER,msg, e);
			}
			doc.normalizeDocument();
			return doc;
		} finally {
			LOG.debug("[PERFORMANCE] - effettuo il marshal del Object in {}.",(System.currentTimeMillis() - startTime));
		}
	}
	
	
	/**
	 * Facciamo il Unmarshaller del Node passatoli.
	 * 
	 * @param unmarshaller			Unmarshaller
	 * @param declaredType			Definisce il tipo di classe da ritornare.
	 * @param node					Node di cui ottenere i JAXBElement.
	 * @return						JaxbElement rappresentante il Document passatoli.
	 * @throws WsOdsRuntimeException	sollevata in caso ci siano problemi sull'unmarshal del node.
	 */
	public static JAXBElement< ? > unmarshall(final Unmarshaller unmarshaller, final Class< ? > declaredType, final Node node) throws WsOdsRuntimeException {
		long startTime = System.currentTimeMillis();
		if (node == null) {
			String msg = "nodo su cui fare l'unmarshall e` nullo ";
			LOG.warn(msg);
			throw new WsOdsRuntimeException(Code.PARAMETRO_NULLO,msg);
		}
		if (declaredType == null) {
			String msg = "declaredType con cui fare l'unmarshall e` nullo ";
			LOG.warn(msg);
			throw new WsOdsRuntimeException(Code.PARAMETRO_NULLO,msg);
		}
        try {
			return unmarshaller.unmarshal(node, declaredType);
		} catch (JAXBException e) {
			String msg = "impossibile fare il unmarschel del document ";
			LOG.warn(msg);
			throw new WsOdsRuntimeException(Code.TOOL_UNMARSHALLER,msg, e);
		} finally {
			LOG.debug("[PERFORMANCE] - effettuo il unmarshall del elemento jaxb in {}.",(System.currentTimeMillis() - startTime));
		}
    }
	/**
	 * Facciamo il Unmarshaller del Document passatoli.
	 * 
	 * @param unmarshaller			Unmarshaller
	 * @param doc					Document di cui ottenere i JAXBElement.
	 * @return						Object rappresentante il Document passatoli.
	 * @throws WsOdsRuntimeException	sollevata in caso ci siano problemi sull'unmarshal del Document.
	 */
	public static Object unmarshal(final Unmarshaller unmarshaller, final Document doc) throws WsOdsRuntimeException {
		long startTime = System.currentTimeMillis();
		try {
			return unmarshaller.unmarshal(doc);
		} catch (JAXBException e) {
			String msg = "impossibile fare il unmarschel del document ";
			LOG.warn(msg);
			throw new WsOdsRuntimeException(Code.TOOL_UNMARSHALLER,msg, e);
		}  finally {
			LOG.debug("[PERFORMANCE] - effettuo il unmarshall del Object in {}.",(System.currentTimeMillis() - startTime));
		}
	}
	
	
	/**
	 * mappa l'oggetto JAXB passatoli su un document generatosecondo le indicazioni passateli.
	 * @param jaxbElement			Elemento da cui prendere le info.
	 * @param schema				schema xsd del document da generare.
	 * @param jaxbObjectPackage		package del jaxb
	 * @return						Document 
	 * @throws WsOdsRuntimeException	sollevata in caso ci siano problemi sull'unmarshal del Document.
	 */
	public static Document getNodeFormJaxb(final JAXBElement< ? >  jaxbElement, final Schema schema, final String jaxbObjectPackage) throws WsOdsRuntimeException {
		JAXBContext jaxbContext = JAXBTool.createJAXBContext(jaxbObjectPackage);
		Marshaller marshaller = JAXBTool.createMarshaller(schema, jaxbContext, false);
		//Document doc = XMLTool.createEmptyDocument(schema, false, true);
		Document doc = XMLTool.createEmptyDocument(true);
		try {
			marshaller.marshal(jaxbElement.getValue(), doc);
		} catch (JAXBException e) {
			String msg = "impossibile mappare il jaxbElement sul document ";
			LOG.warn(msg);
			throw new WsOdsRuntimeException(Code.TOOL_MARSHALLER,msg, e);
		}
		return doc;
	}
	
}

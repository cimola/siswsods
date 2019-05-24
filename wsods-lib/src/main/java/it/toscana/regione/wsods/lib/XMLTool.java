/**
 * 
 * your comment here.
 *
 */
package it.toscana.regione.wsods.lib;


import it.toscana.regione.wsods.exception.WsOdsRuntimeException;
import it.toscana.regione.wsods.type.Code;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.URL;

import javax.xml.XMLConstants;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * @author cciurli
 *
 */
public final class XMLTool {
	
	/** 
	 * costruttore di default.
	 * Privato per impedire l'instanzazione della classe che deve contenere
	 * solo metodi statici.
	 */
	private XMLTool() {
	}
	
	/** logger. */
	private static final Logger LOG = LoggerFactory.getLogger(XMLTool.class);
	
	/**
	 * Estrae il nodo che corrisponde al qName passatoli.
	 * @param node	nodo su cui cercare
	 * @param qName	qName da cercare
	 * @return	nodo cossispondente o null nell'eventualita` che non lo trovi.
	 */
	public static Node extract(final Node node, final QName qName) {
		if (node == null || qName == null) {
			return null;
		}
		if (identifica(node, qName)) {
			return node;
		}
		NodeList list = node.getChildNodes();
		boolean trovato = false;
		int i = 0;
		while (!trovato && i < list.getLength()) {
			if (list.item(i) != null) {
				if (identifica(list.item(i), qName)) {
					trovato = true;
				} else {
					i++;
				}
			} else {
				i++;
			}
		}
		return extract(list.item(i), qName);
	}
	
	/**
	 * verifica se il nodo passatoli come input differisce dal qName.
	 * @param node 	nodo su cui verificare il cuname
	 * @param qName	qName con cui verificare il nodo
	 * @return true nel caso il nodo passato non abbia elementi in disaccordo con il qName passatoli, se il namespaceUri, local name o prefix sono in disaccordo
	 */
	private static boolean identifica(final Node node, final QName qName) {
		if (qName.getNamespaceURI() != null && !qName.getNamespaceURI().equals(node.getNamespaceURI())) {
			return false;
		}
		if (qName.getLocalPart() != null && !qName.getLocalPart().equals(node.getLocalName())) {
			return false;
		}
		if (qName.getPrefix() != null && !qName.getPrefix().equals(node.getPrefix())) {
			return false;
		}
		return true;
	}
    /**
     * Crea un document vuoto.
     * 
     * @param namespaceAware		atributo della factory del Document per il name space.
     * @return						document vuoto.
     * @throws WsOdsRuntimeException	sollevata nel caso ci sia nproblemi nella generazione del document.
     */
    public static Document createEmptyDocument(final boolean namespaceAware)  throws WsOdsRuntimeException {
    	return createEmptyDocument(null, false, namespaceAware);
    }
    
    /**
     * Crea un document vuoto.
     * 
     * @param schema				Schema del del document.
     * @param validating			atributo della factory del Document per la validazione.
     * @param namespaceAware		atributo della factory del Document per il name space.
     * @return						document vuoto.
     * @throws WsOdsRuntimeException	sollevata nel caso ci sia nproblemi nella generazione del document.
     */
    public static Document createEmptyDocument(final Schema schema, final boolean validating, final boolean namespaceAware) throws WsOdsRuntimeException {

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setIgnoringElementContentWhitespace(true);
		if (schema != null) {
			factory.setSchema(schema);
		} else {
			LOG.warn("non si sta usando uno Schema per il Document di output");
		}
		factory.setValidating(validating);
		factory.setNamespaceAware(namespaceAware);
		DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			String msg = "impossibile generare il builder del document";
			LOG.warn(msg);
			throw new WsOdsRuntimeException(Code.GENERICO,msg, e);
		}

		return  builder.newDocument();
    }
    
    
    
	/**
	 * Carico un document da input stream.
	 * 
	 * @param xmlInputStream		input stream da dove recuperare il document.
     * @param validating			atributo della factory del Document per la validazione.
	 * @param namespaceAware		variabile da settare sulla factory del document.
	 * @return						Document caricato.
	 * @throws WsOdsRuntimeException	se ci sono problemi nel caricare il document.
	 */
	public  static Document loadDocument(final InputStream xmlInputStream, final boolean validating, final boolean namespaceAware) throws WsOdsRuntimeException {
		return loadDocument(xmlInputStream, (Schema) null, validating, namespaceAware);
	}


	/**
	 * Carico un document da input stream validandolo con uno schema.
	 * 
	 * @param xmlInputStream		input stream da dove recuperare il document.
	 * @param xsdSchema				Schema con cui validare il document, se nullo non valido il document.
     * @param validating			atributo della factory del Document per la validazione.
	 * @param namespaceAware		variabile da settare sulla factory del document.
	 * @return						Document caricato.	
	 * @throws WsOdsRuntimeException 	se ci sono problemi nel caricare il document.
	 */
	public  static Document loadDocument(final InputStream xmlInputStream, final Schema xsdSchema, final boolean validating, final boolean namespaceAware) throws WsOdsRuntimeException {
		Document doc =  null;
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		if (xsdSchema != null) {
			factory.setSchema(xsdSchema);
		} else {
			LOG.trace("genero il document senza impiegare schema");
		}
		factory.setValidating(validating);
		factory.setNamespaceAware(namespaceAware);
		DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
			doc = builder.parse(xmlInputStream);
		} catch (ParserConfigurationException e) {
			String msg = "Impossibile generare il Builder per il Document";
			LOG.warn(msg);
			throw new WsOdsRuntimeException(Code.GENERICO,msg, e);
		} catch (SAXException e) {
			String msg =  "Impossibile parsare il Document";
			LOG.warn(msg);
			throw new WsOdsRuntimeException(Code.PARSER,msg, e);
		} catch (IOException e) {
			String msg = "Impossibile parsare il Document" ;
			LOG.warn(msg);
			throw new WsOdsRuntimeException(Code.PARSER,msg.toString(), e);
		}
		return doc;
	}

	/**
	 * Ottiene uno schema per la validazione degli xml da un url passatali.
	 * 
	 * @param xsdFile				file dello schema.
	 * @return						Schema ottenuto.
	 * @throws WsOdsRuntimeException	In caso di problemi nell'ottenere lo schema dall'url.
	 */
	public static Schema loadSchema(final File xsdFile) throws WsOdsRuntimeException {
		long startTime = System.currentTimeMillis();
		try {
			SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			if (xsdFile == null) {
				String msg = "impossibile caricare uno schema da un File nullo.";
				LOG.warn(msg);
				throw new WsOdsRuntimeException(Code.GENERICO,msg);
			}
			return sf.newSchema(xsdFile);
		} catch (SAXException e) {
			LOG.warn("impossibile caricare lo schema da file [{}.] ",xsdFile.getPath());
			throw new WsOdsRuntimeException(Code.GENERICO,"impossibile caricare lo schema da file ["+ xsdFile.getPath()+ "]", e);
		} finally {
			if (xsdFile != null) {
				LOG.debug("[PERFORMANCE] - caricato lo Schema dal file {}. in {}. ", xsdFile.getPath(), (System.currentTimeMillis()-startTime));
			}
		}
		
	}
	/**
	 * Ottiene uno schema per la validazione degli xml da un url passatali.
	 * 
	 * @param xsdURL				url dello schema.
	 * @return						Schema ottenuto.
	 * @throws WsOdsRuntimeException	In caso di problemi nell'ottenere lo schema dall'url.
	 */
	public static Schema loadSchema(final URL xsdURL) throws WsOdsRuntimeException {
		long startTime = System.currentTimeMillis();
		try {
			SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			if (xsdURL == null) {
				String msg = "impossibile caricare uno schema da un Url nulla.";
				LOG.warn(msg);
				throw new WsOdsRuntimeException(Code.GENERICO,msg);
			}
			return sf.newSchema(xsdURL);
		} catch (SAXException e) {
			LOG.warn("impossibile caricare lo schema dall'URL [{}.] ",xsdURL.getPath());
			throw new WsOdsRuntimeException(Code.GENERICO,"impossibile caricare lo schema dall'URL ["+ xsdURL.getPath()+ "]", e);
		} finally {
			if (xsdURL != null) {
				LOG.debug("[PERFORMANCE] - caricato lo Schema dal url {}. in {}.",xsdURL.toString(), (System.currentTimeMillis()-startTime));
			}
		}
		
	}
	/**
	 * Ottiene uno schema per la validazione degli xml da una risorsa caricata dal classloader.
	 * 
	 * @param xsdURL				path su cui il classloader ricerca.
	 * @return						Schema ottenuto.
	 * @throws WsOdsRuntimeException	In caso di problemi nell'ottenere lo schema dall'url.
	 */
	public static Schema loadSchema(final String xsdResourceURL) throws WsOdsRuntimeException {
		return loadSchema(getResourceURL(xsdResourceURL));
	}
    /**
     * Restituisce il classLoader del corrente Thread.
     * @return	classLoader del corrente thread.
     */
    private static ClassLoader getClassLoader() {
    	return Thread.currentThread().getContextClassLoader();
    }
	/**
	 * Restituisce l'URL di un file preso dal classLoader.
	 * @param relativePath	path della risorsa.
	 * @return	URL della risorsa.
	 */
	private static URL getResourceURL(final String relativePath) {
		URL url = null;
		url =  getClassLoader().getResource(relativePath);
		if (url == null) {
			LOG.warn("ottenuta un url nulla per il path: [{}.]",relativePath);
		}
		return url;
		
	}
	
	public static String node2string(final Node node, final boolean indent) throws WsOdsRuntimeException {
		long startTime = System.currentTimeMillis();
		String xmlString = null;
		try {
			Transformer transformer = null;
			try {
				transformer = TransformerFactory.newInstance().newTransformer();
			} catch (TransformerConfigurationException e) {
				String msg = "TransformerConfigurationException: Impossibile ottenere il transformer";
				LOG.warn(msg);
				throw new WsOdsRuntimeException(Code.GENERICO,msg, e);
			} catch (TransformerFactoryConfigurationError e) {
				String msg = "TransformerFactoryConfigurationError: Impossibile ottenere il transformer";
				LOG.warn(msg);
				throw new WsOdsRuntimeException(Code.GENERICO,msg, e);
			}
			if (indent) {
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			} else {
				transformer.setOutputProperty(OutputKeys.INDENT, "no");
			}
			StreamResult result = new StreamResult(new StringWriter());
			DOMSource source = new DOMSource(node);
			try {
				transformer.transform(source, result);
			} catch (TransformerException e) {
				String msg = "Impossibile effetuare il transformer";
				LOG.warn(msg);
				throw new WsOdsRuntimeException(Code.GENERICO,msg, e);
			}
	
			xmlString = result.getWriter().toString();
			return xmlString;
		} finally {
			LOG.debug("[PERFORMANCE] - serializato il Node in {}.", (System.currentTimeMillis()-startTime));
			LOG.trace(xmlString);
		}
	}
	
}

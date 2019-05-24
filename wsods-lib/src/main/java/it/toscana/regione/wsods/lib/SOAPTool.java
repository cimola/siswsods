/**
 * 
 * your comment here.
 *
 */
package it.toscana.regione.wsods.lib;

import it.toscana.regione.wsods.exception.WsOdsRuntimeException;
import it.toscana.regione.wsods.type.Code;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Iterator;

import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Dispatch;
import javax.xml.ws.soap.SOAPBinding;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * @author cciurli
 *
 */
public final class SOAPTool {
	
	/** 
	 * costruttore di default.
	 * Privato per impedire l'instanzazione della classe che deve contenere
	 * solo metodi statici.
	 */
	private SOAPTool() {
	}
	
	/** logger. */
	private static final Logger LOG = LoggerFactory.getLogger(JAXBTool.class);
	
    /**
     * Trasforma un SOAPMessage in stringa.
     * @param soap	SoapMessage da convertirsi in stringa.
     * @return		Stringa rappresentatnte il SOAP peredo in ingresto
     */
    public static String soapToString(final SOAPMessage soap) {
		return new String(soapToByteArray(soap));
    }
    /**
     * Trasforma un SOAPMessage in stringa.
     * @param soap	SoapMessage da convertirsi in stringa.
     * @return		byte[] rappresentatnte il SOAP peredo in ingresto
     */
    public static byte[] soapToByteArray(final SOAPMessage soap) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			soap.writeTo(baos);
			return baos.toByteArray();
			
		} catch (SOAPException e) {
			LOG.warn("problemi nello stampare il messaggio soap.");
		} catch (IOException e) {
			LOG.warn("problemi nello stampare il messaggio soap.");
		} finally {
			try {
				baos.close();
			} catch (IOException e) {
				LOG.warn("problemi nel chiudere l'outputStream impiegato stampare il messaggio soap.");
			}
		}
		return new byte[0];
    }
    
    
    
    /**
     * Estrae il primo SOAPBodyElement dal Body di un messaggio SOAP.
     * 
     * @param message				Messaggio SOAP da cui estrarre il body.
     * @return						Body estratto.
     * @throws WsOdsRuntimeException	sollevata quando si trovano problemi nell'estrazioen del body dal SOAPMessage.
     */
    public static Node extractNode(final SOAPMessage message) throws WsOdsRuntimeException {
    	long startTime = System.currentTimeMillis();
    	try {
			if (message == null) {
				return null;
			}
			Node node = null;
			boolean found = false;
			SOAPBody soapBody = null;
			try {
				soapBody = message.getSOAPBody();
			} catch (SOAPException e) {
				String msg = "Impossibile estrarre il body dal SOAPMessage";
				LOG.warn(msg);
				throw new WsOdsRuntimeException(Code.GENERICO, msg, e);
			}
	
			if (soapBody == null) {
				return null;
			}
			Iterator< ? > iterator = soapBody.getChildElements();
			while (iterator.hasNext() && !found) {
				node = (Node) iterator.next();
				if (node instanceof javax.xml.soap.SOAPBodyElement) {
					found = true;
				}
			}
			if (iterator.hasNext() && found) {
				LOG.warn("Ho trovato il primo elemento del SoapBody, ma sono presenti altri.");
			}
	
			if (found) {
				return node;
			} else {
				return null;
			}
    	} finally {
    		LOG.debug("[PERFORMANCE] - caricato il Dispatch in {}. ms ", (startTime-System.currentTimeMillis()));
    	}
	}
    
    /**
     * Estrae un elemento del header soap.
     * @param soap		soap da cui estrarre
     * @param qName		QName del elemento da estrarre
     * @return			Nodo rapprentante l'elemento estratto
     * @throws WsOdsRuntimeException sollevata quando si trovano problemi nell'estrazioen del header dal SOAPHeader.
     */
    public static Node extractHeaderElement(final SOAPMessage soap, final QName qName) throws WsOdsRuntimeException {
		Node element = null;
		SOAPHeader header = null;
		try {
			header = soap.getSOAPHeader();
		} catch (SOAPException e) {
			String msg = "Impossibile estrarre l'header dal SOAPMessage";
			LOG.warn(msg);
			throw new WsOdsRuntimeException(Code.GENERICO,msg, e);
		}
        if (header != null) {
            Iterator< ? > it = header.getChildElements(qName);
            if (it.hasNext()) {
                element = (Node) it.next();
            }
        }
        return element;
	}
    
    /**
     * 
     * Crea un Messaggio SOAP vuoto tramite il dispatch passatoli.
     * 
     * @param dispatch				dispatch da cui creare il soap vuoto.
     * @return						SOAP Message vuoto.
     * @throws WsOdsRuntimeException	in caso di problemi durante la creazione del soap. 
     */
    public static SOAPMessage newEmptySOAPMessage(final Dispatch<SOAPMessage> dispatch) throws WsOdsRuntimeException {
		BindingProvider bp = (BindingProvider) dispatch;
		SOAPBinding sb = (SOAPBinding) bp.getBinding();
		MessageFactory factory = sb.getMessageFactory();
		try {
			return  factory.createMessage();
		} catch (SOAPException e) {
			String msg = "problemi durante la generazione del SOAPMessage vuoto";
			LOG.warn(msg);
			throw new WsOdsRuntimeException(Code.GENERICO,msg, e);
		}
	}
    /**
     * 
     * Crea un Messaggio SOAP vuoto.
     * 
     * @return						SOAP Message vuoto.
     * @throws WsOdsRuntimeException	in caso di problemi durante la creazione del soap. 
     */
    public static SOAPMessage newEmptySOAPMessage() throws WsOdsRuntimeException {
    	SOAPMessage soap = null;
    	MessageFactory messageFactory;
		try {
			messageFactory = MessageFactory.newInstance();
			soap = messageFactory.createMessage();
		} catch (SOAPException e) {
			String msg = "problemi durante la generazione del SOAPMessage vuoto";
			LOG.warn(msg);
			throw new WsOdsRuntimeException(Code.GENERICO,msg, e);
		}
    	
    	return soap;
    }
    /**
     * 
     * Crea un SOAPFault vuoto.
     * 
     * @return						SOAPFault vuoto.
     * @throws WsOdsRuntimeException	in caso di problemi durante la creazione del SOAPFault. 
     */
    public static SOAPFault newSOAPFault() throws WsOdsRuntimeException {
    	SOAPFault fault = null;
    	SOAPFactory  soapFactory;
		try {
			soapFactory = SOAPFactory.newInstance();
			fault = soapFactory.createFault();
		} catch (SOAPException e) {
			String msg = "problemi durante la generazione del SOAPFault vuoto";
			LOG.warn(msg);
			throw new WsOdsRuntimeException(Code.GENERICO,msg, e);
		}
    	
    	return fault;
    }
    
    public static SOAPElement toSOAPElement( final Element element) throws WsOdsRuntimeException {
    	try{
	    	final SOAPFactory soapFactory = SOAPFactory.newInstance();
	    	final SOAPElement soapElement = soapFactory.createElement(element);
	    	return soapElement;
    	} catch (SOAPException e){
    		String msg = "problemi durante la conversione da Element a SoapElement";
			LOG.warn(msg);
			throw new WsOdsRuntimeException(Code.GENERICO,msg, e);
    	}
    	
    }
    
    /**
     * Aggiunge un document come body del SOAPMessage passatoli.
     * @param soap					SOAP Message a cui aggiungere il document
     * @param element				element da aggiungere
     * @throws WsOdsRuntimeException	in caso di problemi durante l'inserzione del document nel soap. 
     */
    public static void addBody(final SOAPMessage soap, final Element element) throws WsOdsRuntimeException {
    	try {
	    	SOAPBody body = soap.getSOAPBody();
	    	SOAPElement soapElement = toSOAPElement(element);
			body.addChildElement(soapElement);
	    } catch (SOAPException e) {
			String msg = "problemi durante la'aggiunta del document al body";
			LOG.warn(msg);
			throw new WsOdsRuntimeException(Code.GENERICO,msg, e);
		}
    }
    
    public static SOAPFault extractFault(final SOAPMessage soap)  {
    	SOAPBody body = null;
    	try {
			body = soap.getSOAPBody();
		} catch (SOAPException e1) {
			LOG.warn("impossibile estrarre il body");
		}
    	if(body!=null && body.hasFault()){
			return body.getFault();
		}
    	return null;
    }
 
}

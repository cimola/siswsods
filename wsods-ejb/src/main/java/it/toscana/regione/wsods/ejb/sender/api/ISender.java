/** */
package it.toscana.regione.wsods.ejb.sender.api;

import javax.ejb.Local;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.xml.soap.SOAPMessage;


/**
 * @author cciurli
 *
 */
@Local
public interface ISender {
	

	final static String EJB_REF = "Sender";
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	SOAPMessage send(final long idTrake, final SOAPMessage request);

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	SOAPMessage getEmptySoap();
	
	
	boolean isTrackerEnabled();

	String getSrc();

	String getDest();
}

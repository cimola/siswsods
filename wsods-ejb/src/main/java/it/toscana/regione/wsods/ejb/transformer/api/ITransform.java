/** */
package it.toscana.regione.wsods.ejb.transformer.api;

import javax.ejb.Local;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.xml.bind.JAXBElement;
import javax.xml.soap.SOAPElement;

import org.w3c.dom.Document;
import org.w3c.dom.Node;


/**
 * @author cciurli
 *
 */
@Local
public interface ITransform <MT,UT> {

	final static String EJB_REF = "Transform";

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	JAXBElement<UT> transform(final Node node);

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	SOAPElement transform(final JAXBElement<MT> jaxb);

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	Document transform(MT jaxb);
		
}

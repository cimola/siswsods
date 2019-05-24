/** */
package it.toscana.regione.wsods.ejb.upload.api;

import it.toscana.regione.wsods.exception.UploadException;

import javax.ejb.Local;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPMessage;


/**
 * @author cciurli
 *
 */
@Local
public interface IManagerUpload {

	final static String EJB_REF = "ManagerUpload";
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	void upload(final SOAPMessage request, final SOAPBody bodyResponse) throws UploadException;
}

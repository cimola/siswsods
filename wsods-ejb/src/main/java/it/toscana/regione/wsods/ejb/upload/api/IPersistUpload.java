/** */
package it.toscana.regione.wsods.ejb.upload.api;

import it.toscana.regione.autocertificazionireddito.entity.jaxb.rt.UploadAps2WsOdsRequest;
import it.toscana.regione.wsods.exception.UploadException;

import javax.ejb.Local;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;


/**
 * @author cciurli
 *
 */
@Local
public interface IPersistUpload {

	
	final static String EJB_REF = "PersistUpload";

	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	void store(final UploadAps2WsOdsRequest value) throws UploadException ;
	
}

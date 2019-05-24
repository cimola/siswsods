/** */
package it.toscana.regione.wsods.ejb.upload.api;

import it.toscana.regione.autocertificazionireddito.entity.jaxb.rt.UploadAps2WsOdsRequest;
import it.toscana.regione.autocertificazionireddito.entity.jaxb.rt.UploadAps2WsOdsResponse;
import it.toscana.regione.wsods.ejb.transformer.api.ITransform;

import javax.ejb.Local;


/**
 * @author cciurli
 *
 */
@Local
public interface ITransformUpload extends ITransform < UploadAps2WsOdsResponse, UploadAps2WsOdsRequest>{

	final static String EJB_REF = "TransformUpload";

//	@TransactionAttribute(TransactionAttributeType.MANDATORY)
//	JAXBElement<UploadAps2WsOdsRequest> transform(final Node node);
//
//	@TransactionAttribute(TransactionAttributeType.MANDATORY)
//	SOAPElement transform(final JAXBElement<UploadAps2WsOdsResponse> jaxb);
		
}

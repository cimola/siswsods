/** */
package it.toscana.regione.wsods.ejb.getesenzioni.api;

import it.toscana.regione.autocertificazionireddito.entity.jaxb.rt.GetEsenzioniAps2WsOdsRequest;
import it.toscana.regione.autocertificazionireddito.entity.jaxb.rt.GetEsenzioniAps2WsOdsResponse;
import it.toscana.regione.wsods.ejb.transformer.api.ITransform;

import javax.ejb.Local;


/**
 * @author cciurli
 *
 */
@Local
public interface ITransformerGetEsenzioni extends ITransform<GetEsenzioniAps2WsOdsResponse, GetEsenzioniAps2WsOdsRequest> {

	final static String EJB_REF = "TransformGetEsenzioni";
}

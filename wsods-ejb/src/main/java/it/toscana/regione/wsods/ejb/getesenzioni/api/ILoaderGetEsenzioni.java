/** */
package it.toscana.regione.wsods.ejb.getesenzioni.api;

import it.toscana.regione.autocertificazionireddito.entity.jaxb.rt.GetEsenzioniAps2WsOdsRequest;
import it.toscana.regione.autocertificazionireddito.entity.jaxb.rt.GetEsenzioniAps2WsOdsResponse;
import it.toscana.regione.wsods.exception.GetEsenzioniException;

import javax.ejb.Local;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.xml.bind.JAXBElement;


/**
 * @author cciurli
 *
 */
@Local
public interface ILoaderGetEsenzioni {

	final static String EJB_REF = "LoaderGetEsenzioni";

	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	JAXBElement<GetEsenzioniAps2WsOdsResponse> load(final JAXBElement<GetEsenzioniAps2WsOdsRequest> req) throws GetEsenzioniException;
}

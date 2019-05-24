/** */
package it.toscana.regione.wsods.ejb.getlista.api;

import it.toscana.regione.autocertificazionireddito.entity.jaxb.rt.GetListaAps2WsOdsRequest;
import it.toscana.regione.autocertificazionireddito.entity.jaxb.rt.GetListaAps2WsOdsResponse;
import it.toscana.regione.wsods.exception.GetListaException;

import javax.ejb.Local;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.xml.bind.JAXBElement;


/**
 * @author cciurli
 *
 */
@Local
public interface ILoaderGetLista {

	final static String EJB_REF = "LoaderGetLista";

	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	JAXBElement<GetListaAps2WsOdsResponse> load(final JAXBElement<GetListaAps2WsOdsRequest> req) throws GetListaException;
}

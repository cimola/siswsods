/** */
package it.toscana.regione.wsods.ejb.getlista.api;

import javax.ejb.Local;

import it.toscana.regione.autocertificazionireddito.entity.jaxb.rt.GetListaAps2WsOdsRequest;
import it.toscana.regione.autocertificazionireddito.entity.jaxb.rt.GetListaAps2WsOdsResponse;
import it.toscana.regione.wsods.ejb.transformer.api.ITransform;


/**
 * @author cciurli
 *
 */
@Local
public interface ITransformerGetLista extends ITransform<GetListaAps2WsOdsResponse, GetListaAps2WsOdsRequest> {

	final static String EJB_REF = "TransformGetLista";
}

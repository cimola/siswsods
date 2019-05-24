/** */
package it.toscana.regione.wsods.ejb.getlista.impl;

import it.toscana.regione.autocertificazionireddito.entity.jaxb.rt.GetListaAps2WsOdsRequest;
import it.toscana.regione.autocertificazionireddito.entity.jaxb.rt.GetListaAps2WsOdsResponse;
import it.toscana.regione.wsods.ejb.getlista.api.ITransformerGetLista;
import it.toscana.regione.wsods.ejb.transformer.abs.Transform;

import javax.ejb.Stateless;


/**
 * @author cciurli
 *
 */
@Stateless(name = ITransformerGetLista.EJB_REF)
public class TransformerGetList extends Transform<GetListaAps2WsOdsResponse, GetListaAps2WsOdsRequest> implements ITransformerGetLista {

	/** {@inheritDoc} */
	@Override
	protected String getEjbRef() {
		return  ITransformerGetLista.EJB_REF;
	}

	/** {@inheritDoc} */
	@Override
	protected String getXsdMarshaller() {
		return "/META-INF/xsd/AutocertificazioniReddito.xsd";
	}

	/** {@inheritDoc} */
	@Override
	protected String getXsdUnmarshaller() {
		return "/META-INF/xsd/AutocertificazioniReddito.xsd";
	}

	/** {@inheritDoc} */
	@Override
	protected Class<GetListaAps2WsOdsResponse> getClassTypeMarshaller() {
		return GetListaAps2WsOdsResponse.class;
	}

	/** {@inheritDoc} */
	@Override
	protected Class<GetListaAps2WsOdsRequest> getClassTypeUnmarshaller() {
		return GetListaAps2WsOdsRequest.class;
	}
	
}

/** */
package it.toscana.regione.wsods.ejb.getesenzioni.impl;

import it.toscana.regione.autocertificazionireddito.entity.jaxb.rt.GetEsenzioniAps2WsOdsRequest;
import it.toscana.regione.autocertificazionireddito.entity.jaxb.rt.GetEsenzioniAps2WsOdsResponse;
import it.toscana.regione.wsods.ejb.getesenzioni.api.ITransformerGetEsenzioni;

import it.toscana.regione.wsods.ejb.transformer.abs.Transform;

import javax.ejb.Stateless;


/**
 * @author cciurli
 *
 */
@Stateless(name = ITransformerGetEsenzioni.EJB_REF)
public class TransformerGetEsenzioni extends Transform<GetEsenzioniAps2WsOdsResponse, GetEsenzioniAps2WsOdsRequest> implements ITransformerGetEsenzioni {

	/** {@inheritDoc} */
	@Override
	protected String getEjbRef() { return  ITransformerGetEsenzioni.EJB_REF; }

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
	protected Class<GetEsenzioniAps2WsOdsResponse> getClassTypeMarshaller() {
		return GetEsenzioniAps2WsOdsResponse.class;
	}

	/** {@inheritDoc} */
	@Override
	protected Class<GetEsenzioniAps2WsOdsRequest> getClassTypeUnmarshaller() {
		return GetEsenzioniAps2WsOdsRequest.class;
	}
	
}

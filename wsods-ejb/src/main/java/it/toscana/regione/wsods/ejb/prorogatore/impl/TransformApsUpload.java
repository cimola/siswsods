package it.toscana.regione.wsods.ejb.prorogatore.impl;


import it.toscana.regione.autocertificazionireddito.entity.jaxb.rt.UploadWebApp2ApsRequest;
import it.toscana.regione.autocertificazionireddito.entity.jaxb.rt.UploadWebApp2ApsResponse;
import it.toscana.regione.wsods.ejb.prorogatore.api.ITransformApsUpload;
import it.toscana.regione.wsods.ejb.transformer.abs.Transform;
import it.toscana.regione.wsods.ejb.transformer.api.ITransform;

import javax.ejb.Stateless;


/**
 * @author cciurli
 *
 */
@Stateless(name = ITransformApsUpload.EJB_REF)
public class TransformApsUpload extends Transform< UploadWebApp2ApsRequest,UploadWebApp2ApsResponse> implements ITransformApsUpload,ITransform< UploadWebApp2ApsRequest,UploadWebApp2ApsResponse> {

	/** {@inheritDoc} */
	@Override
	protected String getEjbRef() {
		return ITransformApsUpload.EJB_REF;
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
	protected Class<UploadWebApp2ApsResponse> getClassTypeUnmarshaller() {
		return UploadWebApp2ApsResponse.class;
	}

	/** {@inheritDoc} */
	@Override
	protected Class<UploadWebApp2ApsRequest> getClassTypeMarshaller() {
		return UploadWebApp2ApsRequest.class;
	}
	

	
}

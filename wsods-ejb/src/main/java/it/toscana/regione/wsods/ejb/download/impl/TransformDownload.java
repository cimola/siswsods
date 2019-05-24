/** */
package it.toscana.regione.wsods.ejb.download.impl;

import javax.ejb.Stateless;

import it.toscana.regione.autocertificazionireddito.entity.jaxb.sogei.download.request.DownloadAutocertificazione;
import it.toscana.regione.autocertificazionireddito.entity.jaxb.sogei.download.response.Ricevuta;
import it.toscana.regione.wsods.ejb.download.api.ITransformDownload;
import it.toscana.regione.wsods.ejb.transformer.abs.Transform;


/**
 * @author cciurli
 *
 */
@Stateless(name = ITransformDownload.EJB_REF)
public class TransformDownload extends Transform< DownloadAutocertificazione, Ricevuta> implements ITransformDownload{
	
	/**
	 * 
	 */
	public TransformDownload() {super();}

	/** {@inheritDoc} */
	@Override
	protected String getEjbRef() {
		return ITransformDownload.EJB_REF;
	}

	/** {@inheritDoc} */
	@Override
	protected String getXsdMarshaller() {
		return "/META-INF/xsd/download/DownloadEseRedRequest.xsd";
	}

	/** {@inheritDoc} */
	@Override
	protected String getXsdUnmarshaller() {
		return "/META-INF/xsd/download/DownloadEseRedResponse.xsd";
	}

	/** {@inheritDoc} */
	@Override
	protected Class<DownloadAutocertificazione> getClassTypeMarshaller() {
		return DownloadAutocertificazione.class;
	}

	/** {@inheritDoc} */
	@Override
	protected Class<Ricevuta> getClassTypeUnmarshaller() {
		return Ricevuta.class;
	}
	
}

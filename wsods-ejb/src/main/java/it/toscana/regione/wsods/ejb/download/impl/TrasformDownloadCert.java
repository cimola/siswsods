package it.toscana.regione.wsods.ejb.download.impl;

import it.toscana.regione.certificazionireddito.entity.jaxb.sogei.download.request.DownloadCertificazione;
import it.toscana.regione.certificazionireddito.entity.jaxb.sogei.download.response.Ricevuta;
import it.toscana.regione.wsods.ejb.download.api.ITransformDownloadCert;
import it.toscana.regione.wsods.ejb.transformer.abs.Transform;

import javax.ejb.Stateless;


/**
 * @author vmaltese
 *
 */
@Stateless(name = ITransformDownloadCert.EJB_REF)
public class TrasformDownloadCert extends Transform< DownloadCertificazione, Ricevuta> implements ITransformDownloadCert{
	
	/**
	 * 
	 */
	public TrasformDownloadCert() {super();}

	/** {@inheritDoc} */
	@Override
	protected String getEjbRef() {
		return ITransformDownloadCert.EJB_REF;
	}

	/** {@inheritDoc} */
	@Override
	protected String getXsdMarshaller() {
		return "/META-INF/xsd/download/DownloadCertEseRedRequest.xsd";
	}

	/** {@inheritDoc} */
	@Override
	protected String getXsdUnmarshaller() {
		return "/META-INF/xsd/download/DownloadCertEseRedResponse.xsd";
	}

	/** {@inheritDoc} */
	@Override
	protected Class<DownloadCertificazione> getClassTypeMarshaller() {
		return DownloadCertificazione.class;
	}

	/** {@inheritDoc} */
	@Override
	protected Class<Ricevuta> getClassTypeUnmarshaller() {
		return Ricevuta.class;
	}
}
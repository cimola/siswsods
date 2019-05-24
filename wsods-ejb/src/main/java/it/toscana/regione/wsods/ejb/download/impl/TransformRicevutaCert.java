/** */
package it.toscana.regione.wsods.ejb.download.impl;

import it.toscana.regione.certificazionireddito.entity.jaxb.sogei.download.response.Ricevuta;
import it.toscana.regione.wsods.ejb.download.api.ITransformRicevutaCert;
import it.toscana.regione.wsods.ejb.transformer.abs.Transform;

import javax.ejb.Stateless;


/**
 * @author vmaltese
 *
 */       
@Stateless(name = ITransformRicevutaCert.EJB_REF)
public class TransformRicevutaCert extends Transform< Ricevuta, Ricevuta> implements ITransformRicevutaCert {
	
	public TransformRicevutaCert() {super();}

	/** {@inheritDoc} */
	@Override
	protected String getEjbRef() {
		return ITransformRicevutaCert.EJB_REF;
	}

	/** {@inheritDoc} */
	@Override
	protected String getXsdMarshaller() {
		return "/META-INF/xsd/download/DownloadCertEseRedResponse.xsd";
	}

	/** {@inheritDoc} */
	@Override
	protected String getXsdUnmarshaller() {
		return "/META-INF/xsd/download/DownloadCertEseRedResponse.xsd";
	}

	/** {@inheritDoc} */
	@Override
	protected Class<Ricevuta> getClassTypeMarshaller() {
		return Ricevuta.class;
	}

	/** {@inheritDoc} */
	@Override
	protected Class<Ricevuta> getClassTypeUnmarshaller() {
		return Ricevuta.class;
	}
}

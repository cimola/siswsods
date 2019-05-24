/** */
package it.toscana.regione.wsods.ejb.download.impl;

import it.toscana.regione.autocertificazionireddito.entity.jaxb.sogei.download.response.Ricevuta;
import it.toscana.regione.wsods.ejb.download.api.ITransformRicevuta;
import it.toscana.regione.wsods.ejb.transformer.abs.Transform;

import javax.ejb.Stateless;


/**
 * @author cciurli
 *
 */
@Stateless(name = ITransformRicevuta.EJB_REF)
public class TransformRicevuta extends Transform< Ricevuta, Ricevuta> implements ITransformRicevuta {
	
	/**
	 * 
	 */
	public TransformRicevuta() {super();}

	/** {@inheritDoc} */
	@Override
	protected String getEjbRef() {
		return ITransformRicevuta.EJB_REF;
	}

	/** {@inheritDoc} */
	@Override
	protected String getXsdMarshaller() {
		return "/META-INF/xsd/download/DownloadEseRedResponse.xsd";
	}

	/** {@inheritDoc} */
	@Override
	protected String getXsdUnmarshaller() {
		return "/META-INF/xsd/download/DownloadEseRedResponse.xsd";
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

/** */
package it.toscana.regione.wsods.ejb.download.api;

import it.toscana.regione.certificazionireddito.entity.jaxb.sogei.download.response.Ricevuta;
import it.toscana.regione.wsods.ejb.transformer.api.ITransform;

import javax.ejb.Local;


/**
 * @author vmaltese
 *
 */
@Local
public interface ITransformRicevutaCert extends ITransform < Ricevuta, Ricevuta >{

	final static String EJB_REF = "ITransformRicevutaCert";
		
}

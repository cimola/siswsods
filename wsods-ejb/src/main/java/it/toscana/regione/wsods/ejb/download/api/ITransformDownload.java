/** */
package it.toscana.regione.wsods.ejb.download.api;

import it.toscana.regione.autocertificazionireddito.entity.jaxb.sogei.download.request.DownloadAutocertificazione;
import it.toscana.regione.autocertificazionireddito.entity.jaxb.sogei.download.response.Ricevuta;
import it.toscana.regione.wsods.ejb.transformer.api.ITransform;

import javax.ejb.Local;


/**
 * @author cciurli
 *
 */
@Local
public interface ITransformDownload extends ITransform < DownloadAutocertificazione, Ricevuta>{

	final static String EJB_REF = "TransformDownload";	
}

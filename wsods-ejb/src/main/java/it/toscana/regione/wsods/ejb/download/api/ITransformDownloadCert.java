package it.toscana.regione.wsods.ejb.download.api;

import javax.ejb.Local;

import it.toscana.regione.certificazionireddito.entity.jaxb.sogei.download.request.DownloadCertificazione;
import it.toscana.regione.certificazionireddito.entity.jaxb.sogei.download.response.Ricevuta;
import it.toscana.regione.wsods.ejb.transformer.api.ITransform;

/**
 * @author vmaltese
 *
 */
@Local
public interface ITransformDownloadCert extends ITransform < DownloadCertificazione, Ricevuta>{

	final static String EJB_REF = "TrasformDownloadCert";
}

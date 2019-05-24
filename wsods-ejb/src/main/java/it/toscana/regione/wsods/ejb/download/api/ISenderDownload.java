/** */
package it.toscana.regione.wsods.ejb.download.api;

import javax.ejb.Local;

import it.toscana.regione.wsods.ejb.sender.api.ISender;


/**
 * @author cciurli
 *
 */
@Local
public interface ISenderDownload  extends ISender {
	
	final static String EJB_REF = "SenderDownload";
	
	final static String EJB_CERT_REF = "SenderDownloadCert";
	
}

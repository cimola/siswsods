/** */
package it.toscana.regione.wsods.ejb.svecchiatore.api;

import javax.ejb.Local;


/**
 * @author cciurli
 *
 */
@Local
public interface IRicevuteDownloadInserimentiDownloadSvecchiatore extends IRicevuteDownloadSvecchiatore {

	final static String EJB_REF = "icevuteDownloadInserimentiDownloadSvecchiatore";
}

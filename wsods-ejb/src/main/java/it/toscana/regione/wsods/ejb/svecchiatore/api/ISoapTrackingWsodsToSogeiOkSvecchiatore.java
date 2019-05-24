/** */
package it.toscana.regione.wsods.ejb.svecchiatore.api;

import it.toscana.regione.wsods.entity.jpa.api.IWsodsSoapTracking;

import javax.ejb.Local;


/**
 * @author cciurli
 *
 */
@Local
public interface ISoapTrackingWsodsToSogeiOkSvecchiatore extends ISvecchiatore<IWsodsSoapTracking> {

	final static String EJB_REF = "soapTrackingWsodsToSogeiOkSvecchiatore";
}

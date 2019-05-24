/** */
package it.toscana.regione.wsods.ejb.svecchiatore.impl;

import it.toscana.regione.wsods.ejb.svecchiatore.abs.SoapTrackingWsodsToSogeiSvecchiatore;
import it.toscana.regione.wsods.ejb.svecchiatore.api.ISoapTrackingWsodsToSogeiOkSvecchiatore;

import javax.ejb.Stateless;


/**
 * @author cciurli
 *
 */
@Stateless(name = ISoapTrackingWsodsToSogeiOkSvecchiatore.EJB_REF)
public class SoapTrackingWsodsToSogeiOkSvecchiatore extends SoapTrackingWsodsToSogeiSvecchiatore implements ISoapTrackingWsodsToSogeiOkSvecchiatore {
	

	/** fild serialVersionUID {@like long}*/
	private static final long	serialVersionUID	= -3534666484546667333L;
	/**
	 * 
	 */
	public SoapTrackingWsodsToSogeiOkSvecchiatore() { super(); }
	
	
	/** {@inheritDoc} */
	@Override
	protected String getOtherWhere() {
		return getBaseWhere()+" AND t.esito = 'OK' ";
	}
	
}

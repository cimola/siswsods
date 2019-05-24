/** */
package it.toscana.regione.wsods.ejb.svecchiatore.impl;

import it.toscana.regione.wsods.ejb.svecchiatore.abs.SoapTrackingWsodsToSogeiSvecchiatore;
import it.toscana.regione.wsods.ejb.svecchiatore.api.ISoapTrackingWsodsToSogeiKoSvecchiatore;

import javax.ejb.Stateless;


/**
 * @author cciurli
 *
 */
@Stateless(name = ISoapTrackingWsodsToSogeiKoSvecchiatore.EJB_REF)
public class SoapTrackingWsodsToSogeiKoSvecchiatore extends SoapTrackingWsodsToSogeiSvecchiatore implements ISoapTrackingWsodsToSogeiKoSvecchiatore {
	

	/** fild serialVersionUID {@like long}*/
	private static final long	serialVersionUID	= -3534666484546667333L;
	/**
	 * 
	 */
	public SoapTrackingWsodsToSogeiKoSvecchiatore() { super(); }
	
	
	/** {@inheritDoc} */
	@Override
	protected String getOtherWhere() {
		return getBaseWhere()+" AND t.esito = 'KO' ";
	}
	
}

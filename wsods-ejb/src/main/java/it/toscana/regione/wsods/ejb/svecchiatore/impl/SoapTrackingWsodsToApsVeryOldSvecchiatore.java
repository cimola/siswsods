package it.toscana.regione.wsods.ejb.svecchiatore.impl;

import javax.ejb.Stateless;

import it.toscana.regione.wsods.costanti.Varie;
import it.toscana.regione.wsods.ejb.svecchiatore.abs.SoapTrackingSvecchiatore;
import it.toscana.regione.wsods.ejb.svecchiatore.api.ISoapTrackingWsodsToApsVeryOldSvecchiatore;
import it.toscana.regione.wsods.entity.jpa.api.IWsodsSoapTracking;
import it.toscana.regione.wsods.singleton.Conf;

@Stateless(name = ISoapTrackingWsodsToApsVeryOldSvecchiatore.EJB_REF)
public class SoapTrackingWsodsToApsVeryOldSvecchiatore extends SoapTrackingSvecchiatore implements ISoapTrackingWsodsToApsVeryOldSvecchiatore{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9405923084698509L;


	public SoapTrackingWsodsToApsVeryOldSvecchiatore() { super(); }
	
	
	@Override
	protected int recordMinimiMantenuti() { return Conf.getProrogheSvecchiatoreRecorMinimiMantenuti(); }
	
	
	@Override
	protected long deltaSvecchiatura() { return Conf.getProrogheDeltaSvecchiatore()+(Varie.TIME_GIORNO*730L); }
	
	
	@Override
	protected String getOtherWhere() {
		return " AND t.src = '"+IWsodsSoapTracking.SRC_WSODS+"' AND t.dest = '"+IWsodsSoapTracking.DEST_APS_ESE+"' ";
	}
	
	
}

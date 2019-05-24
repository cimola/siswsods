package it.toscana.regione.wsods.ejb.svecchiatore.impl;

import javax.ejb.Stateless;

import it.toscana.regione.wsods.ejb.svecchiatore.abs.SoapTrackingSvecchiatore;
import it.toscana.regione.wsods.ejb.svecchiatore.api.ISoapTrackingWsodsToApsKoSvecchiatore;
import it.toscana.regione.wsods.entity.jpa.api.IWsodsSoapTracking;
import it.toscana.regione.wsods.singleton.Conf;

@Stateless(name = ISoapTrackingWsodsToApsKoSvecchiatore.EJB_REF)
public class SoapTrackingWsodsToApsKoSvecchiatore extends SoapTrackingSvecchiatore implements ISoapTrackingWsodsToApsKoSvecchiatore{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9405923084698509L;


	public SoapTrackingWsodsToApsKoSvecchiatore() { super(); }
	
	
	@Override
	protected int recordMinimiMantenuti() { return Conf.getProrogheSvecchiatoreRecorMinimiMantenuti(); }
	
	
	@Override
	protected long deltaSvecchiatura() { return Conf.getProrogheDeltaSvecchiatore(); }
	
	
	@Override
	protected String getOtherWhere() {
		return " AND t.src = '"+IWsodsSoapTracking.SRC_WSODS+"' AND t.dest = '"+IWsodsSoapTracking.DEST_APS_ESE+"' AND t.esito = 'KO' AND t.response is null ";
	}
	
	
}

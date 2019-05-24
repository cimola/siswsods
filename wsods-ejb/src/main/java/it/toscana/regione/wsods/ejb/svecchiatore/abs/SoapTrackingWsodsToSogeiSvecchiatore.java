package it.toscana.regione.wsods.ejb.svecchiatore.abs;

import it.toscana.regione.wsods.entity.jpa.api.IWsodsSoapTracking;
import it.toscana.regione.wsods.singleton.Conf;

public abstract class SoapTrackingWsodsToSogeiSvecchiatore extends SoapTrackingSvecchiatore {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 42352634899L;

	public SoapTrackingWsodsToSogeiSvecchiatore() {
		super();
	}

	protected String getBaseWhere() {
		return " AND t.src = '"+IWsodsSoapTracking.SRC_WSODS+"' AND t.dest = '"+IWsodsSoapTracking.DEST_SOGEI+"' ";
	}

	protected int recordMinimiMantenuti(){ return Conf.getSvecchiatoreRecordMinimiMantenuti(); }
	protected long deltaSvecchiatura(){ return Conf.getDeltaSvecchiatura(); }
	
}

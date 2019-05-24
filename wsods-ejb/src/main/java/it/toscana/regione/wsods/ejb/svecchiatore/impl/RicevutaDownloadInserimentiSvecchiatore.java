/** */
package it.toscana.regione.wsods.ejb.svecchiatore.impl;

import it.toscana.regione.wsods.ejb.svecchiatore.abs.RicevutaDownloadSogeiSvecchiatore;
import it.toscana.regione.wsods.ejb.svecchiatore.api.IRicevuteDownloadInserimentiDownloadSvecchiatore;
import it.toscana.regione.wsods.type.Code;

import javax.ejb.Stateless;


/**
 * @author cciurli
 *
 */
@Stateless(name = IRicevuteDownloadInserimentiDownloadSvecchiatore.EJB_REF)
public class RicevutaDownloadInserimentiSvecchiatore extends RicevutaDownloadSogeiSvecchiatore implements IRicevuteDownloadInserimentiDownloadSvecchiatore {
	

	/** fild serialVersionUID {@like long}*/
	private static final long	serialVersionUID	= -3534666484546667333L;
	/**
	 * 
	 */
	public RicevutaDownloadInserimentiSvecchiatore() { super(); }
	
	
	/** {@inheritDoc} */
	@Override
	protected String getOtherWhere() {
		return getBaseWhere()+" AND t.esito = '"+Code.SOGEI_DOWNLOAD_ELABORAZIONE_CORRETTAMENTE_EFFETTUATA.code+"' AND t.tipoAutocertificazione = 'I' ";
	}
	
}

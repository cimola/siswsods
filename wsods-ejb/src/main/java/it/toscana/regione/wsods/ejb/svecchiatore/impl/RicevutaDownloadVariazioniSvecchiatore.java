/** */
package it.toscana.regione.wsods.ejb.svecchiatore.impl;

import javax.ejb.Stateless;

import it.toscana.regione.wsods.ejb.svecchiatore.abs.RicevutaDownloadSogeiSvecchiatore;
import it.toscana.regione.wsods.ejb.svecchiatore.api.IRicevuteDownloadVariazioniDownloadSvecchiatore;
import it.toscana.regione.wsods.type.Code;


/**
 * @author cciurli
 *
 */
@Stateless(name = IRicevuteDownloadVariazioniDownloadSvecchiatore.EJB_REF)
public class RicevutaDownloadVariazioniSvecchiatore extends RicevutaDownloadSogeiSvecchiatore implements IRicevuteDownloadVariazioniDownloadSvecchiatore {
	

	/** fild serialVersionUID {@like long}*/
	private static final long	serialVersionUID	= -3534666484546667444L;
	
	/**
	 * 
	 */
	public RicevutaDownloadVariazioniSvecchiatore() { super(); }
	
	
	/** {@inheritDoc} */
	@Override
	protected String getOtherWhere() {
		return getBaseWhere()+" AND t.esito = '"+String.valueOf(Code.SOGEI_DOWNLOAD_ELABORAZIONE_CORRETTAMENTE_EFFETTUATA.code)+"' AND t.tipoAutocertificazione = 'V' ";
	}

}

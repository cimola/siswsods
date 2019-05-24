/** */
package it.toscana.regione.wsods.ejb.svecchiatore.impl;

import it.toscana.regione.wsods.ejb.svecchiatore.abs.RicevutaDownloadSogeiSvecchiatore;
import it.toscana.regione.wsods.ejb.svecchiatore.api.RicevuteDownloadVariazioniAlMinutoDownloadSvecchiatore;
import it.toscana.regione.wsods.type.Code;

import javax.ejb.Stateless;


/**
 * @author cciurli
 *
 */
@Stateless(name = RicevuteDownloadVariazioniAlMinutoDownloadSvecchiatore.EJB_REF)
public class RicevutaDownloadVariazioniAlMinutoSvecchiatore extends RicevutaDownloadSogeiSvecchiatore implements RicevuteDownloadVariazioniAlMinutoDownloadSvecchiatore {
	

	private static final long serialVersionUID = 531462602406267805L;


	/**
	 * 
	 */
	public RicevutaDownloadVariazioniAlMinutoSvecchiatore() { super(); }
	
	
	/** {@inheritDoc} */
	@Override
	protected String getOtherWhere() {
		return getBaseWhere()+" AND t.esito = '"+String.valueOf(Code.SOGEI_DOWNLOAD_ELABORAZIONE_CORRETTAMENTE_EFFETTUATA.code)+"' AND t.tipoAutocertificazione = 'A' ";
	}

}

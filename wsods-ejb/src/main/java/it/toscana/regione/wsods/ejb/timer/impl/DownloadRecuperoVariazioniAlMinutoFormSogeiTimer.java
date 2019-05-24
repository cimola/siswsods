/** */
package it.toscana.regione.wsods.ejb.timer.impl;

import it.toscana.regione.wsods.ejb.timer.abs.DownloadFormSogeiTimer;
import it.toscana.regione.wsods.ejb.timer.api.ITimer;
import it.toscana.regione.wsods.entity.jpa.api.IRicevutaDownloadSogei;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

/**
 * @author cciurli
 *
 */
@Stateless(name = ITimer.EJB_REF_DOWNLOAD_RECUPERO_VARIAZIONI_AL_MINUTO_FROM_SOGEI)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class DownloadRecuperoVariazioniAlMinutoFormSogeiTimer  extends DownloadFormSogeiTimer implements ITimer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5181822997273478977L;


	/**
	 * 
	 */
	public DownloadRecuperoVariazioniAlMinutoFormSogeiTimer() {}


	@Override protected String tipoRichiesta() {return "A";}


	@Override protected String tipologiaRicevuto() {return IRicevutaDownloadSogei.TIPOLOGIA_RICEVUTA_RECUPERO;}


	
}

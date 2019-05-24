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
@Stateless(name = ITimer.EJB_REF_DOWNLOAD_RECUPERO_INSERIMENTI_FROM_SOGEI)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class DownloadRecuperoInserimentiFormSogeiTimer extends DownloadFormSogeiTimer implements ITimer {


	/** fild serialVersionUID {@like long}*/
	private static final long	serialVersionUID	= -3534666484546667161L;


	/**
	 * 
	 */
	public DownloadRecuperoInserimentiFormSogeiTimer() {}

	@Override protected String tipoRichiesta() {return "I";}


	@Override protected String tipologiaRicevuto() {return IRicevutaDownloadSogei.TIPOLOGIA_RICEVUTA_RECUPERO;}
	
	
}

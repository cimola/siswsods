/** */
package it.toscana.regione.wsods.ejb.timer.impl;

import it.toscana.regione.wsods.ejb.timer.abs.DownloadCertFormSogeiTimer;
import it.toscana.regione.wsods.ejb.timer.api.ITimer;
import it.toscana.regione.wsods.entity.jpa.api.IRecuperoDownloadSogei;
import it.toscana.regione.wsods.entity.jpa.api.IRicevutaDownloadSogei;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

/**
 * @author vmaltese
 *
 */
@Stateless(name = ITimer.EJB_CERT_REF_DOWNLOAD_RECUPERO_ATTESTAZIONI_FROM_SOGEI)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class DownloadRecuperoAttestazioniFormSogeiTimer extends DownloadCertFormSogeiTimer implements ITimer {

	/** fild serialVersionUID {@like long}*/
	private static final long serialVersionUID	= -3534666484546667161L;


	public DownloadRecuperoAttestazioniFormSogeiTimer() {}

	@Override protected String tipoOperazione() {return IRecuperoDownloadSogei.TIPO_CERTIFICAZIONE;}

	@Override protected String tipologiaRicevuto() {return IRicevutaDownloadSogei.TIPOLOGIA_RICEVUTA_RECUPERO;}
	
	
}

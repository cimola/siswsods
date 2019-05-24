/** */
package it.toscana.regione.wsods.ejb.timer.abs;

import it.toscana.regione.wsods.ejb.download.api.IManagerDownloader;
import it.toscana.regione.wsods.ejb.timer.api.ITimer;
import it.toscana.regione.wsods.ejb.timer.bean.InfoTimer;

import javax.ejb.EJB;
import javax.ejb.Timer;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

/**
 * @author vmaltese
 *
 */
@TransactionManagement(TransactionManagementType.CONTAINER)
public abstract class DownloadCertFormSogeiTimer extends AbstractTimer implements ITimer {

	/** field serialVersionUID {@like long}*/
	private static final long serialVersionUID = -2442048307460894130L;
	
	@EJB(beanName = IManagerDownloader.EJB_CERT_REF)
	private IManagerDownloader manager;

	public DownloadCertFormSogeiTimer() {}

	protected abstract String tipoOperazione();
	protected abstract String tipologiaRicevuto();
	
	@Override 
	protected void execute(final Timer timer, final InfoTimer infoTimer) {
		manager.download(tipoOperazione(),tipologiaRicevuto());
	}
}

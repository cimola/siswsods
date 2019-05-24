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
 * @author cciurli
 *
 */
@TransactionManagement(TransactionManagementType.CONTAINER)
public abstract class DownloadFormSogeiTimer extends AbstractTimer implements ITimer {

	/** fild serialVersionUID {@like long}*/
	private static final long serialVersionUID = -6664107484546667160L;

	@EJB(beanName = IManagerDownloader.EJB_REF)
	private IManagerDownloader	manager;

	/**
	 * 
	 */
	public DownloadFormSogeiTimer() {}

	protected abstract String tipoRichiesta();
	protected abstract String tipologiaRicevuto();
	
	@Override 
	protected void execute(final Timer timer, final InfoTimer infoTimer) {
		manager.download(tipoRichiesta(),tipologiaRicevuto());
	}


	
	
}

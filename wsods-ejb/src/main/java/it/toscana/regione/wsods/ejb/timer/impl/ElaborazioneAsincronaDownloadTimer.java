/** */
package it.toscana.regione.wsods.ejb.timer.impl;

import it.toscana.regione.wsods.ejb.download.api.IManagerDownloader;
import it.toscana.regione.wsods.ejb.timer.abs.AbstractTimer;
import it.toscana.regione.wsods.ejb.timer.api.ITimer;
import it.toscana.regione.wsods.ejb.timer.bean.InfoTimer;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.Timer;


/**
 * @author cciurli
 *
 */
@Stateless(name = ITimer.EJB_REF_ELABORATORE_ASINCORNO )
public class ElaborazioneAsincronaDownloadTimer extends AbstractTimer implements ITimer {
	

	private static final long serialVersionUID = 930850943890756L;

	
	@EJB(beanName = IManagerDownloader.EJB_REF)
	private IManagerDownloader managerDownloader;

	/**
	 * 
	 */
	public ElaborazioneAsincronaDownloadTimer() { super(); }
	
	
	/** {@inheritDoc} */
	@Override
	protected void execute(final Timer timer,final InfoTimer infoTimer ) {

		managerDownloader.elaboraAsincrono();

	}
}

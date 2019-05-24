/** */
package it.toscana.regione.wsods.ejb.timer.impl;

import it.toscana.regione.wsods.ejb.ricalcolatitolonull.api.IManagerRicalcolaTitoloNull;
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
@Stateless(name = ITimer.EJB_REF_RICALCOLA_TITOLO_NULL )
public class RicalcolaTitoloNullTimer extends AbstractTimer implements ITimer{
	
	/** fild serialVersionUID {@like long}*/
	private static final long serialVersionUID = -2715497913736917532L;
	
	@EJB(beanName = IManagerRicalcolaTitoloNull.EJB_REF)
	private IManagerRicalcolaTitoloNull managerRicalcolaTitoloNull;

	/**
	 * 
	 */
	public RicalcolaTitoloNullTimer() { super(); }
	
	
	/** {@inheritDoc} */
	@Override
	protected void execute(final Timer timer,final InfoTimer infoTimer ) {

		final long numeroTimer = infoTimer.getNumero();
		final long discriminante = infoTimer.getDiscriminante();
		
		managerRicalcolaTitoloNull.ricalcolaTitoloNull(numeroTimer,discriminante);
		
		
	}
	
	
}

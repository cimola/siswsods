/** */
package it.toscana.regione.wsods.ejb.timer.impl;

import it.toscana.regione.wsods.ejb.rielaboratore.api.IManagerRielaboratore;
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
@Stateless(name = ITimer.EJB_REF_RIELABORATORE )
public class RielaboratoreTimer extends AbstractTimer implements ITimer{
	
	
	/** fild serialVersionUID {@like long}*/
	private static final long serialVersionUID = 3534675687699787160L;
	
	@EJB(beanName = IManagerRielaboratore.EJB_REF)
	private IManagerRielaboratore managerRielaboratore;

	/**
	 * 
	 */
	public RielaboratoreTimer() { super(); }
	
	
	/** {@inheritDoc} */
	@Override
	protected void execute(final Timer timer,final InfoTimer infoTimer ) {

		final long numeroTimer = infoTimer.getNumero();
		final long discriminante = infoTimer.getDiscriminante();
		
		managerRielaboratore.rielaborarecord(numeroTimer,discriminante);
		
		
	}
	
	
}

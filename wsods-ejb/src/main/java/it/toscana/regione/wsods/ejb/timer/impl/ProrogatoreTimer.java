package it.toscana.regione.wsods.ejb.timer.impl;

import it.toscana.regione.wsods.ejb.prorogatore.api.IManagerProrogatore;
import it.toscana.regione.wsods.ejb.timer.abs.AbstractTimer;
import it.toscana.regione.wsods.ejb.timer.api.ITimer;
import it.toscana.regione.wsods.ejb.timer.bean.InfoTimer;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.Timer;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;


@Stateless(name = ITimer.EJB_REF_PROROGATORE)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ProrogatoreTimer extends AbstractTimer implements ITimer {
	
	
	@EJB(beanName= IManagerProrogatore.EJB_REF)
	private IManagerProrogatore prorogatore;
	
	private static final long serialVersionUID = 5554438430732874654L;

	public ProrogatoreTimer() { super(); }

	@Override
	protected void execute(Timer timer, InfoTimer  infoTimer) {

		final long numeroTimer = infoTimer.getNumero();
		final long discriminante = infoTimer.getDiscriminante();
		
		prorogatore.proroga(numeroTimer,discriminante);
		
	}
	
	
	
}

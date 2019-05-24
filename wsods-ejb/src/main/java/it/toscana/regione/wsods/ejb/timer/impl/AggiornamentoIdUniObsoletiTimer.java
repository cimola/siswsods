/** */
package it.toscana.regione.wsods.ejb.timer.impl;

import it.toscana.regione.wsods.ejb.iduniobsoleti.api.IManagerIdUniObsoleti;
import it.toscana.regione.wsods.ejb.timer.abs.AbstractTimer;
import it.toscana.regione.wsods.ejb.timer.api.ITimer;
import it.toscana.regione.wsods.ejb.timer.bean.InfoTimer;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.Timer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author cciurli
 *
 */
@Stateless(name = ITimer.EJB_REF_AGGIORNAMENTO_ID_UNI_OBSOLETI )
public class AggiornamentoIdUniObsoletiTimer extends AbstractTimer implements ITimer {
	

	private static final Logger	LOG					= LoggerFactory.getLogger(AggiornamentoIdUniObsoletiTimer.class);

	
	/** fild serialVersionUID {@like long}*/
	private static final long serialVersionUID = 3534675687699787160L;
	
	@EJB(beanName = IManagerIdUniObsoleti.EJB_REF)
	private IManagerIdUniObsoleti managerIdUniObsoleti;

	
	/**
	 * 
	 */
	public AggiornamentoIdUniObsoletiTimer() { super(); }
	
	
	/** {@inheritDoc} */
	@Override
	protected void execute(final Timer timer,final InfoTimer infoTimer ) {
		LOG.info("avvio la procedura di aggiornamento degli id");
		final long start = System.currentTimeMillis();
		try {
			managerIdUniObsoleti.aggiornaObsoleti();
		} finally {
			LOG.info("procedura di aggiornamento svolta in {} ms", (System.currentTimeMillis() - start));
		}
		
	}
	
	
}

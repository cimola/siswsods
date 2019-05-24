/** */
package it.toscana.regione.wsods.ejb.timer.impl;

import it.toscana.regione.wsods.ejb.rielaboratore.api.IManagerRielaboratore;
import it.toscana.regione.wsods.ejb.timer.abs.AbstractTimer;
import it.toscana.regione.wsods.ejb.timer.api.ITimer;
import it.toscana.regione.wsods.ejb.timer.bean.InfoTimer;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.Timer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author vmaltese
 *
 */
@Stateless(name = ITimer.EJB_REF_RECUPERA_SCARTATI_CERT )
public class RecuperaScartatiTimerCert extends AbstractTimer implements ITimer {
	
	private static final Logger	LOG	= LoggerFactory.getLogger(RecuperaScartatiTimerCert.class);
	
	/** field serialVersionUID {@like long}*/
	private static final long serialVersionUID = 8881570684126415863L;
	
	@EJB(beanName = IManagerRielaboratore.EJB_REF)
	private IManagerRielaboratore managerRielaboratore;

	public RecuperaScartatiTimerCert() { super(); }
	
	/** {@inheritDoc} */
	@Override
	protected void execute(final Timer timer,final InfoTimer infoTimer ) {
		boolean continua = true;
			while (continua) {
				final int recuperati = managerRielaboratore.recuperaRecordCert();
				LOG.debug("recuperati {} record, proseguo",recuperati);
				continua = recuperati > 0;
			}
			LOG.debug("nessun recod  da recuperare.");
	}
	
	
}

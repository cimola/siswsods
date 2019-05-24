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
 * @author gorlando
 * 
 */
@Stateless(name = ITimer.EJB_REF_RECUPERA_SCARTATI_GIANOV4_CERT)
public class RecuperaScartatiGianov4TimerCert extends AbstractTimer implements ITimer {

	private static final Logger LOG = LoggerFactory.getLogger(RecuperaScartatiGianov4TimerCert.class);

	/** fild serialVersionUID {@like long} */
	private static final long serialVersionUID = 4933737627006582237L;

	@EJB(beanName = IManagerRielaboratore.EJB_REF)
	private IManagerRielaboratore managerRielaboratore;

	/**
	 * 
	 */
	public RecuperaScartatiGianov4TimerCert() {
		super();
	}

	/** {@inheritDoc} */
	@Override
	protected void execute(final Timer timer, final InfoTimer infoTimer) {
		boolean continua = true;
		while (continua) {
			final int recuperati = managerRielaboratore.recuperaRecordCertGianov4();
			LOG.debug("recuperati {} record, proseguo", recuperati);
			continua = recuperati > 0;
		}
		LOG.debug("nessun recod  da recuperare.");
	}

}

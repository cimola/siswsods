/** */
package it.toscana.regione.wsods.ejb.timer.impl;

import it.toscana.regione.wsods.ejb.svecchiatore.api.IRicevuteDownloadInserimentiDownloadSvecchiatore;
import it.toscana.regione.wsods.ejb.svecchiatore.api.IRicevuteDownloadVariazioniDownloadSvecchiatore;
import it.toscana.regione.wsods.ejb.svecchiatore.api.ISoapTrackingWsodsToApsKoSvecchiatore;
import it.toscana.regione.wsods.ejb.svecchiatore.api.ISoapTrackingWsodsToApsVeryOldSvecchiatore;
import it.toscana.regione.wsods.ejb.svecchiatore.api.ISoapTrackingWsodsToSogeiKoSvecchiatore;
import it.toscana.regione.wsods.ejb.svecchiatore.api.ISoapTrackingWsodsToSogeiOkSvecchiatore;
import it.toscana.regione.wsods.ejb.svecchiatore.api.RicevuteDownloadVariazioniAlMinutoDownloadSvecchiatore;
import it.toscana.regione.wsods.ejb.timer.abs.AbstractTimer;
import it.toscana.regione.wsods.ejb.timer.api.ITimer;
import it.toscana.regione.wsods.ejb.timer.bean.InfoTimer;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.Timer;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author cciurli
 *
 */
@TransactionManagement(TransactionManagementType.CONTAINER)
@Stateless(name = ITimer.EJB_REF_SVECCHIATORE)
public class SvecchiatoreTimer extends AbstractTimer implements ITimer {

	private static final Logger	LOG					= LoggerFactory.getLogger(SvecchiatoreTimer.class);

	/** fild serialVersionUID {@like long}*/
	private static final long	serialVersionUID	= -3534107484546667160L;

	@EJB(beanName = IRicevuteDownloadInserimentiDownloadSvecchiatore.EJB_REF)
	private IRicevuteDownloadInserimentiDownloadSvecchiatore	ricevuteDownloadInserimentiDownloadSvecchiatore;
	
	@EJB(beanName = IRicevuteDownloadVariazioniDownloadSvecchiatore.EJB_REF)
	private IRicevuteDownloadVariazioniDownloadSvecchiatore	 ricevuteDownloadVariazioniDownloadSvecchiatore;
	
	@EJB(beanName = RicevuteDownloadVariazioniAlMinutoDownloadSvecchiatore.EJB_REF)
	private RicevuteDownloadVariazioniAlMinutoDownloadSvecchiatore	 ricevuteDownloadVariazioniAlMinutoDownloadSvecchiatore;

	@EJB(beanName = ISoapTrackingWsodsToSogeiKoSvecchiatore.EJB_REF)
	private ISoapTrackingWsodsToSogeiKoSvecchiatore soapTrackingWsodsToSogeiKoSvecchiatore;
	
	@EJB(beanName = ISoapTrackingWsodsToSogeiOkSvecchiatore.EJB_REF)
	private ISoapTrackingWsodsToSogeiOkSvecchiatore soapTrackingWsodsToSogeiOkSvecchiatore;
	
	@EJB(beanName = ISoapTrackingWsodsToApsKoSvecchiatore.EJB_REF)
	private ISoapTrackingWsodsToApsKoSvecchiatore soapTrackingWsodsToApsKoSvecchiatore;
	
	@EJB(beanName = ISoapTrackingWsodsToApsVeryOldSvecchiatore.EJB_REF)
	private ISoapTrackingWsodsToApsVeryOldSvecchiatore soapTrackingWsodsToApsVeryOldSvecchiatore;

	/**
	 * 
	 */
	public SvecchiatoreTimer() {}

	

	@Override 
	protected void execute(final Timer timer, final InfoTimer infoTimer) {

		final int inserimentiSvecchiati = ricevuteDownloadInserimentiDownloadSvecchiatore.svecchia();
		LOG.info("svecchiate {} ricevute di inserimenti ", inserimentiSvecchiati);

		final int variazioniSvecchiate = ricevuteDownloadVariazioniDownloadSvecchiatore.svecchia();
		LOG.info("svecchiate {} ricevute di variazioni ", variazioniSvecchiate);

		final int variazioniAlMinutoSvecchiate = ricevuteDownloadVariazioniAlMinutoDownloadSvecchiatore.svecchia();
		LOG.info("svecchiate {} ricevute di variazioni al minuto ", variazioniAlMinutoSvecchiate);

		final int numSoapTrackingWsodsToSogeiKoSvecchiatore = soapTrackingWsodsToSogeiKoSvecchiatore.svecchia();
		LOG.info("svecchiate {} soapTrackingWsodsToSogeiKoSvecchiatore ", numSoapTrackingWsodsToSogeiKoSvecchiatore);
		
		final int numSoapTrackingWsodsToSogeiOkSvecchiatore = soapTrackingWsodsToSogeiOkSvecchiatore.svecchia();
		LOG.info("svecchiate {} soapTrackingWsodsToSogeiOkSvecchiatore ", numSoapTrackingWsodsToSogeiOkSvecchiatore);
		
		final int numSoapTrackingWsodsToApsKoSvecchiatore = soapTrackingWsodsToApsKoSvecchiatore.svecchia();
		LOG.info("svecchiate {} soapTrackingWsodsToApsKoSvecchiatore ", numSoapTrackingWsodsToApsKoSvecchiatore);
		
		final int numSoapTrackingWsodsToApsVeryOldSvecchiatore = soapTrackingWsodsToApsVeryOldSvecchiatore.svecchia();
		LOG.info("svecchiate {} soapTrackingWsodsToApsVeryOldSvecchiatore ", numSoapTrackingWsodsToApsVeryOldSvecchiatore);
		
	}
		
	

}

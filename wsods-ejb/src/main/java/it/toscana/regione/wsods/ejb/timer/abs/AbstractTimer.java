/** */
package it.toscana.regione.wsods.ejb.timer.abs;

import it.toscana.regione.wsods.ejb.timer.api.ITimer;
import it.toscana.regione.wsods.ejb.timer.bean.InfoTimer;
import it.toscana.regione.wsods.exception.EjbException;
import it.toscana.regione.wsods.exception.RielaboratoreException;
import it.toscana.regione.wsods.lib.Convertitore;
import it.toscana.regione.wsods.singleton.Conf;
import it.toscana.regione.wsods.type.Code;

import java.io.Serializable;
import java.util.Collection;

import javax.annotation.Resource;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerService;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author cciurli
 *
 */
public abstract class AbstractTimer implements ITimer {
	
	private static final Logger LOG = LoggerFactory.getLogger(AbstractTimer.class);
	
	/** fild serialVersionUID {@like long}*/
	private static final long	serialVersionUID	= -1246799583884073031L;

	@Resource
	private TimerService timerService;
	
	/**
	 * 
	 */
	public AbstractTimer() { super(); }
	
	protected abstract void execute(final Timer timer, final InfoTimer infoTimer);
	
	protected TimerService getTimerService(){ return timerService; }

	@Override
	public  Collection<Timer> getTimers() {
		return getTimerService().getTimers();	
	}
	
		
	@Override
	public void createTimer(final InfoTimer info) {
		if(info == null){throw new EjbException(Code.EJB_GENERICO,"Impossibile creare un timer con ifnoTimer Nunllo");}
		final long intervalDuration = info.getTimeOut();
		if(timerService!= null){
			final long initialIntervalDuration = intervalDuration+Conf.getInitialDelay();
			timerService.createTimer(initialIntervalDuration, intervalDuration, info);
			LOG.info("creo il timer "+info.toString()+" con intrvallo " + intervalDuration + " ms");
		}
	}
	
	public void stopTimers() {
		if(timerService!= null){
			Collection<?> c_obj = timerService.getTimers();
			if(c_obj!= null){
				for (Object obj : c_obj) {
					if(obj != null &&  obj instanceof Timer){
						final Serializable info = ((Timer)obj).getInfo();
						((Timer)obj).cancel();
						LOG.info("cancello il timer "+info!=null?info.toString():"");
					}
				}
			}
		}
	}
	
	@Timeout @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void timeout(final Timer timer) {
		final long start = System.currentTimeMillis();
		final InfoTimer infoTimer;
	
		final Serializable info = timer.getInfo();
		if(info != null && info instanceof InfoTimer){
			infoTimer = (InfoTimer) info;
		} else {
			LOG.warn("e' stato impossibile ottenere  l'infotimer ");
			infoTimer = InfoTimer.createInfoTimer(info);
		}
		
		try{
			LOG.info("Start timeout "+infoTimer);
			
			execute(timer,infoTimer);
		
		} catch (Throwable t){
			if(gestita(t)){
				LOG.error("Errore durante l'esecuzione del timer "+infoTimer+"\n"+Convertitore.convertiNoStackTrace(t));
			} else {
				LOG.error("Errore durante l'esecuzione del timer "+infoTimer,t);
			}
		} finally {
			LOG.info("end timeout "+infoTimer+" ms("+(System.currentTimeMillis()-start)+")");
		}
	}
	
	private static boolean gestita(Throwable e){
		if(e==null){
			return false;
		} else if(e instanceof RielaboratoreException && ((RielaboratoreException)e).code.equals(Code.DATO_MANCANTE)){
			return true;
		} else if(e instanceof RielaboratoreException && ((RielaboratoreException)e).code.equals(Code.SDM_DATI_OTTENUTI_NON_SUFFICIENTI)){
			return true;
		} else if(e instanceof RielaboratoreException && ((RielaboratoreException)e).code.equals(Code.SDM_SOGGETTO_NON_TROVATO)){
			return true;
		} else {
			return gestita(e.getCause());
		}
	}
	
	

}

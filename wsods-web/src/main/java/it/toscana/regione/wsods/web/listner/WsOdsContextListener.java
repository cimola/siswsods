/** */
package it.toscana.regione.wsods.web.listner;

import it.toscana.regione.monitoraggio.conf.MonitorServiceConfiguration;
import it.toscana.regione.monitoraggio.exception.ConfigurationException;
import it.toscana.regione.wsods.costanti.Prop;
import it.toscana.regione.wsods.entity.bean.api.ISoggetto;
import it.toscana.regione.wsods.entity.bean.impl.Soggetto;
import it.toscana.regione.wsods.exception.ConvertitoreException;
import it.toscana.regione.wsods.lib.Convertitore;
import it.toscana.regione.wsods.singleton.Cache;
import it.toscana.regione.wsods.singleton.Conf;
import it.toscana.regione.wsods.web.util.RecuperoDownloadUtil;
import it.toscana.regione.wsods.web.util.TimerUtil;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




/**
 * @author cciurli
 *
 */
public class WsOdsContextListener implements ServletContextListener {

	private final static Logger	LOG	= LoggerFactory.getLogger(WsOdsContextListener.class);


	/**
	 * 
	 */
	public WsOdsContextListener() {}


	/** {@inheritDoc} */ @Override public void contextInitialized(final ServletContextEvent sce) {

		LOG.info("start init [WsOdsContextListener] [WsOds]");
		try{
			loadConf(sce);
			
			RecuperoDownloadUtil.configuraDownloadSogeiRecupero();
			
			loadSoggettiFake();
			
			TimerUtil.destroyTimer();
			destroyMonitoraggio();
			
			initMonitoraggio();
			
			TimerUtil.initTimer();
		}catch(RuntimeException e){
			LOG.error("problemi durante lo start del lisner",e);
			throw e;
		}
		LOG.info("end init [WsOdsContextListener] [WsOds]");
		
	}
	
	private void loadSoggettiFake(){
		if(Conf.getLoadSoggettiFake()){
			try {
				final ISoggetto JOIKHI14P57C781P = new Soggetto("AAA000000000000000000001", "JOIKHI14P57C781P", "NAVILLOD", "HERVE'", "000000",Convertitore.convertTime("17/09/1914", "dd/MM/yyyy") , "M");
				final ISoggetto AAOLSS12B01H501X = new Soggetto("AAA000000000000000000002", "AAOLSS12B01H501X", "AAOUA", "ALESSIO", "000000", Convertitore.convertTime("01/02/2012", "dd/MM/yyyy"), "M");
				final ISoggetto NRDGPP40R20A657V = new Soggetto("AAA000000000000000000003", "NRDGPP40R20A657V", "NRD", "GIUSEPPE", "000000", Convertitore.convertTime("20/10/1940", "dd/MM/yyyy"), "M");
				Cache.add(JOIKHI14P57C781P);
				Cache.add(AAOLSS12B01H501X);
				Cache.add(NRDGPP40R20A657V);
			} catch (ConvertitoreException e) {
				LOG.warn(e.getMessage());
			}
		}
	}

	/** {@inheritDoc} */ @Override public void contextDestroyed(final ServletContextEvent sce) {
		LOG.info("start destroy [WsOdsContextListener] [WsOds]");
		try{
			TimerUtil.destroyTimer();
			destroyMonitoraggio();
		}catch(RuntimeException e){
			LOG.error("problemi durante la destroy del lisner",e);
		}
		LOG.info("end destroy [WsOdsContextListener] [WsOds]");
	}
	
	
	private void loadConf(final ServletContextEvent sce){
		if (sce != null) {
			final ServletContext sCtx = sce.getServletContext();
			if (sCtx != null) {
				final String pathFile = sCtx.getInitParameter(Prop.PARAM_WEB_XML_CONF_FILE_PATH);

				if (pathFile == null || pathFile.trim().length() == 0) {
					LOG.warn("carico le properties di configurazione da path di default");
					Conf.init();
				} else {
					LOG.warn("carico le properties di configurazione da [" + pathFile + "]");
					Conf.init(pathFile);
				}

			} else {
				throw new RuntimeException("impossibile recuperare il ServletContext");
			}
		} else {
			throw new RuntimeException("impossibile recuperare il ServletContextEvent");
		}
	}
	
	
	
	
	private void initMonitoraggio(){
		LOG.info("inizializzo il monitoraggio");
		String confPath = Conf.getMonitoraggioConfAbsolutePathFileConf();
		String contextRoot = Conf.getMonitoraggioConfContextRoot();
		String wsUrl = Conf.getMonitoraggioConfUrlWs();
		String jspUrl = Conf.getMonitoraggioConfUrlJsp();
		LOG.debug("confPath: [" + confPath + "]");
		LOG.debug("contextRoot: [" + contextRoot + "]");
		LOG.debug("wsUrl: [" + wsUrl + "]");
		LOG.debug("jspUrl: [" + jspUrl + "]");
		
		try {
			MonitorServiceConfiguration.addConf(confPath, contextRoot, wsUrl, jspUrl);
			MonitorServiceConfiguration.startTimer(contextRoot);
		} catch (ConfigurationException e) {
			String msg = "impossibile caricare la configurazione";
			LOG.error(msg, e);
			throw new RuntimeException(msg, e);
		}
		LOG.info("inizializzazione monitoraggio completata");
	}
	private void destroyMonitoraggio(){
		LOG.info("distruggo eventuali timer per il monitoraggio");
		String confPath = Conf.getMonitoraggioConfAbsolutePathFileConf();
		String contextRoot = Conf.getMonitoraggioConfContextRoot();
		String wsUrl = Conf.getMonitoraggioConfUrlWs();
		String jspUrl = Conf.getMonitoraggioConfUrlJsp();
		LOG.debug("confPath: [" + confPath + "]");
		LOG.debug("contextRoot: [" + contextRoot + "]");
		LOG.debug("wsUrl: [" + wsUrl + "]");
		LOG.debug("jspUrl: [" + jspUrl + "]");
		try {
			MonitorServiceConfiguration.addConf(confPath, contextRoot, wsUrl, jspUrl);
			MonitorServiceConfiguration.destroy(contextRoot);
		} catch (ConfigurationException e) {
			String msg = "impossibile distruggere il timer ";
			LOG.warn(msg,e);
			throw new RuntimeException(msg,e);
		}
		LOG.info("distruzione eventuali timer per il monitoraggio completata");
	}
	

}

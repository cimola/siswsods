package it.toscana.regione.wsods.web.util;

import it.toscana.regione.wsods.costanti.Varie;
import it.toscana.regione.wsods.ejb.download.api.IRecuperoDownloadManager;
import it.toscana.regione.wsods.entity.jpa.api.IRecuperoDownloadSogei;
import it.toscana.regione.wsods.exception.ConvertitoreException;
import it.toscana.regione.wsods.lib.Convertitore;
import it.toscana.regione.wsods.singleton.Conf;
import it.toscana.regione.wsods.singleton.PIL;

import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class RecuperoDownloadUtil {

	private final static Logger	LOG	= LoggerFactory.getLogger(RecuperoDownloadUtil.class);
	
	
	

	private RecuperoDownloadUtil() {
		super();
		
	}
	
	

	private static IRecuperoDownloadManager loadEjbManager(){
		IRecuperoDownloadManager ejb =  null;
		try {
			ejb = PIL.lookup(IRecuperoDownloadManager.EJB_REF, IRecuperoDownloadManager.class);
		} catch (NamingException e) {
			LOG.error("impossibile fare la lookup sul dao IRecuperoDownloadSogeiDao",e);
		}
		return ejb;
	}
	public static boolean validaIntervalloGiorniNonFuturi(final String primoGiorno,final String ultomoGiorno){
		try {
				final long primo = Convertitore.convertTime(primoGiorno, Varie.FORMATO_GIORNO);
				final long ultimo = Convertitore.convertTime(ultomoGiorno, Varie.FORMATO_GIORNO);
				if (primo>ultimo) {return false;}
				final long oggi = Convertitore.convertiTimeInizioGiornata(new Long(System.currentTimeMillis()));
				if (oggi<ultimo) {return false;}
		} catch (ConvertitoreException e) {
			return false;
		}
		return true;
	}
	public static boolean validaDataOraLimite(final String dataOraLimite, final String tipo){
		try {
				String formato = (IRecuperoDownloadSogei.TIPO_CERTIFICAZIONE.equals(tipo))?Varie.FORMATO_DATA_SOGEI:Varie.FORMATO_DATA_ORA_LIMITE_SOGEI;
				Convertitore.convertTime(dataOraLimite, formato);
		} catch (ConvertitoreException e) {
			return false;
		}
		return true;
	}
	public static boolean validaTipoDownload(final String tipo){
		if(tipo != null && tipo.trim().length()==1){
			 return "I".equals(tipo.trim()) || "V".equals(tipo.trim()) || "A".equals(tipo.trim()) || IRecuperoDownloadSogei.TIPO_CERTIFICAZIONE.equals(tipo.trim());
		}
		return false;
	}
	
	
	
	public static String configuraDownloadSogeiRecupero(final String dataOraLimiteMax,final String  dataOraLimiteMin,final String tipoDownload){
		final IRecuperoDownloadManager manager = loadEjbManager();
		
		if(manager != null){
			if("I".equals(tipoDownload)){
				Conf.setMaxDataOraLimiteDownloadRecuperoInserimento(dataOraLimiteMax);
			} else if ("V".equals(tipoDownload) ){
				Conf.setMaxDataOraLimiteDownloadRecuperoVariazioni(dataOraLimiteMax);
			} else if ("A".equals(tipoDownload) ){
				Conf.setMaxDataOraLimiteDownloadRecuperoVariazioniAlMinuto(dataOraLimiteMax);
			}
			else if (IRecuperoDownloadSogei.TIPO_CERTIFICAZIONE.equals(tipoDownload) ){
				Conf.setMaxDataLimiteDownloadRecuperoCertificazioni(dataOraLimiteMax);
			}
			
			manager.configuraDownloadSogeiRecupero(dataOraLimiteMax, dataOraLimiteMin, tipoDownload);
			
			return "configurazione memorizzata e record di start inserito su IRicevutaDownloadSogei\n";
		} else {
			return "impossibile ottenere l'ejb "+IRecuperoDownloadManager.EJB_REF + "\n";
		}
	}
	
	public static void configuraDownloadSogeiRecupero(){
		final IRecuperoDownloadManager manager = loadEjbManager();
		if(manager != null){
			manager.configuraDownloadSogeiRecupero();
		} else {
			LOG.error("Impossibile ottenere l'ejb {}",IRecuperoDownloadManager.EJB_REF);
		}
	}
	
	public static String skypDownloadSogeiCorrente(final String  dataOraLimiteMin,final String tipoDownload){
		final IRecuperoDownloadManager manager = loadEjbManager();
		if(manager != null){
			manager.skipDownloadSogeiCorrente(dataOraLimiteMin, tipoDownload);
			return "impostato lo l'ultimo record elaborato correttamente per la tipologia "+tipoDownload+"\n";
		} else {
			return "Impossibile ottenere l'ejb  "+ IRecuperoDownloadManager.EJB_REF +"\n";
		}
	}
	
}

package it.toscana.regione.wsods.ejb.download.impl;

import it.toscana.regione.wsods.costanti.Prop;
import it.toscana.regione.wsods.ejb.dao.api.IDao;
import it.toscana.regione.wsods.ejb.dao.api.IRecuperoDownloadSogeiDao;
import it.toscana.regione.wsods.ejb.dao.api.IRicevutaDownloadSogeiDao;
import it.toscana.regione.wsods.ejb.download.api.IRecuperoDownloadManager;
import it.toscana.regione.wsods.entity.jpa.api.IRecuperoDownloadSogei;
import it.toscana.regione.wsods.singleton.Conf;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Stateless(name = IRecuperoDownloadManager.EJB_REF)
public class RecuperoDownloadManager implements IRecuperoDownloadManager {
	
	private final static Logger	LOG	= LoggerFactory.getLogger(RecuperoDownloadManager.class);
	
	public RecuperoDownloadManager() { 
	}
	
	@EJB(beanName = IDao.EJB_REF_RICEVUTA_DOWNLOAD_SOGEI)
	private IRicevutaDownloadSogeiDao iRicevutaDownloadSogeiDao;
	
	@EJB(beanName = IDao.EJB_REF_RECUPERO_DOWNLOAD_SOGEI)
	private IRecuperoDownloadSogeiDao iRecuperoDownloadSogeiDao;
	
	/** {@inheritDoc} */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void configuraDownloadSogeiRecupero(final String dataOraLimiteMax,final String  dataOraLimiteMin,final String tipoDownload){
		LOG.info("aggiorno la configurazione del timer di recupero con dataOraLimiteMin [{}] dataOraLimiteMax [{}] tipoDownload [{}]",dataOraLimiteMin, dataOraLimiteMax, tipoDownload);
		iRecuperoDownloadSogeiDao.aggiornaConfigurazione(dataOraLimiteMin, dataOraLimiteMax, tipoDownload);
		LOG.info("inseristo un record fittizio di ricevuta download per dare la via al download di recupero con dataOraLimite [{}] e tipoAutocertificazione [{}]",dataOraLimiteMin, tipoDownload);
		iRicevutaDownloadSogeiDao.insertStartRecupero(tipoDownload, dataOraLimiteMin);
	}

	@Override
	public void skipDownloadSogeiCorrente(final String  dataOraLimiteMin,final String tipoDownload) {
		LOG.info("inseristo un record fittizio di ricevuta download per riconfigurare da quando far ripartitr il download corrente con dataOraLimite [{}] e tipoAutocertificazione [{}]",dataOraLimiteMin, tipoDownload);
		iRicevutaDownloadSogeiDao.insertSkipCorrente(tipoDownload, dataOraLimiteMin);
	}
	 
	/** {@inheritDoc} */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void configuraDownloadSogeiRecupero(){
		LOG.info("carico la configurazione dei limiti del timer di downloar di recupero da db");
		final List<IRecuperoDownloadSogei>  list = iRecuperoDownloadSogeiDao.recuperaConfigurazioni();
		boolean notFindI = true;
		boolean notFindV = true;
		boolean notFindVaM = true;
		boolean notFindA = true;
		
		for(IRecuperoDownloadSogei entity : list){
			if(entity!= null && IRecuperoDownloadSogei.TIPO_INSERIMENTO.equals(entity.getTipoAutocertificazione())){
				Conf.setMaxDataOraLimiteDownloadRecuperoInserimento(entity.getDataOraLimiteStop());
				notFindI = false;
			} else if(entity!= null && IRecuperoDownloadSogei.TIPO_VARIAMENTO.equals(entity.getTipoAutocertificazione())){
				Conf.setMaxDataOraLimiteDownloadRecuperoVariazioni(entity.getDataOraLimiteStop());
				notFindV = false;
			} else if(entity!= null && IRecuperoDownloadSogei.TIPO_VARIAZIONI_AL_MINUTO.equals(entity.getTipoAutocertificazione())){
				Conf.setMaxDataOraLimiteDownloadRecuperoVariazioniAlMinuto(entity.getDataOraLimiteStop());
				notFindVaM = false;
			} else if(entity!= null && IRecuperoDownloadSogei.TIPO_CERTIFICAZIONE.equals(entity.getTipoAutocertificazione())){
				Conf.setMaxDataLimiteDownloadRecuperoCertificazioni(entity.getDataOraLimiteStop());
				notFindA = false;
			}
			else {
				if(entity!= null){
					LOG.warn("trovate alcune configurazioni non gestite: ["+entity+"]");
				}
			}
		}
		if(notFindI){
			Conf.setMaxDataOraLimiteDownloadRecuperoInserimento("010100010000");
		}
		if(notFindV){
			Conf.setMaxDataOraLimiteDownloadRecuperoVariazioni("010100010000");
		}
		if(notFindVaM){
			Conf.setMaxDataOraLimiteDownloadRecuperoVariazioniAlMinuto("010100010000");
		}
		if(notFindA){
			Conf.setMaxDataLimiteDownloadRecuperoCertificazioni("01/01/0001");
		}
		
		LOG.debug("ho settato la properties {} [{}]",Prop.MAX_DATA_ORA_LIMITE_DOWNLOAD_RECUPERO_INSERIMENTI,Conf.getMaxDataOraLimiteDownloadRecuperoInserimento());
		LOG.debug("ho settato la properties {} [{}]",Prop.MAX_DATA_ORA_LIMITE_DOWNLOAD_RECUPERO_VARIAZIONI,Conf.getMaxDataOraLimiteDownloadRecuperoVariazioni());
		LOG.debug("ho settato la properties {} [{}]",Prop.MAX_DATA_ORA_LIMITE_DOWNLOAD_RECUPERO_VARIAZIONI_AL_MINUTO,Conf.getMaxDataOraLimiteDownloadRecuperoVariazioniAlMinuto());
		LOG.debug("ho settato la properties {} [{}]",Prop.MAX_DATA_LIMITE_DOWNLOAD_RECUPERO_CERTIFICAZIONI,Conf.getMaxDataLimiteDownloadRecuperoCertificazioni());
	}


}

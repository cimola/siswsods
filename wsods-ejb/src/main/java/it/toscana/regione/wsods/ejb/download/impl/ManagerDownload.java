/** */
package it.toscana.regione.wsods.ejb.download.impl;

import it.toscana.regione.autocertificazionireddito.entity.jaxb.sogei.download.response.Ricevuta;
import it.toscana.regione.wsods.costanti.Varie;
import it.toscana.regione.wsods.ejb.download.api.IManagerDownloader;
import it.toscana.regione.wsods.ejb.download.api.IToolDownloadSogei;
import it.toscana.regione.wsods.entity.jpa.api.IRicevutaDownloadSogei;
import it.toscana.regione.wsods.exception.ConvertitoreException;
import it.toscana.regione.wsods.exception.WsOdsException;
import it.toscana.regione.wsods.exception.WsOdsRuntimeException;
import it.toscana.regione.wsods.lib.Convertitore;
import it.toscana.regione.wsods.singleton.Conf;
import it.toscana.regione.wsods.type.Code;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author cciurli
 *
 */
@Stateless(name = IManagerDownloader.EJB_REF)
public class ManagerDownload implements IManagerDownloader {
	
	private static final Logger LOG = LoggerFactory.getLogger(ManagerDownload.class);
	
	@EJB(beanName = IToolDownloadSogei.EJB_REF)
	private IToolDownloadSogei toolDownloadSogei;
	
	public ManagerDownload() {
		super();
	}

	/** {@inheritDoc} */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void download(final String tipoRichiesta, final String tipologiaRicevuta) throws WsOdsRuntimeException {
		final long start = System.currentTimeMillis();
		LOG.info("avviata la procedura di download da sogei");
		try {
			
			final long newDataOraLimite = toolDownloadSogei.newDataLimite(tipoRichiesta,tipologiaRicevuta);
			
			if(richiedibile(newDataOraLimite, tipoRichiesta, tipologiaRicevuta)){
				
				final Ricevuta ricevutaDownloadInsersioniSogei = toolDownloadSogei.sendRequestDownload(tipoRichiesta, newDataOraLimite);
			
				final int dimMax = Conf.getMassimaFinestraElaborazioneAutocertificazioni();
				
				if(ricevutaDownloadInsersioniSogei.getListaautocertificazioni()!=null && ricevutaDownloadInsersioniSogei.getListaautocertificazioni().size()>dimMax ){
					LOG.debug("download {} elaborazione asincrona.",tipoRichiesta);
					
					toolDownloadSogei.serializaEsalva(ricevutaDownloadInsersioniSogei,tipologiaRicevuta);

				} else {
					LOG.debug("download {} elaborazione sincrona.",tipoRichiesta);
					
					toolDownloadSogei.convertiEsalva(ricevutaDownloadInsersioniSogei,tipologiaRicevuta);
					
				}
				
			} else{
				LOG.debug("download {} allineato e in attesa.",tipoRichiesta);
			}
			
		} catch (final Throwable t) {
			if(t instanceof WsOdsException ){
				throw new WsOdsRuntimeException((WsOdsException)t);
			} else if(t instanceof WsOdsRuntimeException){
				throw (WsOdsRuntimeException)t;
			} else {
				throw new WsOdsRuntimeException(Code.EJB_GENERICO,t);
			}
		} finally {
			final long time = System.currentTimeMillis() - start;
			if (time > Varie.TIME_MINUTO) {
				LOG.warn("[PERFORMANCE] - download eseguito in " + time + " ms");
			} else {
				LOG.debug("[PERFORMANCE] - download eseguito in " + time + " ms");
			}
		}
		
	}

	private static boolean richiedibile(final long newDataOraLimite,final String tipoRichiesta,final String tipologiaRicevuta){
		if(IRicevutaDownloadSogei.TIPOLOGIA_RICEVUTA_CORRENTE.equals(tipologiaRicevuta)){
			if ("A".equals(tipoRichiesta)) {
				return (newDataOraLimite <= (System.currentTimeMillis() - Conf.getDeltaNuovaDataOraLimiteDownloadVariazioni()));
			}
			else {
				return (newDataOraLimite <= (System.currentTimeMillis() - Conf.getDeltaNuovaDataOraLimiteDownload()));
			}		
		} else if(IRicevutaDownloadSogei.TIPOLOGIA_RICEVUTA_RECUPERO.equals(tipologiaRicevuta)){
			
				final String maxDataOraLimite;
				if("I".equals(tipoRichiesta)){
					maxDataOraLimite = Conf.getMaxDataOraLimiteDownloadRecuperoInserimento();
				} else if("V".equals(tipoRichiesta)){
					maxDataOraLimite = Conf.getMaxDataOraLimiteDownloadRecuperoVariazioni();
				} else if("A".equals(tipoRichiesta)){
					maxDataOraLimite = Conf.getMaxDataOraLimiteDownloadRecuperoVariazioniAlMinuto();
				}else {
					maxDataOraLimite = "010100010000";
				}
				try {
					final long max = Convertitore.convertTime(maxDataOraLimite, Varie.FORMATO_DATA_ORA_LIMITE_SOGEI);
					return (newDataOraLimite < max);
				} catch (ConvertitoreException e) {
					throw new WsOdsRuntimeException(Code.FORMATO,"valore tipologiaRicevuta ["+maxDataOraLimite+"] non convertibile secondo il fromato ["+Varie.FORMATO_DATA_ORA_LIMITE_SOGEI+"]");
				}
		} else {
			throw new WsOdsRuntimeException(Code.EJB_GENERICO,"valore tipologiaRicevuta ["+tipologiaRicevuta+"] non correttamente gestito");
		}
	}

	/** {@inheritDoc} */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void elaboraAsincrono()
	{
		final long start = System.currentTimeMillis();
		long recordElaborati = 0;
		try{
			boolean flag = true;			
			while (flag){
				final long elaborazioeneParziale = toolDownloadSogei.aggiornaRicevutaDownload();
				if(elaborazioeneParziale==0){
					flag = false;
				}
				recordElaborati=recordElaborati+elaborazioeneParziale;
			}
		} finally{
			final long time = System.currentTimeMillis()-start;
			if(recordElaborati==0){
				LOG.debug("[PERFORMANCE] - rielaborazione ricevuta download vuota eseguiti in {} ms",time);
			} else {
				LOG.debug("[PERFORMANCE] - rielaborazione ricevuta download con {} autocertificazioni eseguiti in {} ms",recordElaborati,time);
			}
		}
	}
}

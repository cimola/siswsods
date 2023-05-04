package it.toscana.regione.wsods.ejb.download.impl;

import it.toscana.regione.autocertificazionireddito.entity.jaxb.sogei.download.response.Autocertificazione;
import it.toscana.regione.certificazionireddito.entity.jaxb.sogei.download.response.Certificazione;
import it.toscana.regione.wsods.costanti.Varie;
import it.toscana.regione.wsods.ejb.dao.api.IAutocertificazioneTmpDao;
import it.toscana.regione.wsods.ejb.dao.api.ICertificazioneTmpDao;
import it.toscana.regione.wsods.ejb.dao.api.IRicevutaDownloadSogeiDao;
import it.toscana.regione.wsods.ejb.download.api.IDownloadFromSogei;
import it.toscana.regione.wsods.ejb.download.api.IPersistAutocertificazioniSogei;
import it.toscana.regione.wsods.ejb.download.api.IToolDownloadSogei;
import it.toscana.regione.wsods.ejb.download.api.ITransformRicevuta;
import it.toscana.regione.wsods.ejb.download.api.ITransformRicevutaCert;
import it.toscana.regione.wsods.entity.jpa.api.IAutocertificazioneTmp;
import it.toscana.regione.wsods.entity.jpa.api.ICertificazioneTmp;
import it.toscana.regione.wsods.entity.jpa.api.IRecuperoDownloadSogei;
import it.toscana.regione.wsods.entity.jpa.api.IRicevutaDownloadSogei;
import it.toscana.regione.wsods.exception.ConvertitoreException;
import it.toscana.regione.wsods.exception.DownloadException;
import it.toscana.regione.wsods.exception.MapperException;
import it.toscana.regione.wsods.exception.WsOdsException;
import it.toscana.regione.wsods.exception.WsOdsRuntimeException;
import it.toscana.regione.wsods.lib.Convertitore;
import it.toscana.regione.wsods.lib.Mapper;
import it.toscana.regione.wsods.lib.XMLTool;
import it.toscana.regione.wsods.singleton.Conf;
import it.toscana.regione.wsods.type.Code;
import it.toscana.regione.wsods.type.Sorgente;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.xml.bind.JAXBElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;


@Stateless(name = IToolDownloadSogei.EJB_REF)
public class ToolDownloadSogei implements IToolDownloadSogei {
	

	private static final Logger LOG = LoggerFactory.getLogger(ToolDownloadSogei.class);
	
	@EJB(beanName = IRicevutaDownloadSogeiDao.EJB_REF)
	private IRicevutaDownloadSogeiDao ricevutaDownloadSogeiDao;
	
	@EJB(beanName = IDownloadFromSogei.EJB_REF)
	private IDownloadFromSogei downloadFromSogei;
	
	@EJB(beanName = IPersistAutocertificazioniSogei.EJB_REF)
	private IPersistAutocertificazioniSogei persistAutocertificazioniSogei;
	
	@EJB(beanName = IAutocertificazioneTmpDao.EJB_REF)
	private IAutocertificazioneTmpDao autocertificazioneTmpDao;
	
	@EJB(beanName = ICertificazioneTmpDao.EJB_REF)
	private ICertificazioneTmpDao certificazioneTmpDao;
	
	@EJB(beanName = ITransformRicevuta.EJB_REF)
	private ITransformRicevuta transformRicevuta;
	
	@EJB(beanName = ITransformRicevutaCert.EJB_REF)
	private ITransformRicevutaCert transformRicevutaCert;
	
	public ToolDownloadSogei() { }
	

	@Override @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public it.toscana.regione.certificazionireddito.entity.jaxb.sogei.download.response.Ricevuta sendRequestDownloadCertificazioni(final String tipoOperazione, final long newDataLimite) throws ConvertitoreException {
		String dataLimite  = Convertitore.convertTime(newDataLimite,  Varie.FORMATO_DATA_SOGEI);
		
		final it.toscana.regione.certificazionireddito.entity.jaxb.sogei.download.response.Ricevuta ricevutaDownloadCertSogei = downloadFromSogei.sendCert(dataLimite, tipoOperazione);
		return ricevutaDownloadCertSogei;
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public it.toscana.regione.autocertificazionireddito.entity.jaxb.sogei.download.response.Ricevuta sendRequestDownload(final String tipoRichiesta, final long newDataOraLimite) throws ConvertitoreException {
		final String dataOraLimite  = Convertitore.convertTime(newDataOraLimite,  Varie.FORMATO_DATA_ORA_LIMITE_SOGEI);
		
		final it.toscana.regione.autocertificazionireddito.entity.jaxb.sogei.download.response.Ricevuta ricevutaDownloadInsersioniSogei = downloadFromSogei.send(dataOraLimite, tipoRichiesta);
		return ricevutaDownloadInsersioniSogei;
	}

	@Override @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public long newDataLimite(final String tipoRichiesta, final String tipologiaRicevuta) throws ConvertitoreException {
		String lastDataOraLimite = ricevutaDownloadSogeiDao.getLastDataOraLimite(tipoRichiesta,tipologiaRicevuta);
		
		if (lastDataOraLimite == null || lastDataOraLimite.trim().length() == 0) {
			if (IRecuperoDownloadSogei.TIPO_CERTIFICAZIONE.equals(tipoRichiesta)) {
				lastDataOraLimite = Conf.getDataLimiteCertStart();
				// vork-around:
				// sposto indietro di un giorno la data letta da properties perche' dopo
				// il metodo newDataLimite la riporta avanti di un giorno e imposta l'ora alle 23:59
				lastDataOraLimite = Convertitore.convertTime((Convertitore.convertTime(lastDataOraLimite.trim(), Varie.FORMATO_DATA_SOGEI) - Varie.TIME_GIORNO),
						Varie.FORMATO_DATA_SOGEI);
			} else {
				lastDataOraLimite = Conf.getDataOraLimiteStart();
			}
		}
		
		String formato = (IRecuperoDownloadSogei.TIPO_CERTIFICAZIONE.equals(tipoRichiesta))?Varie.FORMATO_DATA_SOGEI:Varie.FORMATO_DATA_ORA_LIMITE_SOGEI;
		final long extractDataOraLimite = Convertitore.convertTime(lastDataOraLimite.trim(),  formato);
		
		final long newDataOraLimite = newDataLimite(extractDataOraLimite,tipoRichiesta);
		return newDataOraLimite;
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void serializaEsalva(final it.toscana.regione.autocertificazionireddito.entity.jaxb.sogei.download.response.Ricevuta ricevutaDownloadInsersioniSogei, final String tipologiaRicevuta) {
		final long start = System.currentTimeMillis();
		try{
			final Document ricevutaDoc = transformRicevuta.transform(ricevutaDownloadInsersioniSogei);
			
			final String xml = XMLTool.node2string(ricevutaDoc, false);
			
			//final byte[] xml = doc.getBytes();
			
			persistAutocertificazioniSogei.store(ricevutaDownloadInsersioniSogei,tipologiaRicevuta,xml);
			
		}finally{
			LOG.debug("serializza e salva download jaxb entity in {}.", System.currentTimeMillis() - start);
		}
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void serializaEsalvaCert(final it.toscana.regione.certificazionireddito.entity.jaxb.sogei.download.response.Ricevuta ricevutaDownloadInsersioniSogei, final String tipologiaRicevuta) {
		final long start = System.currentTimeMillis();
		try{
			final Document ricevutaDoc = transformRicevutaCert.transform(ricevutaDownloadInsersioniSogei);
			
			final String xml = XMLTool.node2string(ricevutaDoc, false);
			
			persistAutocertificazioniSogei.storeCert(ricevutaDownloadInsersioniSogei,tipologiaRicevuta,xml);
			
		}finally{
			LOG.debug("serializza e salva download jaxb entity in {}.", System.currentTimeMillis() - start);
		}
	}

	@Override @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void convertiEsalva(final it.toscana.regione.autocertificazionireddito.entity.jaxb.sogei.download.response.Ricevuta ricevutaDownloadSogei, final String tipologiaRicevuta) throws DownloadException {
		final long start = System.currentTimeMillis();
		try{
			final IRicevutaDownloadSogei ricevutaDownloadSogeiDb;
			if (ricevutaDownloadSogei != null) {
				try {
					ricevutaDownloadSogeiDb = Mapper.map(ricevutaDownloadSogei,tipologiaRicevuta);
					
					final String tipoAutocertificazione = ricevutaDownloadSogeiDb.getTipoAutocertificazione();
					if(tipoAutocertificazione==null){
						throw new DownloadException(Code.EJB_GENERICO,"problemi durante le operazioni di Store del autocertificazioni scaricate da sogei, tipo Autocertifcazione Nullo ");
					}
					final long dataOrdinamento = extractDataOrdinamento(ricevutaDownloadSogei.getDataOraLimite());
					
					final Sorgente sorgente = converti(tipoAutocertificazione);
					// Questo Delta viene messo per evitare che le Variazioni Scaricate nello stesso minuto delgli inserimenti vengano soppaiantati da questiUltimi.
					final long deltaOrdinamento = deltaDataOrdinamento(sorgente); 
					
					final List<Autocertificazione> listaAutocertificazione = ricevutaDownloadSogei.getListaautocertificazioni();
				
					long indexAutocertificazioni = 0;
					if (listaAutocertificazione != null && listaAutocertificazione.size() > 0) {
						for (final Autocertificazione autocertificazione : listaAutocertificazione) {
							final IAutocertificazioneTmp autocertificazioneTmp = Mapper.map(autocertificazione,sorgente);
							
							if(autocertificazioneTmpDao.notExist(autocertificazioneTmp)){
								autocertificazioneTmp.setTipoAutocertificazione(tipoAutocertificazione);
								autocertificazioneTmp.setDataOrdinamento(new Timestamp(dataOrdinamento+deltaOrdinamento));
								autocertificazioneTmp.setRicevutaIndex(indexAutocertificazioni);
								indexAutocertificazioni++;
								ricevutaDownloadSogeiDb.addAutocertificazioneTmp(autocertificazioneTmp);
							}
						}
						ricevutaDownloadSogeiDb.setNumeroRecordElaborati();
					}
					
				} catch (final WsOdsRuntimeException e) {
					throw new DownloadException("problemi durante le operazioni di Store del autocertificazioni scaricate da sogei ", e);
				} catch (final WsOdsException e) {
					throw new DownloadException("problemi durante le operazioni di Store del autocertificazioni scaricate da sogei ", e);
				}
				
				persistAutocertificazioniSogei.store(ricevutaDownloadSogeiDb);
				
			} else {
				ricevutaDownloadSogeiDb = null;
				LOG.warn("Si e' cercato di persitere una rispodta nulla.");
			}
			
			
			
		}finally{
			LOG.debug("converti e salva download jaxb entity in jpa entity in {}.", System.currentTimeMillis() - start);
		}
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void convertiEsalvaCert(final it.toscana.regione.certificazionireddito.entity.jaxb.sogei.download.response.Ricevuta ricevutaDownloadSogei, final String tipologiaRicevuta) throws DownloadException {
		final long start = System.currentTimeMillis();
		try{
			final IRicevutaDownloadSogei ricevutaDownloadSogeiDb;
			if (ricevutaDownloadSogei != null) {
				try {
					ricevutaDownloadSogeiDb = Mapper.mapCert(ricevutaDownloadSogei,tipologiaRicevuta);
					
					final String tipoCertificazione = ricevutaDownloadSogeiDb.getTipoAutocertificazione();
					if(tipoCertificazione==null){
						throw new DownloadException(Code.EJB_GENERICO,"problemi durante le operazioni di Store del certificazioni scaricate da sogei, tipo Autocertifcazione Nullo ");
					}
					final Sorgente sorgente = converti(tipoCertificazione);
					final long dataOrdinamento = extractDataOrdinamentoCert(ricevutaDownloadSogei.getDataLimite());
										
					final List<Certificazione> listaCertificazione = ricevutaDownloadSogei.getListacertificazioni();
				
					long indexCertificazioni = 0;
					if (listaCertificazione != null && listaCertificazione.size() > 0) {
						for (final Certificazione certificazione : listaCertificazione) {
							//alle volte il servizio di SOGEI se non trova certificazioni
							//inserisce nella risposta una certificazione con tutti i campi interni null(xsi:nil="true")
							//non le devo considerare
//					         <ns2:Listacertificazioni>
//					            <ns2:annoEsenzione xsi:nil="true" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"/>
//					            <ns2:codiceEsenzione xsi:nil="true" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"/>
//					            <ns2:cfSogEsente xsi:nil="true" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"/>
//					            <ns2:dataInizioValid xsi:nil="true" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"/>
//					            <ns2:dataFineValid xsi:nil="true" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"/>
//					            <ns2:dataFineValidOld xsi:nil="true" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"/>
//					         </ns2:Listacertificazioni>							
							if (certificazione != null
									&& !(certificazione.getAnnoEsenzione() == null 
											&& certificazione.getCodiceEsenzione() == null 
											&& certificazione.getCfSogEsente() == null
											&& certificazione.getDataInizioValid() == null 
											&& certificazione.getDataFineValid() == null 
											&& certificazione.getDataFineValidOld() == null)) {
								
								final ICertificazioneTmp certificazioneTmp = Mapper.map(certificazione, sorgente);
								
								if(certificazioneTmpDao.notExist(certificazioneTmp)){
									certificazioneTmp.setDataOrdinamento(new Timestamp(dataOrdinamento));
									certificazioneTmp.setRicevutaIndex(indexCertificazioni);
									indexCertificazioni++;
									ricevutaDownloadSogeiDb.addCertificazioneTmp(certificazioneTmp);
								}

							}

						}
						ricevutaDownloadSogeiDb.setNumeroRecordElaboratiCert();
					}
					
				} catch (final WsOdsRuntimeException e) {
					throw new DownloadException("problemi durante le operazioni di Store del certificazioni scaricate da sogei ", e);
				} catch (final WsOdsException e) {
					throw new DownloadException("problemi durante le operazioni di Store del certificazioni scaricate da sogei ", e);
				}
				
				persistAutocertificazioniSogei.storeCert(ricevutaDownloadSogeiDb);
				
			} else {
				ricevutaDownloadSogeiDb = null;
				LOG.warn("Si e' cercato di persitere una risposta nulla.");
			}
		}finally{
			LOG.debug("converti e salva download jaxb entity in jpa entity in {}.", System.currentTimeMillis() - start);
		}
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public long aggiornaRicevutaDownload() throws DownloadException {
		final List<String> listaTipiGestiti = Arrays.asList(new String[]{IRecuperoDownloadSogei.TIPO_INSERIMENTO, IRecuperoDownloadSogei.TIPO_VARIAMENTO, IRecuperoDownloadSogei.TIPO_VARIAZIONI_AL_MINUTO});
		IRicevutaDownloadSogei ricevutaDb =  ricevutaDownloadSogeiDao.getDaElaborare(IRicevutaDownloadSogei.STATO_ELABORAZIONE_IN_ELABORAZIONE, listaTipiGestiti);
		if(ricevutaDb == null){
			ricevutaDb =  ricevutaDownloadSogeiDao.getDaElaborare(IRicevutaDownloadSogei.STATO_ELABORAZIONE_INSERITO, listaTipiGestiti);
		}
		long count = 0L;
		if(ricevutaDb != null){
			
		
			final String ricevutaXml = ricevutaDb.getXmlDocument();
			final String lastProtocollo =  ricevutaDb.getProtocollo();
			final String tipoAutocertificazione = ricevutaDb.getTipoAutocertificazione();
			final String dataOraLimite =  ricevutaDb.getDataOraLimite();
			long indexAutocertificazioni = ricevutaDb.getNumeroRecordElaborati();
			final long idRicevuteDownload = ricevutaDb.getId();
			
			final Sorgente sorgente = converti(tipoAutocertificazione);
			final long dataOrdinamento = extractDataOrdinamento(dataOraLimite);
			// Questo Delta viene messo per evitare che le Variazioni Scaricate nello
			// stesso minuto delgli inserimenti vengano soppaiantati da questiUltimi.
			final long deltaOrdinamento = deltaDataOrdinamento(sorgente); 
			
			final it.toscana.regione.autocertificazionireddito.entity.jaxb.sogei.download.response.Ricevuta ricevuta = deserializza(ricevutaXml);
			
			final List<Autocertificazione> subLista = estraiSottolistaOrdinata(ricevuta, lastProtocollo);
			
			
			if(subLista!=null && ! subLista.isEmpty()){
				for(final Autocertificazione autocertificazione : subLista ){
					count++;
					final String protocollo = autocertificazione.getProtocollo();
					
					final IAutocertificazioneTmp autocertificazioneTmp;
					try {
						autocertificazioneTmp = Mapper.map(autocertificazione,sorgente);
					} catch (MapperException e) {
						throw new DownloadException("problemi durante la mappatura dell'autocertificazione su entity db IRicevutaDownloadSogei.id ["+idRicevuteDownload+"], protocollo autocerificazione["+protocollo+"] ", e);
					}
					if(autocertificazioneTmpDao.notExist(autocertificazioneTmp)){
						autocertificazioneTmp.setRicevutaIndex(indexAutocertificazioni);
						indexAutocertificazioni++;
						autocertificazioneTmp.setTipoAutocertificazione(tipoAutocertificazione);
						autocertificazioneTmp.setDataOrdinamento(new Timestamp(dataOrdinamento+deltaOrdinamento));
						autocertificazioneTmp.setFkRicevutaDownloadSogei(idRicevuteDownload);
						ricevutaDb.addAutocertificazioneTmp(autocertificazioneTmp);
					} else {
						LOG.debug("l'autocerttificazione con protocollo {}, e' ritenuta duplicata.",protocollo);
					}
					ricevutaDb.setProtocollo(protocollo);
				}
				ricevutaDb.setNumeroRecordElaborati(indexAutocertificazioni);
				ricevutaDb.setStatoElaborazione(IRicevutaDownloadSogei.STATO_ELABORAZIONE_IN_ELABORAZIONE);
			} else {
				LOG.debug("elaborate tutte le autocertificazioni dell ricevuta con id {}.",idRicevuteDownload);
				ricevutaDb.setStatoElaborazione(IRicevutaDownloadSogei.STATO_ELABORAZIONE_ELABORATO);
				ricevutaDb.setXmlDocument(null);
			}
			ricevutaDb.setDataAggiornamento(new Timestamp(System.currentTimeMillis()));
			
			persistAutocertificazioniSogei.store(ricevutaDb.getListAutocertificazioneTmp());
		}
		return count;
	}

	@Override @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public long aggiornaRicevutaDownloadCert() throws DownloadException {
		
		IRicevutaDownloadSogei ricevutaDb = ricevutaDownloadSogeiDao.getDaElaborare(IRicevutaDownloadSogei.STATO_ELABORAZIONE_IN_ELABORAZIONE, Arrays.asList(new String[]{IRecuperoDownloadSogei.TIPO_CERTIFICAZIONE}));
		if(ricevutaDb == null){
			ricevutaDb =  ricevutaDownloadSogeiDao.getDaElaborare(IRicevutaDownloadSogei.STATO_ELABORAZIONE_INSERITO, Arrays.asList(new String[]{IRecuperoDownloadSogei.TIPO_CERTIFICAZIONE}));
		} 
		
		long count = 0L;
		if(ricevutaDb != null)
		{
			final String ricevutaXml = ricevutaDb.getXmlDocument();
			final String lastProtocollo =  ricevutaDb.getProtocollo();
			final String tipoAutocertificazione = ricevutaDb.getTipoAutocertificazione();
			final String dataOraLimite =  ricevutaDb.getDataOraLimite();
			long indexCertificazioni = ricevutaDb.getNumeroRecordElaborati();
			final long idRicevuteDownload = ricevutaDb.getId();
			
			final Sorgente sorgente = converti(tipoAutocertificazione);
			final long dataOrdinamento = extractDataOrdinamentoCert(dataOraLimite);
			
			final it.toscana.regione.certificazionireddito.entity.jaxb.sogei.download.response.Ricevuta ricevuta = deserializzaCert(ricevutaXml);
			
			final List<Certificazione> subLista = estraiSottolistaOrdinataCert(ricevuta, lastProtocollo);
			
			if(subLista!=null && !subLista.isEmpty()){
				for(final Certificazione certificazione : subLista ){
					count++;
					String protocollo = generaProtocollo(certificazione);
					
					//alle volte il servizio di SOGEI se non trova certificazioni
					//inserisce nella risposta una certificazione con tutti i campi interni null(xsi:nil="true")
					//non le devo considerare
//			         <ns2:Listacertificazioni>
//			            <ns2:annoEsenzione xsi:nil="true" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"/>
//			            <ns2:codiceEsenzione xsi:nil="true" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"/>
//			            <ns2:cfSogEsente xsi:nil="true" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"/>
//			            <ns2:dataInizioValid xsi:nil="true" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"/>
//			            <ns2:dataFineValid xsi:nil="true" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"/>
//			            <ns2:dataFineValidOld xsi:nil="true" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"/>
//			         </ns2:Listacertificazioni>							
					if (certificazione != null
							&& (certificazione.getAnnoEsenzione() == null 
									&& certificazione.getCodiceEsenzione() == null 
									&& certificazione.getCfSogEsente() == null
									&& certificazione.getDataInizioValid() == null 
									&& certificazione.getDataFineValid() == null 
									&& certificazione.getDataFineValidOld() == null)) {
						
						ricevutaDb.setProtocollo(protocollo);
						continue;
					}
					
					final ICertificazioneTmp certificazioneTmp;
					try {
						certificazioneTmp = Mapper.map(certificazione,sorgente);
					} catch (MapperException e) {
						throw new DownloadException("problemi durante la mappatura della certificazione su entity db IRicevutaDownloadSogei.id ["+idRicevuteDownload+"], protocollo certificazione["+protocollo+"] ", e);
					}
					if(certificazioneTmpDao.notExist(certificazioneTmp)){
						certificazioneTmp.setRicevutaIndex(indexCertificazioni);
						indexCertificazioni++;
						certificazioneTmp.setDataOrdinamento(new Timestamp(dataOrdinamento));
						certificazioneTmp.setFkRicevutaDownloadSogei(idRicevuteDownload);
						ricevutaDb.addCertificazioneTmp(certificazioneTmp);
					} else {
						LOG.debug("la certificazione con protocollo {}, e' ritenuta duplicata.",protocollo);
					}
					ricevutaDb.setProtocollo(protocollo);
				}
				ricevutaDb.setNumeroRecordElaborati(indexCertificazioni);
				ricevutaDb.setStatoElaborazione(IRicevutaDownloadSogei.STATO_ELABORAZIONE_IN_ELABORAZIONE);
			} else {
				LOG.debug("elaborate tutte le certificazioni della ricevuta con id {}.",idRicevuteDownload);
				ricevutaDb.setStatoElaborazione(IRicevutaDownloadSogei.STATO_ELABORAZIONE_ELABORATO);
				ricevutaDb.setXmlDocument(null);
			}
			ricevutaDb.setDataAggiornamento(new Timestamp(System.currentTimeMillis()));
			
			persistAutocertificazioniSogei.storeCert(ricevutaDb.getListCertificazioneTmp());
		}
		return count;
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public it.toscana.regione.autocertificazionireddito.entity.jaxb.sogei.download.response.Ricevuta deserializza(final String ricevutaXml) {
		if(ricevutaXml == null||ricevutaXml.trim().length()==0 ){
			throw new DownloadException(Code.DATO_MANCANTE,"ricevutaXml Nullo o vuoto ");
		}
		
		final InputStream xmlInputStream = new ByteArrayInputStream(ricevutaXml.getBytes());
	
		final Node ricevutaNode = XMLTool.loadDocument(xmlInputStream, false, true);

		if(ricevutaNode == null){
			throw new DownloadException(Code.FORMATO,"impossibile ottenere il document relativo al campo ricevutaXml sulla tabella IRicevutaDownloadSogei ["+ricevutaXml+"]");
		}
		
		final JAXBElement<it.toscana.regione.autocertificazionireddito.entity.jaxb.sogei.download.response.Ricevuta> ricevutaJaxb = transformRicevuta.transform(ricevutaNode);

		if(ricevutaJaxb == null){
			throw new DownloadException(Code.FORMATO,"impossibile ottenere le entity jaxb dal campo ricevutaXml sulla tabella IRicevutaDownloadSogei ["+ricevutaXml+"]");
		}
		final it.toscana.regione.autocertificazionireddito.entity.jaxb.sogei.download.response.Ricevuta ricevuta =  ricevutaJaxb.getValue();
		return ricevuta;
	}

	@Override @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public it.toscana.regione.certificazionireddito.entity.jaxb.sogei.download.response.Ricevuta deserializzaCert(final String ricevutaXml) {
		if(ricevutaXml == null||ricevutaXml.trim().length()==0 ){
			throw new DownloadException(Code.DATO_MANCANTE,"ricevutaXml Nullo o vuoto ");
		}
		
		final InputStream xmlInputStream = new ByteArrayInputStream(ricevutaXml.getBytes());
	
		final Node ricevutaNode = XMLTool.loadDocument(xmlInputStream, false, true);

		if(ricevutaNode == null){
			throw new DownloadException(Code.FORMATO,"impossibile ottenere il document relativo al campo ricevutaXml sulla tabella IRicevutaDownloadSogei ["+ricevutaXml+"]");
		}
		
		final JAXBElement<it.toscana.regione.certificazionireddito.entity.jaxb.sogei.download.response.Ricevuta> ricevutaJaxb = transformRicevutaCert.transform(ricevutaNode);

		if(ricevutaJaxb == null){
			throw new DownloadException(Code.FORMATO,"impossibile ottenere le entity jaxb dal campo ricevutaXml sulla tabella IRicevutaDownloadSogei ["+ricevutaXml+"]");
		}
		final it.toscana.regione.certificazionireddito.entity.jaxb.sogei.download.response.Ricevuta ricevuta =  ricevutaJaxb.getValue();
		return ricevuta;
	}
	
	private static final  List<Autocertificazione> estraiSottolistaOrdinata(final it.toscana.regione.autocertificazionireddito.entity.jaxb.sogei.download.response.Ricevuta ricevuta , final String lastProtocollo) {
		final long start = System.currentTimeMillis();
		try{
		final List<Autocertificazione> lista = ricevuta.getListaautocertificazioni();
		ordinaPerProtocollo(lista);
		final List<Autocertificazione> subLista = selectSubList(lista, lastProtocollo, Conf.getMassimaFinestraElaborazioneAutocertificazioni());
		return subLista;
		} finally {
			LOG.debug("[PERFORMANCE] - estratta la sottolista ordinata da elaborare {}.", System.currentTimeMillis() - start);
		}
		
	}
	
	private static final  List<Certificazione> estraiSottolistaOrdinataCert(final it.toscana.regione.certificazionireddito.entity.jaxb.sogei.download.response.Ricevuta ricevuta , final String lastProtocollo) {
		final long start = System.currentTimeMillis();
		try{
			final List<Certificazione> lista = ricevuta.getListacertificazioni();
			ordinaPerProtocolloCert(lista);
			final List<Certificazione> subLista = selectSubListCert(lista, lastProtocollo, Conf.getMassimaFinestraElaborazioneCertificazioni());
			return subLista;
		} finally {
			LOG.debug("[PERFORMANCE] - estratta la sottolista ordinata da elaborare {}.", System.currentTimeMillis() - start);
		}
	}

	private static long extractDataOrdinamento(final String dataOraLimite) {
		final long dataOrdinamento;
		try {
			dataOrdinamento = Convertitore.convertTime(dataOraLimite, Varie.FORMATO_DATA_ORA_LIMITE_SOGEI);
		} catch (ConvertitoreException e) {
			throw new DownloadException("Impossibile ottenere la data ordinamento",e);
		}
		return dataOrdinamento;
	}
	
	private static long extractDataOrdinamentoCert(String dataOraLimite) {
		final long dataOrdinamento;
		try {
			dataOrdinamento = Convertitore.convertTime((dataOraLimite.trim()+"-23:59:59"), Varie.FORMATO_TIME_SOGEI);
		} 
		catch (ConvertitoreException e) {
			throw new DownloadException("Impossibile ottenere la data ordinamento",e);
		} 
		return dataOrdinamento;
	}
	
	private static final Sorgente converti(final String tipoAutocertificazione){
		if(tipoAutocertificazione==null) {
			throw new DownloadException(Code.DATO_MANCANTE,"tipo Autocertifcazione Nullo ");
		}
		final Sorgente sorgente;
		if(tipoAutocertificazione.equals(IRecuperoDownloadSogei.TIPO_INSERIMENTO)){
			sorgente = Sorgente.SOGEI_I;
		} else if(tipoAutocertificazione.equals(IRecuperoDownloadSogei.TIPO_VARIAMENTO)){
			sorgente = Sorgente.SOGEI_V;
		} else if(tipoAutocertificazione.equals(IRecuperoDownloadSogei.TIPO_VARIAZIONI_AL_MINUTO)){
			sorgente = Sorgente.SOGEI_V;
		} else if(tipoAutocertificazione.equals(IRecuperoDownloadSogei.TIPO_CERTIFICAZIONE)){
			sorgente = Sorgente.SOGEI_C;
		} else {
			sorgente = Sorgente.ALTRO;
		} 
		return sorgente;
	}
	
	private static final long deltaDataOrdinamento(final Sorgente sorgente){
		final long deltaOrdinamento; // Questo Delta viene messo per evitare che le Variazioni Scaricate nello stesso minuto delgli inserimenti vengano soppaiantati da questiUltimi.
		if(Sorgente.SOGEI_I.equals(sorgente)){
			deltaOrdinamento = 0L;
		} else if(Sorgente.SOGEI_V.equals(sorgente)){
			deltaOrdinamento = Varie.TIME_SECONDO*59L;
		} else if(Sorgente.SOGEI_V.equals(sorgente)){
			deltaOrdinamento = Varie.TIME_SECONDO*59L;
		} else {
			deltaOrdinamento = 0L;
		}
		return deltaOrdinamento;
	}
	
	private static final void ordinaPerProtocollo(final List<Autocertificazione> lista){
		Collections.sort(lista, new Comparator<Autocertificazione>() {
			  @Override
			  public int compare(Autocertificazione a1, Autocertificazione a2) {
			    return a1.getProtocollo().compareTo(a2.getProtocollo());
			  }
			});
	}
	
	private static final void ordinaPerProtocolloCert(final List<Certificazione> lista){
		Collections.sort(lista, new Comparator<Certificazione>() {
			  @Override
			  public int compare(Certificazione a1, Certificazione a2) {
			    return generaProtocollo(a1).compareTo(generaProtocollo(a2));
			  }
			});
	}

	private static final List<Certificazione> selectSubListCert(final List<Certificazione> lista, final String min, final int sizeWindow) 
	{
		final List<Certificazione> nuova = new ArrayList<Certificazione>();
		int size = 0;
		String nuovoProtocollo =  null;
		String ultimoProtocollo = null;
		for (final Certificazione certificazione : lista) {
			nuovoProtocollo = generaProtocollo(certificazione);
			if (size < sizeWindow && (min == null || nuovoProtocollo.compareTo(min) > 0)) {
				nuova.add(certificazione);
				size++;
				ultimoProtocollo = nuovoProtocollo;
			} else if (!(size < sizeWindow) && nuovoProtocollo.equals(ultimoProtocollo)) { /* garantisce di non perdere protocolli duplicati */
				nuova.add(certificazione);
				size++;
			}
		}
		return nuova;
	}

	private static String generaProtocollo(final Certificazione certificazione) {
		return (certificazione==null || (certificazione.getAnnoEsenzione() == null 
				&& certificazione.getCodiceEsenzione() == null 
				&& certificazione.getCfSogEsente() == null
				&& certificazione.getDataInizioValid() == null 
				&& certificazione.getDataFineValid() == null 
				&& certificazione.getDataFineValidOld() == null))?"":(certificazione.getCfSogEsente() + certificazione.getCodiceEsenzione().substring(certificazione.getCodiceEsenzione().length()-2));
	}
	
	private static final List<Autocertificazione> selectSubList(final List<Autocertificazione> lista, final String min, final int sizeWindow) {
		final List<Autocertificazione> nuova = new ArrayList<Autocertificazione>();
		int size = 0;

		for(final Autocertificazione autocertificazione : lista ){
			if(size< sizeWindow && (min==null || autocertificazione.getProtocollo()==null || autocertificazione.getProtocollo().compareTo(min)>0)){
				nuova.add(autocertificazione);
				size++;
			}
		}
		return nuova;
	}
	
	/*Modificata gestione timer variazioni per far in modo che il download sia al minuto sulla giornata corrente*/
	//Il recupero downlaod variazioni from sogei passerà il parametro A e quindi andrà a finire nell'else finale 
	//per fare in modo che l'incremento sia al minuto nella giornata corrente
	private final static long newDataLimite(final long lastDataLimite, final String tipoRichiesta) throws ConvertitoreException {
		
		final long incremento;
		if(IRecuperoDownloadSogei.TIPO_VARIAMENTO.equalsIgnoreCase(tipoRichiesta)){
			final Calendar time = new GregorianCalendar();
			time.setTimeInMillis(lastDataLimite);
			if(time.get(Calendar.HOUR_OF_DAY) == 23 && time.get(Calendar.MINUTE) == 59 ){
				time.add(Calendar.DAY_OF_MONTH, 1);
			}
			time.set(Calendar.HOUR_OF_DAY, 23);
			time.set(Calendar.MINUTE, 59);
			time.set(Calendar.SECOND, 00);
			time.set(Calendar.MILLISECOND, 000);
			incremento = time.getTimeInMillis() - lastDataLimite;
		} else if (IRecuperoDownloadSogei.TIPO_CERTIFICAZIONE.equalsIgnoreCase(tipoRichiesta)) {
			final Calendar time = new GregorianCalendar();
			time.setTimeInMillis(lastDataLimite);
			time.add(Calendar.DAY_OF_MONTH, 1);
			time.set(Calendar.HOUR_OF_DAY, 23);
			time.set(Calendar.MINUTE, 59);
			time.set(Calendar.SECOND, 00);
			time.set(Calendar.MILLISECOND, 000);
			incremento = time.getTimeInMillis() - lastDataLimite;
		} else {
			incremento = Varie.TIME_MINUTO;
		}
		
		
		return lastDataLimite+incremento;
	}
}

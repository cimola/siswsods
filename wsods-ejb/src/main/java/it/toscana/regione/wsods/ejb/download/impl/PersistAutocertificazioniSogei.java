/** */
package it.toscana.regione.wsods.ejb.download.impl;

import it.toscana.regione.wsods.ejb.dao.api.IAutocertificazioneTmpDao;
import it.toscana.regione.wsods.ejb.dao.api.ICertificazioneTmpDao;
import it.toscana.regione.wsods.ejb.dao.api.IDao;
import it.toscana.regione.wsods.ejb.dao.api.IRicevutaDownloadSogeiDao;
import it.toscana.regione.wsods.ejb.download.api.IPersistAutocertificazioniSogei;
import it.toscana.regione.wsods.entity.jpa.api.IAutocertificazioneTmp;
import it.toscana.regione.wsods.entity.jpa.api.ICertificazioneTmp;
import it.toscana.regione.wsods.entity.jpa.api.IRicevutaDownloadSogei;
import it.toscana.regione.wsods.exception.DownloadException;
import it.toscana.regione.wsods.exception.WsOdsException;
import it.toscana.regione.wsods.exception.WsOdsRuntimeException;
import it.toscana.regione.wsods.lib.Mapper;

import java.util.List;

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
@Stateless(name = IPersistAutocertificazioniSogei.EJB_REF)
public class PersistAutocertificazioniSogei implements IPersistAutocertificazioniSogei {
	
	private static final Logger LOG = LoggerFactory.getLogger(PersistAutocertificazioniSogei.class);
	
	@EJB(beanName = IDao.EJB_REF_RICEVUTA_DOWNLOAD_SOGEI)
	private IRicevutaDownloadSogeiDao ricevutaDownloadSogeiDao;


	@EJB(beanName = IDao.EJB_REF_AUTOCERTIFICAZIONE_TMP)
	private IAutocertificazioneTmpDao autocertificazioneTmpDao;

	@EJB(beanName = IDao.EJB_REF_CERTIFICAZIONE_TMP)
	private ICertificazioneTmpDao certificazioneTmpDao;
	
	
	/**
	 * 
	 */
	public PersistAutocertificazioniSogei() {}
	
	
	/** {@inheritDoc} */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void store(final it.toscana.regione.autocertificazionireddito.entity.jaxb.sogei.download.response.Ricevuta ricevutaDownloadSogei, final String tipologiaRicevuta, final String xml) throws DownloadException {
		final long start = System.currentTimeMillis();
		try {
			if (ricevutaDownloadSogei != null) {
				try {
					final IRicevutaDownloadSogei ricevutaDownloadSogeiDb = Mapper.map(ricevutaDownloadSogei,tipologiaRicevuta);
					
					ricevutaDownloadSogeiDb.setXmlDocument(xml);
					ricevutaDownloadSogeiDb.setStatoElaborazione(IRicevutaDownloadSogei.STATO_ELABORAZIONE_INSERITO);
					ricevutaDownloadSogeiDb.setProtocollo(null);
					
					ricevutaDownloadSogeiDao.insert(ricevutaDownloadSogeiDb, false);

				} catch (final WsOdsRuntimeException e) {
					throw new DownloadException("problemi durante le operazioni di Store del autocertificazioni scaricate da sogei ", e);
				} catch (final WsOdsException e) {
					throw new DownloadException("problemi durante le operazioni di Store del autocertificazioni scaricate da sogei ", e);
				}
				
			} else {
				LOG.warn("Si e' cercato di persitere una risposta nulla.");
			}
		} finally{
			LOG.debug("[PERFORMANCE] - invio richiesta di download in {}.", System.currentTimeMillis() - start);
		}
	}

	/** {@inheritDoc} */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void storeCert(final it.toscana.regione.certificazionireddito.entity.jaxb.sogei.download.response.Ricevuta ricevutaDownloadSogeiCert, final String tipologiaRicevuta, final String xml) throws DownloadException {
		final long start = System.currentTimeMillis();
		try {
			if (ricevutaDownloadSogeiCert != null) {
				try {
					final IRicevutaDownloadSogei ricevutaDownloadSogeiDb = Mapper.mapCert(ricevutaDownloadSogeiCert,tipologiaRicevuta);
					
					ricevutaDownloadSogeiDb.setXmlDocument(xml);
					ricevutaDownloadSogeiDb.setStatoElaborazione(IRicevutaDownloadSogei.STATO_ELABORAZIONE_INSERITO);
					ricevutaDownloadSogeiDb.setProtocollo(null);
					
					ricevutaDownloadSogeiDao.insert(ricevutaDownloadSogeiDb, false);

				} catch (final WsOdsRuntimeException e) {
					throw new DownloadException("problemi durante le operazioni di Store del certificazioni scaricate da sogei ", e);
				} catch (final WsOdsException e) {
					throw new DownloadException("problemi durante le operazioni di Store del certificazioni scaricate da sogei ", e);
				}
				
			} else {
				LOG.warn("Si e' cercato di persitere una risposta nulla.");
			}
		} finally{
			LOG.debug("[PERFORMANCE] - invio richiesta di download in {}.", System.currentTimeMillis() - start);
		}
	}

	/** {@inheritDoc} */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void store(final IRicevutaDownloadSogei ricevutaDownloadSogeiDb) throws DownloadException {
		final long start = System.currentTimeMillis();
		try {
			if(ricevutaDownloadSogeiDb != null){
				
				ricevutaDownloadSogeiDb.setXmlDocument(null);
				ricevutaDownloadSogeiDb.setStatoElaborazione(IRicevutaDownloadSogei.STATO_ELABORAZIONE_ELABORATO);
				ricevutaDownloadSogeiDb.setProtocollo(null);
				
				ricevutaDownloadSogeiDao.insert(ricevutaDownloadSogeiDb, false);
				final List<IAutocertificazioneTmp> listaAutocertificazione = ricevutaDownloadSogeiDb.getListAutocertificazioneTmp();
				if (listaAutocertificazione != null && listaAutocertificazione.size() > 0) {
					for (final IAutocertificazioneTmp autocertificazioneTmp : listaAutocertificazione) {
						autocertificazioneTmp.setFkRicevutaDownloadSogei(ricevutaDownloadSogeiDb.getId());
						autocertificazioneTmpDao.insert(autocertificazioneTmp, false);
					}
				}
				ricevutaDownloadSogeiDao.flush();
			} else {
				LOG.warn("Si e' cercato di persitere una risposta nulla.");
			}
		} finally{
			LOG.debug("[PERFORMANCE] - store download in {}.", System.currentTimeMillis() - start);
		}
	}


	/** {@inheritDoc} */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void storeCert(final IRicevutaDownloadSogei ricevutaDownloadSogeiDb) throws DownloadException {
		final long start = System.currentTimeMillis();
		try {
			if(ricevutaDownloadSogeiDb != null){
				
				ricevutaDownloadSogeiDb.setXmlDocument(null);
				ricevutaDownloadSogeiDb.setStatoElaborazione(IRicevutaDownloadSogei.STATO_ELABORAZIONE_ELABORATO);
				ricevutaDownloadSogeiDb.setProtocollo(null);
				
				ricevutaDownloadSogeiDao.insert(ricevutaDownloadSogeiDb, false);
				final List<ICertificazioneTmp> listaCertificazione = ricevutaDownloadSogeiDb.getListCertificazioneTmp();
				if (listaCertificazione != null && listaCertificazione.size() > 0) {
					for (final ICertificazioneTmp certificazioneTmp : listaCertificazione) {
						certificazioneTmp.setFkRicevutaDownloadSogei(ricevutaDownloadSogeiDb.getId());
						certificazioneTmpDao.insert(certificazioneTmp, false);
					}
				}
				ricevutaDownloadSogeiDao.flush();
			} else {
				LOG.warn("Si e' cercato di persitere una risposta nulla.");
			}
		} finally{
			LOG.debug("[PERFORMANCE] - store download in {}.", System.currentTimeMillis() - start);
		}
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.MANDATORY)
	public void store(List<IAutocertificazioneTmp> lista) throws DownloadException {
		if (lista != null && lista.size() > 0) {
			for (final IAutocertificazioneTmp autocertificazioneTmp : lista) {
				autocertificazioneTmpDao.insert(autocertificazioneTmp, false);
			}
		}
		autocertificazioneTmpDao.flush();
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.MANDATORY)
	public void storeCert(List<ICertificazioneTmp> lista) throws DownloadException {
		if (lista != null && lista.size() > 0) {
			for (final ICertificazioneTmp certificazioneTmp : lista) {
				certificazioneTmpDao.insert(certificazioneTmp, false);
			}
		}
		certificazioneTmpDao.flush();
	}
	
}

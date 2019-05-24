/** */
package it.toscana.regione.wsods.ejb.rielaboratore.impl;

import it.toscana.regione.wsods.ejb.dao.api.IAutocertificazioneScartateDao;
import it.toscana.regione.wsods.ejb.dao.api.IAutocertificazioneTmpDao;
import it.toscana.regione.wsods.ejb.dao.api.ICertificazioneScartateDao;
import it.toscana.regione.wsods.ejb.dao.api.ICertificazioneTmpDao;
import it.toscana.regione.wsods.ejb.dao.api.IDownloadAutocertificazioneDao;
import it.toscana.regione.wsods.ejb.dao.api.IDownloadCertificazioneDao;
import it.toscana.regione.wsods.ejb.dao.api.IEsenzioniFasceDao;
import it.toscana.regione.wsods.ejb.dao.api.IRicevutaDownloadSogeiDao;
import it.toscana.regione.wsods.ejb.rielaboratore.api.ITrasformaRielaboratore;
import it.toscana.regione.wsods.ejb.sdm.api.ISdm;
import it.toscana.regione.wsods.entity.bean.api.ISoggetto;
import it.toscana.regione.wsods.entity.jpa.api.IAutocertificazioneTmp;
import it.toscana.regione.wsods.entity.jpa.api.ICertificazioneTmp;
import it.toscana.regione.wsods.entity.jpa.api.IDownloadAutocertificazione;
import it.toscana.regione.wsods.entity.jpa.api.IDownloadCertificazione;
import it.toscana.regione.wsods.entity.jpa.api.IEsenzioniFasce;
import it.toscana.regione.wsods.exception.ConvertitoreException;
import it.toscana.regione.wsods.exception.DaoException;
import it.toscana.regione.wsods.exception.MapperException;
import it.toscana.regione.wsods.exception.RielaboratoreException;
import it.toscana.regione.wsods.exception.SisDataManagerException;
import it.toscana.regione.wsods.lib.Mapper;
import it.toscana.regione.wsods.lib.Util;
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
@Stateless(name = ITrasformaRielaboratore.EJB_REF)
public class TrasformaRielaboratore implements ITrasformaRielaboratore {
	
	private final Logger LOG = LoggerFactory.getLogger(TrasformaRielaboratore.class);
	
	@EJB(beanName = IRicevutaDownloadSogeiDao.EJB_REF)
	private IRicevutaDownloadSogeiDao ricevutaDownloadSogeiDao;

	@EJB(beanName = ISdm.EJB_REF)
	private ISdm sdm;
	
	@EJB(beanName = IDownloadAutocertificazioneDao.EJB_REF)
	private IDownloadAutocertificazioneDao downloadAutocertificazioneDao;
	
	@EJB(beanName = IDownloadCertificazioneDao.EJB_REF)
	private IDownloadCertificazioneDao downloadCertificazioneDao;
	
	@EJB(beanName = IEsenzioniFasceDao.EJB_REF)
	private IEsenzioniFasceDao esenzioniFasceDao;

	@EJB(beanName = IAutocertificazioneTmpDao.EJB_REF)
	private IAutocertificazioneTmpDao autocertificazioneTmpDao;

	@EJB(beanName = ICertificazioneTmpDao.EJB_REF)
	private ICertificazioneTmpDao certificazioneTmpDao;
	
	@EJB(beanName = IAutocertificazioneScartateDao.EJB_REF)
	private IAutocertificazioneScartateDao autocertificazioneScartateDao;
	
	@EJB(beanName = ICertificazioneScartateDao.EJB_REF)
	private ICertificazioneScartateDao certificazioneScartateDao;
	
	/**
	 * 
	 */
	public TrasformaRielaboratore() {super();}
	
	
	/** {@inheritDoc} */
	@Override @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void elabora(final IAutocertificazioneTmp autocertificazioneTmp) throws RielaboratoreException {
		if(autocertificazioneTmp.getNumeroElaborazioni()>=Conf.getNumeroMassimoDiRielaborazioni()){
			LOG.info("Sacrto il record con id ["+autocertificazioneTmp.getId()+"]");
			spostaSuAutocertificazioniScartate(autocertificazioneTmp); 
		
		} else {
			LOG.debug("vado ad elaborare il record con id ["+autocertificazioneTmp.getId()+"]");
			final IEsenzioniFasce esenzioniFasce = convertiAutocertificazioneInEsenzioniFasce(autocertificazioneTmp);
			
			final boolean insertEnabled = isInsertEnabled(esenzioniFasce);
			if(!insertEnabled){
				LOG.info("il record con id ["+autocertificazioneTmp.getId()+"] e' considerato un duplicato, di conseguenza lo elimino sensa inserirlo.");
			}
			inserisciAutocertificazione(autocertificazioneTmp, esenzioniFasce, insertEnabled);
			
			rimuoviEIncrementaElaborazione(autocertificazioneTmp, insertEnabled);
			
		}
	}

	/** {@inheritDoc} */
	@Override @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void elabora(final ICertificazioneTmp certificazioneTmp) throws RielaboratoreException {
		if(certificazioneTmp.getNumeroElaborazioni()>=Conf.getNumeroMassimoDiRielaborazioniCert()){
			LOG.info("Scarto il record con id ["+certificazioneTmp.getId()+"]");
			spostaSuCertificazioniScartate(certificazioneTmp); 
		
		} else {
			LOG.debug("vado ad elaborare il record con id ["+certificazioneTmp.getId()+"]");
			final IEsenzioniFasce esenzioniFasce = convertiCertificazioneInEsenzioniFasce(certificazioneTmp);
			
			final boolean insertEnabled = isInsertEnabled(esenzioniFasce);
			if(!insertEnabled){
				LOG.info("il record con id ["+certificazioneTmp.getId()+"] e' considerato un duplicato, di conseguenza lo elimino sensa inserirlo.");
			}
			inserisciCertificazione(certificazioneTmp, esenzioniFasce, insertEnabled);
			
			rimuoviEIncrementaElaborazione(certificazioneTmp, insertEnabled);
			
		}
	}

	private void rimuoviEIncrementaElaborazione(final IAutocertificazioneTmp autocertificazioneTmp, final boolean insertEnabled) throws RielaboratoreException {
		final long fkRicevutaDownloadSogei = autocertificazioneTmp.getFkRicevutaDownloadSogei();
		try {
			autocertificazioneTmpDao.remove(autocertificazioneTmp);
		} catch (DaoException e) {
			throw new  RielaboratoreException("impossibile rimuovere il record sulla tabella autocertificazioneTmp",e);
		} 
		
		ricevutaDownloadSogeiDao.aggiornaElaborati(fkRicevutaDownloadSogei, insertEnabled);
	}
	
	private void rimuoviEIncrementaElaborazione(final ICertificazioneTmp certificazioneTmp, final boolean insertEnabled) throws RielaboratoreException {
		final long fkRicevutaDownloadSogei = certificazioneTmp.getFkRicevutaDownloadSogei();
		try {
			certificazioneTmpDao.remove(certificazioneTmp);
		} catch (DaoException e) {
			throw new  RielaboratoreException("impossibile rimuovere il record sulla tabella certificazioneTmp",e);
		} 
		
		ricevutaDownloadSogeiDao.aggiornaElaborati(fkRicevutaDownloadSogei, insertEnabled);
	}
	
	private void inserisciAutocertificazione(final IAutocertificazioneTmp autocertificazioneTmp, final IEsenzioniFasce esenzioniFasce, final boolean insertEnabled) throws RielaboratoreException {
		if(insertEnabled){
			esenzioniFasceDao.insert(esenzioniFasce, false);
			
			final IDownloadAutocertificazione downloadAutocertificazione;
			try {
				downloadAutocertificazione = Mapper.map(autocertificazioneTmp.getFkRicevutaDownloadSogei(), esenzioniFasce.getId(), autocertificazioneTmp);
			} catch (ConvertitoreException e) {
				throw new  RielaboratoreException("impossibile mappare autocertificazioneTmp su downloadAutocertificazione",e);
			}
			downloadAutocertificazioneDao.insert(downloadAutocertificazione, false);
		}
	}

	private void inserisciCertificazione(final ICertificazioneTmp certificazioneTmp, final IEsenzioniFasce esenzioniFasce, final boolean insertEnabled) throws RielaboratoreException {
		if(insertEnabled){
			esenzioniFasceDao.insert(esenzioniFasce, false);
			
			final IDownloadCertificazione downloadCertificazione;
			try {
				downloadCertificazione = Mapper.map(certificazioneTmp.getFkRicevutaDownloadSogei(), esenzioniFasce.getId(), certificazioneTmp);
			} catch (ConvertitoreException e) {
				throw new  RielaboratoreException("impossibile mappare certificazioneTmp su downloadCertificazione",e);
			}
			downloadCertificazioneDao.insert(downloadCertificazione, false);
		}
	}
	
	private boolean isInsertEnabled(final IEsenzioniFasce esenzioniFasce) {
		final boolean insertEnabled = esenzioniFasceDao.notExist(esenzioniFasce);
		
		return insertEnabled;
	}
	
	private IEsenzioniFasce convertiAutocertificazioneInEsenzioniFasce(final IAutocertificazioneTmp autocertificazioneTmp) throws RielaboratoreException {
		final IEsenzioniFasce esenzioniFasce;
		try {
			esenzioniFasce = Mapper.map(autocertificazioneTmp);
		} catch (MapperException e) {
			throw new  RielaboratoreException("impossibile mappare autocertificazioneTmp su esenzioniFasce",e);
		}
		final ISoggetto dichiarante;
		final ISoggetto titolare;
		final ISoggetto beneficiario;
		
		
		// verifico presenza cf soggetti
		if (autocertificazioneTmp.getCfDichiarante() == null || autocertificazioneTmp.getCfDichiarante().trim().length() == 0) { throw new RielaboratoreException(Code.SDM_SOGGETTO_NON_TROVATO_O_CON_TRATTI_PARZIALI_O_DATI_INPUT_MANCANTI,"cf dichiarante nullo"); }
		if (autocertificazioneTmp.getCfTitolare() == null || autocertificazioneTmp.getCfTitolare().trim().length() == 0) { throw new RielaboratoreException(Code.SDM_SOGGETTO_NON_TROVATO_O_CON_TRATTI_PARZIALI_O_DATI_INPUT_MANCANTI,"cf titolare nullo"); }
		if (autocertificazioneTmp.getCfBeneficiario() == null || autocertificazioneTmp.getCfBeneficiario().trim().length() == 0) { throw new RielaboratoreException(Code.SDM_SOGGETTO_NON_TROVATO_O_CON_TRATTI_PARZIALI_O_DATI_INPUT_MANCANTI,"cf beneficiario nullo"); }
		
		
		// ricerco soggetti in anagrafe
		try{
			
			dichiarante = sdm.getFromCodiceFiscale(autocertificazioneTmp.getCfDichiarante());
			titolare = sdm.getFromCodiceFiscale(autocertificazioneTmp.getCfTitolare());
			beneficiario = sdm.getFromCodiceFiscale(autocertificazioneTmp.getCfBeneficiario());
			
		} catch(SisDataManagerException e){
			if (Code.SDM_SERVIZIO_NON_DISPONIBILE.equals(e.code) || Code.SDM_SERVIZIO_RESTITUISCE_ERRORE.equals(e.code)) {
				throw new RielaboratoreException(e);
			}
			throw new RielaboratoreException(Code.SDM_SOGGETTO_NON_TROVATO_O_CON_TRATTI_PARZIALI_O_DATI_INPUT_MANCANTI, e);
		}

		if (dichiarante == null) { throw new RielaboratoreException(Code.SDM_SOGGETTO_NON_TROVATO_O_CON_TRATTI_PARZIALI_O_DATI_INPUT_MANCANTI); }
		if (titolare == null) { throw new RielaboratoreException(Code.SDM_SOGGETTO_NON_TROVATO_O_CON_TRATTI_PARZIALI_O_DATI_INPUT_MANCANTI); }
		if (beneficiario == null) { throw new RielaboratoreException(Code.SDM_SOGGETTO_NON_TROVATO_O_CON_TRATTI_PARZIALI_O_DATI_INPUT_MANCANTI); }
		
		esenzioniFasce.setIdUniDichiarante(dichiarante.getIdUni());
		esenzioniFasce.setIdUniTitolare(titolare.getIdUni());
		esenzioniFasce.setIdUniversaleAssistito(beneficiario.getIdUni());
		
		return esenzioniFasce;
	}

	private IEsenzioniFasce convertiCertificazioneInEsenzioniFasce(final ICertificazioneTmp certificazioneTmp) throws RielaboratoreException {
		final IEsenzioniFasce esenzioniFasce;
		try {
			esenzioniFasce = Mapper.map(certificazioneTmp);
		} catch (MapperException e) {
			throw new  RielaboratoreException("impossibile mappare certificazioneTmp su esenzioniFasce",e);
		}
		final ISoggetto soggesente;
		
		if (certificazioneTmp.getCfSogEsente() == null || certificazioneTmp.getCfSogEsente().trim().length() == 0) { throw new RielaboratoreException(Code.SDM_SOGGETTO_NON_TROVATO_O_CON_TRATTI_PARZIALI_O_DATI_INPUT_MANCANTI,"cf soggetto esente nullo"); }
		
		try{
			
			soggesente = sdm.getFromCodiceFiscale(certificazioneTmp.getCfSogEsente());
			
		} catch(SisDataManagerException e){
			if (Code.SDM_SERVIZIO_NON_DISPONIBILE.equals(e.code) || Code.SDM_SERVIZIO_RESTITUISCE_ERRORE.equals(e.code)) {
				throw new RielaboratoreException(e);
			}
			throw new RielaboratoreException(Code.SDM_SOGGETTO_NON_TROVATO_O_CON_TRATTI_PARZIALI_O_DATI_INPUT_MANCANTI, e);
		}

		if (soggesente == null) { throw new RielaboratoreException(Code.SDM_SOGGETTO_NON_TROVATO_O_CON_TRATTI_PARZIALI_O_DATI_INPUT_MANCANTI); }
		
		esenzioniFasce.setIdUniDichiarante(Util.createEmptyString(2));
		esenzioniFasce.setIdUniTitolare(Util.createEmptyString(2));
		esenzioniFasce.setIdUniversaleAssistito(soggesente.getIdUni());
		
		return esenzioniFasce;
	}
	
	private void spostaSuAutocertificazioniScartate(final IAutocertificazioneTmp autocertificazioneTmp) throws RielaboratoreException {
		try {
			autocertificazioneScartateDao.insert(Mapper.copyTo(autocertificazioneTmp), false);
		} catch (DaoException e) {
			throw new  RielaboratoreException("impossibile inserire il record sulla tabella autocertificazioneScartate",e);
		} catch (MapperException e) {
			throw new  RielaboratoreException("impossibile mappare autocertificazioneTmp su autocertificazioneScartate",e);
		}
		try {
			autocertificazioneTmpDao.remove(autocertificazioneTmp);
		} catch (DaoException e) {
			throw new  RielaboratoreException("impossibile rimuovere il record sulla tabella autocertificazioneTmp",e);
		}
	}

	private void spostaSuCertificazioniScartate(final ICertificazioneTmp certificazioneTmp) throws RielaboratoreException {
		try {
			certificazioneScartateDao.insert(Mapper.copyTo(certificazioneTmp), false);
		} catch (DaoException e) {
			throw new  RielaboratoreException("impossibile inserire il record sulla tabella certificazioneScartate",e);
		} catch (MapperException e) {
			throw new  RielaboratoreException("impossibile mappare certificazioneTmp su certificazioneScartate",e);
		}
		try {
			certificazioneTmpDao.remove(certificazioneTmp);
		} catch (DaoException e) {
			throw new  RielaboratoreException("impossibile rimuovere il record sulla tabella certificazioneTmp",e);
		}
	}
}

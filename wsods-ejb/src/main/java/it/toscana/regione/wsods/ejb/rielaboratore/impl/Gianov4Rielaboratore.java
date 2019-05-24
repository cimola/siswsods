/** */
package it.toscana.regione.wsods.ejb.rielaboratore.impl;

import it.toscana.regione.wsods.ejb.dao.api.IAutocertificazioneScartateDao;
import it.toscana.regione.wsods.ejb.dao.api.ICertificazioneScartateDao;
import it.toscana.regione.wsods.ejb.dao.api.IRCTCodBelfComuniDao;
import it.toscana.regione.wsods.ejb.dao.api.IRCTCodBelfStatiDao;
import it.toscana.regione.wsods.ejb.rielaboratore.api.IGianov4Rielaboratore;
import it.toscana.regione.wsods.ejb.sdm.api.ISdm;
import it.toscana.regione.wsods.entity.bean.api.ISoggetto;
import it.toscana.regione.wsods.entity.jpa.api.IAutocertificazioneScartate;
import it.toscana.regione.wsods.entity.jpa.api.IAutocertificazioneTmp;
import it.toscana.regione.wsods.entity.jpa.api.ICertificazioneScartate;
import it.toscana.regione.wsods.exception.RielaboratoreException;
import it.toscana.regione.wsods.exception.SisDataManagerException;
import it.toscana.regione.wsods.exception.WsOdsRuntimeException;
import it.toscana.regione.wsods.lib.Convertitore;
import it.toscana.regione.wsods.lib.RecuperoDatiCF;
import it.toscana.regione.wsods.type.Code;

import java.util.Date;

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
@Stateless(name = IGianov4Rielaboratore.EJB_REF)
public class Gianov4Rielaboratore implements IGianov4Rielaboratore {

	private final Logger LOG = LoggerFactory.getLogger(Gianov4Rielaboratore.class);

	@EJB(beanName = ISdm.EJB_REF)
	private ISdm sdm;

	@EJB(beanName = IAutocertificazioneScartateDao.EJB_REF)
	private IAutocertificazioneScartateDao autocertificazioneScartateDao;

	@EJB(beanName = ICertificazioneScartateDao.EJB_REF)
	private ICertificazioneScartateDao certificazioneScartateDao;

	@EJB(beanName = IRCTCodBelfComuniDao.EJB_REF)
	private IRCTCodBelfComuniDao rctCodBelfComuniDao;

	@EJB(beanName = IRCTCodBelfStatiDao.EJB_REF)
	private IRCTCodBelfStatiDao rctCodBelfStatiDao;

	/**
	 * 
	 */
	public Gianov4Rielaboratore() {
		super();
	}

	/** {@inheritDoc} */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void elaboraAutocertificazioneScartate(Long id) throws RielaboratoreException {
		try {
			IAutocertificazioneScartate a = autocertificazioneScartateDao.get(id);

			// Beneficiario:
			boolean esito = processaSoggettoConCFETratti(a.getCfBeneficiario(), a.getNomeBeneficiario(), a.getCognomeBeneficiario(), a.getDataNascitaBeneficiario(), a
					.getSessoBeneficiario(), a.getComuneNascitaBeneficiario(), a, "Beneficiario");
			if (!esito) {
				return;
			}
			// Titolare:
			esito = processaSoggettoConCFETratti(a.getCfTitolare(), a.getNomeTitolare(), a.getCognomeTitolare(), a.getDataNascitaTitolare(), a.getSessoTitolare(), a
					.getComuneNascitaTitolare(), a, "Titolare");
			if (!esito) {
				return;
			}
			// Dichiarante:
			esito = processaSoggettoConCFETratti(a.getCfDichiarante(), a.getNomeDichiarante(), a.getCognomeDichiarante(), a.getDataNascitaDichiarante(), a.getSessoDichiarante(), a
					.getComuneNascitaDichiarante(), a, "Dichiarante");
			if (!esito) {
				return;
			}
			// Tutto OK!
			a.setRielabora(1L);
		} catch (RielaboratoreException e) {
			throw e;
		} catch (WsOdsRuntimeException e) {
			throw new RielaboratoreException(e.code, e);
		} catch (Throwable e) {
			throw new RielaboratoreException(Code.EJB_GENERICO, e);
		}
	}

	/** {@inheritDoc} */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void elaboraCertificazioneScartate(Long id) throws RielaboratoreException {
		try {
			ICertificazioneScartate a = certificazioneScartateDao.get(id);

			// SogEsente:
			boolean esito = processaSoggettoConCF(a.getCfSogEsente(), a, "SogEsente");
			if (!esito) {
				return;
			}
			// Tutto OK!
			a.setRielabora(1L);
		} catch (RielaboratoreException e) {
			throw e;
		} catch (WsOdsRuntimeException e) {
			throw new RielaboratoreException(e.code, e);
		} catch (Throwable e) {
			throw new RielaboratoreException(Code.EJB_GENERICO, e);
		}
	}

	private boolean processaSoggettoConCFETratti(String cf, String nome, String cognome, Date dataNascita, String sesso, String comuneNascita, IAutocertificazioneScartate a,
			String nomeSoggetto) {
		if (cf == null || cf.trim().length() == 0) {
			LOG.error("AutocertificazioneScartate id: " + a.getId() + " cf " + nomeSoggetto + " assente o vuoto!");
			a.setNumeroElaborazioni(IAutocertificazioneTmp.NUMERO_ELABORAZIONI_SOGGETTI_PARTECIPANTI_SENZA_CF_O_TRATTI_IMPOSSIBILE_INVOCARE_GIANOV4);
			return false;
		}

/* 		la chiamata con cf e' stata spostata all'inizio del metodo, perche' non e' necessario avere tutti i tratti*/
		ISoggetto soggetto = null;
		try {

			soggetto = sdm.getFromCodiceFiscaleNoTransaction(cf);

		} catch (SisDataManagerException e) {
			if (Code.SDM_SERVIZIO_NON_DISPONIBILE.equals(e.code) || Code.SDM_SERVIZIO_RESTITUISCE_ERRORE.equals(e.code)) {
				throw new RielaboratoreException(e);
			}
		}

/*		fine della chiamata con cf, solo se non ho trovato il soggetto vado avanti */
		if (soggetto == null) {
			
			if (nome == null || nome.trim().length() == 0) {
				LOG.error("AutocertificazioneScartate id: " + a.getId() + " nome " + nomeSoggetto + " assente o vuoto!");
				a.setNumeroElaborazioni(IAutocertificazioneTmp.NUMERO_ELABORAZIONI_SOGGETTI_PARTECIPANTI_SENZA_CF_O_TRATTI_IMPOSSIBILE_INVOCARE_GIANOV4);
				return false;
			}
			if (cognome == null || cognome.trim().length() == 0) {
				LOG.error("AutocertificazioneScartate id: " + a.getId() + " cognome " + nomeSoggetto + " assente o vuoto!");
				a.setNumeroElaborazioni(IAutocertificazioneTmp.NUMERO_ELABORAZIONI_SOGGETTI_PARTECIPANTI_SENZA_CF_O_TRATTI_IMPOSSIBILE_INVOCARE_GIANOV4);
				return false;
			}
			if (dataNascita == null) {
				LOG.error("AutocertificazioneScartate id: " + a.getId() + " dataNascita " + nomeSoggetto + " assente o vuoto!");
				a.setNumeroElaborazioni(IAutocertificazioneTmp.NUMERO_ELABORAZIONI_SOGGETTI_PARTECIPANTI_SENZA_CF_O_TRATTI_IMPOSSIBILE_INVOCARE_GIANOV4);
				return false;
			}
			if (sesso == null || sesso.trim().length() == 0) {
				LOG.error("AutocertificazioneScartate id: " + a.getId() + " sesso " + nomeSoggetto + " assente o vuoto!");
				a.setNumeroElaborazioni(IAutocertificazioneTmp.NUMERO_ELABORAZIONI_SOGGETTI_PARTECIPANTI_SENZA_CF_O_TRATTI_IMPOSSIBILE_INVOCARE_GIANOV4);
				return false;
			}
	
			String dataNascitaStr = null;
			try {
				dataNascitaStr = Convertitore.convertTime(dataNascita.getTime(), "dd/MM/yyyy");
			} catch (Exception e) {
				LOG.error("AutocertificazioneScartate id: " + a.getId() + " errore durante la conversione del campo dataNascita del " + nomeSoggetto + "!");
				throw new RielaboratoreException(Code.PARSER, e);
			}
	
			// ottieni codiceBelfiore
			RecuperoDatiCF rdcf = new RecuperoDatiCF(cf);
			rdcf.recuperaInformazioni();
			String codBelfiore = rdcf.getComuneNascita();
			if (codBelfiore == null) {
				LOG.error("AutocertificazioneScartate id: " + a.getId() + " impossibile risalire al codBelfiore dal cf " + nomeSoggetto + "!");
				a.setNumeroElaborazioni(IAutocertificazioneTmp.NUMERO_ELABORAZIONI_SOGGETTI_PARTECIPANTI_SENZA_CF_O_TRATTI_IMPOSSIBILE_INVOCARE_GIANOV4);
				return false;
			}
			// ottieni statoNascita e comuneNascita
			String statoNascita = null;
			if (codBelfiore.startsWith("Z")) {
				// Se il codice belfiore comincia con "Z" si tratta di uno straniero
				statoNascita = rctCodBelfStatiDao.getCodiceIstatByCodBelfiore(codBelfiore);
				comuneNascita = "999999";
	
				if (statoNascita == null || statoNascita.trim().length() == 0) {
					LOG.error("AutocertificazioneScartate id: " + a.getId() + " impossibile risalire allo statoNascita " + nomeSoggetto + " da codBelfiore!");
					a.setNumeroElaborazioni(IAutocertificazioneTmp.NUMERO_ELABORAZIONI_SOGGETTI_PARTECIPANTI_SENZA_CF_O_TRATTI_IMPOSSIBILE_INVOCARE_GIANOV4);
					return false;
				}
			} else {
				// Se si tratta di un italiano
				statoNascita = "100";
				comuneNascita = rctCodBelfComuniDao.getCodiceIstatByCodBelfiore(codBelfiore);
	
				if (comuneNascita == null || comuneNascita.trim().length() == 0) {
					LOG.error("AutocertificazioneScartate id: " + a.getId() + " impossibile risalire al comuneNascita " + nomeSoggetto + " da codBelfiore!");
					a.setNumeroElaborazioni(IAutocertificazioneTmp.NUMERO_ELABORAZIONI_SOGGETTI_PARTECIPANTI_SENZA_CF_O_TRATTI_IMPOSSIBILE_INVOCARE_GIANOV4);
					return false;
				}
			}
/* la chiamata con il solo cf e' stata spostata all'inizio del metodo, tanto per la ricerca con cf non importa avere tutti i tratti
		ISoggetto soggetto = null;
		try {

			soggetto = sdm.getFromCodiceFiscaleNoTransaction(cf);

		} catch (SisDataManagerException e) {
			if (Code.SDM_SERVIZIO_NON_DISPONIBILE.equals(e.code) || Code.SDM_SERVIZIO_RESTITUISCE_ERRORE.equals(e.code)) {
				throw new RielaboratoreException(e);
			}
		}

		if (soggetto == null) {
*/
			try {

				sdm.ricercaAnagraficheByCfETrattiWSODS(cf, nome, cognome, sesso, dataNascitaStr, comuneNascita, statoNascita);

			} catch (SisDataManagerException e) {
				if (Code.SDM_SERVIZIO_NON_DISPONIBILE.equals(e.code)) {
					throw new RielaboratoreException(e);
				} else if (Code.SDM_GIANOV4_RISPONDE_CON_WARNING.equals(e.code)) {
					LOG.error("AutocertificazioneScartate id: " + a.getId() + " " + nomeSoggetto + " gianov4 risponde con WARNING!");
					//Fix:l'autocertificazione deve essere rielaborata se sdm risponde con warning perche' si presuppone che il soggetto sia stato censito.
					//a.setNumeroElaborazioni(IAutocertificazioneTmp.NUMERO_ELABORAZIONI_GIANOV4_RISPONDE_CON_WARNING);
					//In realta' cosi' si ottiene un loop perche' si continua a invocare il servizio con cf e tratti e si ottiene sempre la stessa risposta.
					//E' piu' corretto settare il warning  
					a.setNumeroElaborazioni(IAutocertificazioneTmp.NUMERO_ELABORAZIONI_GIANOV4_RISPONDE_CON_WARNING);
					return false;
				} else if (Code.SDM_GIANOV4_RISPONDE_CON_ERRORE.equals(e.code)) {
					LOG.error("AutocertificazioneScartate id: " + a.getId() + " " + nomeSoggetto + " gianov4 risponde con ERRORE!");
					a.setNumeroElaborazioni(IAutocertificazioneTmp.NUMERO_ELABORAZIONI_GIANOV4_RISPONDE_CON_ERRORE);
					return false;
				} else if (Code.SDM_GIANOV4_NON_TROVA_ANAGRAFICHE.equals(e.code)) {
					LOG.error("AutocertificazioneScartate id: " + a.getId() + " " + nomeSoggetto + " gianov4 non trova anagrafiche!");
					a.setNumeroElaborazioni(IAutocertificazioneTmp.NUMERO_ELABORAZIONI_GIANOV4_NON_TROVA_ANAGRAFICHE);
					return false;
				} else if (Code.SDM_GIANOV4_RITORNA_N_ANAGRAFICHE.equals(e.code)) {
					LOG.error("AutocertificazioneScartate id: " + a.getId() + " " + nomeSoggetto + " gianov4 trova N anagrafiche!");
					a.setNumeroElaborazioni(IAutocertificazioneTmp.NUMERO_ELABORAZIONI_GIANOV4_RITORNA_N_ANAGRAFICHE);
					return false;
				}
			}

		}
		return true;
	}

	private boolean processaSoggettoConCF(String cf, ICertificazioneScartate a, String nomeSoggetto) {
		if (cf == null || cf.trim().length() == 0) {
			LOG.error("CertificazioneScartate id: " + a.getId() + " cf " + nomeSoggetto + " assente o vuoto!");
			a.setNumeroElaborazioni(IAutocertificazioneTmp.NUMERO_ELABORAZIONI_SOGGETTI_PARTECIPANTI_SENZA_CF_O_TRATTI_IMPOSSIBILE_INVOCARE_GIANOV4);
			return false;
		}
		
		ISoggetto soggetto = null;
		try {

			soggetto = sdm.getFromCodiceFiscaleNoTransaction(cf);

		} catch (SisDataManagerException e) {
			if (Code.SDM_SERVIZIO_NON_DISPONIBILE.equals(e.code) || Code.SDM_SERVIZIO_RESTITUISCE_ERRORE.equals(e.code)) {
				throw new RielaboratoreException(e);
			}
		}

		if (soggetto == null) {

			try {

				sdm.ricercaAnagraficheByCfWSODS(cf);

			} catch (SisDataManagerException e) {
				if (Code.SDM_SERVIZIO_NON_DISPONIBILE.equals(e.code)) {
					throw new RielaboratoreException(e);
				} else if (Code.SDM_GIANOV4_RISPONDE_CON_WARNING.equals(e.code)) {
					LOG.error("CertificazioneScartate id: " + a.getId() + " " + nomeSoggetto + " gianov4 risponde con WARNING!");
					a.setNumeroElaborazioni(IAutocertificazioneTmp.NUMERO_ELABORAZIONI_GIANOV4_RISPONDE_CON_WARNING);
					return false;
				} else if (Code.SDM_GIANOV4_RISPONDE_CON_ERRORE.equals(e.code)) {
					LOG.error("CertificazioneScartate id: " + a.getId() + " " + nomeSoggetto + " gianov4 risponde con ERRORE!");
					a.setNumeroElaborazioni(IAutocertificazioneTmp.NUMERO_ELABORAZIONI_GIANOV4_RISPONDE_CON_ERRORE);
					return false;
				} else if (Code.SDM_GIANOV4_NON_TROVA_ANAGRAFICHE.equals(e.code)) {
					LOG.error("CertificazioneScartate id: " + a.getId() + " " + nomeSoggetto + " gianov4 non trova anagrafiche!");
					a.setNumeroElaborazioni(IAutocertificazioneTmp.NUMERO_ELABORAZIONI_GIANOV4_NON_TROVA_ANAGRAFICHE);
					return false;
				} else if (Code.SDM_GIANOV4_RITORNA_N_ANAGRAFICHE.equals(e.code)) {
					LOG.error("CertificazioneScartate id: " + a.getId() + " " + nomeSoggetto + " gianov4 trova N anagrafiche!");
					a.setNumeroElaborazioni(IAutocertificazioneTmp.NUMERO_ELABORAZIONI_GIANOV4_RITORNA_N_ANAGRAFICHE);
					return false;
				}
			}

		}
		return true;
	}

}

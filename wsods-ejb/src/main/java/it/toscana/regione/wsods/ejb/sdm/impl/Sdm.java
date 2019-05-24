/** */
package it.toscana.regione.wsods.ejb.sdm.impl;

import it.toscana.regione.eng.common.entity.AnagraficaPersonaFisica;
import it.toscana.regione.eng.common.exception.AnagraficheRestException;
import it.toscana.regione.eng.common.rest.response.entity.sisdatamanager.estraiAnagraficaRegistryMaster.Anagrafica;
import it.toscana.regione.eng.common.rest.response.entity.sisdatamanager.estraiAnagraficaRegistryMaster.Soggetto;
import it.toscana.regione.eng.common.rest.response.entity.sisdatamanager.listaAssociazioniIdUni.ListaAssociazioniIdUni;
import it.toscana.regione.eng.common.service.RestServiceUtil;
import it.toscana.regione.eng.common.utility.Constant;
import it.toscana.regione.wsods.costanti.Varie;
import it.toscana.regione.wsods.ejb.sdm.api.ISdm;
import it.toscana.regione.wsods.entity.bean.api.ISoggetto;
import it.toscana.regione.wsods.exception.ConvertitoreException;
import it.toscana.regione.wsods.exception.SisDataManagerException;
import it.toscana.regione.wsods.lib.Convertitore;
import it.toscana.regione.wsods.rest.RestClientManager;
import it.toscana.regione.wsods.rest.RestConstant;
import it.toscana.regione.wsods.singleton.Cache;
import it.toscana.regione.wsods.singleton.Conf;
import it.toscana.regione.wsods.type.Code;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless(name = ISdm.EJB_REF)
public class Sdm implements ISdm {

	private static final Logger LOG = LoggerFactory.getLogger(Sdm.class);

	private RestClientManager restClientManager;

	/**
	 * 
	 */
	public Sdm() {
	}

	@PostConstruct
	public void postConstruct() {
		restClientManager = new RestClientManager(Conf.getSisDataManagerUrl(), Conf.getSisDataManagerRequestMethod(), Conf.getSisDataManagerReadTimeout(), Conf.getSisDataManagerConnectionTimeout(), Conf.getSisDataManagerSslEnabled(), Conf.getSisDataManagerSslKeyStore(),
				Conf.getSisDataManagerSslKeyStorePwd());
	}

	@PreDestroy
	public void preDestroy() {
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	/** {@inheritDoc} */@Override
	public void ricercaAnagraficheByCfWSODS(final String codiceFiscale) throws SisDataManagerException {
		Map<String, String> param = new HashMap<String, String>();
		param.put(RestConstant.ROOT, RestConstant.CF_KEY);
		param.put(RestConstant.EXTENSION, codiceFiscale);
		param.put(RestConstant.OIDSENDER, Conf.getOidSenderGianov4());

		String response = restClientManager.ricercaAnagraficheByCfWSODS(param);

		handleRicercaAnagraficheWSODSResponse("ricercaAnagraficheByCfWSODS", response);
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	/** {@inheritDoc} */@Override
	public void ricercaAnagraficheByCfETrattiWSODS(final String codiceFiscale, final String nome, final String cognome, final String sesso, final String dataNascita,
			final String comuneNascita, final String statoNascita) throws SisDataManagerException {
		Map<String, String> param = new HashMap<String, String>();
		param.put(RestConstant.ROOT, RestConstant.CF_KEY);
		param.put(RestConstant.EXTENSION, codiceFiscale);
		param.put(RestConstant.OIDSENDER, Conf.getOidSenderGianov4());
		param.put(RestConstant.NOME, nome);
		param.put(RestConstant.COGNOME, cognome);
		param.put(RestConstant.SESSO, sesso);
		param.put(RestConstant.DATANASCITA, dataNascita);
		param.put(RestConstant.COMUNENASCITA, comuneNascita);
		param.put(RestConstant.STATONASCITA, statoNascita);

		String response = restClientManager.ricercaAnagraficheByCfETrattiWSODS(param);

		handleRicercaAnagraficheWSODSResponse("ricercaAnagraficheByCfETrattiWSODS", response);
	}

	private void handleRicercaAnagraficheWSODSResponse(String nomeServizio, String response) {
		JSONObject responseSerevice = null;

		try {
			responseSerevice = new JSONObject(response);
		} catch (ParseException e) {
			LOG.error("Servizio REST " + nomeServizio + ": Impossibile convertire la string in un oggetto json," + "risposta JSON: " + response);
			throw new SisDataManagerException(Code.SDM_SERVIZIO_NON_DISPONIBILE, e);
		}

		// controllo se si tratta di warning
		String esito = null;
		if (responseSerevice != null && responseSerevice.has(Constant.ESTRATTORE_DATI_RISPOSTA) && responseSerevice.get(Constant.ESTRATTORE_DATI_RISPOSTA) != null) {
			if (responseSerevice.has(Constant.ESTRATTORE_DATI_RISPOSTA)) {
				JSONObject risposta = ((JSONObject) responseSerevice.get(Constant.ESTRATTORE_DATI_RISPOSTA));
				if (risposta.has("esito") && !risposta.isNull("esito")) {
					esito = ((String) risposta.get("esito"));
				}
			}
		}
		if (esito != null) {
			LOG.error("Ricevuto WARNING a seguito invocazione servizio REST " + nomeServizio + ", esito: " + esito);
			throw new SisDataManagerException(Code.SDM_GIANOV4_RISPONDE_CON_WARNING);
		}

		List<AnagraficaPersonaFisica> lista = null;

		try {
			lista = RestServiceUtil.getRicercaResponse(responseSerevice);
		} catch (AnagraficheRestException e) {
			LOG.error("Ricevuto ERRORE a seguito invocazione servizio REST " + nomeServizio + ", errore: " + (e.getType() != null ? e.getType().getDescription() : "")
					+ "risposta JSON: " + response, e);
			throw new SisDataManagerException(Code.SDM_GIANOV4_RISPONDE_CON_ERRORE, e);
		}

		if (lista == null || lista.isEmpty()) {
			LOG.error("Il servizio REST " + nomeServizio + " non ha trovato anagrafiche, " + "risposta JSON: " + response);
			throw new SisDataManagerException(Code.SDM_GIANOV4_NON_TROVA_ANAGRAFICHE);
		}
		if (lista != null && lista.size() > 1) {
			LOG.error("Il servizio REST " + nomeServizio + " ha trovato N anagrafiche, " + "risposta JSON: " + response);
			throw new SisDataManagerException(Code.SDM_GIANOV4_RITORNA_N_ANAGRAFICHE);
		}
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	/** {@inheritDoc} */@Override
	public ISoggetto getFromCodiceFiscaleNoTransaction(final String codiceFiscale) throws SisDataManagerException {
		return this.getFromCodiceFiscale(codiceFiscale);
	}

	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	/** {@inheritDoc} */@Override
	public ISoggetto getFromCodiceFiscale(final String codiceFiscale) throws SisDataManagerException {
		ISoggetto soggetto = Cache.cf(codiceFiscale);
		if (soggetto == null) {
			soggetto = getFromCodiceFiscaleNoCache(codiceFiscale);
			if (soggetto != null) {
				Cache.add(soggetto);
			}
		}
		return soggetto;
	}
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	/** {@inheritDoc} */@Override
	public ISoggetto getFromIdUniversaleNoTransaction(final String idUniversale) throws SisDataManagerException {
		return this.getFromIdUniversale(idUniversale);
	}
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	/** {@inheritDoc} */@Override
	public ISoggetto getFromIdUniversale(final String idUniversale) throws SisDataManagerException {
		ISoggetto soggetto = Cache.idUni(idUniversale);
		if (soggetto == null) {
			soggetto = getFromIdUniversaleNoCache(idUniversale);
			Cache.add(soggetto);
		}
		return soggetto;
	}

	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	/** {@inheritDoc} */@Override
	public ISoggetto getFromCodiceFiscaleNoCache(final String codiceFiscale) throws SisDataManagerException {
		Soggetto soggetto = null;
		try {
			if (codiceFiscale != null && !codiceFiscale.isEmpty()) {
				Map<String, String> param = new HashMap<String, String>();
				param.put(RestConstant.ROOT, RestConstant.CF_KEY);
				param.put(RestConstant.EXTENSION, codiceFiscale);

				soggetto = restClientManager.getSoggetto(param);

			} else {
				LOG.error("Il Codice Fiscale in ingresso e' vuoto");
				throw new SisDataManagerException(Code.SDM_CODICE_FISCALE_VUOTO);
			}
		} catch (Exception e) {
			gestisciEccezioni(e, "[codiceFiscale:" + codiceFiscale + "]");
		}

		return map(soggetto, "[codiceFiscale:" + codiceFiscale + "] info del soggetto inadeguate");

	}

	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	/** {@inheritDoc} */@Override
	public ISoggetto getFromIdUniversaleNoCache(final String idUniversale) throws SisDataManagerException {
		Soggetto soggetto = null;
		try {
			if (idUniversale != null && !idUniversale.isEmpty()) {
				Map<String, String> param = new HashMap<String, String>();
				param.put(RestConstant.ROOT, RestConstant.ID_UNIVERSALE_KEY);
				param.put(RestConstant.EXTENSION, idUniversale);

				soggetto = restClientManager.getSoggetto(param);
			} else {
				LOG.error("L'id universale in ingresso e' vuoto");
				throw new SisDataManagerException(Code.SDM_ID_UNIVERSALE_VUOTO);
			}
		} catch (Exception e) {
			gestisciEccezioni(e, "[idUniversale:" + idUniversale + "]");
		}

		return map(soggetto, "[idUniversale:" + idUniversale + "] info del soggetto inadeguate");

	}

	
	
	@Override @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public ListaAssociazioniIdUni getLinks(final Timestamp lastTime) throws SisDataManagerException {
		final String time;
		if(lastTime != null){
			try {
				time = Convertitore.convertTime(lastTime.getTime(), Varie.FORMATO_TIME_ID_UNI_OBSOLETI);
			} catch (ConvertitoreException e) {
				throw new SisDataManagerException(Code.SDM_DATO_MANCANTE, "impossibile convertire la data minima per il recupero degli idUni obsoleti",e);
			}
		} else {
			time = Conf.getRecuperoIdUniObsoletiDataAggiornamentoMinimaRecordDaRecuperareDefault();
		}
		final int maxRow = Conf.getRecuperoIdUniObsoletiNumeroMassimoRecordDaRecuperare();
		
		final ListaAssociazioniIdUni response = restClientManager.getNextLinksIdUniObs(maxRow, time);
		
		return response;
	}
	
	
	
	
	
	private void gestisciEccezioni(final Throwable t, final String dettaglioDiRiferimento) throws SisDataManagerException {

		gestisciEccezioniFinale(t, dettaglioDiRiferimento);

		gestisciEccezioniOriginale(t, dettaglioDiRiferimento);

		// TODO gestire in modo distinto le varie eccezzioni se necessario,
		// per adesso consideriamo tutto servizio non disponibile e quindi
		// rielaborabile infinite volte.
		throw new SisDataManagerException(Code.SDM_SERVIZIO_NON_DISPONIBILE, dettaglioDiRiferimento + " impossibile ottenere le info del soggetto tramite Sis Data Manager", t);

	}

	private void gestisciEccezioniOriginale(final Throwable t, final String dettaglioDiRiferimento) throws SisDataManagerException {
		final Throwable cause = estraiEccezioneOriginale(t);
		if (cause instanceof SisDataManagerException) {
			final SisDataManagerException e = (SisDataManagerException) cause;
			final StringBuffer sb = new StringBuffer();
			sb.append(dettaglioDiRiferimento).append(" impossibile ottenere le info del soggetto tramite Sis Data Manager");
			if (e.code != null) {

				if (!e.code.equals(Code.SDM_SERVIZIO_NON_DISPONIBILE)) {
					sb.append(" code [").append(e.code).append("]");
					sb.append(" Description [").append(e.getMessage()).append("]");
					throw new SisDataManagerException(Code.SDM_SERVIZIO_RESTITUISCE_ERRORE, sb.toString(), e);
				}
			}
		}
	}

	private void gestisciEccezioniFinale(final Throwable t, final String dettaglioDiRiferimento) throws SisDataManagerException {
		//TODO inserire eventuale gestione eccezzioni non discendenti dalle WSODS 
	}

	private Throwable estraiEccezioneOriginale(final Throwable t) {
		final Throwable cause = t.getCause();
		if (cause != null) {
			return estraiEccezioneOriginale(cause);
		} else {
			return t;
		}
	}

	private ISoggetto map(final Soggetto soggetto, final String dettaglioRichiedente) throws SisDataManagerException {
		if (soggetto != null && soggetto.getAnagrafica() != null) {
			final Anagrafica anagrafica = soggetto.getAnagrafica();

			if (anagrafica.getIdUniversale() != null && anagrafica.getCodiceFiscale() != null && anagrafica.getCodiceFiscale().getExtension() != null && anagrafica.getCognome() != null && anagrafica.getNome() != null && anagrafica.getComuneNascita() != null && anagrafica.getDataDiNascita() != null && anagrafica.getSesso() != null) {
				long dataNascita;
				try {
					dataNascita = Convertitore.convertTime(anagrafica.getDataDiNascita());
				} catch (ConvertitoreException e) {
					throw new SisDataManagerException(Code.SDM_DATI_OTTENUTI_NON_SUFFICIENTI, dettaglioRichiedente, e);
				}
				return new it.toscana.regione.wsods.entity.bean.impl.Soggetto(anagrafica.getIdUniversale(), anagrafica.getCodiceFiscale().getExtension(), anagrafica.getCognome(), anagrafica.getNome(), anagrafica.getComuneNascita(), dataNascita, soggetto.getAnagrafica().getSesso());
			} else {
				throw new SisDataManagerException(Code.SDM_DATI_OTTENUTI_NON_SUFFICIENTI, dettaglioRichiedente);
			}
		} else {
			throw new SisDataManagerException(Code.SDM_SOGGETTO_NON_TROVATO, dettaglioRichiedente);
		}
	}



}

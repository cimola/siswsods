/** */
package it.toscana.regione.wsods.entity.jpa.api;

import java.util.List;

/**
 * @author cciurli
 *
 */
public interface IRicevutaDownloadSogei extends IDbEntity {
	
	public static final long STATO_ELABORAZIONE_INSERITO = 0L;
	public static final long STATO_ELABORAZIONE_IN_ELABORAZIONE = 1L;
	public static final long STATO_ELABORAZIONE_ELABORATO = 2L;
	public static final long STATO_ELABORAZIONE_RECORD_FAKE = 9L;
	public static final String TIPOLOGIA_RICEVUTA_CORRENTE = "CORRENTE";
	public static final String TIPOLOGIA_RICEVUTA_RECUPERO = "RECUPERO";
	
	String getDataOraLimite();
	
	
	void setDataOraLimite(String dataOraLimite);
	
	
	String getDiagnostico();
	
	
	void setDiagnostico(String diagnostico);
	
	
	String getEsito();
	
	
	void setEsito(String esito);
	
	
	String getCodAsl();
	
	
	void setCodAsl(String idUniAsl);
	
	
	String getCodRegione();
	
	
	void setCodRegione(String idUniRegione);
	
	
	long getNumeroRecordTrovati();
	
	
	void setNumeroRecordTrovati(long numeroRecordTrovati);
	
	
	long getNumeroRecordElaborati();
	
	
	void setNumeroRecordElaborati(final long numeroRecordElaborati);
	
	
	long getNumeroRecordNuovi();
	
	
	void setNumeroRecordNuovi(final long numeroRecordNuovi);
	
	
	String getTipoAutocertificazione();
	
	
	void setTipoAutocertificazione(String tipoAutocertificazione);
	
	
	String getUserId();
	
	
	void setUserId(String userId);
	
	
	String getUtenteTocken();
	
	
	void setUtenteTocken(String utenteTocken);
	
	String getXmlDocument();
	void setXmlDocument(final String xmlDocument);
	
	long getStatoElaborazione();
	void setStatoElaborazione(final long statoElaborazone);
	
	String getProtocollo();
	void setProtocollo(final String protocollo);
	
	String getTipologiaRicevuta();
	void setTipologiaRicevuta(final String tipologiaRicevuta);
	
	List<IAutocertificazioneTmp> getListAutocertificazioneTmp();
	
	void addAutocertificazioneTmp(final IAutocertificazioneTmp autocertificazioneTmp);
	
	List<ICertificazioneTmp> getListCertificazioneTmp();
	
	void addCertificazioneTmp(final ICertificazioneTmp certificazioneTmp);

	void setNumeroRecordElaborati();
	
	void setNumeroRecordElaboratiCert();
	
}

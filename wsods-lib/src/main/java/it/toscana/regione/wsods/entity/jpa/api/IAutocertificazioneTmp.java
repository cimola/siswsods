/** */
package it.toscana.regione.wsods.entity.jpa.api;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @author cciurli
 *
 */
public interface IAutocertificazioneTmp extends IDbEntity {
	
	public final static long NUMERO_ELABORAZIONI_CF_DICHIARANTE_NULLO = 900L;
	public final static long NUMERO_ELABORAZIONI_TITOLO_NULLO = 800L;
	public final static long NUMERO_ELABORAZIONI_TITOLO_NULLO_PROTOCOLLO_NON_TROVATO = 801L;
	public final static long NUMERO_ELABORAZIONI_TITOLO_NULLO_SOGGETTI_DIVERGENTI = 802L;
	public final static long NUMERO_ELABORAZIONI_TITOLO_NULLO_TITOLO_TROVATO_NON_VALIDO = 803L;
	public final static long NUMERO_ELABORAZIONI_SOGGETTI_PARTECIPANTI_NON_RISOLTI_O_CON_TRATTI_PARZIALI_IN_ANAGRAFE = 500L;
	public final static long NUMERO_ELABORAZIONI_GIANOV4_RISPONDE_CON_ERRORE = 501L;
	public final static long NUMERO_ELABORAZIONI_GIANOV4_RISPONDE_CON_WARNING = 502L;
	public final static long NUMERO_ELABORAZIONI_GIANOV4_NON_TROVA_ANAGRAFICHE = 503L;
	public final static long NUMERO_ELABORAZIONI_GIANOV4_RITORNA_N_ANAGRAFICHE = 504L;
	public final static long NUMERO_ELABORAZIONI_SOGGETTI_PARTECIPANTI_SENZA_CF_O_TRATTI_IMPOSSIBILE_INVOCARE_GIANOV4 = 505L;
	public final static long NUMERO_ELABORAZIONI_SOGGETTI_PARTECIPANTI_SENZA_STATO_NASCITA_IMPOSSIBILE_INVOCARE_GIANOV4 = 506L;
	
	String getAnnoEsenzione();
	
	
	void setAnnoEsenzione(String annoEsenzione);
	
	
	String getCfBeneficiario();
	
	
	void setCfBeneficiario(String cfBeneficiario);
	
	
	String getCfDichiarante();
	
	
	void setCfDichiarante(String cfDichiarante);
	
	
	String getCfTitolare();
	
	
	void setCfTitolare(String cfTitolare);
	
	
	String getCodAsl();
	
	
	void setCodAsl(String codAsl);
	
	
	String getCodiceEsenzione();
	
	
	void setCodiceEsenzione(String codiceEsenzione);
	
	
	String getCognomeBeneficiario();
	
	
	void setCognomeBeneficiario(String cognomeBeneficiario);
	
	
	String getCognomeDichiarante();
	
	
	void setCognomeDichiarante(String cognomeDichiarante);
	
	
	String getCognomeTitolare();
	
	
	void setCognomeTitolare(String cognomeTitolare);
	
	
	String getComuneNascitaBeneficiario();
	
	
	void setComuneNascitaBeneficiario(String comuneNascitaBeneficiario);
	
	
	String getComuneNascitaDichiarante();
	
	
	void setComuneNascitaDichiarante(String comuneNascitaDichiarante);
	
	
	String getComuneNascitaTitolare();
	
	
	void setComuneNascitaTitolare(String comuneNascitaTitolare);
	
	
	Date getDataAutocertificazione();
	
	
	void setDataAutocertificazione(Date dataAutocertificazione);
	
	
	Date getDataFineValid();
	
	
	void setDataFineValid(Date dataFineValid);
	
	
	Date getDataFineValidOld();
	
	
	void setDataFineValidOld(Date dataFineValidold);
	
	
	Date getDataInizioValid();
	
	
	void setDataInizioValid(Date dataInizioValid);
	
	
	Date getDataNascitaDichiarante();
	
	
	void setDataNascitaDichiarante(Date dataNascitaDichiarante);
	
	
	Date getDataNascitaTitolare();
	
	
	void setDataNascitaTitolare(Date dataNascitaTitolare);
	
	
	Date getDataNascitaBeneficiario();
	
	
	void setDataNascitaBeneficiario(Date dataNascitaBeneficiario);
	
	Timestamp getDataOrdinamento();
	
	void setDataOrdinamento(final Timestamp dataOrdinamento);
	
	long getFkRicevutaDownloadSogei();
	
	
	void setFkRicevutaDownloadSogei(long fkRicevutaDownloadSogei);
	
	
	String getFlagStato();
	
	
	void setFlagStato(String flagStato);
	
	
	String getNomeBeneficiario();
	
	
	void setNomeBeneficiario(String nomeBeneficiario);
	
	
	String getNomeDichiarante();
	
	
	void setNomeDichiarante(String nomeDichiarante);
	
	
	String getNomeTitolare();
	
	
	void setNomeTitolare(String nomeTitolare);
	
	
	String getNote();
	
	
	void setNote(String note);
	
	
	long getNumeroElaborazioni();
	
	
	void setNumeroElaborazioni(long numeroElaborazioni);
	
	
	String getProtocollo();
	
	
	void setProtocollo(String protocollo);
	
	
	long getRicevutaIndex();
	
	
	void setRicevutaIndex(long ricevutaIndex);
	
	
	String getSessoBeneficiario();
	
	
	void setSessoBeneficiario(String sessoBeneficiario);
	
	
	String getSessoDichiarante();
	
	
	void setSessoDichiarante(String sessoDichiarante);
	
	
	String getSessoTitolare();
	
	
	void setSessoTitolare(String sessoTitolare);
	
	
	String getTitolo();
	
	
	void setTitolo(String titolo);

	

	public String getSorgente();
	
	void setSorgente(final String sorgente);

	
	String getTipoAutocertificazione();
	
	void setTipoAutocertificazione(final String tipoAutocertificazione);
	
}



/** */
package it.toscana.regione.wsods.entity.jpa.api;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @author cciurli
 *
 */
public interface IAutocertificazioneScartate extends IDbEntity {
	
	
	long getOldId();
	
	void setOldId(long oldId);
	
	Timestamp getOldDataAggiornamento();
	
	void setOldDataAggiornamento(Timestamp oldDataAggiornamento);
	
	Timestamp getOldDataInserimento();
	
	void setOldDataInserimento(Timestamp oldDataInserimento);
	
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
	

	public String getSorgente();
	
	void setSorgente(final String sorgente);

	
	void setTitolo(String titolo);

	String getTipoAutocertificazione();
	
	void setTipoAutocertificazione(final String tipoAutocertificazione);
	
	long getRielabora();
	
	void setRielabora(final long rielabora);
}

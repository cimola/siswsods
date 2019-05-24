package it.toscana.regione.wsods.entity.jpa.impl;

import it.toscana.regione.wsods.entity.jpa.abs.DbEntity;
import it.toscana.regione.wsods.entity.jpa.api.IAutocertificazioneScartate;
import it.toscana.regione.wsods.entity.jpa.api.IAutocertificazioneTmp;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * The persistent class for the AUTOCERTIFICAZIONE_TMP database table.
 * 
 */
@Entity
@Table(name = "AUTOCERTIFICAZIONE_SCARTATE")
public class AutocertificazioneScartate extends DbEntity implements IAutocertificazioneScartate {
	
	@Transient
	/** fild serialVersionUID {@like long}*/
	private static final long serialVersionUID = 5310489827449004265L;
	
	@Column(name = "OLD_ID", nullable = false)
	private long oldId;
	
	@Column(name = "OLD_DATA_AGGIORNAMENTO", nullable = false)
	private Timestamp oldDataAggiornamento;
	
	@Column(name = "OLD_DATA_INSERIMENTO", nullable = false)
	private Timestamp oldDataInserimento;
	
	@Column(name = "ANNO_ESENZIONE")
	private String annoEsenzione;
	
	@Column(name = "CF_BENEFICIARIO")
	private String cfBeneficiario;
	
	@Column(name = "CF_DICHIARANTE")
	private String cfDichiarante;
	
	@Column(name = "CF_TITOLARE")
	private String cfTitolare;
	
	@Column(name = "COD_ASL")
	private String codAsl;
	
	@Column(name = "CODICE_ESENZIONE")
	private String codiceEsenzione;
	
	@Column(name = "COGNOME_BENEFICIARIO")
	private String cognomeBeneficiario;
	
	@Column(name = "COGNOME_DICHIARANTE")
	private String cognomeDichiarante;
	
	@Column(name = "COGNOME_TITOLARE")
	private String cognomeTitolare;
	
	@Column(name = "COMUNE_NASCITA_BENEFICIARIO")
	private String comuneNascitaBeneficiario;
	
	@Column(name = "COMUNE_NASCITA_DICHIARANTE")
	private String comuneNascitaDichiarante;
	
	@Column(name = "COMUNE_NASCITA_TITOLARE")
	private String comuneNascitaTitolare;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "DATA_AUTOCERTIFICAZIONE")
	private Date dataAutocertificazione;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "DATA_FINE_VALID")
	private Date dataFineValid;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "DATA_FINE_VALID_OLD")
	private Date dataFineValidOld;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "DATA_INIZIO_VALID")
	private Date dataInizioValid;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "DATA_NASCITA_DICHIARANTE")
	private Date dataNascitaDichiarante;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "DATA_NASCITA_TITOLARE")
	private Date dataNascitaTitolare;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "DATA_NASCITA_BENEFICIARIO")
	private Date dataNascitaBeneficiario;
	
	@Column(name = "DATA_ORDINAMENTO")
	private Timestamp dataOrdinamento;
	
	@Column(name = "FK_RICEVUTA_DOWNLOAD_SOGEI")
	private long fkRicevutaDownloadSogei;
	
	@Column(name = "FLAG_STATO")
	private String flagStato;
	
	@Column(name = "NOME_BENEFICIARIO")
	private String nomeBeneficiario;
	
	@Column(name = "NOME_DICHIARANTE")
	private String nomeDichiarante;
	
	@Column(name = "NOME_TITOLARE")
	private String nomeTitolare;
	
	@Column(name = "NOTE")
	private String note;
	
	@Column(name = "NUMERO_ELABORAZIONI", nullable = false)
	private long numeroElaborazioni;
	
	@Column(name = "PROTOCOLLO")
	private String protocollo;
	
	@Column(name = "RICEVUTA_INDEX")
	private long ricevutaIndex;
	
	@Column(name = "SESSO_BENEFICIARIO")
	private String sessoBeneficiario;
	
	@Column(name = "SESSO_DICHIARANTE")
	private String sessoDichiarante;
	
	@Column(name = "SESSO_TITOLARE")
	private String sessoTitolare;
	
	
	@Column(name = "TITOLO")
	private String titolo;

	@Column(name = "SORGENTE",nullable = true, length = 10)
	private String sorgente;
	
	@Column(name = "TIPO_AUTOCERTIFICAZIONE")
	private String tipoAutocertificazione;

	@Column(name = "RIELABORA", nullable = false)
	private long rielabora;
	
	public AutocertificazioneScartate() {}
	
	
	public AutocertificazioneScartate(final IAutocertificazioneTmp entity) {
		super();
		this.oldId = entity.getId();
		this.oldDataAggiornamento = entity.getDataAggiornamento();
		this.oldDataInserimento = entity.getDataInserimento();
		this.annoEsenzione = entity.getAnnoEsenzione();
		this.cfBeneficiario = entity.getCfBeneficiario();
		this.cfDichiarante = entity.getCfDichiarante();
		this.cfTitolare = entity.getCfTitolare();
		this.codAsl = entity.getCodAsl();
		this.codiceEsenzione = entity.getCodiceEsenzione();
		this.cognomeBeneficiario = entity.getCognomeBeneficiario();
		this.cognomeDichiarante = entity.getCognomeDichiarante();
		this.cognomeTitolare = entity.getCognomeTitolare();
		this.comuneNascitaBeneficiario = entity.getComuneNascitaBeneficiario();
		this.comuneNascitaDichiarante = entity.getComuneNascitaDichiarante();
		this.comuneNascitaTitolare = entity.getComuneNascitaTitolare();
		this.dataAutocertificazione = entity.getDataAutocertificazione();
		this.dataFineValid = entity.getDataFineValid();
		this.dataFineValidOld = entity.getDataFineValidOld();
		this.dataInizioValid = entity.getDataInizioValid();
		this.dataNascitaDichiarante = entity.getDataNascitaDichiarante();
		this.dataNascitaTitolare = entity.getDataNascitaTitolare();
		this.dataNascitaBeneficiario = entity.getDataNascitaBeneficiario();
		this.dataOrdinamento = entity.getDataOrdinamento();
		this.fkRicevutaDownloadSogei = entity.getFkRicevutaDownloadSogei();
		this.flagStato = entity.getFlagStato();
		this.nomeBeneficiario = entity.getNomeBeneficiario();
		this.nomeDichiarante = entity.getNomeDichiarante();
		this.nomeTitolare = entity.getNomeTitolare();
		this.note = entity.getNote();
		this.numeroElaborazioni = entity.getNumeroElaborazioni();
		this.protocollo = entity.getProtocollo();
		this.ricevutaIndex = entity.getRicevutaIndex();
		this.sessoBeneficiario = entity.getSessoBeneficiario();
		this.sessoDichiarante = entity.getSessoDichiarante();
		this.sessoTitolare = entity.getSessoTitolare();
		this.tipoAutocertificazione = entity.getTipoAutocertificazione();
		this.titolo = entity.getTitolo();
		this.sorgente = entity.getSorgente();

	}


	/** {@inheritDoc} */
	@Override
	public long getOldId() {
		return oldId;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public void setOldId(final long oldId) {
		this.oldId = oldId;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public Timestamp getOldDataAggiornamento() {
		return oldDataAggiornamento;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public void setOldDataAggiornamento(final Timestamp oldDataAggiornamento) {
		this.oldDataAggiornamento = oldDataAggiornamento;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public Timestamp getOldDataInserimento() {
		return oldDataInserimento;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public void setOldDataInserimento(final Timestamp oldDataInserimento) {
		this.oldDataInserimento = oldDataInserimento;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public String getAnnoEsenzione() {
		return annoEsenzione;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public void setAnnoEsenzione(final String annoEsenzione) {
		this.annoEsenzione = annoEsenzione;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public String getCfBeneficiario() {
		// gestione cf numerici: su db il campo e' char di 16, devo effettuare il trim
		if (cfBeneficiario != null) {
			return cfBeneficiario.trim();
		}
		return cfBeneficiario;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public void setCfBeneficiario(final String cfBeneficiario) {
		this.cfBeneficiario = cfBeneficiario;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public String getCfDichiarante() {
		// gestione cf numerici: su db il campo e' char di 16, devo effettuare il trim
		if (cfDichiarante != null) {
			return cfDichiarante.trim();
		}
		return cfDichiarante;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public void setCfDichiarante(final String cfDichiarante) {
		this.cfDichiarante = cfDichiarante;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public String getCfTitolare() {
		// gestione cf numerici: su db il campo e' char di 16, devo effettuare il trim
		if (cfTitolare != null) {
			return cfTitolare.trim();
		}
		return cfTitolare;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public void setCfTitolare(final String cfTitolare) {
		this.cfTitolare = cfTitolare;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public String getCodAsl() {
		return codAsl;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public void setCodAsl(final String codAsl) {
		this.codAsl = codAsl;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public String getCodiceEsenzione() {
		return codiceEsenzione;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public void setCodiceEsenzione(final String codiceEsenzione) {
		this.codiceEsenzione = codiceEsenzione;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public String getCognomeBeneficiario() {
		return cognomeBeneficiario;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public void setCognomeBeneficiario(final String cognomeBeneficiario) {
		this.cognomeBeneficiario = cognomeBeneficiario;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public String getCognomeDichiarante() {
		return cognomeDichiarante;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public void setCognomeDichiarante(final String cognomeDichiarante) {
		this.cognomeDichiarante = cognomeDichiarante;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public String getCognomeTitolare() {
		return cognomeTitolare;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public void setCognomeTitolare(final String cognomeTitolare) {
		this.cognomeTitolare = cognomeTitolare;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public String getComuneNascitaBeneficiario() {
		return comuneNascitaBeneficiario;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public void setComuneNascitaBeneficiario(final String comuneNascitaBeneficiario) {
		this.comuneNascitaBeneficiario = comuneNascitaBeneficiario;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public String getComuneNascitaDichiarante() {
		return comuneNascitaDichiarante;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public void setComuneNascitaDichiarante(final String comuneNascitaDichiarante) {
		this.comuneNascitaDichiarante = comuneNascitaDichiarante;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public String getComuneNascitaTitolare() {
		return comuneNascitaTitolare;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public void setComuneNascitaTitolare(final String comuneNascitaTitolare) {
		this.comuneNascitaTitolare = comuneNascitaTitolare;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public Date getDataAutocertificazione() {
		return dataAutocertificazione;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public void setDataAutocertificazione(final Date dataAutocertificazione) {
		this.dataAutocertificazione = dataAutocertificazione;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public Date getDataFineValid() {
		return dataFineValid;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public void setDataFineValid(final Date dataFineValid) {
		this.dataFineValid = dataFineValid;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public Date getDataFineValidOld() {
		return dataFineValidOld;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public void setDataFineValidOld(final Date dataFineValidOld) {
		this.dataFineValidOld = dataFineValidOld;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public Date getDataInizioValid() {
		return dataInizioValid;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public void setDataInizioValid(final Date dataInizioValid) {
		this.dataInizioValid = dataInizioValid;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public Date getDataNascitaDichiarante() {
		return dataNascitaDichiarante;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public void setDataNascitaDichiarante(final Date dataNascitaDichiarante) {
		this.dataNascitaDichiarante = dataNascitaDichiarante;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public Date getDataNascitaTitolare() {
		return dataNascitaTitolare;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public void setDataNascitaTitolare(final Date dataNascitaTitolare) {
		this.dataNascitaTitolare = dataNascitaTitolare;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public Date getDataNascitaBeneficiario() {
		return dataNascitaBeneficiario;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public void setDataNascitaBeneficiario(final Date dataNascitaBeneficiario) {
		this.dataNascitaBeneficiario = dataNascitaBeneficiario;
	}
	
	/** {@inheritDoc} */
	@Override
	public Timestamp getDataOrdinamento() {
		return dataOrdinamento;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public void setDataOrdinamento(final Timestamp dataOrdinamento) {
		this.dataOrdinamento = dataOrdinamento;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public long getFkRicevutaDownloadSogei() {
		return fkRicevutaDownloadSogei;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public void setFkRicevutaDownloadSogei(final long fkRicevutaDownloadSogei) {
		this.fkRicevutaDownloadSogei = fkRicevutaDownloadSogei;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public String getFlagStato() {
		return flagStato;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public void setFlagStato(final String flagStato) {
		this.flagStato = flagStato;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public String getNomeBeneficiario() {
		return nomeBeneficiario;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public void setNomeBeneficiario(final String nomeBeneficiario) {
		this.nomeBeneficiario = nomeBeneficiario;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public String getNomeDichiarante() {
		return nomeDichiarante;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public void setNomeDichiarante(final String nomeDichiarante) {
		this.nomeDichiarante = nomeDichiarante;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public String getNomeTitolare() {
		return nomeTitolare;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public void setNomeTitolare(final String nomeTitolare) {
		this.nomeTitolare = nomeTitolare;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public String getNote() {
		return note;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public void setNote(final String note) {
		this.note = note;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public long getNumeroElaborazioni() {
		return numeroElaborazioni;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public void setNumeroElaborazioni(final long numeroElaborazioni) {
		this.numeroElaborazioni = numeroElaborazioni;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public String getProtocollo() {
		return protocollo;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public void setProtocollo(final String protocollo) {
		this.protocollo = protocollo;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public long getRicevutaIndex() {
		return ricevutaIndex;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public void setRicevutaIndex(final long ricevutaIndex) {
		this.ricevutaIndex = ricevutaIndex;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public String getSessoBeneficiario() {
		return sessoBeneficiario;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public void setSessoBeneficiario(final String sessoBeneficiario) {
		this.sessoBeneficiario = sessoBeneficiario;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public String getSessoDichiarante() {
		return sessoDichiarante;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public void setSessoDichiarante(final String sessoDichiarante) {
		this.sessoDichiarante = sessoDichiarante;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public String getSessoTitolare() {
		return sessoTitolare;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public void setSessoTitolare(final String sessoTitolare) {
		this.sessoTitolare = sessoTitolare;
	}
	
	
	
	/** {@inheritDoc} */
	@Override
	public String getTitolo() {
		return titolo;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public void setTitolo(final String titolo) {
		this.titolo = titolo;
	}

	
	/** {@inheritDoc} */
	@Override
	public String getSorgente() {
		return sorgente;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public void setSorgente(final String sorgente) {
		this.sorgente = sorgente;
	}
	
	/** {@inheritDoc} */
	@Override
	public String getTipoAutocertificazione() {
		return tipoAutocertificazione;
	}
	
	/** {@inheritDoc} */
	@Override
	public void setTipoAutocertificazione(final String tipoAutocertificazione) {
		this.tipoAutocertificazione = tipoAutocertificazione;
	}


	/** {@inheritDoc} */
	@Override
	public long getRielabora() {
		return rielabora;
	}


	/** {@inheritDoc} */
	@Override
	public void setRielabora(final long rielabora) {
		this.rielabora = rielabora;
	}
	
}

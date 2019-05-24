package it.toscana.regione.wsods.entity.jpa.impl;

import it.toscana.regione.wsods.entity.jpa.abs.DbEntity;
import it.toscana.regione.wsods.entity.jpa.api.ICertificazioneScartate;
import it.toscana.regione.wsods.entity.jpa.api.ICertificazioneTmp;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * The persistent class for the CERTIFICAZIONE_TMP database table.
 * 
 */
@Entity
@Table(name = "CERTIFICAZIONE_TMP")
public class CertificazioneTmp extends DbEntity implements ICertificazioneTmp {
	
	@Transient
	/** fild serialVersionUID {@like long}*/
	private static final long serialVersionUID = -1374003176506983414L;
	
	@Column(name = "ANNO_ESENZIONE")
	private String annoEsenzione;
	
	@Column(name = "CF_SOG_ESENTE")
	private String cfSogEsente;
	
	@Column(name = "CODICE_ESENZIONE")
	private String codiceEsenzione;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "DATA_FINE_VALID")
	private Date dataFineValid;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "DATA_FINE_VALID_OLD")
	private Date dataFineValidOld;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "DATA_INIZIO_VALID")
	private Date dataInizioValid;
	
	@Column(name = "FK_RICEVUTA_DOWNLOAD_SOGEI")
	private long fkRicevutaDownloadSogei;
	
	@Column(name = "NUMERO_ELABORAZIONI", nullable=false)
	private long numeroElaborazioni;
	
	@Column(name = "RICEVUTA_INDEX")
	private long ricevutaIndex;
	
	@Column(name = "SORGENTE",nullable = true, length = 10)
	private String sorgente;
	
	@Column(name = "DATA_ORDINAMENTO")
	private Timestamp dataOrdinamento;
	
	public CertificazioneTmp() { super(); }
	
	public CertificazioneTmp(final ICertificazioneScartate entity) {
		super();

		this.numeroElaborazioni = 0L; //entity.getNumeroElaborazioni();
		
		this.annoEsenzione = entity.getAnnoEsenzione();
		this.cfSogEsente = entity.getCfSogEsente();
		this.codiceEsenzione = entity.getCodiceEsenzione();
		this.dataOrdinamento = entity.getDataOrdinamento();
		this.dataFineValid = entity.getDataFineValid();
		this.dataFineValidOld = entity.getDataFineValidOld();
		this.dataInizioValid = entity.getDataInizioValid();
		this.fkRicevutaDownloadSogei = entity.getFkRicevutaDownloadSogei();
		this.ricevutaIndex = entity.getRicevutaIndex();
		this.sorgente = entity.getSorgente();
	}

	public String getAnnoEsenzione() {
		return annoEsenzione;
	}

	public void setAnnoEsenzione(String annoEsenzione) {
		this.annoEsenzione = annoEsenzione;
	}

	public String getCfSogEsente() {
		// gestione cf numerici: su db il campo e' char di 16, devo effettuare il trim
		if (cfSogEsente != null) {
			return cfSogEsente.trim();
		}
		return cfSogEsente;
	}

	public void setCfSogEsente(String cfSogEsente) {
		this.cfSogEsente = cfSogEsente;
	}

	public String getCodiceEsenzione() {
		return codiceEsenzione;
	}

	public void setCodiceEsenzione(String codiceEsenzione) {
		this.codiceEsenzione = codiceEsenzione;
	}

	public Date getDataFineValid() {
		return dataFineValid;
	}

	public void setDataFineValid(Date dataFineValid) {
		this.dataFineValid = dataFineValid;
	}

	public Date getDataFineValidOld() {
		return dataFineValidOld;
	}

	public void setDataFineValidOld(Date dataFineValidOld) {
		this.dataFineValidOld = dataFineValidOld;
	}

	public Date getDataInizioValid() {
		return dataInizioValid;
	}

	public void setDataInizioValid(Date dataInizioValid) {
		this.dataInizioValid = dataInizioValid;
	}

	public long getFkRicevutaDownloadSogei() {
		return fkRicevutaDownloadSogei;
	}

	public void setFkRicevutaDownloadSogei(long fkRicevutaDownloadSogei) {
		this.fkRicevutaDownloadSogei = fkRicevutaDownloadSogei;
	}

	public long getNumeroElaborazioni() {
		return numeroElaborazioni;
	}

	public void setNumeroElaborazioni(long numeroElaborazioni) {
		this.numeroElaborazioni = numeroElaborazioni;
	}

	public long getRicevutaIndex() {
		return ricevutaIndex;
	}

	public void setRicevutaIndex(long ricevutaIndex) {
		this.ricevutaIndex = ricevutaIndex;
	}

	public String getSorgente() {
		return sorgente;
	}

	public void setSorgente(String sorgente) {
		this.sorgente = sorgente;
	}

	public Timestamp getDataOrdinamento() {
		return dataOrdinamento;
	}

	public void setDataOrdinamento(Timestamp dataOrdinamento) {
		this.dataOrdinamento = dataOrdinamento;
	}
}

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
 * The persistent class for the CERTIFICAZIONE_SCARTATE database table.
 * 
 */
@Entity
@Table(name = "CERTIFICAZIONE_SCARTATE")
public class CertificazioneScartate extends DbEntity implements ICertificazioneScartate {
	
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
	
	@Column(name = "DATA_ORDINAMENTO")
	private Timestamp dataOrdinamento;
	
	@Column(name = "FK_RICEVUTA_DOWNLOAD_SOGEI")
	private long fkRicevutaDownloadSogei;
	
	@Column(name = "NUMERO_ELABORAZIONI", nullable = false)
	private long numeroElaborazioni;
	
	@Column(name = "RICEVUTA_INDEX")
	private long ricevutaIndex;
	
	@Column(name = "SORGENTE",nullable = true, length = 10)
	private String sorgente;
	
	@Column(name = "RIELABORA", nullable = false)
	private long rielabora;
	
	public CertificazioneScartate() {}
	
	public CertificazioneScartate(final ICertificazioneTmp entity) {
		super();
		this.oldId = entity.getId();
		this.oldDataAggiornamento = entity.getDataAggiornamento();
		this.oldDataInserimento = entity.getDataInserimento();
		this.annoEsenzione = entity.getAnnoEsenzione();
		this.cfSogEsente = entity.getCfSogEsente();
		this.codiceEsenzione = entity.getCodiceEsenzione();
		this.dataFineValid = entity.getDataFineValid();
		this.dataFineValidOld = entity.getDataFineValidOld();
		this.dataInizioValid = entity.getDataInizioValid();
		this.dataOrdinamento = entity.getDataOrdinamento();
		this.fkRicevutaDownloadSogei = entity.getFkRicevutaDownloadSogei();
		this.numeroElaborazioni = entity.getNumeroElaborazioni();
		this.ricevutaIndex = entity.getRicevutaIndex();
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
	public String getCfSogEsente() {
		// gestione cf numerici: su db il campo e' char di 16, devo effettuare il trim
		if (cfSogEsente != null) {
			return cfSogEsente.trim();
		}
		return cfSogEsente;
	}
	
	/** {@inheritDoc} */
	@Override
	public void setCfSogEsente(final String cfSogEsente) {
		this.cfSogEsente = cfSogEsente;
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
	public long getRielabora() {
		return rielabora;
	}

	/** {@inheritDoc} */
	@Override
	public void setRielabora(final long rielabora) {
		this.rielabora = rielabora;
	}
}

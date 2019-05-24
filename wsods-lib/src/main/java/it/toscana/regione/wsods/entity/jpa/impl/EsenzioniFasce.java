package it.toscana.regione.wsods.entity.jpa.impl;

import it.toscana.regione.wsods.entity.jpa.abs.DbEntity;
import it.toscana.regione.wsods.entity.jpa.api.IDownloadAutocertificazione;
import it.toscana.regione.wsods.entity.jpa.api.IEsenzioniFasce;
import it.toscana.regione.wsods.entity.jpa.api.IUploadAutocertificazione;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * The persistent class for the ESENZIONI_FASCE database table.
 * 
 */
@Entity
@Table(name = "ESENZIONI_FASCE")
public class EsenzioniFasce extends DbEntity implements IEsenzioniFasce {
	
	/** fild serialVersionUID {@like long}*/
	private static final long serialVersionUID = -8196695467855425593L;
	

	
	@Temporal(TemporalType.DATE)
	@Column(name = "DATA_FINE")
	private Date dataFine;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "DATA_FINE_EROGAZIONE")
	private Date dataFineErogazione;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "DATA_FINE_OLD", nullable = true)
	private Date dataFineOld;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "DATA_FORNITURA")
	private Date dataFornitura;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "DATA_INIZIO")
	private Date dataInizio;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "DATA_INIZIO_EROGAZIONE")
	private Date dataInizioErogazione;

	@Column(name = "DATA_ORDINAMENTO")
	private Timestamp dataOrdinamento;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "DATA_NASCITA")
	private Date dataNascita;
	
	@Column(name = "FLAG_TIPOLOGIA", length = 1)
	private String flagTipologia;
	
	@Column(name = "ID_UNI_DICHIARANTE", nullable = false, length = 24)
	private String idUniDichiarante;
	
	@Column(name = "ID_UNI_TITOLARE", nullable = false, length = 24)
	private String idUniTitolare;
	
	@Column(name = "ID_UNIVERSALE_ASSISTITO", nullable = false, length = 24)
	private String idUniversaleAssistito;
	
	@Column(name = "NOTA", length = 512)
	private String nota;
	
	@Column(name = "PROTOCOLLO", nullable = false, length = 18)
	private String protocollo;
	
	@Column(name = "SESSO", length = 1)
	private String sesso;
	
	@Column(name = "TIPO_ESENZIONE", length = 3)
	private String tipoEsenzione;
	
	@Column(name = "TITOLO",nullable = false, length = 1)
	private String titolo;

	@Column(name = "SORGENTE",nullable = false, length = 10)
	private String sorgente;
	
	@Column(name = "DATA_ULTIMA_VALUTAZIONE", insertable = false, updatable = true, nullable = true)
	private Timestamp dataUltimaValutazione;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "DATA_PROSSIMA_VALUTAZIONE", insertable = false, updatable = true, nullable = true)
	private Date dataProssimaValutazione;
	
	@Column(name = "FLAG_VALUTAZIONE", nullable = false)
	private long flagValutazione;
	
	@Transient private boolean arricchita = false;
	@Transient private IDownloadAutocertificazione downloadInfo;
	@Transient private IUploadAutocertificazione uploadInfo;
	
	public EsenzioniFasce() {}

	
	/** {@inheritDoc} */
	@Override
	public Date getDataFine() {
		return dataFine;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public void setDataFine(final Date dataFine) {
		this.dataFine = dataFine;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public Date getDataFineErogazione() {
		return dataFineErogazione;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public void setDataFineErogazione(final Date dataFineErogazione) {
		this.dataFineErogazione = dataFineErogazione;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public Date getDataFineOld() {
		return dataFineOld;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public void setDataFineOld(final Date dataFineOld) {
		this.dataFineOld = dataFineOld;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public Date getDataFornitura() {
		return dataFornitura;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public void setDataFornitura(final Date dataFornitura) {
		this.dataFornitura = dataFornitura;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public Date getDataInizio() {
		return dataInizio;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public void setDataInizio(final Date dataInizio) {
		this.dataInizio = dataInizio;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public Date getDataInizioErogazione() {
		return dataInizioErogazione;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public void setDataInizioErogazione(final Date dataInizioErogazione) {
		this.dataInizioErogazione = dataInizioErogazione;
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
	public Date getDataNascita() {
		return dataNascita;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public void setDataNascita(final Date dataNascita) {
		this.dataNascita = dataNascita;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public String getFlagTipologia() {
		return flagTipologia;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public void setFlagTipologia(final String flagTipologia) {
		this.flagTipologia = flagTipologia;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public String getIdUniDichiarante() {
		return idUniDichiarante;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public void setIdUniDichiarante(final String idUniDichiarante) {
		this.idUniDichiarante = idUniDichiarante;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public String getIdUniTitolare() {
		return idUniTitolare;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public void setIdUniTitolare(final String idUniTitolare) {
		this.idUniTitolare = idUniTitolare;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public String getIdUniversaleAssistito() {
		return idUniversaleAssistito;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public void setIdUniversaleAssistito(final String idUniversaleAssistito) {
		this.idUniversaleAssistito = idUniversaleAssistito;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public String getNota() {
		return nota;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public void setNota(final String nota) {
		this.nota = nota;
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
	public String getSesso() {
		return sesso;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public void setSesso(final String sesso) {
		this.sesso = sesso;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public String getTipoEsenzione() {
		return tipoEsenzione;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public void setTipoEsenzione(final String tipoEsenzione) {
		this.tipoEsenzione = tipoEsenzione;
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
	public IDownloadAutocertificazione getDownloadInfo() {
		return downloadInfo;
	}


	
	/** {@inheritDoc} */
	@Override
	public void setDownloadInfo(IDownloadAutocertificazione downloadInfo) {
		this.downloadInfo = downloadInfo;
		this.arricchita=true;
	}


	
	/** {@inheritDoc} */
	@Override
	public IUploadAutocertificazione getUploadInfo() {
		return uploadInfo;
	}


	
	/** {@inheritDoc} */
	@Override
	public void setUploadInfo(IUploadAutocertificazione uploadInfo) {
		this.uploadInfo = uploadInfo;
		this.arricchita=true;
	}


	
	/** {@inheritDoc} */
	@Override
	public boolean isArricchita() {
		return arricchita;
	}


	/** {@inheritDoc} */
	@Override
	public Timestamp getDataUltimaValutazione() {
		return dataUltimaValutazione;
	}


	
	/** {@inheritDoc} */
	@Override
	public void setDataUltimaValutazione(final Timestamp dataUltimaValutazione) {
		this.dataUltimaValutazione = dataUltimaValutazione;
	}


	
	/** {@inheritDoc} */
	@Override
	public Date getDataProssimaValutazione() {
		return dataProssimaValutazione;
	}


	
	/** {@inheritDoc} */
	@Override
	public void setDataProssimaValutazione(final Date dataProssimaValutazione) {
		this.dataProssimaValutazione = dataProssimaValutazione;
	}


	
	/** {@inheritDoc} */
	@Override
	public long getFlagValutazione() {
		return flagValutazione;
	}


	
	/** {@inheritDoc} */
	@Override
	public void setFlagValutazione(final long flagValutazione) {
		this.flagValutazione = flagValutazione;
	}
	
}

package it.toscana.regione.wsods.entity.jpa.impl;

import it.toscana.regione.wsods.entity.jpa.abs.DbEntity;
import it.toscana.regione.wsods.entity.jpa.api.IDownloadAutocertificazione;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the DOWNLOAD_AUTOCERTIFICAZIONE database table.
 * 
 */
@Entity
@Table(name = "DOWNLOAD_AUTOCERTIFICAZIONE")
public class DownloadAutocertificazione extends DbEntity implements IDownloadAutocertificazione {
	
	/** fild serialVersionUID {@like long}*/
	private static final long serialVersionUID = -8455729720807915753L;
	
	
	@Column(name = "ANNO_ESENZIONE", length = 4)
	private String annoEsenzione;
	
	@Column(name = "COD_ASL", length = 6)
	private String codAsl;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "DATA_AUTOCERTIFICAZIONE")
	private Date dataAutocertificazione;
	
	@Column(name = "FK_ESENZIONI_FASCE", nullable = false)
	private long fkEsenzioniFasce;
	
	@Column(name = "FK_RICEVUTA_DOWNLOAD_SOGEI", nullable = false)
	private long fkRicevutaDownloadSogei;
	
	@Column(name = "FLAG_STATO", length = 16)
	private String flagStato;
	
	
	public DownloadAutocertificazione() {}
	
	
	
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
	public long getFkEsenzioniFasce() {
		return fkEsenzioniFasce;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public void setFkEsenzioniFasce(final long fkEsenzioniFasce) {
		this.fkEsenzioniFasce = fkEsenzioniFasce;
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
	
}

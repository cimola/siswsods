package it.toscana.regione.wsods.entity.jpa.impl;

import it.toscana.regione.wsods.entity.jpa.abs.DbEntity;
import it.toscana.regione.wsods.entity.jpa.api.IDownloadCertificazione;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * The persistent class for the DOWNLOAD_CERTIFICAZIONE database table.
 * 
 */
@Entity
@Table(name = "DOWNLOAD_CERTIFICAZIONE")
public class DownloadCertificazione extends DbEntity implements IDownloadCertificazione {
	
	/** fild serialVersionUID {@like long}*/
	private static final long serialVersionUID = -8455729720807915753L;
	
	
	@Column(name = "ANNO_ESENZIONE", length = 4)
	private String annoEsenzione;
	
	@Column(name = "FK_ESENZIONI_FASCE", nullable = false)
	private long fkEsenzioniFasce;
	
	@Column(name = "FK_RICEVUTA_DOWNLOAD_SOGEI", nullable = false)
	private long fkRicevutaDownloadSogei;
	
	
	public DownloadCertificazione() {}
	
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

}

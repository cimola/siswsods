package it.toscana.regione.wsods.entity.jpa.impl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import it.toscana.regione.wsods.entity.jpa.abs.DbEntity;
import it.toscana.regione.wsods.entity.jpa.api.IRecuperoDownloadSogei;


@Entity
@Table(name = "RECUPERO_DOWNLOAD_SOGEI")
public class RecuperoDownloadSogei extends DbEntity implements IRecuperoDownloadSogei {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2329251277505249238L;


	public RecuperoDownloadSogei() { super(); }

	public RecuperoDownloadSogei(final String dataOraLimiteStart,final String dataOraLimiteStop, final String tipo) {
		super();
		this.dataOraLimiteStart = dataOraLimiteStart;
		this.dataOraLimiteStop = dataOraLimiteStop;
		this.tipoAutocertificazione = tipo;
	}

	@Column(name = "DATA_ORA_LIMITE_START", length = 12, nullable = false)
	private String dataOraLimiteStart;
	@Column(name = "DATA_ORA_LIMITE_STOP", length = 12, nullable = false)
	private String dataOraLimiteStop;
	
	@Column(name = "TIPO_AUTOCERTIFICAZIONE", length = 1, nullable = false)
	private String tipoAutocertificazione;
	
	
	@Override
	public String getDataOraLimiteStart() {
		return dataOraLimiteStart;
	}
	
	
	@Override
	public void setDataOraLimiteStart(final String dataOraLimiteStart) {
		this.dataOraLimiteStart = dataOraLimiteStart;
	}
	
	
	@Override
	public String getDataOraLimiteStop() {
		return dataOraLimiteStop;
	}
	
	
	@Override
	public void setDataOraLimiteStop(final String dataOraLimiteStop) {
		this.dataOraLimiteStop = dataOraLimiteStop;
	}

	public String getTipoAutocertificazione() {
		return tipoAutocertificazione;
	}

	public void setTipoAutocertificazione(final String tipo) {
		this.tipoAutocertificazione = tipo;
	}
	
}

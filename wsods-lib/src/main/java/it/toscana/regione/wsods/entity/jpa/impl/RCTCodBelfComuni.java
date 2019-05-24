package it.toscana.regione.wsods.entity.jpa.impl;

import it.toscana.regione.wsods.entity.jpa.api.IRCTCodBelfComuni;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * The persistent class for the COD_BELF_COMUNI database table.
 * 
 */
@Entity
@Table(name = "COD_BELF_COMUNI")
public class RCTCodBelfComuni extends it.toscana.regione.wsods.entity.abs.Entity implements IRCTCodBelfComuni {

	@Transient
	/* * fild serialVersionUID {@like long} */
	private static final long serialVersionUID = 3984490964521772897L;

	@Id
	@Column(name = "PK")
	private String pk;

	@Column(name = "COD_ISTAT")
	private String codIstat;

	@Column(name = "COD_BELFIORE")
	private String codBelfiore;

	@Temporal(TemporalType.DATE)
	@Column(name = "DATA_INIZIO")
	private Date dataInizio;

	@Temporal(TemporalType.DATE)
	@Column(name = "DATA_FINE")
	private Date dataFine;

	public RCTCodBelfComuni() {
		super();
	}

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

	public String getCodIstat() {
		return codIstat;
	}

	public void setCodIstat(String codIstat) {
		this.codIstat = codIstat;
	}

	public String getCodBelfiore() {
		return codBelfiore;
	}

	public void setCodBelfiore(String codBelfiore) {
		this.codBelfiore = codBelfiore;
	}

	public Date getDataInizio() {
		return dataInizio;
	}

	public void setDataInizio(Date dataInizio) {
		this.dataInizio = dataInizio;
	}

	public Date getDataFine() {
		return dataFine;
	}

	public void setDataFine(Date dataFine) {
		this.dataFine = dataFine;
	}

}

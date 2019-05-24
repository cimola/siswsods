package it.toscana.regione.wsods.entity.jpa.impl;

import it.toscana.regione.wsods.entity.jpa.abs.DbEntity;
import it.toscana.regione.wsods.entity.jpa.api.IIdUniDaAggiornare;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "IDUNI_DA_AGGIORNARE")
public class IdUniDaAggiornare extends DbEntity implements IIdUniDaAggiornare {


	private static final long serialVersionUID = 9088266293313113109L;

	@Column(name = "DATA_ULTIMO_AGGIORNAMENTO",  nullable = false )
	private Timestamp dataUltimoAggiornamento;
	
	@Column(name = "SLAVE", nullable = false, length = 24)
	private String slave;

	@Column(name = "MASTER", nullable = false, length = 24)
	private String master;

	@Column(name = "STATO_UTILIZZO", nullable = false)
	private long statoUtilizzo;
	
	
	public IdUniDaAggiornare() { super(); }

	@Override
	public Timestamp getDataUltimoAggiornamento() {
		return dataUltimoAggiornamento;
	}

	@Override
	public void setDataUltimoAggiornamento(final Timestamp dataUltimoAggiornamento) {
		this.dataUltimoAggiornamento = dataUltimoAggiornamento;
	}


	@Override
	public String getSlave() {
		return slave;
	}

	@Override
	public void setSlave(final String slave) {
		this.slave = slave;
	}

	@Override
	public String getMaster() {
		return master;
	}

	@Override
	public void setMaster(final String master) {
		this.master = master;
	}

	@Override
	public long getStatoUtilizzo() {
		return statoUtilizzo;
	}

	@Override
	public void setStatoUtilizzo(final long statoUtilizzo) {
		this.statoUtilizzo = statoUtilizzo;
	}

}

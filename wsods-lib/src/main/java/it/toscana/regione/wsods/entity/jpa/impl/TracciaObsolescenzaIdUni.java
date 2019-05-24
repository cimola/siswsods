package it.toscana.regione.wsods.entity.jpa.impl;

import it.toscana.regione.wsods.entity.jpa.abs.DbEntity;
import it.toscana.regione.wsods.entity.jpa.api.ITracciaObsolescenzaIdUni;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "TRACCIA_OBSOLESCENZA_ID_UNI")
public class TracciaObsolescenzaIdUni extends DbEntity implements ITracciaObsolescenzaIdUni {

	private static final long serialVersionUID = -8095018419857720570L;


	@Column(name = "FK_IDUNI_DA_AGGIORNARE", nullable = false)
	private long fkIdUniDaAggiornare;
	
	@Column(name = "FK_ESENZIONI_FASCE", nullable = false)
	private long fkEsenzioniFasce;
	
	@Column(name = "RUOLO", nullable = false, length = 64)
	private String ruolo;
	
	public TracciaObsolescenzaIdUni() { super(); }

	@Override
	public long getFkIdUniDaAggiornare() {
		return fkIdUniDaAggiornare;
	}

	@Override
	public void setFkIdUniDaAggiornare(final long fkIdUniDaAggiornare) {
		this.fkIdUniDaAggiornare = fkIdUniDaAggiornare;
	}

	@Override
	public long getFkEsenzioniFasce() {
		return fkEsenzioniFasce;
	}

	@Override
	public void setFkEsenzioniFasce(final long fkEsenzioniFasce) {
		this.fkEsenzioniFasce = fkEsenzioniFasce;
	}

	@Override
	public String getRuolo() {
		return ruolo;
	}

	@Override
	public void setRuolo(final String ruolo) {
		this.ruolo = ruolo;
	}

}

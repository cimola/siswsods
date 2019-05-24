package it.toscana.regione.wsods.entity.jpa.impl;

import it.toscana.regione.wsods.entity.jpa.abs.AutocertificazioneEtl;
import it.toscana.regione.wsods.entity.jpa.api.IAutocertificazioneChiusureEtl;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the AUTOCERTIFICAZIONE_CHIUSURE_ETL database table.
 * 
 */
@Entity
@Table(name="AUTOCERTIFICAZIONE_CHIUSURE_ETL")
public class AutocertificazioneChiusureEtl extends  AutocertificazioneEtl implements IAutocertificazioneChiusureEtl {


	private static final long serialVersionUID = 8124001841519496649L;

	
	
	@Column(nullable=false, length=18)
	private String protocollo;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DATA_INIZIO", nullable=false)
	private Date dataInizio;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DATA_FINE_OLD", nullable=false)
	private Date dataFineOld;





	public AutocertificazioneChiusureEtl() {
	}


	/* (non-Javadoc)
	 * @see it.toscana.regione.wsods.entity.jpa.impl.IAutocertificazioneChiusureEtl#getDataFineOld()
	 */
	@Override
	public Date getDataFineOld() {
		return this.dataFineOld;
	}

	/* (non-Javadoc)
	 * @see it.toscana.regione.wsods.entity.jpa.impl.IAutocertificazioneChiusureEtl#setDataFineOld(java.util.Date)
	 */
	@Override
	public void setDataFineOld(final Date dataFineOld) {
		this.dataFineOld = dataFineOld;
	}

	/* (non-Javadoc)
	 * @see it.toscana.regione.wsods.entity.jpa.impl.IAutocertificazioneChiusureEtl#getDataInizio()
	 */
	@Override
	public Date getDataInizio() {
		return this.dataInizio;
	}

	/* (non-Javadoc)
	 * @see it.toscana.regione.wsods.entity.jpa.impl.IAutocertificazioneChiusureEtl#setDataInizio(java.util.Date)
	 */
	@Override
	public void setDataInizio(final Date dataInizio) {
		this.dataInizio = dataInizio;
	}


	/* (non-Javadoc)
	 * @see it.toscana.regione.wsods.entity.jpa.impl.IAutocertificazioneChiusureEtl#getProtocollo()
	 */
	@Override
	public String getProtocollo() {
		return this.protocollo;
	}

	/* (non-Javadoc)
	 * @see it.toscana.regione.wsods.entity.jpa.impl.IAutocertificazioneChiusureEtl#setProtocollo(java.lang.String)
	 */
	@Override
	public void setProtocollo(final String protocollo) {
		this.protocollo = protocollo;
	}


}
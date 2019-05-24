package it.toscana.regione.wsods.entity.jpa.impl;

import it.toscana.regione.wsods.entity.jpa.abs.AutocertificazioneEtl;
import it.toscana.regione.wsods.entity.jpa.api.IAutocertificazioneApertureEtl;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the AUTOCERTIFICAZIONE_APERTURE_ETL database table.
 * 
 */
@Entity
@Table(name="AUTOCERTIFICAZIONE_APERTURE_ETL")
public class AutocertificazioneApertureEtl extends  AutocertificazioneEtl implements IAutocertificazioneApertureEtl {


	private static final long serialVersionUID = 721203828991081334L;

	
	@Temporal(TemporalType.DATE)
	@Column(name="DATA_FINE", nullable=false)
	private Date dataFine;




	public AutocertificazioneApertureEtl() {
	}



	/* (non-Javadoc)
	 * @see it.toscana.regione.wsods.entity.jpa.impl.IAutocertificazioneApertureEtl#getDataFine()
	 */
	@Override
	public Date getDataFine() {
		return this.dataFine;
	}

	/* (non-Javadoc)
	 * @see it.toscana.regione.wsods.entity.jpa.impl.IAutocertificazioneApertureEtl#setDataFine(java.util.Date)
	 */
	@Override
	public void setDataFine(final Date dataFine) {
		this.dataFine = dataFine;
	}






}
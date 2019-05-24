package it.toscana.regione.wsods.entity.jpa.impl;

import it.toscana.regione.wsods.entity.jpa.abs.EsitoInvioEtl;
import it.toscana.regione.wsods.entity.jpa.api.IEsitoInvioApertureEtl;

import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * The persistent class for the ESITO_INVIO_APERTURE_ETL database table.
 * 
 */
@Entity
@Table(name="ESITO_INVIO_APERTURE_ETL")
public class EsitoInvioApertureEtl extends EsitoInvioEtl implements IEsitoInvioApertureEtl {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7798110390329412273L;

	public EsitoInvioApertureEtl() {
		super();
	}

}
package it.toscana.regione.wsods.entity.jpa.impl;

import it.toscana.regione.wsods.entity.jpa.abs.EsitoInvioEtl;
import it.toscana.regione.wsods.entity.jpa.api.IEsitoInvioChiusureEtl;

import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * The persistent class for the ESITO_INVIO_CHIUSURE_ETL database table.
 * 
 */
@Entity
@Table(name="ESITO_INVIO_CHIUSURE_ETL")
public class EsitoInvioChiusureEtl extends EsitoInvioEtl implements IEsitoInvioChiusureEtl {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -5973696196116944264L;

	public EsitoInvioChiusureEtl() {
	}


}
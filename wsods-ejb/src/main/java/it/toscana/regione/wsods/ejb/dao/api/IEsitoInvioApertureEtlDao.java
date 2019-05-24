package it.toscana.regione.wsods.ejb.dao.api;

import it.toscana.regione.wsods.entity.jpa.api.IEsitoInvioApertureEtl;

import javax.ejb.Local;

@Local
public interface IEsitoInvioApertureEtlDao extends IDao<IEsitoInvioApertureEtl> {

	final static String EJB_REF = EJB_REF_ESITO_INVIO_APERTURE_ETL;
}

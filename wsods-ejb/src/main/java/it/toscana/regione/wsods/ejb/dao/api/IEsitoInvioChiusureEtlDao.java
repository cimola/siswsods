package it.toscana.regione.wsods.ejb.dao.api;

import it.toscana.regione.wsods.entity.jpa.api.IEsitoInvioChiusureEtl;

import javax.ejb.Local;

@Local
public interface IEsitoInvioChiusureEtlDao extends IDao<IEsitoInvioChiusureEtl> {

	final static String EJB_REF = IDao.EJB_REF_ESITO_INVIO_CHIUSURE_ETL;
}

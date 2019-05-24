package it.toscana.regione.wsods.ejb.dao.api;

import javax.ejb.Local;

import it.toscana.regione.wsods.entity.jpa.api.IAutocertificazioneApertureEtl;

@Local
public interface IAutocertificazioneApertureEtlDao extends IAutocertificazioneEtlDao<IAutocertificazioneApertureEtl> {

	final static String EJB_REF = IDao.EJB_REF_AUTOCERTIFICAZIONE_APERTURE_ETL;
}

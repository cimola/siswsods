package it.toscana.regione.wsods.ejb.dao.api;

import it.toscana.regione.wsods.entity.jpa.api.IAutocertificazioneChiusureEtl;

import javax.ejb.Local;

@Local
public interface IAutocertificazioneChiusureEtlDao extends IAutocertificazioneEtlDao<IAutocertificazioneChiusureEtl> {

	final static String EJB_REF = IDao.EJB_REF_AUTOCERTIFICAZIONE_CHIUSURE_ETL;
	
}

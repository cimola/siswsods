package it.toscana.regione.wsods.ejb.dao.impl;

import it.toscana.regione.wsods.ejb.dao.abs.AutocertificazioniEtlDao;
import it.toscana.regione.wsods.ejb.dao.api.IAutocertificazioneApertureEtlDao;
import it.toscana.regione.wsods.entity.jpa.api.IAutocertificazioneApertureEtl;

import javax.ejb.Stateless;

@Stateless(name = IAutocertificazioneApertureEtlDao.EJB_REF)
public class AutocertificazioniApertureEtlDao extends AutocertificazioniEtlDao<IAutocertificazioneApertureEtl> implements IAutocertificazioneApertureEtlDao {

	private static final long serialVersionUID = -3723335317061171313L;

	@Override
	protected Class<IAutocertificazioneApertureEtl> clazz() { return IAutocertificazioneApertureEtl.class; }

	@Override
	protected String refTable() { return clazz().getName(); }

	@Override
	public String ejbRef() { return EJB_REF; }



}

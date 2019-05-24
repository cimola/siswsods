package it.toscana.regione.wsods.ejb.dao.impl;

import it.toscana.regione.wsods.ejb.dao.abs.AutocertificazioniEtlDao;
import it.toscana.regione.wsods.ejb.dao.api.IAutocertificazioneChiusureEtlDao;
import it.toscana.regione.wsods.entity.jpa.api.IAutocertificazioneChiusureEtl;

import javax.ejb.Stateless;

@Stateless(name = IAutocertificazioneChiusureEtlDao.EJB_REF)
public class AutocertificazioniChiusureEtlDao extends AutocertificazioniEtlDao<IAutocertificazioneChiusureEtl> implements IAutocertificazioneChiusureEtlDao {

	private static final long serialVersionUID = 7755549917433524125L;

	@Override
	protected Class<IAutocertificazioneChiusureEtl> clazz() { return IAutocertificazioneChiusureEtl.class; }

	@Override
	protected String refTable() { return clazz().getName(); }

	@Override
	public String ejbRef() { return EJB_REF; }

}

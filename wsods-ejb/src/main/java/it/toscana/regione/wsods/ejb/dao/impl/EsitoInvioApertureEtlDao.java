package it.toscana.regione.wsods.ejb.dao.impl;

import it.toscana.regione.wsods.ejb.dao.abs.EntityDao;
import it.toscana.regione.wsods.ejb.dao.api.IEsitoInvioApertureEtlDao;
import it.toscana.regione.wsods.entity.jpa.api.IEsitoInvioApertureEtl;

import javax.ejb.Stateless;

@Stateless(name = IEsitoInvioApertureEtlDao.EJB_REF)
public class EsitoInvioApertureEtlDao extends EntityDao<IEsitoInvioApertureEtl> implements IEsitoInvioApertureEtlDao {

	private static final long serialVersionUID = -3723335317061171313L;

	@Override
	protected Class<IEsitoInvioApertureEtl> clazz() { return IEsitoInvioApertureEtl.class; }

	@Override
	protected String refTable() { return clazz().getName(); }

	@Override
	public String ejbRef() { return EJB_REF; }



}

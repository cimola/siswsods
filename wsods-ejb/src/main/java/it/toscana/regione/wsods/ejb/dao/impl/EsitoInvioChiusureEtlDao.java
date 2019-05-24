package it.toscana.regione.wsods.ejb.dao.impl;

import it.toscana.regione.wsods.ejb.dao.abs.EntityDao;
import it.toscana.regione.wsods.ejb.dao.api.IEsitoInvioChiusureEtlDao;
import it.toscana.regione.wsods.entity.jpa.api.IEsitoInvioChiusureEtl;

import javax.ejb.Stateless;

@Stateless(name = IEsitoInvioChiusureEtlDao.EJB_REF)
public class EsitoInvioChiusureEtlDao extends EntityDao<IEsitoInvioChiusureEtl> implements IEsitoInvioChiusureEtlDao {

	private static final long serialVersionUID = -2610727955642978091L;

	@Override
	protected Class<IEsitoInvioChiusureEtl> clazz() { return IEsitoInvioChiusureEtl.class; }

	@Override
	protected String refTable() { return clazz().getName(); }

	@Override
	public String ejbRef() { return EJB_REF; }



}

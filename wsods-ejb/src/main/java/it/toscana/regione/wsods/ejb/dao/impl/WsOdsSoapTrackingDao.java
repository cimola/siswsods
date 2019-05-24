package it.toscana.regione.wsods.ejb.dao.impl;

import it.toscana.regione.wsods.ejb.dao.abs.EntityDao;
import it.toscana.regione.wsods.ejb.dao.api.IWsOdsSoapTrackingDao;
import it.toscana.regione.wsods.entity.jpa.api.IWsodsSoapTracking;

import javax.ejb.Stateless;

@Stateless(name = IWsOdsSoapTrackingDao.EJB_REF)
public class WsOdsSoapTrackingDao extends EntityDao<IWsodsSoapTracking> implements IWsOdsSoapTrackingDao {

	private static final long serialVersionUID = 1514489489590600432L;

	/** {@inheritDoc} */ @Override public String ejbRef() { return IWsOdsSoapTrackingDao.EJB_REF; }

	/** {@inheritDoc} */ @Override protected Class<IWsodsSoapTracking> clazz() { return IWsodsSoapTracking.class; }

	/** {@inheritDoc} */ @Override protected String refTable() { return IWsodsSoapTracking.class.getName(); }
	
	
}

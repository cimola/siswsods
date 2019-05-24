package it.toscana.regione.wsods.ejb.svecchiatore.abs;

import it.toscana.regione.wsods.ejb.dao.api.IDao;
import it.toscana.regione.wsods.ejb.dao.api.IWsOdsSoapTrackingDao;
import it.toscana.regione.wsods.ejb.svecchiatore.api.ISvecchiatore;
import it.toscana.regione.wsods.entity.jpa.api.IWsodsSoapTracking;

import javax.ejb.EJB;


public abstract class SoapTrackingSvecchiatore extends AbstractSvecchiatore<IWsodsSoapTracking> implements ISvecchiatore<IWsodsSoapTracking> {
	
	private static final long serialVersionUID = 3871892575443891468L;

	
	@EJB(beanName= IWsOdsSoapTrackingDao.EJB_REF)
	private IWsOdsSoapTrackingDao dao;
	
	public SoapTrackingSvecchiatore() {
		super();
	}

	protected abstract int recordMinimiMantenuti();
	protected abstract long deltaSvecchiatura();
	
	
	@Override
	protected IDao<IWsodsSoapTracking> getDao() {
		return dao;
	}

	@Override
	protected Class<IWsodsSoapTracking> getTable() {
		return IWsodsSoapTracking.class;
	}

	

	
}

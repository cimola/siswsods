package it.toscana.regione.wsods.ejb.tracker.impl;

import it.toscana.regione.wsods.ejb.dao.api.IWsOdsSoapTrackingDao;
import it.toscana.regione.wsods.ejb.tracker.api.ISoapTracking;
import it.toscana.regione.wsods.entity.jpa.api.IWsodsSoapTracking;
import it.toscana.regione.wsods.entity.jpa.impl.WsodsSoapTracking;

import java.sql.Timestamp;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;


@Stateless(name=ISoapTracking.EJB_REF)
public class SoapTracking implements ISoapTracking {
	
	
	private static final long serialVersionUID = 7232207422851199403L;


	@EJB(beanName= IWsOdsSoapTrackingDao.EJB_REF)
	private IWsOdsSoapTrackingDao dao;
	
	public SoapTracking() {
		super();
	}
	
	private IWsodsSoapTracking gen(final String src,final String dest){
		final long adesso = System.currentTimeMillis();
		final IWsodsSoapTracking entity  = new WsodsSoapTracking();
		entity.setDataInserimento(new Timestamp(adesso));
		entity.setDataAggiornamento(new Timestamp(adesso));
		entity.setSrc(src);
		entity.setDest(dest);
		return entity;
	}
	
	
	
	
	@Override @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public long tracke(final boolean enable, final String src, final String dest) {
		return tracke(enable, src, dest,0L);
	}
	@Override @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void removeTracke(final boolean enable, final long id) {
		if(enable){ dao.remove(id); } 
	}
	@Override @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public long tracke(final boolean enable, final String src,final String dest, final long fkLink) {
		if(enable){
			final IWsodsSoapTracking entity  = gen(src,dest);
			
			entity.setFkLink(fkLink);
			
			dao.insert(entity, true);
			
			return entity.getId();
		} else {
			return Long.MIN_VALUE;
		}
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void trackeReqest(final boolean enable, final long id, final String request) {
		if(enable){
			final long adesso = System.currentTimeMillis();
			final IWsodsSoapTracking entity = dao.get(id);
			entity.setRequest(request);
			entity.setDataRequest(new Timestamp(adesso));
			entity.setDataAggiornamento(new Timestamp(adesso));
			dao.flush();
		}
		
	}
	
	@Override @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void trackeResponse(final boolean enable, final long id, final String response) {
		if(enable){
			final long adesso = System.currentTimeMillis();
			final IWsodsSoapTracking entity = dao.get(id);
			entity.setResponse(response);
			entity.setDataResponse(new Timestamp(adesso));
			entity.setDataAggiornamento(new Timestamp(adesso));
			dao.flush();
		}
		
	}

	
	
	@Override @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void trackeOk(final boolean enable, final long id) {
		if(enable){
			final long adesso = System.currentTimeMillis();
			final IWsodsSoapTracking entity = dao.get(id);
			entity.setEsito("OK");
			entity.setDataAggiornamento(new Timestamp(adesso));
			dao.flush();
		}
	}
	
	
	@Override @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void trackeKo(final boolean enable, final long id) {
		if(enable){
			final long adesso = System.currentTimeMillis();
			final IWsodsSoapTracking entity = dao.get(id);
			entity.setEsito("KO");
			entity.setDataAggiornamento(new Timestamp(adesso));
			dao.flush();
		}
	}
	
	
	@Override @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void trackeKo(final boolean enable, final long id, final String stackTrace) {
		if(enable){
			final long adesso = System.currentTimeMillis();
			final IWsodsSoapTracking entity = dao.get(id);
			entity.setStackTrace(stackTrace);
			entity.setEsito("KO");
			entity.setDataAggiornamento(new Timestamp(adesso));
			dao.flush();
		}
	}


	
	
}

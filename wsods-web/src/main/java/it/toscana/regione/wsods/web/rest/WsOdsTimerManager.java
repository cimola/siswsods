package it.toscana.regione.wsods.web.rest;

import it.toscana.regione.wsods.costanti.Rest;
import it.toscana.regione.wsods.ejb.timer.api.ITimer;
import it.toscana.regione.wsods.ejb.timer.bean.InfoTimer;
import it.toscana.regione.wsods.singleton.PIL;
import it.toscana.regione.wsods.web.util.TimerUtil;

import java.io.Serializable;
import java.util.Collection;

import javax.ejb.EJBException;
import javax.ejb.NoSuchObjectLocalException;
import javax.ejb.Timer;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Path(Rest.DOMAIN_TIMER_MANAGER)
public class WsOdsTimerManager {
	
	private final static Logger	LOG	= LoggerFactory.getLogger(WsOdsTimerManager.class);
	
	public WsOdsTimerManager() { super(); }
	
	@GET
	@Path(value = Rest.METHOD_LIST)
	@Produces(javax.ws.rs.core.MediaType.TEXT_PLAIN)
	public String list(@Context HttpServletRequest httpServletRequest) {
		LOG.info("ricevuta richiesta al metodo list");
		final StringBuffer sb = new StringBuffer("");
		for(final String ejbRef : TimerUtil.getListaEjbRefTimer()){
			sb.append(ejbRef).append("\n");
		}
		return sb.toString();
	}
	@GET
	@Path(value = Rest.METHOD_STATUS_ALL)
	@Produces(javax.ws.rs.core.MediaType.TEXT_PLAIN)
	public String statusAll(@Context HttpServletRequest httpServletRequest){
		LOG.info("ricevuta richiesta al metodo statusAll");
		final StringBuffer sb = new StringBuffer("");
		for(final String ejbRef : TimerUtil.getListaEjbRefTimer()){
			sb.append(status(ejbRef)).append("\n");
		}
		return sb.toString();
	}
	@GET
	@Path(value = Rest.METHOD_STATUS)
	@Produces(javax.ws.rs.core.MediaType.TEXT_PLAIN)
	public String status(@Context HttpServletRequest httpServletRequest, @QueryParam(Rest.PARAM_EJB_REF) String ejbRef) {
		LOG.info("ricevuta richiesta al metodo status ejbRef[{}]",ejbRef);
		if(ejbRef== null) {return "parametreo mancante : ejbRef is null\n";}
		if(ejbRef!= null && TimerUtil.getListaEjbRefTimer().contains(ejbRef.trim())){
			return status(ejbRef);
		}else {
			return "ejbRef "+ejbRef.trim()+"non adatto\n";
		}	
	}

	private static String status(String ejbRef) throws IllegalStateException, NoSuchObjectLocalException, EJBException {
		try{
			final ITimer timerManager = PIL.lookup(ejbRef.trim(),ITimer.class);	
			Collection< Timer > timers = timerManager.getTimers();
			final StringBuffer sb = new StringBuffer("");
			for(final Timer timer : timers){
				final Serializable info = timer.getInfo();
				if(info instanceof InfoTimer){
					if ((ejbRef.trim()).equals(((InfoTimer)info).getEjbRef())){
						sb.append("Attivo "+((InfoTimer)info).toString()).append("\n");
					}
				} else {
					LOG.warn("trovato timer con info non conforme");
				}
			}
			if(sb.toString().trim().length()>0){
				return sb.toString();
			} else {
				return "Disattivo "+ejbRef.trim()+"\n";
			}
		} catch (NamingException e) {
			LOG.error("Impossiblie effettuare la lookup dei timer",e);
			return "Impossiblie effettuare la lookup dei timer "+e.getMessage()+"\n";
		}
	}
	
	@POST
	@Path(value = Rest.METHOD_CREATE)
	@Produces(javax.ws.rs.core.MediaType.TEXT_PLAIN)
	public String create(@Context HttpServletRequest httpServletRequest, @QueryParam(Rest.PARAM_EJB_REF) String ejbRef, @QueryParam(Rest.PARAM_NUMERO) long numero,@QueryParam(Rest.PARAM_TIMEOUT) long timeout) {
		LOG.info("ricevuta richiesta al metodo start ejbRef[{}], numero[{}], timeout[{}]",ejbRef,numero,timeout);
		if(ejbRef== null) {return "parametreo mancante : ejbRef is null \n";}
		TimerUtil.destroyTimer(ejbRef);
		final int started = TimerUtil.initTimer(ejbRef, numero, timeout);
		return "timer "+ejbRef+" avviati: "+started+"\n";
	}
	
	@PUT
	@Path(value = Rest.METHOD_START)
	@Produces(javax.ws.rs.core.MediaType.TEXT_PLAIN)
	public String start(@Context HttpServletRequest httpServletRequest, @QueryParam(Rest.PARAM_EJB_REF) String ejbRef) {
		LOG.info("ricevuta richiesta al metodo status restart[{}]",ejbRef);
		if(ejbRef== null) {return "parametreo mancante : ejbRef is null\n";}
		if(ejbRef!= null && TimerUtil.getListaEjbRefTimer().contains(ejbRef.trim())){
			TimerUtil.destroyTimer(ejbRef);
			final int started = TimerUtil.initTimer(ejbRef.trim());
			return "timer "+ejbRef+" avviati: "+started+"\n";
		} else {
			return "nessun timer "+ejbRef+" avviato\n";
		}
	}
	
	@DELETE
	@Path(value = Rest.METHOD_STOP)
	@Produces(javax.ws.rs.core.MediaType.TEXT_PLAIN)
	public String stop(@Context HttpServletRequest httpServletRequest, @QueryParam(Rest.PARAM_EJB_REF) String ejbRef) {
		LOG.info("ricevuta richiesta al metodo status stop[{}]",ejbRef);
		if(ejbRef!= null && TimerUtil.getListaEjbRefTimer().contains(ejbRef.trim())){
			TimerUtil.destroyTimer(ejbRef.trim());
			return "timer "+ejbRef+" stoppati \n";
		}
		return "nessun timer "+ejbRef+" stoppato \n";
	}

	@PUT
	@Path(value = Rest.METHOD_START_ALL)
	@Produces(javax.ws.rs.core.MediaType.TEXT_PLAIN)
	public String startAll(@Context HttpServletRequest httpServletRequest) {
		LOG.info("ricevuta richiesta al metodo startAll");
		TimerUtil.destroyTimer();
		TimerUtil.initTimer();
		return "riavviati tutti i timer come da configurazione\n";
	}
	
	@DELETE
	@Path(value = Rest.METHOD_STOP_ALL)
	@Produces(javax.ws.rs.core.MediaType.TEXT_PLAIN)
	public String stopAll(@Context HttpServletRequest httpServletRequest) {
		LOG.info("ricevuta richiesta al metodo stopAll");
		TimerUtil.destroyTimer();
		return "stoppati tutti i timer\n";
	}
}

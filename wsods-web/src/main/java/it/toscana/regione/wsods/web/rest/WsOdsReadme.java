package it.toscana.regione.wsods.web.rest;

import it.toscana.regione.wsods.costanti.Rest;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Path(Rest.DOMAIN_ROOT)
public class WsOdsReadme {
	
	private final static Logger	LOG	= LoggerFactory.getLogger(WsOdsReadme.class);
	
	public WsOdsReadme() { super(); }
	
	@GET
	@Path(value = Rest.METHOD_READMI)
	@Produces(javax.ws.rs.core.MediaType.TEXT_PLAIN)
	public String readme(@Context HttpServletRequest httpServletRequest) {
		LOG.info("ricevuta richiesta al metodo readme");
		final StringBuffer sb = new StringBuffer("");
		
		sb.append("qui di seguito servizi disponibili\n");

		sb.append("\n");
		sb.append("   curl -X POST \"http://<HOST>:<PORT>/wsods/download/recupero/config?min=<TIME>&max=<TIME>&tipo=<TIPO>\"                 \n");
		sb.append("\n");
		sb.append("   curl -X POST \"http://<HOST>:<PORT>/wsods/download/corrente/fake-last-download?time=<TIME>&tipo=<TIPO>\"               \n");
		
		sb.append("\n");
		sb.append("\n");
		sb.append("   curl -X GET \"http://<HOST>:<PORT>/wsods/timer-manager/list\"                                                          \n");
		sb.append("   curl -X GET \"http://<HOST>:<PORT>/wsods/timer-manager/statusAll\"                                                     \n");
		sb.append("   curl -X GET \"http://<HOST>:<PORT>/wsods/timer-manager/status?ejbRef=<EJB-REF>\"                                       \n");
		sb.append("\n");
		sb.append("   curl -X PUT \"http://<HOST>:<PORT>/wsods/timer-manager/startAll\"                                                      \n");
		sb.append("   curl -X PUT \"http://<HOST>:<PORT>/wsods/timer-manager/start?ejbRef=<EJB-REF>\"                                        \n");
		sb.append("\n");
		sb.append("   curl -X DELETE \"http://<HOST>:<PORT>/wsods/timer-manager/stopAll\"                                                    \n");
		sb.append("   curl -X DELETE \"http://<HOST>:<PORT>/wsods/timer-manager/stop?ejbRef=<EJB-REF>\"                                      \n");
		sb.append("\n");
		sb.append("   curl -X POST \"http://<HOST>:<PORT>/wsods/timer-manager/create?ejbRef=<EJB-REF>&numero=<NUMERO>^&timeout=<TIMEOUT>\"   \n");
		sb.append("\n");
		
		return sb.toString();
	}
	

}

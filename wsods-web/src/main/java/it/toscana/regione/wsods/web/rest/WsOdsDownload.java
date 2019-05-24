package it.toscana.regione.wsods.web.rest;

import it.toscana.regione.wsods.costanti.Rest;
import it.toscana.regione.wsods.web.util.RecuperoDownloadUtil;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Path(Rest.DOMAIN_DOWNLOAD)
public class WsOdsDownload {
	
	private final static Logger	LOG	= LoggerFactory.getLogger(WsOdsDownload.class);
	
	public WsOdsDownload() { super(); }
	
	@POST
	@Path(value = Rest.METHOD_CONFIG)
	@Produces(javax.ws.rs.core.MediaType.TEXT_PLAIN)
	public String config(@Context HttpServletRequest httpServletRequest, @QueryParam(Rest.PARAM_MAX) String dataOraLimiteMax, @QueryParam(Rest.PARAM_MIN) String dataOraLimiteMin, @QueryParam(Rest.PARAM_TIPO) String tipoDownload) {
		LOG.info("ricevuta richiesta al metodo config");
		final StringBuffer sb = new StringBuffer("");
		boolean flag = true; 
		if(!RecuperoDownloadUtil.validaDataOraLimite(dataOraLimiteMax, tipoDownload)){
			sb.append("valore paramentro non valido "+Rest.PARAM_MAX+" ["+dataOraLimiteMax+"]\n");
			flag = false; 
		}
		if(!RecuperoDownloadUtil.validaDataOraLimite(dataOraLimiteMin, tipoDownload)){
			sb.append("valore paramentro non valido "+Rest.PARAM_MIN+" ["+dataOraLimiteMin+"]\n");
			flag = false;
		}
		if(!RecuperoDownloadUtil.validaTipoDownload(tipoDownload)){
			sb.append("valore paramentro non valido "+Rest.PARAM_TIPO+" ["+tipoDownload+"]\n");
			flag = false;
		}
		if(flag){
			sb.append(RecuperoDownloadUtil.configuraDownloadSogeiRecupero(dataOraLimiteMax,dataOraLimiteMin,tipoDownload));
		} else {
			sb.append("! Configurazione Fallita !\n");
		}
		return sb.toString();
	}
	
	@POST 
	@Path(value = Rest.METHOD_FAKE_LAST_DOWNLOAD)
	@Produces(javax.ws.rs.core.MediaType.TEXT_PLAIN)
	public String fakeDownloadSogei(@Context HttpServletRequest httpServletRequest, @QueryParam(Rest.PARAM_TIME) String fakeDataOraLimite, @QueryParam(Rest.PARAM_TIPO) String tipoDownload) {
		LOG.info("ricevuta richiesta al metodo fakeDownloadSogei");
		final StringBuffer sb = new StringBuffer("");

		boolean flag = true; 
		if(!RecuperoDownloadUtil.validaDataOraLimite(fakeDataOraLimite, tipoDownload)){
			sb.append("valore paramentro non valido "+Rest.PARAM_TIME+" ["+fakeDataOraLimite+"]\n");
			flag = false; 
		}

		if(!RecuperoDownloadUtil.validaTipoDownload(tipoDownload)){
			sb.append("valore paramentro non valido "+Rest.PARAM_TIPO+" ["+tipoDownload+"]\n");
			flag = false;
		}
		
		if(flag){
			sb.append(RecuperoDownloadUtil.skypDownloadSogeiCorrente(fakeDataOraLimite, tipoDownload));
			
		} else {
			sb.append("! Configurazione Fallita !");
		}
		return sb.toString();
	}
}

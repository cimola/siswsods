/** */
package it.toscana.regione.wsods.ejb.ws.api;

import javax.ejb.Local;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.Provider;


/**
 * @author cciurli
 *
 */
@Local
public interface IWsOds extends Provider<SOAPMessage> {

	final static String EJB_REF_WS_ODS = "WsOds";
	final static String EJB_REF_WS_ODS_UPLOAD = "WsOdsUpload";
	final static String EJB_REF_WS_ODS_GET_LISTA = "WsOdsGetLista";
	
	
	
	@Override
	public SOAPMessage invoke(final SOAPMessage request);
}

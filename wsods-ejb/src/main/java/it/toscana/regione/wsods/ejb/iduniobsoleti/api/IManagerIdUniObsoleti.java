package it.toscana.regione.wsods.ejb.iduniobsoleti.api;

import it.toscana.regione.wsods.exception.WsOdsRuntimeException;

import javax.ejb.Local;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

@Local
public interface IManagerIdUniObsoleti {


	public final static String 	EJB_REF	= "managerIdUniObsoleti";
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	void recuperaObsoleti() throws WsOdsRuntimeException;

	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	void aggiornaObsoleti() throws WsOdsRuntimeException;
}

package it.toscana.regione.wsods.ejb.ricalcolatitolonull.api;

import it.toscana.regione.wsods.exception.WsOdsRuntimeException;

import javax.ejb.Local;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

@Local
public interface IManagerRicalcolaTitoloNull {

	public final static String 	EJB_REF	= "managerRicalcolaTitoloNull";


	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	void ricalcolaTitoloNull(final long numeroTimer,final long discriminante) throws WsOdsRuntimeException;
	
}

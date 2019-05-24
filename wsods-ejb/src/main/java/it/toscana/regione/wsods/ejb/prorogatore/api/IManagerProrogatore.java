package it.toscana.regione.wsods.ejb.prorogatore.api;

import it.toscana.regione.wsods.exception.WsOdsRuntimeException;

import javax.ejb.Local;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;


@Local
public interface IManagerProrogatore {
	

	public final static String 	EJB_REF	= "managerProrogatore";
	
//	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
//	void proroga() throws WsOdsRuntimeException;
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	void proroga(final long numeroTimer, final long discriminante) throws WsOdsRuntimeException;
}

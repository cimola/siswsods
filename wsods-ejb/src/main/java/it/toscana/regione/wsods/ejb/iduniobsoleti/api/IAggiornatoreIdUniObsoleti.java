package it.toscana.regione.wsods.ejb.iduniobsoleti.api;

import it.toscana.regione.wsods.exception.WsOdsRuntimeException;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

@Local
public interface IAggiornatoreIdUniObsoleti {

	public final static String 	EJB_REF	= "aggiornatoreIdUniObsoleti";
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	List<Long> recuperaIdLinkDaElaborare() throws WsOdsRuntimeException;

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	void riportaAssociazione(final Long associazione) ;
	
	
}

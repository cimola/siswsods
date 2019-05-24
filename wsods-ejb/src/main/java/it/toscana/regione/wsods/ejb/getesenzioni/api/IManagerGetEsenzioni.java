package it.toscana.regione.wsods.ejb.getesenzioni.api;

import it.toscana.regione.wsods.exception.GetEsenzioniException;

import javax.ejb.Local;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPMessage;

@Local
public interface IManagerGetEsenzioni {

	
	final static String EJB_REF = "ManagerGetEsenzioni";

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	void getEsenzioni(final SOAPMessage request, final SOAPBody bodyResponse) throws GetEsenzioniException;
}

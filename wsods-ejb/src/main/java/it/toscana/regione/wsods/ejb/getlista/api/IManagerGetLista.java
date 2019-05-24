/** */
package it.toscana.regione.wsods.ejb.getlista.api;

import it.toscana.regione.wsods.exception.GetListaException;

import javax.ejb.Local;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPMessage;


/**
 * @author cciurli
 *
 */
@Local
public interface IManagerGetLista{
	
	final static String EJB_REF = "ManagerGetLista";

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	void getLista(final SOAPMessage request, final SOAPBody bodyResponse) throws GetListaException;
}

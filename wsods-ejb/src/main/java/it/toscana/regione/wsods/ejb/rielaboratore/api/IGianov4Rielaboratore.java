/** */
package it.toscana.regione.wsods.ejb.rielaboratore.api;

import it.toscana.regione.wsods.exception.RielaboratoreException;

import javax.ejb.Local;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;


/**
 * @author gorlando
 *
 */
@Local
public interface IGianov4Rielaboratore {


	public final static String 	EJB_REF	= "gianov4Rielaboratore";

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	void elaboraAutocertificazioneScartate(final Long id) throws RielaboratoreException;
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	void elaboraCertificazioneScartate(final Long id) throws RielaboratoreException;
}

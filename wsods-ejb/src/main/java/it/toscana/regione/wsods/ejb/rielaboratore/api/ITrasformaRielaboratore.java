/** */
package it.toscana.regione.wsods.ejb.rielaboratore.api;

import it.toscana.regione.wsods.entity.jpa.api.IAutocertificazioneTmp;
import it.toscana.regione.wsods.entity.jpa.api.ICertificazioneTmp;
import it.toscana.regione.wsods.exception.RielaboratoreException;

import javax.ejb.Local;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;


/**
 * @author cciurli
 *
 */
@Local
public interface ITrasformaRielaboratore {


	public final static String 	EJB_REF	= "trasformaRielaboratore";

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	void elabora(final IAutocertificazioneTmp autocertificazioneTmp) throws RielaboratoreException;
	void elabora(final ICertificazioneTmp certificazioneTmp) throws RielaboratoreException;
}

/** */
package it.toscana.regione.wsods.ejb.dao.api;

import it.toscana.regione.wsods.exception.DaoException;

import java.io.Serializable;

import javax.ejb.Local;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

/**
 * @author gorlando
 */
@Local
public interface IRCTCodBelfStatiDao extends Serializable {

	final static String EJB_REF = "RCTCodBelfStatiDao";

	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	String getCodiceIstatByCodBelfiore(final String codBelfiore) throws DaoException;

}

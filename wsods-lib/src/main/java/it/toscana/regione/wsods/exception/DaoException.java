/** */
package it.toscana.regione.wsods.exception;

import it.toscana.regione.wsods.type.Code;

/**
 * @author cciurli
 *
 */
public class DaoException extends EjbException {

	/** fild serialVersionUID {@like long}*/
	private static final long	serialVersionUID	= 2937251646126922222L;


	public DaoException() {
		this(Code.DAO_GENERICO);
	}


	/**
	 * @param code
	 */
	public DaoException(final Code code) {
		super(code);
	}


	/**
	 * @param code
	 * @param message
	 */
	public DaoException(final Code code, final String message) {
		super(code, message);
	}


	public DaoException(final String message, final WsOdsException cause) {
		super(message, cause);
	}


	public DaoException(final WsOdsException cause) {
		super(cause);
	}


	/**
	 * @param code
	 * @param cause
	 */
	public DaoException(final Code code, final Throwable cause) {
		super(code, cause);
	}


	/**
	 * @param code
	 * @param message
	 * @param cause
	 */
	public DaoException(final Code code, final String message, final Throwable cause) {
		super(code, message, cause);
	}


	public DaoException(final String message, final WsOdsRuntimeException cause) {
		super(message, cause);
	}


	public DaoException(final WsOdsRuntimeException cause) {
		super(cause);
	}

}

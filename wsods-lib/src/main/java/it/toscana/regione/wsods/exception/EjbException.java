/** */
package it.toscana.regione.wsods.exception;

import it.toscana.regione.wsods.type.Code;

/**
 * @author cciurli
 *
 */
public class EjbException extends WsOdsRuntimeException {

	public EjbException(final String message, final WsOdsRuntimeException cause) {
		super(message, cause);
	}


	public EjbException(final WsOdsRuntimeException cause) {
		super(cause);
	}

	private static final long	serialVersionUID	= 8744369301185982912L;


	public EjbException() {
		this(Code.EJB_GENERICO);
	}


	/**
	 * @param code
	 */
	public EjbException(final Code code) {
		super(code);
	}


	/**
	 * @param code
	 * @param message
	 */
	public EjbException(final Code code, final String message) {
		super(code, message);
	}


	public EjbException(final String message, final WsOdsException cause) {
		super(message, cause);
	}


	public EjbException(final WsOdsException cause) {
		super(cause);
	}


	/**
	 * @param code
	 * @param cause
	 */
	public EjbException(final Code code, final Throwable cause) {
		super(code, cause);
	}


	/**
	 * @param code
	 * @param message
	 * @param cause
	 */
	public EjbException(final Code code, final String message, final Throwable cause) {
		super(code, message, cause);
	}

}

/** */
package it.toscana.regione.wsods.exception;

import it.toscana.regione.wsods.type.Code;

/**
 * @author cciurli
 *
 */
public class RielaboratoreException extends EjbException {


	private static final long serialVersionUID = 7047834788247763944L;


	public RielaboratoreException(final Code code) {
		super(code);
	}


	public RielaboratoreException(final Code code, final String message) {
		super(code, message);
	}


	public RielaboratoreException(final String message, final WsOdsRuntimeException cause) {
		super(message, cause);
	}


	public RielaboratoreException(final WsOdsRuntimeException cause) {
		super(cause);
	}


	public RielaboratoreException(final Code code, final Throwable cause) {
		super(code, cause);
	}


	public RielaboratoreException(final Code code, final String message, final Throwable cause) {
		super(code, message, cause);
	}


	public RielaboratoreException(final String message, final WsOdsException cause) {
		super(message, cause);
	}


	public RielaboratoreException(final WsOdsException cause) {
		super(cause);
	}

}

/** */
package it.toscana.regione.wsods.exception;

import it.toscana.regione.wsods.type.Code;

/**
 * @author cciurli
 *
 */
public class TransformException extends EjbException {

	private static final long serialVersionUID = -2078974404085093615L;


	public TransformException() {
		this(Code.SDM_GENERICO);
	}


	public TransformException(final Code code) {
		super(code);
	}


	public TransformException(final Code code, final String message) {
		super(code, message);
	}


	public TransformException(final String message, final WsOdsRuntimeException cause) {
		super(message, cause);
	}


	public TransformException(final WsOdsRuntimeException cause) {
		super(cause);
	}


	public TransformException(final Code code, final Throwable cause) {
		super(code, cause);
	}


	public TransformException(final Code code, final String message, final Throwable cause) {
		super(code, message, cause);
	}


	public TransformException(final String message, final WsOdsException cause) {
		super(message, cause);
	}


	public TransformException(final WsOdsException cause) {
		super(cause);
	}

}

/** */
package it.toscana.regione.wsods.exception;

import it.toscana.regione.wsods.type.Code;

/**
 * @author cciurli
 *
 */
public class ConvertitoreException extends WsOdsException {

	private static final long	serialVersionUID	= 5609340352261130544L;

	public ConvertitoreException(final Code code) {
		super(code);
	}

	public ConvertitoreException(final Code code, final String message) {
		super(code, message);
	}

	public ConvertitoreException(final WsOdsException cause) {
		super(cause);
	}

	public ConvertitoreException(final String message, final WsOdsException cause) {
		super(message, cause);
	}

	public ConvertitoreException(final Code code, final Throwable cause) {
		super(code, cause);
	}

	public ConvertitoreException(final Code code, final String message, final Throwable cause) {
		super(code, message, cause);
	}

	public ConvertitoreException(final String message, final WsOdsRuntimeException cause) {
		super(message, cause);
	}

	public ConvertitoreException(final WsOdsRuntimeException cause) {
		super(cause);
	}

}

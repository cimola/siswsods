/** */
package it.toscana.regione.wsods.exception;

import it.toscana.regione.wsods.type.Code;

public class SisDataManagerException extends EjbException {

	private static final long serialVersionUID = 8201941177951894447L;

	public SisDataManagerException() {
		this(Code.SDM_GENERICO);
	}

	public SisDataManagerException(final Code code) {
		super(code);
	}

	public SisDataManagerException(final Code code, final String message) {
		super(code, message);
	}

	public SisDataManagerException(final String message, final WsOdsRuntimeException cause) {
		super(message, cause);
	}

	public SisDataManagerException(final WsOdsRuntimeException cause) {
		super(cause);
	}

	public SisDataManagerException(final Code code, final Throwable cause) {
		super(code, cause);
	}

	public SisDataManagerException(final Code code, final String message, final Throwable cause) {
		super(code, message, cause);
	}

	public SisDataManagerException(final String message, final WsOdsException cause) {
		super(message, cause);
	}

	public SisDataManagerException(final WsOdsException cause) {
		super(cause);
	}

}

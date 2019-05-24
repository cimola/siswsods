/** */
package it.toscana.regione.wsods.exception;

import it.toscana.regione.wsods.type.Code;

/**
 * @author cciurli
 *
 */
public class UploadException extends EjbException {
	
	
	private static final long serialVersionUID = 5464571472587866259L;


	public UploadException(final Code code) {
		super(code);
	}
	
	
	public UploadException(final Code code, final String message) {
		super(code, message);
	}
	
	
	public UploadException(final String message, final WsOdsRuntimeException cause) {
		super(message, cause);
	}
	
	
	public UploadException(final WsOdsRuntimeException cause) {
		super(cause);
	}
	
	
	public UploadException(final Code code, final Throwable cause) {
		super(code, cause);
	}
	
	
	public UploadException(final Code code, final String message, final Throwable cause) {
		super(code, message, cause);
	}
	
	
	public UploadException(final String message, final WsOdsException cause) {
		super(message, cause);
	}
	
	
	public UploadException(final WsOdsException cause) {
		super(cause);
	}
	
}

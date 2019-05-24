/** */
package it.toscana.regione.wsods.exception;

import it.toscana.regione.wsods.type.Code;

/**
 * @author cciurli
 *
 */
public class GetListaException extends EjbException {
	
	private static final long serialVersionUID = -4184790907952602785L;
	
	
	public GetListaException(final Code code) {
		super(code);
	}
	
	
	public GetListaException(final Code code, final String message) {
		super(code, message);
	}
	
	
	public GetListaException(final String message, final WsOdsRuntimeException cause) {
		super(message, cause);
	}
	
	
	public GetListaException(final WsOdsRuntimeException cause) {
		super(cause);
	}
	
	
	public GetListaException(final Code code, final Throwable cause) {
		super(code, cause);
	}
	
	
	public GetListaException(final Code code, final String message, final Throwable cause) {
		super(code, message, cause);
	}
	
	
	public GetListaException(final String message, final WsOdsException cause) {
		super(message, cause);
	}
	
	
	public GetListaException(final WsOdsException cause) {
		super(cause);
	}
	
}

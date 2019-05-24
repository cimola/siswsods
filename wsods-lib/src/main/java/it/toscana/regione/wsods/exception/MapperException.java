/** */
package it.toscana.regione.wsods.exception;

import it.toscana.regione.wsods.type.Code;

/**
 * @author cciurli
 *
 */
public class MapperException extends WsOdsException {
	
	private static final long serialVersionUID = -3409659322041496027L;
	
	
	public MapperException(final Code code) {
		super(code);
	}
	
	
	public MapperException(final Code code, final String message) {
		super(code, message);
	}
	
	
	public MapperException(final WsOdsException cause) {
		super(cause);
	}
	
	
	public MapperException(final String message, final WsOdsException cause) {
		super(message, cause);
	}
	
	
	public MapperException(final Code code, final Throwable cause) {
		super(code, cause);
	}
	
	
	public MapperException(final Code code, final String message, final Throwable cause) {
		super(code, message, cause);
	}
	
	
	public MapperException(final String message, final WsOdsRuntimeException cause) {
		super(message, cause);
	}
	
	
	public MapperException(final WsOdsRuntimeException cause) {
		super(cause);
	}
	
}

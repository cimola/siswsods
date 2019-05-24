package it.toscana.regione.wsods.exception;

import it.toscana.regione.wsods.type.Code;

public class WsException extends WsOdsException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public WsException(final Code code) {
		super(code);
	}
	
	
	public WsException(final Code code, final String message) {
		super(code, message);
	}
	
	
	public WsException(final WsOdsException cause) {
		super(cause);
	}
	
	
	public WsException(final String message, final WsOdsException cause) {
		super(message, cause);
	}
	
	
	public WsException(final WsOdsRuntimeException cause) {
		super(cause);
	}
	
	
	public WsException(final String message, final WsOdsRuntimeException cause) {
		super(message, cause);
	}
	
	
	public WsException(final Code code, final Throwable cause) {
		super(code, cause);
	}
	
	
	public WsException(final Code code, final String message, final Throwable cause) {
		super(code, message, cause);
	}
	
}

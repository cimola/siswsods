package it.toscana.regione.wsods.exception;

import it.toscana.regione.wsods.type.Code;


public class SecurityException extends WsOdsException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3819862349777924967L;


	public SecurityException(Code code) {
		super(code);
	}
	
	
	public SecurityException(Code code, String message) {
		super(code, message);
	}
	
	
	public SecurityException(WsOdsException cause) {
		super(cause);
	}
	
	
	public SecurityException(String message, WsOdsException cause) {
		super(message, cause);
	}
	
	
	public SecurityException(WsOdsRuntimeException cause) {
		super(cause);
	}
	
	
	public SecurityException(String message, WsOdsRuntimeException cause) {
		super(message, cause);
	}
	
	
	public SecurityException(Code code, Throwable cause) {
		super(code, cause);
	}
	
	
	public SecurityException(Code code, String message, Throwable cause) {
		super(code, message, cause);
	}
	
}

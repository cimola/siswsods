package it.toscana.regione.wsods.exception;

import it.toscana.regione.wsods.type.Code;


public class ProrogatoreException extends EjbException {
	
	private static final long serialVersionUID = 1L;


	public ProrogatoreException(String message, WsOdsRuntimeException cause) {
		super(message, cause);
	}
	
	
	public ProrogatoreException(WsOdsRuntimeException cause) {
		super(cause);
	}
	
	
	public ProrogatoreException(Code code) {
		super(code);
	}
	
	
	public ProrogatoreException(Code code, String message) {
		super(code, message);
	}
	
	
	public ProrogatoreException(String message, WsOdsException cause) {
		super(message, cause);
	}
	
	
	public ProrogatoreException(WsOdsException cause) {
		super(cause);
	}
	
	
	public ProrogatoreException(Code code, Throwable cause) {
		super(code, cause);
	}
	
	
	public ProrogatoreException(Code code, String message, Throwable cause) {
		super(code, message, cause);
	}
	
}

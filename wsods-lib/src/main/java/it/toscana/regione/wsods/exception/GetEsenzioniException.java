package it.toscana.regione.wsods.exception;

import it.toscana.regione.wsods.type.Code;


public class GetEsenzioniException extends EjbException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1354756385L;


	public GetEsenzioniException(String message, WsOdsRuntimeException cause) {
		super(message, cause);
	}
	
	
	public GetEsenzioniException(WsOdsRuntimeException cause) {
		super(cause);
	}
	
	
	public GetEsenzioniException() { super(); }
	
	
	public GetEsenzioniException(Code code) {
		super(code);
	}
	
	
	public GetEsenzioniException(Code code, String message) {
		super(code, message);
	}
	
	
	public GetEsenzioniException(String message, WsOdsException cause) {
		super(message, cause);
	}
	
	
	public GetEsenzioniException(WsOdsException cause) {
		super(cause);
	}
	
	
	public GetEsenzioniException(Code code, Throwable cause) {
		super(code, cause);
	}
	
	
	public GetEsenzioniException(Code code, String message, Throwable cause) {
		super(code, message, cause);
	}
	
}

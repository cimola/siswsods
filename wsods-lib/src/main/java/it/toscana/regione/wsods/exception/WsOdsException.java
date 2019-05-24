/** */
package it.toscana.regione.wsods.exception;

import it.toscana.regione.wsods.type.Code;

import javax.ejb.ApplicationException;


/**
 * @author cciurli
 *
 */
@ApplicationException(rollback=true, inherited=true)
public class WsOdsException extends Exception {

	private static final long	serialVersionUID	= 4381944018577306419L;

	public final Code code;

	public WsOdsException(final Code code) {
		super(code.msg);
		this.code = code;
	}

	public WsOdsException(final Code code, final String message) {
		super(code.msg+" - "+message);
		this.code = code;
	}

	public WsOdsException(final WsOdsException cause) {
		super(cause.code.msg,cause);
		this.code =  cause.code;
	}


	public WsOdsException(final String message, final WsOdsException cause) {
		super(cause.code.msg+" - "+message, cause);
		this.code = cause.code;
	}
	
	public WsOdsException(final WsOdsRuntimeException cause) {
		super(cause.code.msg,cause);
		this.code =  cause.code;
	}

	public WsOdsException(final String message, final WsOdsRuntimeException cause) {
		super(cause.code.msg+" - "+message, cause);
		this.code = cause.code;
	}
	
	public WsOdsException(final Code code, final Throwable cause) {
		super(code.msg,cause);
		this.code = code;
	}

	public WsOdsException(final Code code, final String message, final Throwable cause) {
		super(code.msg+" - "+message, cause);
		this.code = code;
	}

}

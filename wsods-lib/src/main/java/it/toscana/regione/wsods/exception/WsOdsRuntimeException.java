/** */
package it.toscana.regione.wsods.exception;

import it.toscana.regione.wsods.type.Code;

import javax.ejb.ApplicationException;

/**
 * @author cciurli
 *
 */
@ApplicationException(rollback=true, inherited=true)
public class WsOdsRuntimeException extends RuntimeException {


	private static final long	serialVersionUID	= 7925271720468648270L;
	
	public final Code code;

	public WsOdsRuntimeException(final Code code) {
		super(code.msg);
		this.code = code;
	}

	public WsOdsRuntimeException(final Code code, final String message) {
		super(code.msg+" - "+message);
		this.code = code;
	}
	
	public WsOdsRuntimeException(final WsOdsException cause) {
		super(cause.code.msg,cause);
		this.code =  cause.code;
	}

	public WsOdsRuntimeException(final String message, final WsOdsException cause) {
		super(cause.code.msg+" - "+message, cause);
		this.code = cause.code;
	}
	
	public WsOdsRuntimeException(final WsOdsRuntimeException cause) {
		super(cause.code.msg,cause);
		this.code =  cause.code;
	}


	public WsOdsRuntimeException(final String message, final WsOdsRuntimeException cause) {
		super(cause.code.msg+" - "+message, cause);
		this.code = cause.code;
	}

	public WsOdsRuntimeException(final Code code, final Throwable cause) {
		super(code.msg,cause);
		this.code = code;
	}

	public WsOdsRuntimeException(final Code code, final String message, final Throwable cause) {
		super(code.msg+" - "+message, cause);
		this.code = code;
	}

}

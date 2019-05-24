/** */
package it.toscana.regione.wsods.exception;

import it.toscana.regione.wsods.type.Code;

/**
 * @author cciurli
 *
 */
public class DownloadException extends EjbException {


	private static final long	serialVersionUID	= 8879233826949577195L;


	public DownloadException(final Code code) {
		super(code);
	}


	public DownloadException(final Code code, final String message) {
		super(code, message);
	}

	public DownloadException(final String message, final WsOdsRuntimeException cause) {
		super(message, cause);
	}


	public DownloadException(final WsOdsRuntimeException cause) {
		super(cause);
	}


	public DownloadException(final Code code, final Throwable cause) {
		super(code, cause);
	}


	public DownloadException(final Code code, final String message, final Throwable cause) {
		super(code, message, cause);
	}


	public DownloadException(final String message, final WsOdsException cause) {
		super(message, cause);
	}


	public DownloadException(final WsOdsException cause) {
		super(cause);
	}

}

/** */
package it.toscana.regione.wsods.exception;

import it.toscana.regione.wsods.type.Code;

/**
 * @author cciurli
 *
 */
public class RicalcolaTitoloNullException extends EjbException {


	private static final long serialVersionUID = 7047834788247763944L;


	public RicalcolaTitoloNullException(final Code code) {
		super(code);
	}


	public RicalcolaTitoloNullException(final Code code, final String message) {
		super(code, message);
	}


	public RicalcolaTitoloNullException(final String message, final WsOdsRuntimeException cause) {
		super(message, cause);
	}


	public RicalcolaTitoloNullException(final WsOdsRuntimeException cause) {
		super(cause);
	}


	public RicalcolaTitoloNullException(final Code code, final Throwable cause) {
		super(code, cause);
	}


	public RicalcolaTitoloNullException(final Code code, final String message, final Throwable cause) {
		super(code, message, cause);
	}


	public RicalcolaTitoloNullException(final String message, final WsOdsException cause) {
		super(message, cause);
	}


	public RicalcolaTitoloNullException(final WsOdsException cause) {
		super(cause);
	}

}

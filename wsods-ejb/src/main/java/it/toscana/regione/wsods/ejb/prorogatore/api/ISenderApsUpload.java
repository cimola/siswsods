package it.toscana.regione.wsods.ejb.prorogatore.api;

import it.toscana.regione.wsods.ejb.sender.api.ISender;

import javax.ejb.Local;


/**
 * @author cciurli
 *
 */
@Local
public interface ISenderApsUpload extends ISender {

	final static String EJB_REF = "senderApsUpload";

}

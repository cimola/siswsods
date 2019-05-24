package it.toscana.regione.wsods.ejb.prorogatore.api;

import it.toscana.regione.autocertificazionireddito.entity.jaxb.rt.UploadWebApp2ApsRequest;
import it.toscana.regione.autocertificazionireddito.entity.jaxb.rt.UploadWebApp2ApsResponse;
import it.toscana.regione.wsods.ejb.transformer.api.ITransform;

import javax.ejb.Local;



/**
 * @author cciurli
 *
 */
@Local
public interface ITransformApsUpload extends ITransform < UploadWebApp2ApsRequest,UploadWebApp2ApsResponse>{

	final static String EJB_REF = "transformApsUpload";


}

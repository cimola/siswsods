/** */
package it.toscana.regione.wsods.ejb.dao.api;

import it.toscana.regione.wsods.entity.jpa.api.IWsodsSoapTracking;

import javax.ejb.Local;


/**
 * @author cciurli
 *
 */
@Local
public interface IWsOdsSoapTrackingDao extends IDao<IWsodsSoapTracking> {
	
	final static String EJB_REF = IDao.EJB_REF_WSODS_SOAP_TRACKING;
	
}

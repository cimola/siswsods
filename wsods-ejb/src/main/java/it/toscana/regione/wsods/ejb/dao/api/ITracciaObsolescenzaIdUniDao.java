/** */
package it.toscana.regione.wsods.ejb.dao.api;

import it.toscana.regione.wsods.entity.jpa.api.ITracciaObsolescenzaIdUni;

import javax.ejb.Local;


/**
 * @author cciurli
 *
 */
@Local
public interface ITracciaObsolescenzaIdUniDao extends IDao<ITracciaObsolescenzaIdUni> {

	final static String EJB_REF = IDao.EJB_REF_TRACCIA_OBSOLESCENZA_ID_UNI;
	

}

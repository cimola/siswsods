/** */
package it.toscana.regione.wsods.ejb.dao.api;

import java.util.List;

import it.toscana.regione.wsods.entity.jpa.api.IRecuperoDownloadSogei;

import javax.ejb.Local;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;


/**
 * @author cciurli
 *
 */
@Local
public interface IRecuperoDownloadSogeiDao  extends IDao<IRecuperoDownloadSogei> {

	final static String EJB_REF = IDao.EJB_REF_RECUPERO_DOWNLOAD_SOGEI;
	
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	void aggiornaConfigurazione(final String min, final String max, final String tipo);
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	List<IRecuperoDownloadSogei> recuperaConfigurazioni();

	
	
	
}

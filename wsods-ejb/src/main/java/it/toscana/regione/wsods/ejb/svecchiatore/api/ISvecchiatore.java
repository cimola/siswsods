/** */
package it.toscana.regione.wsods.ejb.svecchiatore.api;

import java.io.Serializable;

import it.toscana.regione.wsods.entity.jpa.api.IDbEntity;


/**
 * @author cciurli
 *
 */
public interface ISvecchiatore<E extends IDbEntity> extends Serializable {

	
	int svecchia();
	
}

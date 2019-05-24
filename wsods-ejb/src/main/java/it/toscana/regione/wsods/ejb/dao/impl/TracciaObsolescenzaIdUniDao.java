/** */
package it.toscana.regione.wsods.ejb.dao.impl;

import it.toscana.regione.wsods.ejb.dao.abs.EntityDao;
import it.toscana.regione.wsods.ejb.dao.api.IDao;
import it.toscana.regione.wsods.ejb.dao.api.ITracciaObsolescenzaIdUniDao;
import it.toscana.regione.wsods.entity.jpa.api.ITracciaObsolescenzaIdUni;

import javax.ejb.Stateless;


/**
 * @author cciurli
 *
 */
@Stateless(name = ITracciaObsolescenzaIdUniDao.EJB_REF)
public class TracciaObsolescenzaIdUniDao extends EntityDao<ITracciaObsolescenzaIdUni> implements ITracciaObsolescenzaIdUniDao, IDao<ITracciaObsolescenzaIdUni> {
	
	private static final long serialVersionUID = 4203824609909873713L;

	
	/** */ public TracciaObsolescenzaIdUniDao() { super(); }

	/** {@inheritDoc} */ @Override public String ejbRef() { return EJB_REF; }

	/** {@inheritDoc} */ @Override protected Class<ITracciaObsolescenzaIdUni> clazz() { return ITracciaObsolescenzaIdUni.class; }

	/** {@inheritDoc} */ @Override protected String refTable() { return ITracciaObsolescenzaIdUni.class.getName(); }


}

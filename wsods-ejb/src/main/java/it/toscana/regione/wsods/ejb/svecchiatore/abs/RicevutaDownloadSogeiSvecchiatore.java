/** */
package it.toscana.regione.wsods.ejb.svecchiatore.abs;

import it.toscana.regione.wsods.ejb.dao.api.IDao;
import it.toscana.regione.wsods.ejb.dao.api.IRicevutaDownloadSogeiDao;
import it.toscana.regione.wsods.ejb.svecchiatore.api.ISvecchiatore;
import it.toscana.regione.wsods.entity.jpa.api.IRicevutaDownloadSogei;
import it.toscana.regione.wsods.singleton.Conf;

import javax.ejb.EJB;


/**
 * @author cciurli
 *
 */
public abstract class RicevutaDownloadSogeiSvecchiatore extends AbstractSvecchiatore<IRicevutaDownloadSogei> implements ISvecchiatore<IRicevutaDownloadSogei> {

	/** fild serialVersionUID {@like long}*/
	private static final long	serialVersionUID	= -3534666484546667222L;
	
	@EJB(beanName = IDao.EJB_REF_RICEVUTA_DOWNLOAD_SOGEI)
	private IRicevutaDownloadSogeiDao ricevutaDownloadSogeiDao;
	
	/**
	 * 
	 */
	public RicevutaDownloadSogeiSvecchiatore() {super();}

	protected int recordMinimiMantenuti(){ return Conf.getSvecchiatoreRecordMinimiMantenuti(); }
	protected long deltaSvecchiatura(){ return Conf.getDeltaSvecchiatura(); }
	
	
	
	/** {@inheritDoc} */
	@Override
	protected IDao<IRicevutaDownloadSogei> getDao() {
		return ricevutaDownloadSogeiDao;
	}
	
	
	

	/** {@inheritDoc} */
	@Override
	protected Class<IRicevutaDownloadSogei> getTable() {
		return IRicevutaDownloadSogei.class;
	}

	protected String getBaseWhere(){
		return " AND ( t.numeroRecordTrovati = 0 ) ";
	}
	


	

	
}

package it.toscana.regione.wsods.ejb.ricalcolatitolonull.impl;

import it.toscana.regione.wsods.ejb.dao.api.IAutocertificazioneScartateDao;
import it.toscana.regione.wsods.ejb.ricalcolatitolonull.api.IManagerRicalcolaTitoloNull;
import it.toscana.regione.wsods.ejb.ricalcolatitolonull.api.IRicalcolaTitoloNull;
import it.toscana.regione.wsods.entity.jpa.api.IAutocertificazioneScartate;
import it.toscana.regione.wsods.exception.WsOdsRuntimeException;
import it.toscana.regione.wsods.singleton.Conf;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless(name = IManagerRicalcolaTitoloNull.EJB_REF)
public class ManagerRicalcolaTitoloNull implements IManagerRicalcolaTitoloNull {

	private final static Logger	LOG	= LoggerFactory.getLogger(ManagerRicalcolaTitoloNull.class);

	@EJB(beanName = IAutocertificazioneScartateDao.EJB_REF)
	private IAutocertificazioneScartateDao autocertificazioneScartateDao;
	
	@EJB(beanName = IRicalcolaTitoloNull.EJB_REF)
	private IRicalcolaTitoloNull ricalcolaTitoloNull;
	
	@Override
	public void ricalcolaTitoloNull(final long numeroTimer, final long discriminante) throws WsOdsRuntimeException {
		
		final int size = Conf.getRicalcolaTitoloNullDimensioneFienstra();

		LOG.info("si recupera la lista delle autocertificazioni scartate per titolo null con i parametri numeroTimer {}, discriminate {}, size {}");
		final List<IAutocertificazioneScartate> lista = autocertificazioneScartateDao.getForTitoloNull(numeroTimer,discriminante,size);
		if(lista != null && lista.size()>0){
			for(final IAutocertificazioneScartate autocertificazioneScartata : lista){
				if(autocertificazioneScartata != null){
					ricalcolaTitoloNull.ricalcolaTitoloNull(autocertificazioneScartata);
				}
			}
		} else {
			LOG.debug("non vi sono autocertificazioni da elaborare con i parametri numeroTimer {}, discriminate {}, size {}");
		}
	}

}

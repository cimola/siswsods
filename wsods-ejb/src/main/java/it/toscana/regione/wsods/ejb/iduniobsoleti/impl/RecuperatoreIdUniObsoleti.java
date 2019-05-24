package it.toscana.regione.wsods.ejb.iduniobsoleti.impl;

import it.toscana.regione.eng.common.rest.response.entity.sisdatamanager.listaAssociazioniIdUni.Link;
import it.toscana.regione.eng.common.rest.response.entity.sisdatamanager.listaAssociazioniIdUni.ListaAssociazioniIdUni;
import it.toscana.regione.wsods.ejb.dao.api.IIdUniDaAggiornareDao;
import it.toscana.regione.wsods.ejb.iduniobsoleti.api.IRecuperatoreIdUniObsoleti;
import it.toscana.regione.wsods.ejb.sdm.api.ISdm;
import it.toscana.regione.wsods.entity.jpa.api.IIdUniDaAggiornare;
import it.toscana.regione.wsods.entity.jpa.impl.IdUniDaAggiornare;
import it.toscana.regione.wsods.exception.WsOdsRuntimeException;

import java.sql.Timestamp;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless(name=IRecuperatoreIdUniObsoleti.EJB_REF)
public class RecuperatoreIdUniObsoleti implements IRecuperatoreIdUniObsoleti {

	private static final Logger LOG = LoggerFactory.getLogger(RecuperatoreIdUniObsoleti.class);
	
	public RecuperatoreIdUniObsoleti() { super(); }

	@EJB(beanName= IIdUniDaAggiornareDao.EJB_REF)
	private IIdUniDaAggiornareDao idUniDaAggiornareDao;
	
	@EJB(beanName= ISdm.EJB_REF)
	private ISdm sdm;
	
	@Override @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void store(final ListaAssociazioniIdUni listaAssociazioniIdUni) {
		
		if( listaAssociazioniIdUni != null &&  listaAssociazioniIdUni.getLinks() != null && !listaAssociazioniIdUni.getLinks().isEmpty()  ){
			LOG.info("memorizzo la lista di associazioni ottenuta al netto dei duplicati");
			for(final Link el : listaAssociazioniIdUni.getLinks()){
				if(el!=null){
					final IIdUniDaAggiornare entity = new IdUniDaAggiornare();
					entity.setDataUltimoAggiornamento(el.getDataUltimoAggiornamento());
					entity.setSlave(el.getSlave());
					entity.setMaster(el.getMaster());
					if(idUniDaAggiornareDao.notExist(entity)){
						idUniDaAggiornareDao.insert(entity, false);
						LOG.debug("inserito un nuovo record");
					} else {
						LOG.debug("trovato un duplicato");
					}
				}
			}
			idUniDaAggiornareDao.flush();
		} else {
			LOG.debug("la lista e' vuota");
		}
	}

	@Override @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public ListaAssociazioniIdUni get() throws WsOdsRuntimeException {
		LOG.info("recupero la lista di associazioni");
		final Timestamp lastTime = idUniDaAggiornareDao.getLast();
		
		final ListaAssociazioniIdUni listaAssociazioniIdUni =  sdm.getLinks(lastTime);
		return listaAssociazioniIdUni;
	}
}

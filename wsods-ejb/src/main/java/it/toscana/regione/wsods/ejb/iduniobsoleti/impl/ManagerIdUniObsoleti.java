package it.toscana.regione.wsods.ejb.iduniobsoleti.impl;

import it.toscana.regione.eng.common.rest.response.entity.sisdatamanager.listaAssociazioniIdUni.ListaAssociazioniIdUni;
import it.toscana.regione.wsods.ejb.iduniobsoleti.api.IAggiornatoreIdUniObsoleti;
import it.toscana.regione.wsods.ejb.iduniobsoleti.api.IManagerIdUniObsoleti;
import it.toscana.regione.wsods.ejb.iduniobsoleti.api.IRecuperatoreIdUniObsoleti;
import it.toscana.regione.wsods.exception.WsOdsRuntimeException;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless(name=IManagerIdUniObsoleti.EJB_REF)
public class ManagerIdUniObsoleti implements IManagerIdUniObsoleti {

	public ManagerIdUniObsoleti() { super(); }

	private static final Logger LOG = LoggerFactory.getLogger(ManagerIdUniObsoleti.class);
	

	@EJB(beanName= IRecuperatoreIdUniObsoleti.EJB_REF)
	private IRecuperatoreIdUniObsoleti recuperatore;
	
	@EJB(beanName= IAggiornatoreIdUniObsoleti.EJB_REF)
	private IAggiornatoreIdUniObsoleti aggiornatore;


	@Override 	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void recuperaObsoleti() throws WsOdsRuntimeException {
		LOG.info("avvio la procedura di recupero degli idUni obsoleti");
		
		final ListaAssociazioniIdUni listaAssociazioniIdUni = recuperatore.get();
		
		recuperatore.store(listaAssociazioniIdUni);

	}

	

	@Override	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void aggiornaObsoleti() throws WsOdsRuntimeException {
		LOG.info("avvio la procedura di update degli idUni obsoleti");
		boolean empty = false;
		while(!empty){
			final List<Long> idsLink= aggiornatore.recuperaIdLinkDaElaborare();
			LOG.info("ho recuperato {} record da elaborare.");
			
			if(idsLink != null && ! idsLink.isEmpty()){
				for(final Long id : idsLink){
					aggiornatore.riportaAssociazione(id);
				}
			} else {
				LOG.info("non vi sono piu' record da elaborare attendo il prossimo avvio del timer per controllare");
				empty = true;
			}
			
		}
	}



	

	
}

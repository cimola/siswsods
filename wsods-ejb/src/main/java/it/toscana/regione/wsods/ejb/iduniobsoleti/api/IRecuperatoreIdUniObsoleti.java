package it.toscana.regione.wsods.ejb.iduniobsoleti.api;

import it.toscana.regione.eng.common.rest.response.entity.sisdatamanager.listaAssociazioniIdUni.ListaAssociazioniIdUni;
import it.toscana.regione.wsods.exception.WsOdsRuntimeException;

import javax.ejb.Local;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

@Local
public interface IRecuperatoreIdUniObsoleti {


	public final static String 	EJB_REF	= "recuperatoreIdUniObsoleti";
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void store(final ListaAssociazioniIdUni listaAssociazioniIdUni) throws WsOdsRuntimeException;

	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	ListaAssociazioniIdUni get() throws WsOdsRuntimeException;
}

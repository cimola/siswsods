package it.toscana.regione.wsods.ejb.ricalcolatitolonull.api;

import it.toscana.regione.wsods.entity.jpa.api.IAutocertificazioneScartate;
import it.toscana.regione.wsods.exception.WsOdsRuntimeException;

import javax.ejb.Local;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

@Local
public interface IRicalcolaTitoloNull {

	public final static String 	EJB_REF	= "ricalcolaTitoloNull";

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	void ricalcolaTitoloNull(final IAutocertificazioneScartate autocertificazioneScartata) throws WsOdsRuntimeException;
	
}

package it.toscana.regione.wsods.ejb.prorogatore.api;

import it.toscana.regione.wsods.entity.jpa.api.IAutocertificazioneApertureEtl;
import it.toscana.regione.wsods.entity.jpa.api.IAutocertificazioneChiusureEtl;
import it.toscana.regione.wsods.entity.jpa.api.IEsitoInvioApertureEtl;
import it.toscana.regione.wsods.entity.jpa.api.IEsitoInvioChiusureEtl;
import it.toscana.regione.wsods.exception.ProrogatoreException;

import javax.ejb.Local;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

@Local
public interface IProrogatore {
	
	final static String EJB_REF = "prorogatore";
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	IEsitoInvioApertureEtl proroga(final IAutocertificazioneApertureEtl autocertificazioniETL) throws ProrogatoreException ;
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	IEsitoInvioChiusureEtl proroga(final IAutocertificazioneChiusureEtl autocertificazioniETL) throws ProrogatoreException ;

}

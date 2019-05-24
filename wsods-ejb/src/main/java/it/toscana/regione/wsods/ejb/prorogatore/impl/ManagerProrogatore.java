package it.toscana.regione.wsods.ejb.prorogatore.impl;

import it.toscana.regione.wsods.ejb.dao.api.IAutocertificazioneApertureEtlDao;
import it.toscana.regione.wsods.ejb.dao.api.IAutocertificazioneChiusureEtlDao;
import it.toscana.regione.wsods.ejb.dao.api.IEsitoInvioApertureEtlDao;
import it.toscana.regione.wsods.ejb.dao.api.IEsitoInvioChiusureEtlDao;
import it.toscana.regione.wsods.ejb.prorogatore.api.IManagerProrogatore;
import it.toscana.regione.wsods.ejb.prorogatore.api.IProrogatore;
import it.toscana.regione.wsods.ejb.tracker.api.ISoapTracking;
import it.toscana.regione.wsods.entity.jpa.api.IAutocertificazioneApertureEtl;
import it.toscana.regione.wsods.entity.jpa.api.IAutocertificazioneChiusureEtl;
import it.toscana.regione.wsods.entity.jpa.api.IEsitoInvioApertureEtl;
import it.toscana.regione.wsods.entity.jpa.api.IEsitoInvioChiusureEtl;
import it.toscana.regione.wsods.exception.ProrogatoreException;
import it.toscana.regione.wsods.exception.WsOdsRuntimeException;
import it.toscana.regione.wsods.singleton.Conf;
import it.toscana.regione.wsods.type.Code;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless(name = IManagerProrogatore.EJB_REF)
public class ManagerProrogatore implements IManagerProrogatore {
	
	public ManagerProrogatore() { }
	
	
	private static final Logger LOG = LoggerFactory.getLogger(ManagerProrogatore.class);
	
	@EJB(beanName= IAutocertificazioneApertureEtlDao.EJB_REF)
	private IAutocertificazioneApertureEtlDao autocertificazioneApertureEtlDao;

	@EJB(beanName= IAutocertificazioneChiusureEtlDao.EJB_REF)
	private IAutocertificazioneChiusureEtlDao autocertificazioneChiusureEtlDao;
	
	@EJB(beanName= IEsitoInvioApertureEtlDao.EJB_REF)
	private IEsitoInvioApertureEtlDao esitoInvioApertureEtlDao;

	@EJB(beanName= IEsitoInvioChiusureEtlDao.EJB_REF)
	private IEsitoInvioChiusureEtlDao esitoInvioChiusureEtlDao;
	
	@EJB(beanName= ISoapTracking.EJB_REF)
	private ISoapTracking soapTracking;
	
	@EJB(beanName= IProrogatore.EJB_REF)
	private IProrogatore prorogatore;
	
	
	@Override @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void proroga(final long numeroTimer, final long discriminante) throws WsOdsRuntimeException {
		LOG.info("processo di proroga per discriminante {}",discriminante);
		final int maxRowLoad = Conf.getProrogheDimensioneFienstra();
		final long maxElaborazioni = Conf.getProrogheNumeroTentativi();


		
		LOG.info(" recupero la configurazione maxRowLoad {}, maxElaborazioni {} numeroTimer {}",maxRowLoad,maxElaborazioni,numeroTimer);
				
		final List<IAutocertificazioneApertureEtl> listaAperture = autocertificazioneApertureEtlDao.loadFromDiscriminante(maxRowLoad, maxElaborazioni, numeroTimer, discriminante);
		if(listaAperture != null){
			LOG.info("processo di proroga con discriminante {} ha recuperato {} record per le aperture",discriminante,listaAperture.size());
			for(final IAutocertificazioneApertureEtl autocertificazioniETL : listaAperture){
				if(autocertificazioniETL != null){
					LOG.debug("elaboro la proroga con id {}",autocertificazioniETL.getId());
					final IEsitoInvioApertureEtl esito = prorogatore.proroga(autocertificazioniETL);
					if(esito != null){
						esitoInvioApertureEtlDao.insert(esito, true);
					} else {
						//caso che non dovrebbe potersi mai verificare
						throw new ProrogatoreException(Code.GENERICO, "impossibile gestire un esito null");
					}
				} else {
					LOG.warn("si e' cercato di elaborare una proroga null");
				}
			}
		}
		autocertificazioneApertureEtlDao.flush();
		
		final List<IAutocertificazioneChiusureEtl> listaChiusure = autocertificazioneChiusureEtlDao.loadFromDiscriminante(maxRowLoad, maxElaborazioni, numeroTimer, discriminante);
		if(listaChiusure != null){
			LOG.info("processo di proroga con discriminante {} ha recuperato {} record per le chiusure",discriminante,listaChiusure.size());
			for(final IAutocertificazioneChiusureEtl autocertificazioniETL : listaChiusure){
				if(autocertificazioniETL != null){
					LOG.debug("elaboro la proroga con id {}",autocertificazioniETL.getId());
					final IEsitoInvioChiusureEtl esito = prorogatore.proroga(autocertificazioniETL);
					if(esito != null){
						esitoInvioChiusureEtlDao.insert(esito, true);
					} else {
						//caso che non dovrebbe potersi mai verificare
						throw new ProrogatoreException(Code.GENERICO, "impossibile gestire un esito null");
					}
				} else {
					LOG.warn("si e' cercato di elaborare una proroga null");
				}
			}
		}
		autocertificazioneChiusureEtlDao.flush();
		
	}



	
}

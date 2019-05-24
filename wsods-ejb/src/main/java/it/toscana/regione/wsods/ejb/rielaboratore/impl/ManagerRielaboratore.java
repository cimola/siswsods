/** */
package it.toscana.regione.wsods.ejb.rielaboratore.impl;

import it.toscana.regione.wsods.ejb.dao.api.IAutocertificazioneScartateDao;
import it.toscana.regione.wsods.ejb.dao.api.IAutocertificazioneTmpDao;
import it.toscana.regione.wsods.ejb.dao.api.ICertificazioneScartateDao;
import it.toscana.regione.wsods.ejb.dao.api.ICertificazioneTmpDao;
import it.toscana.regione.wsods.ejb.rielaboratore.api.IGianov4Rielaboratore;
import it.toscana.regione.wsods.ejb.rielaboratore.api.IManagerRielaboratore;
import it.toscana.regione.wsods.ejb.rielaboratore.api.ITrasformaRielaboratore;
import it.toscana.regione.wsods.entity.jpa.api.IAutocertificazioneScartate;
import it.toscana.regione.wsods.entity.jpa.api.IAutocertificazioneTmp;
import it.toscana.regione.wsods.entity.jpa.api.ICertificazioneScartate;
import it.toscana.regione.wsods.entity.jpa.api.ICertificazioneTmp;
import it.toscana.regione.wsods.entity.jpa.impl.AutocertificazioneTmp;
import it.toscana.regione.wsods.entity.jpa.impl.CertificazioneTmp;
import it.toscana.regione.wsods.exception.DaoException;
import it.toscana.regione.wsods.exception.RielaboratoreException;
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


/**
 * @author cciurli
 *
 */
@Stateless(name = IManagerRielaboratore.EJB_REF)
public class ManagerRielaboratore implements IManagerRielaboratore {
	
	private final Logger log = LoggerFactory.getLogger(ManagerRielaboratore.class);
	
	@EJB(beanName = ITrasformaRielaboratore.EJB_REF)
	private ITrasformaRielaboratore trasformaRielaboratore;
	
	@EJB(beanName = IAutocertificazioneTmpDao.EJB_REF)
	private IAutocertificazioneTmpDao autocertificazioneTmpDao;
	
	@EJB(beanName = ICertificazioneTmpDao.EJB_REF)
	private ICertificazioneTmpDao certificazioneTmpDao;
	
	@EJB(beanName = IAutocertificazioneScartateDao.EJB_REF)
	private IAutocertificazioneScartateDao autocertificazioneScartateDao;
	
	@EJB(beanName = ICertificazioneScartateDao.EJB_REF)
	private ICertificazioneScartateDao certificazioneScartateDao;
	
	@EJB(beanName = IGianov4Rielaboratore.EJB_REF)
	private IGianov4Rielaboratore gianov4Rielaboratore;
	
	/**
	 * 
	 */
	public ManagerRielaboratore() {super();}

	/** {@inheritDoc} */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public int recuperaRecord() throws WsOdsRuntimeException {
		final int size = Conf.getFinestraRecuperoScartati();
		final List<IAutocertificazioneScartate> scartate = autocertificazioneScartateDao.getForMove(size);
		
		if(scartate!=null && !scartate.isEmpty()){
			for(final IAutocertificazioneScartate scartata : scartate){
				final IAutocertificazioneTmp recuperata = new AutocertificazioneTmp(scartata);
				autocertificazioneTmpDao.insert(recuperata, false);
				autocertificazioneScartateDao.remove(scartata);
			}
			autocertificazioneTmpDao.flush();
			return scartate.size();
		} else {
			return 0;
		}
	}
	
	/** {@inheritDoc} */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public int recuperaRecordCert() throws WsOdsRuntimeException {
		final int size = Conf.getFinestraRecuperoScartatiCert();
		final List<ICertificazioneScartate> scartate = certificazioneScartateDao.getForMove(size);
		
		if(scartate!=null && !scartate.isEmpty()){
			for(final ICertificazioneScartate scartata : scartate){
				final ICertificazioneTmp recuperata = new CertificazioneTmp(scartata);
				certificazioneTmpDao.insert(recuperata, false);
				certificazioneScartateDao.remove(scartata);
			}
			certificazioneTmpDao.flush();
			return scartate.size();
		} else {
			return 0;
		}
	}
	
	/** {@inheritDoc} */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void rielaborarecord(final long numeroTimer, final long discriminante) throws WsOdsRuntimeException {
		final List<IAutocertificazioneTmp> autocertificazioni;
		try{
			autocertificazioni = autocertificazioneTmpDao.loadFromDiscriminante(Conf.getFinestraDiRielaborazione(), numeroTimer, discriminante);
		}catch(DaoException e){
			throw new WsOdsRuntimeException("impossibile recuperare i record con discriminante ["+discriminante+"] ",e);
		}
		
		if(autocertificazioni!=null && !autocertificazioni.isEmpty()){

			for(final IAutocertificazioneTmp autocertificazioneTmp : autocertificazioni){
				
				try{
				
					trasformaRielaboratore.elabora(autocertificazioneTmp);
				
				}catch(RielaboratoreException e){
					
					final long id = autocertificazioneTmp.getId();
					final String msg = "impossibie elaborare il record con id ["+id+"] ";
					
					if( isIncrementalException(e) && !isSpecialValueToSetException(e)){
						autocertificazioneTmpDao.incrementaElaborazioi(id);
					} else if (isSpecialValueToSetException(e)) {
						autocertificazioneTmpDao.setElaborazioi(id, getSpecialValueToSet(e));
					}
					
					throw new WsOdsRuntimeException(msg,e);
					
				}
			}
		} else {
			log.info("la rielaborazione con discriminante "+discriminante+" non ha recuperato record da rielaborare");
		}
	}
	
	/** {@inheritDoc} */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void rielaborarecordCert(final long numeroTimer, final long discriminante) throws WsOdsRuntimeException {
		final List<ICertificazioneTmp> certificazioni;
		try{
			certificazioni = certificazioneTmpDao.loadFromDiscriminante(Conf.getFinestraDiRielaborazioneCert(), numeroTimer, discriminante);
		}catch(DaoException e){
			throw new WsOdsRuntimeException("impossibile recuperare i record con discriminante ["+discriminante+"] ",e);
		}
		
		if(certificazioni!=null && !certificazioni.isEmpty()){

			for(final ICertificazioneTmp certificazioneTmp : certificazioni){
				
				try{
				
					trasformaRielaboratore.elabora(certificazioneTmp);
				
				}catch(RielaboratoreException e){
					
					final long id = certificazioneTmp.getId();
					final String msg = "impossibile elaborare il record con id ["+id+"] ";
					
					if( isIncrementalException(e) && !isSpecialValueToSetException(e)){
						certificazioneTmpDao.incrementaElaborazioi(id);
					} else if (isSpecialValueToSetException(e)) {
						certificazioneTmpDao.setElaborazioi(id, getSpecialValueToSet(e));
					}
					
					throw new WsOdsRuntimeException(msg,e);
					
				}
			}
		} else {
			log.info("il la rielaborazione con discriminante "+discriminante+" non ha recuperato record da rielaborare");
		}
	}
	
	private static boolean isIncrementalException(final RielaboratoreException e){
		if(Code.SDM_SERVIZIO_NON_DISPONIBILE.equals(e.code) ){
			return false;
		} else {
			return true;
		}
	}
	
	private static boolean isSpecialValueToSetException(final RielaboratoreException e){
		if(Code.SDM_SOGGETTO_NON_TROVATO_O_CON_TRATTI_PARZIALI_O_DATI_INPUT_MANCANTI.equals(e.code) ){
			return true;
		} else {
			return false;
		}
	}
	
	private static long getSpecialValueToSet(final RielaboratoreException e){
		if(Code.SDM_SOGGETTO_NON_TROVATO_O_CON_TRATTI_PARZIALI_O_DATI_INPUT_MANCANTI.equals(e.code) ){
			return IAutocertificazioneTmp.NUMERO_ELABORAZIONI_SOGGETTI_PARTECIPANTI_NON_RISOLTI_O_CON_TRATTI_PARZIALI_IN_ANAGRAFE;
		} else {
			return IAutocertificazioneTmp.NUMERO_ELABORAZIONI_SOGGETTI_PARTECIPANTI_NON_RISOLTI_O_CON_TRATTI_PARZIALI_IN_ANAGRAFE;
		}
	}
	
	/** {@inheritDoc} */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public int recuperaRecordGianov4() throws WsOdsRuntimeException {
		final int size = Conf.getFinestraRecuperoScartatiGianov4();
		final List<Long> scartate = autocertificazioneScartateDao.getForGianov4(size);

		if (scartate != null && !scartate.isEmpty()) {
			for (final Long scartata : scartate) {
				try {

					gianov4Rielaboratore.elaboraAutocertificazioneScartate(scartata);

				} catch (RielaboratoreException e) {

					final String msg = "impossibile elaborare il record autocertificazioneScartate con id [" + scartata + "] ";
					throw new WsOdsRuntimeException(msg, e);

				}
			}
			return scartate.size();
		} else {
			return 0;
		}
	}
	
	/** {@inheritDoc} */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public int recuperaRecordCertGianov4() throws WsOdsRuntimeException {
		final int size = Conf.getFinestraRecuperoScartatiCertGianov4();
		final List<Long> scartate = certificazioneScartateDao.getForGianov4(size);

		if (scartate != null && !scartate.isEmpty()) {
			for (final Long scartata : scartate) {
				try {

					gianov4Rielaboratore.elaboraCertificazioneScartate(scartata);

				} catch (RielaboratoreException e) {

					final String msg = "impossibile elaborare il record certificazioneScartate con id [" + scartata + "] ";
					throw new WsOdsRuntimeException(msg, e);

				}
			}
			return scartate.size();
		} else {
			return 0;
		}
	}
	
}

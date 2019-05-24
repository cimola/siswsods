/** */
package it.toscana.regione.wsods.lib;

import it.toscana.regione.autocertificazionireddito.entity.jaxb.rt.AttivitaType;
import it.toscana.regione.autocertificazionireddito.entity.jaxb.rt.AutocertificazioneType;
import it.toscana.regione.autocertificazionireddito.entity.jaxb.rt.CodiceEsenzioneType;
import it.toscana.regione.autocertificazionireddito.entity.jaxb.rt.EsenzioneType;
import it.toscana.regione.autocertificazionireddito.entity.jaxb.rt.FlagTipologiaType;
import it.toscana.regione.autocertificazionireddito.entity.jaxb.rt.MetadatiSOGEIType;
import it.toscana.regione.autocertificazionireddito.entity.jaxb.rt.SorgenteAutocertificazioneType;
import it.toscana.regione.autocertificazionireddito.entity.jaxb.rt.StatoEsenzioneType;
import it.toscana.regione.autocertificazionireddito.entity.jaxb.rt.TitoloDichiaranteType;
import it.toscana.regione.autocertificazionireddito.entity.jaxb.sogei.download.response.Autocertificazione;
import it.toscana.regione.certificazionireddito.entity.jaxb.sogei.download.response.Certificazione;
import it.toscana.regione.wsods.costanti.Varie;
import it.toscana.regione.wsods.entity.bean.api.IEsenzioneOds;
import it.toscana.regione.wsods.entity.jpa.api.IAutocertificazioneApertureEtl;
import it.toscana.regione.wsods.entity.jpa.api.IAutocertificazioneChiusureEtl;
import it.toscana.regione.wsods.entity.jpa.api.IAutocertificazioneEtl;
import it.toscana.regione.wsods.entity.jpa.api.IAutocertificazioneScartate;
import it.toscana.regione.wsods.entity.jpa.api.IAutocertificazioneTmp;
import it.toscana.regione.wsods.entity.jpa.api.ICertificazioneScartate;
import it.toscana.regione.wsods.entity.jpa.api.ICertificazioneTmp;
import it.toscana.regione.wsods.entity.jpa.api.IDownloadAutocertificazione;
import it.toscana.regione.wsods.entity.jpa.api.IDownloadCertificazione;
import it.toscana.regione.wsods.entity.jpa.api.IEsenzioniFasce;
import it.toscana.regione.wsods.entity.jpa.api.IRicevutaDownloadSogei;
import it.toscana.regione.wsods.entity.jpa.api.IUploadAutocertificazione;
import it.toscana.regione.wsods.entity.jpa.impl.AutocertificazioneScartate;
import it.toscana.regione.wsods.entity.jpa.impl.AutocertificazioneTmp;
import it.toscana.regione.wsods.entity.jpa.impl.CertificazioneScartate;
import it.toscana.regione.wsods.entity.jpa.impl.CertificazioneTmp;
import it.toscana.regione.wsods.entity.jpa.impl.DownloadAutocertificazione;
import it.toscana.regione.wsods.entity.jpa.impl.DownloadCertificazione;
import it.toscana.regione.wsods.entity.jpa.impl.EsenzioniFasce;
import it.toscana.regione.wsods.entity.jpa.impl.RicevutaDownloadSogei;
import it.toscana.regione.wsods.entity.jpa.impl.UploadAutocertificazione;
import it.toscana.regione.wsods.exception.ConvertitoreException;
import it.toscana.regione.wsods.exception.MapperException;
import it.toscana.regione.wsods.exception.WsOdsRuntimeException;
import it.toscana.regione.wsods.factory.JaxbElementFactory;
import it.toscana.regione.wsods.type.Code;
import it.toscana.regione.wsods.type.Sorgente;
import it.toscana.regione.wsods.type.TitoloType;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.xml.datatype.XMLGregorianCalendar;


/**
 * @author cciurli
 *
 */
public class Mapper {
	
	private Mapper() { super();}

	public static void map(final boolean nuovo,final IEsenzioneOds esenzioneOds, final IEsenzioniFasce esenzioniFasce) throws MapperException{
		if(esenzioneOds == null){throw new MapperException(Code.NULL_INATTESSO,"Impossibile mappare su un IEsenzioneOds null");}
		if(esenzioniFasce == null){throw new MapperException(Code.NULL_INATTESSO,"Impossibile mappare su un IEsenzioneOds un IEsenzioniFasce nulla ");}
		
		if ( nuovo  ){
			esenzioneOds.setId(esenzioniFasce.getId()); 
			esenzioneOds.setFlagTipologia(esenzioniFasce.getFlagTipologia());
			esenzioneOds.setDataOrdinamento(esenzioniFasce.getDataOrdinamento());
			esenzioneOds.setDataFornitura(esenzioniFasce.getDataFornitura());
		}
		
		if (isValid(esenzioniFasce.getTipoEsenzione()) && (!isValid(esenzioneOds.getTipoEsenzione()) || nuovo ) ){
			esenzioneOds.setTipoEsenzione(esenzioniFasce.getTipoEsenzione());
		}
		if (esenzioniFasce.getIdUniversaleAssistito() != null && (esenzioneOds.getIdUniversaleAssistito() == null || nuovo )  ){
			esenzioneOds.setIdUniversaleAssistito(esenzioniFasce.getIdUniversaleAssistito());
		}
		
		if (esenzioniFasce.getIdUniDichiarante() != null && ( esenzioneOds.getIdUniDichiarante() == null || nuovo )  ){
			esenzioneOds.setIdUniDichiarante(esenzioniFasce.getIdUniDichiarante());
		}
		if ( esenzioniFasce.getIdUniTitolare() != null && ( esenzioneOds.getIdUniTitolare() == null ||  nuovo  )  ){
			esenzioneOds.setIdUniTitolare(esenzioniFasce.getIdUniTitolare());
		}

		if (isValid(esenzioniFasce.getDataInizio()) && (!isValid(esenzioneOds.getDataInizio()) || nuovo )  ){
			esenzioneOds.setDataInizio(esenzioniFasce.getDataInizio());
		}
		if (isValid(esenzioniFasce.getDataFine()) && (!isValid(esenzioneOds.getDataFine()) || nuovo )  ){
			esenzioneOds.setDataFine(esenzioniFasce.getDataFine());
		}
		if (isValid(esenzioniFasce.getDataFineOld()) && (!isValid(esenzioneOds.getDataFineOld()) || nuovo)  ){
			esenzioneOds.setDataFineOld(esenzioniFasce.getDataFineOld());
		}

		if (isValid(esenzioniFasce.getNota()) && (!isValid(esenzioneOds.getNota()) || nuovo)  ){
			esenzioneOds.setNota(esenzioniFasce.getNota());
		}
		if (isValid(esenzioniFasce.getProtocollo()) && (!isValid(esenzioneOds.getProtocollo()) || nuovo)  ){
			esenzioneOds.setProtocollo(esenzioniFasce.getProtocollo());
		}

		if (isValid(esenzioniFasce.getTitolo()) && (!isValid(esenzioneOds.getTitolo()) || nuovo)  ){
			esenzioneOds.setTitolo(esenzioniFasce.getTitolo());
		}

		if (isValid(esenzioniFasce.getSorgente()) && (!isValid(esenzioneOds.getSorgente()) || nuovo)  ){
			esenzioneOds.setSorgente(esenzioniFasce.getSorgente());
		}

		
	}
	private static boolean isValid(final Date d){
		return d!=null ;
	}
	private static boolean isValid(final String s){
		return s!=null && s.trim().length()>0;
	}
	public static void map(final boolean nuovo,IEsenzioneOds esenzioneOds, IDownloadAutocertificazione downloadAutocertificazione) throws MapperException{
		if(esenzioneOds == null){throw new MapperException(Code.NULL_INATTESSO,"Impossibile mappare su un AutocertificazioneType null");}
		if(downloadAutocertificazione != null){
			
		}
	}
	public static void map(final boolean nuovo,IEsenzioneOds esenzioneOds, IUploadAutocertificazione uploadAutocertificazione) throws MapperException{
		if(esenzioneOds == null){throw new MapperException(Code.NULL_INATTESSO,"Impossibile mappare su un AutocertificazioneType null");}
		if(uploadAutocertificazione != null){
			if (uploadAutocertificazione.getComuneCentroImpiego() != null && (esenzioneOds.getComuneCentroImpiego() == null || nuovo)  ){
				esenzioneOds.setComuneCentroImpiego(uploadAutocertificazione.getComuneCentroImpiego());
			}
			if (uploadAutocertificazione.getStatoEsenzione() != null && (esenzioneOds.getStatoEsenzione() == null || nuovo)  ){
				esenzioneOds.setStatoEsenzione(uploadAutocertificazione.getStatoEsenzione());
			}
		} 
	}
	

	public static void map(final IEsenzioneOds esenzioniOds, final EsenzioneType esensioneType) throws MapperException{
		if(esensioneType == null){throw new MapperException(Code.NULL_INATTESSO,"Impossibile mappare su un EsenzioneType null");}
		if(esenzioniOds == null){throw new MapperException(Code.NULL_INATTESSO,"Impossibile mappare su un EsenzioneType un IEsenzioniFasce nulla ");}
		
		if (esenzioniOds.getTipoEsenzione() != null ){
			esensioneType.setCodiceEsenzione(CodiceEsenzioneType.fromValue(esenzioniOds.getTipoEsenzione()));
		}
		if (esenzioniOds.getIdUniversaleAssistito() != null ){
			esensioneType.setBeneficiario(JaxbElementFactory.createSoggettoTypeIdUni(esenzioniOds.getIdUniversaleAssistito()));
		}
		
		if (esenzioniOds.getIdUniDichiarante() != null && esenzioniOds.getIdUniDichiarante().trim().length()>0 ){
			esensioneType.setDichiarante(JaxbElementFactory.createSoggettoTypeIdUni(esenzioniOds.getIdUniDichiarante()));
		}
		if ( esenzioniOds.getIdUniTitolare() != null && esenzioniOds.getIdUniTitolare().trim().length()>0 ){
			esensioneType.setTitolare(JaxbElementFactory.createSoggettoTypeIdUni(esenzioniOds.getIdUniTitolare()));
		}
		try{
			if ( esenzioniOds.getDataInizio() != null ){
				esensioneType.setDataInizio(Convertitore.convertTime(Convertitore.convertTime(esenzioniOds.getDataInizio())));
			} else {
				throw new MapperException(Code.NULL_INATTESSO,"Impossibile mappare su un EsenzioneType un IEsenzioniOds id["+esenzioniOds.getId()+"] dataInizio nulla ");
			}
			if (esenzioniOds.getDataFine() != null  ){
				esensioneType.setDataFine(Convertitore.convertTime(Convertitore.convertTime(esenzioniOds.getDataFine())));
			}else {
				throw new MapperException(Code.NULL_INATTESSO,"Impossibile mappare su un EsenzioneType un IEsenzioniOds id["+esenzioniOds.getId()+"] dataFine nulla ");
			}
			if (  esenzioniOds.getDataFineOld() != null ){
				esensioneType.setDataFineOld(Convertitore.convertTime(Convertitore.convertTime(esenzioniOds.getDataFineOld())));
			}
		}catch(ConvertitoreException e){
			throw new MapperException("Impossibile convertire le date [dataInizio,dataFine,dataFineOld]",e);
		}
		if (esenzioniOds.getNota() != null ){
			esensioneType.setNote(esenzioniOds.getNota());
		}
		if (esenzioniOds.getProtocollo() != null){
			esensioneType.setProtocollo(esenzioniOds.getProtocollo());
		}
		if (esenzioniOds.getTitolo() != null ){
			esensioneType.setTitoloDichiarante(TitoloDichiaranteType.fromValue(TitoloType.decodifica(esenzioniOds.getTitolo()).descrizioneRt));
		}

		if (esenzioniOds.getSorgente() != null  ){
			esensioneType.setSorgente(parserSorgenteAutocertificazione(esenzioniOds.getSorgente()));
		}else {
			esensioneType.setSorgente(SorgenteAutocertificazioneType.ALTRO);
		}
		
		if (esenzioniOds.getComuneCentroImpiego() != null ){
			esensioneType.setComuneCentroImpiego(esenzioniOds.getComuneCentroImpiego());
		}
		final String flagTiplogiaDb = esenzioniOds.getFlagTipologia();
		final Date dataAggiornamento;
		if (flagTiplogiaDb != null && flagTiplogiaDb.trim().length()>0){
			if("F".equalsIgnoreCase(flagTiplogiaDb.trim())){
				esensioneType.setFlagTiplogia(FlagTipologiaType.FAER);
			} else if ("C".equalsIgnoreCase(flagTiplogiaDb.trim())){
				esensioneType.setFlagTiplogia(FlagTipologiaType.CERD);		
			} else {
				new MapperException(Code.FORMATO, "impossibile mappatr il fag tipologia id["+esenzioniOds.getId()+"] flag["+flagTiplogiaDb+"]");
			}
			dataAggiornamento =  esenzioniOds.getDataFornitura();
		} else {
			dataAggiornamento =  esenzioniOds.getDataOrdinamento();
		}
		
		try {
			esensioneType.setDataAggiornamento(Convertitore.convertTime(Convertitore.convertTime(dataAggiornamento)));
		} catch (ConvertitoreException e) {
			new MapperException(Code.FORMATO, "impossibile convertire le date id["+esenzioniOds.getId()+"]");
		}
		
		
	}
	
	public static void map(final IEsenzioneOds esenzioniOds,final AutocertificazioneType autocertificazioneType) throws MapperException{
		if(autocertificazioneType == null){throw new MapperException(Code.NULL_INATTESSO,"Impossibile mappare su un AutocertificazioneType null");}
		if(esenzioniOds == null){throw new MapperException(Code.NULL_INATTESSO,"Impossibile mappare su un AutocertificazioneType un IEsenzioniFasce nulla ");}
		
		
		if (esenzioniOds.getTipoEsenzione() != null ){
			autocertificazioneType.setCodiceEsenzione(CodiceEsenzioneType.fromValue(esenzioniOds.getTipoEsenzione()));
		}
		if (esenzioniOds.getIdUniversaleAssistito() != null ){
			autocertificazioneType.setBeneficiario(JaxbElementFactory.createSoggettoTypeIdUni(esenzioniOds.getIdUniversaleAssistito()));
		} else {
			throw new MapperException(Code.NULL_INATTESSO,"Impossibile mappare su un AutocertificazioneType un IEsenzioniOds id["+esenzioniOds.getId()+"] IdUniversaleAssistito nullo ");
		}
		
		if (esenzioniOds.getIdUniDichiarante() != null && esenzioniOds.getIdUniDichiarante().trim().length()>0 ){
			autocertificazioneType.setDichiarante(JaxbElementFactory.createSoggettoTypeIdUni(esenzioniOds.getIdUniDichiarante()));
		} else {
			autocertificazioneType.setDichiarante(autocertificazioneType.getBeneficiario());
		}
		if ( esenzioniOds.getIdUniTitolare() != null && esenzioniOds.getIdUniTitolare().trim().length()>0 ){
			autocertificazioneType.setTitolare(JaxbElementFactory.createSoggettoTypeIdUni(esenzioniOds.getIdUniTitolare()));
		} else {
			autocertificazioneType.setTitolare(autocertificazioneType.getBeneficiario());
		}
		try{
			if ( esenzioniOds.getDataInizio() != null ){
				autocertificazioneType.setDataInizio(Convertitore.convertTime(Convertitore.convertTime(esenzioniOds.getDataInizio())));
			} else {
				throw new MapperException(Code.NULL_INATTESSO,"Impossibile mappare su un AutocertificazioneType un IEsenzioniOds id["+esenzioniOds.getId()+"] dataInizio nulla ");
			}
			if (esenzioniOds.getDataFine() != null  ){
				autocertificazioneType.setDataFine(Convertitore.convertTime(Convertitore.convertTime(esenzioniOds.getDataFine())));
			}else {
				throw new MapperException(Code.NULL_INATTESSO,"Impossibile mappare su un AutocertificazioneType un IEsenzioniOds id["+esenzioniOds.getId()+"] dataFine nulla ");
			}
			if (  esenzioniOds.getDataFineOld() != null ){
				autocertificazioneType.setDataFineOld(Convertitore.convertTime(Convertitore.convertTime(esenzioniOds.getDataFineOld())));
			} else if (autocertificazioneType.getDataFine() != null  ) {
				autocertificazioneType.setDataFineOld(autocertificazioneType.getDataFine());
			} else {
				throw new MapperException(Code.NULL_INATTESSO,"Impossibile mappare su un AutocertificazioneType un IEsenzioniOds id["+esenzioniOds.getId()+"] dataFineOld e dataFine nulla ");
			}
		}catch(ConvertitoreException e){
			throw new MapperException("Impossibile convertire le date [dataInizio,dataFine,dataFineOld]",e);
		}
		if (esenzioniOds.getNota() != null ){
			autocertificazioneType.setNote(esenzioniOds.getNota());
		}
		if (esenzioniOds.getProtocollo() != null){
			autocertificazioneType.setProtocollo(esenzioniOds.getProtocollo());
		}
		if (esenzioniOds.getTitolo() != null ){
			autocertificazioneType.setTitoloDichiarante(TitoloDichiaranteType.fromValue(TitoloType.decodifica(esenzioniOds.getTitolo()).descrizioneRt));
		}

		if (esenzioniOds.getSorgente() != null  ){
			autocertificazioneType.setSorgente(parserSorgenteAutocertificazione(esenzioniOds.getSorgente()));
		}else {
			autocertificazioneType.setSorgente(SorgenteAutocertificazioneType.ALTRO);
		}
		
		if (esenzioniOds.getComuneCentroImpiego() != null ){
			autocertificazioneType.setComuneCentroImpiego(esenzioniOds.getComuneCentroImpiego());
		}
		if (esenzioniOds.getStatoEsenzione() != null ){
			autocertificazioneType.setStatoEsenzione(StatoEsenzioneType.fromValue(esenzioniOds.getStatoEsenzione()));
		}
		
		ricalcoraStatoEsenzioneDaDate(autocertificazioneType);
		
	}

	
	private static SorgenteAutocertificazioneType parserSorgenteAutocertificazione(final String sorgente){
		if(sorgente==null){return SorgenteAutocertificazioneType.ALTRO;}
		
		for(SorgenteAutocertificazioneType sa : SorgenteAutocertificazioneType.values()){
			if(sa.value().equalsIgnoreCase(sorgente)){return sa;}
		}
		
		return SorgenteAutocertificazioneType.ALTRO;
	}
	public static void ricalcoraStatoEsenzioneDaDate(AutocertificazioneType autocertificazioneType) throws MapperException {
		final XMLGregorianCalendar dataFine = autocertificazioneType.getDataFine();
		final XMLGregorianCalendar dataFineOld = autocertificazioneType.getDataFineOld();
		final StatoEsenzioneType statoEsenzioneType = valutaStatoAutocertificazione(dataFine,dataFineOld);
		autocertificazioneType.setStatoEsenzione(statoEsenzioneType);
	}

	private static StatoEsenzioneType valutaStatoAutocertificazione(final XMLGregorianCalendar dataFine, final XMLGregorianCalendar dataFineOld) throws MapperException {
		final long oggi;
		final long dataFineValidita;
		final long dataFineValiditaOld; 
		try{
			
			if(dataFine!=null ){
				dataFineValidita = Convertitore.convertTime(dataFine);
			} else {
				dataFineValidita = Long.MIN_VALUE;
			}

			if(dataFineOld !=null ){
				dataFineValiditaOld = Convertitore.convertTime(dataFineOld);
			} else {
				dataFineValiditaOld = dataFineValidita;
			}
			
			oggi = Convertitore.convertTime(Convertitore.convertTime(System.currentTimeMillis(),Varie.FORMATO_DATA_SOGEI),Varie.FORMATO_DATA_SOGEI);

		}catch(ConvertitoreException e){
			throw new MapperException("Impossibile mappare interpretare lo stato dell'autocertificazione attraverso le sue date",e);
		}
		
		if(dataFineValiditaOld>dataFineValidita){
			return StatoEsenzioneType.REVOCATA;
		} else if(dataFineValidita<oggi) {
			return StatoEsenzioneType.REVOCATA;
		} else if(dataFineValidita==oggi) {
			return StatoEsenzioneType.REVOCATA;
		}else {
			return StatoEsenzioneType.ATTIVA;
		}
	}

	
	public static IRicevutaDownloadSogei map(final it.toscana.regione.autocertificazionireddito.entity.jaxb.sogei.download.response.Ricevuta ricevuta, final String tipologiaRicevuta) throws MapperException{
		IRicevutaDownloadSogei entity = new RicevutaDownloadSogei();
		
		entity.setTipologiaRicevuta(tipologiaRicevuta);
		
		entity.setDataOraLimite(ricevuta.getDataOraLimite());
		entity.setDiagnostico(ricevuta.getDiagnostico());
		entity.setEsito(ricevuta.getEsito());

		try {
			entity.setNumeroRecordTrovati(Convertitore.convertiToLongNumber(ricevuta.getNumeroRecordTrovati()));
		} catch (ConvertitoreException e) {
			throw new MapperException("impossibile convertire il numero di record trovati ",e);
		}
		entity.setTipoAutocertificazione(ricevuta.getTipoAutocertificazione());
		entity.setCodAsl(ricevuta.getCodiceAsl());
		entity.setCodRegione(ricevuta.getCodiceRegione());
		
		//entity.setUserId(userId);
		//entity.setUtenteTocken(utenteTocken);
		
		
		return entity;
	}
	
	public static IRicevutaDownloadSogei mapCert(final it.toscana.regione.certificazionireddito.entity.jaxb.sogei.download.response.Ricevuta ricevuta, final String tipologiaRicevuta) throws MapperException{
		IRicevutaDownloadSogei entity = new RicevutaDownloadSogei();
		
		entity.setTipologiaRicevuta(tipologiaRicevuta);
		
		entity.setDataOraLimite(ricevuta.getDataLimite().trim());
		entity.setDiagnostico(ricevuta.getDiagnostico());
		entity.setEsito(ricevuta.getEsito());

		try {
			entity.setNumeroRecordTrovati(Convertitore.convertiToLongNumber(ricevuta.getNumeroRecordTrovati()));
		} catch (ConvertitoreException e) {
			throw new MapperException("impossibile convertire il numero di record trovati ",e);
		}
		entity.setTipoAutocertificazione(ricevuta.getOperazione());
		entity.setCodAsl(ricevuta.getCodiceAsl());
		entity.setCodRegione(ricevuta.getCodiceRegione());
		
		return entity;
	}

	public static IUploadAutocertificazione map(final long fkEsenzioniFascia,final AutocertificazioneType autocertificazioneType, final MetadatiSOGEIType metadatiSogei, final AttivitaType attivita) throws MapperException {
		final IUploadAutocertificazione entity = new UploadAutocertificazione();
		
		entity.setComuneCentroImpiego(autocertificazioneType.getComuneCentroImpiego());
		entity.setStatoEsenzione(autocertificazioneType.getStatoEsenzione().value());
		if(metadatiSogei.getDataAggRec()!=null){
			try {
				entity.setDataAggRec(new Timestamp(Convertitore.convertTime(metadatiSogei.getDataAggRec(),Varie.FORMATO_TIME_SOGEI)));
			} catch (ConvertitoreException e) {
				throw new MapperException("impossibile convertire la data DataAggRec ",e);
			}
		}
		if(metadatiSogei.getDataInsRec()!=null){
			try {
				entity.setDataInsRec(new Timestamp(Convertitore.convertTime(metadatiSogei.getDataInsRec(),Varie.FORMATO_TIME_SOGEI)));
			} catch (ConvertitoreException e) {
				throw new MapperException("impossibile convertire la data getDataInsRec ",e);
			}
		}
		entity.setDiagnostico(metadatiSogei.getDiagnostico());
		entity.setEsito(String.valueOf(metadatiSogei.getEsito()));
	
		entity.setFkEsenzioniFasce(fkEsenzioniFascia);
		
		mapAttivita(entity,attivita);
	
		return entity;
	}
	
	private static void mapAttivita(final IUploadAutocertificazione uploadAutocertificazione, final AttivitaType attivita) {
		if(attivita != null) {
			uploadAutocertificazione.setAttivita(attivita.value());
			if(AttivitaType.CANCELLA.equals(attivita)){
				uploadAutocertificazione.setStatoEsenzione("ANNULLATA");
			} else if (AttivitaType.REVOCA.equals(attivita)) {
				uploadAutocertificazione.setStatoEsenzione("REVOCATA");
			} else if (AttivitaType.INSERISCI.equals(attivita)) {
				uploadAutocertificazione.setStatoEsenzione("ATTIVA");
			} 
		}
	}
	
	public static IEsenzioniFasce map(final AutocertificazioneType autocertificazione, final String dataAggRec) throws MapperException{
		final IEsenzioniFasce entity = new EsenzioniFasce();
		
		if(autocertificazione == null){ throw new MapperException(Code.NULL_INATTESSO,"si e' cercato di mappare un autocertificazione nulla ");}
		
		try {
			entity.setDataInizio(new Date(Convertitore.convertTime(autocertificazione.getDataInizio())));
		} catch (ConvertitoreException e) {
			throw new MapperException("impossibile convertire la data inizio validita' ",e);
		}
		try {
			entity.setDataFine(new Date(Convertitore.convertTime(autocertificazione.getDataFine())));
		} catch (ConvertitoreException e) {
			throw new MapperException("impossibile convertire la data fine validita' ",e);
		}
		try {
			entity.setDataFineOld(autocertificazione.getDataFineOld()==null?entity.getDataFine():new Date(Convertitore.convertTime(autocertificazione.getDataFineOld())));
		} catch (ConvertitoreException e) {
			throw new MapperException("impossibile convertire la data fine validita' old ",e);
		}
		
		try {
			entity.setDataOrdinamento(new Timestamp(dataAggRec==null||dataAggRec.trim().length()==0?System.currentTimeMillis():Convertitore.convertTime(dataAggRec,Varie.FORMATO_TIME_SOGEI)));
		} catch (ConvertitoreException e) {
			throw new MapperException("impossibile convertire la data DataAggRec ",e);
		}
		final String noteRow = autocertificazione.getNote();
		final String note;
		if(noteRow==null||noteRow.trim().length()==0){
			note="-";
		} else if(noteRow.trim().length()>256){
			note=noteRow.substring(0,256);
		} else {
			note=noteRow.trim();
		}
		entity.setNota(note);
		
		if(autocertificazione.getProtocollo()==null){throw new MapperException(Code.NULL_INATTESSO,"protocollo nullo");}
		if(autocertificazione.getProtocollo().trim().length()==0){throw new MapperException(Code.PARSER,"protocollo vuoto");}
		entity.setProtocollo(autocertificazione.getProtocollo());
		entity.setTipoEsenzione(autocertificazione.getCodiceEsenzione().value());
		entity.setTitolo(mapTitolo(autocertificazione.getTitoloDichiarante().value()));
		
		entity.setSorgente(autocertificazione.getSorgente().value());
		
		return entity;
	}

	public static IAutocertificazioneTmp map(final Autocertificazione autocertificazione,final Sorgente sorgente) throws MapperException{
		final IAutocertificazioneTmp entity = new AutocertificazioneTmp();

		
		try {
			entity.setDataInizioValid(new Date(Convertitore.convertTime(autocertificazione.getDataInizioValid(),Varie.FORMATO_DATA_SOGEI)));
		} catch (ConvertitoreException e) {
			throw new MapperException("impossibile convertire la data inizio validita' ",e);
		}
		try {
			entity.setDataFineValid(new Date(Convertitore.convertTime(autocertificazione.getDataFineValid(),Varie.FORMATO_DATA_SOGEI)));
		} catch (ConvertitoreException e) {
			throw new MapperException("impossibile convertire la data fine validita' ",e);
		}
		if(autocertificazione.getDataFineValidOld()!=null && autocertificazione.getDataFineValidOld().trim().length()>0){
			try{
				entity.setDataFineValidOld(new Date(Convertitore.convertTime(autocertificazione.getDataFineValidOld(),Varie.FORMATO_DATA_SOGEI)));
			} catch (ConvertitoreException e) {
				throw new MapperException("impossibile convertire la data fine validita' old ",e);
			}
		}else{
			entity.setDataFineValidOld(entity.getDataFineValid());
		}
		

		entity.setAnnoEsenzione(autocertificazione.getAnnoEsenzione());
		entity.setCodAsl(autocertificazione.getCodAsl());
		try {
			entity.setDataAutocertificazione(new Date(Convertitore.convertTime(autocertificazione.getData(),Varie.FORMATO_DATA_SOGEI)));
		} catch (ConvertitoreException e) {
			throw new MapperException("impossibile convertire la data dell'autocertificazione ",e);
		}
		entity.setFlagStato(autocertificazione.getFlagStato());
		
		
		final String noteRow = autocertificazione.getNote();
		final String note;
		if(noteRow==null||noteRow.trim().length()==0){
			note="-";
		} else if(noteRow.trim().length()>256){
			note=noteRow.substring(0,256);
		} else {
			note=noteRow.trim();
		}
		entity.setNote(note);
		entity.setProtocollo(autocertificazione.getProtocollo());
		entity.setCodiceEsenzione(autocertificazione.getCodiceEsenzione());
		entity.setTitolo(mapTitolo(autocertificazione.getTitolo()));
		
		
		
		entity.setCfBeneficiario(autocertificazione.getCfSogEsente());
		entity.setNomeBeneficiario(autocertificazione.getNomeEsente());
		entity.setCognomeBeneficiario(autocertificazione.getCognomeEsente());
		try{
			entity.setDataNascitaBeneficiario(autocertificazione.getDataNascEsente()==null?null:new Date(Convertitore.convertTime(autocertificazione.getDataNascEsente(),Varie.FORMATO_DATA_SOGEI)));
		} catch (ConvertitoreException e) {
			throw new MapperException("impossibile convertire la data di nascita del beneficiario ",e);
		}
		entity.setSessoBeneficiario(autocertificazione.getSessoEsente());
		entity.setComuneNascitaBeneficiario(autocertificazione.getComuneNascEsente());
		
		
		entity.setCfTitolare(autocertificazione.getCfSogTitolare());
		entity.setNomeTitolare(autocertificazione.getNomeTitolare());
		entity.setCognomeTitolare(autocertificazione.getCognomeTitolare());
		try{
			entity.setDataNascitaTitolare(autocertificazione.getDataNascTitolare()==null?null:new Date(Convertitore.convertTime(autocertificazione.getDataNascTitolare(),Varie.FORMATO_DATA_SOGEI)));
		} catch (ConvertitoreException e) {
			throw new MapperException("impossibile convertire la data di nascita del titolare ",e);
		}
		entity.setSessoTitolare(autocertificazione.getSessoTitolare());
		entity.setComuneNascitaTitolare(autocertificazione.getComuneNascTitolare());
		
		
		
		entity.setCfDichiarante(autocertificazione.getCf());
		entity.setNomeDichiarante(autocertificazione.getNome());
		entity.setCognomeDichiarante(autocertificazione.getCognome());
		try{
			entity.setDataNascitaDichiarante(autocertificazione.getDataNascita()==null?null:new Date(Convertitore.convertTime(autocertificazione.getDataNascita(),Varie.FORMATO_DATA_SOGEI)));
		} catch (ConvertitoreException e) {
			throw new MapperException("impossibile convertire la data di nascita del dichiarante ",e);
		}
		entity.setSessoDichiarante(autocertificazione.getSesso());
		entity.setComuneNascitaDichiarante(autocertificazione.getComuneNascita());
		

		entity.setSorgente(sorgente.dbValue);
		
		if(entity.getCfDichiarante()==null || entity.getCfDichiarante().trim().length()==0){
			entity.setNumeroElaborazioni(IAutocertificazioneTmp.NUMERO_ELABORAZIONI_CF_DICHIARANTE_NULLO);
		} 
		if(
			entity.getTitolo()==null &&
			entity.getCfDichiarante()!=null && entity.getCfDichiarante().trim().length()==16 &&
			entity.getCfBeneficiario()!=null && entity.getCfBeneficiario().trim().length()==16 &&
			entity.getCfTitolare()!=null && entity.getCfTitolare().trim().length()==16
		){
			if(
				entity.getCfDichiarante().equals(entity.getCfBeneficiario()) &&
				entity.getCfDichiarante().equals(entity.getCfTitolare()) 
			){
				entity.setTitolo(String.valueOf(TitoloType.INTERESSATO.code));
			} else {
				entity.setNumeroElaborazioni(IAutocertificazioneTmp.NUMERO_ELABORAZIONI_TITOLO_NULLO);
			}
		}
		return entity;
	}
	
	public static ICertificazioneTmp map(final Certificazione certificazione,final Sorgente sorgente) throws MapperException{
		final ICertificazioneTmp entity = map(certificazione);
		entity.setSorgente(sorgente.dbValue);
		
		return entity;
	}
	
	public static ICertificazioneTmp map(final Certificazione certificazione) throws MapperException{
		final ICertificazioneTmp entity = new CertificazioneTmp();
		
		entity.setCfSogEsente(certificazione.getCfSogEsente());
		
		try {
			entity.setDataInizioValid(new Date(Convertitore.convertTime(certificazione.getDataInizioValid(),Varie.FORMATO_DATA_SOGEI)));
		} catch (ConvertitoreException e) {
			throw new MapperException("impossibile convertire la data inizio validita' ",e);
		}
		try {
			entity.setDataFineValid(new Date(Convertitore.convertTime(certificazione.getDataFineValid(),Varie.FORMATO_DATA_SOGEI)));
		} catch (ConvertitoreException e) {
			throw new MapperException("impossibile convertire la data fine validita' ",e);
		}
		if(certificazione.getDataFineValidOld()!=null && certificazione.getDataFineValidOld().trim().length()>0){
			try{
				entity.setDataFineValidOld(new Date(Convertitore.convertTime(certificazione.getDataFineValidOld(),Varie.FORMATO_DATA_SOGEI)));
			} catch (ConvertitoreException e) {
				throw new MapperException("impossibile convertire la data fine validita' old ",e);
			}
		}else{
			entity.setDataFineValidOld(entity.getDataFineValid());
		}

		entity.setAnnoEsenzione(certificazione.getAnnoEsenzione());
		entity.setCodiceEsenzione(certificazione.getCodiceEsenzione());
		
		return entity;
	}
	
	public static IEsenzioniFasce map(final IAutocertificazioneTmp autocertificazione) throws MapperException{
		final IEsenzioniFasce entity = new EsenzioniFasce();
	
		entity.setDataInizio(autocertificazione.getDataInizioValid());
		entity.setDataFine(autocertificazione.getDataFineValid());
		if(autocertificazione.getDataFineValidOld() !=null ){
			entity.setDataFineOld(autocertificazione.getDataFineValidOld());
		}else{
			entity.setDataFineOld(entity.getDataFine());
		}
		
		entity.setDataOrdinamento(autocertificazione.getDataOrdinamento());
		entity.setNota(autocertificazione.getNote());
		entity.setProtocollo(autocertificazione.getProtocollo());
		entity.setTipoEsenzione(autocertificazione.getCodiceEsenzione());
		entity.setTitolo(autocertificazione.getTitolo());
		
		entity.setSorgente(autocertificazione.getSorgente()==null?SorgenteAutocertificazioneType.ALTRO.value():autocertificazione.getSorgente());
		
		return entity;
	}
	
	public static IEsenzioniFasce map(final ICertificazioneTmp certificazione) throws MapperException{
		final IEsenzioniFasce entity = new EsenzioniFasce();
		Date nuova_datafineerogazione;
		entity.setDataInizio(certificazione.getDataInizioValid());
		entity.setDataFine(certificazione.getDataFineValid());
		if(certificazione.getDataFineValidOld() !=null ){
			entity.setDataFineOld(certificazione.getDataFineValidOld());
			nuova_datafineerogazione = new Date(certificazione.getDataFineValidOld().getTime() + TimeUnit.DAYS.toMillis(1));
		}else{
			entity.setDataFineOld(entity.getDataFine());
			nuova_datafineerogazione = new Date(certificazione.getDataFineValid().getTime() + TimeUnit.DAYS.toMillis(1));
		}
		
		entity.setDataOrdinamento(certificazione.getDataOrdinamento());
		entity.setDataFornitura(certificazione.getDataOrdinamento());
		entity.setProtocollo(Util.createEmptyString(2));
		entity.setTipoEsenzione(certificazione.getCodiceEsenzione());
		entity.setTitolo(TitoloType.INTERESSATO.code+"");
		entity.setFlagTipologia(EsenzioniFasce.FLAG_TIPOLOGIA_CERTIFICAZIONE);
		entity.setSorgente(certificazione.getSorgente()==null?SorgenteAutocertificazioneType.ALTRO.value():certificazione.getSorgente());

//		jtra[ESE-32]
//		si decide quanto segue:
//			- il campo ESENZIONI_FASCE.DATA_INIZIO_EROGAZIONE si popola con il valore 01/03/(anno del campo ESENZIONI_FASCE.DATA_INIZIO)
//			il campo ESENZIONI_FASCE.DATA_FINE_EROGAZIONE si popola con il valore 31/03/(anno del campo ESENZIONI_FASCE.DATA_INIZIO + 1)
//			Esempio: se DATA_INZIO = '2015-04-01'
//			DATA_INIZIO_EROGAZIONE = '2015-03-01'
//			DATA_FINE_EROGAZIONE = '2016-03-31'
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(entity.getDataInizio().getTime());
		try {
			entity.setDataInizioErogazione(new Date(Convertitore.convertTime("01/03/" + c.get(Calendar.YEAR), Varie.FORMATO_DATA_SOGEI)));
		} catch (ConvertitoreException e) {
			throw new WsOdsRuntimeException(Code.FORMATO, "valore dataInizioErogazione( [" + "01/03/" + c.get(Calendar.YEAR) + "] non convertibile secondo il fromato ["
					+ Varie.FORMATO_DATA_SOGEI + "]");
		}
		try {
			entity.setDataFineErogazione(new Date(Convertitore.convertTime("31/03/" + (c.get(Calendar.YEAR) + 1), Varie.FORMATO_DATA_SOGEI)));
		} catch (ConvertitoreException e) {
			throw new WsOdsRuntimeException(Code.FORMATO, "valore dataFineErogazione( [" + "31/03/" + c.get(Calendar.YEAR) + "] non convertibile secondo il fromato ["
					+ Varie.FORMATO_DATA_SOGEI + "]");
		}
		
		return entity;
	}

	public static IAutocertificazioneScartate copyTo(final IAutocertificazioneTmp autocertificazione) throws MapperException{
		final IAutocertificazioneScartate entity = new AutocertificazioneScartate(autocertificazione);
		return entity;
	}
	
	public static ICertificazioneScartate copyTo(final ICertificazioneTmp certificazione) throws MapperException{
		final ICertificazioneScartate entity = new CertificazioneScartate(certificazione);
		return entity;
	}

	private static String mapTitolo(final String titolo) throws MapperException{
		if(titolo == null || titolo.trim().length() == 0 ) {return null;}
 
		try {
			return String.valueOf(TitoloType.decodifica(titolo).code);
		} catch (MapperException e) {
			throw new MapperException("impossibile mappare il titolo ",e);
		} catch (IllegalArgumentException e) {
			throw new MapperException(Code.FORMATO, "impossibile mappare il titolo ",e);
		}

	}
	
	public static IDownloadAutocertificazione map(final long fkRicevuta,final long fkEsenzioniFascia, final IAutocertificazioneTmp autocertificazione) throws ConvertitoreException{
		final IDownloadAutocertificazione entity = new DownloadAutocertificazione();
		entity.setFkEsenzioniFasce(fkEsenzioniFascia);
		entity.setFkRicevutaDownloadSogei(fkRicevuta);
		entity.setAnnoEsenzione(autocertificazione.getAnnoEsenzione());
		entity.setCodAsl(autocertificazione.getCodAsl());
		entity.setDataAutocertificazione(autocertificazione.getDataAutocertificazione());
		entity.setFlagStato(autocertificazione.getFlagStato());
		
		return entity;
	}

	public static IDownloadCertificazione map(final long fkRicevuta,final long fkEsenzioniFascia, final ICertificazioneTmp certificazione) throws ConvertitoreException{
		final IDownloadCertificazione entity = new DownloadCertificazione();
		entity.setFkEsenzioniFasce(fkEsenzioniFascia);
		entity.setFkRicevutaDownloadSogei(fkRicevuta);
		entity.setAnnoEsenzione(certificazione.getAnnoEsenzione());
		
		return entity;
	}
	
	public static void map(final IAutocertificazioneApertureEtl autocertificazioniETL, final AutocertificazioneType autocertificazioneType) throws MapperException {
		if(autocertificazioniETL != null  && autocertificazioneType != null){
			autocertificazioneType.setStatoEsenzione(StatoEsenzioneType.ATTIVA);
			autocertificazioneType.setNote("Proroga");
			
			if(autocertificazioniETL.getDataInvio()!=null ){
				try {
					autocertificazioneType.setDataInizio(Convertitore.convertTime(autocertificazioniETL.getDataInvio().getTime()));
				} catch (final ConvertitoreException e) {
					throw new MapperException("impossibile ricavare la data inizio , AutocertificazioneApertureEtl ["+autocertificazioniETL.getId()+"]",e);
				}
			} else {
				throw new MapperException(Code.DATO_MANCANTE,"impossibile settare la data inizio, data invio null , AutocertificazioneApertureEtl ["+autocertificazioniETL.getId()+"]");
			}
			if(autocertificazioniETL.getDataFine()!=null ){
				try {
					final XMLGregorianCalendar data = Convertitore.convertTime(autocertificazioniETL.getDataFine().getTime());
					autocertificazioneType.setDataFine(data);
					autocertificazioneType.setDataFineOld(data);
				} catch (final ConvertitoreException e) {
					throw new MapperException("impossibile ricavare la data fine e data fine old , AutocertificazioneApertureEtl ["+autocertificazioniETL.getId()+"]",e);
				}
			} else {
				throw new MapperException(Code.DATO_MANCANTE,"impossibile settare data fine e data fine old, data fine null , AutocertificazioneApertureEtl ["+autocertificazioniETL.getId()+"]");
			}
			_map(autocertificazioniETL, autocertificazioneType);
		}
	}

	public static void map(final IAutocertificazioneChiusureEtl autocertificazioniETL, final AutocertificazioneType autocertificazioneType) throws MapperException {
		if(autocertificazioniETL != null  && autocertificazioneType != null){
			autocertificazioneType.setStatoEsenzione(StatoEsenzioneType.REVOCATA);
			autocertificazioneType.setProtocollo(autocertificazioniETL.getProtocollo());
			if(autocertificazioniETL.getDataInizio()!=null ){
				try {
					autocertificazioneType.setDataInizio(Convertitore.convertTime(autocertificazioniETL.getDataInizio().getTime()));
				} catch (final ConvertitoreException e) {
					throw new MapperException("impossibile ricavare la data inizio , AutocertificazioneChiusureEtl ["+autocertificazioniETL.getId()+"]",e);
				}
			} else {
				throw new MapperException(Code.DATO_MANCANTE,"impossibile settare la data inizio, data inizio null , AutocertificazioneApertureEtl ["+autocertificazioniETL.getId()+"]");
			}
			if(autocertificazioniETL.getDataInvio()!=null ){
				try {
					autocertificazioneType.setDataFine(Convertitore.convertTime(autocertificazioniETL.getDataInvio().getTime()));
				} catch (final ConvertitoreException e) {
					throw new MapperException("impossibile ricavare la data fine , AutocertificazioneChiusureEtl ["+autocertificazioniETL.getId()+"]",e);
				}
			} else {
				throw new MapperException(Code.DATO_MANCANTE,"impossibile settare la data fine, data Invio null , AutocertificazioneApertureEtl ["+autocertificazioniETL.getId()+"]");
			}
			if(autocertificazioniETL.getDataFineOld()!=null ){
				try {
					autocertificazioneType.setDataFineOld(Convertitore.convertTime(autocertificazioniETL.getDataFineOld().getTime()));
				} catch (final ConvertitoreException e) {
					throw new MapperException("impossibile ricavare la data fine old , AutocertificazioneChiusureEtl ["+autocertificazioniETL.getId()+"]",e);
				}
			} else {
				throw new MapperException(Code.DATO_MANCANTE,"impossibile settare la data fine old, data fine old null , AutocertificazioneApertureEtl ["+autocertificazioniETL.getId()+"]");
			}
			_map(autocertificazioniETL, autocertificazioneType);
		}
	}	
	private static void _map(final IAutocertificazioneEtl autocertificazioniETL, final AutocertificazioneType autocertificazioneType) throws MapperException {
		if(autocertificazioniETL != null  && autocertificazioneType != null){
			if(autocertificazioniETL.getCodiceEsenzione()!=null){
				final CodiceEsenzioneType codiceEsenzioneType = CodiceEsenzioneType.fromValue(autocertificazioniETL.getCodiceEsenzione());
				if(codiceEsenzioneType != null){
					autocertificazioneType.setCodiceEsenzione(codiceEsenzioneType);
				}
			}
			if( autocertificazioniETL.getTitolo() != null ){
				final TitoloType titoloType  = TitoloType.decodifica(autocertificazioniETL.getTitolo());
				if( titoloType != null ){
					final TitoloDichiaranteType titoloDichiaranteType = TitoloDichiaranteType.valueOf(titoloType.descrizioneRt);
					if( titoloDichiaranteType !=null ){
						autocertificazioneType.setTitoloDichiarante(titoloDichiaranteType);
					}
				}
			}
			
			autocertificazioneType.setSorgente(SorgenteAutocertificazioneType.PROROGHE);
		}
		
	}
	

}

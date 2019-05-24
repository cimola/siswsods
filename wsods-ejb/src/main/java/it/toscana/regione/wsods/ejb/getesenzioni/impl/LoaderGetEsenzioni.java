package it.toscana.regione.wsods.ejb.getesenzioni.impl;

import it.toscana.regione.autocertificazionireddito.entity.jaxb.rt.EsenzioneType;
import it.toscana.regione.autocertificazionireddito.entity.jaxb.rt.GetEsenzioniAps2WsOdsRequest;
import it.toscana.regione.autocertificazionireddito.entity.jaxb.rt.GetEsenzioniAps2WsOdsResponse;
import it.toscana.regione.autocertificazionireddito.entity.jaxb.rt.RuoloSogettoType;
import it.toscana.regione.autocertificazionireddito.entity.jaxb.rt.TipoListaType;
import it.toscana.regione.wsods.ejb.dao.api.IDownloadAutocertificazioneDao;
import it.toscana.regione.wsods.ejb.dao.api.IEsenzioniFasceDao;
import it.toscana.regione.wsods.ejb.dao.api.IUploadAutocertificazioneDao;
import it.toscana.regione.wsods.ejb.getesenzioni.api.ILoaderGetEsenzioni;
import it.toscana.regione.wsods.ejb.sdm.api.ISdm;
import it.toscana.regione.wsods.entity.bean.api.IEsenzioneOds;
import it.toscana.regione.wsods.entity.bean.api.ISoggetto;
import it.toscana.regione.wsods.entity.jpa.api.IDownloadAutocertificazione;
import it.toscana.regione.wsods.entity.jpa.api.IEsenzioniFasce;
import it.toscana.regione.wsods.entity.jpa.api.IUploadAutocertificazione;
import it.toscana.regione.wsods.exception.GetEsenzioniException;
import it.toscana.regione.wsods.exception.MapperException;
import it.toscana.regione.wsods.factory.JaxbElementFactory;
import it.toscana.regione.wsods.lib.ConsolidatoreEsenzioni;
import it.toscana.regione.wsods.lib.Mapper;
import it.toscana.regione.wsods.type.Code;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.xml.bind.JAXBElement;

@Stateless(name = ILoaderGetEsenzioni.EJB_REF)
public class LoaderGetEsenzioni implements ILoaderGetEsenzioni {
	
	public LoaderGetEsenzioni() { super(); }
	
	
	@EJB(beanName = ISdm.EJB_REF)
	private ISdm sdm;
	
	@EJB(beanName = IEsenzioniFasceDao.EJB_REF)
	private IEsenzioniFasceDao esenzioniFasceDao;
	
	@EJB(beanName = IUploadAutocertificazioneDao.EJB_REF)
	private IUploadAutocertificazioneDao uploadAutocertificazioneDao;
	
	@EJB(beanName = IDownloadAutocertificazioneDao.EJB_REF)
	private IDownloadAutocertificazioneDao downloadAutocertificazioneDao;
	
	
	@Override
	public JAXBElement<GetEsenzioniAps2WsOdsResponse> load(final JAXBElement<GetEsenzioniAps2WsOdsRequest> req) throws GetEsenzioniException {
		final JAXBElement<GetEsenzioniAps2WsOdsResponse> jaxb = JaxbElementFactory.createGetEsenzioniAps2WsOdsResponse();
		
		final String idUni = estraiIdUni(req);
		
		final TipoListaType tipo = estraiTipo(req);
		
		final List<RuoloSogettoType> listaRuoli = estraiListaRuoli(req);
				
		final List<IEsenzioniFasce> listaSrc = caricaEsenzioniArricchite(idUni,listaRuoli);
		
		final List<IEsenzioniFasce> listaAutocertificazioni = extractAutocertificazioni(listaSrc);
		final List<IEsenzioneOds> listaAutocertificazioniOds = new ArrayList<IEsenzioneOds>();
		ConsolidatoreEsenzioni.map(listaAutocertificazioni,listaAutocertificazioniOds,tipo);
		
		final List<IEsenzioniFasce> listaCertificazioni = extractCertificazioni(listaSrc);
		final List<IEsenzioneOds> listaCertificazioniOds = new ArrayList<IEsenzioneOds>();
		ConsolidatoreEsenzioni.map(listaCertificazioni,listaCertificazioniOds,tipo);

		//final List<IEsenzioneOds> listaEsenzioniOds = extractAndConvertCertificazioni(listaSrc);
		
		final List<IEsenzioneOds> listaEsenzioniOds = new ArrayList<IEsenzioneOds>();
		
		listaEsenzioniOds.addAll(listaCertificazioniOds);
		
		listaEsenzioniOds.addAll(listaAutocertificazioniOds);
		
		map(listaEsenzioniOds,jaxb.getValue());
		
		return jaxb;
	}


//	private static List<IEsenzioneOds> extractAndConvertCertificazioni(final List<IEsenzioniFasce> listaSrc){
//		final List<IEsenzioneOds> subList = new ArrayList<IEsenzioneOds>();
//		if(listaSrc!= null){
//		for(IEsenzioniFasce esenzioniFasce : listaSrc){
//			if(esenzioniFasce.getFlagTipologia() != null && "C".equals(esenzioniFasce.getFlagTipologia().trim())){
//				subList.add(new EsenzioneOds(esenzioniFasce));
//			}
//		}
//		}
//		return subList;
//	}
	private static List<IEsenzioniFasce> extractCertificazioni(final List<IEsenzioniFasce> listaSrc){
		final List<IEsenzioniFasce> subList = new ArrayList<IEsenzioniFasce>();
		for(IEsenzioniFasce esenzioniFasce : listaSrc){
			if(esenzioniFasce.getFlagTipologia() != null && "C".equals(esenzioniFasce.getFlagTipologia().trim())){
				subList.add(esenzioniFasce);
			}
		}
		return subList;
	}
	private static List<IEsenzioniFasce> extractAutocertificazioni(final List<IEsenzioniFasce> listaSrc){
		final List<IEsenzioniFasce> subList = new ArrayList<IEsenzioniFasce>();
		for(IEsenzioniFasce esenzioniFasce : listaSrc){
			if(esenzioniFasce.getFlagTipologia() == null || !"C".equals(esenzioniFasce.getFlagTipologia().trim())){
				subList.add(esenzioniFasce);
			}
		}
		return subList;
	}

	private static void map(final List<IEsenzioneOds> listaSrc,final GetEsenzioniAps2WsOdsResponse dest){
		if(dest == null || dest.getGetEsenzioni() == null || dest.getGetEsenzioni().getListaEsenzioni() == null){throw new GetEsenzioniException(Code.NULL_INATTESSO,"si e' cercato di mappare delle entity su un oggetto jaxb nullo.");}
		if(listaSrc!=null && !listaSrc.isEmpty()){
			for(IEsenzioneOds esenzioniOds : listaSrc){
				final EsenzioneType esenzioneType = JaxbElementFactory.createEsenzioneType();
				try {
					Mapper.map(esenzioniOds,esenzioneType);
				} catch (MapperException e) {
					if(esenzioniOds==null){ throw new GetEsenzioniException(e); }
					final long id = esenzioniOds.getId();
					throw new GetEsenzioniException("problemi nella mappatura dell'entity IEsenzioneOds su AutocertificazioneType id["+id+"]",e);
				}
				dest.getGetEsenzioni().getListaEsenzioni().add(esenzioneType);
				
			}
		}
	}
	
	private List<IEsenzioniFasce> caricaEsenzioniArricchite(final String idUni, final List<RuoloSogettoType> listaRuoli){
		
		final List<IEsenzioniFasce> lista = esenzioniFasceDao.esenzioniAssociate(idUni, listaRuoli);
		
		for(final IEsenzioniFasce ef : lista){
			final IDownloadAutocertificazione downloadAutocertificazione = downloadAutocertificazioneDao.legata(ef.getId());
			final IUploadAutocertificazione uploadAutocertificazione = uploadAutocertificazioneDao.legata(ef.getId());
			ef.setDownloadInfo(downloadAutocertificazione);
			ef.setUploadInfo(uploadAutocertificazione);
		}
		return lista;
	}

	private static List<RuoloSogettoType> estraiListaRuoli(final JAXBElement<GetEsenzioniAps2WsOdsRequest> req) {
		if(req.getValue()==null){
			throw new GetEsenzioniException(Code.NULL_INATTESSO,"ottenuta una request vuota per il metodo getEsenzioni");
		} else if(req.getValue().getGetEsenzioni()== null){
			throw new GetEsenzioniException(Code.NULL_INATTESSO,"ottenuta una request vuota per il metodo getEsenzioni");
		} else if(req.getValue().getGetEsenzioni().getRuolo()==null){
			throw new GetEsenzioniException(Code.NULL_INATTESSO,"ottenuta una lista Ruoli  nulla nella request per il metodo getEsenzioni");
		} else {
			return req.getValue().getGetEsenzioni().getRuolo();
		}
	}
	private static TipoListaType estraiTipo(final JAXBElement<GetEsenzioniAps2WsOdsRequest> req) throws GetEsenzioniException {
		final TipoListaType tipo;
		if(req.getValue()==null){
			throw new GetEsenzioniException(Code.NULL_INATTESSO,"ottenura una request vuota per il metodo getEsenzioni");
		} else if(req.getValue().getGetEsenzioni()== null){
			throw new GetEsenzioniException(Code.NULL_INATTESSO,"ottenura una request vuota per il metodo getEsenzioni");
		} else if(req.getValue().getGetEsenzioni().getTipoLista()==null){
			throw new GetEsenzioniException(Code.NULL_INATTESSO,"ottenura un tipoLista nullo nella request per il metodo getEsenzioni");
		}  else {
			tipo = req.getValue().getGetEsenzioni().getTipoLista();
		}
		return tipo;
	}
	private String estraiIdUni(final JAXBElement<GetEsenzioniAps2WsOdsRequest> req) throws GetEsenzioniException {
		final String idUni;

		if(req.getValue()==null){
			throw new GetEsenzioniException(Code.NULL_INATTESSO,"ottenura una request vuota per il metodo getEsenzioni");
		} else if(req.getValue().getGetEsenzioni()== null){
			throw new GetEsenzioniException(Code.NULL_INATTESSO,"ottenura una request vuota per il metodo getEsenzioni");
		} else if(req.getValue().getGetEsenzioni().getSoggetto() ==null){
			throw new GetEsenzioniException(Code.NULL_INATTESSO,"ottenura un soggetto nullo nella request per il metodo getEsenzioni");
		} else if(req.getValue().getGetEsenzioni().getSoggetto().getCodiceFiscale() == null && req.getValue().getGetEsenzioni().getSoggetto().getIdUniversale() == null ){
			throw new GetEsenzioniException(Code.NULL_INATTESSO,"ottenura un soggetto con codiceFiscale e idUni nulli nella request per il metodo getEsenzioni");
		} else {
			if(req.getValue().getGetEsenzioni().getSoggetto().getCodiceFiscale() != null && req.getValue().getGetEsenzioni().getSoggetto().getCodiceFiscale().getValue()!=null){
				final ISoggetto soggetto = sdm.getFromCodiceFiscale(req.getValue().getGetEsenzioni().getSoggetto().getCodiceFiscale().getValue());
				idUni = soggetto.getIdUni();
			} else if(req.getValue().getGetEsenzioni().getSoggetto().getIdUniversale() != null && req.getValue().getGetEsenzioni().getSoggetto().getIdUniversale().getValue()!=null){
				idUni = req.getValue().getGetEsenzioni().getSoggetto().getIdUniversale().getValue();
			} else {
				throw new GetEsenzioniException(Code.NULL_INATTESSO,"ottenura un soggetto con codiceFiscale vuoto per il metodo getEsenzioni");
			}
			
		}
		return idUni;
		
	}
	
}

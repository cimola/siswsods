/** */
package it.toscana.regione.wsods.ejb.getlista.impl;

import it.toscana.regione.autocertificazionireddito.entity.jaxb.rt.AutocertificazioneType;
import it.toscana.regione.autocertificazionireddito.entity.jaxb.rt.GetListaAps2WsOdsRequest;
import it.toscana.regione.autocertificazionireddito.entity.jaxb.rt.GetListaAps2WsOdsResponse;
import it.toscana.regione.autocertificazionireddito.entity.jaxb.rt.TipoListaType;
import it.toscana.regione.wsods.ejb.dao.api.IDownloadAutocertificazioneDao;
import it.toscana.regione.wsods.ejb.dao.api.IEsenzioniFasceDao;
import it.toscana.regione.wsods.ejb.dao.api.IUploadAutocertificazioneDao;
import it.toscana.regione.wsods.ejb.getlista.api.ILoaderGetLista;
import it.toscana.regione.wsods.ejb.sdm.api.ISdm;
import it.toscana.regione.wsods.entity.bean.api.IEsenzioneOds;
import it.toscana.regione.wsods.entity.bean.api.ISoggetto;
import it.toscana.regione.wsods.entity.jpa.api.IDownloadAutocertificazione;
import it.toscana.regione.wsods.entity.jpa.api.IEsenzioniFasce;
import it.toscana.regione.wsods.entity.jpa.api.IUploadAutocertificazione;
import it.toscana.regione.wsods.exception.GetListaException;
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


/**
 * @author cciurli
 *
 */
@Stateless(name = ILoaderGetLista.EJB_REF)
public class LoaderGetLista implements ILoaderGetLista {
	
	

	@EJB(beanName = ISdm.EJB_REF)
	private ISdm sdm;
	
	@EJB(beanName = IEsenzioniFasceDao.EJB_REF)
	private IEsenzioniFasceDao esenzioniFasceDao;
	
	@EJB(beanName = IUploadAutocertificazioneDao.EJB_REF)
	private IUploadAutocertificazioneDao uploadAutocertificazioneDao;
	
	@EJB(beanName = IDownloadAutocertificazioneDao.EJB_REF)
	private IDownloadAutocertificazioneDao downloadAutocertificazioneDao;
	
	/**
	 * 
	 */
	public LoaderGetLista() { super();}

	/** {@inheritDoc} */
	@Override
	public JAXBElement<GetListaAps2WsOdsResponse> load(final JAXBElement<GetListaAps2WsOdsRequest> req) throws GetListaException {
		
		final JAXBElement<GetListaAps2WsOdsResponse> jaxb = JaxbElementFactory.createGetListaAps2WsOdsResponse();
		
		final String idUni = estraiIdUni(req);
		
		final TipoListaType tipo = estraiTipo(req);
				
		final List<IEsenzioniFasce> listaSrc = estraiListaArricchita(idUni,tipo);
		
		final List<IEsenzioneOds> listaEsenzioniOds = new ArrayList<IEsenzioneOds>();
	
		ConsolidatoreEsenzioni.map(listaSrc,listaEsenzioniOds,tipo);
		
		map(listaEsenzioniOds,jaxb.getValue());
		
		return jaxb;
	}
//	
//

	private static void map(final List<IEsenzioneOds> listaSrc,final GetListaAps2WsOdsResponse dest){
		if(dest == null || dest.getGetLista() == null || dest.getGetLista().getListaAutocertificazioni() == null){throw new GetListaException(Code.NULL_INATTESSO,"si e' cercato di mappare delle entity su un oggetto jaxb nullo.");}
		if(listaSrc!=null && !listaSrc.isEmpty()){
			for(IEsenzioneOds esenzioniOds : listaSrc){
				final AutocertificazioneType autocertificazioneType = JaxbElementFactory.createAutocertificazioneType();
				try {
					Mapper.map(esenzioniOds,autocertificazioneType);
				} catch (MapperException e) {
					if(esenzioniOds==null){ throw new GetListaException(e); }
					final long id = esenzioniOds.getId();
					throw new GetListaException("problemi nella mappatura dell'entity IEsenzioneOds su AutocertificazioneType id["+id+"]",e);
				}
				dest.getGetLista().getListaAutocertificazioni().add(autocertificazioneType);
				
			}
		}
	}
	private static TipoListaType estraiTipo(final JAXBElement<GetListaAps2WsOdsRequest> req) throws GetListaException {
		final TipoListaType tipo;
		if(req.getValue()==null){
			throw new GetListaException(Code.NULL_INATTESSO,"ottenura una request vuota per il metodo get list");
		} else if(req.getValue().getGetLista()== null){
			throw new GetListaException(Code.NULL_INATTESSO,"ottenura una request vuota per il metodo get list");
		} else if(req.getValue().getGetLista().getTipoLista()==null){
			throw new GetListaException(Code.NULL_INATTESSO,"ottenura un tipoLista nullo nella request per il metodo get list");
		}  else {
			tipo = req.getValue().getGetLista().getTipoLista();
		}
		return tipo;
	}
	

	private String estraiIdUni(final JAXBElement<GetListaAps2WsOdsRequest> req) throws GetListaException {
		final String idUni;

		if(req.getValue()==null){
			throw new GetListaException(Code.NULL_INATTESSO,"ottenura una request vuota per il metodo get list");
		} else if(req.getValue().getGetLista()== null){
			throw new GetListaException(Code.NULL_INATTESSO,"ottenura una request vuota per il metodo get list");
		} else if(req.getValue().getGetLista().getSoggetto() ==null){
			throw new GetListaException(Code.NULL_INATTESSO,"ottenura un soggetto nullo nella request per il metodo get list");
		} else if(req.getValue().getGetLista().getTipoLista()==null){
			throw new GetListaException(Code.NULL_INATTESSO,"ottenura un tipoLista nullo nella request per il metodo get list");
		} else if(req.getValue().getGetLista().getSoggetto().getCodiceFiscale() == null && req.getValue().getGetLista().getSoggetto().getIdUniversale() == null ){
			throw new GetListaException(Code.NULL_INATTESSO,"ottenura un soggetto con codiceFiscale e idUni nulli nella request per il metodo get list");
		} else {
			if(req.getValue().getGetLista().getSoggetto().getCodiceFiscale() != null && req.getValue().getGetLista().getSoggetto().getCodiceFiscale().getValue()!=null){
				final ISoggetto soggetto = sdm.getFromCodiceFiscale(req.getValue().getGetLista().getSoggetto().getCodiceFiscale().getValue());
				idUni = soggetto.getIdUni();
			} else if(req.getValue().getGetLista().getSoggetto().getIdUniversale() != null && req.getValue().getGetLista().getSoggetto().getIdUniversale().getValue()!=null){
				idUni = req.getValue().getGetLista().getSoggetto().getIdUniversale().getValue();
			} else {
				throw new GetListaException(Code.NULL_INATTESSO,"ottenura un soggetto con codiceFiscale vuoto per il metodo get list");
			}
			
		}
		return idUni;
		
	}
	
	private List<IEsenzioniFasce> estraiListaArricchita(final String idUni,final TipoListaType tipo) {
		final List<IEsenzioniFasce> lista;
//		if(TipoListaType.STORICOPERSONALE.equals(tipo)){
//			lista = esenzioniFasceDao.autocertificazioniAppartenenti(idUni);
//		}else{
			lista = esenzioniFasceDao.autocertificazioniAssociate(idUni);
//		}
		for(final IEsenzioniFasce ef : lista){
			final IDownloadAutocertificazione downloadAutocertificazione = downloadAutocertificazioneDao.legata(ef.getId());
			final IUploadAutocertificazione uploadAutocertificazione = uploadAutocertificazioneDao.legata(ef.getId());
			ef.setDownloadInfo(downloadAutocertificazione);
			ef.setUploadInfo(uploadAutocertificazione);
		}
		return lista;
	}


	
}

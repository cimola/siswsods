package it.toscana.regione.wsods.lib;

import it.toscana.regione.autocertificazionireddito.entity.jaxb.rt.TipoListaType;
import it.toscana.regione.wsods.entity.bean.api.IEsenzioneOds;
import it.toscana.regione.wsods.entity.bean.impl.EsenzioneOds;
import it.toscana.regione.wsods.entity.jpa.api.IEsenzioniFasce;
import it.toscana.regione.wsods.exception.ConvertitoreException;
import it.toscana.regione.wsods.exception.GetListaException;
import it.toscana.regione.wsods.exception.MapperException;
import it.toscana.regione.wsods.type.Code;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class ConsolidatoreEsenzioni {
	
	private ConsolidatoreEsenzioni() { super();}
	

	public static void map(final List<IEsenzioniFasce> listaSrc,final List< IEsenzioneOds > listaDes, final TipoListaType tipo) throws GetListaException {
		if(listaDes == null){throw new GetListaException(Code.NULL_INATTESSO,"si e' cercato di mappare delle entity del db su una lista nulla.");}
		if(listaSrc == null){throw new GetListaException(Code.NULL_INATTESSO,"si e' cercato di mappare una lista di entity del db nall su un oggetto jaxb nullo.");}
		if(tipo == null){throw new GetListaException(Code.NULL_INATTESSO,"si e' cercato di fare una mappatura di tipo null");}
		if(!listaSrc.isEmpty()){
			final Map<String, IEsenzioneOds> map = new HashMap<String, IEsenzioneOds>();
			final Map<String, Long> mapTime = new HashMap<String, Long>();
			
			for(final IEsenzioniFasce esenzioniFasce : listaSrc){
	
				final boolean nuovo = TipoListaType.STORICO.equals(tipo) || isNuovo(mapTime, esenzioniFasce);
				
				final IEsenzioneOds esenzioneOds = find(map,tipo,genKey(esenzioniFasce));
				
				try {
					Mapper.map(nuovo,esenzioneOds, esenzioniFasce);
				} catch (MapperException e) {
					throw new GetListaException("problemi durante il mapper del' esenzioniFacie",e) ;
				}
				try {
					Mapper.map(nuovo,esenzioneOds, esenzioniFasce.getDownloadInfo());
				} catch (MapperException e) {
					throw new GetListaException("problemi durante il mapper del' downloadAutocertificazione",e) ;
				}
				try {
					Mapper.map(nuovo,esenzioneOds, esenzioniFasce.getUploadInfo());
				} catch (MapperException e) {
					throw new GetListaException("problemi durante il mapper del' uploadAutocertificazione",e) ;
				}
			}
			
			for(final String key : map.keySet()){
				listaDes.add(map.get(key));
			}
		}
	}
	
	private static boolean isNuovo(final Map<String, Long> mapTime, final IEsenzioniFasce esenzioniFasce) throws GetListaException{
		final String key = genKey(esenzioniFasce);
		final long lastInsert;
		if(mapTime.containsKey(key)){
			lastInsert = mapTime.get(key).longValue();
		} else {
			lastInsert = Long.MIN_VALUE;
		}
		final long corrente;
		if(esenzioniFasce.getFlagTipologia()==null){
			if(esenzioniFasce.getDataOrdinamento()==null){throw new GetListaException(Code.PARAMETRO_NULLO,"Data Ordinamento nulla, impossiblie stabilire quale record e' piu' recente");}
			corrente = esenzioniFasce.getDataOrdinamento().getTime();
		} else {
			if(esenzioniFasce.getDataFornitura()==null){throw new GetListaException(Code.PARAMETRO_NULLO,"Data Fornitura nulla, impossiblie stabilire quale record e' piu' recente");}
			corrente = esenzioniFasce.getDataFornitura().getTime();
		}
		final boolean nuovo = lastInsert<=corrente;
		
		if(nuovo){
			if(mapTime.containsKey(key)){
				mapTime.remove(key);
			}
			mapTime.put(key, new Long(corrente));
		}
		return nuovo;
		
	}
	
	private static String genKey(final IEsenzioniFasce esenzioniFasce) throws GetListaException {
		if(esenzioniFasce.getTipoEsenzione() == null ){throw new GetListaException(Code.NULL_INATTESSO,"campo Tipo Esenzione nullo");}
		if(esenzioniFasce.getIdUniversaleAssistito() == null){throw new GetListaException(Code.NULL_INATTESSO,"Campo id universale assisitito nullo");}
		if(esenzioniFasce.getDataInizio() == null){throw new GetListaException(Code.NULL_INATTESSO,"Campo data inizzio nullo");}
		try {
			return esenzioniFasce.getTipoEsenzione()+"-"+esenzioniFasce.getIdUniversaleAssistito()+"- "+Convertitore.convertTime(esenzioniFasce.getDataInizio());
		} catch (ConvertitoreException e) {
			throw new GetListaException("Campo data inizzio malformato",e);
		}
	}

	private static IEsenzioneOds find(final Map<String, IEsenzioneOds> map,final TipoListaType tipo, final String key) throws GetListaException {
		if(map == null){throw new GetListaException(Code.NULL_INATTESSO,"si e' cercato di usare una mappa nulla.");}
		if(tipo == null){throw new GetListaException(Code.NULL_INATTESSO,"si e' cercato di usare un tipo nullo.");}
		if(!TipoListaType.STORICO.equals(tipo) && key == null){throw new GetListaException(Code.NULL_INATTESSO,"si e' cercato di usare una chiave nulla.");}
		
		if(TipoListaType.STORICO.equals(tipo)){
			String keyGen = String.valueOf(map.size()+1);
			map.put(keyGen, new EsenzioneOds());
			return map.get(keyGen);
		} else if(TipoListaType.CONSOLIDATO.equals(tipo)){
			if(!map.containsKey(key)){
				map.put(key, new EsenzioneOds());	
			}
			return map.get(key);
//		} else if(TipoListaType.STORICOPERSONALE.equals(tipo)){
//			if(!map.containsKey(key)){
//				map.put(key, new EsenzioneOds());	
//			}
//			return map.get(key);
		} else {
			throw new GetListaException(Code.FORMATO,"si e' cercato di usare una tipo Lista Sconosciuto.");
		}
		
	}
	
}

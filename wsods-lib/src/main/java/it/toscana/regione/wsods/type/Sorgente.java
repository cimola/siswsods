package it.toscana.regione.wsods.type;

import it.toscana.regione.autocertificazionireddito.entity.jaxb.rt.SorgenteAutocertificazioneType;
import it.toscana.regione.wsods.entity.jpa.api.IRecuperoDownloadSogei;



public enum Sorgente {
	
	WEBAPP(SorgenteAutocertificazioneType.WEBAPP,SorgenteAutocertificazioneType.WEBAPP.value()),
    TOTEM(SorgenteAutocertificazioneType.TOTEM,SorgenteAutocertificazioneType.TOTEM.value()),
    FLUSSO(SorgenteAutocertificazioneType.FLUSSO,SorgenteAutocertificazioneType.FLUSSO.value()),
    PROROGHE(SorgenteAutocertificazioneType.PROROGHE,SorgenteAutocertificazioneType.PROROGHE.value()),
    MAPP(SorgenteAutocertificazioneType.MAPP,SorgenteAutocertificazioneType.MAPP.value()),
    SOGEI_I(SorgenteAutocertificazioneType.ALTRO,"SOGEI_I"),
    SOGEI_V(SorgenteAutocertificazioneType.ALTRO,"SOGEI_V"),
    SOGEI_C(SorgenteAutocertificazioneType.ALTRO,"SOGEI_" + IRecuperoDownloadSogei.TIPO_CERTIFICAZIONE),
    ALTRO(SorgenteAutocertificazioneType.ALTRO,SorgenteAutocertificazioneType.ALTRO.value());
	
	
	
	public final SorgenteAutocertificazioneType xmlValue;
	public final String dbValue;
	
	private Sorgente(final SorgenteAutocertificazioneType xmlValue, final String dbValue){
		this.xmlValue = xmlValue;
		this.dbValue = dbValue;
	}
	public static Sorgente identifica(final SorgenteAutocertificazioneType value) {
		if(value== null){return ALTRO;}
		for(Sorgente c : values()){
			if(c.xmlValue.equals(value)){return c;}
		}
		return ALTRO;
	}
	
	public static Sorgente identifica(final String value) {
		if(value== null){return ALTRO;}
		for(Sorgente c : values()){
			if(c.dbValue.equals(value)){return c;}
		}
		return ALTRO;
	}
	public static SorgenteAutocertificazioneType converti(final String value) {
		return identifica(value).xmlValue;
	}
}

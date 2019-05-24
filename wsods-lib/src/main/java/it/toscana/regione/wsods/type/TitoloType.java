/** */
package it.toscana.regione.wsods.type;

import it.toscana.regione.wsods.exception.MapperException;


/**
 * @author cciurli
 *
 */
public enum TitoloType {
	
	
	INTERESSATO(0,"INTERESSATO","INTERESSATO"),
	GENITORE(1,"GENITORE","GENITORE ESERCENTE LA POTESTA'"),
	TUTORE(2,"TUTORE","TUTORE"),
	CURATORE(3,"CURATORE","INTERESSATO CON ASSISTENZA DEL CURATORE"),
	FAMILIARE(4,"PARENTE","CONIUGE, FIGLIO O ALTRO PARENTE FINO AL III GRADO - PER IMPEDIMENTO SANITARIO"),
	DELEGATO(5,"DELEGA","INTERESSATO (TRAMITE DELEGA)"),
	AMMINISTRATORE(6,"AMMINISTRATORE","AMMINISTRATORE");
	
	public final int code;
	public final String descrizioneRt;
	public final String descrizioneSogei;
	
	private TitoloType(final int code,final String descrizioneRt,final String descrizioneSogei){
		this.code = code;
		this.descrizioneRt = descrizioneRt;
		this.descrizioneSogei = descrizioneSogei;
	}
	public static TitoloType decodifica(final int val) throws MapperException{
		return decodifica(new Integer(val));
	}
	public static TitoloType decodifica(final Object val) throws MapperException{
		if(val != null && val instanceof Number){
			for(TitoloType t : values()){
				if(t.code == ((Number)val).intValue()){ return t;}
			}
		} else if(val != null && val instanceof String){
			final String val_s =((String)val).trim();
			if(val_s.length()>0){
				for(TitoloType t : values()){
					if(String.valueOf(t.code).equals(val_s)){ return t;}
					else if(t.descrizioneRt.equals(val_s)){ return t;}
					else if(t.descrizioneSogei.equals(val_s)){ return t;}
				}
			}
		}
		throw new MapperException(Code.PARSER,"impossibile identificatr il codice ["+val+"]");
	}
}

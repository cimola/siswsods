/** */
package it.toscana.regione.wsods.costanti;


/**
 * @author cciurli
 *
 */
public class Varie {

	/** */
	private Varie() { }
	
	
	public static final long TIME_SECONDO = 1000L;
	public static final long TIME_MINUTO = TIME_SECONDO*60L;
	public static final long TIME_ORA = TIME_MINUTO*60L;
	public static final long TIME_GIORNO = TIME_ORA*24L;

	public static final String FORMATO_GIORNO = "ddMMyyyy";
	public static final String FORMATO_DATA_ORA_LIMITE_SOGEI = "ddMMyyyyHHmm";
	public static final String FORMATO_DATA_SOGEI = "dd/MM/yyyy";
	public static final String FORMATO_TIME_SOGEI = "dd/MM/yyyy-HH:mm:ss";
	public static final String FORMATO_TIME_YYYY_MM_DD_HH_MM_SS_SSS = "yyyy.MM.dd-HH:mm:ss:SSS";
	public static final String FORMATO_TIME_ID_UNI_OBSOLETI = "yyyy-MM-dd HH:mm:ss.SSS";
	public static final String FORMATO_TIME_YYYY_MM_DD = "yyyy.MM.dd-HH";

}

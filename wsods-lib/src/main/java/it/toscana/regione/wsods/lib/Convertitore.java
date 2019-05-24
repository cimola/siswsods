/** */
package it.toscana.regione.wsods.lib;

import it.toscana.regione.wsods.costanti.Varie;
import it.toscana.regione.wsods.exception.ConvertitoreException;
import it.toscana.regione.wsods.type.Code;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * @author cciurli
 *
 */
public class Convertitore {

	private Convertitore() { super();}

	public static String converti(final Throwable t){
		final StringBuffer sb = new StringBuffer("");
		stampa(t,sb,true);
		return sb.toString();
	}
	
	public static String convertiNoStackTrace(Throwable t){
		final StringBuffer sb = new StringBuffer("");
		stampa(t,sb,false);
		return sb.toString();
		
	}
	
	private static void stampa(final Throwable t, final StringBuffer sb,final boolean stackTrace){
		if(t != null){
			if(sb.length()==0){
				final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss.SSS");
				sdf.setLenient(false);
				final String time = sdf.format(new Date());
				sb.append("").append(time).append(" ERROR ").append(t.toString()).append("\n");
			} else {
				sb.append("\tcaused by: ").append(t.toString()).append("\n");
			}
			if(stackTrace){
				for(StackTraceElement el : t.getStackTrace()){
					sb.append("\t\t").append(el.getFileName()).append("(").append(el.getLineNumber()).append(") - ").append(el.getClassName()).append(".").append(el.getMethodName()).append("\n");
				}
			}
			stampa(t.getCause(), sb,stackTrace);
		}
	}
	
	public static long convertTime(final String value, final String formato) throws ConvertitoreException{
		if(value == null){throw new ConvertitoreException(Code.PARAMETRO_NULLO," impossibile convertire un null in millisecondi rappresentanti una data");}
		if(formato == null){throw new ConvertitoreException(Code.PARAMETRO_NULLO," impossibile utilizzare un null come formato di conversione da stringa a millisecondi rappresentanti una data");}
		SimpleDateFormat sdf = new SimpleDateFormat(formato);
		sdf.setLenient(false);
		try {
			final Date dValue = sdf.parse(value);
			if(dValue == null){throw new ConvertitoreException(Code.PARSER, "non e' stato possible ottenere una data a partire dalla stringa ed il formato fornitici ["+value+"],["+formato+"]");}
			return dValue.getTime();
		} catch (ParseException e) {
			throw new ConvertitoreException(Code.PARSER, "Impossibile parsare la data ["+value+"] con il formato ["+formato+"]",e);
		}
	}
	public static String convertTime(final long millisecondi, final String formato) throws ConvertitoreException {
		if(formato == null){throw new ConvertitoreException(Code.PARAMETRO_NULLO, "impossibile utilizzare un null come formato di conversione a millisecondi rappresentanti una data");}
		SimpleDateFormat sdf = new SimpleDateFormat(formato);
		sdf.setLenient(false);
		return sdf.format(new Date(millisecondi));
	}
	public static XMLGregorianCalendar convertTime(final long millisecondi) throws ConvertitoreException {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(new Date(millisecondi));
		try {
			return  DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);
		} catch (DatatypeConfigurationException e) {
			throw new ConvertitoreException(Code.PARSER,"impossibile ottenere la Factory per gli XMLGregorianCalendar",e);
		}
		
	}
	public static long convertTime(final Object time) throws ConvertitoreException {
		if (time == null) {
			throw new ConvertitoreException(Code.PARAMETRO_NULLO,"impossibile estrarre il time in millisecondi da un oggetto null");
		} else if (time instanceof Long) {
			return ((Long) time).longValue();
		} else if (time instanceof XMLGregorianCalendar) {
			return ((XMLGregorianCalendar) time).toGregorianCalendar().getTimeInMillis();
		} else if (time instanceof GregorianCalendar) {
			return ((GregorianCalendar) time).getTimeInMillis();
		} else if (time instanceof Date) {
			return ((Date) time).getTime();
		} else if (time instanceof Timestamp) {
			return ((Timestamp) time).getTime();
		} else if (time instanceof java.sql.Date) {
			return ((java.sql.Date) time).getTime();
		} else {
			throw new ConvertitoreException(Code.TIPO_INATTESO,"impossibile estrarre il time in millisecondi da un oggetto di tipo [" + time.getClass().getName() + "]");
		}
	}
	public static long convertiToLongNumber(final String value) throws ConvertitoreException{
		if(value == null){throw new ConvertitoreException(Code.NAMBER_FORMAT, "impossibile convertire un null in long");}
		try{
			final Long result = new Long(value);
			return result.longValue();
		} catch(NumberFormatException e){
			throw new ConvertitoreException(Code.NAMBER_FORMAT,e);
		}
	}
	public static long convertiTimeInizioGiornata(final Object time) throws ConvertitoreException {
		final long effectiveTime = convertTime(time);
		final String date = convertTime(effectiveTime, Varie.FORMATO_TIME_YYYY_MM_DD);
		final long dateTiem = convertTime(date, Varie.FORMATO_TIME_YYYY_MM_DD);
		return dateTiem;
	}
	public static long convertiTimeFineGiornata(final Object time, final long deltaToTomorrow) throws ConvertitoreException {
		final long dateTiem = convertiTimeInizioGiornata(time);
		return dateTiem+(Varie.TIME_GIORNO-deltaToTomorrow);
	}
	
	
}

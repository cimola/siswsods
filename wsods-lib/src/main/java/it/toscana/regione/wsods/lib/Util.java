package it.toscana.regione.wsods.lib;

import it.toscana.regione.wsods.exception.ConvertitoreException;
import it.toscana.regione.wsods.exception.WsOdsException;
import it.toscana.regione.wsods.exception.WsOdsRuntimeException;
import it.toscana.regione.wsods.type.Code;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.XMLGregorianCalendar;

public class Util {

	public Util() {
		// TODO Auto-generated constructor stub
	}

	public static String MD5(final String text) throws WsOdsException {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new WsOdsException(Code.TOOL_GENERICO, e);
		}
		byte[] md5hash = new byte[32];
		try {
			md.update(text.getBytes("iso-8859-1"), 0, text.length());
		} catch (UnsupportedEncodingException e) {
			throw new WsOdsException(Code.TOOL_GENERICO, e);
		}
		md5hash = md.digest();
		return convertToHex(md5hash);
	}

	public static String MD5(final byte[] data) throws WsOdsException {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new WsOdsException(Code.TOOL_GENERICO, e);
		}
		byte[] md5hash = new byte[32];

		md.update(data, 0, data.length);

		md5hash = md.digest();
		return convertToHex(md5hash);
	}

	public static String convertToHex(final byte[] data) {
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < data.length; i++) {
			int halfbyte = (data[i] >>> 4) & 0x0F;
			int two_halfs = 0;
			do {
				if ((0 <= halfbyte) && (halfbyte <= 9))
					buf.append((char) ('0' + halfbyte));
				else
					buf.append((char) ('a' + (halfbyte - 10)));
				halfbyte = data[i] & 0x0F;
			} while (two_halfs++ < 1);
		}
		return buf.toString();
	}

	public static byte[] convertFromHex(final String text) {
		int len = text.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(text.charAt(i), 16) << 4) + Character
					.digit(text.charAt(i + 1), 16));
		}
		return data;
	}

	public static boolean maggioreDiAllaData(final long birth, final int anni,
			final long momento) {
		try {
			final XMLGregorianCalendar compleanno = Convertitore
					.convertTime(birth);
			final int annoNascita = compleanno.getYear();
			compleanno.setYear(annoNascita + anni);
			final long etaAlCompleanno = Convertitore.convertTime(compleanno);
			return momento - etaAlCompleanno > 0;
		} catch (ConvertitoreException e) {
			throw new WsOdsRuntimeException(
					"impossibile convertire le date necessarie al calcolo", e);
		}
	}

	public static long esimoCompleanno(final long birth, final int anni) {
		try {
			final XMLGregorianCalendar compleanno = Convertitore
					.convertTime(birth);
			final int annoNascita = compleanno.getYear();
			compleanno.setYear(annoNascita + anni);
			return Convertitore.convertTime(compleanno);

		} catch (ConvertitoreException e) {
			throw new WsOdsRuntimeException(
					"impossibile convertire le date necessarie al calcolo", e);
		}
	}

	/**
	 * Ritorna una data alla mezzanotte del giorno successivo al giorno corrente
	 * 
	 * 
	 * @return
	 */

	public static Date tomorrow() {
		GregorianCalendar calendar = new GregorianCalendar();
		return nextDay(calendar.getTime());

	}

	public static Date yesterday() {
		GregorianCalendar calendar = new GregorianCalendar();
		return previousDay(calendar.getTime());

	}

	public static Date nextDay(Date currentDate) {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(currentDate);
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	public static Date previousDay(Date currentDate) {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(currentDate);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	public static Date getStartDayDate(Date currentDate) {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(currentDate);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();

	}

	public static Date get2359ofDate(Date currentDate) {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(currentDate);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();

	}

	public static void main(String[] args) {

		Date tomorrow = Util.tomorrow();
		Date yesterday = Util.yesterday();

		Date yesterday2359 = Util.get2359ofDate(yesterday);

		System.out.println("Yesterday_2359: " + yesterday2359);

		System.out.println("Tomorrow: " + tomorrow);
		Date nextDay = Util.nextDay(tomorrow);
		System.out.println("Next Day: " + nextDay);

		Calendar nowCalendar = new GregorianCalendar();
		nowCalendar.set(Calendar.MONTH, 2);
		System.out.println("NowCalendar: " + nowCalendar.getTime());
		Calendar trentunoTreCalendar = new GregorianCalendar();
		trentunoTreCalendar.set(Calendar.MONTH, Calendar.MARCH);
		trentunoTreCalendar.set(Calendar.DAY_OF_MONTH, 31);
		if (nowCalendar.get(Calendar.MONTH) <= 2) {
			trentunoTreCalendar.set(Calendar.YEAR,
					nowCalendar.get(Calendar.YEAR));
		} else {
			trentunoTreCalendar.set(Calendar.YEAR,
					nowCalendar.get(Calendar.YEAR) + 1);
		}
		System.out.println("TrentunoTre: " + trentunoTreCalendar.getTime());

	}

	/**
	 * Crea una stringa vuota di lunghezza 
	 * uguale al parametro fornito in ingresso.
	 *
	 * @param int - la lunghezza della stringa vuota
	 * @return String
	 */
	public static String createEmptyString(int length) {
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < length; i++) {
			s.append(" ");
		}
		return s.toString();
	}

}

package it.toscana.regione.wsods.lib;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RecuperoDatiCF {

	private final Logger LOG = LoggerFactory.getLogger(RecuperoDatiCF.class);

	public static final String NUMERIC_REGEX = "[0-9]*";
	public static final Pattern NUMERIC_PATTERN = Pattern.compile(NUMERIC_REGEX);

	private String comuneNascita;
	private String codiceFiscale;

	public RecuperoDatiCF(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public void recuperaInformazioni() {
		if (!this.isNotNullString(codiceFiscale)) {
			throw new IllegalArgumentException("Codice Fiscale Nullo");
		}

		comuneNascita = this.gestioneOmocodia();
	}

	/**
	 * Restituisce il codice catastale corretto in caso di omocodia
	 * 
	 * @param comuneNascita
	 * @return
	 */
	private String gestioneOmocodia() {
		try {
			String comuneNascita = codiceFiscale.substring(11, 15);
			String belfioreNascita = comuneNascita.substring(1, comuneNascita.length());
			Matcher m = NUMERIC_PATTERN.matcher(belfioreNascita);
			boolean matchOK = m.matches();
			// se non e' solo numerico devo sostituire le lettere con i numeri
			if (!matchOK) {
				char[] omocodeChar = belfioreNascita.toCharArray();
				char[] belfChar = new char[3];
				for (int i = 0; i < omocodeChar.length; i++) {
					switch (omocodeChar[i]) {
					case 'L':
						belfChar[i] = '0';
						break;
					case 'M':
						belfChar[i] = '1';
						break;
					case 'N':
						belfChar[i] = '2';
						break;
					case 'P':
						belfChar[i] = '3';
						break;
					case 'Q':
						belfChar[i] = '4';
						break;
					case 'R':
						belfChar[i] = '5';
						break;
					case 'S':
						belfChar[i] = '6';
						break;
					case 'T':
						belfChar[i] = '7';
						break;
					case 'U':
						belfChar[i] = '8';
						break;
					case 'V':
						belfChar[i] = '9';
						break;
					default:
						belfChar[i] = omocodeChar[i];
						break;
					}
				}
				belfioreNascita = comuneNascita.charAt(0) + new String(belfChar);
			} else {
				belfioreNascita = comuneNascita;
			}
			return belfioreNascita;

		} catch (Throwable t) {
			LOG.error("Errore durante il recupero del codiceBelfiore dal cf", t);
			return null;
		}
	}

	/**
	 * Verifica che l'oggetto {@link String} fornito in argomento non sia nullo o
	 * 
	 * @param string
	 * @return
	 */
	private boolean isNotNullString(String string) {
		return (string != null && !"".equals(string.trim()));
	}

	public String getComuneNascita() {
		return comuneNascita;
	}

	public static void main(String[] args) {
		RecuperoDatiCF r = new RecuperoDatiCF("JNKVNI78L18Z700V");
		r.recuperaInformazioni();
		System.out.println("JNKVNI78L18Z700V" + " --> " + r.getComuneNascita());
		r = new RecuperoDatiCF("GRGLND93E63Z1N9E");
		r.recuperaInformazioni();
		System.out.println("GRGLND93E63Z1N9E" + " --> " + r.getComuneNascita());
		r = new RecuperoDatiCF("RLNGMR78P08A783D");
		r.recuperaInformazioni();
		System.out.println("RLNGMR78P08A783D" + " --> " + r.getComuneNascita());
	}

}

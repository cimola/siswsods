package it.toscana.regione.wsods.rest;

public class RestConstant {

	public static final String ID_DOMINIO_NAME = "idDominio";
	public static final String ROOT = "root";
	public static final String EXTENSION = "extension";
	public static final String CF_KEY = "MEF";
	public static final String ID_UNIVERSALE_KEY = "DBSIS_ID";
	public static final String NOME = "nome";
	public static final String OIDSENDER = "oidSender";
	public static final String COGNOME = "cognome";
	public static final String SESSO = "sesso";
	public static final String DATANASCITA = "dataNascita";
	public static final String COMUNENASCITA = "comuneNascita";
	public static final String STATONASCITA = "statoNascita";

	public static final String NUMERO_MASSIMO_RECORD_DA_RECUPERARE = "numeroMassimoRecordDaRecuperare";
	public static final String DATA_AGGIORNAMENTO_MINIMA_RECORD_DA_RECUPERARE = "dataAggiornamentoMinimaRecordDaRecuperare";
	

	/** X509. */
	public static final String KEYSTORE_TYPE = "JKS";
	/** X509. */
	public static final String TRUSTSTORE_X509 = "X509";
	/** SSL. */
	public static final String SSL = "SSL";
	/** SunX509. */
	public static final String KEY_MANAGER_FACTORY_TYPE = "SunX509";

	static enum ID_DOMINIO_VALUE {
		ESTRAI_ANAGRAFICA_REGISTRY_MASTER("estraiAnagraficaRegistryMaster"),
		LISTA_ASSOCIAZIONI_ID_UNI("listaAssociazioniIdUni"),
		RICERCA_ANAGRAFICHE_BY_CF_WSODS("ricercaAnagraficheByCfWSODS"),
		RICERCA_ANAGRAFICHE_BY_CF_E_TRATTI_WSODS("ricercaAnagraficheByCfETrattiWSODS");

		public final String descr;

		private ID_DOMINIO_VALUE(String descr) {
			this.descr = descr;
		}

	}
}

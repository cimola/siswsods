package it.toscana.regione.wsods.costanti;


public class Rest {
	
	private Rest() {super();}
	
	public static final String DOMAIN_TIMER_MANAGER = "/timer-manager";
	
	public static final String METHOD_LIST = "/list";
	public static final String METHOD_STATUS_ALL = "/statusAll";
	public static final String METHOD_STATUS = "/status";
	public static final String METHOD_START = "/start";
	public static final String METHOD_START_ALL = "/startAll";
	public static final String METHOD_STOP = "/stop";
	public static final String METHOD_STOP_ALL = "/stopAll";
	public static final String METHOD_CREATE = "/create";

	public static final String PARAM_EJB_REF = "ejbRef";
	public static final String PARAM_NUMERO = "numero";
	public static final String PARAM_TIMEOUT = "timeout";
	
	

	public static final String DOMAIN_DOWNLOAD = "/download";
	
	public static final String METHOD_CONFIG = "/recupero/config";
	public static final String METHOD_FAKE_LAST_DOWNLOAD = "/corrente/fake-last-download";

	public static final String PARAM_MAX = "max";
	public static final String PARAM_MIN = "min";
	public static final String PARAM_TIPO = "tipo";

	public static final String PARAM_TIME = "time";

	
	
	public static final String DOMAIN_ROOT = "/";
	public static final String METHOD_READMI = "readme";
	
}


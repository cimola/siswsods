/** */
package it.toscana.regione.wsods.entity.jpa.api;


/**
 * @author cciurli
 *
 */
public interface IRecuperoDownloadSogei extends IDbEntity {
	
	public static final String TIPO_INSERIMENTO = "I";
	public static final String TIPO_VARIAMENTO = "V";
	public static final String TIPO_VARIAZIONI_AL_MINUTO = "A";
	public static final String TIPO_CERTIFICAZIONE = "C";
	
	String getDataOraLimiteStart();
	void setDataOraLimiteStart(final String dataOraLimiteStart);
	
	String getDataOraLimiteStop();
	void setDataOraLimiteStop(final String dataOraLimiteStop);
		
	String getTipoAutocertificazione();
	void setTipoAutocertificazione(final String tipo);
	
}

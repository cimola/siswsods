/** */
package it.toscana.regione.wsods.entity.jpa.api;

import java.util.Date;

/**
 * @author cciurli
 *
 */
public interface IDownloadAutocertificazione extends IDbEntity {


	String getAnnoEsenzione();


	void setAnnoEsenzione(String annoEsenzione);


	String getCodAsl();


	void setCodAsl(String codAsl);


	Date getDataAutocertificazione();


	void setDataAutocertificazione(Date dataAutocertificazione);


	long getFkEsenzioniFasce();


	void setFkEsenzioniFasce(long fkEsenzioniFasce);


	long getFkRicevutaDownloadSogei();


	void setFkRicevutaDownloadSogei(long fkRicevutaDownloadSogei);


	String getFlagStato();


	void setFlagStato(String flagStato);

}

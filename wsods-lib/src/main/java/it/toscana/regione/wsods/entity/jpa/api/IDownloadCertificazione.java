/** */
package it.toscana.regione.wsods.entity.jpa.api;


/**
 * @author vmaltese
 *
 */
public interface IDownloadCertificazione extends IDbEntity {

	String getAnnoEsenzione();
	void setAnnoEsenzione(String annoEsenzione);

	long getFkEsenzioniFasce();
	void setFkEsenzioniFasce(long fkEsenzioniFasce);

	long getFkRicevutaDownloadSogei();
	void setFkRicevutaDownloadSogei(long fkRicevutaDownloadSogei);
}

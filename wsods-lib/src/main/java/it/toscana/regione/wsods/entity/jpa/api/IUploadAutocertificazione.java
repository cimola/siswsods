/** */
package it.toscana.regione.wsods.entity.jpa.api;

import java.sql.Timestamp;

/**
 * @author cciurli
 *
 */
public interface IUploadAutocertificazione extends IDbEntity {


	String getAttivita();


	void setAttivita(String attivita);


	String getComuneCentroImpiego();


	void setComuneCentroImpiego(String comuneCentroImpiego);


	Timestamp getDataAggRec();


	void setDataAggRec(Timestamp dataAggRec);


	Timestamp getDataInsRec();


	void setDataInsRec(Timestamp dataInsRec);


	String getDiagnostico();


	void setDiagnostico(String diagnostico);


	String getEsito();


	void setEsito(String esito);


	long getFkEsenzioniFasce();


	void setFkEsenzioniFasce(long fkEsenzioniFasce);


	String getStatoEsenzione();


	void setStatoEsenzione(String statoEsenzione);


	String getUserId();


	void setUserId(String userId);


	String getUtenteTocken();


	void setUtenteTocken(String utenteTocken);

}

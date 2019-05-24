/** */
package it.toscana.regione.wsods.entity.jpa.api;

import it.toscana.regione.wsods.entity.api.IEntity;

import java.util.Date;

/**
 * @author gorlando
 * 
 */
public interface IRCTCodBelfStati extends IEntity {

	// PK
	// COD_ISTAT
	// COD_BELFIORE
	// DATA_INIZIO
	// DATA_FINE

	String getPk();

	void setPk(String pk);

	String getCodIstat();

	void setCodIstat(String codIstat);

	String getCodBelfiore();

	void setCodBelfiore(String codBelfiore);

	Date getDataInizio();

	void setDataInizio(Date dataInizio);

	Date getDataFine();

	void setDataFine(Date dataFine);

}
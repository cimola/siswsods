/** */
package it.toscana.regione.wsods.entity.jpa.api;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @author vmaltese
 *
 */
public interface ICertificazioneTmp extends IDbEntity {
	
	public final static long NUMERO_ELABORAZIONI_CF_DICHIARANTE_NULLO = 900L;
	
	String getAnnoEsenzione();
	void setAnnoEsenzione(String annoEsenzione);
	
	String getCodiceEsenzione();
	void setCodiceEsenzione(String codiceEsenzione);

	String getCfSogEsente();
	void setCfSogEsente(String cfSogEsente);
		
	Date getDataInizioValid();
	void setDataInizioValid(Date dataInizioValid);
	
	Date getDataFineValid();
	void setDataFineValid(Date dataFineValid);
	
	Date getDataFineValidOld();
	void setDataFineValidOld(Date dataFineValidold);
	
	Timestamp getDataOrdinamento();
	void setDataOrdinamento(final Timestamp dataOrdinamento);
	
	long getFkRicevutaDownloadSogei();
	void setFkRicevutaDownloadSogei(long fkRicevutaDownloadSogei);
		
	long getNumeroElaborazioni();
	void setNumeroElaborazioni(long numeroElaborazioni);
	
	long getRicevutaIndex();
	void setRicevutaIndex(long ricevutaIndex);
	
	public String getSorgente();
	void setSorgente(final String sorgente);
}